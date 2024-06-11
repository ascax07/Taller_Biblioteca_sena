package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class usuario {
    
    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	
	@Column(name="direccion", nullable=false)
	private String direccion;
	
	
	@Column(name="correo_electronico", nullable=false, length = 320)
	private String correo_electronico;
	
	
	@Column(name="Tipo_usuario", nullable=false, length = 13)
	private String Tipo_usuario;


    public usuario() {
        super();
    }


    public usuario(String id, String nombre, String direccion, String correo_electronico, String tipo_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo_electronico = correo_electronico;
        Tipo_usuario = tipo_usuario;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getCorreo_electronico() {
        return correo_electronico;
    }


    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }


    public String getTipo_usuario() {
        return Tipo_usuario;
    }


    public void setTipo_usuario(String tipo_usuario) {
        Tipo_usuario = tipo_usuario;
    }

    
    
	

    



}
