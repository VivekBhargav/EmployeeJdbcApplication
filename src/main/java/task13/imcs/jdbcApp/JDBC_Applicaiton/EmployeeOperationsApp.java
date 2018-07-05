package task13.imcs.jdbcApp.JDBC_Applicaiton;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import task13.imcs.jdbc.Jdbc_Library.Employee;
import task13.imcs.jdbc.Jdbc_Library.EmployeeServices;
import task13.imcs.jdbc.Jdbc_Library.EmployeeServicesImpl;
import task13.imcs.jdbc.Jdbc_Library.EmployeeUtil;
import task13.imcs.jdbc.Jdbc_Library.SalaryException;

public class EmployeeOperationsApp {
	public static Scanner scan = new Scanner(System.in);
	public static Employee empTemp = new Employee();
	public static EmployeeServices empServ = new EmployeeServicesImpl();

	public static void main(String[] args) throws SQLException, IOException {
		int option;
		EmployeeUtil.getDepartment();
		System.out.println("----------------Employee System-------------------");

		do {
			System.out.println("\n\n|||Menu|||");
			System.out.println(
					"1.Create Employee\n2.Read(View) Employee.\n3.View all Employees\n4.Order by column\n5.Update Employee\n6.Delete Employee\n7.Calculate HRA\n8.Calculate Gross Salary\n9.Write data from file to Database\n10.Exit");
			System.out.println("Select your choice: ");
			option = scan.nextInt();

			switch (option) {
			case 1:
				System.out.println("Enter Employee Id: ");
				int id = scan.nextInt();
				try {
					empTemp = EmployeeUtil.createEmpObj(id);
					empServ.addEmployee(empTemp);
					System.out.println("Employee created");
				} catch (SalaryException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("Enter Employee ID you want to view: ");
				int view = scan.nextInt();
					String result = empServ.viewEmployee(view);
					if (result != null) {
						System.out.println("The Employee Details are as follows: \n" + result);
					} else
						System.out.println("Employee doesn't exists with the given ID.");
				break;
			case 3:
				String vAllStr = empServ.viewAllEmployees();
				if (vAllStr != null) {
					System.out.println(vAllStr);
				} else {
					System.out.println("No elements in the arrray");
				}
				break;
			case 4:
				System.out.println("Enter column name to display in order(id, name, salary, age or gender): ");
				String col = scan.next();
				String orderBy = empServ.orderBy(col);
				System.out.println(orderBy);
				break;
			case 5:
				System.out.println("Enter Employee Id you want to Update: ");
				int idUpd = scan.nextInt();
					boolean update = empServ.isExists(idUpd);
					if (update) {
						try {
							empTemp = EmployeeUtil.createEmpObj(idUpd);
							empServ.updateEmployee(idUpd, empTemp);
						} catch (SalaryException e) {
							System.out.println("Salary Excpetion occurred");
							e.printStackTrace();
						}
						System.out.println("Updated the Employee details");
					} 
				break;
			case 6:
				System.out.println("Enter ID of Employee you want to delete: ");
				int delId = scan.nextInt();
				boolean delStatus = empServ.deleteEmployee(delId);
					if (delStatus) {
						System.out.println("Employee deleted.");
					} else
						System.out.println("Given ID doesn't exists.");
				break;
			case 7:
				System.out.println("Enter ID of Employee to calculate HRA: ");
				int hraId = scan.nextInt();
				double hra = empServ.getHRASrvc(hraId);
				if (hra != 0) {
					System.out.println("HRA is: " + hra);
				} else
					System.out.println("Given ID doesn't exists. Please try again.");
				break;
			case 8:
				System.out.println("Enter ID of Employee to calculate Gross Salary: ");
				int grossId = scan.nextInt();
				double gross = empServ.getGrossSrvc(grossId);
				if (gross != 0) {
					System.out.println("Gross Salary is: " + gross);
				} else
					System.out.println("Given ID doesn't exists. Please try again.");
				break;
			case 9:
				System.out.println("Type your option (1-Yes or 2-No) : ");
				int wtFile = scan.nextInt();
				if(wtFile == 1){
					System.out.println("Enter file Location(eg: c:\\temp\\Employee.csv): ");
					
					String fLoc = scan.next();
					boolean dbLoad = empServ.fileOperation(fLoc);
					if(dbLoad == true){
						System.out.println("Written from file to DB successfully");
					}else
						System.out.println("Unsuccessfull");
					break;
				}else
					break;
			default:
				if (option != 10)
					System.out.println("Invalid value. Please try again");
				break;
			}
		} while (option != 10);
	}
}
