package leepans.exception;

/**
 * Exceção lançada quando uma regra de negócio específica é violada.
 */
public class BusinessRuleViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String field;

    public BusinessRuleViolationException(String message) {
        super(message);
    }

    public BusinessRuleViolationException(String message, String field) {
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
