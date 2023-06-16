/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acelerati.gestionusuarios.service.impl;

import com.acelerati.gestionusuarios.dao.TipoUsuarioDao;
import com.acelerati.gestionusuarios.dao.UsuarioDao;
import com.acelerati.gestionusuarios.dto.UsuarioDto;
import com.acelerati.gestionusuarios.entity.TipoUsuario;
import com.acelerati.gestionusuarios.entity.Usuario;
import com.acelerati.gestionusuarios.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vbocanegra
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private TipoUsuarioDao tipoUsuarioDao;

    private TipoUsuario tipoUsuario;

    private final Pattern nombres = Pattern.compile("[a-zA-Z ]{2,20}");
    private final Pattern email = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private final Pattern codigoGral = Pattern.compile("[0-9]{8,15}");
    private final Pattern codigoAlu = Pattern.compile("[0-9]{10}");

    /**
     * Metodo que permite la creacion de un usuario.
     *
     * @author Victor Bocanegra
     * @param usuarioDto UsuarioDto
     * @param usuarioCreacion Long
     */
    @Override
    @Transactional
    public UsuarioDto createUsuario(UsuarioDto usuarioDto, Long usuarioCreacion) {
        Usuario entity;
        validacionesUsuario(usuarioDto, usuarioCreacion);
        entity = new Usuario(usuarioDto.getNombre(), usuarioDto.getApellido(), usuarioDto.getEmail(), usuarioDto.getCodigo(), tipoUsuario);
        usuarioDao.save(entity);
        return new UsuarioDto("Usuario creado satisfatoriamente");

    }

    public void validacionesUsuario(UsuarioDto usuarioDto, Long usuarioCreacion) {

        if (usuarioDto.getNombre() == null || usuarioDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El Nombre del usuario es obligatorio");
        } else {
            Matcher mat = nombres.matcher(usuarioDto.getNombre());
            if (!mat.matches()) {
                throw new IllegalArgumentException("El Nombre del usuario debe tener minimo 2 caracteres y maximo 20 y debe contener solo letras");
            }
        }
        if (usuarioDto.getApellido() == null || usuarioDto.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El Apellido del usuario es obligatorio");
        } else {
            Matcher mat = nombres.matcher(usuarioDto.getApellido());
            if (!mat.matches()) {
                throw new IllegalArgumentException("El apellido del usuario debe tener minimo 2 caracteres y maximo 20 y debe contener solo letras");
            }
        }
        if (usuarioDto.getEmail() == null || usuarioDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El Email del usuario es obligatorio");
        } else {
            Matcher mat = email.matcher(usuarioDto.getEmail());
            if (!mat.matches()) {
                throw new IllegalArgumentException("El email ingresado no tiene una estructura valida ingrese: xxxx@xxx.com");
            }
        }

        if (usuarioDto.getTipoUsuario() == 0) {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio: "
                    + "ingrese: 1-Decano, 2-Director, 3-Profesor o 4-Alumno");
        }
        Optional<TipoUsuario> tipoUsu = tipoUsuarioDao.findById(usuarioDto.getTipoUsuario());

        if (tipoUsu.isEmpty()) {
            throw new IllegalArgumentException("No existe el Tipo de usuario ingresado con id No : " + usuarioDto.getTipoUsuario() + "");
        } else {
            tipoUsuario = tipoUsu.get();
        }

        if (usuarioDto.getCodigo() == null || usuarioDto.getCodigo().isEmpty()) {
            throw new IllegalArgumentException("El Codigo del usuario es obligatorio");
        } else {
            if (tipoUsuario.getTipoUsuarioId() == 4) {
                //es Alumno
                Matcher mat = codigoAlu.matcher(usuarioDto.getCodigo());
                if (!mat.matches()) {
                    throw new IllegalArgumentException("El Codigo del usuario debe tener  10 caracteres y debe contener solo numeros");
                }
            } else {
                Matcher mat = codigoGral.matcher(usuarioDto.getCodigo());
                if (!mat.matches()) {
                    throw new IllegalArgumentException("El Codigo del usuario debe tener minimo 8 caracteres y maximo 15 y debe contener solo numeros");
                }
            }

        }

        Optional<Usuario> existe = usuarioDao.findByCodigo(usuarioDto.getCodigo());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con codigo No : " + usuarioDto.getCodigo() + "");
        }
        existe = usuarioDao.findByEmail(usuarioDto.getEmail());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con email: " + usuarioDto.getEmail() + "");
        }

        existe = usuarioDao.findById(usuarioCreacion);
        if (existe.isPresent()) {
            //Valido si puede crear al usuario 
            if (existe.get().getTipoUsuario().getTipoUsuarioId() == 1) {
                //Puede crear solo directores
                if (tipoUsuario.getTipoUsuarioId() != 2) {
                    throw new IllegalArgumentException("El rol Decano solo puede crear Directores de Programas");
                }
            } else if (existe.get().getTipoUsuario().getTipoUsuarioId() == 2) {
                //Puede crear solo profesores
                if (tipoUsuario.getTipoUsuarioId() != 3) {
                    throw new IllegalArgumentException("El rol Director solo puede crear Profesores");
                }
            } else if (existe.get().getTipoUsuario().getTipoUsuarioId() == 3) {
                //No puede crear
                throw new IllegalArgumentException("El rol Profesor no puede crear usuarios");
            } else if (existe.get().getTipoUsuario().getTipoUsuarioId() == 4) {
                //Puede crear solo alumnos
                if (tipoUsuario.getTipoUsuarioId() != 4) {
                    throw new IllegalArgumentException("El rol Alumno solo puede crear Alumnos");
                }
            }
        } else if (usuarioCreacion != 0) {
            throw new IllegalArgumentException("No existe un usuario de creacion con id = " + usuarioCreacion + "");
        } else {
            //Puede ser creacion de Alumno
            if (tipoUsuario.getTipoUsuarioId() != 4) {
                throw new IllegalArgumentException("Solo pude crear usuarios de Rol Alumno");
            }
        }
    }

    /**
     * Metodo que retorna un usuario por id en BD.
     *
     * @author Victor Bocanegra
     * @param id Long
     * @return UsuarioDto
     */
    @Override
    public UsuarioDto getUsuario(Long id) {
        Optional<Usuario> usuario = usuarioDao.findById(id);
        UsuarioDto result = null;
        if (usuario.isPresent()) {
            result = new UsuarioDto(usuario.get().getUsuarioId(), usuario.get().getNombre(),
                    usuario.get().getApellido(), usuario.get().getEmail(), usuario.get().getCodigo(),
                    usuario.get().getTipoUsuario().getTipoUsuarioId());
        } else {
            throw new IllegalArgumentException("No existe el usuario en BD");
        }
        return result;
    }

    /**
     * Metodo que consulta un usuario por email y login.
     *
     * @author Victor Bocanegra
     * @param email String
     * @param codigo String
     * @return UsuarioDto
     */
    @Override
    public UsuarioDto login(String email, String codigo) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese el email.");
        }
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese el codigo.");
        }
        Optional<Usuario> usuario = usuarioDao.findByEmailAndCodigo(email, codigo);
        UsuarioDto result = null;
        if (usuario.isPresent()) {
            result = new UsuarioDto(usuario.get().getUsuarioId(), usuario.get().getNombre(),
                    usuario.get().getApellido(), usuario.get().getEmail(), usuario.get().getCodigo(),
                    usuario.get().getTipoUsuario().getTipoUsuarioId());
        } else {
            throw new IllegalArgumentException("No existe el usuario en BD. Por favor registrese o solicite su creación");
        }
        return result;
    }

    /**
     * Metodo que retorna listado de todos los usuarios en BD.
     *
     * @author Victor Bocanegra
     * @return List UsuarioDto
     */
    @Override
    public List<UsuarioDto> getUsuarios() {

        List<Usuario> list = usuarioDao.findAll();
        List<UsuarioDto> result = new ArrayList<>();
        list.stream().map((usuario) -> {
            UsuarioDto dto = new UsuarioDto(usuario.getUsuarioId(), usuario.getNombre(),
                    usuario.getApellido(), usuario.getEmail(), usuario.getCodigo(),
                    usuario.getTipoUsuario().getTipoUsuarioId());
            return dto;
        }).forEachOrdered((dto) -> {
            result.add(dto);
        });
        return result;
    }
}
