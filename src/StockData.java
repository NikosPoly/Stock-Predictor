/**
 * Stores and processes stock data, prepares matrices, and calculates polynomial coefficients.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockData {

    private List<Point> stockDataPoints;
    private final String filename;

    /**
     * Constructor to initialize StockData with the filename to load data from.
     * @param filename The name of the file containing stock data
     */
    public StockData(String filename) {
        this.filename = filename;
        stockDataPoints = new ArrayList<>();
    }

    /**
     * Adds a predefined set of stock data points (stock 1).
     */
    public void loadSampleStock1() {
        stockDataPoints.addAll(List.of(
                new Point(0, 0.834), new Point(1, 0.804), new Point(2, 0.798),
                new Point(3, 0.74), new Point(4, 0.76), new Point(5, 0.744),
                new Point(6, 0.76), new Point(7, 0.796), new Point(8, 0.784), new Point(9, 0.784)
        ));
    }

    /**
     * Adds a predefined set of stock data points (stock 2).
     */
    public void loadSampleStock2() {
        stockDataPoints.addAll(List.of(
                new Point(0, 0.858), new Point(1, 0.852), new Point(2, 0.82),
                new Point(3, 0.786), new Point(4, 0.784), new Point(5, 0.758),
                new Point(6, 0.76), new Point(7, 0.788), new Point(8, 0.774), new Point(9, 0.744)
        ));
    }

    /**
     * Loads stock data from a file located in the "Data/" directory using the specified filename.
     * Each line in the file is expected to represent a single numeric value (a stock price),
     * which is parsed and stored as a {@link Point} object, where the x-coordinate represents
     * the day index (starting from 0) and the y-coordinate represents the stock price.
     *
     * @return {@code true} if the file is successfully read and data is loaded, {@code false} if the file is not found.
     *
     * <p>Note: This method will print error messages to standard error if the file is not found,
     * a line contains an invalid number format, or other I/O errors occur.
     * In case of exceptions other than {@link FileNotFoundException}, it still returns {@code true}
     * after printing the error, assuming partial or complete data may have been loaded.</p>
     */
    public boolean loadDataFromFile() {
        String filePath = "Data/" + filename;

        try {
            File file = new File(filePath);
            // Check if file exists
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int x = 0; // Start from day 0
            List<Point> stock = new ArrayList<>();

            // Read file line by line
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        double y = Double.parseDouble(line);
                        stock.add(new Point(x++, y)); // X is generated automatically
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format at line " + x + ": " + line);
                    }
                }
            }

            br.close();
            this.stockDataPoints = stock; // Store the loaded stock data

            System.out.println("Loaded " + stock.size() + " data points from " + filePath);
            return  true;
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("I/O error while reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
        return true;
    }


    /**
     * Gets the stock data points.
     * @return The list of stock data points
     */
    public List<Point> getStockDataPoints() {
        return stockDataPoints;
    }

    /**
     * Gets the filename of the stock data file.
     * @return The filename of the stock data
     */
    public String getFilename() {
        return filename;
    }
}
