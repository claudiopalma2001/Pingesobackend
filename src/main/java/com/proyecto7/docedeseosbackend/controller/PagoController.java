package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.PagoEntity;
import com.proyecto7.docedeseosbackend.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pagos")
@CrossOrigin("*")
public class PagoController {

    @Autowired
    PagoService pagoService;

    /**
     * Obtiene una lista de todos los pagos.
     *
     * @return una respuesta con la lista de pagos.
     */
    @GetMapping("/")
    public ResponseEntity<List<PagoEntity>> listarPagos() {
        List<PagoEntity> pagos = pagoService.getPagos();
        return ResponseEntity.ok(pagos);
    }

    /**
     * Obtiene un pago por su identificador.
     *
     * @param id el identificador del pago.
     * @return una respuesta con el pago encontrado.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<PagoEntity> getPagoById(@PathVariable Long id) {
        Optional<PagoEntity> pago = pagoService.getPagoById(id);
        return pago.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda un nuevo pago en el sistema.
     *
     * @param pago el pago a crear.
     * @return una respuesta con el pago creado.
     */
    @PostMapping("/save")
    public ResponseEntity<PagoEntity> savePago(@RequestBody PagoEntity pago) {
        PagoEntity nuevoPago = pagoService.savePago(pago);
        return ResponseEntity.ok(nuevoPago);
    }

    /**
     * Actualiza los datos de un pago.
     * @param id El ID del pago a actualizar.
     * @param pago El objeto PagoEntity con los nuevos datos.
     * @return La respuesta HTTP.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePago(@PathVariable Long id, @RequestBody PagoEntity pago) {
        try {
            // Llamamos al servicio para actualizar el pago
            PagoEntity updatedPago = pagoService.updatePago(id, pago);
            return new ResponseEntity<>(updatedPago, HttpStatus.OK);
        } catch (Exception e) {
            // Si ocurre un error (por ejemplo, si no se encuentra el pago)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un pago por su identificador.
     *
     * @param id el identificador del pago a eliminar.
     * @return una respuesta indicando que no hay contenido.
     * @throws Exception si ocurre un error durante la eliminación.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePagoById(@PathVariable Long id) throws Exception {
        var isDeleted = pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca un pago por su boleta.
     *
     * @param boleta la boleta del pago que se desea buscar.
     * @return una respuesta con el pago encontrado.
     */
    @GetMapping("/boleta/{boleta}")
    public ResponseEntity<PagoEntity> getPagoByBoleta(@PathVariable String boleta) {
        PagoEntity pago = pagoService.getPagoByBoleta(boleta);
        return pago != null ? ResponseEntity.ok(pago) : ResponseEntity.notFound().build();
    }

    /**
     * Busca una lista de pagos por monto.
     *
     * @param monto el monto de los pagos que se desea buscar.
     * @return una respuesta con la lista de pagos encontrados.
     */
    @GetMapping("/monto/{monto}")
    public ResponseEntity<List<PagoEntity>> getPagosByMonto(@PathVariable double monto) {
        List<PagoEntity> pagos = pagoService.getPagosByMonto(monto);
        return ResponseEntity.ok(pagos);
    }

}
