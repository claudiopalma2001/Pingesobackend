package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con las plantillas.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */
@Repository
public interface PlantillaRepository extends JpaRepository<PlantillaEntity, Long> {

    /**
     * Encuentra plantillas por el ID de cupón.
     *
     * @param idCupon el identificador del cupón.
     * @return una lista de plantillas que coinciden con el ID de cupón dado.
     */
    List<PlantillaEntity> findByIdCupon(int idCupon);

    /**
     * Encuentra plantillas por el ID de idioma.
     *
     * @param idIdioma el identificador del idioma.
     * @return una lista de plantillas que coinciden con el ID de idioma dado.
     */
    List<PlantillaEntity> findByIdIdioma(int idIdioma);


    /**
     * Encuentra plantillas por el ID de plataforma.
     *
     * @param idPlataforma el identificador de la plataforma.
     * @return una lista de plantillas que coinciden con el ID de plataforma dado.
     */
    List<PlantillaEntity> findByIdPlataforma(int idPlataforma);

    /**
     * Encuentra plantillas por la URL de la imagen.
     *
     * @param url_Imagen la URL de la imagen.
     * @return una lista de plantillas que coinciden con la URL de imagen dada.
     */
    List<PlantillaEntity> findByUrlImagen(String url_Imagen);

    @Query("SELECT p.urlImagen FROM PlantillaEntity p")
    List<String> obtenerUrlsDeImagenes();
}
