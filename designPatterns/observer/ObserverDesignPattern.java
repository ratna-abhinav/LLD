package designPatterns.observer;

import java.util.LinkedHashMap;
import java.util.Map;

interface Observer {
    void update(Order order);
}

class Customer implements Observer {

    private int id;

    public Customer(int id) {
        this.id = id;
    }

    @Override
    public void update(Order order) {
        System.out.println("Hey Customer " + this.id + ", your order with order ID " + order.getId() + " is now "
                + order.getStatus());
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + "]";
    }

}

class Resturant implements Observer {

    private int id;
    private String resturantName;

    public Resturant(int id, String resturantName) {
        this.id = id;
        this.resturantName = resturantName;
    }

    @Override
    public void update(Order order) {
        System.out.println("Hey " + this.resturantName + " resturant, the order with order ID " + order.getId()
                + " is now " + order.getStatus());
    }

    @Override
    public String toString() {
        return "Resturant [id=" + id + ", resturantName=" + resturantName + "]";
    }
}

class DeliveryPartner implements Observer {

    private String name;

    public DeliveryPartner(String name) {
        this.name = name;
    }

    @Override
    public void update(Order order) {
        System.out.println(
                "Hey " + this.name + " DP, an order with order ID " + order.getId() + " is now " + order.getStatus());
    }

    @Override
    public String toString() {
        return "DeliveryPartner [name=" + name + "]";
    }
}

class Order {
    private int id;
    private String status;
    Map<Observer, Integer> listOfObservers = new LinkedHashMap<>();

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void getObservers() {
        for (Map.Entry<Observer, Integer> entry : listOfObservers.entrySet()) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();
    }

    public void setStatus(String status) {
        this.status = status;
        this.notifyObservers();
    }

    public void attach(Observer observer) {
        listOfObservers.putIfAbsent(observer, 1);
    }

    public void detach(Observer observer) {
        listOfObservers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : listOfObservers.keySet()) {
            observer.update(this);
        }
    }
}

public class ObserverDesignPattern {
    public static void main(String[] args) {

        Order order = new Order(900123);
        Customer customer = new Customer(1);
        Resturant resturant = new Resturant(1001, "Bansiwala");
        DeliveryPartner deliveryPartner = new DeliveryPartner("David");

        order.attach(customer);
        order.attach(resturant);
        order.attach(deliveryPartner);

        order.setStatus("Order Placed");

        order.detach(deliveryPartner);

        System.out.println("--------------------------------------------------");

        order.setStatus("Order Delivered");
    }
}
