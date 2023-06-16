package com.acelerati.gestionusuarios.controller;

import com.acelerati.gestionusuarios.dto.UsuarioDto;
import com.acelerati.gestionusuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author bocanegravm
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/1.0/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Metodo que permite obtener todos los usuarios de la BD.
     *
     * @author Victor Bocanegra
     * @return List UsuarioDto
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
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
     * @author Victor Bocanegra
     * @param usuarioId int
     * @return UsuarioDto
     */
    @RequestMapping(value = "/{usuarioId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UsuarioDto> getEmpleado(@ApiParam(value = "usuarioId", required = true) @PathVariable int usuarioId) {
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
     * @author Victor Bocanegra
     * @param usuarioId int
     * @param request UsuarioDto
     * @return UsuarioDto
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@RequestParam(value = "usuarioId", required=false,defaultValue = "0") int usuarioId, 
            @RequestBody UsuarioDto request) {
        try {
            usuarioService.createUsuario(request,Long.valueOf(usuarioId));
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo que permite el login de un usuario.
     *
     * @author Victor Bocanegra
     * @param email String
     * @param codigo String
     * @return UsuarioDto
     */
    @RequestMapping(value = "/login/{email}/{codigo}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UsuarioDto> login(@ApiParam(value = "email") @PathVariable String email,
            @ApiParam(value = "codigo") @PathVariable String codigo) {
        try {
            UsuarioDto us = usuarioService.login(email, codigo);
            return new ResponseEntity(us, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
