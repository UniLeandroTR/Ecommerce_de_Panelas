package leepans.exception.mapper;

import io.quarkus.security.ForbiddenException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;

/**
 * Mapeador para ForbiddenException do Quarkus Security seguindo o padrão RFC 7807
 * Trata erros de autorização que ocorrem quando a anotação @RolesAllowed nega acesso
 */
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ForbiddenException exception) {
        ProblemDetail problemDetail = new ProblemDetail(
            403,
            "Acesso negado",
            "Você não possui permissão para realizar esta operação. Verifique suas credenciais e papéis de acesso."
        );
        
        problemDetail.setType("http://localhost:8080/errors/authorization-error");
        
        // Adicionar a instância (URI da requisição)
        if (uriInfo != null) {
            problemDetail.setInstance(uriInfo.getPath());
            problemDetail.addError("endpoint", uriInfo.getPath());
        }
        
        problemDetail.addError("motivo", "Usuário não possui os papéis requeridos");

        return Response
            .status(403)
            .entity(problemDetail)
            .build();
    }
}
