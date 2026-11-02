package demo.Online.Food.Order.System.exception;

public class OnlineFoodOrderNotFoundException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OnlineFoodOrderNotFoundException(String message) {

        super(message);

    }
}