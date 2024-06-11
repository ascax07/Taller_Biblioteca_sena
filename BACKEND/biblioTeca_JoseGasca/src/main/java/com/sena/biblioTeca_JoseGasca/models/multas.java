package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.sena.biblioTeca_JoseGasca.models.usuario;



@Entity
public class multas {

    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	
	
	@ManyToOne
    @JoinColumn(name = "usuario_prestamo")
    private usuario usuario;
	
	
    @ManyToOne
    @JoinColumn(name = "prestamo")
    private prestamo prestamo;
	
	
	@Column(name="valor_multa", nullable=false, length = 10000)
	private String valor_multa;
	
	
	@Column(name="fecha_multa", nullable=false)
	private String fecha_multa;
	
	
	@Column(name="estado", nullable=false, length = 13)
	private String estado; 
    

    public multas() {
        super();
    }


    public multas(String id, com.sena.biblioTeca_JoseGasca.models.usuario usuario,
            com.sena.biblioTeca_JoseGasca.models.prestamo prestamo, String valor_multa, String fecha_multa,
            String estado) {
        this.id = id;
        this.usuario = usuario;
        this.prestamo = prestamo;
        this.valor_multa = valor_multa;
        this.fecha_multa = fecha_multa;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }


    public prestamo getPrestamo() {
        return prestamo;
    }


    public void setPrestamo(prestamo prestamo) {
        this.prestamo = prestamo;
    }


    public String getValor_multa() {
        return valor_multa;
    }


    public void setValor_multa(String valor_multa) {
        this.valor_multa = valor_multa;
    }


    public String getFecha_multa() {
        return fecha_multa;
    }


    public void setFecha_multa(String fecha_multa) {
        this.fecha_multa = fecha_multa;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    

}
