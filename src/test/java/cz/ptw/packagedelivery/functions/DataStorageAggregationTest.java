package cz.ptw.packagedelivery.functions;

import cz.ptw.packagedelivery.record.SmartPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Data Storage Aggregation Test")
class DataStorageAggregationTest {

    @Autowired
    private DeserializerTransformer deserializerTransformer;
    @Autowired
    private DataStorageAggregationTransformer dataStorageAggregationTransformer;

    private List<SmartPackage> deserializedList;

    @BeforeEach
    void setUp() {
        final var urlWithFile = this.getClass().getResource("/inputs.txt");
        assert urlWithFile != null;
        final var testFile = new File(urlWithFile.getFile());

        deserializedList = deserializerTransformer.deserialize(testFile);
    }

    @Test
    @DisplayName("Should Aggregate Post Code Weight 8801 To 15.96 When Test Valid Inputs File Is Loaded")
    void shouldAggregatePostCodeWeight8801To1596WhenTestValidInputsFileIsLoaded() {
        final var outputAggregatedMap = dataStorageAggregationTransformer.transformToOutputMap(deserializedList);
        assertNotNull(outputAggregatedMap);
        assertEquals(4, outputAggregatedMap.size());

        assertEquals(15.96, outputAggregatedMap.get(8801));
    }

    @Test
    @DisplayName("Should Aggregate And Transform Data To Right Output String `08801 15.960` When Input File Is Loaded")
    void shouldAggregateAndTransformDataToRightOutputStringWhenInputFileIsLoaded() {
        final var outputAggregatedMap = dataStorageAggregationTransformer.transformToOutputMap(deserializedList);
        final var outputStringList = dataStorageAggregationTransformer.transformToOutputStringList(outputAggregatedMap);

        assertNotNull(outputStringList);
        assertEquals(4, outputStringList.size());

        assertEquals("08801 15.960", outputStringList.get(0));
        assertEquals("09300 3.200", outputStringList.get(2));
    }
}
