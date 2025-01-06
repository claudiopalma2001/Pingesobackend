package com.proyecto7.docedeseosbackend.service;

import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import com.proyecto7.docedeseosbackend.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de cupones.
 * Realiza las operaciones CRUD sobre los cupones y otras funcionalidades relacionadas.
 */
@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    /**
     * Obtiene una lista de todos los cupones.
     *
     * @return una lista de entidades de cupon {@link CuponEntity}.
     */
    public List<CuponEntity> getCupones() {
        return cuponRepository.findAll();
    }

    /**
     * Obtiene un cupón específico por su ID.
     *
     * @param id el identificador del cupón a buscar.
     * @return la entidad {@link CuponEntity} del cupón encontrado.
     */
    public CuponEntity getCuponById(Long id) {
        return cuponRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene un cupón específico por su nombre.
     *
     * @param nombreCupon el nombre del cupón a buscar.
     * @return la entidad {@link CuponEntity} del cupón encontrado.
     */
    public CuponEntity getCuponByNombre(String nombreCupon) {
        return cuponRepository.findByNombreCupon(nombreCupon);
    }

    /**
     * Obtiene una lista de cupones por su tipo.
     *
     * @param tipo el tipo de cupón a buscar.
     * @return una lista de entidades de cupon {@link CuponEntity} con el tipo especificado.
     */
    public List<CuponEntity> getCuponesByTipo(String tipo) {
        return cuponRepository.findByTipo(tipo);
    }

    /**
     * Obtiene una lista de cupones por su temática.
     *
     * @param idTematica el identificador de la temática de los cupones a buscar.
     * @return una lista de entidades de cupon {@link CuponEntity} con la temática especificada.
     */
    public List<CuponEntity> getCuponesByIdTematica(int idTematica) {
        return cuponRepository.findByIdTematica(idTematica);
    }

    /**
     * Guarda un nuevo cupón usando el repositorio.
     *
     * @param cupon la entidad {@link CuponEntity} del cupón a guardar.
     * @return el cupón guardado.
     */
    public CuponEntity save(CuponEntity cupon) {
        return cuponRepository.save(cupon);
    }

    /**
     * Actualiza un cupón existente en la base de datos.
     *
     * @param id el identificador del cupón a actualizar.
     * @param updatedData el contenido nuevo de la entidad {@link CuponEntity} del cupón a actualizar.
     * @return el cupón actualizado.
     * @throws Exception si el cupón no se encuentra.
     */
    public CuponEntity updateCupon(Long id, CuponEntity updatedData) throws Exception {
        Optional<CuponEntity> existingCupon = cuponRepository.findById(id);
        if (existingCupon.isPresent()) {
            CuponEntity cuponToUpdate = existingCupon.get();

            // Actualizar solo los campos deseados
            cuponToUpdate.setNombreCupon(updatedData.getNombreCupon());
            cuponToUpdate.setTipo(updatedData.getTipo());
            cuponToUpdate.setIdTematica(updatedData.getIdTematica());
            cuponToUpdate.setPrecio(updatedData.getPrecio());

            return cuponRepository.save(cuponToUpdate);
        } else {
            throw new Exception("Cupón no encontrado");
        }
    }

    /**
     * Elimina un cupón por su ID.
     *
     * @param id el identificador del cupón a eliminar.
     * @return true si el cupón fue eliminado correctamente, false si hubo un error.
     * @throws Exception si ocurre algún error durante la eliminación.
     */
    public boolean deleteCupon(Long id) throws Exception {
        try {
            cuponRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Actualiza el precio de un cupón existente en la base de datos.
     *
     * @param id el identificador del cupón a actualizar.
     * @param nuevoPrecio el nuevo precio del cupón.
     * @return el cupón actualizado con el nuevo precio.
     * @throws Exception si el cupón no se encuentra.
     */
    public CuponEntity updatePrecio(Long id, int nuevoPrecio) throws Exception {
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        Optional<CuponEntity> existingCupon = cuponRepository.findById(id);
        if (existingCupon.isPresent()) {
            CuponEntity cuponToUpdate = existingCupon.get();
            cuponToUpdate.setPrecio(nuevoPrecio);
            return cuponRepository.save(cuponToUpdate);
        } else {
            throw new Exception("Cupón no encontrado");
        }
    }

}
