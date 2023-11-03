package com.ontop.payments.recipient.infrastructure.controller;

import com.ontop.payments.recipient.application.SaveRecipients;
import com.ontop.payments.recipient.application.command.CreateRecipientCommand;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.infrastructure.controller.response.RecipientSaveResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RecipientController {
    private final SaveRecipients saveRecipients;

    public RecipientController(SaveRecipients saveRecipients){

        this.saveRecipients = saveRecipients;
    }
    @PostMapping("/users/{user_id}/recipients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecipientSaveResponse> save(@PathVariable("user_id") Long userId, @RequestBody CreateRecipientCommand createRecipient){

        Recipient recipient = this.saveRecipients.execute(userId, createRecipient);
        return ResponseEntity.ok(toResponse(recipient));
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
