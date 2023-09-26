package Lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneticAlgorithm {
    private int mutationPercent = 10;

    private int maxGen = 60;

    private List<Chromosome> population;

    private Chromosome createChromosome(List<City> cities){
        Collections.shuffle(cities);
        List shuffleCities = new ArrayList<>(cities);
        return new Chromosome(shuffleCities);
    }
    private void createPopulation(List<City> cities){
        population = new ArrayList<>();
        for(int i = 0; i < (int) (Math.pow(cities.size(), 2)); i++){
            Chromosome c = createChromosome(cities);
            population.add(c);
        }
    }
    private double calculateDistance(City city1, City city2){
        double s = Math.pow((city2.getX() - city1.getX()), 2) + Math.pow((city2.getY() - city1.getY()), 2);
        return Math.sqrt(s);
    }
    private Chromosome getDistanceForChromosome(Chromosome chromosome){
        double distance = 0;
        for(int i = 1; i < chromosome.getGenome().size(); i++){
            double d = calculateDistance(chromosome.getGenome().get(i), chromosome.getGenome().get(i-1));
            distance += calculateDistance(chromosome.getGenome().get(i), chromosome.getGenome().get(i-1));
        }
        distance += calculateDistance(chromosome.getGenome().get(chromosome.getGenome().size()-1),chromosome.getGenome().get(0));
        chromosome.setDistance(distance);
        return chromosome;
    }

    private Chromosome[] createChildren(Chromosome parent1, Chromosome parent2){
        int point1 = (int) (Math.random()*parent1.getGenome().size()-1);
        int point2;
        if(parent1.getGenome().size() - 1 - point1 == 1){
            point2 = point1+1;
        } else {
            point2 =(int) (Math.random()*(parent1.getGenome().size()-1-point1))+1;
            point2 += point1;
        }
        Chromosome[] children = new Chromosome[2];
        parent1.getGenome();
        List<City> genome1 = new ArrayList<>();
        genome1.addAll(parent1.getGenome().subList(0,point1));
        genome1.addAll(parent2.getGenome().subList(point1,point2+1));
        genome1.addAll(parent1.getGenome().subList(point2+1,parent1.getGenome().size()));
        children[0] = new Chromosome();
        children[0].setGenome(genome1);
        List<City> genome2 = new ArrayList<>();
        genome2.addAll(parent2.getGenome().subList(0,point1));
        genome2.addAll(parent1.getGenome().subList(point1,point2+1));
        genome2.addAll(parent2.getGenome().subList(point2+1,parent1.getGenome().size()));
        children[1]= new Chromosome();
        children[1].setGenome(genome2);
        return children;
    }
    private Chromosome[] selectParents(){
        Comparator<Chromosome> compareByDistance = Comparator.comparing(Chromosome::getDistance);
        Chromosome[] parents = new Chromosome[2];
        population.sort(compareByDistance);
        int index = (int) (Math.random()* population.size());
        parents[0] = population.get(index);
        if(index != 0 && index != population.size()-1) {
            if (Math.abs(population.get(index).getDistance() - population.get(index + 1).getDistance()) < Math.abs(population.get(index).getDistance() - population.get(index - 1).getDistance())) {
                parents[1] = population.get(index + 1);
                population.remove(index + 1);
            } else {
                parents[1] = population.get(index - 1);
                population.remove(index - 1);
            }
        } else if(index == population.size()-1){
            parents[1] = population.get(index - 1);
        } else {
            parents[1] = population.get(index + 1);
        }
        population.remove(index);
        return parents;
    }

    private List<Chromosome> mutate(List<Chromosome> newGen){
        int mutationQuantity = ((newGen.get(0).getGenome().size()-1)*mutationPercent)/100;
        for(int i = 0; i < mutationQuantity; i++){
            int ch = (int) (Math.random()*newGen.size());
            int g1 = (int) (Math.random()*newGen.get(ch).getGenome().size());
            int g2 = (int) (Math.random()*newGen.get(ch).getGenome().size());
            City temp = newGen.get(ch).getGenome().get(g1);
            newGen.get(ch).getGenome().set(g1,newGen.get(ch).getGenome().get(g2));
            newGen.get(ch).getGenome().set(g2,temp);
        }
        return newGen;
    }
    public void geneticAlgorithm(List<City> cities){
        Comparator<Chromosome> compareByDistance = Comparator.comparing(Chromosome::getDistance);
        List<Chromosome> bestFromEachGeneration = new ArrayList<>();
        createPopulation(cities);
        for(int i = 0; i < population.size(); i++) {
            getDistanceForChromosome(population.get(i));
        }
        for(int i = 0; i < maxGen; i++){
            population.sort(compareByDistance);
            bestFromEachGeneration.add(population.get(0));
            System.out.print("Generation "+i+" Distance: " + population.get(0).getDistance());
            System.out.println();
            List<Chromosome> newGen = new ArrayList<>();
            int s = population.size()/2;
            for(int j = 0; j < s; j++){
                Chromosome[] parents = selectParents();
                Chromosome[] children = createChildren(parents[0], parents[1]);
                children[0].setDistance(getDistanceForChromosome(children[0]).getDistance());
                children[1].setDistance(getDistanceForChromosome(children[1]).getDistance());
                newGen.add(children[0]);
                newGen.add(children[1]);
            }
            population = mutate(newGen);
        }
        bestFromEachGeneration.sort(compareByDistance);
        System.out.println("Best distance: " + bestFromEachGeneration.get(0).getDistance());

    }
}
