package fi.oulu.tol.sqat;

public class Inventory {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";

    private Item item;

    // Constructor
    public Inventory(Item item) {
        this.item = item;
    }

    public static Inventory initial(Item item) {
        return new Inventory(item);
    }

    public void doDailyUpdate() {
        updateQuality();
        updateExpiration();

        if (isExpired()) {
            handleExpired();
        }
    }

    /*
     * Update the expiration
     */
    public void updateExpiration() {
        if (isSulfuras()) {
            return;
        }
        item.setSellIn(item.getSellIn() - 1);
    }

    /*
     * Update the quality
     *
     * Refactor:
     * 	- Invert the if condition
     * 	- Isolate code parts and split into smaller pieces
     */
    protected void updateQuality() {
        if (isAgeBrie()) {
            increaseQuality();
        } else if (isBackStage()) {
            increaseQuality();

            if (item.getSellIn() < 11) {
                increaseQuality();
            }

            if (item.getSellIn() < 6) {
                increaseQuality();
            }
        } else if (isSulfuras()) {
            return;
        } else {
            decreaseQuality();
        }
    }

    protected void handleExpired() {
        if (isAgeBrie()) {
            increaseQuality();
        } else if (isBackStage()) {
            item.setQuality(0);
        } else if (isSulfuras()) {
            return;
        } else {
            decreaseQuality();
        }
    }

    protected boolean isExpired() {
        return item.getSellIn() < 0;
    }

    protected void increaseQuality() {
        if (item.getQuality() < 50) {
            item.setQuality(item.getQuality() + 1);
        }
    }

    protected void decreaseQuality() {
        if (item.getQuality() > 0) {
            item.setQuality(item.getQuality() - 1);
        }
    }

    protected boolean isAgeBrie() {
        return AGED_BRIE.equals(item.getName());
    }

    protected boolean isBackStage() {
        return BACKSTAGE.equals(item.getName());
    }

    protected boolean isSulfuras() {
        return SULFURAS.equals(item.getName());
    }
}
