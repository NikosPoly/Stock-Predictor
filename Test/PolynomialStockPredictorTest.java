import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialStockPredictorTest {
    private StockData stockData;
    private PolynomialStockPredictor polynomialStockPredictor;

    @BeforeEach
    void setUp() {
        // Initialize StockData with a test file
        stockData = new StockData("stock2.txt");
        stockData.loadDataFromFile();
        polynomialStockPredictor = new PolynomialStockPredictor(stockData, 3);
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    void displayForecast() {
        // Just checking that the method runs without throwing exceptions
        double[] dummyCoeffs = {1.0, 0.0}; // any dummy coefficients
        assertDoesNotThrow(() -> polynomialStockPredictor.displayForecast(dummyCoeffs));
    }

    @Test
    void buildDesignMatrix() {
        // Test that buildMatrix correctly fills the design matrix and target vector
        double[][] designMatrix = new double[10][4];
        double[] targetVector = new double[10];

        polynomialStockPredictor.buildDesignMatrix(stockData.getStockDataPoints(), designMatrix, targetVector);

        // Check if the matrix is properly populated
        assertNotNull(designMatrix, "Design matrix should not be null");
        assertEquals(10, designMatrix.length, "Matrix should have 10 rows");
        assertEquals(4, designMatrix[0].length, "Matrix should have 4 columns for degree 3");
        assertNotNull(targetVector, "Target vector should not be null");
        assertEquals(10, targetVector.length, "Target vector should have 10 elements");
    }

    @Test
    void solveLeastSquares() {
        PolynomialStockPredictor predictor = new PolynomialStockPredictor(stockData, 2); // 2 terms for linear model

        double[][] A = {
                {1, 1},
                {2, 1},
                {3, 1}
        };
        double[] b = {2, 3, 5};

        double[] coeffs = predictor.solveLeastSquares(A, b);

        // Expected: slope ~1.5, intercept ~0.333...
        assertEquals(1.5, coeffs[0], 0.01);
        assertEquals(0.333, coeffs[1], 0.01);
    }


    @Test
    void transpose() {
        // Example matrix (3x2)
        double[][] matrix = {
                {1, 2},
                {3, 4},
                {5, 6}
        };

        // Expected transposed matrix (2x3)
        double[][] expectedTranspose = {
                {1, 3, 5},
                {2, 4, 6}
        };

        // Perform transpose operation
        PolynomialStockPredictor polynomialStockPredictor = new PolynomialStockPredictor(stockData, 3); // Passing null for stock data just for this test
        double[][] result = polynomialStockPredictor.transpose(matrix);

        // Check if the result matches the expected transposed matrix
        assertNotNull(result, "Transposed matrix should not be null");
        assertEquals(expectedTranspose.length, result.length, "Row count of transposed matrix should match");
        assertEquals(expectedTranspose[0].length, result[0].length, "Column count of transposed matrix should match");

        // Check if each element is correctly transposed
        for (int i = 0; i < expectedTranspose.length; i++) {
            for (int j = 0; j < expectedTranspose[i].length; j++) {
                assertEquals(expectedTranspose[i][j], result[i][j], "Element at [" + i + "][" + j + "] should match");
            }
        }
    }

    @Test
    void multiplyMatrixByMatrix() {
        // Example matrices for multiplication
        double[][] A = {
                {1, 2, 3},
                {4, 5, 6}
        };

        double[][] B = {
                {7, 8},
                {9, 10},
                {11, 12}
        };

        // Expected result of A * B
        double[][] expectedResult = {
                {58, 64},
                {139, 154}
        };

        // Perform matrix multiplication
        PolynomialStockPredictor polynomialStockPredictor = new PolynomialStockPredictor(stockData, 3); // Passing null for stock data just for this test
        double[][] result = polynomialStockPredictor.multiply(A, B);

        // Check that the result is not null
        assertNotNull(result, "Resulting matrix should not be null");

        // Check if the result matrix dimensions are correct
        assertEquals(A.length, result.length, "Row count of resulting matrix should match number of rows in A");
        assertEquals(B[0].length, result[0].length, "Column count of resulting matrix should match number of columns in B");

        // Check if each element of the result matrix is correct
        for (int i = 0; i < expectedResult.length; i++) {
            for (int j = 0; j < expectedResult[i].length; j++) {
                assertEquals(expectedResult[i][j], result[i][j], "Element at [" + i + "][" + j + "] should match expected value");
            }
        }
    }

    @Test
    void multiplyMatrixByVector() {
        // Define a 3x3 matrix
        double[][] A = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // Define a vector
        double[] b = {1, 0, -1};

        // Expected result
        double[] expectedResult = {-2, -2, -2};

        // Create StockPredictor instance
        PolynomialStockPredictor polynomialStockPredictor = new PolynomialStockPredictor(stockData, 3); // stockData is not needed for this test

        // Perform multiplication
        double[] result = polynomialStockPredictor.multiply(A, b);

        // Check dimensions
        assertNotNull(result);
        assertEquals(expectedResult.length, result.length, "Result vector length should match expected");

        // Check each element
        for (int i = 0; i < expectedResult.length; i++) {
            assertEquals(expectedResult[i], result[i], 1e-9, "Element at index " + i + " should match expected");
        }
    }

    @Test
    void testGaussianElimination() {
        double[][] A = {
                {2, 1},
                {1, 3}
        };
        double[] b = {8, 13};

        PolynomialStockPredictor predictor = new PolynomialStockPredictor(stockData, 3); // StockData not needed here
        double[] result = predictor.gaussianElimination(A, b);

        // Expected result
        double[] expected = {2.2, 3.6};

        assertNotNull(result);
        assertEquals(expected.length, result.length);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i], 1e-6, "Mismatch at index " + i);
        }
    }

    @Test
    void testPredictStockValue() {
        // Example polynomial: 2x^2 + 3x + 1
        double[] coeffs = {2, 3, 1};
        double x = 2.0;

        PolynomialStockPredictor predictor = new PolynomialStockPredictor(stockData, 3); // 3 terms = degree 2 polynomial
        double predicted = predictor.predictStockValue(x, coeffs);

        double expected = 30.0; // 2*4 + 3*2 + 1

        assertEquals(expected, predicted, 1e-6);
    }
}