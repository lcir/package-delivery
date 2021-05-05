package cz.ptw.packagedelivery.functions;

import cz.ptw.packagedelivery.record.Fee;
import cz.ptw.packagedelivery.record.SmartPackage;
import cz.ptw.packagedelivery.record.SmartPackageWithPrice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    Map<Integer, Optional<SmartPackageWithPrice>> transformToOutputMap(List<SmartPackage> source, final List<Fee> feeSource);

    /**
     * Method for transform data from list to map of aggregated items.
     *
     * @param transformedSource Map of aggregated input data
     * @return List of output string.
     */
    List<String> transformToOutputStringList(Map<Integer, Optional<SmartPackageWithPrice>> transformedSource);
}
