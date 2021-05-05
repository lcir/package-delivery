package cz.ptw.packagedelivery.functions;

import cz.ptw.packagedelivery.record.SmartPackage;

import java.util.List;
import java.util.Map;

/**
 * Simple data storage transformer.
 */
public interface DataStorageAggregationTransformer {

    /**
     * Method for transform data from list to map of aggregated items.
     *
     * @param source List of input data
     * @return Map of aggregated data.
     */
    Map<Integer, Double> transformToOutputMap(List<SmartPackage> source);

    /**
     * Method for transform data from list to map of aggregated items.
     *
     * @param transformedSource Map of aggregated input data
     * @return List of output string.
     */
    List<String> transformToOutputStringList(Map<Integer, Double> transformedSource);
}
