package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con los pagos.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */
@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
    /**
     * Busca un pago por su boleta.
     *
     * @param boleta la boleta del pago que se desea buscar.
     * @return el objeto PagoEntity correspondiente a la boleta, o null si no se encuentra.
     */
    PagoEntity findByBoleta(String boleta);

    /**
     * Busca una lista de pagos por el monto.
     *
     * @param monto el monto de los pagos que se desea buscar.
     * @return una lista de objetos PagoEntity que coinciden con el monto proporcionado.
     */
    List<PagoEntity> findByMonto(double monto);
}

