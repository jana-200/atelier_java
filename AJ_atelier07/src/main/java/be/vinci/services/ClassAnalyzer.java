package be.vinci.services;

import jakarta.json.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class analyzer. It saves a class into attribute, from a constructor, and
 * gives a lot of convenient methods to transform this into a JSON object
 * to print the UML diagram.
 */
public class ClassAnalyzer {

    private Class aClass;

    public ClassAnalyzer(Class aClass) {
        this.aClass = aClass;
    }

    /**
     * Create a JSON Object with all the info of the class.
     * @return
     */
    public JsonObject getFullInfo() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("name", aClass.getSimpleName());
        objectBuilder.add("fields", getFields());
        objectBuilder.add("methods", getMethods());

        return objectBuilder.build();
    }


    /**
     * From the field descriptor f, create a Json Object with all field data.
     * Example :
     * {
     * name: "firstname",
     * type: "String",
     * visibility : "private"  // public, private, protected, package
     * isStatic: false,
     * }
     * @param f filed descriptor - describe an attribute
     * @return the generated JSON
     */
    public JsonObject getField(Field f) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("name", f.getName());
        objectBuilder.add("type", f.getType().getSimpleName());
        objectBuilder.add("visibility", getFieldVisibility(f));
        objectBuilder.add("isStatic", isFieldStatic(f));
        return objectBuilder.build();
    }

    /**
     * Get fields, and create a Json Array with all fields data.
     * Example :
     * [ {}, {} ]
     * This method rely on the getField() method to handle each field one by one.
     */
    public JsonArray getFields() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        // Add all fields descriptions to array (use the getField() method above)
        Field[] fields= aClass.getDeclaredFields();
        for(Field field:fields){
            arrayBuilder.add(getField(field));
        }
        return arrayBuilder.build();
    }

    /**
     * Return whether a field is static or not
     *
     * @param f the field to check
     * @return true if the field is static, false else
     */
    private boolean isFieldStatic(Field f) {
        if(Modifier.isStatic(f.getModifiers()))
            return true;
        return false;
    }

    /**
     * Get field visibility in a string form
     *
     * @param f the field to check
     * @return the visibility (public, private, protected, package)
     */
    private String getFieldVisibility(Field f) {
        if(Modifier.isPublic(f.getModifiers()))
            return "public";
        if(Modifier.isPrivate(f.getModifiers()))
            return "private";
        if(Modifier.isProtected(f.getModifiers()))
            return "protected";

        return "string";
    }



    //method


    public JsonObject getMethod(Method m) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("name", m.getName());
        objectBuilder.add("returnType", m.getReturnType().getSimpleName());
        objectBuilder.add("parameters", getParameters(m));
        objectBuilder.add("visibility",getMethodVisibility(m));
        objectBuilder.add("isStatic", isMethodStatic(m));
        objectBuilder.add("isAbstract", isMethodAbstract(m));

        return objectBuilder.build();
    }

    public JsonArrayBuilder getParameters(Method m){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Parameter parameter : m.getParameters()){
            arrayBuilder.add(parameter.getType().getSimpleName());
        }
        return arrayBuilder;
    }
    public JsonArray getMethods() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        // Add all methods descriptions to array (use the getMethod() method above)
        Method[] fields= aClass.getDeclaredMethods();
        for(Method method:fields){
            arrayBuilder.add(getMethod(method));
        }
        return arrayBuilder.build();
    }

    private boolean isMethodStatic(Method m) {
        if(Modifier.isStatic(m.getModifiers()))
            return true;
        return false;
    }
    private boolean isMethodAbstract(Method m){
        if(Modifier.isAbstract(m.getModifiers()))
            return true;
        return false;
    }

    private String getMethodVisibility(Method m) {
        if(Modifier.isPublic(m.getModifiers()))
            return "public";
        if(Modifier.isPrivate(m.getModifiers()))
            return "private";
        if(Modifier.isProtected(m.getModifiers()))
            return "protected";

        return "string";
    }

}
