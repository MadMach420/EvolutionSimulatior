package app.main;

import app.main.elements.Animal;
import app.main.maps.AbstractMap;
import app.main.maps.EquatorMap;
import app.main.maps.MapType;
import app.main.maps.ToxicMap;

public class Engine implements Runnable {

    public AbstractMap map;
    private final int grassGrowth;
    private final int energyFromGrass;
    private final int minEnergyToBreed;
    private final int startingEnergy;
    private final int energyLoss;
    private final int genomeLength;
    private final int minMutations;
    private final int maxMutations;
    private final boolean randomMutation;
    private final boolean jumpToRandomGene;

    public Engine(int mapWidth, int mapHeight, int grassCount, int animalCount, boolean wrapEdges,  MapType variant,
                  int grassGrowth, int energyFromGrass, int minEnergyToBreed, int startingEnergy, int energyLoss,
                  int genomeLength, int minMutations, int maxMutations, boolean randomMutation, boolean jumpToRandomGene) {
        this.grassGrowth = grassGrowth;
        this.energyFromGrass = energyFromGrass;
        this.minEnergyToBreed = minEnergyToBreed;
        this.startingEnergy = startingEnergy;
        this.energyLoss = energyLoss;
        this.genomeLength = genomeLength;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.randomMutation = randomMutation;
        this.jumpToRandomGene = jumpToRandomGene;

        if (variant == MapType.TOXIC) {
            this.map = new ToxicMap(mapWidth, mapHeight, wrapEdges);
        } else {
            this.map = new EquatorMap(mapWidth, mapHeight, wrapEdges);
        }

        this.map.spawnMultipleGrass(grassCount);
        this.spawnRandomAnimals(animalCount);
    }

    private void spawnRandomAnimals(int n) {
        for (int i = 0; i < n; i++) {
            Genome newGenome = new Genome(genomeLength, randomMutation, jumpToRandomGene, minMutations, maxMutations);
            this.map.addAnimal(
                    new Animal(new Vector2D(this.map.width, this.map.height, true), this.map, startingEnergy, energyLoss, newGenome)
            );
        }
    }

    @Override
    public void run() {
        this.map.checkAnimalDeath();
        this.map.moveAnimals();
        this.map.eatGrass(energyFromGrass);
        this.map.breedAnimals(minEnergyToBreed);
        this.map.spawnMultipleGrass(grassGrowth);
    }
}
