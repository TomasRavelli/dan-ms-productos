package dan.tp2021.productos.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(catalog = "`dan-ms-productos`")
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Integer stockActual;
	private Integer stockMinimo;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Unidad unidad;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getStockActual() {
		return stockActual;
	}
	public void setStockActual(Integer stockActual) {
		this.stockActual = stockActual;
	}
	public Integer getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(Integer stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	@Override
	public String toString() {
		return "Material{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", precio=" + precio +
				", stockActual=" + stockActual +
				", stockMinimo=" + stockMinimo +
				", unidad=" + unidad +
				'}';
	}
}
