package com.ontop.payments.recipient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.ontop.payments.recipient.application.command.CreateRecipientCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RecipientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Faker faker = new Faker();

    @Autowired
    private  ObjectMapper objectMapper;


    @Test
    void save() throws Exception {
        var recipient = createTestRecipient();
        var body = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(recipient);
        mockMvc.perform(post("/users/1/recipients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.routing_number").value(recipient.getRoutingNumber()));

    }

    private CreateRecipientCommand createTestRecipient() {
        return  CreateRecipientCommand.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .nationalIdentificationNumber(faker.idNumber().ssnValid())
                .accountNumber(faker.finance().iban("ES"))
                .bankName(faker.funnyName().name())
                .routingNumber(faker.finance().bic())
                .currency(faker.currency().code())
                .build();
    }
}
