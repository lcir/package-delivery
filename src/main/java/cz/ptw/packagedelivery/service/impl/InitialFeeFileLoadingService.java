package cz.ptw.packagedelivery.service.impl;

import cz.ptw.packagedelivery.service.FileLoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Class for loading initial data to fee storage
 */
@Component("initialFeeFileLoadingService")
@Slf4j
@RequiredArgsConstructor
public class InitialFeeFileLoadingService implements FileLoadingService {

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
            log.warn("Not implemented right now.");
        }
    }
}
