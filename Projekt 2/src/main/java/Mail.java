public class Mail {
    private Contact contact;
    private String topic;
    private String message;

    public Mail(Contact contact, String topic, String message) {
        this.contact = contact;
        this.topic = topic;
        this.message = message;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.topic;
    }


}
