package com.ontop.payments.transfer.repository;

import com.github.javafaker.Faker;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.application.command.TransferDestinationCommand;
import com.ontop.payments.transfer.application.command.TransferSourceCommand;
import com.ontop.payments.transfer.domain.Transfer;
import com.ontop.payments.transfer.domain.TransferStatus;
import com.ontop.payments.transfer.domain.UserAccount;
import com.ontop.payments.transfer.infrastructure.repository.PaymentsHttpRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
class PaymentsHttpRepositoryTest {

    @Autowired
    private PaymentsHttpRepository paymentsHttpRepository;
    private final Faker faker = new Faker();

    @Test
    void paymentProvider() {
        var transfer = createTestTransfer();

        var response = paymentsHttpRepository.PaymentProvider(transfer);

        assertThat(response).isNotNull();
    }

    private Transfer createTestTransfer() {
        var userId = faker.number().randomNumber();
        var transferSource = new TransferSourceCommand(userId, faker.number().randomNumber());
        var transferDestination = new TransferDestinationCommand(faker.number().randomNumber());

        var transfer =  new Transfer(new TransferCommand(1100L, transferSource, transferDestination));
        transfer.setStatus(TransferStatus.PENDING);
        transfer.setSource(UserAccount.builder()
                .userId(userId)
                .accountId(faker.number().randomNumber())
                .type("COMPANY")
                .routingNumber("028444018")
                .currency("USD")
                .accountNumber("0245253419")
                .build());
        transfer.setDestination(Recipient.builder()
                        .bankName("BANK OF AMERICA")
                        .routingNumber("211927207")
                        .nationalIdentificationNumber("123456789")
                        .lastName(faker.name().lastName())
                        .firstName(faker.name().firstName())
                        .userId(userId)
                        .accountNumber("1885226711")
                .currency("USD").build());
        return  transfer;
    }
}