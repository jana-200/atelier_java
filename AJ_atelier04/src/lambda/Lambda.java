package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lambda {

    /**
     * Retourne une liste contenant uniquement les Integer qui correspondent
     * au predicat match
     * @param list La liste d'Integer originale
     * @param match le predicat à respecter
     * @return une liste contenant les integer qui respectent match
     */
    public static <T> List<T> allMatches(List<T> list, Predicate<T> match) {
        List<T> liste=new ArrayList<>();
        for(T i:list){
            if(match.test(i))
                liste.add(i);
        }
        return liste;
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> match) {
        List<T> liste= list.stream().filter(n-> match.test(n)).collect(Collectors.toList());
        return liste;
    }
    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste d'Integer originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les integer transformés par transform
     */
    public static <T, R> List<R> transformAll(List<T> list, Function<T, R> transform) {
        List<R> liste=new ArrayList<>();
        for(T i:list){
            liste.add(transform.apply(i));
        }
        return liste;
    }


    public static <T, R> List<R> map(List<T> list, Function<T, R> transform) {
        List<R> liste=list.stream().map(n->transform.apply(n)).collect(Collectors.toList());
        return liste;
    }




}
