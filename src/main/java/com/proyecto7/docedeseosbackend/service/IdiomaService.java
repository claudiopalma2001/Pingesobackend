package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.IdiomaEntity;
import com.proyecto7.docedeseosbackend.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de idiomas.
 * Realiza las operaciones CRUD sobre los cupones y otras funcionalidades relacionadas.
 */
@Service
public class IdiomaService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    /**
     * Obtiene una lista de todos los idiomas disponibles.
     *
     * @return una lista de entidades de idioma {@link IdiomaEntity}.
     */
    public List<IdiomaEntity> getIdiomas() {
        return idiomaRepository.findAll();
    }

    /**
     * Obtiene un idioma específico por su ID.
     *
     * @param id el identificador del idioma a buscar.
     * @return la entidad {@link IdiomaEntity} del idioma encontrado, o null si no se encuentra.
     */
    public IdiomaEntity getIdiomaById(Long id) {
        Optional<IdiomaEntity> idioma = idiomaRepository.findById(id);
        return idioma.orElse(null);
    }

    /**
     * Obtiene un idioma específico por su nombre.
     *
     * @param nombreIdioma el nombre del idioma a buscar.
     * @return la entidad {@link IdiomaEntity} del idioma encontrado, o null si no se encuentra.
     */
    public IdiomaEntity getIdiomaByNombre(String nombreIdioma) {
        return idiomaRepository.findByNombreIdioma(nombreIdioma);
    }

    /**
     * Guarda un nuevo idioma usando el repositorio.
     *
     * @param idioma la entidad {@link IdiomaEntity} del idioma a guardar.
     * @return el idioma guardado.
     */
    public IdiomaEntity saveIdioma(IdiomaEntity idioma) {
        return idiomaRepository.save(idioma);
    }

    /**
     * Actualiza un idioma existente en la base de datos.
     *
     * @param id el ID del idioma a actualizar.
     * @param updatedData el contenido actualizado de la entidad {@link IdiomaEntity}.
     * @return el idioma actualizado.
     * @throws Exception si el idioma no se encuentra.
     */
    public IdiomaEntity updateIdioma(Long id, IdiomaEntity updatedData) throws Exception {
        Optional<IdiomaEntity> existingIdioma = idiomaRepository.findById(id);
        if (existingIdioma.isPresent()) {
            IdiomaEntity idiomaToUpdate = existingIdioma.get();
            idiomaToUpdate.setNombreIdioma(updatedData.getNombreIdioma());
            return idiomaRepository.save(idiomaToUpdate);
        } else {
            throw new Exception("Idioma no encontrado");
        }
    }

    /**
     * Elimina un idioma por su ID.
     *
     * @param id el identificador del idioma a eliminar.
     * @return true si el idioma fue eliminado correctamente, false si hubo un error.
     * @throws Exception si ocurre algún error durante la eliminación.
     */
    public boolean deleteIdioma(Long id) throws Exception {
        try {
            idiomaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
