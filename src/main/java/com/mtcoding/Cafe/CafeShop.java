package com.mtcoding.Cafe;

public class CafeShop {
    public static void main(String[] args) {
        MenuOrder menuOrder = new MenuOrder(new MenuItem[]{
                new MenuItem("Americano",2000),
                new MenuItem("Espresso",3000),
                new MenuItem("Cappuccino",3500),
                new MenuItem("CaramelMacchiato",4000)});
        Customer customer = new Customer();
        Barista barista = new Barista();

        customer.orderCoffee("Americano",menuOrder,barista);
    }
}
