package microprocessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface Command {
    void execute(String[] args);
}

class Register {
    private Map<String, Integer> registers;

    public Register() {
        this.registers = new HashMap<>();
        registers.put("A", 0);
        registers.put("B", 0);
        registers.put("C", 0);
        registers.put("D", 0);
    }

    public void setValue(String register, int value) {
        if (registers.containsKey(register)) {
            registers.put(register, value);
        } else {
            System.out.println("Invalid register: " + register);
        }
    }

    public int getValue(String register) {
        if (registers.containsKey(register)) {
            return registers.get(register);
        }
        throw new IllegalArgumentException("Invalid register: " + register);
    }

    public void displayRegisters() {
        System.out.println("Register values:");
        for (String reg : registers.keySet()) {
            System.out.println(reg + " = " + registers.get(reg));
        }
        System.out.println("\n");
    }
}

// 1. Set Command (Single Responsibility Principle)
class SetCommand implements Command {
    private Register register;

    public SetCommand(Register register) {
        this.register = register;
    }

    @Override
    public void execute(String[] args) {
        String registerName = args[1];
        int value = Integer.parseInt(args[2]);
        register.setValue(registerName, value);
    }
}

// 2. Increment Command
class IncCommand implements Command {
    private Register register;

    public IncCommand(Register register) {
        this.register = register;
    }

    @Override
    public void execute(String[] args) {
        String registerName = args[1];
        int currentValue = register.getValue(registerName);
        register.setValue(registerName, currentValue + 1);
    }
}

// 3. Move Command
class MoveCommand implements Command {
    private Register register;

    public MoveCommand(Register register) {
        this.register = register;
    }

    @Override
    public void execute(String[] args) {
        String register1 = args[1];
        String register2 = args[2];
        int value = register.getValue(register2);
        register.setValue(register1, value);
    }
}

// 4. Decrement Command
class DecCommand implements Command {
    private Register register;

    public DecCommand(Register register) {
        this.register = register;
    }

    @Override
    public void execute(String[] args) {
        String registerName = args[1];
        int currentValue = register.getValue(registerName);
        register.setValue(registerName, currentValue - 1);
    }
}

// 5. AddR Command
class AddRCommand implements Command {
    private Register register;

    public AddRCommand(Register register) {
        this.register = register;
    }

    @Override
    public void execute(String[] args) {
        String register1 = args[1];
        String register2 = args[2];
        int sum = register.getValue(register1) + register.getValue(register2);
        register.setValue(register1, sum);
    }
}

// Command Processor (Open for extension, closed for modification)
class CommandProcessor {
    private Map<String, Command> commands;

    public CommandProcessor() {
        commands = new HashMap<>();
    }

    public void registerCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public void executeCommand(String input) {
        String[] args = input.split(" ");
        String commandName = args[0].toUpperCase();
        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute(args);
        } else {
            System.out.println("Invalid command: " + commandName);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Register register = new Register();
        CommandProcessor commandProcessor = new CommandProcessor();

        commandProcessor.registerCommand("SET", new SetCommand(register));
        commandProcessor.registerCommand("INC", new IncCommand(register));
        commandProcessor.registerCommand("MOVE", new MoveCommand(register));
        commandProcessor.registerCommand("DEC", new DecCommand(register));
        commandProcessor.registerCommand("ADDR", new AddRCommand(register));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter commands (type 'EXIT' to stop):");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                break;
            }
            commandProcessor.executeCommand(input);
            register.displayRegisters();
        }
        scanner.close();
    }
}
