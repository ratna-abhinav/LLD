import java.util.HashMap;
import java.util.Map;

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
  public void close() {
    System.out.println("doc is closing ....");
  }
}

class OpenCommand implements Command {
  private final Document doc;
  public OpenCommand(Document doc) { this.doc = doc; }
  @Override public void execute() { doc.open(); }
}

class SaveCommand implements Command {
  private final Document doc;
  public SaveCommand(Document doc) { this.doc = doc; }
  @Override public void execute() { doc.save(); }
}

class CloseCommand implements Command {
  private final Document doc;
  public CloseCommand(Document doc) { this.doc = doc; }
  @Override public void execute() { doc.close(); }
}

class ShortcutManager {
  private final Map<String, Command> shortcuts = new HashMap<>();

  public void registerShortcut(String keyCombo, Command command) {
    System.out.println("org: " + keyCombo + " normalized: " + normalize(keyCombo));
    shortcuts.put(normalize(keyCombo), command);
  }

  public void unregisterShortcut(String keyCombo) {
    shortcuts.remove(normalize(keyCombo));
  }

  public void handleKeyCombo(String keyCombo) {
    String key = normalize(keyCombo);
    Command cmd = shortcuts.get(key);
    if (cmd != null) {
      System.out.printf("Executing shortcut %s -> ", keyCombo);
      cmd.execute();
    } else {
      System.out.printf("No action mapped for shortcut '%s'%n", keyCombo);
    }
  }

  private String normalize(String combo) {
    if (combo == null) return "";
    return combo.trim().toUpperCase().replaceAll("\\s+", "");
  }
}

public class ShortcutsModeler {
  public static void main(String[] args) {
    Document doc = new Document();

    Command openCmd = new OpenCommand(doc);
    Command saveCmd = new SaveCommand(doc);
    Command closeCmd = new CloseCommand(doc);

    ShortcutManager manager = new ShortcutManager();

    manager.registerShortcut("CTRL+O", openCmd);
    manager.registerShortcut("CTRL+S", saveCmd);
    manager.registerShortcut("CTRL+W", closeCmd);

    manager.handleKeyCombo("CTRL+O"); 
    manager.handleKeyCombo("ctrl+s");
    manager.handleKeyCombo("Ctrl + W");
    manager.handleKeyCombo("CTRL+P");
     
    // manager.registerShortcut("CTRL+Q", () -> System.out.println("Quit app (lambda)"));
    // manager.handleKeyCombo("ctrl+q");
  }
}
