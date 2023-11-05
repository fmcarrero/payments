package com.ontop.payments.recipient.application;

import com.ontop.payments.recipient.application.command.CreateRecipientCommand;
import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.domain.RecipientRepository;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class SaveRecipientsTest {

    private final SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);
    @Test
    void execute_error_firstName() {
        var service = new SaveRecipients(snowflakeIdGenerator, null);

        assertThatThrownBy(() -> {
            service.execute(1L, CreateRecipientCommand.builder().build());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("First name cannot be null or empty");
    }

    @Test
    void execute_error_invalidCurrency() {
        var service = new SaveRecipients(snowflakeIdGenerator, null);

        assertThatThrownBy(() -> {
            service.execute(1L, getCreateRecipientCommandErrorCurrency());
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid currency: garbage");
    }
    @Test
    void execute_success() {
        RecipientRepository recipientRepository = Mockito.mock(RecipientRepository.class);
        when(recipientRepository.save(any(Recipient.class))).thenReturn(createRecipient());
        var service = new SaveRecipients(snowflakeIdGenerator, recipientRepository);

        var response =service.execute(1L, getCreateRecipientCommand());

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getCreatedAt()).isNotNull();
    }

    private Recipient createRecipient(){
        return Recipient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .routingNumber("123456789")
                .nationalIdentificationNumber("123456789")
                .accountNumber("123456789")
                .bankName("Bank of America")
                .createdAt(LocalDateTime.now())
                .currency("USD")
                .userId(1L)
                .build();
    }
    private CreateRecipientCommand getCreateRecipientCommand() {
        return CreateRecipientCommand.builder()
                .accountNumber("123456789")
                .routingNumber("123456789")
                .firstName("John")
                .lastName("Doe")
                .bankName("Bank of America")
                .nationalIdentificationNumber("123456789")
                .currency("USD")
                .build();
    }

    private static CreateRecipientCommand getCreateRecipientCommandErrorCurrency() {
        return CreateRecipientCommand.builder()
                .accountNumber("123456789")
                .routingNumber("123456789")
                .firstName("John")
                .lastName("Doe")
                .bankName("Bank of America")
                .nationalIdentificationNumber("123456789")
                .currency("garbage")
                .build();
    }
}