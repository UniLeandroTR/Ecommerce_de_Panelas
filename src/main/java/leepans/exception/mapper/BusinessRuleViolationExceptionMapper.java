package leepans.exception.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import leepans.exception.BusinessRuleViolationException;
import leepans.exception.ProblemDetail;
import leepans.exception.ProblemDetailSupport;
import leepans.exception.ProblemTypes;

/**
 * Intercepta a exceção BusinessRuleViolationException e a converte em uma resposta 
 * HTTP estruturada no padrão RFC 7807.
 */
@Provider
public class BusinessRuleViolationExceptionMapper implements ExceptionMapper<BusinessRuleViolationException> {

    @Inject
    ProblemDetailSupport problemDetailSupport;

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(BusinessRuleViolationException exception) {
        int status = 422; 
        String title = "Violação de Regra de Negócio";

        ProblemDetail problemDetail = problemDetailSupport.create(
                status, 
                title, 
                exception.getMessage(), 
                ProblemTypes.BUSINESS_RULE,
                uriInfo
        );

        if (exception.getField() != null) {
            problemDetail.setField(exception.getField());
        }

        return problemDetailSupport.toResponse(problemDetail);
    }
}
