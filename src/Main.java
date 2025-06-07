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