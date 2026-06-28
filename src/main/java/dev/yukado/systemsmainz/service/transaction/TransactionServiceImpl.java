package dev.yukado.systemsmainz.service.transaction;

import dev.yukado.systemsmainz.entity.Transaction;
import dev.yukado.systemsmainz.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Object[] getIncomeExpenses(LocalDateTime from) {
        return transactionRepository.getIncomeExpenses(from);
    }

    @Override
    public List<Object[]> getSalesByPrice(LocalDateTime from) {
        return transactionRepository.getSalesByPrice(from);
    }

    @Override
    public List<Object[]> getSalesByCountry(LocalDateTime from) {
        return transactionRepository.getSalesByCountry(from);
    }

    @Override
    public List<Object[]> getSalesByCity(LocalDateTime from) {
        return transactionRepository.getSalesByCity(from);
    }

    @Override
    public List<Transaction> findAllSince(LocalDateTime start) {
        return transactionRepository.findAllSince(start);
    }

    @Override
    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    @Override
    public void addTransaction(
            Long userId,
            String type,
            Double amount,
            Double price,
            String country,
            String city,
            String currency
    ) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setType(type);
        t.setAmount(amount);
        t.setPrice(price);
        t.setCountry(country);
        t.setCityname(city);
        t.setCurrency(currency);

        // paymentId ist ein Long → wir generieren eine zufällige ID
        t.setPaymentId(System.currentTimeMillis());

        // processedTxId bleibt erstmal null
        t.setProcessedTxId(null);

        t.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(t);
    }

    @Override
    public void addWithdrawal(Long userId, double amount) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setType("withdrawal");
        t.setAmount(amount);
        t.setPrice(amount);
        t.setCountry("DE");
        t.setCityname("Online");
        t.setCurrency("EUR");

        transactionRepository.save(t);
    }
}

