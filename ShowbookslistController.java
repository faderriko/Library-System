
package libraryshowbook.bookslist;

import databaseHolder.DatabaseClass;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class ShowbookslistController implements Initializable {
    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> bookIDColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    } 

    private void initCol() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    private void loadData() {
        DatabaseClass databaseClass = DatabaseClass.getInstance();
        String qu = "SELECT * FROM BOOK";
        ResultSet result = databaseClass.execQuery(qu);
        try{
            while(result.next()){
                String id = result.getString("id");
                String title = result.getString("title");
                String author = result.getString("author");
                String publisher = result.getString("publisher");
                Boolean availability = result.getBoolean("isAvail");
                
                list.add(new Book(title,id,author,publisher,availability));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShowbookslistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
   public static class Book{
        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;
        
       public Book(String title,String id,String author,String publisher,Boolean availability){
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.availability = new SimpleBooleanProperty(availability);
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }
        
        
    }
    
}
