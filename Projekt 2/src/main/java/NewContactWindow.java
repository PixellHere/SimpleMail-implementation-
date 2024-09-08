import javax.swing.*;
import java.awt.*;

public class NewContactWindow extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;

    public NewContactWindow(MainWindow mainWindow){
        super(mainWindow,"Create new contact",true);

        this.setLayout(new GridLayout(2,1));


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        gbc.weightx=0.25;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("First name: "),gbc);
        gbc.gridy=1;
        mainPanel.add(new JLabel("Last name: "),gbc);
        gbc.gridy=2;
        mainPanel.add(new JLabel("Email: "),gbc);
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(200, 24));
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        gbc.weightx=1.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(firstNameField,gbc);
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(200, 24));
        gbc.gridy=1;
        mainPanel.add(lastNameField,gbc);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 24));
        gbc.gridy=2;
        mainPanel.add(emailField,gbc);
        this.add(mainPanel);


        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save contact");
        JButton cancelButton = new JButton("Cancel");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        this.add(buttonsPanel);


        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> this.dispose());


        this.setSize(400, 300);
        this.setLocationRelativeTo(mainWindow);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private boolean isEmailCorrect(String email){
        if(email.contains("@")){
            if (email.contains(".")){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    private void saveContact(){
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String emailAdress = emailField.getText().toLowerCase();

        if(firstName.isEmpty()||lastName.isEmpty()||emailAdress.isEmpty()){
            JOptionPane.showMessageDialog(this, "At least one of the fields is empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isEmailCorrect(emailAdress)) {
            JOptionPane.showMessageDialog(this, "Email adress is in wrong format", "Error", JOptionPane.ERROR_MESSAGE);
        } else if(MainWindow.checkIsContactExsist(emailAdress)) {
            JOptionPane.showMessageDialog(this, "Contact with this email already exist", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            MainWindow.contactList.add(new Contact(firstName,lastName,emailAdress));
            this.dispose();
        }
    }
}
