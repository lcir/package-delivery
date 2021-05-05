package cz.ptw.packagedelivery.functions.impl;

import cz.ptw.packagedelivery.functions.DataStorageAggregationTransformer;
import cz.ptw.packagedelivery.record.Fee;
import cz.ptw.packagedelivery.record.SmartPackage;
import cz.ptw.packagedelivery.record.SmartPackageWithPrice;
import org.springframework.stereotype.Component;

import java.util.*;
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
    public Map<Integer, Optional<SmartPackageWithPrice>> transformToOutputMap(final List<SmartPackage> source, final List<Fee> feeSource) {

        return source
                .stream()
                .map(x -> new SmartPackageWithPrice(x.weight(), x.postCode(),
                                feeSource.stream()
                                        .sorted(Comparator.comparing(Fee::weight).reversed())
                                        .filter(fee -> fee.weight() < x.weight())
                                        .findFirst()
                                        .orElse(feeSource.stream()
                                                .min(Comparator.comparing(Fee::weight))
                                                .orElse(new Fee(0d, 0d)))
                                        .price()
                        )
                )
                .collect(Collectors.groupingBy(
                        SmartPackageWithPrice::postCode,
                        Collectors.reducing((smartPackage, smartPackage2) ->
                                new SmartPackageWithPrice(
                                        smartPackage.weight() + smartPackage2.weight(),
                                        smartPackage.postCode(),
                                        smartPackage.price() + smartPackage2.price())
                        )
                        )
                );
    }

    /**
     * Method for transform data from list to map of aggregated items.
     *
     * @param transformedSource Map of aggregated input data
     * @return List of output string.
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public List<String> transformToOutputStringList(final Map<Integer, Optional<SmartPackageWithPrice>> transformedSource) {
        return transformedSource
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .sorted(Map.Entry.<Integer, Optional<SmartPackageWithPrice>>comparingByValue(
                        Comparator.comparing(o -> o.get().weight())).reversed()
                )
                .map(entry -> String.format(Locale.US, "%05d %.3f %.2f",
                        entry.getKey(),
                        entry.getValue().get().weight(),
                        entry.getValue().get().price()
                ))
                .toList();
    }
}
