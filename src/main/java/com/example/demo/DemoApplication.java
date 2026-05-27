package com.example.demo;

import java.util.List;
import java.util.TimeZone;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import com.example.demo.BranchAndBound_EldenRing.repo.ArmorRepository;
import com.example.demo.Dijkstra_EldenRing.model.mapa.SitioDeGraciaEntity;
import com.example.demo.Dijkstra_EldenRing.repo.SitioDeGraciaRepository;
import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import com.example.demo.QuickSort_Minecraft.repo.ItemRepository;

@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @PostConstruct
    public void init() {
        // Fuerza a la JVM a usar UTC, solucionando el error FATAL de la zona horaria en PostgreSQL
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info(">>> Zona horaria de la aplicación forzada a UTC para evitar conflictos con PostgreSQL.");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepository,
                                   SitioDeGraciaRepository sitioDeGraciaRepository,
                                   ArmorRepository armorRepository) {
        return args -> {

            // =================================================================
            // --- 1. SEEDER INVENTARIO (QUICKSORT - MINECRAFT) ---
            // =================================================================
            itemRepository.deleteAll(); 

            List<ItemEntity> itemsToLoad = List.of(
                    new ItemEntity("Redstone", 64, "Recurso"),
                    new ItemEntity("Pico de Diamante", 1, "Herramienta"),
                    new ItemEntity("Pollo Cocido", 32, "Comida"),
                    new ItemEntity("Tierra", 16, "Bloque"),
                    new ItemEntity("Lingote de Hierro", 21, "Recurso")
            );
            
            itemRepository.saveAll(itemsToLoad);
            log.info(">>> ¡Inventario inicializado en PostgreSQL (Minecraft - QuickSort)!");


            // =================================================================
            // --- 2. SEEDER MAPA (DIJKSTRA - ELDEN RING) ---
            // =================================================================
            // Limpiamos primero el repositorio de sitios de gracia
            sitioDeGraciaRepository.deleteAll();

            // Instanciamos los 10 nodos para el grafo de Dijkstra
            SitioDeGraciaEntity necrolimbo = new SitioDeGraciaEntity("Necrolimbo");
            SitioDeGraciaEntity liurnia = new SitioDeGraciaEntity("Liurnia");
            SitioDeGraciaEntity caelid = new SitioDeGraciaEntity("Caelid");
            SitioDeGraciaEntity altus = new SitioDeGraciaEntity("Meseta Altus");
            SitioDeGraciaEntity mountain = new SitioDeGraciaEntity("Picos de los Gigantes");
            SitioDeGraciaEntity peninsula = new SitioDeGraciaEntity("Peninsula");
            SitioDeGraciaEntity gelmir = new SitioDeGraciaEntity("Mt. Gelmir");
            SitioDeGraciaEntity dragonbarrow = new SitioDeGraciaEntity("Dragonbarrow");
            SitioDeGraciaEntity prohibido = new SitioDeGraciaEntity("Tierras Prohibidas");
            SitioDeGraciaEntity nieve = new SitioDeGraciaEntity("Nieve (Oeste)");

            // SOLUCIÓN AL ENTITYNOTFOUNDEXCEPTION: Guardamos primero las entidades vacías
            // para que existan físicamente en PostgreSQL antes de interconectarlas como destinos.
            sitioDeGraciaRepository.saveAll(List.of(
                    necrolimbo, liurnia, caelid, altus, mountain,
                    peninsula, gelmir, dragonbarrow, prohibido, nieve
            ));

            // --- Relaciones de Rutas (Ahora los destinos ya existen en la BD) ---
            necrolimbo.addRuta(liurnia, 34);
            necrolimbo.addRuta(peninsula, 25);
            necrolimbo.addRuta(caelid, 100);
            necrolimbo.addRuta(dragonbarrow, 70);

            liurnia.addRuta(necrolimbo, 34);
            liurnia.addRuta(altus, 30);
            liurnia.addRuta(gelmir, 40);

            caelid.addRuta(necrolimbo, 100);
            caelid.addRuta(altus, 40);
            caelid.addRuta(dragonbarrow, 60);
            caelid.addRuta(peninsula, 5);

            altus.addRuta(liurnia, 30);
            altus.addRuta(gelmir, 20);
            altus.addRuta(prohibido, 50);
            altus.addRuta(caelid, 40);
            altus.addRuta(nieve, 100);

            mountain.addRuta(prohibido, 70);
            mountain.addRuta(dragonbarrow, 20);
            mountain.addRuta(nieve, 30);

            peninsula.addRuta(necrolimbo, 10);
            peninsula.addRuta(caelid, 5);

            gelmir.addRuta(liurnia, 40);
            gelmir.addRuta(altus, 20);

            dragonbarrow.addRuta(caelid, 60);
            dragonbarrow.addRuta(mountain, 20);
            dragonbarrow.addRuta(necrolimbo, 70);

            prohibido.addRuta(altus, 50);
            prohibido.addRuta(mountain, 70);

            nieve.addRuta(altus, 100);
            nieve.addRuta(mountain, 30);

            // Volvemos a guardar el grafo completo para actualizar y persistir las rutas asociadas
            sitioDeGraciaRepository.saveAll(List.of(
                    necrolimbo, liurnia, caelid, altus, mountain,
                    peninsula, gelmir, dragonbarrow, prohibido, nieve
            ));
            log.info(">>> ¡Mapa inicializado en PostgreSQL (Elden Ring - Dijkstra)!");


            // =================================================================
            // --- 3. SEEDER ARMADURA (BRANCH & BOUND - ELDEN RING) ---
            // =================================================================
            armorRepository.deleteAll();

            List<ArmorEntity> armors = List.of(
                    // Cascos (HEAD)
                    new ArmorEntity("Casco de Radahn", 8, 20, "HEAD"),
                    new ArmorEntity("Casco de Cuero", 2, 3, "HEAD"),
                    new ArmorEntity("Casco de Veterano", 7, 18, "HEAD"),

                    // Pechos (CHEST)
                    new ArmorEntity("Pecho de Radahn", 25, 50, "CHEST"),
                    new ArmorEntity("Pecho de Cuero", 5, 8, "CHEST"),
                    new ArmorEntity("Pecho de Veterano", 18, 42, "CHEST"),

                    // Guantes (ARMS)
                    new ArmorEntity("Guantes de Radahn", 6, 15, "ARMS"),
                    new ArmorEntity("Guantes de Cuero", 1, 2, "ARMS"),
                    new ArmorEntity("Guantes de Veterano", 5, 12, "ARMS"),

                    // Piernas (LEGS)
                    new ArmorEntity("Botas de Radahn", 12, 25, "LEGS"),
                    new ArmorEntity("Botas de Cuero", 3, 4, "LEGS"),
                    new ArmorEntity("Botas de Veterano", 10, 22, "LEGS")
            );

            armorRepository.saveAll(armors);
            log.info(">>> ¡Armaduras inicializadas en PostgreSQL (Elden Ring - Branch & Bound)!");
        };
    }
}