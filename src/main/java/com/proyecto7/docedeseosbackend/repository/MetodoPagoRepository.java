package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.MetodoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la gestión de operaciones CRUD relacionadas con los métodos de pago.
 * Extiende JpaRepository para aprovechar las operaciones de acceso a datos estándar.
 */
@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPagoEntity, Long> {
    /**
     * Busca un método de pago por su nombre.
     *
     * @param nombreMetodo el nombre del método de pago que se desea buscar.
     * @return el objeto MetodoPagoEntity correspondiente al nombre, o null si no se encuentra.
     */
    public MetodoPagoEntity findByNombreMetodo(String nombreMetodo);

    /**
     * Busca una lista de métodos de pago por su ID de pago.
     *
     * @param idPago el ID de pago de los métodos que se desea buscar.
     * @return una lista de objetos MetodoPagoEntity que coinciden con el ID de pago proporcionado.
     */
    public List<MetodoPagoEntity> findByIdPago(int idPago);
}

