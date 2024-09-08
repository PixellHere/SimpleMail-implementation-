import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    public static List<Contact> contactList = new ArrayList<>();
    public static List<Mail> mailList = new ArrayList<>();
    public static DefaultListModel<Mail> mailDefaultListModel = new DefaultListModel<>();

    public MainWindow() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // MENU PANEL WITH BUTTONS
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        ImageIcon mailIcon = new ImageIcon(getClass().getResource("/icons/new_mail.png"));
        ImageIcon contactIcon = new ImageIcon(getClass().getResource("/icons/new_contact.png"));

        mailIcon = new ImageIcon(mailIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        contactIcon = new ImageIcon(contactIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        JButton newMailButton = new JButton("Send new mail");
        newMailButton.setIcon(mailIcon);
        newMailButton.setFont(buttonFont);
        newMailButton.setPreferredSize(new Dimension(160, 100));
        newMailButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newMailButton.setHorizontalTextPosition(SwingConstants.CENTER);
        menuPanel.add(newMailButton);

        JButton newContactButton = new JButton("Create new contact");
        newContactButton.setIcon(contactIcon);
        newContactButton.setFont(buttonFont);
        newContactButton.setPreferredSize(new Dimension(160, 100));
        newContactButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newContactButton.setHorizontalTextPosition(SwingConstants.CENTER);
        menuPanel.add(newContactButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        this.add(menuPanel, gbc);

        // EMPTY PANEL FOR BETTER UI
        JPanel emptyPanel = new JPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(emptyPanel, gbc);

        newMailButton.addActionListener(e -> SwingUtilities.invokeLater(() ->
                new NewMailWindow(this)
        ));

        newContactButton.addActionListener(e -> SwingUtilities.invokeLater(() ->
                new NewContactWindow(this)
        ));

        // SENT MESSAGES PANEL
        JPanel sentMessagesPanel = new JPanel();
        sentMessagesPanel.setLayout(new BorderLayout());

        JLabel sentMessagesLabel = new JLabel("Sent messages:");
        sentMessagesLabel.setFont(buttonFont);
        sentMessagesPanel.add(sentMessagesLabel, BorderLayout.PAGE_START);

        mailDefaultListModel.addAll(mailList);
        JList<Mail> mailJList = new JList<>(mailDefaultListModel);
        JScrollPane messagesScrollPane = new JScrollPane(mailJList);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sentMessagesPanel.add(messagesScrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(sentMessagesPanel, gbc);

        // READING EMAILS PANEL
        JPanel readEmailsPanel = new JPanel();
        readEmailsPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.insets = new Insets(5, 5, 5, 5);

        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.anchor = GridBagConstraints.EAST;
        infoPanel.add(new JLabel("To:"), constraints2);

        constraints2.gridy = 1;
        infoPanel.add(new JLabel("Topic:"), constraints2);

        constraints2.gridx = 1;
        constraints2.gridy = 0;
        constraints2.anchor = GridBagConstraints.WEST;
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.weightx = 1.0;
        JTextField recipient = new JTextField();
        recipient.setEnabled(false);
        recipient.setPreferredSize(new Dimension(200, 24));
        infoPanel.add(recipient, constraints2);

        constraints2.gridy = 1;
        JTextField topic = new JTextField();
        topic.setEnabled(false);
        topic.setPreferredSize(new Dimension(200, 24));
        infoPanel.add(topic, constraints2);

        readEmailsPanel.add(infoPanel, BorderLayout.PAGE_START);

        JTextArea message = new JTextArea();
        message.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(message);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        readEmailsPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0.75;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(readEmailsPanel, gbc);

        mailJList.addListSelectionListener(e -> {
            Mail pickedMail = mailJList.getSelectedValue();
            if(pickedMail!=null){
                String pickedMailContact = pickedMail.getContact().getAllInfo();
                recipient.setText(pickedMailContact);
                String pickedMailTopic = pickedMail.getTopic();
                topic.setText(pickedMailTopic);
                String pickedMailMessage = pickedMail.getMessage();
                message.setText(pickedMailMessage);
            }
        });

        this.setTitle("PJATK MAIL");
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static boolean checkIsContactExsist(String email){
        for (Contact contact : contactList) {
            if (contact.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
        contactList.add(new Contact("Jan", "Kowalski", "jkowalski@gmail.com"));
        contactList.add(new Contact("Tomasz", "Problem", "tomaszp@pjwstk.com"));
        mailList.add(new Mail(contactList.get(0), "Proba nr1", "Cześć, \nTutaj jest przygotowana pierwsza próbna wiadomość :)"));
    }
}