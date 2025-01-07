package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import com.proyecto7.docedeseosbackend.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para gestionar las operaciones relacionadas con las compras.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad 'Compra'.
 */
@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    /**
     * Obtiene todas las compras registradas en el sistema.
     * @return Lista de todas las compras.
     */
    public List<CompraEntity> getAllCompras(){
        return compraRepository.findAll();
    }

    /**
     * Obtiene todas las compras realizadas por un usuario específico.
     * @param userId ID del usuario.
     * @return Lista de compras efectuadas por el usuario.
     */
    public List<CompraEntity> getAllComprasByUserId(Long userId){
        return compraRepository.findByIdUsuario(userId);
    }

    /**
     * Obtiene una compra específica por su ID.
     * @param id ID de la compra.
     * @return La compra correspondiente al ID proporcionado.
     */
    public CompraEntity getCompraById(long id) {
        return compraRepository.findById(id).get();
    }

    /**
     * Guarda una nueva compra en la base de datos.
     * @param compra Objeto de la compra a guardar.
     * @return compra guardada.
     */
    public CompraEntity save(CompraEntity compra){
        return compraRepository.save(compra);
    }

    /**
     * Elimina una compra del sistema utilizando su ID.
     * @param id ID de la compra a eliminar.
     * @return true si la compra fue eliminada con éxito, false en caso contrario.
     */
    public boolean deleteCompra(Long id){
        try {
            compraRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    /**
     * Actualiza los datos de una compra existente.
     * @param updatedCompra Objeto con los datos actualizados.
     * @return Compra actualizada.
     */
    public CompraEntity updateCompra(CompraEntity updatedCompra) {
        CompraEntity existingCompra = compraRepository.findById(updatedCompra.getId())
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + updatedCompra.getId()));

        // Actualiza los campos necesarios
        existingCompra.setMontoTotal(updatedCompra.getMontoTotal());
        existingCompra.setFechaCompra(updatedCompra.getFechaCompra());
        existingCompra.setCuponesFinales(updatedCompra.getCuponesFinales());

        // Guarda la compra actualizada
        return compraRepository.save(existingCompra);
    }
}

