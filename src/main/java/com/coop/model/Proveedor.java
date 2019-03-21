package com.coop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="proveedores")
public class Proveedor implements Serializable {
	
	private static final long serialVersionUID = 378416843050349263L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(length=100)
	private String poveedor;
	
	@ManyToOne
	@JoinColumn(name = "id_Localidad")
	private Localidad localidad;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPoveedor() {
		return poveedor;
	}
	public void setPoveedor(String poveedor) {
		this.poveedor = poveedor;
	}
	public Localidad getLocalidad() {
		return localidad;
	}
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

}
