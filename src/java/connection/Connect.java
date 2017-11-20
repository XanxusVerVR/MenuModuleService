package connection;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mysql.authentication.data.ConnectData;
public class Connect {
    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());
        }
        try {
            dbConnection = (Connection) DriverManager.getConnection("jdbc:mysql://" + ConnectData.IP + ":" + ConnectData.PORT + "/" + ConnectData.DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&"+ "user=" + ConnectData.USER + "&password=" + ConnectData.PASSWORD + "");//連線的資料
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
