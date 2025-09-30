package br.com.fiap._2espb20252_ddd.cp02.domain;

import java.util.Objects;

public class Employee{
    private Long id;
    private String name;
    private String document;
    private Double baseWage;

    public Employee(Long id, String name, String document, Double baseWage) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.baseWage = baseWage;
    }

    public Employee(String name, String document, Double baseWage) {
        this.name = name;
        this.document = document;
        this.baseWage = baseWage;
    }

    public Employee(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Double getBaseWage() {
        return baseWage;
    }

    public void setBaseWage(Double baseWage) {
        this.baseWage = baseWage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Double getFinalWage(){
        return this.baseWage;
    }
}
