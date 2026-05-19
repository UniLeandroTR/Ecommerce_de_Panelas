package leepans.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.colecao.ColecaoRequestDTO;
import leepans.dto.colecao.ColecaoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.ColecaoMapper;
import leepans.model.Colecao;
import leepans.service.ecommerce.ColecaoService;

@Path("/colecoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColecaoResource {

    @Inject
    ColecaoService service;

    @Inject
    ColecaoMapper mapper;

    @POST
    @Transactional
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid ColecaoRequestDTO dto) {
        Colecao colecao = service.create(mapper.toEntity(dto));
        return Response.status(201).entity(mapper.toResponseDTO(colecao)).build();
    }

    @GET
    public Response findAll() {
        List<ColecaoResponseDTO> lista = service.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<ColecaoResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, ColecaoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão da coleção é obrigatória para atualização.", "version");
        }
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}