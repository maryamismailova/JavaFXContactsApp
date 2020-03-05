package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;

    public void setNameField(String nameField) {
        this.nameField.setText(nameField);
    }

    public void setLastNameField(String lastNameField) {
        this.lastNameField.setText(lastNameField);
    }

    public void setPhoneNumberField(String phoneNumberField) {
        this.phoneNumberField.setText(phoneNumberField);
    }

    public void setNotesField(String notesField) {
        this.notesField.setText(notesField);
    }

    public Contact processContact(){
        Contact newContact= new Contact();
        System.out.println("HERE1");
        System.out.println(newContact==null?"NULL":"Not NULL");
        if(!nameField.getText().isEmpty())newContact.setFirstName(nameField.getText());
        else newContact.setFirstName("Name");
        System.out.println("HERE2");
        if(!lastNameField.getText().isEmpty())newContact.setLastName(lastNameField.getText());
        else newContact.setLastName("Last Name");
        System.out.println("HERE3");
        if(!phoneNumberField.getText().isEmpty())newContact.setPhoneNumber(phoneNumberField.getText());
        else return null;
        System.out.println("HERE4");
        if(!notesField.getText().isEmpty())newContact.setNotes(notesField.getText());
        else newContact.setNotes("");

        System.out.println("New Contact: "+newContact);
        return newContact;
    }


}
