import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public abstract class Pizza implements Iterable<Ingredient>{
    public final static double PRIX_BASE=5.0;
    private String titre;
    private String description;
    private ArrayList<Ingredient> ingredients;

    public Pizza(String titre, String description) {
        this.titre = titre;
        this.description = description;
        this.ingredients=new ArrayList<>();
    }
    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        this(titre,description);
        ArrayList<Ingredient> newListe=new ArrayList<>();
        for (Ingredient ing:ingredients) {
            if(newListe.contains(ing)) throw new IllegalArgumentException("vous ne pouvez pas ajouter deux fois le mm ingrédient");
            else newListe.add(ing);
        }
        this.ingredients = ingredients;
    }


    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public boolean ajouter(Ingredient ingredient){
        if(ingredients.contains(ingredient))return false;
        ingredients.add(ingredient);
        return true;
    }

    public boolean supprimer(Ingredient ingredient){
        if(!ingredients.contains(ingredient))return false;
        ingredients.remove(ingredient);
        return true;
    }

    public double calculerPrix(){
        double prix=0;
        for (Ingredient ingredient:ingredients) {
            prix+=ingredient.getPrix();
        }
        return prix+PRIX_BASE;
    }

    public Iterator<Ingredient> iterator(){
        return ingredients.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza that = (Pizza) o;
        return Objects.equals(titre, that.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre);
    }

    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients){
            infos +="\n" + ingredient.getNom();
        }
        infos +="\nprix : " + calculerPrix() + " euros";
        return infos;
    }
}
