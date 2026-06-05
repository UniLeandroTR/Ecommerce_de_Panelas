package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.BadRequestException;
import leepans.exception.ProblemDetail;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(BadRequestException exception) {
        ProblemDetail problemDetail = problemDetailSupport.create(
                400,
                "Requisição inválida",
                exception.getMessage(),
                ProblemTypes.BAD_REQUEST,
                uriInfo
        );

        if (exception.getField() != null) {
            problemDetail.addError(exception.getField(), exception.getMessage());
        }

        return problemDetailSupport.toResponse(problemDetail);
    }
}
