import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Commande implements Iterable<LigneDeCommande>{
    private static int numeroSuivant=1;
    private int numero;
    private Client client;
    private LocalDateTime date;
    private ArrayList<LigneDeCommande> lignesCommande;

    public Commande(Client client) {
        if(client.getCommandeEnCours()!=null)throw new IllegalArgumentException("impossible de créer une commande pour un client ayant encore une commande en cours");
        this.client = client;
        this.numero=numeroSuivant++;
        this.lignesCommande=new ArrayList<>();
        this.date=LocalDateTime.now();
        client.enregistrer(this);
    }

    public int getNumero() {
        return numero;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public boolean ajouter(Pizza pizza, int quantite){
        if(this!=client.getCommandeEnCours())return false;
        for (LigneDeCommande ldc:lignesCommande) {
            if(ldc.getPizza().equals(pizza)) {
                ldc.setQuantite(ldc.getQuantite() + 1);
                return true;
            }

        }
        LigneDeCommande lc =new LigneDeCommande(pizza,quantite);
        lignesCommande.add(lc);
        return true;
    }
    public boolean ajouter(Pizza pizza){
        return ajouter(pizza,1);
    }
    public double calculerMontantTotal() {
        double prix = 0;
        for (LigneDeCommande lc : lignesCommande) {
            prix += lc.claculerPrixTotal();
        }
        return prix;
    }
    public String detailler(){
        String string="";
        for (LigneDeCommande lc:lignesCommande) {
            string += lc.toString()+"\n";
        }
        return string;
    }
    public Iterator<LigneDeCommande> iterator(){
        return lignesCommande.iterator();
    }
    public boolean retirer(Pizza pizza, int quantite){ //pas testé
        if(this!=client.getCommandeEnCours())return false;
        for (LigneDeCommande lc:lignesCommande) {
            if(lc.getPizza().equals(pizza)) {
                if (lc.getQuantite() <= quantite)
                    lc.setQuantite(lc.getQuantite() - quantite);
                return true;
            }
        }
        return false;
    }
    public boolean retirer(Pizza pizza){ //pas testé
        return retirer(pizza,1);
    }
    public boolean supprimer(Pizza pizza){ //pas testé
        if(this!=client.getCommandeEnCours())return false;
        for (LigneDeCommande lc:lignesCommande) {
            if(lc.getPizza().equals(pizza)) {
                lc.setQuantite(0);
                return true;
            }
        }
        return false;
    }

    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String encours = "";
        if (client.getCommandeEnCours() == this)
            encours = " (en cours)";
        return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
    }
}
