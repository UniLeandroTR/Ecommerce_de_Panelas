package leepans.exception.mapper;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.AuthorizationException;
import leepans.exception.ProblemDetail;

/**
 * Mapeador para AuthorizationException (erros de autorização) seguindo o padrão RFC 7807
 * Trata casos onde o usuário não possui permissão para acessar um recurso
 */
@Provider
public class AuthorizationExceptionMapper implements ExceptionMapper<AuthorizationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(AuthorizationException exception) {
        ProblemDetail problemDetail = new ProblemDetail(
            403,
            "Acesso negado",
            exception.getMessage()
        );
        
        problemDetail.setType("http://localhost:8080/errors/authorization-error");
        
        // Adicionar a instância (URI da requisição)
        if (uriInfo != null) {
            problemDetail.setInstance(uriInfo.getPath());
        }
        
        // Adicionar o recurso específico se disponível
        if (exception.getResource() != null) {
            problemDetail.addError("resource", "Recurso: " + exception.getResource());
        }

        return Response
            .status(403)
            .entity(problemDetail)
            .build();
    }
}
