package designPatterns.builder;

class Desktop {
  private String motherboard;
  private String memory;
  private String processor;
  private String storage = "DDR4";

  public String getMotherboard() {
    return motherboard;
  }

  public void setMotherboard(String motherboard) {
    this.motherboard = motherboard;
  }

  public String getMemory() {
    return memory;
  }

  public void setMemory(String memory) {
    this.memory = memory;
  }

  public String getProcessor() {
    return processor;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public String getStorage() {
    return storage;
  }

  public void setStorage(String storage) {
    this.storage = storage;
  }

  @Override
  public String toString() {
    return "Desktop [motherboard=" + motherboard + ", memory=" + memory + ", processor=" + processor + ", storage="
        + storage + "]";
  }

}

abstract class DesktopBuilder {
  protected Desktop desktop;
  // protected Desktop desktop = new Desktop();

  DesktopBuilder() {
    System.out.println("Base Class called\n");
  }

  abstract DesktopBuilder buildMotherboard();

  abstract DesktopBuilder buildProcessor();

  abstract DesktopBuilder buildMemory();

  abstract DesktopBuilder buildStorage();

  public Desktop build() {
    return desktop;
  }
}

class DellDesktopBuilder extends DesktopBuilder {
  DellDesktopBuilder() {
    desktop = new Desktop();
  }

  @Override
  DesktopBuilder buildMotherboard() {
    desktop.setMotherboard("Dell Motherboard");
    return this;
  }

  @Override
  DesktopBuilder buildProcessor() {
    desktop.setProcessor("Dell Processor");
    return this;
  }

  @Override
  DesktopBuilder buildMemory() {
    desktop.setMemory("Dell Memory");
    return this;
  }

  @Override
  DesktopBuilder buildStorage() {
    desktop.setStorage("Dell Storage");
    return this;
  }
}

class HPDesktopBuilder extends DesktopBuilder {
  HPDesktopBuilder() {
    desktop = new Desktop();
  }

  @Override
  DesktopBuilder buildMotherboard() {
    desktop.setMotherboard("HP Motherboard");
    return this;
  }

  @Override
  DesktopBuilder buildProcessor() {
    desktop.setProcessor("HP Processor");
    return this;
  }

  @Override
  DesktopBuilder buildMemory() {
    desktop.setMemory("HP Memory");
    return this;
  }

  @Override
  DesktopBuilder buildStorage() {
    desktop.setStorage("HP Storage");
    return this;
  }
}

class DesktopDirector {

  public Desktop buildDesktop(DesktopBuilder builder) {
    return builder.buildMotherboard().buildProcessor().buildMemory().build();
  }
}

public class BuilderDesignPattern {
  public static void main(String[] args) {
    DesktopDirector director = new DesktopDirector();
    DellDesktopBuilder dellDesktopBuilder = new DellDesktopBuilder();
    Desktop dellDesktop = director.buildDesktop(dellDesktopBuilder);
    System.out.println(dellDesktop.toString());
    System.out.println();

    HPDesktopBuilder hpDesktopBuilder = new HPDesktopBuilder();
    Desktop hpDesktop = director.buildDesktop(hpDesktopBuilder);
    System.out.println(hpDesktop.toString());
  }
}
