package com.ontop.payments.recipient.domain;

import java.util.Optional;

public interface RecipientRepository {

    /**
     * Saves a recipient.
     *
     * @param recipient the recipient to save
     * @return the saved recipient
     */
    Recipient save(Recipient recipient);


    Optional<Recipient> getRecipientByIdAndUser(long recipientId, long userId);

}
