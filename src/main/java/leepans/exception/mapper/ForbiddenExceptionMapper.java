package leepans.exception.mapper;

import io.quarkus.security.ForbiddenException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ForbiddenException exception) {
        ProblemDetail problemDetail = problemDetailSupport.create(
                403,
                "Acesso negado",
                "Você não possui permissão para realizar esta operação.",
                ProblemTypes.AUTHORIZATION,
                uriInfo
        );

        if (uriInfo != null) {
            problemDetail.addError("endpoint", uriInfo.getPath());
        }

        return problemDetailSupport.toResponse(problemDetail);
    }
}
