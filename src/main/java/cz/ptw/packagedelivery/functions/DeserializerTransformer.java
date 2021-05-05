package cz.ptw.packagedelivery.functions;

import cz.ptw.packagedelivery.record.SmartPackage;

import java.io.File;
import java.util.List;

/**
 * Data Deserializer Transformer
 */
public interface DeserializerTransformer {

    /**
     * Method for deserialization of single string line
     *
     * @param line simple string line with weight/postCode payload.
     * @return Single new SmartPackage instance
     */
    SmartPackage deserialize(String line);

    /**
     * Method for deserialization all lines in file
     *
     * @param inputFile File with lines
     * @return List of new smart packages.
     */
    List<SmartPackage> deserialize(File inputFile);
}
