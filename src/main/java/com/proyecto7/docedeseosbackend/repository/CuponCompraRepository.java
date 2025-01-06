package com.proyecto7.docedeseosbackend.repository;
import com.proyecto7.docedeseosbackend.entity.CuponCompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio que maneja las operaciones de acceso a datos para la entidad 'CuponCompra'.
 * Extiende de JpaRepository, proporcionando métodos estándar para realizar operaciones CRUD
 * sobre la tabla 'cuponcompra' en la base de datos.
 *
 * La interfaz 'CuponCompraRepository' no requiere implementación, ya que JpaRepository proporciona
 * implementaciones predeterminadas de los métodos.
 */

@Repository
public interface CuponCompraRepository extends JpaRepository <CuponCompraEntity, Long>{
}
