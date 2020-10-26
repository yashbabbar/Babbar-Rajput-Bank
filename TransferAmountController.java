package TransferAmount;

import Bank.DbUtil;
import Login.LoginScreenController;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class TransferAmountController implements Initializable {

        Connection con=null;
        PreparedStatement  ps = null;
        ResultSet rs = null;
    
    @FXML
    private Label account_no;
    
    @FXML
    private Label balance;
    
    @FXML
    private TextField account_no_field;
    
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
    
    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    Date d = new Date();
    String date =dateformat.format(d);
    
    LocalTime localTime=LocalTime.now();
    DateTimeFormatter dt = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time =localTime.format(dt);
        
    public void setInfo(){
        

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
    
    public void checkButton(){
        
        try
        {
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DbUtil.getConnection();
            String sql= "SELECT * FROM userdata WHERE AccountNo=?";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, account_no_field.getText());
            
            rs = ps.executeQuery();
            if(rs.next()){
             
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Account Information");
                a.setHeaderText("Below is the Information of account.");
                a.setContentText("Account No ="+account_no_field.getText()+"\n Name="+rs.getString("Name")+"\n Mobile Number ="+rs.getString("MobileNo"));
                a.showAndWait();
                
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
    
    public void transferAmountButton() throws SQLException, ClassNotFoundException{
        
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
             
                int transfer_amt = Integer.parseInt(amt_field.getText());
                int ta = Integer.parseInt(balance.getText());
                if(transfer_amt>ta){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Error is Transfer Money");
                    a.setContentText("Your don't have enough balance. Enter it again!!!");
                    a.showAndWait();
                    
                }
                else
                {
                    int total=ta-transfer_amt;
                    String sql1="UPDATE userdata SET Balance='"+total+"'WHERE AccountNo='"+LoginScreenController.acc+"'"; 
                    ps=con.prepareStatement(sql1);
                    ps.execute();
                    
                    String sql2= "SELECT * FROM userdata WHERE AccountNo=?";
                    ps=con.prepareStatement(sql2);
                    ps.setString(1, account_no_field.getText());
                    
                    rs = ps.executeQuery();
                     if(rs.next()){
                    
                    int cur=Integer.parseInt(amt_field.getText());
                    int prev=Integer.parseInt(rs.getString("Balance"));
                
                    int total1=cur+prev;
                    
                    
                    String sql4="UPDATE userdata SET Balance='"+total1+"'WHERE AccountNo='"+account_no_field.getText()+"'"; 
                    ps=con.prepareStatement(sql4);
                    ps.execute();
                    
                    String sql5="INSERT INTO transferamount(AccountNo, Amount, SendTo, Date, Time) VALUES (?,?,?,?,?)";
                    ps=con.prepareStatement(sql5);
                    ps.setString(1, LoginScreenController.acc);
                    ps.setString(2, String.valueOf(amt_field.getText()));
                    ps.setString(3, String.valueOf(account_no_field.getText()));
                    ps.setString(4, date);
                    ps.setString(5, time);
                    
                    int i = ps.executeUpdate();
                    if(i>0)
                    {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Amount Transfer");
                        a.setHeaderText("Amount Transfered Sucessfully");
                        a.setContentText("Amount"+cur+"has been successfully transfered\n"+"To Account No ="+account_no_field.getText());
                        a.showAndWait();
                        
                        account_no_field.setText("");
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
                }
            }
        }catch(Exception e)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Login");
            a.setContentText("There is some error. Try Again"+e.getMessage());
            a.showAndWait();
        }
    } 
            
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setInfo();
        
    }    
    
}
