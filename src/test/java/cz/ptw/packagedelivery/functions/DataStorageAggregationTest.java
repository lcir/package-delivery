package cz.ptw.packagedelivery.functions;

import cz.ptw.packagedelivery.record.Fee;
import cz.ptw.packagedelivery.record.SmartPackage;
import cz.ptw.packagedelivery.service.FeeDeserializerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Data Storage Aggregation Test")
class DataStorageAggregationTest {

    @Autowired
    private DeserializerTransformer deserializerTransformer;
    @Autowired
    private FeeDeserializerService feeDeserializerService;
    @Autowired
    private DataStorageAggregationTransformer dataStorageAggregationTransformer;

    private List<SmartPackage> deserializedList;
    private List<Fee> feeList;

    @BeforeEach
    void setUp() {
        final var urlWithFile = this.getClass().getResource("/inputs.txt");
        assert urlWithFile != null;
        final var testFile = new File(urlWithFile.getFile());

        final var feeUrlWithFile = this.getClass().getResource("/fee.txt");
        assert feeUrlWithFile != null;
        final var feeTestFile = new File(feeUrlWithFile.getFile());

        feeList = feeDeserializerService.deserialize(feeTestFile);
        deserializedList = deserializerTransformer.deserialize(testFile);
    }

    @Test
    @DisplayName("Should Aggregate Post Code Weight 8801 To 15.96 When Test Valid Inputs File Is Loaded")
    void shouldAggregatePostCodeWeight8801To1596WhenTestValidInputsFileIsLoaded() {
        final var outputAggregatedMap = dataStorageAggregationTransformer.transformToOutputMap(deserializedList, feeList);
        assertNotNull(outputAggregatedMap);
        assertEquals(5, outputAggregatedMap.size());

        assertTrue(outputAggregatedMap.get(8801).isPresent());
        assertEquals(15.96, outputAggregatedMap.get(8801).get().weight());
    }

    @Test
    @DisplayName("Should Aggregate And Transform Data To Right Output String `08801 15.960 7.00` When Input File Is Loaded")
    void shouldAggregateAndTransformDataToRightOutputStringWhenInputFileIsLoaded() {
        final var outputAggregatedMap = dataStorageAggregationTransformer.transformToOutputMap(deserializedList, feeList);
        final var outputStringList = dataStorageAggregationTransformer.transformToOutputStringList(outputAggregatedMap);

        assertNotNull(outputStringList);
        assertEquals(5, outputStringList.size());

        assertEquals("08801 15.960 7.00", outputStringList.get(0));
        assertEquals("09300 3.200 2.00", outputStringList.get(2));
    }

    @Test
    @DisplayName("Should Aggregate And Transform Data To Right Output String `08801 15.960 0.00` When Input File Is Loaded And Fee is Missing")
    void shouldAggregateAndTransformDataToRightOutputStringWhenInputFileIsLoadedAndFeeIsMissing() {
        final var outputAggregatedMap = dataStorageAggregationTransformer.transformToOutputMap(deserializedList, new ArrayList<>());
        final var outputStringList = dataStorageAggregationTransformer.transformToOutputStringList(outputAggregatedMap);

        assertNotNull(outputStringList);
        assertEquals(5, outputStringList.size());

        assertEquals("08801 15.960 0.00", outputStringList.get(0));
        assertEquals("09300 3.200 0.00", outputStringList.get(2));
    }
}
