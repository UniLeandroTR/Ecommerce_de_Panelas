package leepans.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import leepans.dto.cupomDesconto.CupomDescontoEcommerceDTO;
import leepans.dto.cupomDesconto.CupomDescontoRequestDTO;
import leepans.dto.cupomDesconto.CupomDescontoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.CupomDescontoMapper;
import leepans.model.CupomDesconto;
import leepans.service.ecommerce.CupomDescontoService;

@Path("/cupons-desconto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CupomDescontoResource {

    @Inject
    CupomDescontoService service;

    @POST
    @Path("/admin")
    @RolesAllowed({ "ADMIN" })
    public Response create(@Valid CupomDescontoRequestDTO dto) {
        CupomDesconto cupomDesconto = service.create(CupomDescontoMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED)
                .entity(CupomDescontoMapper.toResponse(cupomDesconto))
                .build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<CupomDescontoResponseDTO> lista = service.findAll()
                .stream()
                .map(CupomDescontoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        CupomDescontoResponseDTO cupomDesconto = CupomDescontoMapper.toResponse(service.findById(id));
        return Response.ok(cupomDesconto).build();
    }

    @GET
    @Path("/ativo/{ativo}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findByAtivo(@PathParam("ativo") boolean ativo) {
        List<CupomDescontoResponseDTO> lista = service.findByAtivo(ativo)
                .stream()
                .map(CupomDescontoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/codigo/{codigo}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findByCodigo(@PathParam("codigo") String codigo) {
        CupomDescontoResponseDTO cupomDesconto = CupomDescontoMapper.toResponse(service.findByCodigo(codigo));
        return Response.ok(cupomDesconto).build();
    }

    @GET
    @Path("/aplicaveis")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findAplicaveis(@QueryParam("valorCompra") Double valorCompra) {
        List<CupomDescontoEcommerceDTO> lista = service.findByAtivoAndValorMinimoCompra(true, valorCompra)
                .stream()
                .map(CupomDescontoMapper::toEcommerceDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findAllEcommerce() {
        List<CupomDescontoEcommerceDTO> lista = service.findByAtivo(true)
                .stream()
                .map(CupomDescontoMapper::toEcommerceDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, @Valid CupomDescontoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException(
                    "A versão do cupom de desconto é obrigatória para atualização.",
                    "version"
            );
        }
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
