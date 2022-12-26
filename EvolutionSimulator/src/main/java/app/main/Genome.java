package app.main;

import java.util.ArrayList;
import java.util.Collections;

public class Genome {
    private final int[] genome;
    private int activeGene = 0;
    public boolean randomMutation = true;
    public boolean jumpToRandomGene = false;
    public int minMutations = 0;
    public int maxMutations = 0;

    public Genome(int genomeLength, boolean randomMutation, boolean jumpToRandomGene, int minMutations, int maxMutations) {
        this.genome = generateRandomGenome(genomeLength);
        this.activeGene = (int) Math.floor(Math.random() * genome.length);
        this.mutate();
        this.randomMutation = randomMutation;
        this.jumpToRandomGene = jumpToRandomGene;
        if (minMutations < 0 || maxMutations > genome.length || minMutations > maxMutations) {
            throw new IllegalArgumentException("Illegal min/max mutations value!");
        }
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
    }

    public Genome(int[] genome, boolean randomMutation, boolean jumpToRandomGene, int minMutations, int maxMutations) {
        this.genome = genome;
        this.activeGene = (int) Math.floor(Math.random() * genome.length);
        this.mutate();
        this.randomMutation = randomMutation;
        this.jumpToRandomGene = jumpToRandomGene;
        if (minMutations < 0 || maxMutations > genome.length || minMutations > maxMutations) {
            throw new IllegalArgumentException("Illegal min/max mutations value!");
        }
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
    }

    private int[] generateRandomGenome(int length) {
        int[] genome = new int[length];
        for (int i = 0; i < length; i++) {
            genome[i] = (int) Math.floor(Math.random() * 8);
        }
        return genome;
    }

    private void mutate() {
        int numberOfMutations = (int) (Math.floor(Math.random() * maxMutations) + minMutations);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < genome.length; i++) list.add(i);
        Collections.shuffle(list);
        for (int i = 0; i < numberOfMutations; i++) {
            int geneToMutate = list.get(i);
            if (randomMutation) {
                genome[geneToMutate] = (int) Math.floor(Math.random() * 8);
            } else if (Math.random() < 0.5) {
                genome[geneToMutate] = ((genome[geneToMutate] - 1) % 8 + 8) % 8;
            } else {
                genome[geneToMutate] = (genome[geneToMutate] + 1) % 8;
            }
        }
    }

    public int getGene() {
        int gene = genome[activeGene];
        activeGene = (activeGene + 1) % genome.length;
        if (jumpToRandomGene && Math.random() < 0.2) {
            activeGene = (int) Math.floor(Math.random() * genome.length);
        }
        return gene;
    }

    public int getGeneAtIndex(int i) {
        return genome[i];
    }

    public int getLength() {
        return this.genome.length;
    }
}
