package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;
import leepans.exception.ResourceNotFoundException;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        ProblemDetail problemDetail = problemDetailSupport.create(
                404,
                "Recurso não encontrado",
                exception.getMessage(),
                ProblemTypes.NOT_FOUND,
                uriInfo
        );

        if (exception.getResource() != null) {
            problemDetail.addError("resource", exception.getResource());
        }
        if (exception.getIdentifier() != null) {
            problemDetail.addError("id", String.valueOf(exception.getIdentifier()));
        }

        return problemDetailSupport.toResponse(problemDetail);
    }
}
