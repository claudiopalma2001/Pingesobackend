package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.MetodoPagoEntity;
import com.proyecto7.docedeseosbackend.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con los métodos de pago.
 */
@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    /**
     * Obtiene una lista de todos los métodos de pago.
     *
     * @return una lista de entidades de método de pago {@link MetodoPagoEntity}.
     */
    public List<MetodoPagoEntity> getMetodosPago() {
        return metodoPagoRepository.findAll();
    }

    /**
     * Obtiene un método de pago específico por su ID.
     *
     * @param id el identificador del método de pago a buscar.
     * @return la entidad {@link MetodoPagoEntity} del método de pago encontrado.
     */
    public MetodoPagoEntity getMetodoPagoById(Long id) {
        return metodoPagoRepository.findById(id).orElse(null);
    }

    /**
     * Busca un método de pago por su nombre.
     *
     * @param nombreMetodo el nombre del método de pago a buscar.
     * @return la entidad {@link MetodoPagoEntity} correspondiente, o null si no se encuentra.
     */
    public MetodoPagoEntity getMetodoPagoByNombre(String nombreMetodo) {
        return metodoPagoRepository.findByNombreMetodo(nombreMetodo);
    }

    /**
     * Busca una lista de métodos de pago por su ID de pago.
     *
     * @param idPago el ID de pago de los métodos que se desea buscar.
     * @return una lista de objetos MetodoPagoEntity que coinciden con el ID de pago proporcionado.
     */
    public List<MetodoPagoEntity> getMetodosPagoByIdPago(int idPago) {
        return metodoPagoRepository.findByIdPago(idPago);
    }

    /**
     * Guarda un nuevo método de pago en la base de datos.
     *
     * @param metodoPago la entidad {@link MetodoPagoEntity} del método de pago a guardar.
     * @return el método de pago guardado.
     */
    public MetodoPagoEntity saveMetodoPago(MetodoPagoEntity metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    /**
     * Actualiza un método de pago existente en la base de datos.
     *
     * @param id la entidad {@link MetodoPagoEntity} del método de pago a actualizar.
     * @param updatedData la entidad a actualizarse
     * @return el método de pago actualizado.
     */
    public MetodoPagoEntity updateMetodoPago(Long id, MetodoPagoEntity updatedData) throws Exception {
        Optional<MetodoPagoEntity> existingMetodoPago = metodoPagoRepository.findById(id);

        if (existingMetodoPago.isPresent()) {
            MetodoPagoEntity metodoPagoToUpdate = existingMetodoPago.get();

            // Actualizamos solo los campos que se deseen modificar
            metodoPagoToUpdate.setNombreMetodo(updatedData.getNombreMetodo());
            metodoPagoToUpdate.setIdPago(updatedData.getIdPago());

            return metodoPagoRepository.save(metodoPagoToUpdate);
        } else {
            throw new Exception("Metodo de pago no encontrado");
        }
    }

    /**
     * Elimina un método de pago por su ID.
     *
     * @param id el identificador del método de pago a eliminar.
     * @return true si el método de pago fue eliminado correctamente, false si hubo un error.
     */
    public boolean deleteMetodoPago(Long id) {
        try {
            metodoPagoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

