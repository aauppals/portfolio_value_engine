package calculations;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class InstrumentDefinitionProvider {
    private static Connection conn;

    public static void createNewDatabase(String fileName) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbPath = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;

        try {
            Connection connection = DriverManager.getConnection(dbPath);
            if (connection == null) {
                throw new RuntimeException("Unable to make connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(String fileName) {
        //ToDo: Path below is hardcoded at the moment
        String dbPath = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;

        String sqlStocks = "CREATE TABLE IF NOT EXISTS Stocks(\n"
                + " id integer PRIMARY KEY,\n"
                + " ticker text NOT NULL,\n"
                + " price real,\n"
                + " expectedReturn real,\n"
                + " volatility real\n"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(dbPath);
            Statement stmt = conn.createStatement();
            stmt.execute(sqlStocks);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlOptions = "CREATE TABLE IF NOT EXISTS Options(\n"
                + " id integer PRIMARY KEY,\n"
                + " ticker text NOT NULL,\n"
                + " maturityDate text NOT NULL,\n"
                + " strikePrice real NOT NULL,\n"
                + " optionType text NOT NULL,\n"
                + " interestRate real,\n"
                + " volatility real\n"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(dbPath);
            Statement stmt = conn.createStatement();
            stmt.execute(sqlOptions);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    private static Connection connection() {
//        String dbPath = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
//        try {
//            conn = DriverManager.getConnection(dbPath);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return conn;
//    }
//
//    private static void ensureConnectionOpened() throws SQLException {
//        if (conn.isClosed()) {
//            connection();
//        }
//    }

    public static void insertStocks(String fileName, String ticker, double price, double expectedReturn, double volatility) {
        String sql = "INSERT INTO Stocks(ticker, price, expectedReturn, volatility) VALUES(?,?,?,?)";

        try {
            //ensureConnectionOpened();
            String dbPath = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
            try {
                conn = DriverManager.getConnection(dbPath);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            preparedStatement.setDouble(2, price);
            preparedStatement.setDouble(3, expectedReturn);
            preparedStatement.setDouble(4, volatility);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateStockPriceByTicker(String fileName, String ticker, double price) {
        String sql = "UPDATE Stocks SET price = ? WHERE ticker = ?";

        try {
            String dbPath = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
            try {
                conn = DriverManager.getConnection(dbPath);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, price);
            preparedStatement.setString(2, ticker);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertOptions(String fileName, String ticker, String maturityDate, double strikePrice,
                                     String optionType, double interestRate, double volatility) {

        String sql = "INSERT INTO Options(ticker, maturityDate, strikePrice, optionType, interestRate, volatility) VALUES(?,?,?,?,?,?)";

        try {
//            ensureConnectionOpened();
            String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            preparedStatement.setString(2, maturityDate);
            preparedStatement.setDouble(3, strikePrice);
            preparedStatement.setString(4, optionType);
            preparedStatement.setDouble(5, interestRate);
            preparedStatement.setDouble(6, volatility);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static CalcObject getStockByTicker(String fileName, String ticker) {
        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String sql = "SELECT price,expectedReturn,volatility from STOCKS WHERE ticker = ?";
        CalcObject calcObject = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            // loop through the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                calcObject = new CalcObject((resultSet.getDouble("price")),
                        resultSet.getDouble("expectedReturn"),
                        resultSet.getDouble("volatility"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return calcObject;
    }

    public static void getAllStocks(String fileName) {

        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql = "SELECT * FROM Stocks";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // loop through the result set
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t" +
                        resultSet.getString("ticker") + "\t" +
                        resultSet.getDouble("price") + "\t" +
                        resultSet.getDouble("expectedReturn") + "\t" +
                        resultSet.getDouble("volatility"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static @NotNull
    ArrayList<String> getAllStockTickers(String fileName) {

        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql = "SELECT ticker FROM Stocks";
        ArrayList<String> tickers = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // loop through the result set
            while (resultSet.next()) {
                tickers.add(resultSet.getString("ticker"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickers;
    }

    public static void deleteAllStocks(String fileName) {
        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String sql = "DELETE FROM Stocks";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void getAllOptions(String fileName) {

        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql = "SELECT * FROM Options";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // loop through the result set
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t" +
                        resultSet.getString("ticker") + "\t" +
                        resultSet.getString("maturityDate") + "\t" +
                        resultSet.getDouble("strikePrice") + "\t" +
                        resultSet.getString("optionType") + "\t" +
                        resultSet.getDouble("interestRate") + "\t" +
                        resultSet.getDouble("volatility"));

            }
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAllOptions(String fileName) {
        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String sql = "DELETE FROM Options";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}




