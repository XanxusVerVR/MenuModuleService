package execute.action;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javabean.MealData;
import mysql.authentication.data.ConnectData;

public class Select {
    PrintWriter out = null;
    private String getMenuJson;
    public Select() {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://" + ConnectData.IP + ":" + ConnectData.PORT + "/" + ConnectData.DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&"
                    + "user=" + ConnectData.USER + "&password=" + ConnectData.PASSWORD + "");//連線的資料
            String query = "select * from menu";
            PreparedStatement preparedStmt = connect.prepareStatement(query);

            // execute select SQL stetement
            int size=0;
            ResultSet rs = preparedStmt.executeQuery();
//            while (rs.next()) {
//                size++;
//            }
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
//                String mealName = rs.getString("mealName");
//              int mealPrice = rs.getString("mealPrice");
//                String mealDescription = rs.getString("mealDescription");
//                String mealCategory = rs.getString("mealCategory");
//                String mealImage = rs.getString("mealImage");
//                System.out.println("mealName : " + mealName);
//                System.out.println("mealPrice : " + mealPrice);
//                System.out.println("mealDescription : " + mealDescription);
//                System.out.println("mealCategory : " + mealCategory);
//                System.out.println("mealImage : " + mealImage);
                i++;
            }
            setGetMenuJson(gson.toJson(md));
//            System.out.println(rowTotal);
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

    public void setGetMenuJson(String getMenuJson) {
        this.getMenuJson = getMenuJson;
    }

    public String getMenuJson() {
        
        return getMenuJson;
    }

    public int getTotalRow() {//取得總比數，有多少筆資料
        int size = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://" + ConnectData.IP + ":" + ConnectData.PORT + "/" + ConnectData.DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&"
                    + "user=" + ConnectData.USER + "&password=" + ConnectData.PASSWORD + "");//連線的資料
            String query = "select * from menu";
            PreparedStatement preparedStmt = connect.prepareStatement(query);

            // execute select SQL stetement
            ResultSet rs = preparedStmt.executeQuery();
            size = 0;
            while (rs.next()) {
                size++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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