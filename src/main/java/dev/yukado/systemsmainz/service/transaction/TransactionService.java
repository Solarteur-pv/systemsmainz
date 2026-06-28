package dev.yukado.systemsmainz.service.transaction;

import dev.yukado.systemsmainz.entity.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    Object[] getIncomeExpenses(LocalDateTime from);

    List<Object[]> getSalesByPrice(LocalDateTime from);

    List<Object[]> getSalesByCountry(LocalDateTime from);

    List<Object[]> getSalesByCity(LocalDateTime from);

    List<Transaction> findAllSince(LocalDateTime start);

    List<Transaction> findByUserId(Long userId);

    void addTransaction(
            Long userId,
            String type,
            Double amount,
            Double price,
            String country,
            String city,
            String currency
    );

    void addWithdrawal(Long userId, double amount);
}

