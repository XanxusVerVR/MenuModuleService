package main;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import execute.action.Delete;
import java.io.IOException;
import java.io.PrintWriter;
import javabean.ClientInsertMealRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import execute.action.Insert;
import execute.action.Select;
import java.util.logging.Level;
import java.util.logging.Logger;
import javabean.ServerResponse;

public class MainServlet extends HttpServlet {

    int thisQueryNumber;
    ServerResponse sr;
    String statusJson;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        GetHttpPostBody gb = new GetHttpPostBody();//創造客戶端請求的Body內容的物件
        String payloadRequest = gb.getBody(request);//取得客戶端請求的Body內容

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();//創造Gson物件

        ClientInsertMealRequest enums = gson.fromJson(payloadRequest, ClientInsertMealRequest.class);//jsonSerialization為原始的json字串，此行把json字串放進物件之中


        thisQueryNumber = enums.getQueryNumber();//取出query的動作，看是新增還是刪除

        if (thisQueryNumber == 2) {//2就是要新增資料
            String thisMealName = enums.getMealData().getMealName();//取出餐點名稱
            int thisMealPrice = enums.getMealData().getMealPrice();//取出餐點價格
            String thisMealDescription = enums.getMealData().getMealDescription();//取出餐點描述
            String thisMealCategory = enums.getMealData().getMealCategory();//取出餐點類型
            String thisMealImage = enums.getMealData().getMealImage();//取出餐點圖片
            Insert it = new Insert(thisMealName, thisMealPrice, thisMealDescription, thisMealCategory, thisMealImage);
            try {
                it.insertData();
            } catch (Exception ex) {
                Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            sr = new ServerResponse(true);//創造一個回應狀態的物件
            statusJson = gson.toJson(sr);//製作status的Json
            out.write(statusJson);
        }
        else if(thisQueryNumber == 3){//thisQueryNumber=3 就是刪除
//            int thisQueryNumber = enums.getQueryNumber();
            int thisMealNumber = enums.getMealData().getMealNumber();
            Delete d = new Delete(thisMealNumber);
            try {
                d.deleteData();
            } catch (Exception ex) {
                Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            sr = new ServerResponse(true);//創造一個回應狀態的物件
            statusJson = gson.toJson(sr);//製作status的Json
            out.write(statusJson);
        }
        else{//查詢
            Select s = new Select();
            String menuJson = s.getMenuJson();
            out.print(menuJson);
        }
    }

}
