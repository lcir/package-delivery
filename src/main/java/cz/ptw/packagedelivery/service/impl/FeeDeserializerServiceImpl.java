package cz.ptw.packagedelivery.service.impl;

import cz.ptw.packagedelivery.exception.FeeInputFormatIsNotValidException;
import cz.ptw.packagedelivery.record.Fee;
import cz.ptw.packagedelivery.service.FeeDeserializerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Fee Deserializer Transformer
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeeDeserializerServiceImpl implements FeeDeserializerService {

    private static final String WEIGHT = "weight";
    private static final String PRICE = "price";
    private static final Pattern DESERIALIZER_PATTERN =
            Pattern.compile("(?<" + WEIGHT + ">\\d+\\.?\\d{0,3}) (?<" + PRICE + ">\\d+\\.\\d{2})");

    private Fee deserialize(final String line) {

        final var matcher = DESERIALIZER_PATTERN.matcher(line);

        if (matcher.matches()) {
            final var weight = matcher.group(WEIGHT);
            final var price = matcher.group(PRICE);
            return new Fee(Double.parseDouble(weight), Double.parseDouble(price));
        }

        throw new FeeInputFormatIsNotValidException(String.format("Problem with deserialization of fee line %s.", line));
    }

    /**
     * Method for deserialization all lines in file
     *
     * @param inputFile File with lines
     * @return List of new fee records.
     */
    @Override
    public List<Fee> deserialize(final File inputFile) {

        try (var reader = new BufferedReader(new FileReader(inputFile))) {
            return reader.lines()
                    .map(line -> {
                        try {
                            return this.deserialize(line);
                        } catch (FeeInputFormatIsNotValidException e) {
                            log.error(String.format("Problem with line in fee file %s. On line %s.", inputFile.getPath(), line));
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Problem with fee file input buffer", e);
        }

        return Collections.emptyList();
    }
}
