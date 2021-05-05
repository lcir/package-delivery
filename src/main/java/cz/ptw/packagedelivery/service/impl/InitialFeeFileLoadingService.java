package cz.ptw.packagedelivery.service.impl;

import cz.ptw.packagedelivery.record.Fee;
import cz.ptw.packagedelivery.service.FeeDeserializerService;
import cz.ptw.packagedelivery.service.FileLoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Class for loading initial data to fee storage
 */
@Component("initialFeeFileLoadingService")
@Slf4j
@RequiredArgsConstructor
public class InitialFeeFileLoadingService implements FileLoadingService {

    private final FeeDeserializerService feeDeserializerService;
    private final List<Fee> feeStorage;

    /**
     * Method for loading file - method after right fetching fills fee storage
     *
     * @param inputFilePath input file path
     */
    public void fileLoading(final String inputFilePath){
        final var file = new File(inputFilePath);
        if(!file.exists()) {
            log.error("Fee File " + inputFilePath + " was not found.");
        }
        else {
            feeStorage.addAll(feeDeserializerService.deserialize(file));
        }
    }
}
