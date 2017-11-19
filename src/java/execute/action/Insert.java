package execute.action;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import mysql.authentication.data.ConnectData;

public class Insert {

    PrintWriter out = null;
    private String mealName;
    private int mealPrice;
    private String mealDescription;
    private String mealCategory;
    private String mealImage;

    public Insert() {
        this.mealName = null;
        this.mealPrice = 0;
        this.mealDescription = null;
        this.mealCategory = null;
        this.mealImage = null;
    }

    public Insert(String mealName, int mealPrice, String mealDescription, String mealCategory, String mealImage) {
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealDescription = mealDescription;
        this.mealCategory = mealCategory;
        this.mealImage = mealImage;
    }

    public void insertData() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://" + ConnectData.IP + ":" + ConnectData.PORT + "/" + ConnectData.DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&"
                    + "user=" + ConnectData.USER + "&password=" + ConnectData.PASSWORD + "");//連線的資料
            Statement statement = (Statement) connect.createStatement();
            statement.executeUpdate("INSERT INTO `menu` ( mealName, mealPrice, mealDescription, mealCategory, mealImage)VALUES( \'" + this.mealName + "\', " + this.mealPrice + ", \'" + this.mealDescription + "\', \'" + this.mealCategory + "\', \'" + this.mealImage + "\')");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
