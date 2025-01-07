package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la conexión con la base de datos.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad 'Compra'.
 */
@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, Long> {
    /**
     * Método que obtiene todas las compras asociadas a un usuario mediante su ID.
     * @param id ID del usuario.
     * @return Lista de compras realizadas por el usuario.
     */
    List<CompraEntity> findByIdUsuario(Long id);


}
