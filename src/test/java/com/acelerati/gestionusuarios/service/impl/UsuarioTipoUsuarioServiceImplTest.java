package com.acelerati.gestionusuarios.service.impl;

import com.acelerati.gestionusuarios.dao.TipoUsuarioDao;
import com.acelerati.gestionusuarios.dao.UsuarioDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UsuarioTipoUsuarioServiceImplTest {
    @InjectMocks
    UsuarioTipoUsuarioServiceImpl usuarioTipoUsuarioService;

    @Mock
    UsuarioDao usuarioDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void cuandoElUsuarioTienePermisos() {
        when(this.usuarioDao.findByUsuarioIdAndTipoUsuarioTipoUsuarioId(any(), any())).thenReturn(true);

        Boolean aBoolean = this.usuarioTipoUsuarioService.validarUsuarioTipoUsuario(any(), any());
        assertNotNull(aBoolean);
        assertTrue(aBoolean);
    }

    @Test
    void cuandoElUsuarioNoTienePermisos() {
        when(this.usuarioDao.findByUsuarioIdAndTipoUsuarioTipoUsuarioId(any(), any())).thenReturn(false);

        Boolean aBoolean = this.usuarioTipoUsuarioService.validarUsuarioTipoUsuario(any(), any());
        assertNotNull(aBoolean);
        assertFalse(aBoolean);
    }
}