
package libraryaddbook;

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



public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton CancelButton;
    
    DatabaseClass databaseClass;
    @FXML
    private JFXTextField publisher;
    @FXML
    private AnchorPane rootPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      databaseClass = DatabaseClass.getInstance();
    }    

    

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveBook(ActionEvent event) {
        String bookID = id.getText();
        String bookTitle = title.getText();
        String bookAuthor = author.getText();
        String bookPublisher = publisher.getText();
        
        if(bookID.isEmpty()||bookTitle.isEmpty()||bookAuthor.isEmpty()||bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all Fields!");
            alert.showAndWait();
            return;
        }
        
        String qu = "INSERT INTO BOOK VALUES("
               + "'" + bookID +"',"
               + "'" + bookTitle +"',"
               + "'" + bookAuthor +"',"
               + "'" + bookPublisher +"',"
               + "" + "true" +""
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
            alert.setContentText("Failed!");
            alert.showAndWait();
        }
    }
    
}
