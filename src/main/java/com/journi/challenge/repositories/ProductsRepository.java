package com.journi.challenge.repositories;

import com.journi.challenge.models.Product;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Named
@Singleton
public class ProductsRepository {
    private List<Product> allProducts = new ArrayList<>();

    {
        allProducts.add(new Product("photobook-square-soft-cover", "Photobook Square with Soft Cover", 25.0, "EUR"));
        allProducts.add(new Product("photobook-square-hard-cover", "Photobook Square with Hard Cover", 30.0, "EUR"));
        allProducts.add(new Product("photobook-landscape-soft-cover", "Photobook Landscape with Soft Cover", 35.0, "EUR"));
        allProducts.add(new Product("photobook-landscape-hard-cover", "Photobook Landscape with Hard Cover", 45.0, "EUR"));
    }

    public List<Product> list(String countryCode) {
        List<Product> products = new ArrayList<>();
        for (Product product : allProducts) {
            product = convert(countryCode, product);
            products.add(product);
        }
        return products;
    }

    private Product convert(String countryCode, Product product) {
        String currencyCode = "EUR";
        if (countryCode.equals("USA")) {
            product.setPrice(product.getPrice() * 0.91);
            product.setCurrencyCode("USD");
        } else if (countryCode.equals("UK")) {
            product.setPrice(product.getPrice() * 0.76);
            product.setCurrencyCode("GBP");
        } else {
            product.setCurrencyCode(currencyCode);
        }
        return product;
    }
}
