/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package trainticketing;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Nandavahindra
 */
public class DashboardController implements Initializable {

    @FXML
    private Button nav_close;
    @FXML
    private Button nav_min;
    @FXML
    private Label main_name;
    @FXML
    private Button nav_dashboard;
    @FXML
    private Button nav_av;
    @FXML
    private Button nav_tix;
    @FXML
    private Button nav_order;
    @FXML
    private Button nav_signout;
    @FXML
    private AnchorPane dash_form;
    @FXML
    private Label dash_train_num;
    @FXML
    private Label dash_addon;
    @FXML
    private Label dash_balance;
    @FXML
    private AnchorPane tix_form;
    @FXML
    private TableView<?> tix_table;
    @FXML
    private TableColumn<?, ?> tix_tab_class;
    @FXML
    private TableColumn<?, ?> tix_tab_seat;
    @FXML
    private TableColumn<?, ?> tix_tab_addprice;
    @FXML
    private Button tix_select;
    @FXML
    private TextField tix_name;
    @FXML
    private TextField tix_from;
    @FXML
    private TextField tix_to;
    @FXML
    private TextField tix_date;
    @FXML
    private TextField tix_class;
    @FXML
    private TextField tix_seat;
    @FXML
    private TextField tix_dep;
    @FXML
    private Button tix_reset_detail;
    @FXML
    private TextField tix_arr;
    @FXML
    private TextField tix_totalprice;
    @FXML
    private TextField tix_passname;
    @FXML
    private TextField tix_phone;
    @FXML
    private Button tix_book;
    @FXML
    private Button tix_reset_pass;
    @FXML
    private AnchorPane av_form;
    @FXML
    private TextField av_name;
    @FXML
    private TextField av_from;
    @FXML
    private TextField av_to;
    @FXML
    private TextField av_deo;
    @FXML
    private TextField av_arr;
    @FXML
    private TextField av_price;
    @FXML
    private Button av_book;
    @FXML
    private TextField av_date;
    @FXML
    private TableView<trainData> av_table;
    @FXML
    private TableColumn<trainData, String> av_tab_name;
    @FXML
    private TableColumn<trainData, String> av_tab_from;
    @FXML
    private TableColumn<trainData, String> av_tab_to;
    @FXML
    private TableColumn<trainData, String> av_tab_dep;
    @FXML
    private TableColumn<trainData, String> av_tab_arr;
    @FXML
    private TableColumn<trainData, String> av_tab_date;
    @FXML
    private TextField av_search;
    @FXML
    private StackPane dash_window;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    //database connection
    String url = "jdbc:postgresql://localhost:5432/fpmbd1";
    String user = "postgres";
    String password = "admin67";
    @FXML
    private AnchorPane order_form;
    @FXML
    private AnchorPane etix;
    
