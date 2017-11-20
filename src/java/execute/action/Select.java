package execute.action;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.Connection;
import connection.Connect;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javabean.MealData;

public class Select {
    PrintWriter out = null;
    private String getMenuJson;
    private Connection con;
    public Select() {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            con = Connect.getDBConnection();
            String query = "select * from menu";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            int rowTotal = getTotalRow();
            MealData[] md = new MealData[rowTotal];
            int i = 0;
            while (rs.next()) {
                md[i] = new MealData();
                md[i].setMealNumber(rs.getInt("mealSerialNumber"));
                md[i].setMealName(rs.getString("mealName"));
                md[i].setMealPrice(rs.getInt("mealPrice"));
                md[i].setMealDescription(rs.getString("mealDescription"));
                md[i].setMealCategory(rs.getString("mealCategory"));
                md[i].setMealImage(rs.getString("mealImage"));
                i++;
            }
            setGetMenuJson(gson.toJson(md));
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

    public void setGetMenuJson(String getMenuJson) {
        this.getMenuJson = getMenuJson;
    }

    public String getMenuJson() {
        
        return getMenuJson;
    }

    public int getTotalRow() {//取得總比數，有多少筆資料
        int size = 0;
        try {
            con = Connect.getDBConnection();
            String query = "select * from menu";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            size = 0;
            while (rs.next()) {
                size++;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return size;
    }
}
//連線模組化https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/