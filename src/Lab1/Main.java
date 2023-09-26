package Lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static List<City> cities;
    private static void generateCities() {
        cities = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            int x = (int)(Math.random()*200);
            int y = (int)(Math.random()*200);
            //System.out.println(x);
            City city = new City(x, y);
            cities.add(city);
        }
    }
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm =  new GeneticAlgorithm();
        generateCities();
//        cities.forEach(g -> System.out.print(g.getX() + " "));
//        System.out.println();
        geneticAlgorithm.geneticAlgorithm(cities);
//        Collections.shuffle(cities);
//        cities.forEach(g -> System.out.print(g.getX() + " "));
//        Collections.shuffle(cities);
//        System.out.println();
//        cities.forEach(g -> System.out.print(g.getX() + " "));

    }

}
