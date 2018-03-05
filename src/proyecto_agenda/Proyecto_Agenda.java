/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.awt.Graphics;

/**
 *
 * @author pc HP
 */
public class Proyecto_Agenda {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        String user = "";
        String nombre = "";
        String correoElectronico = "", correoElectronico2 = "";
        int guardar = 0;
        int option2 = 0, option3 =0;
        int dateQuery = 0; 
        Prints p = new Prints();
        Connect connect = new Connect();
        
        Prints.printHeader();
        
        do{
            try{
                System.out.println("<:::INICIO:::>");
                System.out.println("Ingrese su Correo Electronico");
                System.out.print(">> ");
                user = sc.nextLine();
                
                if (connect.validarUsuario(user)) { //**Funcion: Consulta DB - Select Email Tabla Users - Comparar cada linea del RS con mi varaible.
                    do{
                       try{
                           System.out.println();
                           System.out.println(Console_Colors.ANSI_GREEN + connect.retornarNombre(user) + Console_Colors.ANSI_RESET);
                           Prints.printMenu();
                           option2 = sc.nextInt();
                           
                           if (option2 >= 1 && option2 <=3) {
                               switch (option2) {
                                   case 1:
                                       
                                       break;
                                   case 2:
                                       
                                       break;
                                   default:
                                       break;
                               }
                           }
                           else{
                               System.out.print("**OPCION NO VÁLIDA");
                           }
                       }catch (InputMismatchException e) {
                           
                       }
                       
                    }while (option2 != 3);
                    
                    
                    
                }
                else{
                    System.out.println(Console_Colors.ANSI_RED + "**USUARIO NO EXISTE" + Console_Colors.ANSI_RESET);
                    System.out.println("Ingrese su Nombre: ");
                    System.out.print(">> ");
                    nombre = sc.nextLine();
                    int confirmacion = 0;
                    do{
                        String respuesta ="";
                        int salir = 0;
                        
                        do{
                            System.out.println("¿Desea utilizar " + Console_Colors.ANSI_BLUE+  user + Console_Colors.ANSI_RESET + " como su Correo Electrónico?  <SI>/<NO>");
                            respuesta = sc.nextLine();
                            if (respuesta.equalsIgnoreCase("SI") || respuesta.equalsIgnoreCase("NO")) {
                                salir = 1;
                            }else{
                                System.out.println(Console_Colors.ANSI_RED + "**RESPUESTA NO VALIDA" + Console_Colors.ANSI_RESET);
                            }
                        } while (salir != 1);
                        if (respuesta.equalsIgnoreCase("SI")) correoElectronico = user;
                        else if (respuesta.equalsIgnoreCase("NO")) {
                            do{
                                System.out.println("Ingrese su Correo Electrónico: ");
                                System.out.print(">> ");
                                correoElectronico = sc.nextLine();
                                System.out.println("Confirme su Correo Electrónico: ");
                                System.out.print(">> ");
                                correoElectronico2 = sc.nextLine();
                                if (correoElectronico.equals(correoElectronico2)) {
                                    confirmacion = 1;
                                }else{
                                    System.out.println(Console_Colors.ANSI_RED + "**ERROR DE CONFIRMACIÓN" + Console_Colors.ANSI_RESET);
                                }
                            }while (confirmacion != 1);
                        }
                        if (confirmacion == 0) {
                            System.out.println("Confirme su Correo Electrónico: ");
                            System.out.print(">> ");
                            correoElectronico2 = sc.nextLine();
                            if (correoElectronico.equals(correoElectronico2)) {
                                confirmacion = 1;
                            }else{
                                System.out.println(Console_Colors.ANSI_RED + "**ERROR DE CONFIRMACIÓN" + Console_Colors.ANSI_RESET);
                            }
                        }
                    } while (confirmacion != 1);
                    do{
                        System.out.println("Ingrese <Y> para guardar o <N> para cancelar.");
                        System.out.print(">> ");
                        String guarda = sc.nextLine();
                        
                        if (guarda.equalsIgnoreCase("Y")) {
                            connect.insertarUsuario(nombre, correoElectronico); //Insert DB Tabla Users
                            guardar = 1;
                        }else if (guarda.equalsIgnoreCase("N")){
                            guardar = 1;
                        }else{
                            System.out.println(Console_Colors.ANSI_RED + "**RESPUESTA NO VALIDA" + Console_Colors.ANSI_RESET);
                        }
                        
                        
                    } while (guardar != 1);
                    
                }
                
            }catch (InputMismatchException e) {
                
            }
            
        }while (option2 != 3);
        
        
        
        
        /*do{
            Prints.printMenu();
            option = p.catchError();
                       
            if (option>=1&&option<=3) {
                switch (option){
                    case 1:
                        // conexion a DB por medio de un Insert    
                        break;
                    case 2:
                        do{
                        Prints.printEventsMenu();
                        option2 = p.catchError();
                        
                            if (option2 >=1 && option2 <=4) {
                                switch (option2){
                                    case 1:
                                        // conexion a DB por medio de un select from, sorteado y con limit de 3.    
                                        break;
                                    case 2:
                                        dateQuery = p.askDate();
                                        // cconexion a DB por medio de un select from, where date = fecha.    
                                        break;
                                    case 3:
                                        
                                        break;
                                    default:
                                        break;
                                }
                                
                            }else if (option2 == 0) {
                                Prints.printCero();
                                
                            }else{
                                Prints.printOutOfMenu(option2);
                            }
                        }while (option2 != 4);
                        break;
                    default:
                        break;
                }
            }else if (option == 0) {
                Prints.printCero();
            }else{
                Prints.printOutOfMenu(option);
            }
        }while(option!=3);*/
    }
    
}
