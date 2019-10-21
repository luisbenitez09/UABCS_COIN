package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author luisangel
 */
public class connectionManager {
    private static final String DB_NAME = "UABCS_COIN?useUnicode=true&useJDBCCompliantTimeZoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/" + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Beol970929";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    
    public static Connection getConnection () throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName(DRIVER_NAME);
        connection = DriverManager.getConnection(URL, DB_USER, DB_PASS);
        return connection;
    }
}
