package leepans.exception.mapper;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;
import leepans.exception.ValidationException;

/**
 * Mapeador para ValidationException (erros de validação de negócio) seguindo o padrão RFC 7807
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ValidationException exception) {
        ProblemDetail problemDetail = new ProblemDetail(
            422,
            "Erro de validação",
            exception.getMessage()
        );
        
        problemDetail.setType("http://localhost:8080/errors/validation-error");
        
        // Adicionar a instância (URI da requisição)
        if (uriInfo != null) {
            problemDetail.setInstance(uriInfo.getPath());
        }
        
        // Adicionar o campo específico se disponível
        if (exception.getField() != null) {
            problemDetail.addError(exception.getField(), exception.getMessage());
        }

        return Response
            .status(422)
            .entity(problemDetail)
            .build();
    }
}
