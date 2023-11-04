package com.ontop.payments.transaction.repository;

import com.github.javafaker.Faker;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.infrastructure.repository.TransactionPostgresRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
class TransactionPostgresRepositoryTest {

    @Autowired
    private TransactionPostgresRepository transactionPostgresRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);

    private final Faker faker = new Faker();

    @Test
    public void whenSaveTransaction_thenItIsPersisted() {
        Transaction transaction = createTestTransaction();
        Transaction savedTransaction = transactionPostgresRepository.save(transaction);

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getCreatedAt()).isNotNull();


    }

    // A helper method to create a Transaction object
    private Transaction createTestTransaction() {
        return Transaction.builder()
                .amount(faker.number().randomNumber())
                .userId(faker.number().randomNumber())
                .paymentId(faker.internet().uuid())
                .walletId(faker.number().randomNumber())
                .id(snowflakeIdGenerator.nextId())
                .build();
    }
}