package serviceImpl.forumImpl;

import modele.forum.Message;
import modele.forum.Topic;

import java.util.Date;

public class MessageImpl implements Message {


    private String auteur;
    private String texte;
    private Date date;
    private static long compteur;
    private long identifiant;
    private Topic topic;


    public MessageImpl(String auteur, String texte, Date date, Topic topic) {
        this.auteur = auteur;
        this.texte = texte;
        this.date = date;
        this.topic = topic;
        compteur++;
        this.identifiant=compteur;
    }

    @Override
    public String getAuteur() {
        return auteur;
    }


    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setText(String texte) {
        this.texte = texte;
    }

    @Override
    public String getText() {
        return texte;
    }

    @Override
    public long getIdentifiant() {
        return identifiant;
    }

    @Override
    public Topic getTopic() {
        return topic;
    }


}
