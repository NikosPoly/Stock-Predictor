# Stock Predictor

This project provides implementations of polynomial regression in order to predict the next values in stock data.

## Table of Contents


- [Dependencies](#dependencies)
- [Running the Project](#running-the-project)
- [Classes](#classes)
- [Data Folder](#data-folder)
- [Usage](#usage)
- [Example](#example)
- [Testing](#testing)
- [Author](#author)
- [License](#license)



## Dependencies

- Java 8 or higher
- Maven 3.x or higher (if using Maven)
- Gradle 5.x or higher (if using Gradle)
- An IDE like IntelliJ IDEA or Eclipse (optional, but recommended)

### Running the Project

If you're compiling manually via the terminal:

```bash
javac -d out src/main/java/*.java
java -cp out Main
```

If you're using Maven:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main"
```

If you're using Gradle:

```bash
gradle build
gradle run
```


## Classes

### 1. `Point`
A simple class representing a point in a 2D coordinate system with x and y coordinates.
```java
public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
```

### 2. `StockData`
This class is responsible for handing the data.

#### Methods

- **`StockData(String filename)`**
    - Constructor to initialize the `StockData` instance with a filename from which to load stock data.
    - **Parameters**: `filename` (String) – The name of the file containing stock data.

- **`void loadSampleStock1()`**
    - Loads a predefined set of stock data points for "Stock 1". This data is hard-coded and represents stock prices over 10 days.

- **`void loadSampleStock2()`**
    - Loads a predefined set of stock data points for "Stock 2". Similar to `loadSampleStock1()`, but with different data values.

- **`boolean loadDataFromFile()`**
    - Loads stock data from a file located in the `Data/` directory. Each line in the file represents a stock price, with the x-coordinate being the day index (starting from 0) and the y-coordinate being the stock price.
    - The method reads the file line by line and parses the data into `Point` objects, storing them in the `stockDataPoints` list.
    - **Returns**: `true` if data is successfully loaded, `false` if the file is not found or there is an error reading the file.
    - If an error occurs during reading (e.g., invalid number format, I/O errors), error messages are printed to the standard error.

- **`List<Point> getStockDataPoints()`**
    - Gets the list of stock data points.
    - **Returns**: A list of `Point` objects representing the stock data.

- **`String getFilename()`**
    - Gets the filename of the stock data file.
    - **Returns**: The filename (String) used to load the stock data.

### 3. `PolynomialStockPredictor`
This class predicts stock prices based on polynomial regression of historical stock data. It uses the method of least squares to fit a polynomial model to the data and then uses the resulting polynomial to forecast future stock values.

#### Fields
- **polynomialTerms**: The number of terms in the polynomial (degree + 1).
- **EPSILON**: A small constant used to handle numerical stability during Gaussian elimination.
- **stockData**: An instance of the `StockData` class, containing the historical stock data points.

#### Constructor



```java
public PolynomialStockPredictor(StockData stockData, int degree){}
```   

- **Parameters**:
    - `stockData`: The stock data for prediction (type: `StockData`).
    - `degree`: The degree of the polynomial for regression (type: `int`).


- **Description**:
  Initializes the `PolynomialStockPredictor` with the provided stock data and polynomial degree. The constructor prepares the design matrix and target vector based on the stock data. It then calls the `performRegression` method to calculate the polynomial coefficients based on the provided stock data and degree of the polynomial.

- **Key Operations**:
    - Sets the `polynomialTerms` as `degree + 1` to define the number of polynomial terms.
    - Initializes matrices (`designMatrix` and `targetsVector`) for regression based on the number of data points in `stockData`.
    - Calls the `performRegression` method to perform the regression, solve for the polynomial coefficients, and display the forecasted stock values for future days.

### Methods

### `performRegression(double[][] designMatrixStock, double[] targetsStock)`

Processes the stock data and prepares matrices for regression. It builds the design matrix and target vector, then solves for the polynomial coefficients using the least squares method.


### `displayForecast(double[] regressionCoefficients)`

Displays the stock price predictions for the next few days based on the polynomial coefficients.


### `buildDesignMatrix(List<Point> dataPoints, double[][] A, double[] b)`

Builds the design matrix (`A`) and the target vector (`b`) from the stock data points.


### `solveLeastSquares(double[][] A, double[] b)`
Solves the least squares problem using the normal equation (`(A^T * A) * x = A^T * b`) to find the polynomial coefficients.


### `transpose(double[][] matrix)`
transpose a given matrix.

### `multiply(double[][] A, double[][] B)`
Multiplies two matrices `A` and `B`.


### `multiply(double[][] A, double[] b)`

Multiplies a matrix `A` with a vector `b`.


### `gaussianElimination(double[][] A, double[] b)`

Solves a system of linear equations using Gaussian elimination.


### `predictStockValue(double dayIndex, double[] polynomialCoeffsStock)`

Predicts the stock price for a given day using the polynomial regression model.




## Data folder
The Data/ directory contains example .txt files used by the application. These files are important for
demonstrating functionality and are required for running the project correctly.

You can replace or add your own files, but make sure they follow the expected format used by the code.

## Usage
To use this project, you need to create instances of the StockData class, load a file from the 
Data/ folder, and then pass it to the PolynomialStockPredictor class to obtain the predicted values.
Refer to the [Example](#example) section for sample usage.



## Example

To use this project, we have an illustrative example in Main.java, where we apply polynomial regression on
three different files.

```java
/**
 * Main class to execute the stock prediction process.
 */
public class Main {
    /**
     * The main method that loads stock data and initiates prediction for each stock file.
     * @param args Command line arguments
     */
    public static void main(String[] args) {

// Stock data for stock1.txt
        StockData stockData1 = new StockData("stock1.txt");
        if (stockData1.loadDataFromFile()) {  // Check if file is loaded successfully
            new PolynomialStockPredictor(stockData1, 3);
        } else {
            System.out.println("Skipping predictor for stock1.txt due to file load failure.");
        }

        // Stock data for stock3.txt
        StockData stockData3 = new StockData("stock3.txt");
        if (stockData3.loadDataFromFile()) {  // Check if file is loaded successfully
            new PolynomialStockPredictor(stockData3, 3);
        } else {
            System.out.println("Skipping predictor for stock3.txt due to file load failure.");
        }


        StockData stockData2 = new StockData("stock2.txt");
        if (stockData2.loadDataFromFile()) {  // Check if file is loaded successfully
            new PolynomialStockPredictor(stockData2, 3);
        } else {
            System.out.println("Skipping predictor for stock3.txt due to file load failure.");
        }



    }
}
```


## Testing

We created Java unit tests to validate that our classes (`StockData` and `PolynomialStockPredictor`) work as expected. For this purpose, we use the test classes (`StockDataTest` and `PolynomialStockPredictorTest`).

### Test Coverage:
- **Constructor Tests**: Ensure the constructors of `StockData` and `PolynomialStockPredictor` initialize the objects correctly.
- **Regression Tests**: Validate that the `performRegression` method calculates the correct polynomial coefficients.
- **Matrix Operations**: Ensure the correct behavior of matrix operations like matrix multiplication, Gaussian elimination, etc.
- **Forecasting Tests**: Confirm that the `displayForecast` method correctly predicts future stock prices based on historical data.

### Test Framework:
- **JUnit** is used for unit testing the functionality of both `StockData` and `PolynomialStockPredictor`.

### Mock Data:
- Mock stock data is provided to test the `PolynomialStockPredictor` with a variety of stock patterns (e.g., random data points, increasing/decreasing trends).

### Expected Outcomes:
- **StockData** should correctly store and retrieve stock data.
- **PolynomialStockPredictor** should return accurate polynomial coefficients and correctly forecast future stock prices.
- All matrix operations should execute without errors, and the results should be logically consistent (i.e., no unexpected NaN or infinity values).

### Running Tests:
You can run the tests using the following methods:

- **Maven**: Run the tests using Maven with the following command:
  ```bash
  mvn test
  ```
  

- **Gradle**: If you’re using Gradle as your build system, you can run the tests with:
  ```bash
  gradle test
  ```


- **IDE (IntelliJ IDEA, Eclipse, etc.)**: f you're using an IDE, you can run the tests directly from the IDE. Most IDEs have built-in support for running unit tests with JUnit. Simply right-click on the test class or method and select the "Run" option.
  



## Author
Developed by NikosPoly

## License
This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.****
