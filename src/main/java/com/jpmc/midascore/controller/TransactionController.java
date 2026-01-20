package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private UserRecord userRecord;

    @Autowired
    private TransactionRecord transactionRecord;




}
