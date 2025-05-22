package day1.task2;

/**
 * Represents an employee with basic details such as ID, name, age, department, and salary.
 * Provides methods to access and modify these details.
 */
public class Employee {

    /** The unique ID of the employee */
    private int id;

    /** The name of the employee */
    private String name;

    /** The age of the employee */
    private int age;

    /** The department the employee works in */
    private String department;

    /** The salary of the employee */
    private double salary;

    /**
     * Constructs an Employee object with the specified details.
     *
     * @param id the employee's unique ID
     * @param name the employee's name
     * @param age the employee's age
     * @param department the department in which the employee works
     * @param salary the employee's salary
     */
    public Employee(int id, String name, int age, String department, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    /**
     * Gets the employee's unique ID.
     *
     * @return the employee's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the employee's unique ID.
     *
     * @param id the employee's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the employee's name.
     *
     * @return the employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee's name.
     *
     * @param name the employee's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee's age.
     *
     * @return the employee's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the employee's age.
     *
     * @param age the employee's age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the department the employee works in.
     *
     * @return the employee's department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department the employee works in.
     *
     * @param department the employee's department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Gets the employee's salary.
     *
     * @return the employee's salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the employee's salary.
     *
     * @param salary the employee's salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }
}


