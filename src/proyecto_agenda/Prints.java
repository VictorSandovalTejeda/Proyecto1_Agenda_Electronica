/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_agenda;

/**
 *
 * @author pc HP
 */
public class Prints {

    public Prints() {
    }

    public static void imprimirHeader() {
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

    public static void imprimirMenuPrincipal() {
        System.out.println("------------------------------------------");
        System.out.println("--------------MENU PRINCIPAL--------------");
        System.out.println("1. Ingresar Eventos");
        System.out.println("2. Ver Eventos");
        System.out.println("3. <Salir del Programa>");
        System.out.println("4. ← Cambiar de cuenta");
        System.out.println("------------------------------------------");
        System.out.print("Elija una opcion >>");
    }

    public static void imprimirMenuEventos() {
        System.out.println("..........................................");
        System.out.println("...............MENU EVENTOS...............");
        System.out.println("1. Eventos mas PROXIMOS");
        System.out.println("2. Eventos por DÍA");
        System.out.println("3. Eventos por MES");
        System.out.println("4. ← Atras");
        System.out.println("..........................................");
        System.out.print("Elija una opcion >>");
    }

    public String completarMes(int seqMonth) {
        String nuevoMes = null;

        if (seqMonth == 1) {
            nuevoMes = "Enero";
        } else if (seqMonth == 2) {
            nuevoMes = "Febrero";
        } else if (seqMonth == 3) {
            nuevoMes = "Marzo";
        } else if (seqMonth == 4) {
            nuevoMes = "Abril";
        } else if (seqMonth == 5) {
            nuevoMes = "Mayo";
        } else if (seqMonth == 6) {
            nuevoMes = "Junio";
        } else if (seqMonth == 7) {
            nuevoMes = "Julio";
        } else if (seqMonth == 8) {
            nuevoMes = "Agosto";
        } else if (seqMonth == 9) {
            nuevoMes = "Septiembre";
        } else if (seqMonth == 10) {
            nuevoMes = "Octubre";
        } else if (seqMonth == 11) {
            nuevoMes = "Noviembre";
        } else if (seqMonth == 12) {
            nuevoMes = "Diciembre";
        }

        return nuevoMes;
    }
}
