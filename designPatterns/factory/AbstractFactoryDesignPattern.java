package designPatterns.factory;

interface Sofa {
  void sitOn();
}

interface Table {
  void place();
}

class ModernSofa implements Sofa {
  @Override
  public void sitOn() {
    System.out.println("Sit on modern Sofa.");
  }
}

class TraditionalSofa implements Sofa {
  @Override
  public void sitOn() {
    System.out.println("Sit on traditional Sofa.");
  }
}

class ModernTable implements Table {
  @Override
  public void place() {
    System.out.println("Place the modern table.");
  }
}

class TraditionalTable implements Table {
  @Override
  public void place() {
    System.out.println("Place the traditional table.");
  }
}

// this will create a factory i.e., ModernFactory ...
interface FurnitureFactory {
  Sofa createSofa();

  Table createTable();

  public static FurnitureFactory createFurnitureFactory(String type) {
    if (type.equals("modern"))
      return new ModernFurnitureFactory();
    return new TraditionalFurnitureFactory();
  }
}

// this factory will create modern sofa, table ...
class ModernFurnitureFactory implements FurnitureFactory {
  @Override
  public Sofa createSofa() {
    return new ModernSofa();
  }

  @Override
  public Table createTable() {
    return new ModernTable();
  }
}

// this factory will create traditional sofa, table ...
class TraditionalFurnitureFactory implements FurnitureFactory {
  @Override
  public Sofa createSofa() {
    return new TraditionalSofa();
  }

  @Override
  public Table createTable() {
    return new TraditionalTable();
  }
}

public class AbstractFactoryDesignPattern {

  public static void main(String[] args) {
    FurnitureFactory modernFactory = FurnitureFactory.createFurnitureFactory("modern");
    Sofa modernSofa = modernFactory.createSofa();
    modernSofa.sitOn();

    Table moderTable = modernFactory.createTable();
    moderTable.place();
  }
}
