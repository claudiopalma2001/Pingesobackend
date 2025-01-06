package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.TematicaEntity;
import com.proyecto7.docedeseosbackend.repository.TematicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TematicaService {

    @Autowired
    private TematicaRepository tematicaRepository;

    /**
     * Obtiene una lista de todas las temáticas.
     *
     * @return una lista de entidades de temática {@link TematicaEntity}.
     */
    public List<TematicaEntity> getAllTematicas() {
        return tematicaRepository.findAll();
    }

    /**
     * Obtiene una temática específica por su ID.
     *
     * @param id el identificador de la temática a buscar.
     * @return la entidad {@link TematicaEntity} de la temática encontrada.
     */
    public Optional<TematicaEntity> getTematicaById(Long id) {
        return tematicaRepository.findById(id);
    }

    /**
     * Guarda una nueva temática usando el repositorio.
     *
     * @param tematica la entidad {@link TematicaEntity} de la temática a guardar.
     * @return la temática guardada.
     */
    public TematicaEntity save(TematicaEntity tematica) {
        return tematicaRepository.save(tematica);
    }

    /**
     * Actualiza una temática existente en la base de datos.
     *
     * @param id el ID de la temática a actualizar.
     * @param updatedData los datos actualizados de la entidad {@link TematicaEntity} de la temática.
     * @return la temática actualizada.
     * @throws Exception si la temática no se encuentra.
     */
    public TematicaEntity updateTematica(Long id, TematicaEntity updatedData) throws Exception {
        Optional<TematicaEntity> existingTematica = tematicaRepository.findById(id);
        if (existingTematica.isPresent()) {
            TematicaEntity tematicaToUpdate = existingTematica.get();

            // Actualizamos solo los campos que se deseen modificar
            tematicaToUpdate.setNombreTematica(updatedData.getNombreTematica());
            tematicaToUpdate.setDescripcion(updatedData.getDescripcion());

            return tematicaRepository.save(tematicaToUpdate);
        } else {
            throw new Exception("Temática no encontrada");
        }
    }

    /**
     * Elimina una temática por su ID.
     *
     * @param id el identificador de la temática a eliminar.
     * @return true si la temática fue eliminada correctamente, false si hubo un error.
     * @throws Exception si ocurre algún error durante la eliminación.
     */
    public boolean deleteTematica(Long id) throws Exception {
        try {
            tematicaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
