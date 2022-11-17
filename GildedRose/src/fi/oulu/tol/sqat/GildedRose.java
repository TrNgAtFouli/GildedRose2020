package fi.oulu.tol.sqat;

import java.util.ArrayList;
import java.util.List;

public class GildedRose {
    List<Item> items;

    // constructor
    public GildedRose() {
        items = new ArrayList<Item>();
    }

    public GildedRose(List<Item> items) {
        this.items = items;
    }

    /**
     * Categories of item: - Normal Items - Aged Bries - Sulfuras - Backstage
     */
    public void updateInventory() {
        for (Item item : items) {
            Inventory.initial(item).doDailyUpdate();
        }
    }


    // getter
    public List<Item> getItems() {
        return items;
    }

    // setter
    public void setItem(Item item) {
        items.add(item);
    }

    // update one day
    public void oneDay() {
        updateInventory();
    }
}
