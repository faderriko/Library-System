
package mainHolder;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import databaseHolder.DatabaseClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainFXMLController implements Initializable {

    
    @FXML
    private HBox memberInfo;
    @FXML
    private TextField showMemberField;
    @FXML
    private HBox bookInfo;
    @FXML
    private Text bookTitle;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    @FXML
    private Text memberName;
    
    @FXML
    private Text memberMobile;
    @FXML
    private Text memberEmail;
    @FXML
    private TextField bookIDField;
    
    DatabaseClass databaseClass;
    @FXML
    private JFXTextField textFieldBookID;
    @FXML
    private ListView<String> issueDataView;
    
    
    Boolean isReadyForSubmission = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseClass = DatabaseClass.getInstance();
        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(memberInfo, 1);
        
    }    

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/libraryAddMember/addMember.fxml","Add Member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/libraryaddbook/FXMLDocument.fxml"," Add Book");
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("/showMembers/showMembers.fxml","Member List");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadWindow("/libraryshowbook/bookslist/showbookslist.fxml","Book List");
    }
    
    void loadWindow(String loc,String title){
        
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @FXML
    private void showMemberInfo(ActionEvent event) {
        clearMemberCache();
        boolean flag = false;
        String id = showMemberField.getText();
        String qu = "SELECT * FROM MEMBER WHERE id ='" + id + "'";
        ResultSet result = databaseClass.execQuery(qu);
        
        try{
            while(result.next())
            {
                String mName = result.getString("name");
                String mMobile = result.getString("mobile");
                String mEmail = result.getString("email");
                
                
                
                
                    memberName.setText(mName);
                    memberMobile.setText(mMobile);
                    memberEmail.setText(mEmail);
                
                
                    flag = true;    
                 
            }
                
                if(!flag)
                {
                    memberName.setText("NO SUCH MEMBER AVAILABLE!!!");
                
                } 
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showBookInfo(ActionEvent event) {
        clearBookCache();
        boolean flag = false;
        String id = bookIDField.getText();
        String qu = "SELECT * FROM BOOK WHERE id ='" + id + "'";
        ResultSet result = databaseClass.execQuery(qu);
        
        try{
            while(result.next())
            {
                String bTitle = result.getString("title");
                String bAuthor = result.getString("author");
                Boolean bStatus = result.getBoolean("isAvail");
                
                
                
                
                    bookTitle.setText(bTitle);
                bookAuthor.setText(bAuthor);
                String status = (bStatus)?"Available" : "Not Available"; 
                bookStatus.setText(status);
                flag = true;    
                 
            }
                
                if(!flag)
                {
                    bookTitle.setText("NO SUCH BOOK AVAILABLE!!!");
                
                } 
                
                
            
        } catch (SQLException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void clearMemberCache()
    {
        memberName.setText("");
        memberMobile.setText("");
        memberEmail.setText("");
        
    }
    
    void clearBookCache()
    {
        bookTitle.setText("");
        bookAuthor.setText("");
        bookStatus.setText("");
    }

    @FXML
    private void issueBookOperation(ActionEvent event) {
        
        String bookID = bookIDField.getText();
        String memberID = showMemberField.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to issue the book "+ bookTitle.getText()+" to "+ memberName.getText()+" ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK)
        {
            String insertInto = "INSERT INTO ISSUE(bookID,memberID) VALUES ("
                    +"'"+ bookID +"',"
                    +"'"+ memberID +"')"; 
            String updateTable = "UPDATE BOOK SET isAvail = false WHERE id = '"+bookID+"'";
             
            
            if(databaseClass.execAction(insertInto) && databaseClass.execAction(updateTable))
            {
            
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Issue Successful");
                alert1.setHeaderText(null);
                alert1.setContentText("issue Complete");
                alert1.showAndWait();
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Failed");
                alert1.showAndWait();
                
            }
        }
        else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelled");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Cancelled");
                alert1.showAndWait();
        }
    }

    @FXML
    private void loadBookInformation(ActionEvent event) {
        isReadyForSubmission = false;
        ObservableList<String>  issueData = FXCollections.observableArrayList();
        String bookID = textFieldBookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = '" + bookID + "'";  
        ResultSet result = databaseClass.execQuery(qu);
        
        try{
            while(result.next()){
                String id = bookID;
                String mMemberID = result.getString("memberID");
                Timestamp missueTime = result.getTimestamp("issueTime");
                int mRenewCount = result.getInt("renew_count");
                
                
                issueData.add("Issue Date and Time : " +missueTime.toGMTString());
                issueData.add("Renew count : " +mRenewCount);
                
                issueData.add("BOOK INFORMATION:-\n");
                qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";  
                ResultSet result1 = databaseClass.execQuery(qu);
                while(result1.next())
                {
                    issueData.add("         Book Name : " + result1.getString("title"));
                    issueData.add("         Book Author: " + result1.getString("author"));
                    issueData.add("         Book Publisher: " + result1.getString("publisher"));
                    
                
                }
                issueData.add("MEMBER ISSUED TO:-\n");
                qu = "SELECT * FROM MEMBER WHERE id = '" + mMemberID + "'";  
                result1 = databaseClass.execQuery(qu);
                while(result1.next())
                {
                    issueData.add("         Name: " + result1.getString("name"));
                    issueData.add("         Mobile: " + result1.getString("mobile"));
                    issueData.add("         Email: " + result1.getString("email"));
                    
                }
                
                isReadyForSubmission = true;
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        issueDataView.getItems().setAll(issueData);
    }

    @FXML
    private void loadSubmission(ActionEvent event) {
        if(!isReadyForSubmission)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Select Book to submit!!");
                    alert.showAndWait();
            return;
        }
        
        
        String bookID = textFieldBookID.getText();
        String submit = "DELETE FROM ISSUE WHERE bookID = '"+bookID+"'";
        String remove = "UPDATE BOOK SET isAvail = TRUE WHERE id = '"+bookID+"'"; 
        
        if(databaseClass.execAction(submit) && databaseClass.execAction(remove))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Submision Successful");
                    alert.showAndWait();
                    return;
        }
        else{
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Submission Failed");
                    alert.showAndWait();
                    return;
        }
    }

    @FXML
    private void renewBook(ActionEvent event) {
        if(!isReadyForSubmission)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Select Book to renew!!");
                    alert.showAndWait();
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renew Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to renew the book ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK){
          String renew = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE bookID = '"+ bookIDField.getText()+"'";   
            
            if(databaseClass.execAction(renew)){
               Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
               alert1.setTitle("Renew Successful");
               alert1.setHeaderText(null);
               alert1.setContentText("Renew Complete");
               alert1.showAndWait(); 
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Renew Failed");
                alert1.showAndWait();
            }
        }
        else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelled");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Cancelled");
                alert1.showAndWait();
        }
        
    }
    
}
 