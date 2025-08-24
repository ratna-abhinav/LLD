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

class DinerHouse {
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

  public java.util.List<MenuItem> getMenuItems() {
    return menuItems;
  }

  public Iterator createIterator() {
    return new DinerHouseIterator(menuItems);
  }
}

class PancakeHouse {
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

  public MenuItem[] getMenuItems() {
    return menuItems;
  }

  public Iterator createIterator() {
    return new PancakeHouseIterator(menuItems);
  }
}

interface Iterator {
  boolean hasNext();
  MenuItem next();
}

class DinerHouseIterator implements Iterator {
  private final List<MenuItem> items;
  private int index = 0;

  public DinerHouseIterator(List<MenuItem> items) {
    this.items = items;
  }

  @Override
  public boolean hasNext() {
    return index < items.size();
  }

  @Override
  public MenuItem next() {
    return items.get(index++);
  }
}

class PancakeHouseIterator implements Iterator {
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
}

class Waitress {
  private final DinerHouse dinerHouse;
  private final PancakeHouse pancakeHouse;

  public Waitress(DinerHouse dinerHouse, PancakeHouse pancakeHouse) {
    this.dinerHouse = dinerHouse;
    this.pancakeHouse = pancakeHouse;
  }

  public void printMenu() {
    Iterator dinerIterator = dinerHouse.createIterator();
    Iterator pancakeIterator = pancakeHouse.createIterator();

    System.out.println("MENU\n----\nBREAKFAST");
    printMenu(pancakeIterator);
    System.out.println("\nLUNCH");
    printMenu(dinerIterator);
  }

  private void printMenu(Iterator iterator) {
    while (iterator.hasNext()) {
      MenuItem menuItem = iterator.next();
      System.out.print(menuItem.getName() + ", ");
      System.out.print(menuItem.getPrice() + " -- ");
      System.out.println(menuItem.getDescription());
    }
  }
}

public class IteratorDesignPattern {
  public static void main(String[] args) {
    Waitress waitress = new Waitress(new DinerHouse(), new PancakeHouse());
    waitress.printMenu();
  }
}
