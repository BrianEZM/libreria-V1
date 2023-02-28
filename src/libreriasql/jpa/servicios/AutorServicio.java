/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreriasql.jpa.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import libreriasql.jpa.entidades.Autor;

/**
 *
 * @author A286931
 */
public class AutorServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    EntityManagerFactory EMF = Persistence.createEntityManagerFactory("libreriaSQL-JPAPU");
    EntityManager em = EMF.createEntityManager();

    public Autor crearAutor() {

        Autor autorx = new Autor();
        Autor autorExiste = new Autor();

        //boolean autorExist = false;
        try {
            System.out.println("Ingrese el nombre del autor:");

            String nombre = leer.next().toUpperCase();

            List<Autor> autoresExistentes = listarAutores();

            for (Autor autoresExistente : autoresExistentes) {
                if (autoresExistente.getNombre().equalsIgnoreCase(nombre)) {

                    System.out.println("Este Autor ya existe en la base de datos, no se puede volver a agregar.");
                    System.out.println("De todos modos el mismo sera asignado al libro cargado.");

                    autorExiste = autoresExistente;

                    //autorExist = true;
                    return autorExiste;

                }
            }

            //if (!autorExist) {
            autorx.setAlta(true);
            autorx.setNombre(nombre);

            em.getTransaction().begin();
            em.persist(autorx);
            em.getTransaction().commit();

            System.out.println("Autor agregado a la base:");
            System.out.println(autorx);

            //}
            //return null;
        } catch (Exception e) {
            System.out.println("ERRRRROR! >>>> " + e);
            System.out.println("Error al crear libro!!!");

            //return null;
        }

        return autorx;

    }

    public List<Autor> listarAutores() {

        try {

            List<Autor> autores = em.createQuery("SELECT a FROM Autor a").getResultList();

            return autores;

        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al listar libros!");

            return null;
        }

    }

    public void darDeBajaAutor() {

        boolean bucle = true;

        try {

            System.out.println("Ingrese el nombre del Autor a dar de baja de la base:");
            String nombre = leer.next().toUpperCase();

            Autor autorx = (Autor) em.createQuery("SELECT a"
                    + " FROM Autor a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).
                    getSingleResult();

            System.out.println("Autor a dar de baja:");
            System.out.println(autorx);

            do {
                System.out.println("Confirmar: SI / NO");
                String eleccion = leer.next().toUpperCase();

                if (eleccion.equalsIgnoreCase("SI")) {

                    autorx.setAlta(false);
                    System.out.println("Autor dado de baja!");

                    em.getTransaction().begin();
                    em.merge(autorx);
                    em.getTransaction().commit();

                    bucle = false;

                } else if (eleccion.equalsIgnoreCase("NO")) {

                    System.out.println("Accion 'DAR DE BAJA' cancelada.");

                    bucle = false;

                } else {

                    System.out.println("Opcion incorrecta!");
                }

            } while (bucle);

        } catch (Exception e) {
            System.out.println("ERRRRROR! >>>> " + e);
            System.out.println("Error al dar de baja Autor");
        }

    }

    public void buscarAutor() {

        try {

            System.out.println("Ingrese el nombre del Autor a buscar:");
            String nombre = leer.next().toUpperCase();

            Autor autorx = (Autor) em.createQuery("SELECT a"
                    + " FROM Autor a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).
                    getSingleResult();

            if (autorx.getNombre().equalsIgnoreCase(nombre)) {
                if (autorx.getAlta()) {
                    System.out.println("Autor encontrado:");
                    System.out.println(autorx);
                } else {
                    System.out.println("Autor no disponible! (Dado de baja)");
                }
            } else {
                System.out.println("Autor NO encontrado");
            }

        } catch (Exception e) {
            System.out.println("ERRRRROR! >>>> " + e);
            System.out.println("Error al buscar Autor");
        }

    }

}
