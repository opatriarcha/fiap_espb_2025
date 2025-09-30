package br.com.fiap.es._2espb._2025.ddd.principal;

//import br.com.fiap.es._2espb._2025.ddd.secondary.ClassInAnotherPackage;

import br.com.fiap.es._2espb._2025.ddd.secondary.ClassInAnotherPackage;

public class Main extends Object{

    public static void main(String[] args) {
        Math math = new Math();
        ClassInAnotherPackage clazz  = new ClassInAnotherPackage();
        System.out.println("qqerrcoisa");
        Integer qqerrcoisa = math.sum(1, 2);
        System.out.println(qqerrcoisa);

//        Student jr = new Student("Junior", 19, "fiapation");
//        System.out.println(jr);
//        System.out.println("-------------------------------------------------------");
//        System.out.println(jr.toString());
//
//        jr.setName("Junior2");
//        jr.setAge(23);
//        System.out.println(jr);


    }
}