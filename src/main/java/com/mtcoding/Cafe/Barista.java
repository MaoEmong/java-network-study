package com.mtcoding.Cafe;

public class Barista implements IBarista {

    public Coffee createCoffee(String name)
    {
        Coffee coffee;
        switch (name)
        {
            case "Americano":
                coffee = new Coffee("Americano");
            return coffee;
            case "Espresso":
                coffee = new Coffee("Espresso");
                return coffee;
            case "Cappuccino":
                coffee = new Coffee("Cappuccino");
                return coffee;
            case "CaramelMacchiato":
                coffee = new Coffee("CaramelMacchiato");
                return coffee;
        }
        return null;
    }
}
