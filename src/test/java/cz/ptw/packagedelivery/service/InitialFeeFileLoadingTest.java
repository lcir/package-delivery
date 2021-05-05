package cz.ptw.packagedelivery.service;

import cz.ptw.packagedelivery.record.SmartPackage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Initial Fee File Loading Test")
class InitialFeeFileLoadingTest {

    @Autowired
    private FileLoadingService initialFeeFileLoadingService;
    @Autowired
    private List<SmartPackage> storage;

    @Test
    @DisplayName("Should I Deserialize Lines From File When File Is Given")
    void shouldIDeserializeLinesFromFileWhenFileIsGiven() {
        final var urlWithFile = this.getClass().getResource("/fee.txt");
        assert urlWithFile != null;
        initialFeeFileLoadingService.fileLoading(urlWithFile.getPath());

        //assertEquals(5, storage.size());
    }

    @Test
    @DisplayName("Should I Not Fail During Fetching When File With Initial Data Dont Exists")
    void shouldINotFailDuringFetchingWhenFileWithInitialDataDontExists() {
        initialFeeFileLoadingService.fileLoading("path");

        //assertEquals(0, storage.size());
    }
}
