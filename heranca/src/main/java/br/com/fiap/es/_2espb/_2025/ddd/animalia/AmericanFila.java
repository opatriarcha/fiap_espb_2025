package br.com.fiap.es._2espb._2025.ddd.animalia;

import java.sql.SQLOutput;

public class AmericanFila extends Dog implements GuardAble{

    @Override
    public void doGuard() {
        System.out.println("Act as a guard");
    }

    @Override
    public void sound() {
        System.out.println("Cao que late grosso");
    }
    
    public void doAmericanFilaThings(){
        System.out.println("FAZER COISAS DE FILA AMERICANO");
    }
}