    public ObservableList<trainData> availableTrainTrainData()
    {
        ObservableList<trainData> trainListData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM kereta";
        try {
            connect = DriverManager.getConnection(url, user, password);
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            trainData train;
            while(result.next())
            {
                train = new trainData(result.getInt("id_kereta"),
                                  result.getString("nama_kereta"),
                                  result.getString("asal"), 
                                  result.getString("tujuan"), 
                                  result.getString("jam_berangkat"), 
                                  result.getString("jam_tiba"), 
                                  result.getInt("harga"), 
                                  result.getDate("tanggal_pemberangkatan"));
                trainListData.add(train);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trainListData;
    }
    
    private ObservableList<trainData> availableTrainTrainListData;
    public void availableTrainShowData()
    {
        availableTrainTrainListData = availableTrainTrainData();
        
        av_tab_name.setCellValueFactory(new PropertyValueFactory<>("nama_kereta"));
        av_tab_from.setCellValueFactory(new PropertyValueFactory<>("asal"));
        av_tab_to.setCellValueFactory(new PropertyValueFactory<>("tujuan"));
        av_tab_dep.setCellValueFactory(new PropertyValueFactory<>("jam_berangkat"));
        av_tab_arr.setCellValueFactory(new PropertyValueFactory<>("jam_tiba"));
        av_tab_date.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        
        av_table.setItems(availableTrainTrainListData);
    }
    
    @FXML
    public void availableTrainSelectData()
    {
        trainData train = av_table.getSelectionModel().getSelectedItem();
        int num = av_table.getSelectionModel().getSelectedIndex();
        if((num -1) < -1)
        {
            return;
        }
        
        av_name.setText(String.valueOf(train.getNama_kereta()));
        av_from.setText(String.valueOf(train.getAsal()));
        av_to.setText(String.valueOf(train.getTujuan()));
        av_deo.setText(String.valueOf(train.getJam_berangkat()));
        av_arr.setText(String.valueOf(train.getJam_tiba()));
        av_price.setText(String.valueOf(train.getHarga()));
        av_date.setText(String.valueOf(train.getTanggal()));
    }
    
    @FXML
    public void search()
    {
         av_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }
    
  public void filterTable(String searchInput) {
    ObservableList<trainData> filtered = FXCollections.observableArrayList();
    for(trainData train : availableTrainTrainListData)
    {
         if (train.getNama_kereta().toLowerCase().contains(searchInput.toLowerCase())) {
                filtered.add(train);
        }
         else if (train.getAsal().toLowerCase().contains(searchInput.toLowerCase())) {
                filtered.add(train);
        }
         else if (train.getTujuan().toLowerCase().contains(searchInput.toLowerCase())) {
                filtered.add(train);
        }
         else if (train.getHarga().toString().toLowerCase().contains(searchInput.toLowerCase())) {
                filtered.add(train);
        }
         else if (train.getTanggal().toString().toLowerCase().contains(searchInput.toLowerCase())) {
                filtered.add(train);
        }
    }
    av_table.setItems(filtered);
}
  
  public void bookNow()
  {
      
      
      
      dash_form.setVisible(false);
      av_form.setVisible(false);
      tix_form.setVisible(true);
      order_form.setVisible(false);
      
      nav_dashboard.setStyle("-fx-background-color: transparent;");
      nav_av.setStyle("-fx-background-color: transparent;");
      nav_tix.setStyle("-fx-background-color: #948e99;");
      nav_order.setStyle("-fx-background-color: transparent;");
  }

    
    @FXML
    public void switchForm(ActionEvent event)
    {
        if(event.getSource() == nav_dashboard)
        {
            dash_form.setVisible(true);
            av_form.setVisible(false);
            tix_form.setVisible(false);
            order_form.setVisible(false);
            
            nav_dashboard.setStyle("-fx-background-color: #948e99;");
            nav_av.setStyle("-fx-background-color: transparent;");
            nav_tix.setStyle("-fx-background-color: transparent;");
            nav_order.setStyle("-fx-background-color: transparent;"); 
        }else if (event.getSource() == nav_av){
            dash_form.setVisible(false);
            av_form.setVisible(true);
            tix_form.setVisible(false);
            order_form.setVisible(false);
            nav_dashboard.setStyle("-fx-background-color: transparent;");
            nav_av.setStyle("-fx-background-color: #948e99;");
            nav_tix.setStyle("-fx-background-color: transparent;");
            nav_order.setStyle("-fx-background-color: transparent;"); 
            
            availableTrainShowData();
            search();
        }else if (event.getSource() == nav_tix){
            dash_form.setVisible(false);
            av_form.setVisible(false);
            tix_form.setVisible(true);
            order_form.setVisible(false);
            
            nav_dashboard.setStyle("-fx-background-color: transparent;");
            nav_av.setStyle("-fx-background-color: transparent;");
            nav_tix.setStyle("-fx-background-color: #948e99;");
            nav_order.setStyle("-fx-background-color: transparent;"); 
        }else if (event.getSource() == nav_order){
            dash_form.setVisible(false);
            av_form.setVisible(false);
            tix_form.setVisible(false);
            order_form.setVisible(true);
            
            nav_dashboard.setStyle("-fx-background-color: transparent;");
            nav_av.setStyle("-fx-background-color: transparent;");
            nav_tix.setStyle("-fx-background-color: transparent;");
            nav_order.setStyle("-fx-background-color: #948e99;"); 
        }
    }
    

    public void set_name()
    {
        main_name.setText(LoggedIn.getName());
    }

    
    @FXML
    public void close()
    {
        System.exit(0);
    }
    
    @FXML
    public void minimize()
    {
        Stage stage = (Stage)dash_window.getScene().getWindow();
        stage.setIconified(true);
    }
    private double x;
    private double y;
    
    
    @FXML
    public void signout(){
                    
        try {
                    dash_window.getScene().getWindow().hide();
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
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
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                    
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nav_dashboard.setStyle("-fx-background-color: #948e99;");
        set_name();
    }    
    
}
