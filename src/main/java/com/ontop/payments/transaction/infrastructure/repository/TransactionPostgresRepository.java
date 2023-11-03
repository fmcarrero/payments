package com.ontop.payments.transaction.infrastructure.repository;

import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.domain.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionPostgresRepository implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionPostgresRepository(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionJpaEntity entity = toJpaEntity(transaction);
        TransactionJpaEntity savedEntity = transactionJpaRepository.save(entity);
        transaction.setCreatedAt(savedEntity.getCreatedAt());
        return transaction;
    }

    @Override
    public List<Transaction> FindAll(Long cursorId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);

        if (cursorId != null) {
            return toDomain(transactionJpaRepository.findByOrderByIdDesc(pageRequest));
        } else {
            return toDomain(transactionJpaRepository.findByOrderByIdDesc(pageRequest));
        }

    }

    private  List<Transaction> toDomain(List<TransactionJpaEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }
    private Transaction toDomain(TransactionJpaEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .userId(entity.getUserId())
                .paymentId(entity.getPaymentId())
                .walletId(entity.getWalletId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private TransactionJpaEntity toJpaEntity(Transaction transaction) {
        return TransactionJpaEntity.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .userId(transaction.getUserId())
                .paymentId(transaction.getPaymentId())
                .walletId(transaction.getWalletId())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
