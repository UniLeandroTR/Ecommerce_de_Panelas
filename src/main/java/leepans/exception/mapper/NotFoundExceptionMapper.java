package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException exception) {
        String detail = exception.getMessage() != null && !exception.getMessage().isBlank()
                ? exception.getMessage()
                : "O recurso solicitado não foi encontrado.";

        return problemDetailSupport.toResponse(
                404,
                "Recurso não encontrado",
                detail,
                ProblemTypes.NOT_FOUND,
                uriInfo
        );
    }
}
