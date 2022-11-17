package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;
import fi.oulu.tol.sqat.MainApp;

public class GildedRoseTest {
	@Test
	public void testTheTruth() {
		assertTrue(true);
	}

	@Test
	public void testMainApp() {
		MainApp.main(new String[0]);
	}
	@Test
	public void exampleTest() {
		// create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();

		// access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();

		// assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}

	@Test
	public void test_normal_item() {
		Item normalItem = new Item("A normal item", 20, 40);

		// create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(normalItem);
		inn.oneDay();

		// access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();

		// assert quality has decreased by one
		assertEquals("A normal item has decreased quality by one = 39", 39, items.get(0).getQuality());

		passingDateWith(inn, normalItem.getSellIn() + 1);

		// sell by date passed
		assertEquals(normalItem.getSellIn(), -1);

		// Quality decreases twice as fast now
		assertEquals(normalItem.getQuality(), 18);
		passingDateWith(inn, 10);
		// Quality is never negative
		assertEquals(normalItem.getQuality(), 0);
		assertEquals(normalItem.getSellIn(), -11);
	}
	
	@Test
	public void test_multiple_normal_item() {
		Item normalItem1 = new Item("First normal item", 5, 4);
		Item normalItem2 = new Item("Second normal item", 3, 2);

		List<Item> items = new ArrayList<Item>();
		items.add(normalItem1);
		items.add(normalItem2);

		// create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose(items);
		inn.oneDay();

		// access a list of items, get the quality of the one set
		List<Item> normalItemAfterOneDay = inn.getItems();

		// First item
		assertEquals(normalItemAfterOneDay.get(0).getQuality(),3);
		assertEquals(normalItemAfterOneDay.get(0).getSellIn(), 4);

		// Second item
		assertEquals(normalItemAfterOneDay.get(1).getQuality(),1);
		assertEquals(normalItemAfterOneDay.get(1).getSellIn(), 2);
	}

	@Test
	public void test_aged_brie() {
		Item item = new Item("Aged Brie", 20, 20);

		// create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(item);
		inn.oneDay();

		// access a list of items, get the quality of the one set
		List<Item> itemsAfterOneDay = inn.getItems();
		Item agedBrieAfterOneDay = itemsAfterOneDay.get(0);

		// Aged Brie increases quality by 1 after 1 day
		assertEquals(agedBrieAfterOneDay.getName(), "Aged Brie");
		assertEquals(agedBrieAfterOneDay.getQuality(), 21);
		assertEquals(agedBrieAfterOneDay.getSellIn(), 19);

		passingDateWith(inn, item.getSellIn() + 1);

		// sell by date passed
		assertEquals(item.getSellIn(), -1);

		// Aged Brie has quality increases
		assertEquals(item.getQuality(), 42);

		passingDateWith(inn, 10);
		// Aged Brie has quality never > 50
		assertEquals(item.getQuality(), 50);
		assertEquals(item.getSellIn(), -11);
	}

	@Test
	public void test_sulfuras() {
		String sulfurasName = "Sulfuras, Hand of Ragnaros";
		Item item = new Item(sulfurasName, 20, 80);

		// create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(item);
		inn.oneDay();

		// access a list of items, get the quality of the one set
		List<Item> itemsAfterOneDay = inn.getItems();
		Item sulfurasAfterOneDay = itemsAfterOneDay.get(0);

		// Sulfuras increases quality by 1 after 1 day
		assertEquals(sulfurasAfterOneDay.getName(), sulfurasName);
		// Legendary Sulfuras has quality unchanged
		assertNotEquals(sulfurasAfterOneDay.getQuality(), 79);
		assertEquals(sulfurasAfterOneDay.getQuality(), 80);
		// Legendary Sulfuras has no alters
		assertNotEquals(sulfurasAfterOneDay.getSellIn(), 19);
		assertEquals(sulfurasAfterOneDay.getSellIn(), 20);

		passingDateWith(inn, item.getSellIn() + 1);

		// sell by date passed
		assertNotEquals(item.getSellIn(), -1);
		assertEquals(item.getSellIn(), 20);
		// quality unchanged
		assertEquals(item.getQuality(), 80);
	}

	@Test
	public void test_backstage() {
		String backStageItemName = "Backstage passes to a TAFKAL80ETC concert";

		Item item = new Item(backStageItemName, 20, 20);

		// create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(item);
		inn.oneDay();

		// access a list of items, get the quality of the one set
		List<Item> itemsAfterOneDay = inn.getItems();
		Item backStageAfterOneDay = itemsAfterOneDay.get(0);

	
		assertEquals(backStageAfterOneDay.getName(), backStageItemName);
		// Backstage increases quality by 1 after 1 day
		assertNotEquals(backStageAfterOneDay.getQuality(), 20);
		assertEquals(backStageAfterOneDay.getQuality(), 21);
		assertEquals(backStageAfterOneDay.getSellIn(), 19);
		
		passingDateWith(inn, 9);
		
		// Quality increases by 2 when sellIn value approaches 10 days or less
		assertEquals(item.getQuality(), 30);
		assertEquals(item.getSellIn(), 10);
		passingDateWith(inn, 1);
		assertEquals(item.getQuality(), 32);
		assertEquals(item.getSellIn(), 9);
		
		// Quality increases by 3 when sellIn value approaches 5 days or less
		passingDateWith(inn, 4);
		assertEquals(item.getQuality(), 40);
		assertEquals(item.getSellIn(), 5);
		passingDateWith(inn, 1);
		assertEquals(item.getQuality(), 43);
		assertEquals(item.getSellIn(), 4);
		passingDateWith(inn, 2);
		assertEquals(item.getQuality(), 49);
		assertEquals(item.getSellIn(), 2);
		
		// Quality never > 50
		passingDateWith(inn, 1);
		assertEquals(item.getQuality(), 50);
		assertEquals(item.getSellIn(), 1);
		
		// Quality = 0 when sellIn passed
		passingDateWith(inn, 2);
		assertEquals(item.getQuality(), 0);
		assertEquals(item.getSellIn(), -1);
	}
/*
	@Test
	public void test() {
		Random random = new Random(10);
		int min = -1;
		int max = 100;
		int numberOfRandomItems = 100;
		
		String[] itemNames = {"+5 Dexterity Vest", 
			      "Aged Brie",
			      "Elixir of the Mongoose",
			      "Sulfuras, Hand of Ragnaros",
			      "Backstage passes to a TAFKAL80ETC concert",
			      "Conjured Mana Cake"};
		
		GildedRose gildedRose = new GildedRose();

		
		for (int cnt = 0; cnt < numberOfRandomItems; cnt++) {
			String name = itemNames[0 + random.nextInt(itemNames.length)];
			int sellIn = min + random.nextInt(max);
			int quality = min + random.nextInt(max);
			gildedRose.setItem(new Item(name, sellIn, quality));
		}
		gildedRose.oneDay();
		
		StringBuilder builder = new StringBuilder();
		for (Item item : gildedRose.getItems()) {
			builder.append(item).append("\r");
		}
		 builder.toString();
	}
	*/
	private void passingDateWith(GildedRose g, int days) {
		int d = days;
		while (d > 0) {
			g.oneDay();
			d--;
		}
	}
}
