/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
        String correoElectronico = "", correoElectronico2 = "", correoNotificar = "", password = "";
        String nombreEvento = "", descripcionEvento = "", fechaEvento = "", ubicacionEvento = "", horaInicio = "", horaFin = "";
        String notificar = "";//variable para saber si el usuario desea notificar a mas correos
        String[] arrayCorreos;
        String continuar = "";
        String diaConsulta = "", mesConsulta= "";
        boolean dateValidation, timeValidation;
        int seq1 = 0, seq2 = 0, seq3 = 0, seq4 = 0;
        int seqYear = 0, seqMonth = 0, seqDay = 0;
        int year = 0, month = 0, day = 0;
        int guardar = 0;
        int option2 = 0, option3 = 0;
        int cantCorreos = 0;//preguntar cuantos correos va a notificar.        
        int userID = 0;
        Prints p = new Prints();
        Connect connect = new Connect();
        SendEmail sendEmail = new SendEmail();
        GregorianCalendar calendar = new GregorianCalendar();

        
        Prints.imprimirHeader();
        do {
            try {
                System.out.println("<:::INICIO:::>");
                System.out.println("Ingrese su Correo Electronico");
                System.out.print(">> ");
                user = sc.nextLine();
                if (p.validateEmail(user) == true) {

                    if (connect.validarUsuario(user)) { //**Funcion: Consulta DB - Select Email Tabla Users - Comparar cada linea del RS con mi varaible.
                        do {
                            try {
                                System.out.println();
                                System.out.println(Console_Colors.ANSI_GREEN + connect.retornarNombre(user) + Console_Colors.ANSI_RESET);
                                Prints.imprimirMenuPrincipal();
                                option2 = sc.nextInt();

                                if (option2 >= 1 && option2 <= 3) {
                                    switch (option2) {
                                        case 1:
                                            System.out.println();
                                            System.out.println(Console_Colors.ANSI_PURPLE + "1. Ingresar Eventos" + Console_Colors.ANSI_RESET);
                                            System.out.println("Usuario Activo: " + Console_Colors.ANSI_GREEN + user + Console_Colors.ANSI_RESET);
                                            sc.nextLine();
                                            do {
                                                System.out.println("Ingrese Nombre del evento (Maximo 15 caracteres):");
                                                System.out.print(">> ");
                                                nombreEvento = sc.nextLine();
                                                if (nombreEvento.length() > 15) {
                                                    System.out.print(Console_Colors.ANSI_RED + "**NOMBRE DEMASIADO LARGO" + Console_Colors.ANSI_RESET);
                                                }
                                            } while (nombreEvento.length() > 15);
                                            do {
                                                System.out.println("Ingrese una Descripcion para el evento (Maximo 100 caracteres:");
                                                System.out.print(">> ");
                                                descripcionEvento = sc.nextLine();
                                                if (descripcionEvento.length() > 100) {
                                                    System.out.print(Console_Colors.ANSI_RED + "**DESCRIPCION DEMASIADO LARGA" + Console_Colors.ANSI_RESET);
                                                }
                                            } while (descripcionEvento.length() > 100);
                                            do {
                                                System.out.println("Ingrese la fecha del evento (YYYY-MM-DD / Ej: 2018-11-29):");
                                                System.out.print(">> ");
                                                fechaEvento = sc.nextLine();
                                                dateValidation = fechaEvento.matches("\\d{4}-\\d{2}-\\d{2}");
                                                if (dateValidation == false) {
                                                    System.out.println(Console_Colors.ANSI_RED + "**FORMATO INCORRECTO" + Console_Colors.ANSI_RESET);
                                                } else {
                                                    seqYear = Integer.parseInt(fechaEvento.subSequence(0, 4).toString());
                                                    seqMonth = Integer.parseInt(fechaEvento.subSequence(5, 7).toString());
                                                    seqDay = Integer.parseInt(fechaEvento.subSequence(8, 10).toString());
                                                    year = calendar.get(Calendar.YEAR);
                                                    month = calendar.get(Calendar.MONTH);
                                                    day = calendar.get(Calendar.DATE);

                                                    if (seqYear < year || (seqYear == year && seqMonth < month) || (seqYear == year && seqMonth == month && seqDay < day)) {
                                                        System.out.println(Console_Colors.ANSI_RED + "**LA FECHA NO PUEDE SER MENOR AL ACTUAL" + Console_Colors.ANSI_RESET);
                                                        dateValidation = false;
                                                    } else if (seqMonth <= 0 || seqMonth > 12) {
                                                        System.out.println(Console_Colors.ANSI_RED + "**EL MES ESTA FUERA DE RANGO" + Console_Colors.ANSI_RESET);
                                                        dateValidation = false;
                                                    } else if (seqMonth == 1 || seqMonth == 3 || seqMonth == 5
                                                            || seqMonth == 7 || seqMonth == 8 || seqMonth == 10
                                                            || seqMonth == 12) {
                                                        if (seqDay < 1 || seqDay > 31) {
                                                            System.out.println("No hay " + seqDay + " dias en el mes de " + p.completarMes(seqMonth));
                                                            dateValidation = false;
                                                        }
                                                    } else if (seqMonth == 4 || seqMonth == 6 || seqMonth == 9
                                                            || seqMonth == 11) {
                                                        if (seqDay < 1 || seqDay > 30) {
                                                            System.out.println("No hay " + seqDay + " dias en el mes de " + p.completarMes(seqMonth));
                                                            dateValidation = false;
                                                        }
                                                    } else if (seqMonth == 2) {
                                                        if (seqDay == 29) {
                                                            if (calendar.isLeapYear(seqYear) == false) {
                                                                System.out.println("El año " + seqYear + " NO es bisiesto");
                                                                dateValidation = false;
                                                            }
                                                        } else if (seqDay > 29) {
                                                            System.out.println("No hay " + seqDay + " dias en el mes de " + p.completarMes(seqMonth));
                                                            dateValidation = false;
                                                        }
                                                    }
                                                }
                                            } while (dateValidation != true);
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
                                            } while (timeValidation != true);
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
                                            } while (timeValidation != true);
                                            System.out.println("El correo electrónico " + Console_Colors.ANSI_BLUE + user + Console_Colors.ANSI_RESET + " sera moderador del Evento.");
                                            userID = connect.retornarID(user);
                                            System.out.println("Ingrese la contraseña de su correo:");
                                            System.out.print(">> ");
                                            password = sc.nextLine();
                                            System.out.println("Desea notificar a alguien más via correo?  <SI>/<NO>");
                                            notificar = sc.nextLine();
                                            if (notificar.equalsIgnoreCase("NO")) {
                                                connect.insertarEvento(nombreEvento, descripcionEvento, fechaEvento, ubicacionEvento, horaInicio, horaFin, userID);
                                                sendEmail.send(user, password, user, nombreEvento, descripcionEvento);//FUNCION PARA ENVIAR CORREO
                                            } else if (notificar.equalsIgnoreCase("SI")) {
                                                connect.insertarEvento(nombreEvento, descripcionEvento, fechaEvento, ubicacionEvento, horaInicio, horaFin, userID);
                                                System.out.println("A cuantos correos va a notificar?");
                                                cantCorreos = sc.nextInt();
                                                for (int i = 0; i < cantCorreos; i++) {
                                                    int salir = 0;
                                                    do {
                                                        System.out.print(i + ". Ingrese Correo Electronico: ");
                                                        correoNotificar = sc.nextLine();
                                                        if (p.validateEmail(correoNotificar) == true) {
                                                            sendEmail.send(user, password, correoNotificar, nombreEvento, descripcionEvento);
                                                        } else {
                                                            System.out.println(Console_Colors.ANSI_RED + "**CORREO ELECTRÓNICO NO ES VALIDO" + Console_Colors.ANSI_RESET);
                                                            System.out.println();
                                                            salir = 1;
                                                        }
                                                    } while (salir == 1);
                                                }//FUNCION PARA ENVIAR VARIOS CORREOS
                                            }
                                            break;
                                        case 2:
                                            System.out.println();
                                            System.out.println(Console_Colors.ANSI_PURPLE + "2. Ver Eventos" + Console_Colors.ANSI_RESET);
                                            System.out.println("Usuario Activo: " + Console_Colors.ANSI_GREEN + user + Console_Colors.ANSI_RESET);
                                            do {
                                                try {
                                                    Prints.imprimirMenuEventos();
                                                    option3 = sc.nextInt();
                                                    switch (option3) {
                                                        case 1:
                                                            System.out.println();
                                                            connect.mostrarEventosRecientes();
                                                            System.out.println("←Ingrese <R> para regresar");
                                                            continuar = sc.next();
                                                            break;
                                                        case 2:
                                                            do {
                                                                sc.nextLine();
                                                                System.out.print("Ingrese un Día (Ej: 2018-04-25) >>");
                                                                diaConsulta = sc.nextLine();
                                                                dateValidation = diaConsulta.matches("\\d{4}-\\d{2}-\\d{2}");
                                                                if (dateValidation == false) {
                                                                    System.out.println(Console_Colors.ANSI_RED + "**FORMATO INCORRECTO" + Console_Colors.ANSI_RESET);
                                                                } else {
                                                                    seqYear = Integer.parseInt(diaConsulta.subSequence(0, 4).toString());
                                                                    seqMonth = Integer.parseInt(diaConsulta.subSequence(5, 7).toString());
                                                                    seqDay = Integer.parseInt(diaConsulta.subSequence(8, 10).toString());
                                                                    year = calendar.get(Calendar.YEAR);
                                                                    month = calendar.get(Calendar.MONTH);
                                                                    day = calendar.get(Calendar.DATE);

                                                                    if (seqYear < year || (seqYear == year && seqMonth < month) || (seqYear == year && seqMonth == month && seqDay < day)) {
                                                                        System.out.println(Console_Colors.ANSI_RED + "**LA FECHA NO PUEDE SER MENOR AL ACTUAL" + Console_Colors.ANSI_RESET);
                                                                        dateValidation = false;
                                                                    } else if (seqMonth <= 0 || seqMonth > 12) {
                                                                        System.out.println(Console_Colors.ANSI_RED + "**EL MES ESTA FUERA DE RANGO" + Console_Colors.ANSI_RESET);
                                                                        dateValidation = false;
                                                                    } else if (seqMonth == 1 || seqMonth == 3 || seqMonth == 5
                                                                            || seqMonth == 7 || seqMonth == 8 || seqMonth == 10
                                                                            || seqMonth == 12) {
                                                                        if (seqDay < 1 || seqDay > 31) {
                                                                            System.out.println("No hay " + seqDay + " dias en el mes de " + p.completarMes(seqMonth));
                                                                            dateValidation = false;
                                                                        }
                                                                    } else if (seqMonth == 4 || seqMonth == 6 || seqMonth == 9
                                                                            || seqMonth == 11) {
                                                                        if (seqDay < 1 || seqDay > 30) {
                                                                            System.out.println("No hay " + seqDay + " dias en el mes de " + p.completarMes(seqMonth));
                                                                            dateValidation = false;
                                                                        }
                                                                    } else if (seqMonth == 2) {
                                                                        if (seqDay == 29) {
                                                                            if (calendar.isLeapYear(seqYear) == false) {
                                                                                System.out.println("El año " + seqYear + " NO es bisiesto");
                                                                                dateValidation = false;
                                                                            }
                                                                        } else if (seqDay > 29) {
                                                                            System.out.println("No hay " + seqDay + " dias en el mes de " + p.completarMes(seqMonth));
                                                                            dateValidation = false;
                                                                        }
                                                                    }
                                                                }
                                                            } while (dateValidation != true);
                                                            connect.mostrarEventosPorDia(diaConsulta);
                                                            System.out.println("←Ingrese <R> para regresar");
                                                            continuar = sc.next();
                                                            break;
                                                        case 3:
                                                            do{
                                                                sc.nextLine();
                                                                System.out.print("Ingrese Año y Mes (Ej: 2018-04) >>");
                                                                mesConsulta = sc.nextLine();
                                                                dateValidation = mesConsulta.matches("\\d{4}-\\d{2}");
                                                                if (dateValidation == false) {
                                                                    System.out.println(Console_Colors.ANSI_RED + "**FORMATO INCORRECTO" + Console_Colors.ANSI_RESET);
                                                                } else {
                                                                    seqYear = Integer.parseInt(mesConsulta.subSequence(0, 4).toString());
                                                                    seqMonth = Integer.parseInt(mesConsulta.subSequence(5, 7).toString());
                                                                    year = calendar.get(Calendar.YEAR);
                                                                    month = calendar.get(Calendar.MONTH);
                                                                    if (seqMonth <= 0 || seqMonth > 12) {
                                                                        System.out.println(Console_Colors.ANSI_RED + "**EL MES ESTA FUERA DE RANGO" + Console_Colors.ANSI_RESET);
                                                                        dateValidation = false;
                                                                    }
                                                                }
                                                            }while (dateValidation == false);
                                                            connect.mostrarEventosPorMes(mesConsulta);
                                                            System.out.println("←Ingrese <R> para regresar");
                                                            continuar = sc.next();
                                                            break;
                                                        default:
                                                            break;
                                                    }

                                                } catch (InputMismatchException e) {

                                                }
                                            } while (option3 != 4);
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
                } else {
                    System.out.println(Console_Colors.ANSI_RED + "**CORREO ELECTRÓNICO NO ES VALIDO" + Console_Colors.ANSI_RESET);
                    System.out.println();
                }

            } catch (InputMismatchException e) {

            }

        } while (option2 != 3);
    }
}
