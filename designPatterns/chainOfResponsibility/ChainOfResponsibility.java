package designPatterns.chainOfResponsibility;

abstract class OrderHandler {
    protected OrderHandler nextHandler;

    abstract void processOrder(String order);

    void setNextHandler(OrderHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected void handleNext(String order) {
        if (nextHandler != null) {
            nextHandler.processOrder(order);
        }
    }
}

class OrderValidationHandler extends OrderHandler {

    @Override
    public void processOrder(String order) {
        System.out.println(order + " order is placed :)");
        super.handleNext(order);
    }

}

class PaymentProcessHandler extends OrderHandler {

    @Override
    void processOrder(String order) {
        System.out.println("Payment is getting processed ....");
        super.handleNext(order);
    }
}

class FoodPreparationHandler extends OrderHandler {
    @Override
    void processOrder(String order) {
        System.out.println(order + " is getting prepared ....");
        super.handleNext(order);
    }
}

class DeliveryConfirmationHandler extends OrderHandler {
    @Override
    void processOrder(String order) {
        System.out.println(order + " is delivered :)");
        super.handleNext(order);
    }
}

public class ChainOfResponsibility {
    public static void main(String[] args) {

        OrderHandler orderValidationHandler = new OrderValidationHandler();
        OrderHandler paymentProcessHandler = new PaymentProcessHandler();
        OrderHandler foodPreparationHandler = new FoodPreparationHandler();
        OrderHandler deliveryConfirmationHandler = new DeliveryConfirmationHandler();

        // we are deciding the next step/handler/process at runtime
        orderValidationHandler.setNextHandler(paymentProcessHandler);
        paymentProcessHandler.setNextHandler(foodPreparationHandler);
        foodPreparationHandler.setNextHandler(deliveryConfirmationHandler);

        orderValidationHandler.processOrder("Hot - Fresh Pizza");
    }
}