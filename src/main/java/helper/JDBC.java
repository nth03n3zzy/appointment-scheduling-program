package helper;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * The JDBC class provides methods for opening and closing a database connection using JDBC driver.
 * @author Anthony Collins.
 */
public abstract class JDBC {
    /**
     * establishes protocol to connect to the database.
     */
    private static final String protocol = "jdbc";
    /**
     * establishes vendor of the database driver.
     */
    private static final String vendor = ":mysql:";
    /**
     * establishes location of database server.
     */
    private static final String location = "//localhost/";
    /**
     * name of the database to connect to.
     */
    private static final String databaseName = "client_schedule";
    /**
     * establishes JDBC URl to connect to.
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName; //+ "?connectionTimeZone = SERVER"; // LOCAL
    /**
     * establishes driver used to connect to the database.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * establishes username for database connection.
     */
    private static final String userName = "sqlUser"; // Username
    /**
     * establishes password for database connection.
     */
    private static String password = "Passw0rd!"; // Password
    /**
     * The connection object used to establish a connection to the database.
     */
    public static Connection connection;  // Connection Interface

    /**
     * opens a connection to the database using properties specified in the JDBC class.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * closes the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}

