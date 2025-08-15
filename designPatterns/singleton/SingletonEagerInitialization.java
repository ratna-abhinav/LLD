package designPatterns.singleton;

class DatabaseConnection {
    private static final DatabaseConnection dbInstance = new DatabaseConnection();

    private DatabaseConnection() {
        System.out.println("Initializing DB connection...");
    }

    public static DatabaseConnection getInstance() {
        return dbInstance;
    }

    public void executeQuery(String query) {
        System.out.println("Executing Query = " + query);
    }
}

public class SingletonEagerInitialization {
    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.executeQuery("INSERT INTO salary VALUES()");
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        db2.executeQuery("SELECT * FORM salary");

        if (db1.equals(db2)) System.out.println("Singleton Design Pattern in working");
        else System.out.println("Singleton Design Pattern is not working");
    }
}
