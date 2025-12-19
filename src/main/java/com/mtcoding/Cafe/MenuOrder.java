package com.mtcoding.Cafe;

public class MenuOrder {
    private MenuItem[] menuItem;

    public MenuOrder(MenuItem[] menuItem) {
        this.menuItem = menuItem;
    }

    public boolean chooseMenu(String name)
    {
        for(var n : menuItem)
            if(n.getName().equals(name))
                return true;

        return false;
    }
}
