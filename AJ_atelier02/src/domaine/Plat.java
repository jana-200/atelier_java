package domaine;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

public class Plat {

    private String nom;
    private int nbPersonnes;
    private Difficulte niveauDeDifficulte;
    private Cout cout;
    private Duration dureeEnMinutes;
    private Type type;

    private List<Instruction> instructions;
    private Set<IngredientQuantifie> ingredientsQualifies;
    public enum Difficulte {
        X,XX,XXX,XXXX,XXXXX;

        @Override
        public String toString() {
            return "*".repeat(this.ordinal() + 1);
        }
    }

    public enum Cout{
        $,$$,$$$,$$$$,$$$$$;
        public String toString() {
            return "€".repeat(this.ordinal() + 1);
        }
    }
    public enum Type{
        ENTREE("Entrée"),
        PLAT("Plat"),
        DESSERT("Dessert");
        private final String nom;

        Type(String nom) {
            this.nom = nom;
        }

        public String getNom() {
            return nom;
        }

        public String toString() {
            return nom;
        }


    }

    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout, Type type) {
        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
        this.dureeEnMinutes=Duration.ofMinutes(0);
        this.instructions = new ArrayList<>();
        this.ingredientsQualifies = new HashSet<>();
        this.type=type;
    }

    public String getNom() {
        return nom;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Duration getDureeEnMinutes() {

        return dureeEnMinutes;
    }
    public Type getType() {
        return type;
    }

    public void insererInstruction(int position, Instruction instruction) {
        if (position <= 0 || position > instructions.size() + 1) {
            throw new IllegalArgumentException("Position invalide : " + position);
        }
        instructions.add(position - 1, instruction);
        this.dureeEnMinutes= this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    public void ajouterInstruction(Instruction instruction) {
        instructions.add(instruction);
        this.dureeEnMinutes= this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }

    public Instruction remplacerInstruction(int position, Instruction instruction) {
        if (position <= 0 || position > instructions.size()) {
            throw new IllegalArgumentException();
        }
        Instruction replaced=instructions.set(position - 1, instruction);
        this.dureeEnMinutes= this.dureeEnMinutes.minus(replaced.getDureeEnMinutes()).plus(instruction.getDureeEnMinutes());

        return replaced;

    }

    public Instruction supprimerInstruction(int position) {
        if (position <= 0 || position > instructions.size()) {
            throw new IllegalArgumentException();
        }
        Instruction removed=instructions.remove(position - 1);
        this.dureeEnMinutes= this.dureeEnMinutes.minus(removed.getDureeEnMinutes());
        return removed;
    }

    public List<Instruction> instructions() {
        return Collections.unmodifiableList(instructions);
    }

    public boolean ajouterIngredient(Ingredient ingredient, int quantite, Unite unite){
        Util.checkObject(unite);
        Util.checkStrictlyPositive(quantite);
        IngredientQuantifie ing=new IngredientQuantifie(ingredient,quantite,unite);
        if (ingredientsQualifies.contains(ing)) return false;
        ingredientsQualifies.add(ing);
        return true;
    }
    public boolean ajouterIngredient(Ingredient ingredient, int quantite){
        return ajouterIngredient( ingredient,  quantite, Unite.NEANT);
    }
    public boolean modifierIngredient(Ingredient ingredient, int quantite, Unite unite){
        IngredientQuantifie ing=trouverIngredientQuantifie(ingredient);
        if(ing != null){
            ing.setQuantite(quantite);
            ing.setUnite(unite);
            return true;
        }
        return false;
    }

    public boolean supprimerIngredient(Ingredient ingredient){
        IngredientQuantifie ing=trouverIngredientQuantifie(ingredient);
        if(ing != null) return ingredientsQualifies.remove(ing);
        return false;
    }

    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient){
        Util.checkObject(ingredient);
        for (IngredientQuantifie ingredientQuantifie : ingredientsQualifies) {
            if (ingredientQuantifie.getIngredient().equals(ingredient))
                return ingredientQuantifie;
        }
        return null;
    }

    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredientsQualifies) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (Instruction instruction : this.instructions) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }

}
