package com.ontop.payments.transfer.application.send;

import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.domain.RecipientRepository;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.domain.TransactionRepository;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.application.command.TransferDestinationCommand;
import com.ontop.payments.transfer.application.command.TransferSourceCommand;
import com.ontop.payments.transfer.domain.Balance;
import com.ontop.payments.transfer.domain.PaymentProvider;
import com.ontop.payments.transfer.domain.UserAccount;
import com.ontop.payments.transfer.domain.WalletTransaction;
import com.ontop.payments.transfer.domain.exception.InsufficientFundsException;
import com.ontop.payments.transfer.domain.repository.PaymentsRepository;
import com.ontop.payments.transfer.domain.repository.UserAccountRepository;
import com.ontop.payments.transfer.domain.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransferMoneyTest {

    private final SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1);
    @Mock
    WalletRepository walletRepository;
    @Mock
    RecipientRepository recipientRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    UserAccountRepository userAccountRepository;
    @Mock
    PaymentsRepository paymentsRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute() {
        TransferCommand transferCommand = createTransferCommand();
        Transaction transaction = createTransaction(transferCommand);
        PaymentProvider paymentProvider = createPaymentProvider();
        WalletTransaction walletTransaction = createWalletTransaction(transferCommand);

        var transferMoney = new TransferMoney(walletRepository, recipientRepository, paymentsRepository,
                transactionRepository, snowflakeIdGenerator, userAccountRepository);
        when(walletRepository.getBalanceByUser(transferCommand.getSource().getUserId())).thenReturn(createBalance(transferCommand));
        when(recipientRepository.getRecipientByIdAndUser(transferCommand.getDestination().getRecipientId(), transferCommand.getSource().getUserId())).thenReturn(createRecipient());
        when(userAccountRepository.getUserAccount(transferCommand.getSource().getUserId(), transferCommand.getSource().getSourceId())).thenReturn(createUserAccount(transferCommand));
        when(paymentsRepository.PaymentProvider(any())).thenReturn(paymentProvider);
        when(walletRepository.saveTransaction(any())).thenReturn(walletTransaction);
        when(transactionRepository.save(any())).thenReturn(transaction);

        Transaction response = transferMoney.execute(transferCommand);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(transaction.getId());
    }

    @Test
    void execute_error_insufficient_balance() {
        TransferCommand transferCommand = createTransferCommand();
        when(walletRepository.getBalanceByUser(transferCommand.getSource().getUserId())).thenReturn(createBalanceInsufficientFunds(transferCommand));

        var transferMoney = new TransferMoney(walletRepository, recipientRepository, paymentsRepository,
                transactionRepository, snowflakeIdGenerator, userAccountRepository);

        assertThatThrownBy(() -> transferMoney.execute(transferCommand)).isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient Funds Attempted to transfer");
    }

    private WalletTransaction createWalletTransaction(TransferCommand transferCommand) {
        return new WalletTransaction(23424, transferCommand.getAmount() * 2, transferCommand.getSource().getUserId());
    }

    private PaymentProvider createPaymentProvider() {
        return PaymentProvider.builder()
                .id("123456789")
                .build();
    }

    private Transaction createTransaction(TransferCommand transferCommand) {
        return Transaction.builder()
                .id(1L)
                .amount(1000)
                .userId(transferCommand.getSource().getUserId())
                .paymentId("123456789")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private UserAccount createUserAccount(TransferCommand transferCommand) {
        return UserAccount.builder()
                .accountId(1L)
                .accountNumber("123456789")
                .currency("USD")
                .type("checking")
                .userId(transferCommand.getSource().getUserId())
                .routingNumber("123456789")
                .build();
    }

    private Optional<Recipient> createRecipient() {
        return Optional.of(Recipient.builder()
                .id(this.snowflakeIdGenerator.nextId())
                .firstName("John")
                .lastName("Doe")
                .routingNumber("123456789")
                .nationalIdentificationNumber("123456789")
                .accountNumber("123456789")
                .createdAt(LocalDateTime.now())
                .bankName("Bank of America")
                .currency("USD")
                .build());
    }

    private Balance createBalance(TransferCommand transferCommand) {
        return new Balance(transferCommand.getAmount() * 2, transferCommand.getSource().getUserId());
    }

    private Balance createBalanceInsufficientFunds(TransferCommand transferCommand) {
        return new Balance(transferCommand.getAmount() - 1, transferCommand.getSource().getUserId());
    }

    private TransferCommand createTransferCommand() {
        return TransferCommand.builder()
                .amount(1000)
                .destination(TransferDestinationCommand.builder()
                        .recipientId(1L)
                        .build())
                .source(TransferSourceCommand.builder()
                        .sourceId(1L)
                        .userId(1L)
                        .build())
                .build();
    }
}