package dan.tp2021.productos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = DetalleProvision.class)
@Entity
public class DetalleProvision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(optional = false)
    private Material material;
    private Integer cantidad;
    @ManyToOne
    private Provision provision;

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

	public Provision getProvision() {
		return provision;
	}

	public void setProvision(Provision provision) {
		this.provision = provision;
	}

    @Override
    public String toString() {
        return "DetalleProvision{" +
                "id=" + id +
                ", material=" + material +
                ", cantidad=" + cantidad +
                ", provision=" + (provision != null ? provision.getId() : "null") +
                '}';
    }
}
