package cz.ptw.packagedelivery.service;

import cz.ptw.packagedelivery.record.Fee;

import java.io.File;
import java.util.List;

/**
 * Fee Deserializer Transformer
 */
public interface FeeDeserializerService {

    /**
     * Method for deserialization all lines in file
     *
     * @param inputFile File with fee lines
     * @return List of new Fee records.
     */
    List<Fee> deserialize(File inputFile);
}
