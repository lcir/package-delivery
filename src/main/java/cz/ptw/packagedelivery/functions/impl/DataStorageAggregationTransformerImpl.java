package cz.ptw.packagedelivery.functions.impl;

import cz.ptw.packagedelivery.functions.DataStorageAggregationTransformer;
import cz.ptw.packagedelivery.record.SmartPackage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple data storage transformer.
 */
@Component
public class DataStorageAggregationTransformerImpl implements DataStorageAggregationTransformer {

    /**
     * Method for transform data from list to map of aggregated items.
     *
     * @param source List of input data
     * @return Map of aggregated data.
     */
    @Override
    public Map<Integer, Double> transformToOutputMap(final List<SmartPackage> source) {
        return source
                .stream().collect(Collectors.groupingBy(
                        SmartPackage::postCode,
                        Collectors.mapping(
                                SmartPackage::weight,
                                Collectors.summingDouble(f -> f)
                        )
                ));
    }


    /**
     * Method for transform data from list to map of aggregated items.
     *
     * @param transformedSource Map of aggregated input data
     * @return List of output string.
     */
    @Override
    public List<String> transformToOutputStringList(final Map<Integer, Double> transformedSource) {
        return transformedSource
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(entry -> String.format(Locale.US, "%05d %.3f", entry.getKey(), entry.getValue()))
                .toList();
    }
}
