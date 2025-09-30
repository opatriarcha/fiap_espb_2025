package br.com.fiap.es._2espb._2025.ddd.principal;

import java.time.LocalDate;

public class Student extends Object{
    private String name;
    private int age;
    private String schoolName;
    private LocalDate birthDate;

    public Student() {
    }

    public Student(String name, int age, String schoolName, LocalDate birthDate) {
        this.setName(name);
        this.setAge(age);
        this.setSchoolName(schoolName);
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return super.toString() + "\r\n"+
                "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        //if( user.hasAcccess() )
        System.out.println("verificando acessos.....");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }
}