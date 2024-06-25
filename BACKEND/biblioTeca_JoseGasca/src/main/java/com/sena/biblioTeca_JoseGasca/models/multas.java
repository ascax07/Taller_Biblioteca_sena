package com.sena.biblioTeca_JoseGasca.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;


@Entity
public class multas {

    @Id
	@GeneratedValue(strategy =GenerationType.UUID)
	@Column(name="id", nullable=false, length = 36)
	private String id;
	

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario multado no puede estar vacío")
    private usuario usuario;
	

    @ManyToOne
    @JoinColumn(name = "prestamo_id", nullable = false)
    @NotNull(message = "El préstamo no puede estar vacío")
    private prestamo prestamo;
	

    @Column(name = "valor_multa", nullable = false)
    @Positive(message = "El valor de la multa debe ser positivo")
    private double valor_multa;
	
	

    @Column(name = "fecha_multa", nullable = false)
    @NotEmpty(message = "La fecha de la multa no puede estar vacía")
    private String fecha_multa;
	

    @Column(name = "estado", nullable = false, length = 13)
    @NotEmpty(message = "El estado no puede estar vacío")
    @Pattern(regexp = "pendiente|pagado", message = "El estado debe ser pendiente o pagado")
    private String estado;
    

    public multas() {
        super();
    }


    public multas(String id,
            com.sena.biblioTeca_JoseGasca.models.@NotNull(message = "El usuario multado no puede estar vacío") usuario usuario,
            com.sena.biblioTeca_JoseGasca.models.@NotNull(message = "El préstamo no puede estar vacío") prestamo prestamo,
            @Positive(message = "El valor de la multa debe ser positivo") double valor_multa,
            @NotEmpty(message = "La fecha de la multa no puede estar vacía") String fecha_multa,
            @NotEmpty(message = "El estado no puede estar vacío") @Pattern(regexp = "Pendiente|Pagado", message = "El estado debe ser Pendiente o Pagado") String estado) {
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


    public double getValor_multa() {
        return valor_multa;
    }


    public void setValor_multa(double valor_multa) {
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
