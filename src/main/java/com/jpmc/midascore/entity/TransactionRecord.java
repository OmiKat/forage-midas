package com.jpmc.midascore.entity;

import com.jpmc.midascore.foundation.Transaction;
import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id" , nullable = false)
    private UserRecord sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id" , nullable = false)
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private boolean isvalid;

    @Column(nullable = false)
    private float incentive;

    public TransactionRecord(float amount, boolean isvalid, UserRecord recipient, UserRecord sender , float incentive) {
        this.amount = amount;
        this.isvalid = isvalid;
        this.recipient = recipient;
        this.sender = sender;
        this.incentive=incentive;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "amount=" + amount +
                ", id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", isvalid=" + isvalid +
                '}';
    }
    public static TransactionRecord fromTransaction(Transaction transaction, UserRecord sender, UserRecord recipient, boolean valid , float incentive) {
        return new TransactionRecord(transaction.getAmount() , valid, sender, recipient , incentive);
    }
    public TransactionRecord() {
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }
}
