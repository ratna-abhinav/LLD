package designPatterns.composite;

import java.util.ArrayList;
import java.util.List;

// Component
abstract class Component {
    abstract void display();

    public void add(Component menuComponent) {
        throw new UnsupportedOperationException();
    }
    public void remove(Component menuComponent) {
        throw new UnsupportedOperationException();
    }
}

// MenuComposite class
// its a composite that contains other Components
class MenuComposite extends Component {
    private String name;
    private List<Component> menuComponents = new ArrayList<>();
    
    public MenuComposite(String name) {
        this.name = name;
    }
    
    public void add(Component menuComponent) {
        menuComponents.add(menuComponent);
    }

    public void remove(Component menuComponent) {
        menuComponents.remove(menuComponent);
    }

    @Override
    public void display() {
        System.out.println("Menu: " + name);
        for (Component component : menuComponents) {
            component.display();
        }
    }
}

// MenuItem class
class MenuItem extends Component {
    private String name;
    private double price;
    private String description;

    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public void display() {
        System.out.println("  Item: " + name + ", Price: $" + price + ", Description: " + description);
    }
}

// Client class
// it contains a reference to the top-level Component
class Waitress {
    private Component allMenus;

    public Waitress(Component allMenus) {
        this.allMenus = allMenus;
    }

    public void displayMenu() {
        allMenus.display();
    }
}


public class Main {
  public static void main(String[] args) {
    Component pancakeHouseMenu = new MenuComposite("**Pancake House Menu**");
    Component dinerMenu = new MenuComposite("**Diner Menu**");
    Component cafeMenu = new MenuComposite("**Cafe Menu**");
    Component dessertMenu = new MenuComposite("**Dessert Menu**");

    Component allMenus = new MenuComposite("All Menus");
    allMenus.add(pancakeHouseMenu);
    allMenus.add(dinerMenu);
    allMenus.add(cafeMenu);

    pancakeHouseMenu.add(new MenuItem("Pancakes", 5.99, "Delicious fluffy pancakes"));
    pancakeHouseMenu.add(new MenuItem("Waffles", 6.99, "Crispy golden waffles"));
    pancakeHouseMenu.add(new MenuItem("Omelette", 7.99, "Three-egg omelette with cheese"));

    dinerMenu.add(new MenuItem("Pasta", 8.99, "Spaghetti with marinara sauce"));
    dessertMenu.add(new MenuItem("Apple Pie", 3.99, "Homemade apple pie with ice cream"));    dessertMenu.add(new MenuItem("Ice Cream", 2.99, "Vanilla ice cream scoop"));
    dessertMenu.add(new MenuItem("Cake", 4.99, "Chocolate cake slice"));

    dinerMenu.add(dessertMenu);

    cafeMenu.add(new MenuItem("Coffee", 2.49, "Freshly brewed coffee"));
    cafeMenu.add(new MenuItem("Tea", 1.99, "Assorted herbal teas"));

    Waitress waitress = new Waitress(allMenus);
    waitress.displayMenu();
  }
}
