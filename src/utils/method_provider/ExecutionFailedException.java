package utils.method_provider;

public class ExecutionFailedException extends RuntimeException {
    public ExecutionFailedException(String message) {
        super(message);
    }
}