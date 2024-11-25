package gui;

import gui.components.*;
import gui.components.TextField;
import model.ModelException;
import model.database.Connection;

import model.entities.AttendeeAccount;
import service.managers.AttendeeAccountsManager;




import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ChangeUserInformationWindow {

    private AttendeeAccountsManager AAM;
    private JFrame window;

    public ChangeUserInformationWindow(String toBeChanged){
        InitWindow();

    }

    private void ChangeGeneral() throws ModelException {
        String ID = "31"; // Placeholder
        
        AttendeeAccount acc = AAM.getAttendee(ID);



        // We set each textField value to the current value in the database as to prevent accidental changes.
        // Since if the user dont want a change, they dont have to modify the fields as opposed to having to enter
        // previously identical information.
        // e.g. not change the name into empty string etc.

        // Initialize Name fields
        RegularLabel firstName = new RegularLabel("Enter new first Name:");
        RegularLabel lastName = new RegularLabel("Enter new last Name:");

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();

        String[] name = acc.getName().split(" ");

        window.add(firstName);
        window.add(firstNameField);
        firstNameField.setText(name[0]);

        window.add(lastName);
        window.add(lastNameField);
        lastNameField.setText(name[1]);


        // Initialize Mobile No Fields
        RegularLabel newMobile = new RegularLabel("Enter new Mobile Number:");
        TextField newMobileField = new TextField();

        window.add(newMobile);
        window.add(newMobileField);
        newMobileField.setText(String.valueOf(acc.getMobileNo()));

        // Initialize Organization fields

        RegularLabel newOrganization = new RegularLabel("Enter new Organization:");
        TextField newOrganizationField = new TextField();

        window.add(newOrganization);
        window.add(newOrganizationField);
        newOrganizationField.setText(acc.getOrganization());




        //Initialize password fields.
        RegularLabel passlabel = new RegularLabel("Enter new password:");
        RegularLabel passLabelConfirm = new RegularLabel("confirm new password:");

        PasswordField PF1 = new PasswordField();
        PasswordField PF2 = new PasswordField();


        // No need to set the text to old password value. Goes against design decision?
        window.add(passlabel);
        window.add(PF1);
        window.add(passLabelConfirm);
        window.add(PF2);


        CheckBox confirmation = new CheckBox("I confirm the change.");
        window.add(confirmation);
        gui.components.Button changeButton = new gui.components.Button("Change", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String newName = firstNameField.getText() + ' ' + lastNameField.getText();

                String PF1STR = new String(PF1.getPassword());
                String PF2STR = new String(PF2.getPassword());

                String newNum = newMobile.getText();

                String newOrg = newOrganizationField.getText();

                if(confirmation.isSelected()){

                    // If the passwords match.
                    if(!PF1STR.equals(acc.getPassword()) && PF1STR.equals(PF2STR)){
                        try {
                            AAM.updateAttendee(ID,"Password", PF2STR);
                        } catch (ModelException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    // If the newNum is not the same as the number in database and is a valid HK number.
                    if(!newNum.equals(acc.getMobileNo()) && newNum.length() == 8 && newNum.charAt(0) == '9'){
                        try {
                            AAM.updateAttendee(ID,"MobileNo", newNum);
                        } catch (ModelException ex) {
                            throw new RuntimeException(ex);
                        }                    }
                    // If the new name is not the same as the one in the database.
                    if(!newName.equals(acc.getName())){
                        try {
                            AAM.updateAttendee(ID,"Name", newName);
                        } catch (ModelException ex) {
                            throw new RuntimeException(ex);
                        }                    }
                    // If the newOrg is not the same as the one in the organization.
                    if(!newOrg.equals(acc.getOrganization())){
                        try {
                            AAM.updateAttendee(ID,"Organization", newOrg);
                        } catch (ModelException ex) {
                            throw new RuntimeException(ex);
                        }                    }

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

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());


        window.setVisible(true);



    }
}
