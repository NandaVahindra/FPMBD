/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package trainticketing;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

/**
 *
 * @author Nandavahindra
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private AnchorPane SignIn_form;
    @FXML
    private Button In_min;
    @FXML
    private Button In_close;
    @FXML
    private TextField In_username;
    @FXML
    private PasswordField In_password;
    @FXML
    private Button In_login;
    @FXML
    private Hyperlink In_hyperlink;
    @FXML
    private AnchorPane SignUp_form;
    @FXML
    private Button Up_min;
    @FXML
    private Button Up_close;
    @FXML
    private TextField Up_email;
    @FXML
    private PasswordField Up_password;
    @FXML
    private Button Up_sign;
    @FXML
    private Hyperlink Up_hyperlink;
    @FXML
    private TextField Up_username;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    //database connection
    String url = "jdbc:postgresql://localhost:5432/fpmbd1";
    String user = "postgres";
    String password = "admin67";
    
    private double x ;
    private double y ;
    
    //function to check the input register phone number is already registered in the database or not
    public boolean no_hp_cek(String stringToCheck)
    {
        String sql = "SELECT COUNT(*) FROM pemesan WHERE no_hp = ?";
        boolean stringExists = false;

    try (Connection connection = DriverManager.getConnection(url,user,password);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, stringToCheck);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                stringExists = count > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return stringExists;
    }
    
    //function to check the input of phone number when register is a numeric string
    public boolean isStringNumeric(String stringToCheck) {
    boolean isNumeric = false;

    // Use regular expression to check if the string contains only numeric characters
    if (stringToCheck.matches("\\d+")) {
        isNumeric = true;
    }

    return isNumeric;
}
    //sign up button function
    public void sign_Up()
    {
        try {
            connect = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "INSERT INTO pemesan (nama_pemesan,no_hp) VALUES (?,?)";
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, Up_username.getText());
            prepare.setString(2, Up_email.getText());
            
            Alert alert;
            //check if the text field is empty or not
            if(Up_username.getText().isEmpty() || Up_email.getText().isEmpty())
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else if(no_hp_cek(Up_email.getText())) //check the phone number already used or not
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Phone Number Already used");
                alert.showAndWait();
            }
            else if(!isStringNumeric(Up_email.getText())) //check if the phone number is a valid numeric string
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please input a valid Phone Number");
                alert.showAndWait();
            }
            else{
                    prepare.execute();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully created an account!");
                    alert.showAndWait();
                    Up_email.clear();
                    Up_username.clear();
                    //switch to sign in page after successfully register an account
                    SignUp_form.setVisible(false);
                    SignIn_form.setVisible(true);
                    In_username.clear();
                    In_password.clear();
                    
                    
            }
        }
        catch(SQLException e) {
        }
    }

    //sign in button function
    public void sign_In()
    {
        try {
            connect = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "SELECT nama_pemesan, no_hp FROM pemesan WHERE nama_pemesan = ? and no_hp = ?";
        
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, In_username.getText());
            prepare.setString(2, In_password.getText());
            
            result = prepare.executeQuery();
            
            Alert alert;
            if(In_username.getText().isEmpty() || In_password.getText().isEmpty()) //check if the field empty or not
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{ 
                if(result.next())  //iterate data on database to check if there is a matches account
                {   
                    LoggedIn log = new LoggedIn();
                    log.setName(In_username.getText());
                    In_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    root.setOnMousePressed((MouseEvent event) ->{
                     x = stage.getX() - event.getScreenX();
                     y = stage.getY() - event.getScreenY();
                    });
                    root.setOnMouseDragged((MouseEvent event)->{
                    stage.setX(event.getScreenX()+x);
                    stage.setY(event.getScreenY()+y); 
             
                    stage.setOpacity(.8);
                    });
                    root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);});
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                    
                }
                else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Wrong Username or Password!");
                    alert.showAndWait();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close()
    {
        System.exit(0);
    }
    
    public void signIn_min()
    {
        Stage stage = (Stage)SignIn_form.getScene().getWindow();
        stage.setIconified(true);
    }
    public void signUp_min()
    {
        Stage stage = (Stage)SignUp_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    public void switchform(ActionEvent event)
    {
        if(event.getSource() == In_hyperlink)
        {
            Up_email.clear();
            Up_username.clear();
            SignIn_form.setVisible(false);
            SignUp_form.setVisible(true);
            
        }
        else if(event.getSource() == Up_hyperlink)
        {
            In_username.clear();
            In_password.clear();
            SignUp_form.setVisible(false);
            SignIn_form.setVisible(true);
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
