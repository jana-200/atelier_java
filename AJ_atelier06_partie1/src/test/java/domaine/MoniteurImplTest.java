package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {
    private Sport sport;
    private Moniteur moniteur;
    private Stage stage;
    @BeforeEach
    void setUp() {
        sport = new SportStub(true);
        moniteur = new MoniteurImpl("joseph");
        stage = new StageStub(8, sport, null);
    }

    private void amenerALEtat(int etat, Moniteur moniteur) {
        for (int i = 1; i <= etat; i++) {
            moniteur.ajouterStage(new StageStub(i, sport, null));
        }
    }

    @Test
    void testMoniteurTC1() {
        assertTrue(moniteur.ajouterStage(stage));
        assertTrue(moniteur.contientStage(stage));
        assertEquals(1, moniteur.nombreDeStages());
    }
    @Test
    void testMoniteurTC2() {
        moniteur.ajouterStage(stage);

        Stage stage1 = new StageStub(7, sport, null);

        assertTrue(moniteur.ajouterStage(stage1));
        assertTrue(moniteur.contientStage(stage1));
        assertEquals(2, moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC3() {
        moniteur.ajouterStage(stage);
        Stage stage2 = new StageStub(7, sport, null);

        moniteur.ajouterStage(stage2);
        Stage stage3 = new StageStub(6, sport, null);

        assertTrue(moniteur.ajouterStage(stage3));
        assertTrue(moniteur.contientStage(stage3));
        assertEquals(3, moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC4() {
        moniteur.ajouterStage(stage);
        Stage stage2 = new StageStub(7, sport, null);

        moniteur.ajouterStage(stage2);
        Stage stage3 = new StageStub(6, sport, null);

        moniteur.ajouterStage(stage3);
        Stage stage4 = new StageStub(5, sport, null);

        assertTrue(moniteur.ajouterStage(stage4));
        assertTrue(moniteur.contientStage(stage4));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC5() {
        amenerALEtat(3,moniteur);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertFalse(moniteur.contientStage(stage));
        assertEquals(3,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC6() {
        amenerALEtat(2,moniteur);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertFalse(moniteur.contientStage(stage));
        assertEquals(2,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC7() {
        amenerALEtat(1,moniteur);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertFalse(moniteur.contientStage(stage));
        assertEquals(1,moniteur.nombreDeStages());
    }
    @Test
    void testMoniteurTC8() {
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertFalse(moniteur.contientStage(stage));
        assertEquals(0,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC9() {
        amenerALEtat(3,moniteur);
        moniteur.ajouterStage(stage);
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC10() {
        amenerALEtat(4,moniteur);
        Stage mmSemaine=new StageStub(1,sport,null);
        assertFalse(moniteur.ajouterStage(mmSemaine));
        assertFalse(moniteur.contientStage(mmSemaine));
        assertEquals(4,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC11() {
        amenerALEtat(4,moniteur);
        assertFalse(moniteur.supprimerStage(stage));
        assertEquals(4,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC12() {
        amenerALEtat(4,moniteur);
        Stage autreMoniteur= new StageStub(6,sport,new MoniteurImpl("joester"));
        assertFalse(moniteur.ajouterStage(autreMoniteur));
        assertEquals(4,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC13() {
        amenerALEtat(4,moniteur);
        Stage bonStage= new StageStub(6,sport,moniteur);
        assertTrue(moniteur.ajouterStage(bonStage));
        assertTrue(moniteur.contientStage(bonStage));
        assertEquals(5,moniteur.nombreDeStages());
    }

    @Test
    void testMoniteurTC14() {
        amenerALEtat(4,moniteur);
        Sport sportFaux=new SportStub(false);
        Stage mauvaisSport= new StageStub(6,sportFaux,null);
        assertFalse(moniteur.ajouterStage(mauvaisSport));
        assertFalse(moniteur.contientStage(mauvaisSport));
        assertEquals(4,moniteur.nombreDeStages());
    }

}