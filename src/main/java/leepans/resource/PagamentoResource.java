package leepans.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.pagamento.PagamentoEcommerceDTO;
import leepans.dto.pagamento.PagamentoPatchDTO;
import leepans.dto.pagamento.PagamentoRequestDTO;
import leepans.dto.pagamento.PagamentoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.PagamentoMapper;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;
import leepans.service.ecommerce.PagamentoService;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService service;

    @POST
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid PagamentoRequestDTO dto) {
        Pagamento pagamento = service.create(PagamentoMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED)
                .entity(PagamentoMapper.toResponse(pagamento))
                .build();
    }

    @GET
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<PagamentoResponseDTO> lista = service.findAll()
                .stream()
                .map(PagamentoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        PagamentoResponseDTO pagamento = PagamentoMapper.toResponse(service.findById(id));
        return Response.ok(pagamento).build();
    }

    @GET
    @Path("/status/{status}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByStatus(@PathParam("status") StatusPagamento statusPagamento) {
        List<PagamentoResponseDTO> lista = service.findByStatusPagamento(statusPagamento)
                .stream()
                .map(PagamentoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/tipo/{tipo}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByTipo(@PathParam("tipo") TipoPagamento tipoPagamento) {
        List<PagamentoResponseDTO> lista = service.findByTipoPagamento(tipoPagamento)
                .stream()
                .map(PagamentoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/status/{status}/tipo/{tipo}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByStatusAndTipo(
            @PathParam("status") StatusPagamento statusPagamento,
            @PathParam("tipo") TipoPagamento tipoPagamento) {
        List<PagamentoResponseDTO> lista = service.findByStatusAndTipo(statusPagamento, tipoPagamento)
                .stream()
                .map(PagamentoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/valor-acima/{valor}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByValorGreaterThan(@PathParam("valor") Double valor) {
        List<PagamentoResponseDTO> lista = service.findByValorGreaterThan(valor)
                .stream()
                .map(PagamentoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/ecommerce")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findAllEcommerce() {
        List<PagamentoEcommerceDTO> lista = service.findAll()
                .stream()
                .map(PagamentoMapper::toEcommerceDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/ecommerce/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findByIdEcommerce(@PathParam("id") Long id) {
        return Response.ok(PagamentoMapper.toEcommerceDTO(service.findById(id))).build();
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response setStatus(@PathParam("id") Long id, @Valid PagamentoPatchDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException(
                    "A versão do pagamento é obrigatória para atualização.",
                    "version"
            );
        }
        service.setStatus(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
