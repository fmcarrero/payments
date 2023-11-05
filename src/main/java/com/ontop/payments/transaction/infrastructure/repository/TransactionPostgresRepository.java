package com.ontop.payments.transaction.infrastructure.repository;

import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.command.TransactionSearchCriteriaCommand;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.domain.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        return toDomain(savedEntity);
    }

    @Override
    public PageResponse<List<Transaction>> findAll(TransactionSearchCriteriaCommand searchCriteria) {
        PageRequest pageRequest = PageRequest.of(searchCriteria.getPage(), searchCriteria.getSize(), Sort.Direction.DESC, "id");

        Specification<TransactionJpaEntity> spec = Specification.where(null);

        if (searchCriteria.getAmount() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("amount"), searchCriteria.getAmount()));
        }

        if (searchCriteria.getCreatedAtStart() != null && searchCriteria.getCreatedAtEnd() != null) {
            spec = spec.and((root, query, cb) -> cb.between(root.get("createdAt"), searchCriteria.getCreatedAtStart(), searchCriteria.getCreatedAtEnd()));
        }
        var response = this.transactionJpaRepository.findAll(spec, pageRequest);

        return new PageResponse<>(this.toDomain(response.getContent()), response.getTotalElements());
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
