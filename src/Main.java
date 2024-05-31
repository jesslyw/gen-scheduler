import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("employee 1"));
        employees.add(new Employee("employee 2"));
        employees.add(new Employee("employee 3"));
        //employees.add(new Employee("employee 4"));


        employees.get(0).setShiftAvailability("monday", "PM", true);
        employees.get(0).setShiftAvailability("tuesday", "PM", true);
        employees.get(0).setShiftAvailability("wednesday", "AM", true);
        employees.get(0).setShiftAvailability("friday", "AM", true);
        employees.get(0).setShiftAvailability("thursday", "PM", true);

        employees.get(1).setShiftAvailability("monday", "AM", true);
        employees.get(1).setShiftAvailability("tuesday", "AM", true);
        employees.get(1).setShiftAvailability("tuesday", "PM", true);
        employees.get(1).setShiftAvailability("saturday", "PM", true);
        employees.get(1).setShiftAvailability("saturday", "AM", true);

        employees.get(2).setShiftAvailability("monday", "PM", true);
        employees.get(2).setShiftAvailability("monday", "AM", true);
        employees.get(2).setShiftAvailability("wednesday", "AM", true);
        employees.get(2).setShiftAvailability("wednesday", "PM", true);
        employees.get(2).setShiftAvailability("sunday", "PM", true);

        /*employees.get(3).setShiftAvailability("friday", "PM", true);
        employees.get(3).setShiftAvailability("saturday", "PM", true);
        employees.get(3).setShiftAvailability("sunday", "AM", true);*/




        Schedule initialSchedule = new Schedule(employees);

        GeneticAlgorithm ga = new GeneticAlgorithm();
        Schedule finalSchedule = ga.evolve(initialSchedule);

        int fitness = ScheduleEvaluator.evaluateFitness(finalSchedule);
        System.out.println("Fitness of the final schedule: " + fitness);

        finalSchedule.printSchedule(); // print final schedule

        for(Employee e :employees){ // print availability
            System.out.println(" ");
            for (int day = 0; day < 7; day++) {
                for (int shift = 0; shift < 2; shift++){
                    if (e.getShiftAvailability()[day][shift]){
                        String dayName = Schedule.getDayName(day);
                        String shiftName = Schedule.getShiftName(shift);

                        System.out.println(e.getName() + "  " + dayName + " " + shiftName);
                    }
                }
            }

        }

    }
}
