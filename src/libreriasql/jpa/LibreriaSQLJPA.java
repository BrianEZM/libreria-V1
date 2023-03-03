/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package libreriasql.jpa;

import java.util.Scanner;
import libreriasql.jpa.entidades.Libro;
import libreriasql.jpa.servicios.AutorServicio;
import libreriasql.jpa.servicios.EditorialServicio;
import libreriasql.jpa.servicios.LibroServicio;
import libreriasql.jpa.servicios.Menu;

/**
 *
 * @author A286931
 */
public class LibreriaSQLJPA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        LibroServicio servLibro = new LibroServicio();
//        AutorServicio servAutor = new AutorServicio();
//        EditorialServicio servEditorial = new EditorialServicio();
        Menu servMenu = new Menu();
//
//        Scanner leer = new Scanner(System.in).useDelimiter("\n");

        System.out.println("ESTOY PARADO EN LA RAMA 'MAIN'");
        servMenu.menuMain();

    }

}
