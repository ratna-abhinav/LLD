import javax.smartcardio.CommandAPDU;

interface Command {
  void execute();
}

class Document {
  public void open() {
    System.out.println("doc is opening ....");
  }
  public void save() {
    System.out.println("doc is getting saved ....");
  }
}

class OpenCommand implements Command {
  private final Document doc;

  public OpenCommand(Document doc) {
    this.doc = doc;
  }

  @Override
  public void execute() {
    doc.open();
  }
}

class SaveCommand implements Command {
  private Document doc;

  public SaveCommand(Document doc) {
    this.doc = doc;
  }

  @Override
  public void execute() {
    doc.save();
  }
}

class Invoker {

  Command saveCmd;
  Command openCmd;

  // public Invoker(Command saveCmd, Command openCmd) {
  //   this.saveCmd = saveCmd;
  //   this.openCmd = openCmd;
  // }

  // public void clickSave() {
  //   saveCmd.execute();
  // }

  // public void clickOpen() {
  //   openCmd.execute();
  // }

  public void clickSave(Command cmd) {
    cmd.execute();
  }
  public void clickOpen(Command cmd) {
    cmd.execute();
  }
}

public class CommandDesignPattern {  
  public static void main(String[] args) {
    Document doc = new Document();
    Command openCmd = new OpenCommand(doc);
    Command saveCmd = new SaveCommand(doc);
    
    Invoker invoker = new Invoker();
    invoker.clickOpen(openCmd);
    invoker.clickSave(saveCmd);

    // Invoker invoker = new Invoker(saveCmd, openCmd);
    // invoker.clickSave();
    // invoker.clickOpen();
  }
}
