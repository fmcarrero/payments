package com.ontop.payments.recipient.infrastructure.controller;

import com.ontop.payments.recipient.application.SaveRecipients;
import com.ontop.payments.recipient.application.command.CreateRecipientCommand;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.infrastructure.controller.response.RecipientSaveResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipientController {
    private final SaveRecipients saveRecipients;

    public RecipientController(SaveRecipients saveRecipients){

        this.saveRecipients = saveRecipients;
    }
    @PostMapping("/users/{user_id}/recipients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecipientSaveResponse> save(@PathVariable("user_id") Long userId, @RequestBody CreateRecipientCommand createRecipient){
        Recipient recipient = this.saveRecipients.execute(userId, createRecipient);
        return new ResponseEntity<>(toResponse(recipient), HttpStatus.CREATED)  ;
    }
    private RecipientSaveResponse toResponse(Recipient recipient){
        return RecipientSaveResponse.builder()
                .id(recipient.getId())
                .firstName(recipient.getFirstName())
                .lastName(recipient.getLastName())
                .accountNumber(recipient.getAccountNumber())
                .routingNumber(recipient.getRoutingNumber())
                .nationalIdentificationNumber(recipient.getNationalIdentificationNumber())
                .createdAt(recipient.getCreatedAt())
                .bankName(recipient.getBankName())
                .userId(recipient.getUserId())
                .build();
    }
}
