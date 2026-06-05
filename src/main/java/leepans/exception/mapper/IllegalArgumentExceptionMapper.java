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
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return problemDetailSupport.toResponse(
                400,
                "Requisição inválida",
                exception.getMessage() != null ? exception.getMessage() : "Argumento inválido.",
                ProblemTypes.BAD_REQUEST,
                uriInfo
        );
    }
}
