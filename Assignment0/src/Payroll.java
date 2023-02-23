public class Payroll {
    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        employees[0] = new HourlyEmployee("Binyamin", "Klein", 123, 42, 25);
        employees[1] = new CommissionEmployee("Ariel", "Blumstein", 456, 5555, 25);
        employees[2] = new BasePlusCommissionEmployee("Guy", "Yechezkel", 789, 8000, 15, 2000);

        for (int i = 0; i < 3; i++) {
            System.out.println(employees[i]);
            double earnings = employees[i].earnings();
            if (employees[i] instanceof BasePlusCommissionEmployee) {
                BasePlusCommissionEmployee employee = (BasePlusCommissionEmployee) employees[i];
                earnings += employee.getBaseSalary() * 0.1;
            }
            System.out.printf("Weekly earnings: %.2f", earnings);
            System.out.println();
        }
    }
}
