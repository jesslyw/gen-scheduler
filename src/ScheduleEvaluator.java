import java.util.List;

public class ScheduleEvaluator {

    /**
     * evalute fitness of a schedule
     * @param schedule
     * @return
     */
    public static int evaluateFitness(Schedule schedule) {
        List<Employee> employees = schedule.getEmployees();
        int[][] scheduleMatrix = schedule.getScheduleMatrix();


        int fitness = 0;

        // constraint 1 : everyone got a shift

        boolean allEmployeesHaveShift = true;
        for (int i = 0; i < employees.size(); i++) {
            boolean employeeHasShift = false;
            for (int day = 0; day < 7; day++) {
                for (int shift = 0; shift < 2; shift++) {
                    if (schedule.getShift(day, shift) == i) {
                        employeeHasShift = true;
                        break;
                    }
                }
                if (employeeHasShift) {
                    break;
                }
            }
            if (!employeeHasShift) {
                allEmployeesHaveShift = false;
                break; // if one employee has no shift
            }
        }

        if (allEmployeesHaveShift) {
            fitness += 80;
        } else {fitness -= 80;}

        int averageShifts = 14 / employees.size();

        for (int i = 0; i < employees.size(); i++){
            int numberOfShifts = 0;
            for (int day = 0; day < 7; day++) {
                for (int shift = 0; shift < 2; shift++) {

                    // constraint 2 : PM shift allows rest the next morning
                    if (shift == 0 && day > 0){
                        if (scheduleMatrix[day][shift] == i && scheduleMatrix[day-1][Schedule.shiftMap.get("pm")] == i){
                        fitness -= 50;
                    }
                    }
                    // constraint 3 : shifts match availability
                    if (scheduleMatrix[day][shift] == i && employees.get(i).getShiftAvailability()[day][shift]){
                        fitness += 40;
                    }else {
                        fitness -= 5;
                    }
                    if (scheduleMatrix[day][shift] == i) {
                        numberOfShifts++;
                    }
                }
            }
            // constraint 4 : fair shift distribution
            if (numberOfShifts > averageShifts + 1) {
                fitness -= 60;
            } else if (numberOfShifts < averageShifts - 1) {
                fitness -= 20;
            }
        }


        return fitness;
    }


}
