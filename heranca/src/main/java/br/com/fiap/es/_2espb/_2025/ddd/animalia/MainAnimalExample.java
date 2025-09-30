package br.com.fiap.es._2espb._2025.ddd.animalia;

public class MainAnimalExample extends Object{
    public static void main(String[] args) {
        AmericanFila dog = new AmericanFila();
        Dog caramelo = new ViraLata();

        System.out.println("------------AMERICAN FILA ________________");
        dog.doGuard();
        dog.doDogsThings();
        dog.sleep();
        dog.sound();
        dog.doAmericanFilaThings();

        System.out.println("------------- TOMBA --------------------");
        caramelo.doGuard();
        caramelo.doDogsThings();
        caramelo.sleep();
        caramelo.sound();


        Dog sal = new Salsicha();
        sal.doGuard();



    }
}
