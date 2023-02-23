public class CommissionEmployee extends Employee {
    private float grossSales;
    private int commission;

    public CommissionEmployee(String first, String last, int num, float gross, int com){
        super(first, last, num);
        grossSales = gross;
        commission = com;
    }
    public CommissionEmployee(){
        super();
        grossSales = 0;
        commission = 0;
    }

    //Getters
    public float getGrossSales(){
        return grossSales;
    }
    public int getCommission(){
        return commission;
    }

    //Setters
    public void setGrossSales(float gross){
        if (gross < 0) {
            throw new IllegalArgumentException("Gross sales must be non-negative");
        }
        grossSales = gross;
    }
    public void setCommission(int com) {
        if (com < 0 || com > 100) {
            throw new IllegalArgumentException("Commission must be between 0 and 100");
        }
        commission = com;
    }

    // Override the toString method
    @Override
    public String toString() {
        return super.toString() + ", Gross Sales: " + grossSales + ", Commission: " + commission;
    }

    // Override the equals method
    @Override
    public boolean equals(Object obj) {
        //Check Employees equal
        if (!super.equals(obj)) {
            return false;
        }
        //Make sure obj is an CommissionEmployee
        if (!(obj instanceof CommissionEmployee)) {
            return false;
        }
        //Cast it
        CommissionEmployee employee = (CommissionEmployee) obj;
        //Check equality
        return employee.grossSales == this.grossSales && employee.commission == this.commission;
    }

    // Override the earnings method
    @Override
    float earnings() {
        return grossSales * commission/100;
    }
}
