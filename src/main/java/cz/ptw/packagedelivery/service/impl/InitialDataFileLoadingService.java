package cz.ptw.packagedelivery.service.impl;

import cz.ptw.packagedelivery.functions.DeserializerTransformer;
import cz.ptw.packagedelivery.record.SmartPackage;
import cz.ptw.packagedelivery.service.FileLoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Class for loading initial data to storage
 */
@Service("initialDataFileLoadingService")
@Slf4j
@RequiredArgsConstructor
public class InitialDataFileLoadingService implements FileLoadingService {

    private final DeserializerTransformer deserializerTransformer;
    private final List<SmartPackage> storage;

    /**
     * Method for loading file - method after right fetching fills storage
     *
     * @param inputFilePath input file path
     */
    public void fileLoading(final String inputFilePath) {
        final var file = new File(inputFilePath);
        if (!file.exists()) {
            log.error("File " + inputFilePath + " was not found.");
        } else {
            storage.addAll(deserializerTransformer.deserialize(file));
        }
    }
}
