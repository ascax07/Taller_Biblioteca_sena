package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.sena.biblioTeca_JoseGasca.models.usuario;

import com.sena.biblioTeca_JoseGasca.models.libro;

@Entity
public class prestamo {

    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	
	
	@Column(name="fecha_prestamo", nullable=false)
	private String fecha_prestamo;
	
	
	@Column(name="fecha_devolucion", nullable=false, length = 45)
	private String fecha_devolucion;
	

	@ManyToOne
    @JoinColumn(name = "usuario_prestamo")
    private usuario usuario;

    
	@ManyToOne
    @JoinColumn(name = "libro_prestamo")
    private libro libro;

    @Column(name="estado", nullable=false, length = 9)
	private String estado;
	
    public prestamo() {
        super();
    }

    public prestamo(String id, String fecha_prestamo, String fecha_devolucion,
            com.sena.biblioTeca_JoseGasca.models.usuario usuario, com.sena.biblioTeca_JoseGasca.models.libro libro,
            String estado) {
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
