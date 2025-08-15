package designPatterns.factory;

interface Furniture 
{
  void display();
}

class Sofa implements Furniture 
{
  Sofa() {
    System.out.println("Sofa created...");
  }
  @Override
  public void display() {
    System.out.println("Sit on sofa");
  }
}

class Chair implements Furniture 
{
  Chair() {
    System.out.println("Chair created...");
  }
  @Override
  public void display() {
    System.out.println("Sit on chair");
  }
}

interface FurnitureFactory 
{
  Furniture createFurniture();
}

class SofaFactory implements FurnitureFactory 
{
  @Override
  public Furniture createFurniture() {
    return new Sofa();
  }
}

class ChairFactory implements FurnitureFactory
{
  @Override
  public Furniture createFurniture() {
    return new Chair();
  }
}

public class FactoryMethodDesignPattern {
  public static void main(String[] args) 
  {
    FurnitureFactory sofaFactory = new SofaFactory();
    Furniture sofa = sofaFactory.createFurniture();
    sofa.display();

    FurnitureFactory chairFactory = new ChairFactory();
    chairFactory.createFurniture().display();
  }
}
