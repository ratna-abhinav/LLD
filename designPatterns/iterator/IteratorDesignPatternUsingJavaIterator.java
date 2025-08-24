package designPatterns.iterator;

import java.util.List;

class MenuItem {
  private String name;
  private String description;
  private boolean vegetarian;
  private double price;

  public MenuItem(String name, String description, boolean vegetarian, double price) {
    this.name = name;
    this.description = description;
    this.vegetarian = vegetarian;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isVegetarian() {
    return vegetarian;
  }

  public double getPrice() {
    return price;
  }
}

interface Menu {
  java.util.Iterator<MenuItem> createIterator();
}

class DinerHouse implements Menu {
  private final List<MenuItem> menuItems;

  public DinerHouse() {
    menuItems = new java.util.ArrayList<>();
    addItem("Vegetarian BLT", "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99);
    addItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99);
    addItem("Soup of the day", "Soup of the day, with a side of potato salad", false, 3.29);
    addItem("Hotdog", "A hot dog, with saurkraut, relish, onions, topped with cheese", false, 3.05);
    addItem("Steamed Veggies and Brown Rice", "Steamed vegetables over brown rice", true, 3.99);
    addItem("Pasta", "Spaghetti with Marinara Sauce, and a slice of sourdough bread", true, 3.89);
  }

  public void addItem(String name, String description, boolean vegetarian, double price) {
    MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
    menuItems.add(menuItem);
  }

  @Override
  public java.util.Iterator<MenuItem> createIterator() {
    return menuItems.iterator();
  }
}

class PancakeHouse implements Menu {
  private MenuItem[] menuItems;
  private int numberOfItems = 0;
  static final int MAX_ITEMS = 6;

  public PancakeHouse() {
    menuItems = new MenuItem[MAX_ITEMS];
    addItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and toast", true, 2.99);
    addItem("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", false, 2.99);
    addItem("Blueberry Pancakes", "Pancakes made with fresh blueberries", true, 3.49);
    addItem("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59);
  }

  public void addItem(String name, String description, boolean vegetarian, double price) {
    MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
    if (numberOfItems >= MAX_ITEMS) {
      System.err.println("Sorry, menu is full! Can't add item to menu");
    } else {
      menuItems[numberOfItems] = menuItem;
      numberOfItems++;
    }
  }

  @Override
  public java.util.Iterator<MenuItem> createIterator() {
    return new PancakeHouseIterator(menuItems);
  }
}

class PancakeHouseIterator implements java.util.Iterator<MenuItem> {
  private final MenuItem[] items;
  private int index = 0;

  public PancakeHouseIterator(MenuItem[] items) {
    this.items = items;
  }

  @Override
  public boolean hasNext() {
    return index < items.length && items[index] != null;
  }

  @Override
  public MenuItem next() {
    return items[index++];
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove operation is not supported.");
  }
}

class Waitress {
  private final Menu dinerHouse;
  private final Menu pancakeHouse;

  public Waitress(Menu dinerHouse, Menu pancakeHouse) {
    this.dinerHouse = dinerHouse;
    this.pancakeHouse = pancakeHouse;
  }

  public void printMenu() {
    java.util.Iterator<MenuItem> dinerIterator = dinerHouse.createIterator();
    java.util.Iterator<MenuItem> pancakeIterator = pancakeHouse.createIterator();

    System.out.println("BREAKFAST Menu\n");
    printMenu(pancakeIterator);
    System.out.println("\nLUNCH Menu\n");
    printMenu(dinerIterator);
  }

  private void printMenu(java.util.Iterator<MenuItem> iterator) {
    while (iterator.hasNext()) {
      MenuItem menuItem = iterator.next();
      System.out.print(menuItem.getName() + ", ");
      System.out.print(menuItem.getPrice() + " -- ");
      System.out.println(menuItem.getDescription());
    }
  }
}

public class IteratorDesignPatternUsingJavaIterator {
  public static void main(String[] args) {
    Waitress waitress = new Waitress(new DinerHouse(), new PancakeHouse());
    waitress.printMenu();
  }
}
