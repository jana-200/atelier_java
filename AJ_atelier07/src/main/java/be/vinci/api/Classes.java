package be.vinci.api;

import be.vinci.services.ClassAnalyzer;
import be.vinci.classes.User;
import jakarta.json.JsonStructure;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * Send class data to make class diagrams
 * The class name must be given, and present into the "classes" package
 */
@Path("classes")
public class Classes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonStructure getClassInfo(@QueryParam("classname") String classname) throws ClassNotFoundException {
        if (classname == null || classname.isEmpty()) {
            throw new WebApplicationException("Classname parameter is missing", 400); // Erreur 400 : Paramètre absent
        }
        String nomComplet = "be.vinci.classes." + classname;

        // Charger dynamiquement la classe ou lancer une erreur 404 si introuvable
        Class<?> classe = Class.forName(nomComplet);

        // Utiliser ClassAnalyzer pour analyser la classe
        ClassAnalyzer analyzer = new ClassAnalyzer(classe);
        return analyzer.getFullInfo();
    }

}
