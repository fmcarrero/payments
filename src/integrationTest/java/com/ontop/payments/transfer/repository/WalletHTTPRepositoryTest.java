package com.ontop.payments.transfer.repository;

import com.github.javafaker.Faker;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.application.command.TransferDestinationCommand;
import com.ontop.payments.transfer.application.command.TransferSourceCommand;
import com.ontop.payments.transfer.domain.Transfer;
import com.ontop.payments.transfer.infrastructure.repository.WalletHTTPRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class WalletHTTPRepositoryTest {

    @Autowired
    private  WalletHTTPRepository walletHTTPRepository;

    private final Faker faker = new Faker();

    @Test
    void getBalanceByUser() {
        var userId = faker.number().randomNumber();
        var balance = walletHTTPRepository.getBalanceByUser(userId);

        assertThat(balance).isNotNull();
        assertThat(balance.getUserId()).isEqualTo(userId);
        assertThat(balance.getBalance()).isGreaterThan(0);
    }

    @Test
    void saveTransaction() {
        Transfer transfer = createTestTransfer();
        var response = walletHTTPRepository.saveTransaction(transfer);

        assertThat(response).isNotNull();
        assertThat(response.getAmount()).isEqualTo(transfer.getTotalWithdrawal());
    }

    @Test
    void saveTransactionWithInvalidSource() {
        assertThatThrownBy(() -> {
            walletHTTPRepository.saveTransaction(null);
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining(" because \"transfer\" is null");
    }

    private Transfer createTestTransfer() {
        var transferSource = new TransferSourceCommand(faker.number().randomNumber(), faker.number().randomNumber());
        var transferDestination = new TransferDestinationCommand(faker.number().randomNumber());

        return new Transfer(new TransferCommand(1100L, transferSource, transferDestination));
    }
}