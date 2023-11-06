package com.ontop.payments.transfer.application.send;

import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.domain.RecipientRepository;
import com.ontop.payments.shared.SnowflakeIdGenerator;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.domain.TransactionRepository;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.domain.Balance;
import com.ontop.payments.transfer.domain.PaymentProvider;
import com.ontop.payments.transfer.domain.Transfer;
import com.ontop.payments.transfer.domain.WalletTransaction;
import com.ontop.payments.transfer.domain.exception.InsufficientFundsException;
import com.ontop.payments.transfer.domain.exception.RecipientNotFoundException;
import com.ontop.payments.transfer.domain.repository.PaymentsRepository;
import com.ontop.payments.transfer.domain.repository.UserAccountRepository;
import com.ontop.payments.transfer.domain.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferMoney {
    private final WalletRepository walletRepository;
    private final RecipientRepository recipientRepository;
    private final PaymentsRepository paymentsRepository;

    private final TransactionRepository transactionRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator ;

    private final UserAccountRepository userAccountRepository;
    public TransferMoney(WalletRepository walletRepository, RecipientRepository recipientRepository, PaymentsRepository paymentsRepository, TransactionRepository transactionRepository, SnowflakeIdGenerator snowflakeIdGenerator, UserAccountRepository userAccountRepository){
        this.walletRepository = walletRepository;
        this.recipientRepository = recipientRepository;
        this.paymentsRepository = paymentsRepository;
        this.transactionRepository = transactionRepository;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.userAccountRepository = userAccountRepository;
    }

    public Transaction execute(TransferCommand transferCommand) {
        Transfer transfer = new Transfer(transferCommand);
        Balance balance = this.walletRepository.getBalanceByUser(transfer.getSource().getUserId());

        checkBalance(balance, transfer);

        Recipient recipient = this.recipientRepository.getRecipientByIdAndUser( transfer.getDestination().getId(), transfer.getSource().getUserId())
                .orElseThrow(() -> new RecipientNotFoundException("Recipient id " + transfer.getDestination().getId() + " not found"));
        transfer.setDestination(recipient);
        var source = this.userAccountRepository.getUserAccount(transfer.getSource().getUserId(),transferCommand.getSource().getSourceId());
        transfer.setSource(source);
        PaymentProvider response = this.paymentsRepository.PaymentProvider(transfer);

        WalletTransaction walletTransaction = this.walletRepository.saveTransaction(transfer);

        return this.transactionRepository.save(
                Transaction.builder()
                        .id(snowflakeIdGenerator.nextId())
                        .amount(transfer.getTotalWithdrawal())
                        .userId(transfer.getSource().getUserId())
                        .paymentId(response.getId())
                        .walletId(walletTransaction.getWalletTransactionId())
                        .createdAt(LocalDateTime.now())
                        .build());
    }

    private static void checkBalance(Balance balance, Transfer transfer) {
        if (balance.getBalance() < transfer.getTotalWithdrawal()){
            throw new InsufficientFundsException("Insufficient Funds Attempted to transfer " + transfer.getTotalWithdrawal() + " but only have " + balance.getBalance());
        }
    }
}
