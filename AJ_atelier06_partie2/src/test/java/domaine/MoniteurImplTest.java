package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {
    private Stage stage;
    private Sport sport;
    private Moniteur moniteur;
    @BeforeEach
    void setUp() {
        moniteur=new MoniteurImpl("lili");
        sport=Mockito.mock(Sport.class);
        Mockito.when(sport.contientMoniteur(moniteur)).thenReturn(true);

        stage= Mockito.mock(Stage.class);

        Mockito.when(stage.getSport()).thenReturn(sport);
        Mockito.when(stage.getMoniteur()).thenReturn(null);
        Mockito.when(stage.getNumeroDeSemaine()).thenReturn(8);

    }

    private void amenerALEtat(int etat, Moniteur moniteur) {
        for (int i = 1; i <= etat; i++) {
            Stage newStage = Mockito.mock(Stage.class);
            Mockito.when(newStage.getSport()).thenReturn(sport);
            Mockito.when(newStage.getMoniteur()).thenReturn(null);
            Mockito.when(newStage.getNumeroDeSemaine()).thenReturn(i);
            moniteur.ajouterStage(newStage);
        }
    }

    @Test
    void testMoniteurTC1(){
        assertTrue(moniteur.ajouterStage(stage));
        assertTrue(moniteur.contientStage(stage));
        assertEquals(1, moniteur.nombreDeStages());
        Mockito.verify(stage).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC2(){
        amenerALEtat(1,moniteur);
        assertTrue(moniteur.ajouterStage(stage));
        assertTrue(moniteur.contientStage(stage));
        assertEquals(2, moniteur.nombreDeStages());
        Mockito.verify(stage).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC3(){
        amenerALEtat(2,moniteur);
        assertTrue(moniteur.ajouterStage(stage));
        assertTrue(moniteur.contientStage(stage));
        assertEquals(3, moniteur.nombreDeStages());
        Mockito.verify(stage).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC4(){
        amenerALEtat(3,moniteur);
        assertTrue(moniteur.ajouterStage(stage));
        assertTrue(moniteur.contientStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
        Mockito.verify(stage).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC5(){
        amenerALEtat(3,moniteur);
        moniteur.ajouterStage(stage);
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
        Mockito.verify(stage).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC6(){
        amenerALEtat(4,moniteur);
        Stage newStage= Mockito.mock(Stage.class);

        Mockito.when(newStage.getSport()).thenReturn(sport);
        Mockito.when(newStage.getMoniteur()).thenReturn(null);
        Mockito.when(newStage.getNumeroDeSemaine()).thenReturn(1);

        assertFalse(moniteur.ajouterStage(newStage));
        assertEquals(4, moniteur.nombreDeStages());
        Mockito.verify(newStage, Mockito.never()).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC7(){
        amenerALEtat(4,moniteur);
        Stage newStage= Mockito.mock(Stage.class);

        Mockito.when(newStage.getSport()).thenReturn(sport);
        Mockito.when(newStage.getMoniteur()).thenReturn(new MoniteurImpl("jana"));
        Mockito.when(newStage.getNumeroDeSemaine()).thenReturn(5);

        assertFalse(moniteur.ajouterStage(newStage));
        assertFalse(moniteur.contientStage(newStage));
        assertEquals(4, moniteur.nombreDeStages());
        Mockito.verify(newStage, Mockito.never()).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC8(){
        amenerALEtat(4,moniteur);
        Stage newStage= Mockito.mock(Stage.class);

        Mockito.when(newStage.getSport()).thenReturn(sport);
        Mockito.when(newStage.getMoniteur()).thenReturn(moniteur);
        Mockito.when(newStage.getNumeroDeSemaine()).thenReturn(5);

        assertTrue(moniteur.ajouterStage(newStage));
        assertTrue(moniteur.contientStage(newStage));
        assertEquals(5, moniteur.nombreDeStages());
        Mockito.verify(newStage, Mockito.never()).enregistrerMoniteur(moniteur);

    }

    @Test
    void testMoniteurTC9(){
        amenerALEtat(1,moniteur);
        Sport newSport=Mockito.mock(Sport.class);
        Mockito.when(newSport.contientMoniteur(moniteur)).thenReturn(false);

        Stage newStage= Mockito.mock(Stage.class);
        Mockito.when(newStage.getSport()).thenReturn(newSport);
        Mockito.when(newStage.getMoniteur()).thenReturn(null);
        Mockito.when(newStage.getNumeroDeSemaine()).thenReturn(5);

        assertFalse(moniteur.ajouterStage(newStage));
        assertFalse(moniteur.contientStage(newStage));
        assertEquals(1, moniteur.nombreDeStages());
        Mockito.verify(newStage, Mockito.never()).enregistrerMoniteur(moniteur);

    }
}