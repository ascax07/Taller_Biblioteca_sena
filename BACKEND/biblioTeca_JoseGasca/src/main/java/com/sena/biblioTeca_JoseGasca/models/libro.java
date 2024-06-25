package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class libro {

    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	
	
	@NotNull(message = "El título no puede estar vacío")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
	
	
    @NotNull(message = "El autor no puede estar vacío")
    @Size(min = 1, max = 100, message = "El autor debe tener entre 1 y 100 caracteres")
    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

// ISBN CON PREFIJO: (por si las moscas xdd)
    // @NotNull(message = "El ISBN no puede estar vacío")
    // @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$", message = "El ISBN debe ser válido y tener 13 caracteres")
    // @Column(name = "isbn", nullable = false, unique = true, length = 13)
    // private String isbn;

    @NotNull(message = "El ISBN no puede estar vacío")
    @Pattern(regexp = "^\\d{13}$", message = "El ISBN debe ser válido y tener 13 caracteres numéricos")
    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String isbn;

    @NotNull(message = "El género no puede estar vacío")
    @Column(name = "genero", nullable = false)
    private String genero;

    @Min(value = 0, message = "El número de ejemplares disponibles no puede ser negativo")
    @Column(name = "num_ejem_dis", nullable = false)
    private int num_ejem_dis;
	
    @Min(value = 0, message = "El número de ejemplares ocupados no puede ser negativo")
    @Column(name = "num_ejem_ocup", nullable = false)
    private int num_ejem_ocup;
	
    @AssertTrue(message = "El número de ejemplares ocupados no puede ser mayor que el número de ejemplares disponibles")
    private boolean isNumeroEjemplaresOcupadosValid() {
        return num_ejem_ocup <= num_ejem_dis;
    }


    public libro() {
        super();
    }


    public libro(String id, String titulo, String autor, String isbn, String genero, int num_ejem_dis,
            int num_ejem_ocup) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
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


    public String getGenero() {
        return genero;
    }


    public void setGenero(String genero) {
        this.genero = genero;
    }


    public int getNum_ejem_dis() {
        return num_ejem_dis;
    }


    public void setNum_ejem_dis(int num_ejem_dis) {
        this.num_ejem_dis = num_ejem_dis;
    }


    public int getNum_ejem_ocup() {
        return num_ejem_ocup;
    }


    public void setNum_ejem_ocup(int num_ejem_ocup) {
        this.num_ejem_ocup = num_ejem_ocup;
    }

}
