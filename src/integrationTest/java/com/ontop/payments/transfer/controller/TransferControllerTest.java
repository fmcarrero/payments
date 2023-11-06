package com.ontop.payments.transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.domain.RecipientRepository;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.application.command.TransferDestinationCommand;
import com.ontop.payments.transfer.application.command.TransferSourceCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipientRepository recipientRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);

    private final Faker faker = new Faker();

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save() throws Exception {
        var recipient = recipientRepository.save(createTestRecipient());
        var transferCommand = createTestTransfer(recipient.getId(), recipient.getUserId());
        var body = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(transferCommand);

        mockMvc.perform(post("/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(transferCommand.getSource().getUserId()))
                .andExpect(jsonPath("$.amount").value(110 ));
    }

    private Recipient createTestRecipient() {
        return  Recipient.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .nationalIdentificationNumber(faker.idNumber().ssnValid())
                .accountNumber(faker.finance().iban("ES"))
                .bankName(faker.funnyName().name())
                .routingNumber(faker.finance().bic())
                .currency(faker.currency().code())
                .id(snowflakeIdGenerator.nextId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private TransferCommand createTestTransfer(Long recipientId, Long userId) {
        return  TransferCommand.builder()
                .amount(100)
                .source(TransferSourceCommand.builder()
                        .userId(userId)
                        .build())
                .destination(TransferDestinationCommand.builder()
                        .recipientId(recipientId)
                        .build())
                .build();
    }
}