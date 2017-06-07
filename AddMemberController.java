
package libraryAddMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import databaseHolder.DatabaseClass;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AddMemberController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton CancelButton;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;

    
    @FXML
    private AnchorPane rootPane;
    
    DatabaseClass databaseClass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         databaseClass = DatabaseClass.getInstance();
    }    

    @FXML
    private void saveMember(ActionEvent event) {
        String mID = id.getText();
        String mName = name.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();
        
        if(mID.isEmpty()||mName.isEmpty()||mMobile.isEmpty()||mEmail.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all Fields!");
            alert.showAndWait();
            return;
        }
        String qu = "INSERT INTO MEMBER VALUES("
               + "'" + mID +"',"
               + "'" + mName +"',"
               + "'" + mMobile +"',"
               + "'" + mEmail +"'"
               +")";
        System.out.println(qu);
        if(databaseClass.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success!");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error Occurred");
            alert.showAndWait();
        }

    
}

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
