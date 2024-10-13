package domaine;

import java.util.*;

public class Livre {
    private Map<Plat.Type, SortedSet<Plat>> platsParType;

    public Livre() {
        platsParType = new HashMap<>();
    }

    /**
     * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans.
     * Il faut ajouter correctement le plat en fonction de son type.
     * @param plat le plat à ajouter
     * @return true si le plat a été ajouté, false sinon.
     */
    public boolean ajouterPlat(Plat plat) {
        if(platsParType.containsKey(plat.getType())){
            if(platsParType.get(plat.getType()).contains(plat)) return false;
            platsParType.get(plat.getType()).add(plat);
            return true;
        }
        SortedSet<Plat> nouveauxPlats = new TreeSet<>(new Comparator<Plat>() {
            @Override
            public int compare(Plat o1, Plat o2) {
                int diff=o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
                if(diff==0) return o1.getNom().compareTo(o2.getNom());
                return diff;
            }
        });
        nouveauxPlats.add(plat);
        platsParType.put(plat.getType(), nouveauxPlats);
        return true;
    }
    /**
     * Supprime un plat du livre, s'il est dedans.
     * Si le plat supprimé est le dernier de ce type de plat, il faut supprimer
     ce type de
     * plat de la Map.
     * @param plat le plat à supprimer
     * @return true si le plat a été supprimé, false sinon.
     */
    public boolean supprimerPlat (Plat plat) {
        if(!platsParType.containsKey(plat.getType())) return false;
        if(!platsParType.get(plat.getType()).contains(plat)) return false;
        platsParType.get(plat.getType()).remove(plat);
        if(platsParType.get(plat.getType()).isEmpty()) platsParType.remove(plat.getType());
        return true;
    }

    /** L'ensemble n'est pas modifable.
     * @param type le type de plats souhaité
     * @return l'ensemble des plats
     */
    public SortedSet<Plat> getPlatsParType(Plat.Type type) {
        if(platsParType.get(type)==null) return null;
        return Collections.unmodifiableSortedSet(platsParType.get(type));
    }

    /**
     * Renvoie true si le livre contient le plat passé en paramètre. False sinon.
     * Pour cette recherche, un plat est identique à un autre si son type, son niveau de
     * difficulté et son nom sont identiques.
     * @param plat le plat à rechercher
     * @return true si le livre contient le plat, false sinon.
     */
    public boolean contient(Plat plat) {
        for (Plat.Type type : platsParType.keySet()) {
            if(platsParType.get(type).contains(plat)) return true;
        }
        return false;
    }
    /**
     * Renvoie un ensemble contenant tous les plats du livre. Ils ne doivent pas être triés.
     * @return l'ensemble de tous les plats du livre.
     */
    public Set<Plat> tousLesPlats() {
        Set<Plat> tousLesPlats = new HashSet<>(); // Utilisation d'un HashSet pour ne pas trier les plats

        for (SortedSet<Plat> plats : platsParType.values()) {
            tousLesPlats.addAll(plats); // Ajoute tous les plats de chaque SortedSet
        }

        return tousLesPlats;
    }

    @Override
    public String toString() {
        String str ="";
        for (Plat.Type type : platsParType.keySet()) {
            str += type.getNom() + "\n" + "====" +"\n";
            for (Plat plat : platsParType.get(type)) {
                str+= plat.getNom()+ "\n"+"\n";
            }
        }
        return str;
    }
}
