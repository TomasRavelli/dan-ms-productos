package dan.tp2021.productos.domain;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class MovimientosStock {
	//Aca no tendria que ir una Provision y sacar ambos detalles? segun el enunciado, cada movimiento de stock conoce que provision lo creo.
	@Id
	private Integer id;
	@OneToOne
	private DetallePedido detallePedido;
	@OneToOne
	private DetalleProvision detalleProvision;
	@ManyToOne
	private Material material;
	private Integer cantidadEntrada;
	private Integer cantidadSalida;
	private Instant fecha;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public DetallePedido getDetallePedido() {
		return detallePedido;
	}
	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}
	public DetalleProvision getDetalleProvision() {
		return detalleProvision;
	}
	public void setDetalleProvision(DetalleProvision detalleProvision) {
		this.detalleProvision = detalleProvision;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Integer getCantidadEntrada() {
		return cantidadEntrada;
	}
	public void setCantidadEntrada(Integer cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}
	public Integer getCantidadSalida() {
		return cantidadSalida;
	}
	public void setCantidadSalida(Integer cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}
	public Instant getFecha() {
		return fecha;
	}
	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}
	
	
}
