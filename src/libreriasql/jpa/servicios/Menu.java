/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreriasql.jpa.servicios;

import java.util.Scanner;
import libreriasql.jpa.entidades.Libro;

/**
 *
 * @author A286931
 */
public class Menu {

    LibroServicio servLibro = new LibroServicio();
    AutorServicio servAutor = new AutorServicio();
    EditorialServicio servEditorial = new EditorialServicio();

    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public void menuMain() {

        boolean login = true;

        do {
            try {
                System.out.println("Ingrese la opcion deseada:");
                System.out.println("""
                               1-Cargar Entidad
                               2-Prestar Libro
                               3-Recibir Libro
                               4-Consultar Entidad
                               5-Modificar Libro
                               6-Resetear Stock Libro
                               7-Dar de Baja Entidad
                               8-Salir""");

                String option = leer.next();

                switch (option) {
                    case "1":
                        subMenu1();
                        break;

                    case "2":
                        servLibro.prestarLibro();
                        break;

                    case "3":
                        servLibro.recibirLibro();
                        break;

                    case "4":
                        subMenu3();
                        break;

                    case "5":
                        subMenu2();
                        break;

                    case "6":
                        servLibro.resetStockLibro();
                        break;

                    case "7":
                        servLibro.darDeBajaLibro();
                        break;

                    case "8":
                        System.out.println("Adios!");
                        login = false;
                        break;

                    default:
                        System.out.println("Opcion incorrecta, ingrese un valor de 1 a 9");
                }

            } catch (Exception e) {

                System.out.println("Error IT: " + e);
                System.out.println("Error en el menu main");
            }

        } while (login);

    }

    //MENU PARA CARGAR UNA ENTIDAD
    public void subMenu1() {

        try {
            System.out.println("Ingrese la opcion deseada:");
            System.out.println("""
                               1-Cargar Autor
                               2-Cargar Editorial
                               3-Cargar Libro
                               4-Volver al menu anterior""");

            String eleccion = leer.next().toUpperCase();

            switch (eleccion) {
                case "1":
                    servAutor.crearAutor();
                    break;
                case "2":
                    servEditorial.crearEditorial();
                    break;
                case "3":
                    servLibro.crearLibro();
                    break;
                case "4":
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Valor incorrecto! Volviendo al menu principal.");
            }

        } catch (Exception e) {

            System.out.println("Error IT: " + e);
            System.out.println("Error en el sub-menu1");
        }

    }

    //MENU PARA ALTERAR ENTIDAD 'LIBRO'
    public void subMenu2() {

        try {
            System.out.println("Ingrese la opcion deseada:");
            System.out.println("""
                               1-Modificar/Agregar Autor
                               2-Modificar/Agregar Editorial
                               3-Modificar Libro
                               4-Volver al menu anterior""");

            String eleccion = leer.next().toUpperCase();

            switch (eleccion) {
                case "1":
                    servLibro.agregarAutor();
                    break;
                case "2":
                    servLibro.agregarEditorial();
                    break;
                case "3":
                    servLibro.modificarLibro();
                    break;
                case "4":
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Valor incorrecto! Volviendo al menu principal.");
            }

        } catch (Exception e) {

            System.out.println("Error IT: " + e);
            System.out.println("Error en el sub-menu2");
        }

    }

    //MENU PARA CONSULTAR LIBRO POR DISTINTOS PARAMETROS
    public void subMenu3() {

        try {
            System.out.println("Ingrese la opcion deseada:");
            System.out.println("""
                               1-Consultar Libro
                               2-Consultar Autor
                               3-Consultar Editorial
                               4-Volver al menu anterior""");

            String eleccion = leer.next().toUpperCase();

            switch (eleccion) {
                case "1":
                    servLibro.buscarLibros();
                    break;
                case "2":
                    servAutor.buscarAutor();
                    break;
                case "3":
                    servEditorial.buscarEditorial();
                    break;
                case "4":
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Valor incorrecto! Volviendo al menu principal.");
            }

        } catch (Exception e) {

            System.out.println("Error IT: " + e);
            System.out.println("Error en el sub-menu2");
        }

    }

}
