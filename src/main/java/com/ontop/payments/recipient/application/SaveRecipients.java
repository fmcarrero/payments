package com.ontop.payments.recipient.application;

import com.ontop.payments.recipient.application.command.CreateRecipientCommand;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.domain.RecipientRepository;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SaveRecipients {

    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final RecipientRepository recipientRepository;

    public SaveRecipients(SnowflakeIdGenerator snowflakeIdGenerator, RecipientRepository recipientRepository) {
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.recipientRepository = recipientRepository;
    }

    public Recipient execute(Long userId, CreateRecipientCommand createRecipientCommand){
        Recipient recipient = commandToRecipient(createRecipientCommand,userId);
        return this.recipientRepository.save(recipient);
    }
    private Recipient commandToRecipient(CreateRecipientCommand createRecipientCommand,Long userId){
        return  Recipient.builder()
                .id(this.snowflakeIdGenerator.nextId())
                .firstName(createRecipientCommand.getFirstName())
                .lastName(createRecipientCommand.getLastName())
                .routingNumber(createRecipientCommand.getRoutingNumber())
                .nationalIdentificationNumber(createRecipientCommand.getNationalIdentificationNumber())
                .accountNumber(createRecipientCommand.getAccountNumber())
                .bankName(createRecipientCommand.getBankName())
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .build();
    }
}
