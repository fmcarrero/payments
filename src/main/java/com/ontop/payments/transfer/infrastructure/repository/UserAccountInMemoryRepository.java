package com.ontop.payments.transfer.infrastructure.repository;

import com.ontop.payments.transfer.domain.UserAccount;
import com.ontop.payments.transfer.domain.repository.UserAccountRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountInMemoryRepository implements UserAccountRepository {
    @Override
    public UserAccount getUserAccount(long userId, long accountId) {
        return UserAccount.builder()
                        .userId(userId)
                        .accountId(accountId)
                        .currency("USD")
                        .accountNumber("0245253419")
                        .routingNumber("028444018")
                        .type("COMPANY")
                        .build();
    }
}
