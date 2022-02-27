package calculations;

import java.sql.*;

public class InstrumentDefinitionProvider {
    private static Connection conn;

    public static void createNewDatabase(String fileName) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/" + fileName;

        try {
            Connection connection = DriverManager.getConnection(url);
            if (connection == null) {
                throw new RuntimeException("Unable to make connection");
            }
//            DatabaseMetaData meta = connection.getMetaData();
//            System.out.println("The driver name is " + meta.getDriverName());
//            System.out.println("A new database has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        //ToDo: Path below is hardcoded at the moment
        String dbPath = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";

        String sqlStocks = "CREATE TABLE IF NOT EXISTS stocks(\n"
                + " id integer PRIMARY KEY,\n"
                + " ticker text NOT NULL,\n"
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

    public static void insertStocks(String ticker, double volatility) {
        String sql = "INSERT INTO Stocks(ticker, volatility) VALUES(?,?)";

        try {
            //ensureConnectionOpened();
            String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticker);
            preparedStatement.setDouble(2, volatility);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertOptions(String ticker, String maturityDate, double strikePrice,
                                     String optionType, double interestRate, double volatility) {

        String sql = "INSERT INTO Options(ticker, maturityDate, strikePrice, optionType, interestRate, volatility) VALUES(?,?,?,?,?,?)";

        try {
//            ensureConnectionOpened();
            String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
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

    public static void getAllStocks() {

        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
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
                        resultSet.getDouble("volatility"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void deleteAllStocks() {
        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
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


    public static void getAllOptions() {

        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
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

    public static void deleteAllOptions() {
        String url = "jdbc:sqlite:C:/Users/N B C/Desktop/portfolio_value_engine/Test.db";
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




