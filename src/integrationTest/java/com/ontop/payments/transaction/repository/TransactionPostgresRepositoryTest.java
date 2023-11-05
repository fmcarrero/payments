package com.ontop.payments.transaction.repository;

import com.github.javafaker.Faker;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.command.TransactionSearchCriteriaCommand;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.infrastructure.repository.TransactionPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("test")
@Import(TransactionPostgresRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @BeforeEach
    public void setUp() {
        Transaction transaction1 = createTestTransaction();
        Transaction transaction2 = createTestTransaction();
        transactionPostgresRepository.save(transaction1);
        transactionPostgresRepository.save(transaction2);
    }
    @Test
    public void whenFindAll_thenTransactionsAreReturned() {
        PageResponse<List<Transaction>> transactions = transactionPostgresRepository.findAll(new TransactionSearchCriteriaCommand());

        // Assert that the transactions are returned
        assertThat(transactions.content()).isNotEmpty();
        assertThat(transactions.content()).hasSize(2);
    }
    @Test
    public void whenFinAllWithCreatedAt_thenTransactionsAreNotReturned() {
        TransactionSearchCriteriaCommand searchCriteria = new TransactionSearchCriteriaCommand();
        searchCriteria.setCreatedAtStart(LocalDateTime.now().minusDays(2));
        searchCriteria.setCreatedAtEnd(LocalDateTime.now().minusDays(1));

        PageResponse<List<Transaction>> transactions = transactionPostgresRepository.findAll(searchCriteria);

        // Assert that the transactions are returned
        assertThat(transactions.content()).isEmpty();
        assertThat(transactions.total()).isEqualTo(0);
    }

    // A helper method to create a Transaction object
    private Transaction createTestTransaction() {
        return Transaction.builder()
                .amount(faker.number().randomNumber())
                .userId(faker.number().randomNumber())
                .paymentId(faker.internet().uuid())
                .walletId(faker.number().randomNumber())
                .id(snowflakeIdGenerator.nextId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}