package com.thoughtworks.codepairing.model;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {
    private List<Product> products;
    private Customer customer;

    public ShoppingCart(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Order checkout() {
        double totalPrice = 0;
        double freeItemDiscount = 0;
        int freeItemCount = 0;
        double freeItemPrice = 0;

        int loyaltyPointsEarned = 0;
        for (Product product : products) {
            double discount = 0;

            if (product.getProductCode().startsWith("DIS_10")) {
                discount = (product.getPrice() * 0.1);
                loyaltyPointsEarned += (product.getPrice() / 10);
            } else if (product.getProductCode().startsWith("DIS_15")) {
                discount = (product.getPrice() * 0.15);
                loyaltyPointsEarned += (product.getPrice() / 15);
            } else if (product.getProductCode().startsWith("DIS_20")) {
                discount = (product.getPrice()* 0.2);
                loyaltyPointsEarned += (product.getPrice()/20);
            }else if (product.getProductCode().startsWith("BUY2_1Free")) {
                freeItemCount += 1;
                freeItemPrice = product.getPrice();
                loyaltyPointsEarned += (product.getPrice() / 5);
            }else {
                loyaltyPointsEarned += (product.getPrice() / 5);
            }

            totalPrice += product.getPrice() - discount;
        }

        freeItemDiscount = freeItemCount / 3 * freeItemPrice;
        totalPrice -= freeItemDiscount;
        
        if (totalPrice >= 500) {
            totalPrice = totalPrice * 0.95;
        }

        return new Order(totalPrice, loyaltyPointsEarned);
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName() + "\n" + "Bought:  \n" + products.stream().map(p -> "- " + p.getName()+ ", "+p.getPrice()).collect(Collectors.joining("\n"));
    }
}

