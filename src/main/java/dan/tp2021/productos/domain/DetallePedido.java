package dan.tp2021.productos.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//TODO no deberia apuntar al schema de Pedido?
@Entity
@Table(name = "detalle_pedido", catalog = "`dan-ms-pedido`")
public class DetallePedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "producto_id", foreignKey = @ForeignKey(name = "FK_producto_detalle_pedido")) //Usamos el nombre que se le da al material en dan-ms-pedidos, porque esta entidad le pertenece a ese microservicio
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
