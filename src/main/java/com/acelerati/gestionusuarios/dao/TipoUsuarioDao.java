/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acelerati.gestionusuarios.dao;

import com.acelerati.gestionusuarios.entity.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;




/**
 *
 * @author vbocanegra
 */
public interface TipoUsuarioDao extends JpaRepository<TipoUsuario, Long> {

}
