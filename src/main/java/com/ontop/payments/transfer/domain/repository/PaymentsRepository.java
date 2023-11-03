package com.ontop.payments.transfer.domain.repository;

import com.ontop.payments.transfer.domain.PaymentProvider;
import com.ontop.payments.transfer.domain.Transfer;

public interface PaymentsRepository {
    PaymentProvider PaymentProvider(Transfer transfer);
}
