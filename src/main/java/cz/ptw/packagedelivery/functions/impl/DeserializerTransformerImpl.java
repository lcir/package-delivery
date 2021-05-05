package cz.ptw.packagedelivery.functions.impl;

import cz.ptw.packagedelivery.exception.SmartPackageInputFormatIsNotValidException;
import cz.ptw.packagedelivery.functions.DeserializerTransformer;
import cz.ptw.packagedelivery.record.SmartPackage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
 * Data Deserializer Transformer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeserializerTransformerImpl implements DeserializerTransformer {

    private static final String WEIGHT = "weight";
    private static final String POST_CODE = "postCode";
    private static final Pattern DESERIALIZER_PATTERN =
            Pattern.compile("(?<" + WEIGHT + ">\\d+\\.?\\d{0,3}) (?<" + POST_CODE + ">\\d{5})");

    /**
     * Method for deserialization of single string line
     *
     * @param line simple string line with weight/postCode payload.
     * @return Single new SmartPackage instance
     */
    @Override
    public SmartPackage deserialize(final String line) {

        final var matcher = DESERIALIZER_PATTERN.matcher(line);

        if (matcher.matches()) {
            final var weight = matcher.group(WEIGHT);
            final var postCode = matcher.group(POST_CODE);
            return new SmartPackage(Double.parseDouble(weight), Integer.parseInt(postCode));
        }

        throw new SmartPackageInputFormatIsNotValidException(String.format("Problem with deserialization of line %s.", line));
    }

    /**
     * Method for deserialization all lines in file
     *
     * @param inputFile File with lines
     * @return List of new smart packages.
     */
    @Override
    public List<SmartPackage> deserialize(final File inputFile) {

        try (var reader = new BufferedReader(new FileReader(inputFile))) {
            return reader.lines()
                    .map(line -> {
                        try {
                            return this.deserialize(line);
                        } catch (SmartPackageInputFormatIsNotValidException e) {
                            log.error(String.format("Problem with line in file %s.", inputFile.getPath()), e);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Problem with file input buffer", e);
        }

        return Collections.emptyList();
    }
}
