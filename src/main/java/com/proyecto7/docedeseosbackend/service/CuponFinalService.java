package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.CuponFinalEntity;
import com.proyecto7.docedeseosbackend.repository.CuponFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuponFinalService {

    @Autowired
    CuponFinalRepository cuponFinalRepository;

    /**
     * Obtiene una lista de todos los cupones finales.
     *
     * @return Una lista de cupones finales.
     */
    public List<CuponFinalEntity> getCuponesFinales() {
        return (List<CuponFinalEntity>) cuponFinalRepository.findAll();
    }

    /**
     * Obtiene un cupon final por su ID.
     *
     * @param id El ID del cupon final que se desea obtener.
     * @return El cupon final correspondiente al ID.
     */
    public CuponFinalEntity getCuponFinalById(Long id) {
        return cuponFinalRepository.findById(id).get();
    }

    /**
     * Obtiene una lista de cupones finales asociados a un cupon específico.
     *
     * @param idCupon El ID del cupon asociado a los cupones finales.
     * @return Una lista de cupones finales asociados al cupon.
     */
    public List<CuponFinalEntity> getCuponesFinalesByIdCupon(Long idCupon) {
        return cuponFinalRepository.findByIdCupon(idCupon);
    }

    /**
     * Obtiene una lista de cupones finales asociados a un usuario específico.
     *
     * @param idUsuario El ID del usuario asociado a los cupones finales.
     * @return Una lista de cupones finales asociados al usuario.
     */
    public List<CuponFinalEntity> getCuponesFinalesByIdUsuario(Long idUsuario) {
        return cuponFinalRepository.findByIdUsuario(idUsuario);
    }

    /**
     * Obtiene una lista de cupones finales asociados a una plantilla específica.
     *
     * @param idPlantilla El ID de la plantilla asociada a los cupones finales.
     * @return Una lista de cupones finales asociados a la plantilla.
     */
    public List<CuponFinalEntity> getCuponesFinalesByIdPlantilla(Long idPlantilla) {
        return cuponFinalRepository.findByIdPlantilla(idPlantilla);
    }

    /**
     * Guarda un nuevo cupon final en la base de datos.
     *
     * @param cuponFinal El cupon final que se desea guardar.
     * @return El cupon final guardado.
     */
    public CuponFinalEntity saveCuponFinal(CuponFinalEntity cuponFinal) {
        return cuponFinalRepository.save(cuponFinal);
    }

    /**
     * Guarda una lista de cupones finales en la base de datos.
     *
     * @param cuponesFinales Lista de objetos {@link CuponFinalEntity} a guardar.
     * @return Lista de cupones finales guardados.
     */
    public List<CuponFinalEntity> saveAllCuponesFinales(List<CuponFinalEntity> cuponesFinales) {
        return cuponFinalRepository.saveAll(cuponesFinales);
    }

    /**
     * Actualiza los datos de un cupon final existente.
     *
     * @param id El ID del cupon final que se desea actualizar.
     * @param updatedData Los nuevos datos del cupon final.
     * @return El cupon final actualizado.
     * @throws Exception Si el cupon final no se encuentra.
     */
    public CuponFinalEntity updateCuponFinal(Long id, CuponFinalEntity updatedData) throws Exception {
        Optional<CuponFinalEntity> existingCupon = cuponFinalRepository.findById(id);
        if (existingCupon.isPresent()) {
            CuponFinalEntity cuponToUpdate = existingCupon.get();

            cuponToUpdate.setCampoDe(updatedData.getCampoDe());
            cuponToUpdate.setCampoPara(updatedData.getCampoPara());
            cuponToUpdate.setCampoIncluye(updatedData.getCampoIncluye());
            cuponToUpdate.setFecha(updatedData.getFecha());
            cuponToUpdate.setPrecioF(updatedData.getPrecioF());

            return cuponFinalRepository.save(cuponToUpdate);
        } else {
            throw new Exception("Cupon no encontrado");
        }
    }

    /**
     * Elimina un cupon final por su ID.
     *
     * @param id El ID del cupon final que se desea eliminar.
     * @return True si la eliminación fue exitosa, de lo contrario, lanza una excepción.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    public boolean deleteCuponFinal(Long id) throws Exception {
        try {
            cuponFinalRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public CuponFinalEntity updatePrecio(Long id, int nuevoPrecioF) throws Exception {
        Optional<CuponFinalEntity> existingCupon = cuponFinalRepository.findById(id);
        if (existingCupon.isPresent()) {
            CuponFinalEntity cuponToUpdate = existingCupon.get();
            cuponToUpdate.setPrecioF(nuevoPrecioF); // Actualiza solo el precio
            return cuponFinalRepository.save(cuponToUpdate); // Guarda los cambios
        } else {
            throw new Exception("Cupón no encontrado");
        }
    }

    public List<CuponFinalEntity> getCuponesFinalesByIds(List<Long> ids) {
        return cuponFinalRepository.findAllById(ids);
    }


}
