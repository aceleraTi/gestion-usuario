/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acelerati.gestionusuarios.service;

import com.acelerati.gestionusuarios.dto.UsuarioDto;
import java.util.List;

/**
 *
 * @author vbocanegra
 */
public interface UsuarioService {

    /**
     * Metodo que permite la creacion de un usuario.
     *
     * @author Victor Bocanegra
     * @param usuarioDto UsuarioDto
     * @param usuarioCreacion Long
     * @return  UsuarioDto
     */
    UsuarioDto createUsuario(UsuarioDto usuarioDto, Long usuarioCreacion);

    /**
     * Metodo que retorna un usuario por id en BD.
     *
     * @author Victor Bocanegra
     * @param id Long
     * @return UsuarioDto
     */
    UsuarioDto getUsuario(Long id);

    /**
     * Metodo que retorna listado de todos los usuarios en BD.
     *
     * @author Victor Bocanegra
     * @return List UsuarioDto
     */
    List<UsuarioDto> getUsuarios();

    /**
     * Metodo que consulta un usuario por email y login.
     *
     * @author Victor Bocanegra
     * @param email String
     * @param codigo String
     * @return UsuarioDto
     */
    UsuarioDto login(String email, String codigo);

}
