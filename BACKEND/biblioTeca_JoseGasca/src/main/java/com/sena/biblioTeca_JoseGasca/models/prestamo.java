package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@Entity
public class prestamo {

    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	

    @Column(name="fecha_prestamo", nullable=false)
    @NotNull(message = "La fecha de préstamo no puede estar vacía")
    private String fecha_prestamo;


    @Column(name="fecha_devolucion", nullable=false)
    @NotNull(message = "La fecha de devolución no puede estar vacía")
    private String fecha_devolucion;
	

    @ManyToOne
    @JoinColumn(name = "usuario_prestamo")
    @NotNull(message = "El usuario que realiza el préstamo no puede estar vacío")
    private usuario usuario;


    @ManyToOne
    @JoinColumn(name = "libro")
    @NotNull(message = "El libro prestado no puede estar vacío")
    private libro libro;


    @Column(name="estado", nullable=false, length = 13)
    @NotNull(message = "El estado no puede estar vacío")
    @Size(min = 1, max = 13, message = "El estado debe tener entre 1 y 13 caracteres (Estados: Prestamo, Entregado, Cancelado. )")
    private String estado;
	
    public prestamo() {
        super();
    }

    
    public prestamo(String id, @NotNull(message = "La fecha de préstamo no puede estar vacía") String fecha_prestamo,
            @NotNull(message = "La fecha de devolución no puede estar vacía") String fecha_devolucion,
            com.sena.biblioTeca_JoseGasca.models.@NotNull(message = "El usuario que realiza el préstamo no puede estar vacío") usuario usuario,
            com.sena.biblioTeca_JoseGasca.models.@NotNull(message = "El libro prestado no puede estar vacío") libro libro,
            @NotNull(message = "El estado no puede estar vacío") @Size(min = 1, max = 13, message = "El estado debe tener entre 1 y 13 caracteres (Estados: Préstamo, Entregado, Cancelado. )") String estado) {
        this.id = id;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.usuario = usuario;
        this.libro = libro;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }

    public libro getLibro() {
        return libro;
    }

    public void setLibro(libro libro) {
        this.libro = libro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}
