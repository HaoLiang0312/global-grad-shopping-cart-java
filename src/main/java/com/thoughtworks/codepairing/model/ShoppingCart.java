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

        int loyaltyPointsEarned = 0;
        for (Product product : products) {
            double discount = 0;
            if (product.getProductCode().startsWith("DIS_10")) {
                discount = (product.getPrice() * 0.1);
                loyaltyPointsEarned += (product.getPrice() / 10);
            } else if (product.getProductCode().startsWith("DIS_15")) {
                discount = (product.getPrice() * 0.15);
                loyaltyPointsEarned += (product.getPrice() / 15);
            //第一题 20% discount实现
            }else if (product.getProductCode().startsWith("DIS_20")) {
                discount = (product.getPrice() * 0.2);
                loyaltyPointsEarned += (product.getPrice() / 20);
            //第二题满100减50实现
                
            }else if (product.getProductCode().startsWith("DISCOUNT_50_PER_100") ){
                continue;
                
            }else {
                loyaltyPointsEarned += (product.getPrice() / 5);
            }

            totalPrice += product.getPrice() - discount;
        }

        double any100Total = 0;

        for (Product product : products){
            
            if (product.getProductCode().startsWith("DISCOUNT_50_PER_100") ){
                any100Total += product.getPrice();
            }
        }
        if (any100Total >= 100){
            loyaltyPointsEarned = (int) (loyaltyPointsEarned + any100Total / 5);
            any100Total =any100Total- any100Total / 100 * 50;
        }
        else if (any100Total < 100){
            loyaltyPointsEarned = (int) (loyaltyPointsEarned + any100Total / 5);
        }
        totalPrice =  totalPrice + any100Total ;
       

        return new Order(totalPrice, loyaltyPointsEarned);
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName() + "\n" + "Bought:  \n" + products.stream().map(p -> "- " + p.getName()+ ", "+p.getPrice()).collect(Collectors.joining("\n"));
    }
}
