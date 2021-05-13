package dan.tp2021.productos.domain;

import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Unidad {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String descripcion;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Unidad{" +
				"id=" + id +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}
