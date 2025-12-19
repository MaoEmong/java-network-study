package com.mtcoding.Cafe;

public class Customer implements ICustomer{

    public Coffee orderCoffee(String name,MenuOrder menuOrder, IBarista barista)
    {
        if(menuOrder.chooseMenu(name)) {
        Coffee coffee = barista.createCoffee(name);
        return coffee;
        }
        System.out.println("메뉴판에 없는 메뉴입니다");
        return null;
    }
}
