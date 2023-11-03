package com.ontop.payments.transfer.domain;

import com.ontop.payments.transfer.domain.exception.InvalidUserException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferSource {
    private long userId;

  //  this.validateUserId(transferCommand.getSource().getUserId());
    private void validateUserId(Long userId) {
        if (userId == null) {
            throw new InvalidUserException();
        }
    }

}
