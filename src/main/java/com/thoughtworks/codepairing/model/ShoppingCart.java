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
        double dis20TotalPrice = 0;  // 用于计算所有DIS_20商品的总价

        int loyaltyPointsEarned = 0;
        for (Product product : products) {
            double discount = 0;

            if (product.getProductCode().length() < 6) {
                loyaltyPointsEarned += (product.getPrice()/5);
            }
            else{
            switch (product.getProductCode().substring(0, 6)) {
                case "DIS_10": 
                    discount = (product.getPrice() * 0.1);
                    loyaltyPointsEarned += (product.getPrice() / 10);
                    break;
                case "DIS_15":
                    discount = (product.getPrice()* 0.15);
                    loyaltyPointsEarned += (product.getPrice() / 15);
                    break;
                case "DIS_20":
                    // 累加所有DIS_20商品的价格
                    dis20TotalPrice += product.getPrice();
                    loyaltyPointsEarned += (product.getPrice() / 20);
                    break;
                case "BUY2_1":
                    freeItemCount += 1;
                    freeItemPrice = product.getPrice();
                    break;
                default:
                    loyaltyPointsEarned += (product.getPrice() / 5);
                    break;
                }
            }   

            totalPrice += product.getPrice() - discount;
        }

        // 计算DIS_20商品的总折扣
        int fullHundreds = (int)(dis20TotalPrice / 100);
        double dis20Discount = fullHundreds * 20;
        totalPrice -= dis20Discount;

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

