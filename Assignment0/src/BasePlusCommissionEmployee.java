public class BasePlusCommissionEmployee extends CommissionEmployee{
    private float baseSalary;

    public BasePlusCommissionEmployee(String first, String last, int num, float gross, int com, float base){
        super(first, last, num, gross, com);
        baseSalary = base;
    }

    public BasePlusCommissionEmployee(){
        super();
        baseSalary = 0;
    }

    //Getter
    public float getBaseSalary() {
        return baseSalary;
    }

    //Setter
    public void setBaseSalary(float base) {
        if (base < 0) {
            throw new IllegalArgumentException("Base salary must be non-negative");
        }
        baseSalary = base;
    }

    @Override
    public String toString() {
        return super.toString() + ", Base Salary: " + baseSalary;
    }

    @Override
    public boolean equals(Object obj) {
        //Check CommissionEmployee equal
        if (!super.equals(obj)) {
            return false;
        }
        //Make sure obj is a BasePlusCommissionEmployee
        if (!(obj instanceof BasePlusCommissionEmployee)) {
            return false;
        }
        //Cast it
        BasePlusCommissionEmployee employee = (BasePlusCommissionEmployee) obj;
        //Check equality
        return employee.baseSalary == this.baseSalary;
    }
}
