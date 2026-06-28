package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Einnahmen / Ausgaben (IMMER 2 Werte!)
    @Query("""
    SELECT 
        COALESCE(SUM(CASE WHEN t.type = 'purchase' THEN t.amount END), 0) AS income,
        COALESCE(SUM(CASE WHEN t.type = 'payout' THEN t.amount END), 0) AS expenses
    FROM Transaction t
    WHERE t.createdAt >= :from
""")
    Object[] getIncomeExpenses(@Param("from") LocalDateTime from);


    // Verkäufe nach Preis
    @Query("""
        SELECT t.price, COUNT(t)
        FROM Transaction t
        WHERE t.type = 'purchase'
          AND t.createdAt >= :from
        GROUP BY t.price
        ORDER BY t.price ASC
    """)
    List<Object[]> getSalesByPrice(@Param("from") LocalDateTime from);

    // Verkäufe nach Country
    @Query("""
        SELECT t.country, COUNT(t)
        FROM Transaction t
        WHERE t.type = 'purchase'
          AND t.createdAt >= :from
        GROUP BY t.country
        ORDER BY COUNT(t) DESC
    """)
    List<Object[]> getSalesByCountry(@Param("from") LocalDateTime from);

    // Verkäufe nach City
    @Query("""
        SELECT t.cityname, COUNT(t)
        FROM Transaction t
        WHERE t.type = 'purchase'
          AND t.createdAt >= :from
        GROUP BY t.cityname
        ORDER BY COUNT(t) DESC
    """)
    List<Object[]> getSalesByCity(@Param("from") LocalDateTime from);

    // Alle Transaktionen seit Zeitpunkt X
    @Query("SELECT t FROM Transaction t WHERE t.createdAt >= :start")
    List<Transaction> findAllSince(@Param("start") LocalDateTime start);

    List<Transaction> findByUserId(Long userId);
}
