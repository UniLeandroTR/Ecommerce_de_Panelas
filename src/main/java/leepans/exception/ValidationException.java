package leepans.exception;

/**
 * Exceção lançada quando há erros de validação de negócio
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String field;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message, String field, Throwable cause) {
        super(message, cause);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
