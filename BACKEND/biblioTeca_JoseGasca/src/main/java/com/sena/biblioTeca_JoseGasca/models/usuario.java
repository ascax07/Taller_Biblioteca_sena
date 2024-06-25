package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class usuario {
    
    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	
	
	@Column(name="nombre", nullable=false)
    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;
	
	
    @Column(name="direccion", nullable=false)
    @NotNull( message = "La dirección no puede estar vacía")
    @Size(min = 1, max = 100, message = "la direccion debe tener entre 1 y 100 caracteres")
    private String direccion;
	
	

    @NotNull(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico debe tener un formato válido de  @  ")
    @Size(min = 1,  message = "la direccion debe tener  minimo 1  caracter")
    @Column(name="correo_electronico", nullable=false, unique=true)
	private String correo_electronico;
	
    @Column(name="tipo_usuario", nullable=false)
    @NotNull(message = "El tipo de usuario no puede estar vacío")
    @Pattern(regexp = "lector|bibliotecario|administrador", message = "El tipo de usuario debe ser uno de los siguientes: lector, bibliotecario, administrador")
	private String tipo_usuario;


    public usuario() {
        super();
    }


    public usuario(String id,
            @NotNull(message = "El nombre no puede estar vacío") @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres") String nombre,
            @NotNull(message = "La dirección no puede estar vacía") @Size(min = 1, max = 100, message = "la direccion debe tener entre 1 y 100 caracteres") String direccion,
            @NotNull(message = "El correo electrónico no puede estar vacío") @Email(message = "El correo electrónico debe tener un formato válido de  @  ") @Size(min = 1, message = "la direccion debe tener  minimo 1  caracter") String correo_electronico,
            @NotNull(message = "El tipo de usuario no puede estar vacío") @Pattern(regexp = "lector|bibliotecario|administrador", message = "El tipo de usuario debe ser uno de los siguientes: lector, bibliotecario, administrador") String tipo_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo_electronico = correo_electronico;
        this.tipo_usuario = tipo_usuario;
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
        return tipo_usuario;
    }


    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }


}
