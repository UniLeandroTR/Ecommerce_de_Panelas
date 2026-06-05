package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(IllegalStateException exception) {
        return problemDetailSupport.toResponse(
                422,
                "Regra de negócio violada",
                exception.getMessage() != null ? exception.getMessage() : "Estado inválido para a operação.",
                ProblemTypes.BUSINESS_RULE,
                uriInfo
        );
    }
}
