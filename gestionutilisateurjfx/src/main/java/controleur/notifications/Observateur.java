package controleur.notifications;

public interface Observateur {

    void inscription(Sujet sujet);

    void broadcastNotification(Notification notification);

}
