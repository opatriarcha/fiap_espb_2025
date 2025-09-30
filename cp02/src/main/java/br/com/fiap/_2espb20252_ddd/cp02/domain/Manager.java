package br.com.fiap._2espb20252_ddd.cp02.domain;

public class Manager extends Employee{

    private Double bonus;

    public Manager() {
		super();
	}

    @Override
    public Double getFinalWage() {
        Double bonus = this.bonus/100;
        return (super.getFinalWage() * bonus) + this.getBaseWage();
    }

    public Manager(String name, String document, Double baseWage, Double bonus) {
        super(name, document, baseWage);
        this.bonus = bonus;
    }

    public Manager(Long id, String name, String document, Double baseWage, Double bonus) {
        super(id, name, document, baseWage);
        this.bonus = bonus;
    }

}
