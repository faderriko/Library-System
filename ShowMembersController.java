
package showMembers;

import databaseHolder.DatabaseClass;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class ShowMembersController implements Initializable {
    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> memberIDColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> emailColumn;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
        
    } 
    private void initCol() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
    }
    
    private void loadData() {
        DatabaseClass databaseClass = DatabaseClass.getInstance();
        String qu = "SELECT * FROM MEMBER";
        ResultSet result = databaseClass.execQuery(qu);
        try{
            while(result.next()){
                String id = result.getString("id");
                String name = result.getString("name");
                String mobile = result.getString("mobile");
                String email = result.getString("email");
                
                list.add(new Member(name,id,mobile,email));
                tableView.setItems(list);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShowMembersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static class Member {
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        
        
       public Member(String name,String id,String mobile,String email){
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

        
    }
    
    
}
