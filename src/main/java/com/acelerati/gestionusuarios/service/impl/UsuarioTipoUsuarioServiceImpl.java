package com.acelerati.gestionusuarios.service.impl;

import com.acelerati.gestionusuarios.dao.UsuarioDao;
import com.acelerati.gestionusuarios.service.UsuarioTipoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioTipoUsuarioServiceImpl implements UsuarioTipoUsuarioService {

    private final UsuarioDao usuarioRepositorio;

    @Override
    public Boolean validarUsuarioTipoUsuario(Long idUsuario, Long idTipoUsuario) {
        return this.usuarioRepositorio.findByUsuarioIdAndTipoUsuarioTipoUsuarioId(idUsuario,
                idTipoUsuario);
    }



}
