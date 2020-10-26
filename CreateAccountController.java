package CreateAccount;

import Bank.DbUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import Login.Banking;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CreateAccountController implements Initializable {
    
    private FileChooser filechooser= new FileChooser();
    private File file;
    
    private FileInputStream fis;
    
    @FXML
    private ImageView pic;
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField idcardno;
    
    @FXML
    private TextField mobileno;
    
    @FXML
    private TextField city;
    
    @FXML
    private TextField address;
    
    @FXML
    private TextField accountno;
    
    @FXML
    private TextField pin;
    
    @FXML
    private TextField balance;
    
    @FXML
    private TextField answer;
    
    @FXML
    private DatePicker dob;
    
    @FXML
    private ComboBox gender;
    
    @FXML
    private ComboBox martialstatus;
    
    @FXML
    private ComboBox religion;
    
    @FXML
    private ComboBox accountype;
    
    @FXML
    private ComboBox questions;
    
    ObservableList<String> list=FXCollections.observableArrayList("Male","Female","Other");
    ObservableList<String> list1=FXCollections.observableArrayList("Single","Maried");
    ObservableList<String> list2=FXCollections.observableArrayList("Hindu","Christian","Muslim","Punjabi","Other");
    ObservableList<String> list3=FXCollections.observableArrayList("Saving","Current");
    ObservableList<String> list4=FXCollections.observableArrayList("What is your Pet Name?","What is your Childhood Town?","What is your Nickname?");
    @FXML
    private Button browse_pic;
    

    @FXML
    public void backtologin(MouseEvent event) throws IOException{
        
        Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/Login/LoginScreen.fxml")));
    }
    
    @FXML
    public void closeApp(MouseEvent event){
        
        Platform.exit();
        System.exit(0);
        
    }
    
    @FXML
    public void setUpPic(MouseEvent event){
        
        filechooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*png","*jpg"));
        file=filechooser.showOpenDialog(null);
        if(file!=null){
            Image img=new Image(file.toURI().toString(), 150, 150, true, true);
            pic.setImage(img);
            pic.setPreserveRatio(true);
        }   
        
    }
    
    public boolean validateName(){
        
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(name.getText());
        if(m.find() && m.group().equals(name.getText())){
            return true;
        }else{
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Wrong Name");
            a.setHeaderText("Your Name is Wrong");
            a.setContentText("Please enter character only in name. Try Again");
            a.showAndWait(); 
            return false;
        }
     
    }
    
    public boolean validateMobileno(){
        
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobileno.getText());
        if(m.find() && m.group().equals(mobileno.getText())){
            return true;
        }else{
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Wrong Mobileno");
            a.setHeaderText("Your Mobileno is Wrong");
            a.setContentText("Please enter number only in name. Try Again");
            a.showAndWait(); 
            return false;
        }
     
    }
    
    public boolean validateIdcardno(){
        
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(idcardno.getText());
        if(m.find() && m.group().equals(idcardno.getText())){
            return true;
        }else{
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Wrong Idcardno");
            a.setHeaderText("Your Idcardno is Wrong");
            a.setContentText("Please enter number only in name. Try Again");
            a.showAndWait(); 
            return false;
        }
     
    }
    
    public boolean validateBalance(){
        
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(balance.getText());
        if(m.find() && m.group().equals(balance.getText())){
            return true;
        }else{
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Invalod Balance");
            a.setHeaderText("Your Balance is Wrong");
            a.setContentText("Please enter Cureent Balance. Try Again");
            a.showAndWait(); 
            return false;
        }
     
    }
    

    public void clearAllFields(){
        
        name.clear();
        idcardno.clear();
        mobileno.clear();
        gender.getSelectionModel().clearSelection();
        martialstatus.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        city.clear();
        address.clear();
        pin.clear();
        accountype.getSelectionModel().clearSelection();
        balance.clear();
        questions.getSelectionModel().clearSelection();
        answer.clear();
        Image img = new Image("/Images/150.png");
        pic.setImage(img);
        accountno.setText(String.valueOf(generateAccountno()));
        
    }

    public int generateAccountno(){
        
        Random rand = new Random();
        int num = rand.nextInt(899999) + 100000;
        return num;
        
    }
    
    @FXML
   public void newAccount(MouseEvent event){
        
        Connection con=null;
        PreparedStatement  ps = null;
        try
        {
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DbUtil.getConnection();
            
            if(validateName() && validateMobileno() && validateIdcardno() && validateBalance()){
            String sql= "INSERT INTO userdata(Name, ICN, MobileNo, Gender, MartialStatus, Religion, DOB, City, Address, AccountNo, PIN, AccountType, Balance, SecurityQuestion, Answer, ProfilePic) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, name.getText());
            ps.setString(2, idcardno.getText());
            ps.setString(3, mobileno.getText());
            ps.setString(4, (String) gender.getValue());
            ps.setString(5, (String) martialstatus.getValue());
            ps.setString(6, (String) religion.getValue());
            ps.setString(7, ((TextField)dob.getEditor()).getText());
            ps.setString(8, city.getText());
            ps.setString(9, address.getText());
            ps.setString(10, accountno.getText());
            ps.setString(11, pin.getText());
            ps.setString(12, (String) accountype.getValue());
            ps.setString(13, balance.getText());
            ps.setString(14, (String) questions.getValue());
            ps.setString(15, answer.getText());
            fis=new FileInputStream(file);
            ps.setBinaryStream(16, (InputStream)fis, (int)file.length());
            
            int i=ps.executeUpdate();
            if(i>0){
                Alert a = new Alert(AlertType.INFORMATION);
                a.setTitle("Account Created");
                a.setHeaderText("Account Created");
                a.setContentText("Your account has been created sucessfully. You can now login with your login");
                a.showAndWait();
                
                clearAllFields();
                
            }
            else
            {
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Account not Creating");
                a.setContentText("Your account  is not created. there is some error. Try Again");
                a.showAndWait();
            }
            }
            
        }catch(Exception e)
        {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error is Creating Account");
            a.setContentText("Your account  is not Connected. there is some technical issue"+ e.getMessage());
            a.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gender.setItems(list);
        martialstatus.setItems(list1);
        religion.setItems(list2);
        accountype.setItems(list3);
        questions.setItems(list4);  
        accountno.setText(String.valueOf(generateAccountno())); 
        accountno.setEditable(false);
        
    }    
    
}
