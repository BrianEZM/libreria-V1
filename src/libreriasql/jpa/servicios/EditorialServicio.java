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
import libreriasql.jpa.entidades.Editorial;

/**
 *
 * @author A286931
 */
public class EditorialServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    EntityManagerFactory EMF = Persistence.createEntityManagerFactory("libreriaSQL-JPAPU");
    EntityManager em = EMF.createEntityManager();

    public Editorial crearEditorial() {

        Editorial editorialx = new Editorial();
        Editorial editorialExiste = new Editorial();

        //boolean editorialExist = false;
        try {
            System.out.println("Ingrese el nombre de la editorial:");

            String nombre = leer.next().toUpperCase();

            List<Editorial> editorialesExistentes = listarEditoriales();

            for (Editorial editorialesExistente : editorialesExistentes) {
                if (editorialesExistente.getNombre().equalsIgnoreCase(nombre)) {

                    System.out.println("Esta Editorial ya existe en la base de datos, no se puede volver a agregar.");
                    System.out.println("De todos modos la misma sera asignada al libro cargado.");

                    editorialExiste = editorialesExistente;

                    //autorExist = true;
                    return editorialExiste;

                }
            }

            editorialx.setAlta(true);
            editorialx.setNombre(nombre);

            em.getTransaction().begin();
            em.persist(editorialx);
            em.getTransaction().commit();

            System.out.println("Editorial agregada a la base:");
            System.out.println(editorialx);

        } catch (Exception e) {
            System.out.println("ERRRRROR! >>>> " + e);
            System.out.println("Error al crear editorial!!!");
        }

        return editorialx;

    }

    public List<Editorial> listarEditoriales() {

        try {

            List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e").getResultList();

            return editoriales;

        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al listar libros!");

            return null;
        }

    }

    public void darDeBajaEditorial() {

        boolean bucle = true;

        try {

            System.out.println("Ingrese el nombre de la Editorial a dar de baja de la base:");
            String nombre = leer.next().toUpperCase();

            Editorial editorialx = (Editorial) em.createQuery("SELECT e"
                    + " FROM Editorial e"
                    + " WHERE e.nombre = :nombre").setParameter("nombre", nombre).
                    getSingleResult();

            System.out.println("Editorial a dar de baja:");
            System.out.println(editorialx);

            do {
                System.out.println("Confirmar: SI / NO");
                String eleccion = leer.next().toUpperCase();

                if (eleccion.equalsIgnoreCase("SI")) {

                    editorialx.setAlta(false);
                    System.out.println("Editorial dada de baja!");

                    em.getTransaction().begin();
                    em.merge(editorialx);
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
            System.out.println("Error al dar de baja Editorial");
        }

    }

    public void buscarEditorial() {

        try {

            System.out.println("Ingrese el nombre de la Editorial a buscar:");
            String nombre = leer.next().toUpperCase();

            Editorial editorialx = (Editorial) em.createQuery("SELECT e"
                    + " FROM Editorial e"
                    + " WHERE e.nombre = :nombre").setParameter("nombre", nombre).
                    getSingleResult();

            if (editorialx.getNombre().equalsIgnoreCase(nombre)) {
                if (editorialx.getAlta()) {
                    System.out.println("Editorial encontrada:");
                    System.out.println(editorialx);
                } else {
                    System.out.println("Editorial no disponible! (Dado de baja)");
                }
            } else {
                System.out.println("Editorial NO encontrada");
            }

        } catch (Exception e) {
            System.out.println("ERRRRROR! >>>> " + e);
            System.out.println("Error al buscar Editorial");
        }

    }
}
