package designPatterns.decorator;

abstract class BeverageComponent {
  public abstract double getPrice();
  String description = "Unknown Berverage";
}

class Expresso extends BeverageComponent {

  Expresso() {
    this.description = "Expresso";
  }

  @Override
  public double getPrice() {
    return 1.99;
  }
}

class DarkRoast extends BeverageComponent {

  DarkRoast() {
    this.description = "Dark Roast";
  }

  @Override
  public double getPrice() {
    return 0.99;
  }
}

abstract class CondimentDecorator extends BeverageComponent {
  BeverageComponent beverage;
}

class Mocha extends CondimentDecorator {

  Mocha(BeverageComponent beverage) {
    this.beverage = beverage;
  }

  @Override
  public double getPrice() {
    return beverage.getPrice() + 0.20;
  }
}

class Whip extends CondimentDecorator {

  Whip(BeverageComponent beverage) {
    this.beverage = beverage;
  }

  @Override
  public double getPrice() {
    return beverage.getPrice() + 0.30;
  }
}

public class Main {
  public static void main(String[] args) {
    BeverageComponent expresso = new Expresso();
    System.out.println("Expresso price: " + expresso.getPrice());

    // Expresso with Mocha
    BeverageComponent expressoWithMocha = new Mocha(expresso);
    System.out.println("Expresso with Mocha price: " + expressoWithMocha.getPrice());

    // Expresso with Mocha and Whip
    BeverageComponent expressoWithMochaAndWhip = new Whip(expressoWithMocha);
    System.out.println("Expresso with Mocha and Whip price: " + expressoWithMochaAndWhip.getPrice());
  }
}
