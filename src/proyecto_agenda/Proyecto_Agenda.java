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
        String nombreEvento = "", descripcionEvento = "", fechaEvento = "", ubicacionEvento = "", horaInicio = "", horaFin = "";
        String notificar = "";//variable para saber si el usuario desea notificar a mas correos
        String[] arrayCorreos;
        boolean timeValidation;
        int seq1 = 0, seq2 = 0, seq3 = 0, seq4 = 0;
        int guardar = 0;
        int option2 = 0, option3 = 0;
        int cantCorreos = 0;//preguntar cuantos correos va a notificar.        
        int dateQuery = 0;
        int userID = 0;
        Prints p = new Prints();
        Connect connect = new Connect();
        
        Prints.printHeader();
        
        do {
            try {
                System.out.println("<:::INICIO:::>");
                System.out.println("Ingrese su Correo Electronico");
                System.out.print(">> ");
                user = sc.nextLine();
                
                if (connect.validarUsuario(user)) { //**Funcion: Consulta DB - Select Email Tabla Users - Comparar cada linea del RS con mi varaible.
                    do {
                        try {
                            System.out.println();
                            System.out.println(Console_Colors.ANSI_GREEN + connect.retornarNombre(user) + Console_Colors.ANSI_RESET);
                            Prints.printMenu();
                            option2 = sc.nextInt();
                            
                            if (option2 >= 1 && option2 <= 3) {
                                switch (option2) {
                                    case 1:
                                        System.out.println();
                                        System.out.println(Console_Colors.ANSI_GREEN + connect.retornarNombre(user) + Console_Colors.ANSI_RESET);
                                        System.out.println("INGRESAR EVENTO");
                                        System.out.println("Ingrese Nombre del evento:");
                                        System.out.print(">> ");
                                        nombreEvento = sc.nextLine();
                                        System.out.println("Ingrese una Descripcion para el evento:");
                                        System.out.print(">> ");
                                        descripcionEvento = sc.nextLine();
                                        System.out.println("Ingrese la fecha del evento:");
                                        System.out.print(">> ");
                                        fechaEvento = sc.nextLine();
                                        System.out.println("Ingrese la Ubicacion del evento:");
                                        System.out.print(">> ");
                                        ubicacionEvento = sc.nextLine();
                                        do {
                                            System.out.println("Ingrese la hora de Inicio del evento (Ej: 14:30):");
                                            System.out.print(">> ");
                                            horaInicio = sc.nextLine();
                                            timeValidation = horaInicio.matches("\\d{2}:\\d{2}");
                                            if (timeValidation == false) {
                                                System.out.println(Console_Colors.ANSI_RED + "**FORMATO INCORRECTO" + Console_Colors.ANSI_RESET);
                                            } else {
                                                seq1 = Integer.parseInt(horaInicio.subSequence(0, 2).toString());
                                                seq2 = Integer.parseInt(horaInicio.subSequence(3, 5).toString());
                                                if (seq1 < 0 || seq1 >= 24) {
                                                    System.out.println(Console_Colors.ANSI_RED + "**HORA NO PUEDE SER MAYOR A 23" + Console_Colors.ANSI_RESET);
                                                    timeValidation = false;
                                                } else if (seq2 < 0 || seq2 > 59) {
                                                    System.out.println(Console_Colors.ANSI_RED + "**MINUTOS NO PUEDEN SER MAYOR A 59" + Console_Colors.ANSI_RESET);
                                                    timeValidation = false;
                                                }
                                            }
                                        } while (timeValidation != false);
                                        do {
                                            System.out.println("Ingrese la hora de Finalizacón del evento (Ej: 15:30):");
                                            System.out.print(">> ");
                                            horaFin = sc.nextLine();
                                            timeValidation = horaFin.matches("\\d{2}:\\d{2}");
                                            if (timeValidation == false) {
                                                System.out.println(Console_Colors.ANSI_RED + "**FORMATO INCORRECTO" + Console_Colors.ANSI_RESET);
                                            } else {
                                                seq3 = Integer.parseInt(horaFin.subSequence(0, 2).toString());
                                                seq4 = Integer.parseInt(horaFin.subSequence(3, 5).toString());
                                                if (seq3 < 0 || seq3 >= 24) {
                                                    System.out.println(Console_Colors.ANSI_RED + "**HORA NO PUEDE SER MAYOR A 23" + Console_Colors.ANSI_RESET);
                                                    timeValidation = false;
                                                } else if (seq4 < 0 || seq4 > 59) {
                                                    System.out.println(Console_Colors.ANSI_RED + "**MINUTOS NO PUEDEN SER MAYOR A 59" + Console_Colors.ANSI_RESET);
                                                    timeValidation = false;
                                                } else if ((seq3 < seq1) || ((seq3 == seq1) && (seq4 <= seq2))) {
                                                    System.out.println(Console_Colors.ANSI_RED + "**HORA DE FIN NO PUEDE SER MENOR A LA HORA DE INICIO" + Console_Colors.ANSI_RESET);
                                                    timeValidation = false;
                                                }
                                            }                                            
                                        } while (timeValidation != false);
                                        System.out.println("El correo electrónico " + Console_Colors.ANSI_BLUE + user + Console_Colors.ANSI_RESET + " sera moderador del Evento.");
                                        userID = connect.retornarID(user);
                                        System.out.println("Desea notificar a alguien via correo?  <SI>/<NO>");
                                        notificar = sc.nextLine();
                                        if (notificar.equalsIgnoreCase("NO")) {
                                            connect.insertarEvento(nombreEvento, descripcionEvento, fechaEvento, ubicacionEvento, horaInicio, horaFin, userID);
                                            //FUNCION PARA ENVIAR CORREO
                                        }else if (notificar.equalsIgnoreCase("SI")) {
                                            connect.insertarEvento(nombreEvento, descripcionEvento, fechaEvento, ubicacionEvento, horaInicio, horaFin, userID);
                                            System.out.println("A cuantos correos va a notificar?");
                                            cantCorreos = sc.nextInt();
                                            arrayCorreos = new String[cantCorreos];
                                            for (int i = 0; i < cantCorreos; i++) {
                                                System.out.print( i + ". Correo Electronico: ");
                                                arrayCorreos[i] = sc.nextLine();
                                            }
                                            //FUNCION PARA ENVIAR CORREOS
                                        }
                                        break;
                                    case 2:
                                        
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                System.out.print("**OPCION NO VÁLIDA");
                            }
                        } catch (InputMismatchException e) {
                            
                        }
                        
                    } while (option2 != 3);
                    
                } else {
                    System.out.println(Console_Colors.ANSI_RED + "**USUARIO NO EXISTE" + Console_Colors.ANSI_RESET);
                    System.out.println("Ingrese su Nombre: ");
                    System.out.print(">> ");
                    nombre = sc.nextLine();
                    int confirmacion = 0;
                    do {
                        String respuesta = "";
                        int salir = 0;
                        
                        do {
                            System.out.println("¿Desea utilizar " + Console_Colors.ANSI_BLUE + user + Console_Colors.ANSI_RESET + " como su Correo Electrónico?  <SI>/<NO>");
                            respuesta = sc.nextLine();
                            if (respuesta.equalsIgnoreCase("SI") || respuesta.equalsIgnoreCase("NO")) {
                                salir = 1;
                            } else {
                                System.out.println(Console_Colors.ANSI_RED + "**RESPUESTA NO VALIDA" + Console_Colors.ANSI_RESET);
                            }
                        } while (salir != 1);
                        if (respuesta.equalsIgnoreCase("SI")) {
                            correoElectronico = user;
                        } else if (respuesta.equalsIgnoreCase("NO")) {
                            do {
                                System.out.println("Ingrese su Correo Electrónico: ");
                                System.out.print(">> ");
                                correoElectronico = sc.nextLine();
                                System.out.println("Confirme su Correo Electrónico: ");
                                System.out.print(">> ");
                                correoElectronico2 = sc.nextLine();
                                if (correoElectronico.equals(correoElectronico2)) {
                                    confirmacion = 1;
                                } else {
                                    System.out.println(Console_Colors.ANSI_RED + "**ERROR DE CONFIRMACIÓN" + Console_Colors.ANSI_RESET);
                                }
                            } while (confirmacion != 1);
                        }
                        if (confirmacion == 0) {
                            System.out.println("Confirme su Correo Electrónico: ");
                            System.out.print(">> ");
                            correoElectronico2 = sc.nextLine();
                            if (correoElectronico.equals(correoElectronico2)) {
                                confirmacion = 1;
                            } else {
                                System.out.println(Console_Colors.ANSI_RED + "**ERROR DE CONFIRMACIÓN" + Console_Colors.ANSI_RESET);
                            }
                        }
                    } while (confirmacion != 1);
                    do {
                        System.out.println("Ingrese <Y> para guardar o <N> para cancelar.");
                        System.out.print(">> ");
                        String guarda = sc.nextLine();
                        
                        if (guarda.equalsIgnoreCase("Y")) {
                            connect.insertarUsuario(nombre, correoElectronico); //Insert DB Tabla Users
                            guardar = 1;
                        } else if (guarda.equalsIgnoreCase("N")) {
                            guardar = 1;
                        } else {
                            System.out.println(Console_Colors.ANSI_RED + "**RESPUESTA NO VALIDA" + Console_Colors.ANSI_RESET);
                        }
                        
                    } while (guardar != 1);
                    
                }
                
            } catch (InputMismatchException e) {
                
            }
            
        } while (option2 != 3);

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
