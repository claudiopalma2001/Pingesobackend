package com.proyecto7.docedeseosbackend.repository;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la conexion con la BD
 * */
@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, Long> {
    /**
     *Método que obtiene todas las compras a partir de un user_id
     * */
    List<CompraEntity> findByIdUsuario(Long id);


}
