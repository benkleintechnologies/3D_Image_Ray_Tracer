public abstract class Employee {
    private String firstName;
    private String lastName;
    private int id;

    //Constructors
    public Employee(String first, String last, int num){
        firstName = first;
        lastName = last;
        id = num;
    }
    public Employee(){
        firstName = "Plony";
        lastName = "Almoni";
        id = 0;
    }

    //Getters
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public int getId(){
        return id;
    }

    //Setters
    public void setFirstName(String name){
        firstName = name;
    }
    public void setLastName(String name){
        lastName = name;
    }
    public void setId(int num){
        id = num;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", ID: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        //Make sure obj is an Employee
        if (!(obj instanceof Employee)) {
            return false;
        }
        //Cast it
        Employee employee = (Employee) obj;
        //Check Equality
        return employee.firstName.equals(this.firstName) && employee.lastName.equals(this.lastName) && employee.id == this.id;
    }

    abstract float earnings(); //return salary
}
