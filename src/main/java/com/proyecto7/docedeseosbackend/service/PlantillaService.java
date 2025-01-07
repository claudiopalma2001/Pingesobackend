package com.proyecto7.docedeseosbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import com.proyecto7.docedeseosbackend.repository.PlantillaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Servicio para gestionar las operaciones relacionadas con las plantillas.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad 'Plantilla'.
 */
@Service
public class PlantillaService {

    @Autowired
    private PlantillaRepository plantillaRepository;

    private static final Logger log = LoggerFactory.getLogger(PlantillaService.class);

    /**
     * Obtiene todas las plantillas.
     *
     * @return una lista con todas las entidades {@link PlantillaEntity}.
     */
    public List<PlantillaEntity> getAllPlantillas() {
        return plantillaRepository.findAll();
    }

    /**
     * Obtiene una plantilla por su ID.
     *
     * @param id el identificador de la plantilla a buscar.
     * @return la entidad {@link PlantillaEntity} de la plantilla encontrada.
     */
    public PlantillaEntity getPlantillaPorId(Long id) {
        Optional<PlantillaEntity> plantilla = plantillaRepository.findById(id);
        return plantilla.orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));
    }

    /**
     * Obtiene plantillas por el ID del cupón.
     *
     * @param idCupon el identificador del cupón asociado.
     * @return una lista de plantillas que coinciden con el ID del cupón proporcionado.
     */
    public List<PlantillaEntity> getPlantillasByIdCupon(int idCupon) {
        return plantillaRepository.findByIdCupon(idCupon);
    }

    /**
     * Guarda una nueva plantilla.
     *
     * @param plantilla la entidad {@link PlantillaEntity} a guardar.
     * @return la plantilla guardada.
     */
    public PlantillaEntity savePlantilla(PlantillaEntity plantilla) {
        return plantillaRepository.save(plantilla);
    }

    /**
     * Actualiza una plantilla existente.
     *
     * @param id el identificador de la plantilla a actualizar.
     * @param plantillaActualizada la entidad {@link PlantillaEntity} con los nuevos datos.
     * @return la plantilla actualizada.
     */
    public PlantillaEntity updatePlantilla(Long id, PlantillaEntity plantillaActualizada) {
        Optional<PlantillaEntity> plantillaExistente = plantillaRepository.findById(id);
        if (plantillaExistente.isPresent()) {
            PlantillaEntity plantilla = plantillaExistente.get();
            plantilla.setIdCupon(plantillaActualizada.getIdCupon());
            plantilla.setIdIdioma(plantillaActualizada.getIdIdioma());
            plantilla.setIdPlataforma(plantillaActualizada.getIdPlataforma());
            plantilla.setUrlImagen(plantillaActualizada.getUrlImagen());
            return plantillaRepository.save(plantilla);
        } else {
            throw new RuntimeException("Plantilla no encontrada");
        }
    }

    /**
     * Elimina una plantilla.
     *
     * @param id el identificador de la plantilla a eliminar.
     */
    public void deletePlantilla(Long id) {
        if (plantillaRepository.existsById(id)) {
            plantillaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Plantilla no encontrada");
        }
    }

    /**
     * Elimina todas las plantillas asociadas a un ID de cupón.
     *
     * @param idCupon el identificador del cupón cuyas plantillas se deben eliminar.
     */
    public void deletePlantillasByIdCupon(int idCupon) {
        List<PlantillaEntity> plantillas = plantillaRepository.findByIdCupon(idCupon);
        plantillaRepository.deleteAll(plantillas);
    }


}
