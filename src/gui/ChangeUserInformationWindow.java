package gui;

import controller.Controller;
import gui.components.*;
import gui.components.TextField;
import model.ModelException;
import model.database.Connection;
import model.entities.Banquet;
import org.w3c.dom.Text;
import service.managers.AttendeeAccountsManager;




import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static javax.swing.JOptionPane.*;


public class ChangeUserInformationWindow {

    private AttendeeAccountsManager AAM;
    private JFrame window;
    private JPanel panel;
    private GridBagConstraints gbc = new GridBagConstraints();
    public ChangeUserInformationWindow(String toBeChanged){
        switch (toBeChanged){
            case "Password":
                PasswordChange();
                break;
            case "Salt":
                break;
            case "Name":
                NameChange();
                break;
            case "MobileNo":
                MobileNoChange();
                break;
            case "Organization":
                OrganizationChange();
                break;
            default:
                break;

        }
        InitWindow();

    }
    private void PasswordChange(){
        RegularLabel firstName = new RegularLabel("First Name:");
        RegularLabel lastName = new RegularLabel("Last Name:");

        PasswordField PF1 = new PasswordField();
        PasswordField PF2 = new PasswordField();


        CheckBox confirmation = new CheckBox("I confirm the change.");
        gui.components.Button changeButton = new gui.components.Button("Change", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String PF1STR = new String(PF1.getPassword());
                String PF2STR = new String(PF2.getPassword());

                if(confirmation.isSelected() && PF1STR.equals(PF2STR)){
                    int ID = 31; // Placeholder
                    try {
                        AAM.updateAttendee(ID,"Password", PF2STR);
                    } catch (ModelException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        window.setVisible(true);

    }
    private void SaltChange(){ // ?????

    }
    private void NameChange(){

        // Should get the ID from the UserWindow in order to modify the current user attributes

        RegularLabel firstName = new RegularLabel("First Name:");
        RegularLabel lastName = new RegularLabel("Last Name:");

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        CheckBox confirmation = new CheckBox("I confirm the change.");
        gui.components.Button changeButton = new gui.components.Button("Change", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = firstNameField.getText() + ' ' + lastNameField.getText();
                if(confirmation.isSelected()){
                    int ID = 31; // Placeholder
                    try {
                        AAM.updateAttendee(ID,"Organization", newName);
                    } catch (ModelException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        window.setVisible(true);
    }

    private void MobileNoChange(){

        // Should get the ID from the UserWindow in order to modify the current user attributes

        RegularLabel newMobile = new RegularLabel("First Name:");

        TextField newMobileField = new TextField();

        CheckBox confirmation = new CheckBox("I confirm the change.");
        gui.components.Button changeButton = new gui.components.Button("Change", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newNum = newMobile.getText();
                if(confirmation.isSelected()){

                    if(newNum.length() == 8 && newNum.charAt(0) == '9'){ // only limited to HK for now,
                        int ID = 31; // Placeholder
                        try {
                            AAM.updateAttendee(ID,"MobileNo", newNum);
                        } catch (ModelException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else{
                        JFrame popup = new JFrame();
                        popup.setSize(200,200);
                        popup.setTitle("Invalid Input");
                        TitleLabel title = new TitleLabel("Please enter a valid HK mobile number.");
                        popup.add(title);
                        popup.setVisible(true);
                    }

                }

            }
        });

        window.setVisible(true);

    }
    private void OrganizationChange(){

        // Should get the ID from the UserWindow in order to modify the current user attributes

        RegularLabel newOrganization = new RegularLabel("New Organization:");

        TextField newOrganizationField = new TextField();


        CheckBox confirmation = new CheckBox("I confirm the change.");
        gui.components.Button changeButton = new gui.components.Button("Change", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(confirmation.isSelected()){
                    int ID = 31; // Placeholder
                    try {
                        AAM.updateAttendee(ID,"Organization", newOrganizationField.getText());
                    } catch (ModelException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        window.setVisible(true);

    }


    public void InitWindow(){
        try {
            AAM = new AttendeeAccountsManager(new Connection());
        } catch (ModelException e) {
            throw new RuntimeException(e);
        }
        window = new JFrame();
        window.setTitle("Update User Information");
        window.getDefaultCloseOperation();
        window.setSize(640,360);
        window.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());


        window.setVisible(true);

    }




}
