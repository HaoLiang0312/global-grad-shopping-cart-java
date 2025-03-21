package com.thoughtworks.codepairing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    public static final int PRICE = 100;
    public static final String PRODUCT = "Product";

    Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("test");
    }

    @Test
    public void shouldCalculatePriceWithNoDiscount() {
        List<Product> products = List.of(new Product(PRICE, "", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(100.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsWithNoDiscount() {
        List<Product> products = List.of(new Product(PRICE, "", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(20, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceFor10PercentDiscount() {
        List<Product> products = List.of(new Product(PRICE, "DIS_10_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(90.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsFor10PercentDiscount() {
        List<Product> products = List.of(new Product(PRICE, "DIS_10_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(10, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceFor15PercentDiscount() {
        List<Product> products = List.of(new Product(PRICE, "DIS_15_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(85.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsFor15PercentDiscount() {
        List<Product> products = List.of(new Product(PRICE, "DIS_15_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(6, order.getLoyaltyPoints());
    }

    //第一题 20%折扣测试

    @Test
    public void shouldCalculatePriceFor20PercentDiscount() {
        List<Product> products = List.of(new Product(PRICE, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(80.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsFor20PercentDiscount() {
        List<Product> products = List.of(new Product(PRICE, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(5, order.getLoyaltyPoints());
    }

    //第二题 满100减50
    @Test
    public void shouldCalculatePriceForAverage100Discount50() {
        List<Product> products = List.of(new Product(PRICE, "DISCOUNT_50_PER_100", PRODUCT),
        new Product(PRICE, "DISCOUNT_50_PER_100", PRODUCT),
        new Product(PRICE, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(180.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsForAverage100Discount50() {
        List<Product> products = List.of(new Product(PRICE, "DISCOUNT_50_PER_100", PRODUCT),
        new Product(PRICE, "DISCOUNT_50_PER_100", PRODUCT),
        new Product(PRICE, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(45, order.getLoyaltyPoints());
    }

    //第三题 失败测试
    @Test
    public void shouldCalculatePriceForAnyAverage100Discount50() {
        List<Product> products = List.of(new Product(80, "DISCOUNT_50_PER_100_fail", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(80.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsForAnyAverage100Discount50() {
        List<Product> products = List.of(new Product(80, "DISCOUNT_50_PER_100_fail", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(16, order.getLoyaltyPoints());
    }

    @Test
    public void shouldCalculatePriceForAnyAverage100Discount50Achieve() {
        List<Product> products = List.of(new Product(40, "DISCOUNT_50_PER_100_A", PRODUCT),
        new Product(60, "DISCOUNT_50_PER_100_ B", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(50.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateLoyaltyPointsForAnyAverage100Discount50Achieve() {
        List<Product> products = List.of(new Product(40, "DISCOUNT_50_PER_100_A", PRODUCT),
        new Product(60, "DISCOUNT_50_PER_100_ B", PRODUCT));
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(20, order.getLoyaltyPoints());
    }
}
