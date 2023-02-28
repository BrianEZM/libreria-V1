/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreriasql.jpa.entidades;

import java.io.Serializable;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.persistence.*;

/**
 *
 * @author A286931
 */
@Entity
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String ISBN;
    private String titulo;
    private Integer anio;
    private Integer ejemplares;
    private Integer ejemplaresPrestados;
    private Integer ejeplaresRestantes;
    private boolean alta;
    @OneToOne
    private Autor autor;
    @OneToOne
    private Editorial editorial;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Libro() {
    }

    public Libro(String ISBN, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejeplaresRestantes, boolean alta, Autor autor, Editorial editorial) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejeplaresRestantes = ejeplaresRestantes;
        this.alta = alta;
        this.autor = autor;
        this.editorial = editorial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public Integer getEjeplaresRestantes() {
        return ejeplaresRestantes;
    }

    public void setEjeplaresRestantes(Integer ejeplaresRestantes) {
        this.ejeplaresRestantes = ejeplaresRestantes;
    }

    public boolean getAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ISBN != null ? ISBN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
        if ((this.ISBN == null && other.ISBN != null) || (this.ISBN != null && !this.ISBN.equals(other.ISBN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro{");
        //sb.append("id=").append(id);
        sb.append(", ISBN=").append(ISBN);
        sb.append(", titulo=").append(titulo);
        sb.append(", anio=").append(anio);
        sb.append(", ejemplares=").append(ejemplares);
        sb.append(", ejemplaresPrestados=").append(ejemplaresPrestados);
        sb.append(", ejeplaresRestantes=").append(ejeplaresRestantes);
        sb.append(", alta=").append(alta);
        sb.append(", autor=").append(autor);
        sb.append(", editorial=").append(editorial);
        sb.append('}');
        return sb.toString();
    }

}
