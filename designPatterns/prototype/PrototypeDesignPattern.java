package designPatterns.prototype;

interface NetworkDevice {
  void display();

  NetworkDevice clone();

  void update(String newName);
}

class Router implements NetworkDevice {
  int id;
  String name;
  String firewallRules;

  public Router(int id, String name, String firewallRules) {
    this.id = id;
    this.name = name;
    this.firewallRules = firewallRules;
  }

  @Override
  public void display() {
    System.out.println("Router [ id = " + id + ", name = " + name + ", firewallRules = " + firewallRules + " ]");
  }

  @Override
  public NetworkDevice clone() {
    return new Router(id, name, firewallRules);
  }

  @Override
  public void update(String newName) {
    this.name = newName;
  }
}

public class PrototypeDesignPattern {
  public static void main(String[] args) {
    NetworkDevice routerPrototype = new Router(12, "Cisco", "Egress");
    routerPrototype.display();

    NetworkDevice routerClone = routerPrototype.clone();
    routerClone.display();

    routerPrototype.update("D-Link");
    routerPrototype.display();

    routerClone.display();
  }
}
