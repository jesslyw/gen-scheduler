import java.util.*;

public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.3; // number between 0 and 1
    private static final int GENERATIONS = 200;

    private Random random;



    public GeneticAlgorithm() {
        random = new Random();
    }

    public Schedule evolve(Schedule initialSchedule) {
        List<Schedule> population = generateInitialPopulation(initialSchedule);

        for (int i = 0; i < GENERATIONS; i++) {
            population = crossover(population);
            mutate(population);
            System.out.println("generation " + i + " fittest: " + ScheduleEvaluator.evaluateFitness(getFittestSchedule(population)));
        }

        return getFittestSchedule(population);
    }

    private List<Schedule> generateInitialPopulation(Schedule initialSchedule) {
        List<Schedule> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Schedule schedule = new Schedule(initialSchedule.getEmployees());
            randomizeSchedule(schedule);
            schedule.printSchedule(); // print initial
            population.add(schedule);
        }
        return population;
    }

    public void randomizeSchedule(Schedule schedule) {
        for (int day = 0; day < 7; day++) {
            for (int shift = 0; shift < 2; shift++) {
                schedule.setShift(day, shift, 0);
            }
        }
    }



    private List<Schedule> crossover(List<Schedule> population) {
        List<Schedule> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i += 2) {
            Schedule parent1 = population.get(i);
            Schedule parent2 = population.get((i + 1) % population.size());
            Schedule child1 = Schedule.crossover(parent1, parent2);
            Schedule child2 = Schedule.crossover(parent2, parent1);
            newPopulation.add(child1);
            newPopulation.add(child2);
        }
        return newPopulation;
    }


    public void mutate(List<Schedule> population) {
        for (Schedule schedule : population) {
            for (int day = 0; day < 7; day++) {
                for (int shift = 0; shift < 2; shift++) {
                    if (random.nextDouble() < MUTATION_RATE) {
                        int randomEmployeeIndex = random.nextInt(schedule.getEmployees().size());
                        schedule.setShift(day, shift, randomEmployeeIndex);
                    }
                }
            }
        }
    }



    public Schedule getFittestSchedule(List<Schedule> population) {
        Schedule fittest = population.get(0);
        int maxFitness = ScheduleEvaluator.evaluateFitness(fittest);
        for (Schedule schedule : population) {
            int fitness = ScheduleEvaluator.evaluateFitness(schedule);
            if (fitness > maxFitness) {
                fittest = schedule;
                maxFitness = fitness;
            }
        }
        return fittest;
    }
}
