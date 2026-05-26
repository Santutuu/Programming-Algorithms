package com.example.demo;

// Imports de QuickSort
import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import com.example.demo.QuickSort_Minecraft.repo.ItemRepository;

// Imports de Dijkstra (¡CON TUS NOMBRES DE CLASE!)
import com.example.demo.Dijkstra_EldenRing.model.mapa.SitioDeGraciaEntity;
import com.example.demo.Dijkstra_EldenRing.repo.SitioDeGraciaRepository;

import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import com.example.demo.BranchAndBound_EldenRing.repo.ArmorRepository;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import java.util.List;

@SpringBootApplication
public class  DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * ¡ACÁ VA EL CÓDIGO DEL SEEDER!
     * Este es el Bean que carga los datos al arrancar.
     */
    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepository,
                                   SitioDeGraciaRepository sitioDeGraciaRepository,
                                   ArmorRepository armorRepository) { // ¡Usamos tu repo!
        return args -> {

            // --- SEEDER INVENTARIO (QUICKSORT) ---
            itemRepository.deleteAll().block();
            Flux<ItemEntity> itemsToLoad = Flux.just(
                    new ItemEntity("Redstone", 64, "Recurso"),
                    new ItemEntity("Pico de Diamante", 1, "Herramienta"),
                    new ItemEntity("Pollo Cocido", 32, "Comida"),
                    new ItemEntity("Tierra", 16, "Bloque"),
                    new ItemEntity("Lingote de Hierro", 21, "Recurso")
            );
            itemRepository.saveAll(itemsToLoad).blockLast(); // ¡Acá SÍ se usa saveAll!
            System.out.println(">>> ¡Inventario inicializado!");


            // --- SEEDER MAPA (DIJKSTRA) ---

            // 1. Borramos el mapa anterior
            sitioDeGraciaRepository.deleteAllSitiosDeGracia().block();

            // 2. Creamos los 10 nodos (5 nuevos)
            SitioDeGraciaEntity necrolimbo = new SitioDeGraciaEntity("Necrolimbo");
            SitioDeGraciaEntity liurnia = new SitioDeGraciaEntity("Liurnia");
            SitioDeGraciaEntity caelid = new SitioDeGraciaEntity("Caelid");
            SitioDeGraciaEntity altus = new SitioDeGraciaEntity("Meseta Altus");
            SitioDeGraciaEntity mountain = new SitioDeGraciaEntity("Picos de los Gigantes");

            // --- 5 NUEVOS NODOS ---
            SitioDeGraciaEntity peninsula = new SitioDeGraciaEntity("Peninsula");
            SitioDeGraciaEntity gelmir = new SitioDeGraciaEntity("Mt. Gelmir");
            SitioDeGraciaEntity dragonbarrow = new SitioDeGraciaEntity("Dragonbarrow");
            SitioDeGraciaEntity prohibido = new SitioDeGraciaEntity("Tierras Prohibidas");
            SitioDeGraciaEntity nieve = new SitioDeGraciaEntity("Nieve (Oeste)");


            // 3. Creamos las nuevas relaciones (Rutas) CON COSTO
            // ¡Acá está la lógica para que Dijkstra brille!

            // --- Rutas desde Necrolimbo ---
            necrolimbo.addRuta(liurnia, 34);      // Camino normal
            necrolimbo.addRuta(peninsula, 25);    // Camino fácil al sur
            necrolimbo.addRuta(caelid, 100);     // ¡El camino trampa "obvio"!
            necrolimbo.addRuta(dragonbarrow, 70);

            // --- Rutas desde Liurnia ---
            liurnia.addRuta(necrolimbo, 34);
            liurnia.addRuta(altus, 30);
            liurnia.addRuta(gelmir, 40);

            // --- Rutas desde Caelid ---
            caelid.addRuta(necrolimbo, 100);
            caelid.addRuta(altus, 40);
            caelid.addRuta(dragonbarrow, 60);
            caelid.addRuta(peninsula, 5);

            // --- Rutas desde Meseta Altus ---
            altus.addRuta(liurnia, 30);
            altus.addRuta(gelmir, 20);
            altus.addRuta(prohibido, 50);  // Camino "directo" a las cumbres
            altus.addRuta(caelid, 40);     // Camino secreto caro
            altus.addRuta(nieve, 100);

            // --- Rutas desde Picos de los gigantes (Destino) ---
            mountain.addRuta(prohibido, 70);
            mountain.addRuta(dragonbarrow, 20); // Atajo barato desde el norte
            mountain.addRuta(nieve, 30);

            // --- Rutas de los 5 nodos nuevos ---

            // Peninsula (El inicio del atajo)
            peninsula.addRuta(necrolimbo, 10);
            peninsula.addRuta(caelid, 5); // ¡El "portal" barato a Caelid!

            // Mt. Gelmir
            gelmir.addRuta(liurnia, 40);
            gelmir.addRuta(altus, 20);

            // Dragonbarrow (Norte de Caelid)
            dragonbarrow.addRuta(caelid, 60);
            dragonbarrow.addRuta(mountain, 20); // ¡El atajo a picos de los gigantes!
            dragonbarrow.addRuta(necrolimbo, 70);

            // Tierras Prohibidas (El camino "obvio")
            prohibido.addRuta(altus, 50);
            prohibido.addRuta(mountain, 70);  // Ruta cara a picos de los gigantes

            // Nieve (Oeste)
            nieve.addRuta(altus, 100);
            nieve.addRuta(mountain, 30);


            // 4. Guardamos TODOS los 10 nodos en la BD
            sitioDeGraciaRepository.saveAll(List.of(
                    necrolimbo, liurnia, caelid, altus, mountain,
                    peninsula, gelmir, dragonbarrow, prohibido, nieve
            )).blockLast();


            System.out.println(">>> ¡Mapa de Elden Ring inicializado!");


            // --- SEEDER ARMADURA (BRANCH & BOUND) ---
            armorRepository.deleteAllArmor().block();
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
            armorRepository.saveAll(armors).blockLast();
            System.out.println(">>> ¡Armaduras de Elden Ring (con slots) inicializadas!");
        };
    }
}