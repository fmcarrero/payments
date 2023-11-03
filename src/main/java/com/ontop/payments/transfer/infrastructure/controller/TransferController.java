package com.ontop.payments.transfer.infrastructure.controller;

import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.application.send.TransferMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class TransferController {

    public final TransferMoney transferMoney;

    public TransferController(TransferMoney transferMoney){
        this.transferMoney = transferMoney;
    }

    @PostMapping("/transfers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Transaction> transfer(@RequestBody TransferCommand transferCommand){
        Transaction transaction = this.transferMoney.Execute(transferCommand);

        return ResponseEntity.ok(transaction);
    }
}
