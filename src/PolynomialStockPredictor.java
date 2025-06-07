/**
 * Predicts stock prices based on polynomial regression of historical stock data.
 */
import java.util.List;
import static java.lang.Math.pow;

public class PolynomialStockPredictor {

    private final int polynomialTerms;                  // Number of terms in the polynomial (degree + 1)
    private static final double EPSILON = 1e-10;   // Small value to handle numerical stability

    StockData stockData;

    /**
     * Constructor to initialize StockPredictor with stock data and degree of the polynomial.
     * @param stockData The stock data for prediction
     * @param degree The degree of the polynomial for regression
     */
    public PolynomialStockPredictor(StockData stockData, int degree) {
        this.stockData = stockData;
        this.polynomialTerms = degree + 1; // Terms depend on the degree
        int dataPoints = stockData.getStockDataPoints().size(); // Get the number of data points from the StockData object

        // Initialize matrices based on data points and terms
        double[][] designMatrix = new double[dataPoints][polynomialTerms];
        double[] targetsVector = new double[dataPoints];

        performRegression(designMatrix, targetsVector);
    }

    /**
     * Processes the stock data and prepares matrices for regression.
     * @param designMatrixStock The design matrix for stock data
     * @param targetsStock The target vector for stock data
     */
    public void performRegression(double[][] designMatrixStock, double[] targetsStock) {
        buildDesignMatrix(stockData.getStockDataPoints(), designMatrixStock, targetsStock);

        double[] polynomialCoeffsStock = solveLeastSquares(designMatrixStock, targetsStock);

        displayForecast(polynomialCoeffsStock);
    }

    /**
     * Displays the results of the stock predictions for the next days.
     * @param regressionCoefficients The polynomial coefficients obtained from regression
     */
    public void displayForecast(double[] regressionCoefficients) {
        String filename = stockData.getFilename();

        System.out.println("Stock Predictions for file " + filename + ":");
        for (int i = 10; i <= 16; i++) {
            System.out.printf("%dth day: %.4f%n", i, predictStockValue(i, regressionCoefficients));
        }
    }

    /**
     * Builds the design matrix and target vector for stock data.
     * @param dataPoints The list of stock data points
     * @param A The design matrix
     * @param b The target vector
     */
    public void buildDesignMatrix(List<Point> dataPoints, double[][] A, double[] b) {
        for (int row = 0; row < dataPoints.size(); row++) {
            double x = dataPoints.get(row).getX();
            for (int col = 0; col < polynomialTerms; col++) {
                A[row][col] = pow(x, polynomialTerms - col - 1); // Set A[i][j] to x raised to the appropriate power
            }
            b[row] = dataPoints.get(row).getY(); // Set b[i] to the y-value (stock price) of the point
        }
    }

    /**
     * Solves the least squares problem using the normal equation.
     * @param A The design matrix
     * @param b The target vector
     * @return The polynomial coefficients
     */
    public double[] solveLeastSquares(double[][] A, double[] b) {
        double[][] At = transpose(A);
        double[][] AtA = multiply(At, A);
        double[] Atb = multiply(At, b);

        return gaussianElimination(AtA, Atb);
    }

    /**
     * Transposes a matrix.
     * @param matrix The matrix to be transposed
     * @return The transposed matrix
     */
    public double[][] transpose(double[][] matrix) {
        double[][] transposed = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < transposed.length; i++)
            for (int j = 0; j < transposed[0].length; j++)
                transposed[i][j] = matrix[j][i];
        return transposed;
    }

    /**
     * Multiplies two matrices.
     * @param A The first matrix
     * @param B The second matrix
     * @return The result of multiplying A and B
     */
    public double[][] multiply(double[][] A, double[][] B) {
        int rows = A.length, cols = B[0].length, sumDim = B.length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                for (int k = 0; k < sumDim; k++)
                    result[i][j] += A[i][k] * B[k][j];
        return result;
    }

    /**
     * Multiplies a matrix with a vector.
     * @param A The matrix
     * @param b The vector
     * @return The result of multiplying A with b
     */
    public double[] multiply(double[][] A, double[] b) {
        int rows = A.length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < b.length; j++)
                result[i] += A[i][j] * b[j];
        return result;
    }

    /**
     * Solves a system of linear equations using Gaussian elimination.
     * @param A The matrix of coefficients
     * @param b The vector of constants
     * @return The solution vector
     */
    public double[] gaussianElimination(double[][] A, double[] b) {
        int n = b.length;
        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++)
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) max = i;

            double[] tempRow = A[p]; A[p] = A[max]; A[max] = tempRow;
            double tempVal = b[p]; b[p] = b[max]; b[max] = tempVal;

            if (Math.abs(A[p][p]) <= EPSILON)
                throw new ArithmeticException("Matrix is singular or nearly singular");

            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++)
                sum += A[i][j] * x[j];
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    /**
     * Predicts the stock value for a given day using polynomial regression.
     * @param dayIndex The day number
     * @param polynomialCoeffsStock The polynomial coefficients
     * @return The predicted stock price for the day
     */
    public double predictStockValue(double dayIndex, double[] polynomialCoeffsStock) {
        double result = 0;
        for (int i = 0; i < polynomialCoeffsStock.length; i++) {
            result += polynomialCoeffsStock[i] * pow(dayIndex, polynomialTerms - i - 1);
        }
        return result;
    }
}