package dev.yukado.systemsmainz.service.stats;

import dev.yukado.systemsmainz.dto.TransactionStatsDTO;
import dev.yukado.systemsmainz.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminStatsService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionStatsDTO getStats(Duration duration) {

        LocalDateTime start = LocalDateTime.now().minus(duration);

        TransactionStatsDTO dto = new TransactionStatsDTO();

        // ---------------------------------------------------------
        // Einnahmen / Ausgaben (IM SERVICE BERECHNET)
        // ---------------------------------------------------------
        double income = 0.0;
        double expenses = 0.0;

        var tx = transactionRepository.findAllSince(start);

        for (var t : tx) {
            if ("purchase".equals(t.getType())) {
                income += t.getAmount();
            }
            if ("payout".equals(t.getType())) {
                expenses += t.getAmount(); // positiv lassen!
            }
        }

        dto.setIncome(income);
        dto.setExpenses(expenses);

        // ---------------------------------------------------------
        // Verkäufe nach Preis
        // ---------------------------------------------------------
        List<Object[]> priceRows = transactionRepository.getSalesByPrice(start);
        List<TransactionStatsDTO.PriceGroup> priceGroups = new ArrayList<>();

        for (Object[] row : priceRows) {
            priceGroups.add(
                    new TransactionStatsDTO.PriceGroup(
                            toDouble(row[0]),
                            toLong(row[1])
                    )
            );
        }
        dto.setByPrice(priceGroups);

        // ---------------------------------------------------------
        // Verkäufe nach Country
        // ---------------------------------------------------------
        List<Object[]> countryRows = transactionRepository.getSalesByCountry(start);
        List<TransactionStatsDTO.CountryGroup> countryGroups = new ArrayList<>();

        for (Object[] row : countryRows) {
            countryGroups.add(
                    new TransactionStatsDTO.CountryGroup(
                            (String) row[0],
                            toLong(row[1])
                    )
            );
        }
        dto.setByCountry(countryGroups);

        // ---------------------------------------------------------
        // Verkäufe nach City
        // ---------------------------------------------------------
        List<Object[]> cityRows = transactionRepository.getSalesByCity(start);
        List<TransactionStatsDTO.CityGroup> cityGroups = new ArrayList<>();

        for (Object[] row : cityRows) {
            cityGroups.add(
                    new TransactionStatsDTO.CityGroup(
                            (String) row[0],
                            toLong(row[1])
                    )
            );
        }
        dto.setByCity(cityGroups);

        return dto;
    }

    private double toDouble(Object o) {
        if (o == null) return 0.0;
        if (o instanceof Number n) return n.doubleValue();
        return 0.0;
    }

    private long toLong(Object o) {
        if (o == null) return 0L;
        if (o instanceof Number n) return n.longValue();
        return 0L;
    }
}
