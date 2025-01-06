package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.PlataformaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio que maneja las operaciones de acceso a datos para la entidad 'Plataforma'.
 * Extiende de JpaRepository, proporcionando métodos estándar para realizar operaciones CRUD
 * sobre la tabla 'plataforma' en la base de datos.
 *
 * La interfaz 'PlataformaRepository' no requiere implementación, ya que JpaRepository proporciona
 * implementaciones predeterminadas de los métodos.
 */

@Repository
public interface PlataformaRepository extends JpaRepository <PlataformaEntity, Long>{
}
