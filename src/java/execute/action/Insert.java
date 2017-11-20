package execute.action;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import connection.Connect;
import java.io.PrintWriter;
import java.sql.SQLException;

public class Insert {

    private Connection con;
    private PrintWriter out = null;
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
            con = Connect.getDBConnection();
            Statement statement = (Statement) con.createStatement();
            statement.executeUpdate("INSERT INTO `menu` ( mealName, mealPrice, mealDescription, mealCategory, mealImage)VALUES( \'" + this.mealName + "\', " + this.mealPrice + ", \'" + this.mealDescription + "\', \'" + this.mealCategory + "\', \'" + this.mealImage + "\')");
        } catch (SQLException e) {
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
