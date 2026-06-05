package leepans.exception;

/**
 * Recurso solicitado não existe (HTTP 404).
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String resource;
    private final Object identifier;

    public ResourceNotFoundException(String resource, Object identifier) {
        super(buildMessage(resource, identifier));
        this.resource = resource;
        this.identifier = identifier;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.resource = null;
        this.identifier = null;
    }

    public String getResource() {
        return resource;
    }

    public Object getIdentifier() {
        return identifier;
    }

    private static String buildMessage(String resource, Object identifier) {
        if (resource == null || resource.isBlank()) {
            return "Recurso não encontrado.";
        }
        if (identifier == null) {
            return resource + " não encontrado.";
        }
        return resource + " com identificador '" + identifier + "' não encontrado.";
    }
}
