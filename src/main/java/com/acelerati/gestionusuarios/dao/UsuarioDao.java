/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acelerati.gestionusuarios.dao;

import com.acelerati.gestionusuarios.entity.TipoUsuario;
import com.acelerati.gestionusuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author vbocanegra
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    /**
     * Metodo que retorna un Usuario a partir de su codigo.
     *
     * @param codigo String
     * @return Optional Usuario
     * @author vbocanegra
     */
    Optional<Usuario> findByCodigo(String codigo);

    /**
     * Metodo que retorna un Usuario a partir de su email.
     *
     * @param email String
     * @return Optional Usuario
     * @author vbocanegra
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Metodo que retorna un Usuario a partir de su nombre y su apellido.
     *
     * @param email  String
     * @param codigo String
     * @return Optional Usuario
     * @author vbocanegra
     */
    Optional<Usuario> findByEmailAndCodigo(String email, String codigo);

    /**
     * Metodo que retorna una lista de Usuario a partir de su tipo.
     *
     * @param tipoUsuario TipoUsuario
     * @return Lista Usuario
     * @author vbocanegra
     */
    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

    @Query(value = "Select " +
            "if(usuario.tipo_usuario_id = ?2,'true','false') " +
            "From usuario WHERE usuario.usuario_id = ?1", nativeQuery = true)
    Boolean findByUsuarioIdAndTipoUsuarioTipoUsuarioId(Long idUser, Long tipoUsuario);


}
