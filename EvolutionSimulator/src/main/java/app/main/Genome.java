package app.main;

import java.util.ArrayList;
import java.util.Collections;

public class Genome {
    private final int[] genome;
    private int activeGene = 0;
    private boolean randomMutation = true;
    private boolean jumpToRandomGene = false;
    private int minMutations = 0;
    private int maxMutations = 0;

    public Genome(int genomeLength) {
        genome = new int[genomeLength];
    }

    public Genome(int[] genome, boolean randomMutation, boolean jumpToRandomGene) {
        this.genome = genome;
        this.mutate();
        this.randomMutation = randomMutation;
        this.jumpToRandomGene = jumpToRandomGene;
    }

    public Genome(int[] genome, boolean randomMutation, boolean jumpToRandomGene, int minMutations, int maxMutations) {
        this(genome, randomMutation, jumpToRandomGene);
        if (minMutations < 0 || maxMutations > genome.length || minMutations > maxMutations) {
            throw new IllegalArgumentException("Illegal min/max mutations value!");
        }
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
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
}
