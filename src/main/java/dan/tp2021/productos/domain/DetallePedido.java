package dan.tp2021.productos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//Esta clase es necesaria? Sí
@Entity
public class DetallePedido {
	@Id
	private Integer id;
	@ManyToOne
	private Material material;
	private Integer cantidad;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	
	
}
