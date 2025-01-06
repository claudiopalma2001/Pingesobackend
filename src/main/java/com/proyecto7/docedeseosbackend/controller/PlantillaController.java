package com.proyecto7.docedeseosbackend.controller;

import com.proyecto7.docedeseosbackend.entity.CuponEntity;
import com.proyecto7.docedeseosbackend.entity.PlantillaEntity;
import com.proyecto7.docedeseosbackend.service.CuponService;
import com.proyecto7.docedeseosbackend.service.PlantillaService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plantillas")
@CrossOrigin("*")
public class PlantillaController {

    @Autowired
    private PlantillaService plantillaService;

    @Autowired
    private CuponService cuponService;

    @Value("${plantillas.base-path}")
    private String basePath;

    private static final Logger log = LoggerFactory.getLogger(PlantillaController.class);


    /**
     * Obtiene una lista de todas las plantillas.
     *
     * @return una respuesta con la lista de plantillas.
     */
    @GetMapping("/")
    public ResponseEntity<List<PlantillaEntity>> listarPlantillas() {
        List<PlantillaEntity> plantillas = plantillaService.getAllPlantillas();
        return ResponseEntity.ok(plantillas);
    }

    /**
     * Obtiene una plantilla específica por su identificador.
     *
     * @param id el identificador de la plantilla.
     * @return una respuesta con la plantilla encontrada o un error si no se encuentra.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<PlantillaEntity> getPlantillaPorId(@PathVariable Long id) {
        try {
            PlantillaEntity plantilla = plantillaService.getPlantillaPorId(id);
            return ResponseEntity.ok(plantilla);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cupon/{idCupon}")
    public ResponseEntity<List<PlantillaEntity>> getPlantillasByIdCupon(@PathVariable int idCupon) {
        List<PlantillaEntity> plantillas = plantillaService.getPlantillasByIdCupon(idCupon);
        return ResponseEntity.ok(plantillas);
    }

    /**
     * Guarda una nueva plantilla.
     *
     * @param plantilla la entidad PlantillaEntity a guardar.
     * @return una respuesta con la plantilla creada.
     */
    @PostMapping("/save")
    public ResponseEntity<PlantillaEntity> savePlantilla(@RequestBody PlantillaEntity plantilla) {
        PlantillaEntity nuevaPlantilla = plantillaService.savePlantilla(plantilla);
        return ResponseEntity.created(URI.create("/api/v1/plantillas/id/" + nuevaPlantilla.getId()))
                .body(nuevaPlantilla);
    }

