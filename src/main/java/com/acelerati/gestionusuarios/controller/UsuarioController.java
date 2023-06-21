package com.acelerati.gestionusuarios.controller;

import com.acelerati.gestionusuarios.dto.UsuarioDto;
import com.acelerati.gestionusuarios.service.UsuarioService;
import com.acelerati.gestionusuarios.service.UsuarioTipoUsuarioService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bocanegravm
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@Api(tags = "Gestion de usuarios",description = "Permite, Crear un usuario, Obtener un usuario por el Id," +
        "Listar todos los usuarios del sistema  y validar un usuario.")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioTipoUsuarioService usuarioTipoUsuarioService;

    /**
     * Metodo que permite obtener todos los usuarios de la BD.
     *
     * @return List UsuarioDto
     * @author Victor Bocanegra
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "Listar usuarios", description ="Permite obtener todos los usuarios del sistema.")
    public ResponseEntity<List<UsuarioDto>> findAll() {
        try {
            List<UsuarioDto> list = usuarioService.getUsuarios();
            return ResponseEntity.ok(list);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo que permite obtener un usuario de la BD.
     *
     * @param usuarioId int
     * @return UsuarioDto
     * @author Victor Bocanegra
     */
    @RequestMapping(value = "/{usuarioId}", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "Obtener usuario", description ="Permite obtener un usuario por su id.")
    public ResponseEntity<UsuarioDto> getEmpleado(@ApiParam(value = "usuarioId", required = true)
            @PathVariable int usuarioId) {
        try {
            UsuarioDto us = usuarioService.getUsuario(Long.valueOf(usuarioId));
            return new ResponseEntity(us, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo que permite la creacion de un usuario.
     *
     * @param usuarioId int
     * @param request UsuarioDto
     * @return UsuarioDto
     * @author Victor Bocanegra
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @Operation(summary = "Crear usuario", description ="Permite Crear un usuario en el sistema.")
    public ResponseEntity<?> create(@RequestParam(value = "usuarioId", required = false, defaultValue = "0") int usuarioId,
            @RequestBody UsuarioDto request) {
        try {
            usuarioService.createUsuario(request, Long.valueOf(usuarioId));
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/validar/{idUsuario}/{idTipoUsuario}", method = RequestMethod.GET,
            produces
            = "application/json")
    @Operation(summary = "Validar usuario", description ="Permite validar por tipo de usuario.")
    public ResponseEntity<?> validar(@PathVariable Long idUsuario,
            @PathVariable Long idTipoUsuario) {

        System.out.println("ooo");
        return new ResponseEntity<>(
                this.usuarioTipoUsuarioService
                        .validarUsuarioTipoUsuario(idUsuario, idTipoUsuario), HttpStatus.OK);

    }
}
