package designPatterns.singleton;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DatabaseConnection {

    private DatabaseConnection() {
        System.out.println("Initializing connection...");
    }

    private static DatabaseConnection dbInstance;

    public void executeQuery(String query) {
        System.out.println("Executing Query = " + query);
    }

    private static Lock mtx = new ReentrantLock();

    // Double-checked locking
    public static DatabaseConnection getInstance() {
        if (Objects.isNull(dbInstance)) {
            mtx.lock();
            try {
                if (Objects.isNull(dbInstance)) dbInstance = new DatabaseConnection();
            } finally {
                mtx.unlock();
            }
        }
        return dbInstance;
    }
}


public class SingletonDPDoubleCheckedLocking {
    public static void main(String[] args) {

        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        db1.executeQuery("SELECT * from users");
        db2.executeQuery("INSERT INTO users VALUES(1, 'jjk', 'saturo gojo')");

        if (db1.equals(db2))
            System.out.println("db1 and db2 instances are same");
        else
            System.out.println("db1 instance and db2 instance is different");
    }
}