    /**
     * Actualiza una plantilla existente.
     *
     * @param id el ID de la plantilla a actualizar.
     * @param plantilla los datos actualizados de la plantilla.
     * @return una respuesta con la plantilla actualizada o un error si no se encuentra.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PlantillaEntity> updatePlantilla(@PathVariable Long id, @RequestBody PlantillaEntity plantilla) {
        return ResponseEntity.ok(plantillaService.updatePlantilla(id, plantilla));
    }

    /**
     * Elimina una plantilla por su identificador.
     *
     * @param id el identificador de la plantilla a eliminar.
     * @return una respuesta indicando si la eliminación fue exitosa.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlantillaById(@PathVariable Long id) {
        log.info("Solicitud para eliminar plantilla con ID: {}", id);
        try {
            plantillaService.deletePlantilla(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error al eliminar la plantilla con ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }


   private static int imagenCounter = 36;

    @PostMapping("/saveWithCupon")
    public ResponseEntity<?> savePlantillaAndCupon(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("nombreCupon") String nombreCupon,
            @RequestParam("tipo") String tipo,
            @RequestParam("idTematica") int idTematica,
            @RequestParam("idIdioma") int idIdioma,
            @RequestParam("idPlataforma") int idPlataforma,
            @RequestParam("precio") int precio
    ) {
        String imagenUrl = null;
        boolean imagenGuardada = false;

        try {
            imagenUrl = saveImage(archivo, idTematica);
            imagenGuardada = true;

            CuponEntity nuevoCupon = new CuponEntity();
            nuevoCupon.setNombreCupon(nombreCupon);
            nuevoCupon.setTipo(tipo);
            nuevoCupon.setIdTematica(idTematica);
            nuevoCupon.setPrecio(precio);

            CuponEntity savedCupon = cuponService.save(nuevoCupon);

            PlantillaEntity nuevaPlantilla = new PlantillaEntity();
            nuevaPlantilla.setIdCupon(Math.toIntExact(savedCupon.getId()));
            nuevaPlantilla.setIdIdioma(idIdioma);
            nuevaPlantilla.setIdPlataforma(idPlataforma);
            nuevaPlantilla.setUrlImagen(imagenUrl);

            PlantillaEntity savedPlantilla = plantillaService.savePlantilla(nuevaPlantilla);

            imagenCounter++;
            log.info("ImagenCounter incrementado a: {}", imagenCounter);

            return ResponseEntity.ok().body("Plantilla y cupón creados exitosamente");

        } catch (IOException e) {
            if (imagenGuardada) {
                deleteImage(imagenUrl);
            }
            return ResponseEntity.status(500).body("Error al guardar la imagen");
        } catch (Exception e) {
            if (imagenGuardada) {
                deleteImage(imagenUrl);
            }
            return ResponseEntity.status(500).body("Error al guardar el cupón o la plantilla");
        }
    }

    private String saveImage(MultipartFile archivo, int tematica) throws IOException {

        // Ruta absoluta del frontend
        File frontendDir = new File(System.getProperty("user.dir")).getParentFile();
        String rutaBase = new File(frontendDir, basePath).getAbsolutePath();

        log.info("Ruta base calculada para plantillas: {}", rutaBase);

        // Carpeta "Todas"
        String rutaTodas = rutaBase + File.separator + "Todas";
        File dirTodas = new File(rutaTodas);
        if (!dirTodas.exists() && !dirTodas.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta: " + rutaTodas);
        }

        // Determinar el nombre del directorio temático
        String nombreDirectorioTematica;
        switch (tematica) {
            case 1:
                nombreDirectorioTematica = "pololos";
                break;
            case 2:
                nombreDirectorioTematica = "familiar";
                break;
            case 3:
                nombreDirectorioTematica = "infantil";
                break;
            case 4:
                nombreDirectorioTematica = "amistad";
                break;
            case 5:
                nombreDirectorioTematica = "papa";
                break;
            case 6:
                nombreDirectorioTematica = "embarazada";
                break;
            case 7:
                nombreDirectorioTematica = "personalizable";
                break;
            default:
                nombreDirectorioTematica = "extra";
                break;
        }

        // Carpeta temática
        String rutaTematica = rutaBase + File.separator + nombreDirectorioTematica;
        File dirTematica = new File(rutaTematica);
        if (!dirTematica.exists() && !dirTematica.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta para la temática: " + nombreDirectorioTematica);
        }

        // Obtener extensión del archivo
        String originalFilename = archivo.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        if (extension.isEmpty()) {
            throw new IOException("El archivo no tiene una extensión válida.");
        }

        // Generar nombre de imagen
        String nombreImagen = nombreDirectorioTematica + imagenCounter + extension;

        // Guardar archivo en la carpeta temática
        File imagenArchivoTematica = new File(dirTematica, nombreImagen);
        archivo.transferTo(imagenArchivoTematica);
        log.info("Archivo guardado en la carpeta temática: {}", imagenArchivoTematica.getAbsolutePath());

        // Copiar archivo a la carpeta "Todas"
        File imagenArchivoTodas = new File(dirTodas, nombreImagen);
        Files.copy(imagenArchivoTematica.toPath(), imagenArchivoTodas.toPath(), StandardCopyOption.REPLACE_EXISTING);
        log.info("Archivo copiado a la carpeta 'Todas': {}", imagenArchivoTodas.getAbsolutePath());

        //imagenCounter ++;

        return nombreImagen;
    }

    public void deleteImage(String imagenUrl) {
        try {
            File frontendDir = new File(System.getProperty("user.dir")).getParentFile();
            String rutaBase = new File(frontendDir, basePath).getAbsolutePath();

            log.info("Ruta base calculada para eliminar imágenes: {}", rutaBase);

            // Eliminar de la carpeta "Todas"
            String rutaTodas = rutaBase + File.separator + "Todas";
            File imagenArchivoTodas = new File(rutaTodas, imagenUrl);
            if (imagenArchivoTodas.exists()) {
                if (imagenArchivoTodas.delete()) {
                    log.info("Imagen eliminada de la carpeta 'Todas': {}", imagenArchivoTodas.getPath());
                } else {
                    log.error("No se pudo eliminar la imagen de la carpeta 'Todas': {}", imagenArchivoTodas.getPath());
                }
            } else {
                log.warn("La imagen no existe en la carpeta 'Todas': {}", imagenArchivoTodas.getPath());
            }

            // Buscar y eliminar de las carpetas temáticas
            String[] tematicas = {"pololos", "familiar", "infantil", "amistad", "papa", "embarazada", "personalizable", "extra"};
            for (String tematica : tematicas) {
                String rutaTematica = rutaBase + File.separator + tematica;
                File imagenArchivoTematica = new File(rutaTematica, imagenUrl);
                if (imagenArchivoTematica.exists()) {
                    if (imagenArchivoTematica.delete()) {
                        log.info("Imagen eliminada de la carpeta temática '{}': {}", tematica, imagenArchivoTematica.getPath());
                        break; // Salimos después de eliminar la imagen
                    } else {
                        log.error("No se pudo eliminar la imagen de la carpeta temática '{}': {}", tematica, imagenArchivoTematica.getPath());
                    }
                } else {
                    log.warn("La imagen no existe en la carpeta temática '{}': {}", tematica, imagenArchivoTematica.getPath());
                }
            }
        } catch (Exception e) {
            log.error("Error al eliminar la imagen: {}", e.getMessage());
        }
    }


    @PostConstruct
    private void inicializarImagenCounter() {
        try {
            File frontendDir = new File(System.getProperty("user.dir")).getParentFile();
            String rutaBase = new File(frontendDir, basePath).getAbsolutePath();
            File carpetaTodas = new File(rutaBase + File.separator + "Todas");

            log.info("Iniciando inicialización de imagenCounter...");
            if (carpetaTodas.exists() && carpetaTodas.isDirectory()) {
                File[] archivos = carpetaTodas.listFiles((dir, name) -> name.matches(".*\\d+\\.\\w+"));
                log.info("Archivos encontrados en 'Todas': {}", archivos != null ? archivos.length : 0);

                if (archivos != null && archivos.length > 0) {
                    int maxNumero = 35; // Ignorar las primeras 84 plantillas
                    for (File archivo : archivos) {
                        String nombre = archivo.getName();
                        log.info("Evaluando archivo: {}", nombre);
                        String numeroStr = nombre.replaceAll("\\D+", "");
                        if (!numeroStr.isEmpty()) {
                            int numero = Integer.parseInt(numeroStr);
                            if (numero > 35) { // Solo considerar números mayores a 35
                                maxNumero = Math.max(maxNumero, numero);
                            }
                        }
                    }
                    imagenCounter = maxNumero + 1;
                } else {
                    imagenCounter = 36; // Si no hay archivos adicionales, comenzar en 36
                }
            } else {
                imagenCounter = 36; // Si no existe la carpeta "Todas", comenzar en 36
            }
            log.info("ImagenCounter inicializado a: {}", imagenCounter);
        } catch (Exception e) {
            log.error("Error al inicializar imagenCounter: {}", e.getMessage());
            imagenCounter = 36; // Valor por defecto en caso de error
        }
    }
}


