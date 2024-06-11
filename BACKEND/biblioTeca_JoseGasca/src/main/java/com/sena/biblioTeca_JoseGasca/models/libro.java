package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class libro {
 
    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	
	
	@Column(name="titulo", nullable=false)
	private String titulo;
	
	
	@Column(name="autor", nullable=false, length = 45)
	private String autor;
	
	
	@Column(name="isbn", nullable=false, length = 10)
	private String isbn;
	
	
	@Column(name="num_ejem_dis", nullable=false, length = 250)
	private String num_ejem_dis;
	
	
	@Column(name="num_ejem_ocup", nullable=false, length = 250)
	private String num_ejem_ocup;


    public libro() {
        super();
    }


    public libro(String id, String titulo, String autor, String isbn, String num_ejem_dis, String num_ejem_ocup) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.num_ejem_dis = num_ejem_dis;
        this.num_ejem_ocup = num_ejem_ocup;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getAutor() {
        return autor;
    }


    public void setAutor(String autor) {
        this.autor = autor;
    }


    public String getIsbn() {
        return isbn;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public String getNum_ejem_dis() {
        return num_ejem_dis;
    }


    public void setNum_ejem_dis(String num_ejem_dis) {
        this.num_ejem_dis = num_ejem_dis;
    }


    public String getNum_ejem_ocup() {
        return num_ejem_ocup;
    }


    public void setNum_ejem_ocup(String num_ejem_ocup) {
        this.num_ejem_ocup = num_ejem_ocup;
    }


    
    
	
	
}
