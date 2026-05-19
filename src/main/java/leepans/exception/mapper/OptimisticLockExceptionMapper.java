package leepans.exception.mapper;

import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.ProblemDetail;

@Provider
public class OptimisticLockExceptionMapper implements ExceptionMapper<OptimisticLockException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(OptimisticLockException exception) {
        ProblemDetail problemDetail = new ProblemDetail(
            409,
            "Conflito de concorrência",
            exception.getMessage() != null
                ? exception.getMessage()
                : "O recurso foi alterado por outra transação."
        );

        problemDetail.setType("http://localhost:8080/errors/concurrency-conflict");

        if (uriInfo != null) {
            problemDetail.setInstance(uriInfo.getPath());
        }

        return Response
            .status(409)
            .entity(problemDetail)
            .build();
    }
}
