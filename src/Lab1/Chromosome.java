package Lab1;

import java.util.List;

public class Chromosome {
    private List<City> genome;
    private Double distance;
    public Chromosome(){

    }

    public Chromosome(List<City> genome) {
        this.genome = genome;
    }

    public List<City> getGenome() {
        return genome;
    }

    public void setGenome(List<City> genome) {
        this.genome = genome;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
