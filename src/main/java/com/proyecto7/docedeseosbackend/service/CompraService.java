package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.CompraEntity;
import com.proyecto7.docedeseosbackend.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    /**
     * Método para encontrar todas las compras en el sistema
     * @return
     */

    public List<CompraEntity> getAllCompras(){
        return compraRepository.findAll();
    }

    /**
     * Método para encontras todas las compras efectuadas por un usuario
     * @param userId
     * @return
     */
    public List<CompraEntity> getAllComprasByUserId(Long userId){
        return compraRepository.findByIdUsuario(userId);
    }

    public CompraEntity getCompraById(long id) {
        return compraRepository.findById(id).get();
    }

    /**
     * Método para guardar una compra en la base de datos
     * @param compra
     * @return
     */

    public CompraEntity save(CompraEntity compra){
        return compraRepository.save(compra);
    }

    /**
     * Método que elimina una compra del sistema a partir de su id
     * @param id
     * @return
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
     * Método par actualizar Los datos de una compra
     * @param updatedCompra
     * @return Compra actualizada
     */

    /*public CompraEntity updateCompra(CompraEntity compra){
        return compraRepository.save(compra);
    }*/
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

