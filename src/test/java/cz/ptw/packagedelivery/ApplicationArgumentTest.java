package cz.ptw.packagedelivery;

import cz.ptw.packagedelivery.record.SmartPackage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(args = {"./src/test/resources/inputs.txt", "./src/test/resources/fee.txt"})
@DirtiesContext()
@DisplayName("Application Argument Test")
class ApplicationArgumentTest {

    @Autowired
    private List<SmartPackage> storage;

    @Test
    @DisplayName("Should Fill Storage When Right Argument Is Provided.")
    void shouldFillStorageWhenRightArgumentIsProvided() {
        assertEquals(6, storage.size());
    }
}
