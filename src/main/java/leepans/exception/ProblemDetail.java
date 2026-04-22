package leepans.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representando o padrão RFC 7807 - Problem Details for HTTP APIs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {
    
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;
    private String timestamp;
    private String field;
    private List<FieldError> errors;

    public ProblemDetail() {
        this.timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME);
        this.errors = new ArrayList<>();
    }

    public ProblemDetail(Integer status, String title, String detail) {
        this();
        this.status = status;
        this.title = title;
        this.detail = detail;
    }

    public ProblemDetail(Integer status, String title, String detail, String field) {
        this();
        this.status = status;
        this.title = title;
        this.detail = detail;
        this.field = field;
    }

    public ProblemDetail(Integer status, String title, String detail, String instance, String type) {
        this();
        this.status = status;
        this.title = title;
        this.detail = detail;
        this.instance = instance;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    public void addError(String field, String message) {
        this.errors.add(new FieldError(field, message));
    }

    /**
     * Classe interna representando um erro de validação de campo
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError {
        private String field;
        private String message;

        public FieldError() {}

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
