package DashBoard;


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

public class MainScreenController implements Initializable {
    
    @FXML
    private Label name;
    
    @FXML
    private Label body;
    
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
             
                name.setText(rs.getString("Name"));                
                
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
    public void history(MouseEvent event) throws IOException{
        
        Parent fxml = FXMLLoader.load(getClass().getResource("/TransactionHistory/TransactionHistory.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
        
    }
    
    @FXML
    public void amount(MouseEvent event) throws IOException{
        
        Parent fxml = FXMLLoader.load(getClass().getResource("/TransferAmount/TransferAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
        
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        body.setText("Babbar Rajput Bank is a Banking and Financial service bank owned by yash.\nThe bank was founded in 2020.It is the fast growing private sector bank in India,\nboth in terms of business and its network.");
        setInfo();
    }    
    
}
