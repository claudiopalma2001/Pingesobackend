package com.proyecto7.docedeseosbackend.controller;
import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;
import com.proyecto7.docedeseosbackend.responses.Login;
import com.proyecto7.docedeseosbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return una respuesta con la lista de usuarios.
     */
    @GetMapping("/")
    public ResponseEntity<List<UsuarioEntity>> listarUsuarios() {
        List<UsuarioEntity> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id el identificador del usuario.
     * @return una respuesta con el usuario encontrado.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@PathVariable Long id) {
        UsuarioEntity usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por su correo.
     *
     * @param correo el correo del usuario.
     * @return una respuesta con el usuario encontrado.
     */
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioEntity> getUsuarioByCorreo(@PathVariable String correo) {
        UsuarioEntity usuario = usuarioService.getUsuarioByCorreo(correo);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por su correo.
     *
     * @param correoId el correo del usuario.
     * @return una respuesta con el usuario encontrado.
     */
    @GetMapping("/correoId/{correoId}")
    public ResponseEntity<Long> getIdUsuarioByCorreo(@PathVariable String correoId) {
        UsuarioEntity usuario = usuarioService.getUsuarioByCorreo(correoId);
        if (usuario != null) {
            return ResponseEntity.ok(usuario.getId());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtiene una lista de usuarios filtrados por edad.
     *
     * @param edad la edad para filtrar los usuarios.
     * @return una respuesta con la lista de usuarios filtrados.
     */
    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<UsuarioEntity>> getUsuariosByEdad(@PathVariable int edad) {
        List<UsuarioEntity> usuarios = usuarioService.getUsuariosByEdad(edad);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Método para obtener todos los usuarios asociados a un rol específico.
     *
     * @param idRol El ID del rol para el cual se desean obtener los usuarios.
     * @return Una lista de objetos {@link UsuarioEntity} que contienen los usuarios asociados al rol especificado.
     */
    @GetMapping("/idRol/{idRol}")
    public ResponseEntity<List<UsuarioEntity>> getUsuariosByIdRol(@PathVariable int idRol) {
        List<UsuarioEntity> usuarios = usuarioService.getUsuariosByIdRol(idRol);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene una lista de usuarios filtrados por plan de usuario.
     *
     * @param planUsuario el plan de usuario para filtrar.
     * @return una respuesta con la lista de usuarios filtrados.
     */
    @GetMapping("/planUsuario/{planUsuario}")
    public ResponseEntity<List<UsuarioEntity>> getUsuariosByPlanUsuario(@PathVariable String planUsuario) {
        List<UsuarioEntity> usuarios = usuarioService.getUsuariosByPlanUsuario(planUsuario);
        return ResponseEntity.ok(usuarios);
    }



    /**
     * Guarda un nuevo usuario en el sistema.
     *
     * @param usuario el usuario a crear.
     * @return una respuesta con el usuario creado.
     */
    @PostMapping("/save")
    public ResponseEntity<UsuarioEntity> saveUsuario(@RequestBody UsuarioEntity usuario) {
        UsuarioEntity usuarioNuevo = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(usuarioNuevo);
    }

    /**
     * Actualiza los datos de un usuario.
     * @param id El ID del usuario a actualizar.
     * @param usuario El objeto UsuarioEntity con los nuevos datos.
     * @return La respuesta HTTP.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Long id, @RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity updatedUsuario = usuarioService.updateUsuario(id, usuario);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id el identificador del usuario a eliminar.
     * @return una respuesta indicando que no hay contenido.
     * @throws Exception si ocurre un error durante la eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUsuarioById(@PathVariable Long id) throws Exception {
        var isDeleted = usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Inicia sesión de un usuario utilizando las credenciales proporcionadas.
     *
     * @param form el formulario de inicio de sesión con correo y contraseña.
     * @return un objeto Login que indica si el inicio de sesión fue exitoso.
     */
    @PostMapping("/login")
    public Login login(@RequestBody LoginForm form){
        return usuarioService.login(form);
    }
}
