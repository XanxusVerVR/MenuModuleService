package execute.action;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import mysql.authentication.data.ConnectData;

public class Delete {

    PrintWriter out = null;
    private int mealNumber;//要被刪除的餐點在資料庫中的號碼

    public Delete(int mealNumber) {
        this.mealNumber = mealNumber;
    }

    public void deleteData() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://" + ConnectData.IP + ":" + ConnectData.PORT + "/" + ConnectData.DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&"
                    + "user=" + ConnectData.USER + "&password=" + ConnectData.PASSWORD + "");//連線的資料

            String query = "delete from menu where mealSerialNumber = ?";
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, this.mealNumber);

            // execute the preparedstatement
            preparedStmt.execute();

            connect.close();

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
