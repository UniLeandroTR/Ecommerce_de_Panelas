package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(WebApplicationException exception) {
        Response original = exception.getResponse();
        int status = original != null ? original.getStatus() : 500;

        String detail = exception.getMessage() != null && !exception.getMessage().isBlank()
                ? exception.getMessage()
                : "Erro ao processar a requisição.";

        String typeSuffix = switch (status) {
            case 400 -> ProblemTypes.BAD_REQUEST;
            case 401 -> ProblemTypes.UNAUTHORIZED;
            case 403 -> ProblemTypes.AUTHORIZATION;
            case 404 -> ProblemTypes.NOT_FOUND;
            case 409 -> ProblemTypes.CONCURRENCY;
            case 422 -> ProblemTypes.VALIDATION;
            default -> ProblemTypes.INTERNAL;
        };

        String title = switch (status) {
            case 400 -> "Requisição inválida";
            case 401 -> "Não autorizado";
            case 403 -> "Acesso negado";
            case 404 -> "Recurso não encontrado";
            case 409 -> "Conflito";
            case 422 -> "Erro de validação";
            default -> "Erro interno";
        };

        ProblemDetail problemDetail = problemDetailSupport.create(status, title, detail, typeSuffix, uriInfo);
        return problemDetailSupport.toResponse(problemDetail);
    }
}
