package cz.ptw.packagedelivery.service;

import cz.ptw.packagedelivery.record.SmartPackage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Initial Data File Loading Test")
class InitialDataFileLoadingTest {

    @Autowired
    private FileLoadingService initialDataFileLoadingService;
    @Autowired
    private List<SmartPackage> storage;

    @Test
    @DisplayName("Should I Deserialize Lines From File When File Is Given")
    void shouldIDeserializeLinesFromFileWhenFileIsGiven() {
        final var urlWithFile = this.getClass().getResource("/inputs.txt");
        assert urlWithFile != null;
        initialDataFileLoadingService.fileLoading(urlWithFile.getPath());

        assertEquals(5, storage.size());
    }

    @Test
    @DisplayName("Should I Not Fail During Fetching When File With Initial Data Dont Exists")
    void shouldINotFailDuringFetchingWhenFileWithInitialDataDontExists() {
        initialDataFileLoadingService.fileLoading("path");

        assertEquals(0, storage.size());
    }
}
