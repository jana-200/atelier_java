package lambda;

import java.util.Arrays;
import java.util.List;

public class TestLambda {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(153, 22, 4567, 50, 209, 34, 1040);

        List<Integer> result;



        //Trouve tous les entiers de la liste qui sont plus grands que 200
        result = Lambda.allMatches(list, n->n>200);
        System.out.println(result);

        //Trouve tous les entiers pairs de la liste
        result = Lambda.allMatches(list, n-> n%2 == 0);
        System.out.println(result);

        //Trouve tous les entiers de la liste dont le premier chiffre est 1
        result = Lambda.allMatches(list, n-> String.valueOf(n).charAt(0) == '1');
        System.out.println(result);

        //Retourne une liste contenant les entiers de la liste originale multipliés par 2
        result = Lambda.transformAll(list,n->n*2);
        System.out.println(result);

        //Retourne une liste contenant les entiers de la liste originale auxquels on a soustrait 25
        result = Lambda.transformAll(list, n->n-25);
        System.out.println(result);




        List<String> list2 = Arrays.asList("hello", "bonjour", "goeiedag", "hallo", "hej");

        //Trouve toutes les String de la liste qui commencent par "h"
        List<String> result2 = Lambda.allMatches(list2, str-> str.charAt(0)=='h');
        System.out.println(result2);

        //Retournerune liste qui contient la taille de chacune des String de la liste originale
        List<Integer> result3 = Lambda.transformAll(list2, str-> str.length());
        System.out.println(result3);

        //Trouve toutes les String de la liste qui commencent par "h"
        List<String> filter = Lambda.filter(list2, str-> str.charAt(0)=='h');
        System.out.println(filter);

        //Retournerune liste qui contient la taille de chacune des String de la liste originale
        List<Integer> map = Lambda.map(list2, str-> str.length());
        System.out.println(map);


    }
}
