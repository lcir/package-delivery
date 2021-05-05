package cz.ptw.packagedelivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Fee Deserialization Test")
class FeeDeserializeTest {

    @Autowired
    private FeeDeserializerService feeDeserializerService;

    @Test
    @DisplayName("Should I Deserialize Lines From Fee File When File Is Given")
    void shouldIDeserializeLinesFromFileWhenFileIsGiven() {
        final var urlWithFile = this.getClass().getResource("/fee.txt");
        assert urlWithFile != null;
        final var testFile = new File(urlWithFile.getFile());

        final var deserializedList = feeDeserializerService.deserialize(testFile);
        assertNotNull(deserializedList);
        assertFalse(deserializedList.isEmpty());
        assertEquals(7, deserializedList.size());
    }

    @Test
    @DisplayName("Should I Deserialize Lines From Fee File When File Is Given")
    void shouldIDeserializeLinesFromFsileWhenFileIsGiven() {
        final var testFile = new File("test.file");

        final var deserializedList = feeDeserializerService.deserialize(testFile);
        assertNotNull(deserializedList);
        assertTrue(deserializedList.isEmpty());
    }
}
