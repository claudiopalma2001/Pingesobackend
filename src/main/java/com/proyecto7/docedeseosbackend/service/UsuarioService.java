package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import com.proyecto7.docedeseosbackend.entity.forms.LoginForm;
import com.proyecto7.docedeseosbackend.repository.UsuarioRepository;
import com.proyecto7.docedeseosbackend.responses.Login;
import com.proyecto7.docedeseosbackend.utils.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private JWTMiddlewareService JWT;

    @Autowired
    private Sql2o sql2o;

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return una lista de entidades de usuario {@link UsuarioEntity}.
     */
    public List<UsuarioEntity> getUsuarios() {
        return (List<UsuarioEntity>) usuarioRepository.findAll();
    }

    /**
     * Obtiene un usuario específico por su ID.
     *
     * @param id el identificador del usuario a buscar.
     * @return la entidad {@link UsuarioEntity} del usuario encontrado.
     */
    public UsuarioEntity getUsuarioById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    /**
     * Obtiene un usuario específico por su correo electrónico.
     *
     * @param correo el correo electrónico del usuario a buscar.
     * @return la entidad {@link UsuarioEntity} del usuario encontrado.
     */
    public UsuarioEntity getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }


    public List<UsuarioEntity> getUsuariosByIdRol(int idRol) {
        return usuarioRepository.findByIdRol(idRol);
    }

    /**
     * Obtiene una lista de usuarios con una edad específica.
     *
     * @param edad la edad de los usuarios a buscar.
     * @return una lista de entidades de usuario {@link UsuarioEntity} con la edad especificada.
     */
    public List<UsuarioEntity> getUsuariosByEdad(int edad) {
        return usuarioRepository.findByEdad(edad);
    }


    /**
     * Obtiene una lista de usuarios que tienen un plan específico.
     *
     * @param planUsuario el tipo de plan de usuario a buscar.
     * @return una lista de entidades de usuario {@link UsuarioEntity} con el plan especificado.
     */
    public List<UsuarioEntity> getUsuariosByPlanUsuario(String planUsuario) {
        return usuarioRepository.findByPlanUsuario(planUsuario);
    }

    /**
     * Guarda un nuevo usuario usando el repositorio.
     *
     * @param usuario la entidad {@link UsuarioEntity} del usuario a guardar.
     * @return el usuario guardado.
     */
    public UsuarioEntity save(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param id del usuario a actualizar.
     * @param updatedData, contenido nuevo de la entidad {@link UsuarioEntity} del usuario a actualizar.
     * @return el usuario actualizado.
     */

    public UsuarioEntity updateUsuario(Long id, UsuarioEntity updatedData) throws Exception {
        Optional<UsuarioEntity> existingUsuario = usuarioRepository.findById(id);
        if (existingUsuario.isPresent()) {
            UsuarioEntity usuarioToUpdate = existingUsuario.get();

            // Actualizamos solo los campos que se deseen modificar
            usuarioToUpdate.setNombre(updatedData.getNombre());
            usuarioToUpdate.setCorreo(updatedData.getCorreo());
            usuarioToUpdate.setPassword(Encrypter.encrypt(updatedData.getPassword(), updatedData.getCorreo()));
            usuarioToUpdate.setEdad(updatedData.getEdad());
            usuarioToUpdate.setPlanUsuario(updatedData.getPlanUsuario());
            usuarioToUpdate.setIdRol(updatedData.getIdRol());

            return usuarioRepository.save(usuarioToUpdate);
        } else {
            throw new Exception("Usuario no encontrado");
        }
    }


    /**
     * Elimina un usuario por su ID.
     *
     * @param id el identificador del usuario a eliminar.
     * @return true si el usuario fue eliminado correctamente, false si hubo un error.
     * @throws Exception si ocurre algún error durante la eliminación.
     */
    public boolean deleteUsuario(Long id) throws Exception {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Inicia sesión para un usuario, verificando su correo y contraseña, y generando un token JWT.
     *
     * @param form el formulario de inicio de sesión {@link LoginForm} con el correo y la contraseña.
     * @return una instancia de {@link Login} con el estado de autenticación y el token JWT si es exitoso.
     */
    public Login login(LoginForm form) {
        UsuarioEntity usuario = usuarioRepository.findByCorreo(form.getCorreo());
        /*TODO: ver estados de inicio de sesion con los atributos de UsuarioEntity*/
        if (usuario == null) {
            return new Login(false, null);
        }
        if (!form.getPassword().equals(Encrypter.decrypt(usuario.getPassword(),usuario.getCorreo()))){
            return new Login(false, null);
        }

        String jwt = JWT.generateToken(form);
        return new Login(true, jwt);
    }

    public UsuarioEntity saveUsuario(UsuarioEntity usuario) {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO usuarios (nombre, correo, password, edad, plan_usuario, id_rol) " +
                    "VALUES (:nombre, :correo, :password, :edad, :plan_usuario, :id_rol)";
            conn.createQuery(sql)
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("correo", usuario.getCorreo())
                    .addParameter("password", Encrypter.encrypt(usuario.getPassword(),usuario.getCorreo()))
                    .addParameter("edad", usuario.getEdad())
                    .addParameter("plan_usuario", usuario.getPlanUsuario())
                    .addParameter("id_rol", usuario.getIdRol())
                    .executeUpdate();
            return usuario;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
