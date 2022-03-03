package calculations;

import com.google.common.annotations.VisibleForTesting;
import instrument.Instrument;
import instrument.Option;
import instrument.OptionType;
import instrument.Stock;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.forceDelete;

public class InstrumentDefinitionProvider {
    public static final String INSTRUMENTS_DB_FILE_NAME = "Instruments.db";
    @VisibleForTesting
    static final String DATABASE_LOCATION = "jdbc:sqlite:" + INSTRUMENTS_DB_FILE_NAME;
    private final File databaseFile = new File(INSTRUMENTS_DB_FILE_NAME);
    //assumption: we assume option names at least 1 hyphen in them
    private static final String OPTION_IDENTIFIER = "-";
    private volatile boolean connectionOpened = false;

    private Connection conn;

    public InstrumentDefinitionProvider() throws IOException {
        boolean exists = databaseFile.exists();
        if (exists) {
            forceDelete(databaseFile);
        }
        createNewDatabase();
    }

    private void createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.conn = connect();
        if (this.conn == null) {
            throw new RuntimeException("Unable to make connection");
        }
        this.connectionOpened = true;
    }

    public void closeConnection() throws SQLException {
        this.conn.close();
        this.connectionOpened = false;
    }

    public void createInstrumentTables() {
        String sqlStocks = "CREATE TABLE IF NOT EXISTS Stocks(\n"
                + " ticker text PRIMARY KEY,\n"
                + " volatility real\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sqlStocks);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlOptions = "CREATE TABLE IF NOT EXISTS Options(\n"
                + " ticker text PRIMARY KEY,\n"
                + " maturityDate text NOT NULL,\n"
                + " strikePrice real NOT NULL,\n"
                + " optionType text NOT NULL,\n"
                + " underlyingStock text NOT NULL\n"
                + ");";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sqlOptions);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connect() {
        try {
            return DriverManager.getConnection(DATABASE_LOCATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureConnectionOpened() throws SQLException {
        if (conn.isClosed()) {
            conn = connect();
        }
    }

    public Instrument getInstrumentByTicker(String ticker) {
        if (ticker.contains(OPTION_IDENTIFIER)) {
            return getOptionByTicker(ticker);
        }
        return getStockByTicker(ticker);
    }

    public void insertStocks(String ticker, double volatility) {
        String sql = "INSERT INTO Stocks(ticker, volatility) VALUES(?,?)";

        try {
            //ensureConnectionOpened();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            preparedStatement.setDouble(2, volatility);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertOptions(String ticker, String maturityDate, double strikePrice,
                              OptionType optionType, String underlyingStock) {
        String sql = "INSERT INTO Options(ticker, maturityDate, strikePrice, optionType, underlyingStock) VALUES(?,?,?,?,?)";

        try {
            ensureConnectionOpened();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            preparedStatement.setString(2, maturityDate);
            preparedStatement.setDouble(3, strikePrice);
            preparedStatement.setString(4, optionType.toString());
            preparedStatement.setString(5, underlyingStock);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Stock getStockByTicker(String ticker) {
        String sql = "SELECT ticker, volatility from Stocks WHERE ticker = ?";
        Stock stock = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stock = new Stock(ticker, resultSet.getDouble("volatility"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stock;
    }

    private Option getOptionByTicker(String ticker) {
        String sql = "SELECT ticker, maturityDate, strikePrice, optionType, underlyingStock " +
                "FROM Options WHERE ticker = ?";
        Option option = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                option = new Option(ticker,
                        Instant.parse(resultSet.getString("maturityDate")),
                        resultSet.getDouble("strikePrice"),
                        OptionType.fromText(resultSet.getString("optionType")),
                        getStockByTicker(resultSet.getString("underlyingStock")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return option;
    }

    public List<Option> getOptionByUnderlyingStock(String underlyingStock) {
        String sql = "SELECT ticker, maturityDate, strikePrice, optionType, underlyingStock " +
                "FROM Options WHERE underlyingStock = ?";
        final List<Option> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, underlyingStock);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new Option(resultSet.getString("ticker"),
                        Instant.parse(resultSet.getString("maturityDate")),
                        resultSet.getDouble("strikePrice"),
                        OptionType.fromText(resultSet.getString("optionType")),
                        getStockByTicker(resultSet.getString("underlyingStock"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @NotNull
    public ArrayList<String> getAllStockTickers() {
        String sql = "SELECT ticker FROM Stocks";
        ArrayList<String> tickers = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                tickers.add(resultSet.getString("ticker"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickers;
    }
}




