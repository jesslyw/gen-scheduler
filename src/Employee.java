class Employee {
    private String name;
    private boolean[][] shiftAvailability;

    public Employee(String name) {
        this.name = name;
        this.shiftAvailability = new boolean[7][2]; // 7 days and 2 shifts per day
    }

    public String getName() {
        return name;
    }

    /**
     * Set employees shift availability
     * @param day
     * @param shift AM or PM
     * @param available
     */
    public void setShiftAvailability(String day, String shift, boolean available) {
        Integer dayIndex = Schedule.dayMap.get(day.toLowerCase());
        Integer shiftIndex = Schedule.shiftMap.get(shift.toLowerCase());
        if (dayIndex != null) {
            shiftAvailability[dayIndex][shiftIndex] = available;
        }
    }

    public boolean isAvailable(String day, String shift) {
        Integer dayIndex = Schedule.dayMap.get(day.toLowerCase());
        Integer shiftIndex = Schedule.shiftMap.get(shift.toLowerCase());
        if (dayIndex != null && shiftIndex != null) {
            return shiftAvailability[dayIndex][shiftIndex];
        }
        return false;
    }

    public boolean[][] getShiftAvailability() {
        return shiftAvailability;
    }
}
