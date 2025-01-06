package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.CuponCompraEntity;
import com.proyecto7.docedeseosbackend.repository.CuponCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuponCompraService {

    @Autowired
    CuponCompraRepository cuponCompraRepository;

    /**
     * Obtiene una lista de todos los cupones de compra.
     *
     * @return Una lista de cupones de compra.
     */
    public List<CuponCompraEntity> getCuponesCompra() {
        return (List<CuponCompraEntity>) cuponCompraRepository.findAll();
    }

    /**
     * Obtiene un cupon de compra por su ID.
     *
     * @param id El ID del cupon de compra que se desea obtener.
     * @return El cupon de compra correspondiente al ID.
     */
    public CuponCompraEntity getCuponCompraById(Long id) {
        return cuponCompraRepository.findById(id).get();
    }

    /**
     * Guarda un nuevo cupon de compra en la base de datos.
     *
     * @param cuponCompra El cupon de compra que se desea guardar.
     * @return El cupon de compra guardado.
     */
    public CuponCompraEntity saveCuponCompra(CuponCompraEntity cuponCompra) {
        return cuponCompraRepository.save(cuponCompra);
    }

    /**
     * Elimina un cupon de compra por su ID.
     *
     * @param id El ID del cupon de compra que se desea eliminar.
     * @return True si la eliminación fue exitosa, de lo contrario, lanza una excepción.
     * @throws Exception Si ocurre un error durante la eliminación.
     */
    public boolean deleteCuponCompra(Long id) throws Exception {
        try {
            cuponCompraRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
