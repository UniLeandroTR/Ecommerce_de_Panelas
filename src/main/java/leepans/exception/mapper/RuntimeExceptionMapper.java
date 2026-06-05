package leepans.exception.mapper;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;
import org.jboss.logging.Logger;

/**
 * Fallback para exceções não mapeadas. Prioridade baixa para não sobrepor mappers específicos.
 */
@Provider
@Priority(Interceptor.Priority.APPLICATION + 1000)
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger LOG = Logger.getLogger(RuntimeExceptionMapper.class);

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(RuntimeException exception) {
        LOG.error("Erro não tratado", exception);

        return problemDetailSupport.toResponse(
                500,
                "Erro interno do servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                ProblemTypes.INTERNAL,
                uriInfo
        );
    }
}
