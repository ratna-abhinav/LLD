package designPatterns.factory;

enum ORDER_TYPE {
  DINE_IN,
  TAKEOUT,
  DELIVERY
}

abstract class Order {
  int orderId;
  int totalPrice;

  Order(int orderId, int totalPrice) {
    this.orderId = orderId;
    this.totalPrice = totalPrice;
  }
  
  abstract void message();
}

class DineIn extends Order {
  DineIn(int orderId, int totalPrice) {
    super(orderId, totalPrice);
  }

  @Override
  void message() {
    System.out.println("Thank you for placing the order, your order type = " + ORDER_TYPE.DINE_IN);
    System.out.println("Total Bill = " + totalPrice);
  }
}

class TakeOut extends Order {
  TakeOut(int orderId, int totalPrice) {
    super(orderId, totalPrice);
  }

  @Override
  void message() {
    System.out.println("Thanks for placing order, your order type = " + ORDER_TYPE.TAKEOUT);
    System.out.println("Total Bill = " + super.totalPrice);
  }
}

class FactoryClass {
  public static Order createOrderObject(ORDER_TYPE type, int orderId, int totalPrice) {
    if (type.equals(ORDER_TYPE.DINE_IN))
      return new DineIn(orderId, totalPrice);
    else
      return new TakeOut(orderId, totalPrice);
  }
}

public class SimpleFactoryDesignPattern {
  public static void main(String[] args) {
    Order dineInOrder = FactoryClass.createOrderObject(ORDER_TYPE.DINE_IN, 123, 3450);
    dineInOrder.message();
  }
}
