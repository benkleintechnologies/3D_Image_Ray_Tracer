public class HourlyEmployee extends Employee {
    private int hours;
    private float wage;

    public HourlyEmployee(String first, String last, int num, int h, float w){
        super(first, last, num);
        hours = h;
        wage = w;
    }

    public HourlyEmployee(){
        super();
        hours = 0;
        wage = 0;
    }

    //Getters
    public int getHours(){
        return hours;
    }
    public float getWage(){
        return wage;
    }

    //Setters
    public void setWage(float w){
        if (w < 0){
            throw new IllegalArgumentException("Hours must be non-negative");
        }
        wage = w;
    }
    public void setHours(int h){
        if (h < 0){
            throw new IllegalArgumentException("Hours must be non-negative");
        }
        hours = h;
    }

    @Override
    public String toString() {
        return super.toString() + ", Hours: " + hours + ", Wage: " + wage;
    }

    @Override
    public boolean equals(Object obj) {
        //Check Employees equal
        if (!super.equals(obj)) {
            return false;
        }
        //Make sure obj is an HourlyEmployee
        if (!(obj instanceof HourlyEmployee)) {
            return false;
        }
        //Cast it
        HourlyEmployee employee = (HourlyEmployee) obj;
        //Check Equality
        return employee.hours == this.hours && employee.wage == this.wage;
    }

    @Override
    float earnings() {
        return wage*hours;
    }

}
