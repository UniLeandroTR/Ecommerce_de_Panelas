package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        String detail = exception.getMessage() != null && !exception.getMessage().isBlank()
                ? exception.getMessage()
                : "Credenciais inválidas ou ausentes.";

        return problemDetailSupport.toResponse(
                401,
                "Não autorizado",
                detail,
                ProblemTypes.UNAUTHORIZED,
                uriInfo
        );
    }
}
