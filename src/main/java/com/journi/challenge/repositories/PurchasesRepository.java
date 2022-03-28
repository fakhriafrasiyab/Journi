package com.journi.challenge.repositories;

import com.journi.challenge.models.Purchase;
import com.journi.challenge.models.PurchaseStats;

import javax.inject.Named;
import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Named
@Singleton
public class PurchasesRepository {

    private final List<Purchase> allPurchases = new ArrayList<>();

    public List<Purchase> list() {
        return allPurchases;
    }

    public void save(Purchase purchase) {
        allPurchases.add(purchase);
    }

    public PurchaseStats getLast30DaysStats() {
        List<Purchase> purchases = addElements();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE.withZone(ZoneId.of("UTC"));

        LocalDateTime start = LocalDate.now().atStartOfDay().minusDays(30);

        List<Purchase> recentPurchases = purchases
                .stream()
                .filter(p -> p.getTimestamp().isAfter(start))
                .sorted(Comparator.comparing(Purchase::getTimestamp))
                .collect(Collectors.toList());

        long countPurchases = recentPurchases.size();
        double totalAmountPurchases = recentPurchases.stream().mapToDouble(Purchase::getTotalValue).sum();
        return new PurchaseStats(
                formatter.format(recentPurchases.get(0).getTimestamp()),
                formatter.format(recentPurchases.get(recentPurchases.size() - 1).getTimestamp()),
                countPurchases,
                totalAmountPurchases,
                totalAmountPurchases / countPurchases,
                recentPurchases.stream().mapToDouble(Purchase::getTotalValue).min().orElse(0.0),
                recentPurchases.stream().mapToDouble(Purchase::getTotalValue).min().orElse(0.0)
        );
    }

    private List<Purchase> addElements() {
        List<String> productIds = new ArrayList<>();
        productIds.add("1");
        productIds.add("2");
        productIds.add("3");
        Purchase purchase = new Purchase("1",LocalDateTime.of(2022,03,8,12,11), productIds, "faxri", 100.00, "EUR");
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        return purchases;
    }
}
