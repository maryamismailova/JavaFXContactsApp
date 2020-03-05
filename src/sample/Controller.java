package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    BorderPane borderPane;
    @FXML
    TableView<Contact> contactsTableView;
//    @FXML
//    TableColumn<String, Contact> firstNameColumn;
//    @FXML
//    TableColumn<String, Contact> lastNameColumn;
//    @FXML
//    TableColumn<String, Contact> phoneNumberColumn;
//    @FXML
//    TableColumn<String, Contact> notesColumn;

    ContactData contactData;

    public void initialize(){
        contactData=new ContactData();

        TableColumn<Contact, String> firstNameColumn=new TableColumn<>("First Name");
        TableColumn<Contact, String> lastNameColumn=new TableColumn<>("Last Name");
        TableColumn<Contact, String> phoneNumberColumn=new TableColumn<>("Phone Number");
        TableColumn<Contact, String> notesColumn=new TableColumn<>("Notes");

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        contactsTableView.getColumns().addAll(firstNameColumn, lastNameColumn, phoneNumberColumn, notesColumn);
        contactsTableView.setItems(contactData.getContacts());
        contactsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contactsTableView.getSelectionModel().selectFirst();
    }



    @FXML
    private void showEditContactDialog() {
        Contact contact=(Contact)contactsTableView.getSelectionModel().getSelectedItem();
        showContactEditor(contact);
    }
    @FXML
    private void showNewContactDialog() {
        showContactEditor(null);
    }

    public void showContactEditor(Contact contact){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Add a new contact");
        dialog.setHeaderText("Use this dialog to add or edit a contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        ContactController controller = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        if(contact!=null){
            controller.setNameField(contact.getFirstName());
            controller.setLastNameField(contact.getLastName());
            controller.setPhoneNumberField(contact.getPhoneNumber());
            controller.setNotesField(contact.getNotes());
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Contact newContact = controller.processContact();
            System.out.println("Received a new contact");
            if(newContact!=null){
                if(contact==null)contactData.addContact(newContact);
                else contactData.editContact(newContact, contact);
                System.out.println("added a contact");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Contact creation failed");
                alert.setContentText("A contact with no phone number couldn't be create");
                alert.show();
            }
//            todoListView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public  void showDeleteContactDialog(){
        Contact contact=(Contact)contactsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete a contact");
        alert.setHeaderText("Contact to delete: " + contact.getFirstName()+" "+contact.getLastName());
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            contactData.deleteContact(contact);
        }
    }

    @FXML
    public void handleExit() {
        contactData.saveContacts();
        Platform.exit();
    }

}
