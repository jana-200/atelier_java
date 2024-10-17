package domaine;

import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PrixTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;



    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1,20);
        prixAucune.definirPrix(10,10);

        prixPub=new Prix(TypePromo.PUB, 0.2);
        prixPub.definirPrix(3,15);

        prixSolde=new Prix(TypePromo.SOLDE, 0.3);

    }

    @Test
    @DisplayName("Vérifier que le constructeur lance IllegalArgumentException si le type de promo est null")
    void testConstructeur1(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Prix(null,0.0);
        },"fallait une exception");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -33.0})
    @DisplayName("Vérifier que le constructeur lance IllegalArgumentException si la valeur est <= 0")
    void testConstructeur2(double valeurTestee){
        assertThrows(IllegalArgumentException.class, () -> {
            new Prix(TypePromo.PUB,valeurTestee);
        },"fallait une exception");
    }
    @Test
    @DisplayName("Vérifier le type de promotion")
    void getTypePromo() {
        //Vérifiez que le type de la promo est null lors de l’instanciation d’un prix au moyen du constructeur sans paramètre.
        assertNull(prixAucune.getTypePromo());
        //Vérifiez que le type de la promo correspond bien à celle passée en paramètre du constructeur
        assertEquals(TypePromo.PUB, prixPub.getTypePromo());
        assertEquals(TypePromo.SOLDE, prixSolde.getTypePromo());
    }

    @Test
    @DisplayName("Vérifier la valeur de la promotion")
    void getValeurPromo() {
        // Vérifiez que la valeur de la promo est initialisée à 0 lors de l’instanciation d’un prix au moyen du constructeur sans paramètre.
        assertEquals(0.0, prixAucune.getValeurPromo());
        //Vérifiez que la valeur de la promo correspond bien à celle passée en paramètre duconstructeur.
        assertEquals(0.2, prixPub.getValeurPromo());
        assertEquals(0.3, prixSolde.getValeurPromo());

    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -33})
    @DisplayName("Définir un nouveau prix")
    void definirPrix(int entiers) {
        //Vérifiez que la méthode definirPrix lance une IllegalArgumentException si le paramètre quantité est 0 ou négatif.
        assertThrows(IllegalArgumentException.class, () -> {
            new Prix().definirPrix(entiers,10);
        },"fallait une exception");
        //Vérifiez que la méthode definirPrix lance une IllegalArgumentException si le paramètre prixUnitaire est 0 ou négatif.
        assertThrows(IllegalArgumentException.class, () -> {
            new Prix().definirPrix(2,entiers);
        },"fallait une exception");
        //Définissez un prix de 6 euros à partir de 10 unités pour l’attribut prixAucune et vérifiez que l’ancien prix a été remplacé.
        prixAucune.definirPrix(10, 6);
        assertEquals(6, prixAucune.getPrix(10));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -33})
    @DisplayName("Obtenir le prix en fonction de la quantité")
    void getPrix(int entiers) {
        //Vérifiez que la méthode lance une IllegalArgumentException si le paramètre quantité est négatif ou nul.
        assertThrows(IllegalArgumentException.class, () -> {
            prixAucune.getPrix(entiers);
        },"fallait une exception");
        //Testez les prix renvoyés par la méthode getPrix pour l’attribut prixAucune :
        // faites le test pour 1 unité, 5 unités, 9 unités, 10 unités, 15 unités, 20 unités et 25 unités.
        assertEquals(20, prixAucune.getPrix(1));
        assertEquals(20, prixAucune.getPrix(5));
        assertEquals(20, prixAucune.getPrix(9));
        assertEquals(10, prixAucune.getPrix(10));
        assertEquals(10, prixAucune.getPrix(15));
        assertEquals(10, prixAucune.getPrix(20));
        assertEquals(10, prixAucune.getPrix(25));
        //Testez qu’une QuantiteNonAutoriseeException est lancée si vous demandez le prix pour 2 unités pour l’attribut prixPub
        assertThrows(QuantiteNonAutoriseeException.class, () -> {
            prixPub.getPrix(2);
        },"fallait une exception");
        //Testez qu’une QuantiteNonAutoriseeException est lancée si vous demandez le prix pour 1 unité pour l’attribut prixSolde
        assertThrows(QuantiteNonAutoriseeException.class, () -> {
            prixSolde.getPrix(1);
        },"fallait une exception");

    }
}