package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;
import leepans.exception.ValidationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ValidationException exception) {
        ProblemDetail problemDetail = problemDetailSupport.create(
                422,
                "Erro de validação",
                exception.getMessage(),
                ProblemTypes.VALIDATION,
                uriInfo
        );

        if (exception.getField() != null) {
            problemDetail.addError(exception.getField(), exception.getMessage());
        }

        return problemDetailSupport.toResponse(problemDetail);
    }
}
