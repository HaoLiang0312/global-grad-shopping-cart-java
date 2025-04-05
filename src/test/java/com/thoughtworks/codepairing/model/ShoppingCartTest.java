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
    //第一题
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

    //第二题
    @Test
    public void shouldCalculatePriceForBuy2Get1Free() {
        List<Product> products = List.of(new Product(PRICE, "BUY2_1Free_ABCD", PRODUCT),
        new Product(PRICE, "BUY2_1Free_ABCD", PRODUCT),
        new Product(PRICE, "BUY2_1Free_ABCD", PRODUCT),
        new Product(PRICE, "DIS_20_ABCD", PRODUCT)
        );
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(280.0, order.getTotalPrice(), 0.0);
    }

    //第三题
    @Test
    public void shouldCalculateTotalPriceMoreThan500() {
        List<Product> products = List.of(
        new Product(PRICE, "", PRODUCT),
        new Product(PRICE, "", PRODUCT),
        new Product(PRICE, "", PRODUCT),
        new Product(PRICE, "", PRODUCT),
        new Product(PRICE, "", PRODUCT)
        );
        ShoppingCart cart = new ShoppingCart(customer, products);
        Order order = cart.checkout();

        assertEquals(475.0, order.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculatePriceFor20PercentDiscountPer100() {
        // 测试价格刚好100的情况
        List<Product> products1 = List.of(new Product(100, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart1 = new ShoppingCart(customer, products1);
        Order order1 = cart1.checkout();
        assertEquals(80.0, order1.getTotalPrice(), 0.0);

        // 测试价格超过100但不到200的情况
        List<Product> products2 = List.of(new Product(150, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart2 = new ShoppingCart(customer, products2);
        Order order2 = cart2.checkout();
        assertEquals(130.0, order2.getTotalPrice(), 0.0);

        // 测试价格刚好200的情况
        List<Product> products3 = List.of(new Product(200, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart3 = new ShoppingCart(customer, products3);
        Order order3 = cart3.checkout();
        assertEquals(160.0, order3.getTotalPrice(), 0.0);

        // 测试价格超过200但不到300的情况
        List<Product> products4 = List.of(new Product(250, "DIS_20_ABCD", PRODUCT));
        ShoppingCart cart4 = new ShoppingCart(customer, products4);
        Order order4 = cart4.checkout();
        assertEquals(210.0, order4.getTotalPrice(), 0.0);
    }

    @Test
    public void shouldCalculateDiscountForMultipleDIS20Products() {
        // 测试多个DIS_20商品总价刚好100的情况
        List<Product> products1 = List.of(
            new Product(60, "DIS_20_ABCD", PRODUCT),
            new Product(40, "DIS_20_ABCD", PRODUCT)
        );
        ShoppingCart cart1 = new ShoppingCart(customer, products1);
        Order order1 = cart1.checkout();
        assertEquals(80.0, order1.getTotalPrice(), 0.0);

        // 测试多个DIS_20商品总价超过100但不到200的情况
        List<Product> products2 = List.of(
            new Product(60, "DIS_20_ABCD", PRODUCT),
            new Product(60, "DIS_20_ABCD", PRODUCT)
        );
        ShoppingCart cart2 = new ShoppingCart(customer, products2);
        Order order2 = cart2.checkout();
        assertEquals(100.0, order2.getTotalPrice(), 0.0);

        // 测试多个DIS_20商品总价刚好200的情况
        List<Product> products3 = List.of(
            new Product(100, "DIS_20_ABCD", PRODUCT),
            new Product(100, "DIS_20_ABCD", PRODUCT)
        );
        ShoppingCart cart3 = new ShoppingCart(customer, products3);
        Order order3 = cart3.checkout();
        assertEquals(160.0, order3.getTotalPrice(), 0.0);

        // 测试混合商品的情况
        List<Product> products4 = List.of(
            new Product(60, "DIS_20_ABCD", PRODUCT),
            new Product(60, "DIS_20_ABCD", PRODUCT),
            new Product(100, "DIS_10_ABCD", PRODUCT)
        );
        ShoppingCart cart4 = new ShoppingCart(customer, products4);
        Order order4 = cart4.checkout();
        assertEquals(190.0, order4.getTotalPrice(), 0.0);
    }

}
