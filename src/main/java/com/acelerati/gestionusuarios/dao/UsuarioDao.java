/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acelerati.gestionusuarios.dao;

import com.acelerati.gestionusuarios.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
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
     * @param email String
     * @param codigo String
     * @return Optional Usuario
     * @author vbocanegra
     */
    Optional<Usuario> findByEmailAndCodigo(String email, String codigo);

}
