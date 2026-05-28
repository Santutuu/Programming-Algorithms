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
import org.springframework.transaction.annotation.Transactional;

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
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info(">>> Zona horaria forzada a UTC.");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner initDatabase(ItemRepository itemRepository,
                                   SitioDeGraciaRepository sitioDeGraciaRepository,
                                   ArmorRepository armorRepository) {
        return args -> {
            
            // Limpieza masiva eficiente usando batch
            itemRepository.deleteAllInBatch();
            sitioDeGraciaRepository.deleteAllInBatch();
            armorRepository.deleteAllInBatch();

            // 1. SEEDER MINECRAFT
            itemRepository.saveAll(List.of(
                    new ItemEntity("Redstone", 64, "Recurso"),
                    new ItemEntity("Pico de Diamante", 1, "Herramienta"),
                    new ItemEntity("Pollo Cocido", 32, "Comida"),
                    new ItemEntity("Tierra", 16, "Bloque"),
                    new ItemEntity("Lingote de Hierro", 21, "Recurso")
            ));
            log.info(">>> Inventario inicializado.");

            // 2. SEEDER ELDEN RING (MAPA)
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

            // Definimos relaciones
            necrolimbo.addRuta(liurnia, 34);
            necrolimbo.addRuta(peninsula, 25);
            
            // Guardamos todo el grafo de una vez
            sitioDeGraciaRepository.saveAll(List.of(
                    necrolimbo, liurnia, caelid, altus, mountain,
                    peninsula, gelmir, dragonbarrow, prohibido, nieve
            ));
            log.info(">>> Mapa inicializado.");

            // 3. SEEDER ARMADURAS
            armorRepository.saveAll(List.of(
                    new ArmorEntity("Casco de Radahn", 8, 20, "HEAD"),
                    new ArmorEntity("Pecho de Radahn", 25, 50, "CHEST"),
                    new ArmorEntity("Guantes de Radahn", 6, 15, "ARMS"),
                    new ArmorEntity("Botas de Radahn", 12, 25, "LEGS")
            ));
            log.info(">>> Armaduras inicializadas.");
        };
    }
}
