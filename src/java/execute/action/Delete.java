package execute.action;

import com.mysql.jdbc.Connection;
import connection.Connect;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.sql.SQLException;

public class Delete {

    PrintWriter out = null;
    private int mealNumber;//要被刪除的餐點在資料庫中的號碼
    private Connection con;
    public Delete(int mealNumber) {
        this.mealNumber = mealNumber;
    }

    public void deleteData() throws Exception {
        try {
            con = Connect.getDBConnection();
            String query = "delete from menu where mealSerialNumber = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, this.mealNumber);
            preparedStmt.execute();
            con.close();
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
