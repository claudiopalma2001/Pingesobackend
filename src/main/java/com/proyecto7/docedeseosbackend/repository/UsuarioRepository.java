package com.proyecto7.docedeseosbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto7.docedeseosbackend.entity.UsuarioEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con los usuarios.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */

@Repository
public interface UsuarioRepository extends JpaRepository <UsuarioEntity, Long>{

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo el correo electrónico del usuario que se desea buscar.
     * @return el objeto UsuarioEntity correspondiente al correo, o null si no se encuentra.
     */
    public UsuarioEntity findByCorreo(String correo);

    /**
     * Busca una lista de usuarios por su edad.
     *
     * @param edad la edad de los usuarios que se desea buscar.
     * @return una lista de objetos UsuarioEntity que coinciden con la edad proporcionada.
     */
    public List<UsuarioEntity> findByEdad(int edad);

    /**
     * Busca una lista de usuarios por su plan de usuario.
     *
     * @param planUsuario el nombre del plan de usuario que se desea buscar.
     * @return una lista de objetos UsuarioEntity que coinciden con el plan de usuario proporcionado.
     */
    public List<UsuarioEntity> findByPlanUsuario(String planUsuario);

    public List<UsuarioEntity> findByIdRol(int idRol);


}
