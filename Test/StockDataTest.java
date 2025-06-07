import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class StockDataTest {

    private StockData stockData;

    @BeforeEach
    void setUp() {
        // Initialize StockData with a test file
        stockData = new StockData("test_stock_data.txt");
    }

    @Test
    void testLoadDataFromFile_validFile() {
        // Simulate a file loading method. Ensure the file exists before testing.
        StockData validStockData = new StockData("stock2.txt");
        assertTrue(validStockData.loadDataFromFile(), "Data should be loaded successfully from the file");
    }

    @Test
    void testLoadDataFromFile_invalidFile() {
        // Pass a non-existent or invalid file and expect false.
        StockData invalidStockData = new StockData("non_existent_file.txt");
        assertFalse(invalidStockData.loadDataFromFile(), "Data should not load from an invalid file");
    }

    @Test
    void testLoadSampleStock1() {
        stockData.loadSampleStock1();
        assertEquals(10, stockData.getStockDataPoints().size(), "Stock data should contain 10 points after addData1 is called");
    }

    @Test
    void testLoadSampleStock2() {
        stockData.loadSampleStock2();
        assertEquals(10, stockData.getStockDataPoints().size(), "Stock data should contain 10 points after addData2 is called");
    }

    @Test
    void testGetStock() {
        stockData.loadSampleStock1();
        assertNotNull(stockData.getStockDataPoints(), "Stock data should not be null after loading or adding");
        assertFalse(stockData.getStockDataPoints().isEmpty(), "Stock data should contain points");
    }

    @Test
    void testGetFilename() {
        stockData.loadSampleStock1();
        assertNotNull(stockData.getFilename(), "Filename should not be null after loading or adding");
        assertFalse(stockData.getFilename().isEmpty(), "Filename  should contain  a name");
    }
}
