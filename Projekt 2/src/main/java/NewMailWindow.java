import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import javax.swing.*;
import java.awt.*;

public class NewMailWindow extends JDialog {
    private JTextField recipient;
    private Contact recipientMail;
    private JTextField topic;
    private JTextArea message;

    public NewMailWindow(MainWindow mainWindow){
        super(mainWindow,"Write new mail",true);
        this.setLayout(new BorderLayout());

        // Mail Configuration Panel
        JPanel mailConfPanel = new JPanel();
        mailConfPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.weightx=0.25;
        gbc.weighty=0.0;
        mailConfPanel.add(new JLabel("To:"),gbc);

        gbc.gridy=1;
        mailConfPanel.add(new JLabel("Topic:"),gbc);

        recipient = new JTextField();
        recipient.setEnabled(false);
        recipient.setPreferredSize(new Dimension(200,24));
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.weightx=1.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mailConfPanel.add(recipient,gbc);

        topic = new JTextField();
        topic.setPreferredSize(new Dimension(200,24));
        gbc.gridy=1;
        mailConfPanel.add(topic,gbc);

        JButton pickContactButton = new JButton("Choose contact");
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.weightx=0.25;
        gbc.weighty=0.0;
        mailConfPanel.add(pickContactButton,gbc);

        pickContactButton.addActionListener(e ->
                showContactList(mainWindow)
        );

        this.add(mailConfPanel,BorderLayout.PAGE_START);


        //Message Content
        message = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(message);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);


        //Button Panel
        JPanel buttonsPanel = new JPanel();
        JButton sendButton = new JButton("Send email");
        JButton cancelButton = new JButton("Cancel");
        buttonsPanel.add(sendButton);
        buttonsPanel.add(cancelButton);
        this.add((buttonsPanel),BorderLayout.SOUTH);

        sendButton.addActionListener(e -> {
            if(isMessageCorrect(recipientMail,topic.getText(),message.getText())) {
                sendEmail();
                this.dispose();
            }else
                JOptionPane.showMessageDialog(this, "At least one of the fields is empty", "Error", JOptionPane.ERROR_MESSAGE);
        });
        cancelButton.addActionListener(e -> this.dispose());



        this.setSize(450, 600);
        this.setLocationRelativeTo(mainWindow);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void showContactList(MainWindow mainWindow) {
        JDialog contactDialog = new JDialog(mainWindow, "Select Contact", true);
        contactDialog.setLayout(new BorderLayout());

        DefaultListModel<Contact> contactDefaultListModel = new DefaultListModel<>();
        contactDefaultListModel.addAll(MainWindow.contactList);
        JList<Contact> contactJList = new JList<>(contactDefaultListModel);
        contactJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(contactJList);
        contactDialog.add(scrollPane, BorderLayout.CENTER);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e ->  {
                Contact selectedContact = contactJList.getSelectedValue();
                if (selectedContact != null) {
                    recipient.setText(selectedContact.getAllInfo() );
                    recipientMail=selectedContact;
                }
                contactDialog.dispose();
        });
        contactDialog.add(selectButton, BorderLayout.SOUTH);

        contactDialog.setSize(300, 200);
        contactDialog.setLocationRelativeTo(mainWindow);
        contactDialog.setVisible(true);
    }

    private boolean isMessageCorrect(Contact contact, String topic, String message){
        if(contact==null||topic.isEmpty()||message.isEmpty())
            return false;
        else
            return true;
    }

    private void sendEmail(){
        MainWindow.mailList.add(new Mail(recipientMail, topic.getText(), message.getText()));
        MainWindow.mailDefaultListModel.addElement(new Mail(recipientMail, topic.getText(), message.getText()));
        System.out.println("Email has been sent");
        System.out.println("------------");
        System.out.println("To: " + recipientMail.getAllInfo());
        System.out.println("Topic: " + topic.getText());
        System.out.println(message.getText());

        Email email = EmailBuilder
                .startingBlank()
                .from("s30734@pjwstk.edu.pl")
                .to(recipientMail.getEmail())
                .withSubject(topic.getText())
                .withPlainText(message.getText())
                .buildEmail();


        try(Mailer mailer = MailerBuilder
            .withSMTPServer("smtp.gmail.com",587,"","") // Wpisz dane konta
            .buildMailer()){

            mailer.sendMail(email);
        }catch (Exception e){
            this.dispose();
            throw new RuntimeException(e);
        }

    }

}
