/*
Online Java - IDE, Code Editor, Compiler

Online Java is a quick and easy tool that helps you to build, compile, test your programs online.
*/

public class Main
{
    public static void main(String[] args) {
        System.out.println("Instantiating Students . . . . . . . .");
        
        Student firstStudent = new Student("Johnny", "Lawrence", 'B');
        
        
        
        
    }
}


public class Student {
    //instance variables
    public String firstName;
    public char middleInitial;
    public String lastName;
    
    public int age;
    public double grade;
    
    Student(String inFirstName, String inLastName, char inMiddleInitial) {
        //body
        
    }
    
    Student(String inFirstName, String inLastName) {
        //body
        
    }
}