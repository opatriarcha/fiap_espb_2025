package br.com.fiap.es._2espb._2025.ddd.animalia;

import java.io.Serializable;

public class Tartaruga extends Animal implements GuardAble, Serializable {
    @Override
    public void sound() {
        System.out.println("Sounds like a DUCK");
    }

    @Override
    public void doGuard() {
        System.out.println("try to act like  a  BIG FUCKER DOG");
    }
}
