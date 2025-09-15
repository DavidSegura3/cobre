package com.cobre.cbmm;

import com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent;
import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account.AccountDataDAO;
import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account.data.AccountData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@DirtiesContext
class AccountsModuleIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private AccountDataDAO accountDataDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.0"));

    @DynamicPropertySource
    static void kafkaProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.consumer.bootstrap-servers", KAFKA::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", KAFKA::getBootstrapServers);
        registry.add("spring.kafka.producer.value-serializer", () -> "org.springframework.kafka.support.serializer.JsonSerializer");
        registry.add("spring.kafka.producer.properties.spring.json.type.mapping", () -> "crossborder:com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent");
        registry.add("spring.kafka.consumer.value-deserializer", () -> "org.springframework.kafka.support.serializer.JsonDeserializer");
        registry.add("spring.kafka.consumer.properties.spring.json.type.mapping", () -> "crossborder:com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent");
        registry.add("spring.kafka.consumer.properties.spring.json.trusted.packages", () -> "com.cobre.cbmm.domain.models.movement");
    }

    @BeforeEach
    void setUp() {
        accountDataDAO.deleteAll();
        AccountData origin = AccountData.builder()
                .id("ACC123456789")
                .balance(new BigDecimal("200000.00"))
                .currency("COP")
                .build();

        AccountData destination = AccountData.builder()
                .id("ACC987654321")
                .balance(new BigDecimal("0.00"))
                .currency("USD")
                .build();

        accountDataDAO.save(origin);
        accountDataDAO.save(destination);
    }

    @Test
    void shouldProcessCrossBorderMovementEventAndUpdateBalances() throws Exception {
        // Arrange: Crear el evento CBMM
        String eventJson = "{\"eventId\":\"cbmm_20250912_000123\"," +
                "\"eventType\":\"cross_border_money_movement\"," +
                "\"origin\":{\"accountId\":\"ACC123456789\",\"currency\":\"COP\",\"amount\":15000.50}," +
                "\"destination\":{\"accountId\":\"ACC987654321\",\"currency\":\"USD\",\"amount\":880.25}}";
        CrossBorderMovementEvent event = objectMapper.readValue(eventJson, CrossBorderMovementEvent.class);

        // Act: Enviar el evento a Kafka
        kafkaTemplate.send("cbmm.requests", event);

        // Assert: Esperar y verificar el estado final en la base de datos
        await().atMost(10, TimeUnit.SECONDS).until(() -> {
            Optional<AccountData> originAccount = accountDataDAO.findById("ACC123456789");
            return originAccount.isPresent() && originAccount.get().getBalance().compareTo(new BigDecimal("184999.50")) == 0;
        });

        Optional<AccountData> originAccount = accountDataDAO.findById("ACC123456789");
        Optional<AccountData> destinationAccount = accountDataDAO.findById("ACC987654321");

        assertTrue(originAccount.isPresent());
        assertTrue(destinationAccount.isPresent());

        assertEquals(new BigDecimal("184999.50"), originAccount.get().getBalance());
        assertEquals(new BigDecimal("880.25"), destinationAccount.get().getBalance());
    }
}