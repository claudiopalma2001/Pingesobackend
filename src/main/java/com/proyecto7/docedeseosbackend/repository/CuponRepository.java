package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con los cupones.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */

@Repository
public interface CuponRepository extends JpaRepository<CuponEntity, Long> {

    /**
     * Busca un cupón por su nombre.
     *
     * @param nombreCupon el nombre del cupón que se desea buscar.
     * @return el objeto CuponEntity correspondiente al nombre, o null si no se encuentra.
     */
    public CuponEntity findByNombreCupon(String nombreCupon);

    /**
     * Busca una lista de cupones por su tipo.
     *
     * @param tipo el tipo del cupón que se desea buscar.
     * @return una lista de objetos CuponEntity que coinciden con el tipo proporcionado.
     */
    public List<CuponEntity> findByTipo(String tipo);

    /**
     * Busca una lista de cupones por su temática.
     *
     * @param idTematica el id de la temática de los cupones que se desea buscar.
     * @return una lista de objetos CuponEntity que coinciden con la temática proporcionada.
     */
    public List<CuponEntity> findByIdTematica(int idTematica);

}
