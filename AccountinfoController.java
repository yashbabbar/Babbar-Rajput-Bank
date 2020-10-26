package AccountInfomation;

import Bank.DbUtil;
import Login.LoginScreenController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AccountinfoController implements Initializable {
    
    @FXML
    private Text account_no;
    
    @FXML
    private Text sex;
    
    @FXML
    private Text account_type;
    
    @FXML
    private Text religion;
    
    @FXML
    private Label balance;
    
    @FXML
    private Pane dashboard_main;
    
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
                balance.setText(rs.getString("Balance"));
                account_no.setText(rs.getString("AccountNo"));
                sex.setText(rs.getString("Gender"));
                account_type.setText(rs.getString("AccountType"));
                religion.setText(rs.getString("Religion"));
                
                
            }
            else
            {
                 Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error is Login Account");
                a.setContentText("Your account  number or password is wrong. Enter it again!!!");
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
    
     @FXML
     public void withdraw(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/WithdrawAmount/Withdrawamount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
    
      @FXML
     public void deposit(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/Deposit/Deposit.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setInfo();
        
    }    
    
}
