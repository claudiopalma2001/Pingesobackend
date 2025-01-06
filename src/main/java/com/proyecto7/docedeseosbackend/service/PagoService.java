package com.proyecto7.docedeseosbackend.service;
import com.proyecto7.docedeseosbackend.entity.PagoEntity;
import com.proyecto7.docedeseosbackend.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    /**
     * Obtiene una lista de todos los pagos.
     *
     * @return una lista de entidades de pago {@link PagoEntity}.
     */
    public List<PagoEntity> getPagos() {
        return pagoRepository.findAll();
    }

    /**
     * Obtiene un pago específico por su ID.
     *
     * @param id el identificador del pago a buscar.
     * @return un Optional con la entidad {@link PagoEntity} del pago encontrado.
     */
    public Optional<PagoEntity> getPagoById(Long id) {
        return pagoRepository.findById(id);
    }

    /**
     * Guarda un nuevo pago en la base de datos.
     *
     * @param pago la entidad {@link PagoEntity} del pago a guardar.
     * @return el pago guardado.
     */
    public PagoEntity savePago(PagoEntity pago) {
        return pagoRepository.save(pago);
    }

    /**
     * Actualiza un pago existente en la base de datos.
     *
     * @param pago la entidad {@link PagoEntity} del pago a actualizar.
     * @param id del objeto a actualizar
     * @return el pago actualizado.
     */
    public PagoEntity updatePago(Long id, PagoEntity pago) throws Exception {
        Optional<PagoEntity> existingPago = pagoRepository.findById(id);

        if (existingPago.isPresent()) {
            PagoEntity updatedPago = existingPago.get();
            updatedPago.setMonto(pago.getMonto());
            updatedPago.setBoleta(pago.getBoleta());

            return pagoRepository.save(updatedPago);
        } else {
            throw new Exception("Pago no encontrado");
        }
    }

    /**
     * Elimina un pago por su ID.
     *
     * @param  id el identificador del pago a eliminar.
     * @return true si el pago fue eliminado correctamente, false si hubo un error.
     * @throws Exception si ocurre algún error durante la eliminación.
     */
    public boolean deletePago(Long id) throws Exception {
        try {
            pagoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Busca un pago por su boleta.
     *
     * @param boleta la boleta del pago que se desea buscar.
     * @return el objeto PagoEntity correspondiente a la boleta, o null si no se encuentra.
     */
    public PagoEntity getPagoByBoleta(String boleta) {
        return pagoRepository.findByBoleta(boleta);
    }

    /**
     * Busca una lista de pagos por monto.
     *
     * @param monto el monto de los pagos que se desea buscar.
     * @return una lista de objetos PagoEntity que coinciden con el monto proporcionado.
     */
    public List<PagoEntity> getPagosByMonto(double monto) {
        return pagoRepository.findByMonto(monto);
    }
}

