
package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public class ExercicesPanaches {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        ExercicesPanaches main = new ExercicesPanaches(transactions);
        main.run();
    }

    private List<Transaction> transactions;

    public ExercicesPanaches(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void run() {
        // Complete the methods below based on the exercise descriptions
        exercice1();
        exercice2();
        exercice3();
        exercice4();
        exercice5();
        exercice6();
    }

    private void exercice1() {
        System.out.println("exercice 1");
        var s = transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::max).orElse(-1);
            System.out.println(s);
    }
    private void exercice2() {
        System.out.println("exercice 2");

        var s= transactions.stream().filter(t->t.getTrader().getCity().equals("Cambridge")).collect(Collectors.groupingBy(Transaction::getTrader, counting()));
        System.out.println(s);
    }

    private void exercice3() {
        System.out.println("exercice 3");

        var s= transactions.stream().filter(t->t.getValue()>500).
                map(Transaction::getTrader).map(Trader::getName).sorted((n1,n2)->Integer.compare(n2.length(),n1.length())).findFirst();
        System.out.println(s);
    }

    private void exercice4() {
        System.out.println("exercice 4");
        var s= transactions.stream().
                collect(Collectors.groupingBy(t -> t.getTrader().getCity(),                   // Grouper par la ville du trader
                Collectors.averagingDouble(Transaction::getValue) // Calculer la moyenne des valeurs des transactions
        ));
        System.out.println(s);
    }

    private void exercice5() {
        System.out.println("exercice 5");
        var s=transactions.stream().filter(t->t.getTrader().getCity().equals("Milan")).
                map(Transaction::getValue).reduce(Integer::min).orElse(-1);
        System.out.println(s);
    }
    private void exercice6() {
        System.out.println("exercice 6");
        var s=transactions.stream().collect(Collectors.groupingBy(t->t.getYear()));
        System.out.println(s);
    }
}
