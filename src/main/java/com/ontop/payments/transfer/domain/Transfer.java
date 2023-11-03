package com.ontop.payments.transfer.domain;

import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.transfer.application.command.TransferCommand;
import com.ontop.payments.transfer.domain.exception.InvalidAmountException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transfer {
    public static final long FEE = 10;
    private long amount;
    private UserAccount  source;
    private Recipient destination;
    private SourceInformation sourceInformation;

    private TransferStatus status;

    private void validateAmount(long amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
    }

    public long getRate(){
        return this.amount / FEE;
    }

    public long getTotalWithdrawal(){
        return this.amount + this.getRate();
    }
    public Transfer(TransferCommand transferCommand){
        this.validateAmount(transferCommand.getAmount());
        this.amount = transferCommand.getAmount();
        this.source = UserAccount.builder()
                .userId(transferCommand.getSource().getUserId())
                .build();
        this.destination = new Recipient(transferCommand.getDestination().getRecipientId());
        this.status = TransferStatus.PENDING;
    }

    public boolean isCompleted() {
        return this.status == TransferStatus.COMPLETED;
    }

    @Data
    @Builder
    public static class SourceInformation {
        private String name;
    }
}
