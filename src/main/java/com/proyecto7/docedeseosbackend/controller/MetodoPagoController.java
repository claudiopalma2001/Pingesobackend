package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.MetodoPagoEntity;
import com.proyecto7.docedeseosbackend.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metodoPagos")
@CrossOrigin("*")

public class MetodoPagoController {
    @Autowired
    private MetodoPagoService metodoPagoService;

    /**
     * Obtiene una lista de todos los métodos de pago.
     *
     * @return una respuesta con la lista de métodos de pago.
     */
    @GetMapping("/")
    public ResponseEntity<List<MetodoPagoEntity>> listarMetodosPago() {
        List<MetodoPagoEntity> metodosPago = metodoPagoService.getMetodosPago();
        return ResponseEntity.ok(metodosPago);
    }

    /**
     * Obtiene un método de pago por su identificador.
     *
     * @param id el identificador del método de pago.
     * @return una respuesta con el método de pago encontrado.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<MetodoPagoEntity> getMetodoPagoById(@PathVariable Long id) {
        MetodoPagoEntity metodoPago = metodoPagoService.getMetodoPagoById(id);
        return ResponseEntity.ok(metodoPago);
    }

    /**
     * Busca un método de pago por su nombre.
     *
     * @param nombreMetodo el nombre del método de pago.
     * @return una respuesta con el método de pago encontrado.
     */
    @GetMapping("/nombre/{nombreMetodo}")
    public ResponseEntity<MetodoPagoEntity> getMetodoPagoByNombre(@PathVariable String nombreMetodo) {
        MetodoPagoEntity metodoPago = metodoPagoService.getMetodoPagoByNombre(nombreMetodo);
        return ResponseEntity.ok(metodoPago);
    }

    /**
     * Obtiene una lista de métodos de pago filtrados por ID de pago.
     *
     * @param idPago el ID de pago para filtrar.
     * @return una respuesta con la lista de métodos de pago filtrados.
     */
    @GetMapping("/idPago/{idPago}")
    public ResponseEntity<List<MetodoPagoEntity>> getMetodosPagoByIdPago(@PathVariable int idPago) {
        List<MetodoPagoEntity> metodosPago = metodoPagoService.getMetodosPagoByIdPago(idPago);
        return ResponseEntity.ok(metodosPago);
    }

    /**
     * Guarda un nuevo método de pago en el sistema.
     *
     * @param metodoPago el método de pago a crear.
     * @return una respuesta con el método de pago creado.
     */
    @PostMapping("/save")
    public ResponseEntity<MetodoPagoEntity> saveMetodoPago(@RequestBody MetodoPagoEntity metodoPago) {
        MetodoPagoEntity metodoPagoNuevo = metodoPagoService.saveMetodoPago(metodoPago);
        return ResponseEntity.ok(metodoPagoNuevo);
    }

    /**
     * Actualiza un método de pago existente en el sistema.
     *
     * @param metodoPago el método de pago con los datos actualizados.
     * @return una respuesta con el método de pago actualizado.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMetodoPago(@PathVariable Long id, @RequestBody MetodoPagoEntity metodoPago) {
        try {
            MetodoPagoEntity updatedMetodoPago = metodoPagoService.updateMetodoPago(id, metodoPago);
            return new ResponseEntity<>(updatedMetodoPago, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un método de pago por su identificador.
     *
     * @param id el identificador del método de pago a eliminar.
     * @return una respuesta indicando que no hay contenido.
     * @throws
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetodoPagoById(@PathVariable Long id) throws Exception {
        boolean isDeleted = metodoPagoService.deleteMetodoPago(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
