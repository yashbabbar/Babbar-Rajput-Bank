package DashBoard;


import Bank.DbUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Login.LoginScreenController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
            
    @FXML
    private Pane dashboard_main;
    
    @FXML
    private Text name;
    
    @FXML
    private FontAwesomeIconView icon;
    
    @FXML
    private Circle profilepic;
    
    @FXML
    private void closeApp(MouseEvent event) {
        
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private void ninimizeApp(MouseEvent event) {
        
        Stage stage=(Stage) icon.getScene().getWindow();
        stage.setIconified(true);
        
    }

     public void setData(){
        
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
                InputStream is = rs.getBinaryStream("ProfilePic");
                OutputStream os = new FileOutputStream(new File("yash.png"));
                byte[] content = new byte[1024];
                int size = 0;
                while((size = is.read(content)) != -1){
                        
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image img=new Image("file:yash.png", false);
                profilepic.setFill(new ImagePattern(img));

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
     public void click(MouseEvent event){
         
         xOffset =event.getSceneX();
         yOffset =event.getSceneY();
         
     }
     
     @FXML
     public void drag(MouseEvent event){
      
         LoginScreenController.stage.setX(event.getScreenX()-xOffset);
         LoginScreenController.stage.setY(event.getScreenY()-yOffset);
         
     }
     
     @FXML
     public void accountInformation(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/AccountInfomation/Accountinfo.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
     
    @FXML
     public void withdraw(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/WithdrawAmount/Withdrawamount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
     
     @FXML
     public void transactionHistory(MouseEvent event) throws IOException{
     
        Parent fxml = FXMLLoader.load(getClass().getResource("/TransactionHistory/TransactionHistory.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
     }
     
     @FXML
     public void deposit(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/Deposit/Deposit.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
     
     @FXML
     public void changepin(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/Changepin/Changepin.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
     
     @FXML
     public void transferAmount(MouseEvent event) throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("/TransferAmount/TransferAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
   
    @FXML
     public void mainScreen() throws IOException{
      
        Parent fxml = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
         
     }
     
    @FXML
     public void logout(MouseEvent event) throws IOException{
         
                ((Node)event.getSource()).getScene().getWindow().hide();
                Parent root=FXMLLoader.load(getClass().getResource("/Login/LoginScreen.fxml")); 
                Scene scene=new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/design/design.css" ).toExternalForm());
                Stage stage=new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    xOffset =event.getSceneX();
                    yOffset =event.getSceneY();
                
                }
            });
        
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                
                }
            });
         
     }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
        try 
        {
            
            mainScreen();
        
        } catch (IOException ex) 
        {
           
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        
    }    
    
}
