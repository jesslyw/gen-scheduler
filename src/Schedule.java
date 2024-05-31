import java.util.*;

class Schedule {
    private List<Employee> employees;
    private int[][] scheduleMatrix; // [day][shift]

    public static final Map<String, Integer> shiftMap;
    public static final Map<String, Integer> dayMap;


    static {
        dayMap = new HashMap<>();
        dayMap.put("monday", 0);
        dayMap.put("tuesday", 1);
        dayMap.put("wednesday", 2);
        dayMap.put("thursday", 3);
        dayMap.put("friday", 4);
        dayMap.put("saturday", 5);
        dayMap.put("sunday", 6);
    }

    static {
        shiftMap = new HashMap<>();
        shiftMap.put("am", 0);
        shiftMap.put("pm", 1);
    }


    public Schedule(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
        this.scheduleMatrix = new int[7][2]; // 7 days and 2 shifts per day
        for (int day = 0; day < 7; day++) {
            for (int shift = 0; shift < 2; shift++) {
                this.scheduleMatrix[day][shift] = -1;
            }// Initialize to -1 indicating no worker assigned
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setShift(int day, int shift, int employeeIndex) {
        scheduleMatrix[day][shift] = employeeIndex;
    }

    public int getShift(int day, int shift) {
        return scheduleMatrix[day][shift];
    }

    public int[][] getScheduleMatrix() {
        return scheduleMatrix;
    }

    /**
     * Create a child schedule from 2 parent schedules
     * @param parent1
     * @param parent2
     * @return
     */
    public static Schedule crossover(Schedule parent1, Schedule parent2) {
        List<Employee> employees = parent1.getEmployees();
        Schedule child = new Schedule(employees);
        Random random = new Random();

        for (int day = 0; day < 7; day++) {
            boolean firstShiftFromParent1 = random.nextBoolean();

            if (firstShiftFromParent1) {
                // First shift from parent1
                child.setShift(day, 0, parent1.getShift(day, 0));
                // Second shift from parent2
                child.setShift(day, 1, parent2.getShift(day, 1));
            } else {
                // First shift from parent2
                child.setShift(day, 0, parent2.getShift(day, 0));
                // Second shift from parent1
                child.setShift(day, 1, parent1.getShift(day, 1));
            }
        }

        return child;
    }

    public void mutate(double mutationRate) {
        Random random = new Random();

        for (int day = 0; day < 7; day++) {
            for (int shift = 0; shift < 2; shift++) {
                if (random.nextDouble() < mutationRate) {
                    // Randomly assign a different employee to this shift
                    int newEmployeeIndex = random.nextInt(employees.size());
                    scheduleMatrix[day][shift] = newEmployeeIndex;
                }
            }
        }
    }

    public void printSchedule() {
        for (int day = 0; day < 7; day++) {
            System.out.println(getDayName(day) + ":");
            for (int shift = 0; shift < 2; shift++) {
                int employeeIndex = scheduleMatrix[day][shift];
                if (employeeIndex != -1) {
                    Employee employee = employees.get(employeeIndex);
                    System.out.println("  " + getShiftName(shift) + ": " + employee.getName());
                } else {
                    System.out.println("  " + getShiftName(shift) + ": No Employee Assigned");
                }
            }
            System.out.println();
        }
    }

    public static String getDayName(int dayIndex) {
        for (Map.Entry<String, Integer> entry : dayMap.entrySet()) {
            if (entry.getValue() == dayIndex) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getShiftName(int shiftIndex) {
        for (Map.Entry<String, Integer> entry : shiftMap.entrySet()) {
            if (entry.getValue() == shiftIndex) {
                return entry.getKey();
            }
        }
        return null;
    }
}
