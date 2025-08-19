package designPatterns.chainOfResponsibility;

abstract class OrderHandler {
    protected OrderHandler nextHandler;

    abstract void processOrder(String order);

    void setNextHandler(OrderHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

class OrderValidationHandler extends OrderHandler {

    @Override
    public void processOrder(String order) {
        System.out.println(order + " order is placed :)");
        nextHandler.processOrder(order);
    }

}

class PaymentProcessHandler extends OrderHandler {

    @Override
    void processOrder(String order) {
        System.out.println("Payment is getting processed ....");
        if (nextHandler != null) nextHandler.processOrder(order);
    }
}

class FoodPreparationHandler extends OrderHandler {
    @Override
    void processOrder(String order) {
        System.out.println(order + " is getting prepared ....");
        if (nextHandler != null) nextHandler.processOrder(order);
    }
}

class DeliveryConfirmationHandler extends OrderHandler {
    @Override
    void processOrder(String order) {
        System.out.println(order + " is delivered :)");
        if (nextHandler != null) nextHandler.processOrder(order);
    }
}

public class ChainOfResponsibility {
    public static void main(String[] args) {

        OrderValidationHandler orderValidationHandler = new OrderValidationHandler();
        PaymentProcessHandler paymentProcessHandler = new PaymentProcessHandler();
        FoodPreparationHandler foodPreparationHandler = new FoodPreparationHandler();
        DeliveryConfirmationHandler deliveryConfirmationHandler = new DeliveryConfirmationHandler();

        orderValidationHandler.setNextHandler(paymentProcessHandler);
        paymentProcessHandler.setNextHandler(foodPreparationHandler);
        foodPreparationHandler.setNextHandler(deliveryConfirmationHandler);

        orderValidationHandler.processOrder("Pizza & Chicken");
    }
}