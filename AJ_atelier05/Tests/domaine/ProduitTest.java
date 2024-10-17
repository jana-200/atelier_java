package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;
    private Produit p1;
    private Produit p2;


    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1,20);
        prixAucune.definirPrix(10,10);

        prixPub=new Prix(TypePromo.PUB, 0.2);
        prixPub.definirPrix(3,15);

        prixSolde=new Prix(TypePromo.SOLDE, 0.3);

        p1=new Produit("p1","p1","p1");
        p1.ajouterPrix(LocalDate.of(2004,2,3),prixAucune);
        p1.ajouterPrix(LocalDate.of(2005,2,3),prixPub);
        p1.ajouterPrix(LocalDate.now(),prixSolde);

        p2=new Produit("p2","p2","p2");

    }

    @Test
    void getMarque() {
        assertEquals("p1",p1.getMarque());
        assertEquals("p2",p2.getMarque());

    }

    @Test
    void getNom() {
        assertEquals("p1",p1.getNom());
        assertEquals("p2",p2.getNom());
    }

    @Test
    void getRayon() {
        assertEquals("p1",p1.getRayon());
        assertEquals("p2",p2.getRayon());
    }

    @Test
    void ajouterPrix() {
        assertThrows(IllegalArgumentException.class, () -> {
            p2.ajouterPrix(null,new Prix());
        },"fallait une exception");
        assertThrows(IllegalArgumentException.class, () -> {
            p2.ajouterPrix(LocalDate.now(),null);
        },"fallait une exception");

        assertThrows(DateDejaPresenteException.class, () -> {
            p1.ajouterPrix(LocalDate.of(2004,2,3),prixPub);
        },"fallait une exception");

        p2.ajouterPrix(LocalDate.of(2024, 10, 17),prixAucune);
        assertEquals(prixAucune, p2.getPrix(LocalDate.of(2024, 10, 17)));


    }

    @Test
    void getPrix() {
        assertEquals(prixAucune, p1.getPrix(LocalDate.of(2004, 2, 3)));

        assertThrows(PrixNonDisponibleException.class, () -> {
            p1.getPrix(LocalDate.of(2002, 10, 17));
        },"fallait une exception");

        assertThrows(PrixNonDisponibleException.class, () -> {
            p2.getPrix(LocalDate.of(2002, 10, 17));
        },"fallait une exception");

        assertEquals(prixAucune, p1.getPrix(LocalDate.of(2004,5,5)));

    }

    @Test
    void testEquals() {
        Produit same=new Produit("p1","p1","p1");
        assertEquals(true,p1.equals(same));

        Produit rayonDiff=new Produit("p2","p2","p1");
        assertEquals(false,p2.equals(rayonDiff));

        //... (test nomDiff et marqueDiff)

    }

    @Test
    void testHashCode() {
        Produit same=new Produit("p1","p1","p1");
        assertEquals(p1.hashCode(), same.hashCode());
    }
}