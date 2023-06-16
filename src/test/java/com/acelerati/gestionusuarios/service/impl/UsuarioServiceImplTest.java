/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acelerati.gestionusuarios.service.impl;

/**
 *
 * @author boca-
 */
import com.acelerati.gestionusuarios.dao.TipoUsuarioDao;
import com.acelerati.gestionusuarios.dao.UsuarioDao;
import com.acelerati.gestionusuarios.dto.UsuarioDto;
import com.acelerati.gestionusuarios.entity.TipoUsuario;
import com.acelerati.gestionusuarios.entity.Usuario;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @InjectMocks
    UsuarioServiceImpl userService;

    @Mock
    UsuarioDao userDao;

    @Mock
    TipoUsuarioDao tipoUsuarioDao;

    @Test
    @DisplayName("Operacion de guardar Usuario existosa.")
    public void guardarUser() {

        Usuario user = new Usuario();
        user.setUsuarioId(2L);
        user.setNombre("Victor");
        user.setApellido("Bocanegra");

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(2L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(1L);

        Usuario userDec = new Usuario();
        userDec.setUsuarioId(2L);
        userDec.setNombre("User Decano");
        userDec.setApellido("decano");
        userDec.setTipoUsuario(crea);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findById(usuarioCreacion)).thenReturn(Optional.of(userDec));
        when(userDao.save(Mockito.any(Usuario.class))).thenReturn(user);
        assertTrue(userService.createUsuario(dto, usuarioCreacion).getMensaje().contains("Usuario creado satisfatoriamente"));
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error nombre.")
    public void guardarUserErrorNombre() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Nombre del usuario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error nombre 2.")
    public void guardarUserErrorNombre2() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("4564");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Nombre del usuario debe tener minimo 2 caracteres y maximo 20 y debe contener solo letras", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error apellido 1.")
    public void guardarUserErrorApellido() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Apellido del usuario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error apellido 2.")
    public void guardarUserErrorApellido2() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("4534");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El apellido del usuario debe tener minimo 2 caracteres y maximo 20 y debe contener solo letras", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error email 1.")
    public void guardarUserErrorEmail() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Email del usuario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error email 2.")
    public void guardarUserErrorEmail2() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El email ingresado no tiene una estructura valida ingrese: xxxx@xxx.com", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error Tipo Usuario.")
    public void guardarUserErrorTipoUsuario() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(0L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El tipo de usuario es obligatorio: "
                + "ingrese: 1-Decano, 2-Director, 3-Profesor o 4-Alumno", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error Tipo Usuario 2")
    public void guardarUserErrorTipoUsuario2() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(1L);

        Long usuarioCreacion = 1L;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("No existe el Tipo de usuario ingresado con id No : " + dto.getTipoUsuario() + "", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error Codigo")
    public void guardarUserErrorCodigo() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("");
        dto.setTipoUsuario(1L);

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(1L);

        Long usuarioCreacion = 1L;
        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Codigo del usuario es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error Codigo 2")
    public void guardarUserErrorCodigo2() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("564564");
        dto.setTipoUsuario(4L);

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(4L);

        Long usuarioCreacion = 1L;
        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Codigo del usuario debe tener  10 caracteres y debe contener solo numeros", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error Codigo 3")
    public void guardarUserErrorCodigo3() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("564564");
        dto.setTipoUsuario(3L);

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(3L);

        Long usuarioCreacion = 1L;
        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El Codigo del usuario debe tener minimo 8 caracteres y maximo 15 y debe contener solo numeros", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error existe usuario Codigo")
    public void guardarUserErrorExisteUsuarioCodigo() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("564565644");
        dto.setTipoUsuario(3L);

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(3L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(1L);

        Usuario userDec = new Usuario();
        userDec.setUsuarioId(2L);
        userDec.setNombre("User Decano");
        userDec.setApellido("decano");
        userDec.setTipoUsuario(crea);
        userDec.setCodigo("564564");

        Long usuarioCreacion = 2L;
        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findByCodigo(dto.getCodigo())).thenReturn(Optional.of(userDec));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("Ya existe un usuario con codigo No : " + dto.getCodigo() + "", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error existe usuario Email")
    public void guardarUserErrorExisteUsuarioEmail() {

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("564565644");
        dto.setTipoUsuario(3L);

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(3L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(1L);

        Usuario userDec = new Usuario();
        userDec.setUsuarioId(2L);
        userDec.setNombre("User Decano");
        userDec.setApellido("decano");
        userDec.setTipoUsuario(crea);
        userDec.setCodigo("564564");

        Long usuarioCreacion = 2L;
        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findByEmail(dto.getEmail())).thenReturn(Optional.of(userDec));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("Ya existe un usuario con email: " + dto.getEmail() + "", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error decano crea otro rol diferente de director")
    public void guardarUserErrorDecano() {

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(3L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(1L);

        Usuario userDec = new Usuario();
        userDec.setUsuarioId(2L);
        userDec.setNombre("User Decano");
        userDec.setApellido("decano");
        userDec.setTipoUsuario(crea);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("67637856344563");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findById(usuarioCreacion)).thenReturn(Optional.of(userDec));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El rol Decano solo puede crear Directores de Programas", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error director crea otro rol diferente de profesor")
    public void guardarUserErrorDirector() {

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(4L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(2L);

        Usuario userDir = new Usuario();
        userDir.setUsuarioId(2L);
        userDir.setNombre("User Dir");
        userDir.setApellido("director");
        userDir.setTipoUsuario(crea);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("6763785634");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findById(usuarioCreacion)).thenReturn(Optional.of(userDir));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El rol Director solo puede crear Profesores", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario profesor")
    public void guardarUserErrorProfesor() {

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(4L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(3L);

        Usuario userPro = new Usuario();
        userPro.setUsuarioId(2L);
        userPro.setNombre("User Pro");
        userPro.setApellido("profesor");
        userPro.setTipoUsuario(crea);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("6763785634");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findById(usuarioCreacion)).thenReturn(Optional.of(userPro));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El rol Profesor no puede crear usuarios", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario alumno crea otro rol diferente de alumno")
    public void guardarUserErrorAlumno() {

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(2L);

        TipoUsuario crea = new TipoUsuario();
        crea.setTipoUsuarioId(4L);

        Usuario userAlu = new Usuario();
        userAlu.setUsuarioId(2L);
        userAlu.setNombre("User Pro");
        userAlu.setApellido("profesor");
        userAlu.setTipoUsuario(crea);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("6763785634");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        when(userDao.findById(usuarioCreacion)).thenReturn(Optional.of(userAlu));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("El rol Alumno solo puede crear Alumnos", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario error usuario creacion no existe")
    public void guardarUserErrorUsuarioCreacionNoexiste() {

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(2L);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("6763785634");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 1L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("No existe un usuario de creacion con id = " + usuarioCreacion + "", exception.getMessage());
    }

    @Test
    @DisplayName("Operacion de guardar Usuario alumno crea otro rol diferente de alumno")
    public void guardarUserErrorAlumno2() {

        TipoUsuario tipoUsuToCrear = new TipoUsuario();
        tipoUsuToCrear.setTipoUsuarioId(2L);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Victor");
        dto.setApellido("Bocanegra");
        dto.setEmail("xxxx@gggg.com");
        dto.setCodigo("6763785634");
        dto.setTipoUsuario(2L);

        Long usuarioCreacion = 0L;

        when(tipoUsuarioDao.findById(dto.getTipoUsuario())).thenReturn(Optional.of(tipoUsuToCrear));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUsuario(dto, usuarioCreacion);
        });
        assertEquals("Solo pude crear usuarios de Rol Alumno", exception.getMessage());
    }

}
