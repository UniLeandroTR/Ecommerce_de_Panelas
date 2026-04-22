package leepans.exception.mapper;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;

/**
 * Mapeador para ConstraintViolationException do Bean Validation seguindo o padrão RFC 7807
 * Trata erros de validação de campos (anotações como @NotBlank, @NotNull, etc)
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ProblemDetail problemDetail = new ProblemDetail(
            422,
            "Erro de validação",
            "Um ou mais campos não passaram na validação."
        );

        problemDetail.setType("http://localhost:8080/errors/validation-error");
        
        // Adicionar a instância (URI da requisição)
        if (uriInfo != null) {
            problemDetail.setInstance(uriInfo.getPath());
        }

        // Adicionar cada erro de validação à lista
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            problemDetail.addError(extractFieldName(propertyPath), message);
        }

        return Response
            .status(422)
            .entity(problemDetail)
            .build();
    }

    private String extractFieldName(String propertyPath) {
        if (propertyPath == null || propertyPath.isBlank()) {
            return propertyPath;
        }

        int lastDot = propertyPath.lastIndexOf('.');
        return lastDot >= 0 ? propertyPath.substring(lastDot + 1) : propertyPath;
    }
}
