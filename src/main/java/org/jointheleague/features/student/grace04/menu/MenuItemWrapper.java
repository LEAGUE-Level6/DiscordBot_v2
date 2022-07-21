package org.jointheleague.features.student.grace04.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItemWrapper {

    @SerializedName("menuItems")
    @Expose
    private MenuItem[] menuItemItems;

    public MenuItem[] getMenuItems() {
        return menuItemItems;
    }
    public void setMenuItems(MenuItem[] menuItemItems) {
        this.menuItemItems = menuItemItems;
    }
}
