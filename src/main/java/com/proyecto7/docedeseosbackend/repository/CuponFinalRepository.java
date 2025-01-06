package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio que maneja las operaciones de acceso a datos para la entidad 'CuponFinal'.
 * Extiende de JpaRepository, proporcionando métodos estándar para realizar operaciones CRUD
 * sobre la tabla 'cuponfinal' en la base de datos.
 *
 * Además de los métodos CRUD proporcionados por JpaRepository, incluye métodos personalizados
 * para obtener cupones finales basados en diferentes criterios de búsqueda:
 * - Buscar cupones finales por 'idCupon'.
 * - Buscar cupones finales por 'idUsuario'.
 * - Buscar cupones finales por 'idPlantilla'.
 */

@Repository
public interface CuponFinalRepository extends JpaRepository <CuponFinalEntity, Long>{

    /**
     * Método para obtener una lista de cupones finales por el 'idCupon'.
     *
     * @param idCupon El identificador del cupón.
     * @return Una lista de cupones finales que coinciden con el 'idCupon' proporcionado.
     */
    public List<CuponFinalEntity> findByIdCupon(Long idCupon);

    /**
     * Método para obtener una lista de cupones finales por el 'idUsuario'.
     *
     * @param idUsuario El identificador del usuario.
     * @return Una lista de cupones finales que coinciden con el 'idUsuario' proporcionado.
     */
    public List<CuponFinalEntity> findByIdUsuario(Long idUsuario);

    /**
     * Método para obtener una lista de cupones finales por el 'idPlantilla'.
     *
     * @param idPlantilla El identificador de la plantilla.
     * @return Una lista de cupones finales que coinciden con el 'idPlantilla' proporcionado.
     */
    public List<CuponFinalEntity> findByIdPlantilla(Long idPlantilla);
}

