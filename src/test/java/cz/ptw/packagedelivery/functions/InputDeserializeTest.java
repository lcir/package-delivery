package cz.ptw.packagedelivery.functions;

import cz.ptw.packagedelivery.exception.SmartPackageInputFormatIsNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Input Deserialization Test")
class InputDeserializeTest {

    @Autowired
    private DeserializerTransformer deserializerTransformer;

    @Test
    @DisplayName("Should I Deserialize Data From Line When Line Is Given")
    void shouldIDeserializeDataFromLineWhenLineIsGiven() {

        final var weight = 3.4d;
        final var postCode = 8801;
        final var lineWithData = String.format(Locale.US, "%1.1f %05d", weight, postCode);
        final var smartPackage = deserializerTransformer.deserialize(lineWithData);
        assertNotNull(smartPackage);
        assertEquals(weight, smartPackage.weight());
        assertEquals(postCode, smartPackage.postCode());
    }

    @Test
    @DisplayName("Should Fail With Exception Because Invalid Weight Format")
    void shouldFailWithExceptionBecauseInvalidWeightFormat() {
        assertThrows(SmartPackageInputFormatIsNotValidException.class, () -> {
            final var lineWithData = "asd 90876";
            deserializerTransformer.deserialize(lineWithData);
        });
    }

    @Test
    @DisplayName("Should Fail With Exception Because Invalid Post Code Format")
    void shouldFailWithExceptionBecauseInvalidPostCodeFormat() {
        assertThrows(SmartPackageInputFormatIsNotValidException.class, () -> {
            final var lineWithData = "4.5  x90876";
            deserializerTransformer.deserialize(lineWithData);
        });
    }

    @Test
    @DisplayName("Should I Deserialize Lines From File When File Is Given")
    void shouldIDeserializeLinesFromFileWhenFileIsGiven() {
        final var urlWithFile = this.getClass().getResource("/inputs.txt");
        assert urlWithFile != null;
        final var testFile = new File(urlWithFile.getFile());

        final var deserializedList = deserializerTransformer.deserialize(testFile);
        assertNotNull(deserializedList);
        assertFalse(deserializedList.isEmpty());
        assertEquals(6, deserializedList.size());
    }

    @Test
    @DisplayName("Should I Did Not Deserialize Lines From File When File Is Not Given")
    void shouldIDidNotDeserializeLinesFromFileWhenFileIsNotGiven() {
        final var testFile = new File("test.file");

        final var deserializedList = deserializerTransformer.deserialize(testFile);
        assertNotNull(deserializedList);
        assertTrue(deserializedList.isEmpty());
    }
}
