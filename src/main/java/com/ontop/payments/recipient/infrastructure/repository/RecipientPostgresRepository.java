package com.ontop.payments.recipient.infrastructure.repository;

import com.ontop.payments.recipient.domain.Recipient;
import com.ontop.payments.recipient.domain.RecipientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipientPostgresRepository implements RecipientRepository {
    private final RecipientJpaRepository repository;

    public RecipientPostgresRepository(RecipientJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipient save(Recipient recipient) {
        RecipientJpaEntity entity = toJpaEntity(recipient);
        RecipientJpaEntity savedEntity = repository.save(entity);
        recipient.setCreatedAt(savedEntity.getCreatedAt());
        return recipient;
    }

    @Override
    public Optional<Recipient> getRecipientByIdAndUser(long recipientId, long userId) {
        Optional<RecipientJpaEntity> recipient = repository.findByIdAndUserId(recipientId, userId);
        return toDomainEntity(recipient);
    }

    private Optional<Recipient> toDomainEntity(Optional<RecipientJpaEntity> recipientJpaEntity) {
        return recipientJpaEntity.map(jpaEntity -> Recipient.builder()
                .id(jpaEntity.getId())
                .firstName(jpaEntity.getFirstName())
                .lastName(jpaEntity.getLastName())
                .nationalIdentificationNumber(jpaEntity.getNationalIdentificationNumber())
                .bankName(jpaEntity.getBankName())
                .routingNumber(jpaEntity.getRoutingNumber())
                .accountNumber(jpaEntity.getAccountNumber())
                .userId(jpaEntity.getUserId())
                .createdAt(jpaEntity.getCreatedAt())
                .currency(jpaEntity.getCurrency())
                .build());
    }

    private RecipientJpaEntity toJpaEntity(Recipient recipient) {
       return RecipientJpaEntity.builder()
               .id(recipient.getId())
               .firstName(recipient.getFirstName())
               .lastName(recipient.getLastName())
               .nationalIdentificationNumber(recipient.getNationalIdentificationNumber())
               .bankName(recipient.getBankName())
               .routingNumber(recipient.getRoutingNumber())
               .accountNumber(recipient.getAccountNumber())
               .userId(recipient.getUserId())
               .currency(recipient.getCurrency())
               .build();
    }

}
