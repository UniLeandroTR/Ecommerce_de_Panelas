package leepans.exception;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 * Fábrica de respostas {@link ProblemDetail} conforme RFC 7807.
 */
@ApplicationScoped
public class ProblemDetailSupport {

    public static final MediaType PROBLEM_JSON_MEDIA_TYPE =
            new MediaType("application", "problem+json");

    @ConfigProperty(name = "app.api.problem-base-uri", defaultValue = ProblemTypes.DEFAULT_BASE_URI)
    String problemBaseUri;

    public String typeUri(String suffix) {
        String base = problemBaseUri.endsWith("/") ? problemBaseUri.substring(0, problemBaseUri.length() - 1) : problemBaseUri;
        return base + "/" + suffix;
    }

    public ProblemDetail create(int status, String title, String detail, String typeSuffix, UriInfo uriInfo) {
        ProblemDetail problemDetail = new ProblemDetail(status, title, detail);
        problemDetail.setType(typeUri(typeSuffix));
        if (uriInfo != null) {
            problemDetail.setInstance(uriInfo.getRequestUri().getPath());
        }
        return problemDetail;
    }

    public Response toResponse(int status, String title, String detail, String typeSuffix, UriInfo uriInfo) {
        return Response
                .status(status)
                .type(PROBLEM_JSON_MEDIA_TYPE)
                .entity(create(status, title, detail, typeSuffix, uriInfo))
                .build();
    }

    public Response toResponse(ProblemDetail problemDetail) {
        return Response
                .status(problemDetail.getStatus())
                .type(PROBLEM_JSON_MEDIA_TYPE)
                .entity(problemDetail)
                .build();
    }
}
