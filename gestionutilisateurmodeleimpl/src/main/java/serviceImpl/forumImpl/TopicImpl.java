package serviceImpl.forumImpl;

import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;

import java.util.Collection;

public class TopicImpl implements Topic {


    private String createur;
    private String nom;
    private static long compteur;
    private long identifiant;
    private Theme theme;
    Collection<Message>listeMessages;


    public TopicImpl(String createur, String nom, Theme theme, Collection<Message> listeMessages) {
        this.createur = createur;
        this.nom = nom;
        compteur ++;
        this.identifiant = compteur;
        this.theme = theme;
        this.listeMessages = listeMessages;
    }


    @Override
    public String getCreateur() {
        return createur;
    }

    @Override
    public String getNom() {
        return nom;
    }


    @Override
    public long getIdentifiant() {
        return identifiant;
    }

    public Theme getTheme() {
        return theme;
    }

    @Override
    public Collection<Message> getListeMessages() {
        return listeMessages;
    }

    @Override
    public boolean ajouterMessage(Message message) {
        listeMessages.add(message);
        return true;
    }
}
