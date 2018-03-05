/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author pc HP
 */
public class Prints {
    
    Scanner sc = new Scanner(System.in);
    private int option = 0, option2 = 0;
    private int reset = 0;

    public Prints() {
    }
    
    public static void printHeader(){
        System.out.println(Console_Colors.ANSI_BLUE + ":::::::::::::::::::::::::::::::::::::AGENDA ELECTRÓNICA:::::::::::::::::::::::::::::::::::::" + Console_Colors.ANSI_RESET);
        System.out.println("............................................................................................");
        System.out.println("");
        System.out.println("  ********      ************    ************    ****    ****    *********         ********  ");
        System.out.println(" **********     ************    ************    *****   ****    **********       ********** ");
        System.out.println("****    ****    ****            ****            ******  ****    ****   ****     ****    ****");
        System.out.println("************    **** *******    ********        ******* ****    ****    ****    ****    ****");
        System.out.println("************    **** *******    ********        ************    ****    ****    ************");
        System.out.println("****    ****    ****    ****    ****            **** *******    ****   ****     ************");
        System.out.println("****    ****    ************    ************    ****  ******    **********      ****    ****");
        System.out.println("****    ****    ************    ************    ****   *****    *********       ****    ****");
        System.out.println("");
        System.out.println("............................................................................................");
        System.out.println(Console_Colors.ANSI_BLUE + "::::::::::::::::::::Crea tus propios eventos y notifica a tus contactos!::::::::::::::::::::" + Console_Colors.ANSI_RESET);
        System.out.println("");
    }
    
    public static void printMenu(){
        System.out.println("------------------------------------------");
        System.out.println("--------------MENU PRINCIPAL--------------");
        System.out.println("1. Ingresar Eventos");
        System.out.println("2. Ver Eventos");
        System.out.println("3. <Salir del Programa>");
        System.out.println("------------------------------------------");
        System.out.print("Elija una opcion >>");
    }
    
    public int catchError(){
        try{
                option = sc.nextInt();
                
            }catch(InputMismatchException e){
                System.out.println();
                System.out.println(Console_Colors.ANSI_RED + "Por favor ingrese un numero entero" + Console_Colors.ANSI_RESET);
                sc.next();
                option=reset;
            }
    return option;    
    }
    
    public static void printCero(){
        System.out.println("                                            →Intenta de nuevo");
    }
    
    public static void printOutOfMenu(int option){
        System.out.println();
        System.out.println(Console_Colors.ANSI_RED + "Error: " + option + " no es una opcion del Menu." + Console_Colors.ANSI_RESET);
        System.out.println("                                            →Intenta de nuevo");
        System.out.println();
    }
    
    public static void printEventsMenu(){
        System.out.println();
        System.out.println(Console_Colors.ANSI_PURPLE + "VER EVENTOS" + Console_Colors.ANSI_RESET);
        System.out.println("..............................");
        System.out.println("1. Eventos mas cercanos:");
        System.out.println("2. Eventos por DÍA");
        System.out.println("3. Eventos por MES");
        System.out.println("4. ← Atras");
        System.out.println("..............................");
        System.out.print("Elija una opcion del Menu >>");
    }
    
    public int askDate(){
        System.out.print("Favor ingrese una fecha >>");
        int date = sc.nextInt();
        return date;
    }
}
