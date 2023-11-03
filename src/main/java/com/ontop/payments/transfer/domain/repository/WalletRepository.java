package com.ontop.payments.transfer.domain.repository;

import com.ontop.payments.transfer.domain.Balance;
import com.ontop.payments.transfer.domain.Transfer;
import com.ontop.payments.transfer.domain.WalletTransaction;

public interface WalletRepository {
    /**
    * Get balance by user id
    * @param userId User id
    * @return Balance
    */
    Balance getBalanceByUser(Long userId);

    /**
    * Save transaction
    * @param transfer Transfer
     */
    WalletTransaction saveTransaction(Transfer transfer);
}
