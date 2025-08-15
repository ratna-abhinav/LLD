package designPatterns;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PaymentGatewayManger {

    private PaymentGatewayManger() {
        System.out.println("Constrcutor");
    }

    private static PaymentGatewayManger instance;

    private static Lock mtx = new ReentrantLock();

    public static PaymentGatewayManger getInstance() {
        if (instance == null) {
            mtx.lock();
            try {
                if (instance == null) {
                    instance = new PaymentGatewayManger();
                }
            } finally {
                mtx.unlock();
            }
        }
        return instance;
    }

    public void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " through the payment gateway.");
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentGatewayManger paymentGateway = PaymentGatewayManger.getInstance();
        paymentGateway.processPayment(100.0);

    }
}
