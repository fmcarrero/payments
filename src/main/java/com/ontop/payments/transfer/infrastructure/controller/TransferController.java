package com.ontop.payments.transfer.infrastructure.controller;

import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.application.send.TransferMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    public final TransferMoney transferMoney;

    public TransferController(TransferMoney transferMoney){
        this.transferMoney = transferMoney;
    }

    @PostMapping("/transfers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CreateTransferResponse> transfer(@RequestBody TransferCommand transferCommand){
        Transaction transaction = this.transferMoney.execute(transferCommand);

        return ResponseEntity.ok(createTestTransfer(transaction));
    }
    private CreateTransferResponse createTestTransfer(Transaction transaction) {
        return  CreateTransferResponse.builder()
                .amount(transaction.getAmount())
                .userId(transaction.getUserId())
                .createdAt(transaction.getCreatedAt())
                .id(transaction.getId())
                .paymentId(transaction.getPaymentId())
                .walletId(transaction.getWalletId())
                .build();
    }
}
