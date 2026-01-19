package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepo;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final UserRepository userRepo;
    private final TransactionRecordRepo transactionRepo;

    @Autowired
    public TransactionService(UserRepository userRepo, TransactionRecordRepo transactionRepo) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
    }

    @Transactional
    public void processTransaction(Transaction transaction){
        logger.info("processing Transaction : {}" , transaction);

        //get the sender and the recipient
        UserRecord sender = userRepo.findById(transaction.getSenderId());
        UserRecord recipient = userRepo.findById(transaction.getRecipientId());

        //check for validity
        boolean isValid = validateTransaction(transaction , sender , recipient);

        if(isValid){
            executeTransaction(transaction, sender , recipient);
            logger.info("Transaction executed successfully : {}" , transaction);
        }
        else{
            logger.info("Transaction failed : {}" , transaction);
        }

        TransactionRecord record = TransactionRecord.fromTransaction(transaction,sender,recipient,isValid);
        transactionRepo.save(record);
        logger.info("Transaction recorded: {}", record);

    }

    private boolean validateTransaction(Transaction transaction , UserRecord sender , UserRecord recipient){
        if(sender == null){
            logger.warn("The sender with Id {} not found " , transaction.getSenderId());
            return false;
        }
        if(recipient == null){
            logger.warn("the recipient with Id {} not found " , transaction.getRecipientId());
            return false;
        }
        if(sender.getBalance() < transaction.getAmount() ){
            logger.warn("Insufficient balance for sender {} : required {} , available {}",
                    sender.getName() , transaction.getAmount() , sender.getBalance());
            return false;
        }
        if(transaction.getAmount() <= 0){
            logger.warn("The minimum transaction amount is 1");
            return false;
        }
        return true;
    }

    private void executeTransaction(Transaction transaction , UserRecord sender , UserRecord recipient){
        float newSenderBalance = - transaction.getAmount() + sender.getBalance();
        sender.setBalance(newSenderBalance);
        userRepo.save(sender);

        float newRecipientBalance = transaction.getAmount() + recipient.getBalance();
        recipient.setBalance(newRecipientBalance);
        userRepo.save(recipient);

        logger.info("Balances updated - Sender {}: {} -> {}, Recipient {}: {} -> {}",
                sender.getName(), sender.getBalance() - transaction.getAmount(), newSenderBalance,
                recipient.getName(), recipient.getBalance() + transaction.getAmount() , newRecipientBalance);

    }

}
