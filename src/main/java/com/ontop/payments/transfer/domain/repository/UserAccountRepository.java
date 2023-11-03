package com.ontop.payments.transfer.domain.repository;

import com.ontop.payments.transfer.domain.UserAccount;

public interface UserAccountRepository {
    UserAccount getUserAccount(long userId, long accountId);
}
