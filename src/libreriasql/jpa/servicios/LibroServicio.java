/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreriasql.jpa.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.*;
import libreriasql.jpa.entidades.Autor;
import libreriasql.jpa.entidades.Editorial;
import libreriasql.jpa.entidades.Libro;

/**
 *
 * @author A286931
 */
public class LibroServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    AutorServicio servAutor = new AutorServicio();
    EditorialServicio servEditorial = new EditorialServicio();

    EntityManagerFactory EMF = Persistence.createEntityManagerFactory("libreriaSQL-JPAPU");
    EntityManager em = EMF.createEntityManager();

    public Libro crearLibro() {

        Libro librox = new Libro();

        boolean tituloExist = false;

        try {

            System.out.println("Ingrese el titulo del Libro a registrar:");
            String titulo = leer.next().toUpperCase();

            System.out.println("Prueba sin agregar cadena");
            System.out.println(titulo);

            List<Libro> librosExistentes = comprobarLibros();

            for (Libro librosExistente : librosExistentes) {
                if (librosExistente.getTitulo().equalsIgnoreCase(titulo)) {

                    System.out.println("Este libro ya existe en la base de datos, no se puede volver a agregar.");

                    tituloExist = true;
                }
            }

            if (!tituloExist) {

                System.out.println("Ingrese el AÑO del Libro a registrar (numeros):");
                Integer anio = leer.nextInt();

                System.out.println("Ingrese el STOCK disponible del Libro a registrar (total ejemplares):");
                Integer stock = leer.nextInt();

                System.out.println("¿Desea agregar el Autor ahora?");
                String agregarAutor = leer.next().toUpperCase();

                try {
                    if (agregarAutor.equalsIgnoreCase("SI")) {
                        Autor autorx = servAutor.crearAutor();
                        librox.setAutor(autorx);
                    } else {
                        System.out.println("Podra agregarlo luego desde el menu principal");
                    }

                    System.out.println("¿Desea agregar la Editorial ahora?");
                    String agregarEditorial = leer.next().toUpperCase();

                    if (agregarEditorial.equalsIgnoreCase("SI")) {
                        Editorial editorialx = servEditorial.crearEditorial();
                        librox.setEditorial(editorialx);
                    } else {
                        System.out.println("Podra agregarla luego desde el menu principal");
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e);
                    System.out.println("Error al cargar datos autor/editorial, podra cargarlos luego desde el menu principal");
                }

                int ejemplaresInicialmentePrestados = 0;

                librox.setTitulo(titulo);
                librox.setAlta(true);
                librox.setEjemplares(stock);
                librox.setEjemplaresPrestados(ejemplaresInicialmentePrestados);
                librox.setEjeplaresRestantes(stock - ejemplaresInicialmentePrestados);
                librox.setAnio(anio);
                //librox.setISBN(ISBN);

                em.getTransaction().begin();
                em.persist(librox);
                em.getTransaction().commit();

                System.out.println("Libro agregado:");
                System.out.println(librox);

            }

        } catch (Exception e) {
            System.out.println("ERRRRROR! >>>> " + e);
            System.out.println("Error al crear libro!!!");
        }
        return librox;
    }

    public void agregarAutor() {

        try {

            System.out.println("Ingrese el nombre del Libro:");
            String titulo = leer.next().toUpperCase();

            System.out.println("Ingrese el nombre del autor de este libro:");
            String autor = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            Autor autorx = (Autor) em.createQuery("SELECT a"
                    + " FROM Autor a"
                    + " WHERE a.nombre = :autor").setParameter("autor", autor).
                    getSingleResult();

            librox.setAutor(autorx);

            em.getTransaction().begin();
            em.merge(librox);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("ERRRORRR " + e);
            System.out.println("Error al agregar modificacion!");
        }

    }

    public void agregarEditorial() {

        try {

            System.out.println("Ingrese el nombre del Libro:");
            String titulo = leer.next().toUpperCase();

            System.out.println("Ingrese el nombre de la editorial de este libro:");
            String editorial = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            Editorial editorialx = (Editorial) em.createQuery("SELECT e"
                    + " FROM Editorial e"
                    + " WHERE e.nombre = :editorial").setParameter("editorial", editorial).
                    getSingleResult();

            librox.setEditorial(editorialx);

            em.getTransaction().begin();
            em.merge(librox);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("ERRRORRR " + e);
            System.out.println("Error al agregar modificacion!");
        }

    }

    public void prestarLibro() {

        try {
            System.out.println("Ingrese el nombre del Libro a prestar:");
            String titulo = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            System.out.println("¿Cuantas unidades del libro se estan prestando en esta ocasion?");
            int prestar = leer.nextInt();
            int totalPrestar = prestar + librox.getEjemplaresPrestados();

            if (totalPrestar > librox.getEjeplaresRestantes()) {

                System.out.println("No se dispone de esa cantidad de copias para este libro");
                System.out.println("El total disponible actualmente es " + librox.getEjeplaresRestantes());

            } else {

                System.out.println("Cantidad de copias prestadas OK");
                int stock = librox.getEjeplaresRestantes() - prestar;
                System.out.println("La cantidad de ejemplares disponibles ahora es " + stock);

                librox.setEjemplaresPrestados(totalPrestar);
                librox.setEjeplaresRestantes(stock);

                em.getTransaction().begin();
                em.merge(librox);
                em.getTransaction().commit();

            }

        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al prestar libro!");
        }

    }

    public void recibirLibro() {

        try {
            System.out.println("Ingrese el nombre del Libro a reingresar:");
            String titulo = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            int prestados = librox.getEjemplaresPrestados();

            System.out.println("¿Cuantas unidades del libro se estan devolviendo en esta ocasion?");
            int devolver = leer.nextInt();

            if (devolver > prestados) {

                System.out.println("La cantidad ingresada es incorrecta, supera el total de unidades prestadas actualmente: "
                        + prestados);

            } else {
                int prestadosActual = prestados - devolver;

                System.out.println("Cantidad de copias a reingresar OK");
                int stock = librox.getEjeplaresRestantes() + devolver;
                System.out.println("La cantidad de ejemplares disponibles ahora es " + stock);

                librox.setEjemplaresPrestados(prestadosActual);
                librox.setEjeplaresRestantes(stock);

                em.getTransaction().begin();
                em.merge(librox);
                em.getTransaction().commit();

            }

        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al prestar libro!");
        }

    }

    public void resetStockLibro() {

        try {
            System.out.println("Ingrese el nombre del Libro a resetear stock:");
            String titulo = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            librox.setEjemplaresPrestados(0);
            librox.setEjeplaresRestantes(librox.getEjemplares());

            System.out.println("Libro resetado:");
            System.out.println(librox);

            em.getTransaction().begin();
            em.merge(librox);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al resetear libro!");
        }

    }

    public void consultarStock() {

        System.out.println("Ingrese el nombre del Libro a consultar stock:");
        String titulo = leer.next().toUpperCase();

        Libro librox = (Libro) em.createQuery("SELECT l"
                + " FROM Libro l"
                + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                getSingleResult();

        System.out.println("La cantidad disponible de este libro es:");
        System.out.println(librox.getEjeplaresRestantes());

    }

    public void darDeBajaLibro() {

        boolean bucle = true;

        try {

            System.out.println("Ingrese el nombre del Libro a dar de baja de la base:");
            String titulo = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            System.out.println("Libro a dar de baja:");
            System.out.println(librox);

            do {
                System.out.println("Confirmar: SI / NO");
                String eleccion = leer.next().toUpperCase();

                if (eleccion.equalsIgnoreCase("SI")) {

                    librox.setAlta(false);
                    System.out.println("Libro dado de baja!");

                    em.getTransaction().begin();
                    em.merge(librox);
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
            System.out.println("Error IT: " + e);
            System.out.println("Error al dar de baja libro!");
        }

    }

    public void buscarLibros() {

        try {

            System.out.println("Ingrese la opcion con la que se buscara el libro:");
            System.out.println("""
                               1-Consultar Libro por TITULO
                               2-Consultar Libro por AUTOR
                               3-Consultar Libro por EDITORIAL
                               4-Volver al menu anterior""");

            String eleccion = leer.next().toUpperCase();

            switch (eleccion) {
                case "1":
                    System.out.println("Ingrese el nombre del Libro:");
                    String titulo = leer.next().toUpperCase();

                    Libro librox = (Libro) em.createQuery("SELECT l"
                            + " FROM Libro l"
                            + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                            getSingleResult();

                    if (librox.getTitulo().equalsIgnoreCase(titulo)) {
                        if (librox.getAlta()) {
                            System.out.println("Libro encontrado!");
                            System.out.println(librox);
                        } else {
                            System.out.println("Libro no disponible! (Dado de baja)");
                        }
                    } else {
                        System.out.println("Libro NO encontrado");
                    }

                    break;

                case "2":
                    System.out.println("Ingrese el nombre del Autor:");
                    String nombre = leer.next().toUpperCase();

                    Autor autorx = (Autor) em.createQuery("SELECT a"
                            + " FROM Autor a"
                            + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).
                            getSingleResult();

                    if (autorx.getNombre().equalsIgnoreCase(nombre)) {
                        if (autorx.getAlta()) {
                            System.out.println("Autor encontrado!");

                            String ID = autorx.getId();

                            List<Libro> libros = em.createQuery("SELECT l"
                                    + " FROM Libro l").getResultList();

                            if (!libros.isEmpty()) {
                                boolean verificador = false;
                                for (int i = 0; i < libros.size(); i++) {

                                    if (libros.get(i).getAutor() == null) {
                                        continue;
                                        //verificador = true;
                                    }

                                    if (libros.get(i).getAutor().getId() == ID) {
                                        System.out.println(libros.get(i));
                                        verificador = true;
                                    }
                                }

                                if (!verificador) {
                                    System.out.println("No hay libros para este Autor");
                                }

                            } else if (libros == null) {
                                System.out.println("No hay libros en la base de datos!");
                            } else {
                                System.out.println("No hay libros en la base de datos!");
                            }

                        } else {
                            System.out.println("Autor no disponible! (Dado de baja)");
                        }

                    } else {
                        System.out.println("Libros NO encontrados para el Autor ingresado");
                    }
                    break;

                case "3":
                    System.out.println("Ingrese el nombre de la Editorial:");
                    String editorial = leer.next().toUpperCase();

                    Editorial editorialx = (Editorial) em.createQuery("SELECT e"
                            + " FROM Editorial e"
                            + " WHERE e.nombre = :nombre").setParameter("nombre", editorial).
                            getSingleResult();

                    if (editorialx.getNombre().equalsIgnoreCase(editorial)) {
                        if (editorialx.getAlta()) {
                            System.out.println("Editorial encontrada!");

                            String ID = editorialx.getId();

                            List<Libro> libros = em.createQuery("SELECT l"
                                    + " FROM Libro l").getResultList();

                            for (int i = 0; i < libros.size(); i++) {
                                if (libros.get(i).getEditorial().getId().equalsIgnoreCase(ID)) {
                                    System.out.println(libros.get(i));
                                }
                            }
                        } else {
                            System.out.println("Editorial no disponible! (Dada de baja)");
                        }
                    } else {
                        System.out.println("Libros NO encontrados para la Editorial ingresada");
                    }

                    break;

                case "4":
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Valor incorrecto! Volviendo al menu principal.");
            }
        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al listar libros!");

        }

    }

    public List<Libro> comprobarLibros() {

        try {

            List<Libro> libros = em.createQuery("SELECT l FROM Libro l").getResultList();

            return libros;

        } catch (Exception e) {
            System.out.println("Error IT: " + e);
            System.out.println("Error al listar libros!");

            return null;
        }
    }

    public int modificarEjemplares(Libro librox) {

        System.out.println("¿Cuantos ejemplares se suman al total?");

        int sumarEjemplares = 0;
        try {

            int actualEjemplares = librox.getEjemplares();

            sumarEjemplares = leer.nextInt() + actualEjemplares;

        } catch (Exception e) {

            System.out.println("Error IT: " + e);
            System.out.println("Error al sumar ejemplares!");
        }

        System.out.println("La nueva cantidad de ejemplares totales es " + sumarEjemplares);
        return sumarEjemplares;
    }

    public void modificarLibro() {

        //Libro librox2 = new Libro();
        try {

            System.out.println("Ingrese el nombre del Libro:");
            String titulo = leer.next().toUpperCase();

            Libro librox = (Libro) em.createQuery("SELECT l"
                    + " FROM Libro l"
                    + " WHERE l.titulo = :titulo").setParameter("titulo", titulo).
                    getSingleResult();

            if (librox.getTitulo().equalsIgnoreCase(titulo)) {
                if (librox.getAlta()) {

                    System.out.println("¿Que dato desea modificar?");

                    System.out.println("Titulo: " + librox.getTitulo() + " - Anio: " + librox.getAnio() + " - Ejemplares totales: " + librox.getEjemplares());

                    System.out.println("""
                               1-Titulo
                               2-Anio
                               3-Cantidad de Ejemplares
                               4-Volver al menu anterior""");

                    String eleccion = leer.next();

                    switch (eleccion) {
                        case "1":
                            System.out.println("Ingrese el nuevo Titulo");
                            librox.setTitulo(leer.next().toUpperCase());
                            break;

                        case "2":
                            System.out.println("Ingrese el nuevo Anio");
                            librox.setAnio(leer.nextInt());
                            break;

                        case "3":
                            int suma = modificarEjemplares(librox);
                            librox.setEjemplares(suma);
                            librox.setEjeplaresRestantes(suma - librox.getEjemplaresPrestados());
                            break;

                        case "4":
                            System.out.println("Volviendo...");
                            break;

                        default:
                            System.out.println("Valor incorrecto! Volviendo al menu principal.");
                    }

                    em.getTransaction().begin();
                    em.merge(librox);
                    em.getTransaction().commit();

                } else {
                    System.out.println("Libro no disponible! (Dado de baja)");
                }

            } else {

                System.out.println("Libro NO encontrado!");

            }

        } catch (Exception e) {
            System.out.println("ERRRORRR " + e);
            System.out.println("Error al agregar modificacion!");
        }

    }

}
