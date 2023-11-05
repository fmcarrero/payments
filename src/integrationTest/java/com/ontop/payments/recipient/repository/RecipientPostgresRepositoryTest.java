package com.ontop.payments.recipient.repository;

import com.github.javafaker.Faker;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.infrastructure.repository.RecipientPostgresRepository;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(RecipientPostgresRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RecipientPostgresRepositoryTest {

    @Autowired
    private RecipientPostgresRepository recipientPostgresRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);

    private final Faker faker = new Faker();
    @Test
    void save() {
        var recipient = createTestRecipient();

        var response = recipientPostgresRepository.save(recipient);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(recipient.getId());
    }
    @Test
    void getRecipientByIdAndUser() {
        var recipient = createTestRecipient();
        recipientPostgresRepository.save(recipient);

        var response = recipientPostgresRepository.getRecipientByIdAndUser(recipient.getId(), recipient.getUserId());

        assertThat(response).isPresent();
        assertThat(response.get().getId()).isEqualTo(recipient.getId());
    }

    private Recipient createTestRecipient() {
       return Recipient.builder()
                    .id(snowflakeIdGenerator.nextId())
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .nationalIdentificationNumber(String.valueOf(faker.number().randomNumber()))
                    .bankName(faker.name().name())
                    .routingNumber(String.valueOf(faker.number().randomNumber()))
                    .accountNumber(faker.finance().bic())
                    .userId(faker.number().randomNumber())
                    .createdAt(LocalDateTime.now())
                    .currency(faker.currency().code())
                    .build();
    }
}
