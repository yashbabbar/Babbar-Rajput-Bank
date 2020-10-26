package Deposit;

import Bank.DbUtil;
import Login.LoginScreenController;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepositController implements Initializable {

    
    @FXML
    private Label account_no;
    
    @FXML
    private Label balance;
    
    @FXML
    private TextField amt_field;
    
    @FXML
    private TextField pin_field;
    
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int hour = cal.get(Calendar.HOUR);
    int minute = cal.get(Calendar.MINUTE);
    int second = cal.get(Calendar.SECOND);
    int daynight = cal.get(Calendar.AM_PM);
    
    DateFormat dateformat = new SimpleDateFormat("yyyy/mm/dd");
    Date d = new Date();
    String date =dateformat.format(d);
    
    LocalTime localTime=LocalTime.now();
    DateTimeFormatter dt = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time =localTime.format(dt);
        
    public void setInfo(){
        
        Connection con=null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        try
        {
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DbUtil.getConnection();
            String sql= "SELECT * FROM userdata WHERE AccountNo=?";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, LoginScreenController.acc);
            
            rs = ps.executeQuery();
            if(rs.next()){
             
                account_no.setText(rs.getString("AccountNo")); 
                balance.setText(rs.getString("Balance"));
                
            }
            
            
        }catch(Exception e)
        {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in Login");
                a.setContentText("There is some error. Try Again");
                a.showAndWait();
        }
    } 
    
    public void depositButton(){
        
        Connection con=null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
        try
        {
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DbUtil.getConnection();
            String sql= "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, LoginScreenController.acc);
            ps.setString(2, pin_field.getText());
            
            rs = ps.executeQuery();
            if(rs.next()){
             
                int da=Integer.parseInt(amt_field.getText());
                int ta=Integer.parseInt(balance.getText());
                
                    int total=ta+da;
                    String sql1="UPDATE userdata SET Balance='"+total+"'WHERE AccountNo='"+LoginScreenController.acc+"'"; 
                    ps=con.prepareStatement(sql1);
                    ps.execute();
                    
                    String sql2="INSERT INTO deposit(AccountNo, DepositAmount, NewAmount, Date, Time) VALUES (?,?,?,?,?)";
                    ps=con.prepareStatement(sql2);
                    ps.setString(1, LoginScreenController.acc);
                    ps.setString(2, String.valueOf(da));
                    ps.setString(3, String.valueOf(total));
                    ps.setString(4, date);
                    ps.setString(5, time);
                    
                    int i = ps.executeUpdate();
                    if(i>0)
                    {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Amount Deposit");
                        a.setHeaderText("Amount Deposit Sucessfully");
                        a.setContentText("Amount"+da+"has been successfully Deposited\n"+"Current Balance"+total);
                        a.showAndWait();
                        
                        amt_field.setText("");
                        pin_field.setText("");
                        balance.setText(String.valueOf(total));
                        
                    }
                     
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in depositing amount");
                a.setContentText("You enter wrong pin. Enter it again!!!");
                a.showAndWait();
            }
            
        }catch(Exception e)
        {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in depositing");
                a.setContentText("There is some error. Try Again");
                a.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfo();
        
    }    
    
}
