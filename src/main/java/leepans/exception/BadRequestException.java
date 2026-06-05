package leepans.exception;

/**
 * Requisição inválida ou regra de entrada violada (HTTP 400).
 */
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String field;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
