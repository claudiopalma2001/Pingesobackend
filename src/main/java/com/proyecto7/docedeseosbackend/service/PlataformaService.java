package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.PlataformaEntity;
import com.proyecto7.docedeseosbackend.repository.PlataformaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlataformaService {

    @Autowired
    PlataformaRepository plataformaRepository;

    /**
     * Obtiene una lista de todas las plataformas.
     *
     * @return Una lista de plataformas.
     */
    public List<PlataformaEntity> getPlataformas() {
        return (List<PlataformaEntity>) plataformaRepository.findAll();
    }

    /**
     * Obtiene una plataforma por su ID.
     *
     * @param id El ID de la plataforma que se desea obtener.
     * @return La plataforma correspondiente al ID.
     */
    public PlataformaEntity getPlataformaById(Long id) {
        return plataformaRepository.findById(id).get();
    }

    /**
     * Guarda una nueva plataforma en la base de datos.
     *
     * @param plataforma La plataforma que se desea guardar.
     * @return La plataforma guardada.
     */
    public PlataformaEntity savePlataforma(PlataformaEntity plataforma) {
        return plataformaRepository.save(plataforma);
    }

    /**
     * Elimina una plataforma por su ID.
     *
     * @param id El ID de la plataforma que se desea eliminar.
     * @return True si la eliminación fue exitosa, de lo contrario, lanza una excepción.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    public boolean deletePlataforma(Long id) throws Exception {
        try {
            plataformaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
