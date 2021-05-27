package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.Provision;
import dan.tp2021.productos.exeptions.provision.ProvisionException;


public interface ProvisionService {

	Provision getProvisionById(Integer id) throws ProvisionException;
	List<Provision> getListaProvisiones();
	Provision saveProvision(Provision p) throws ProvisionException;
	Provision deleteProvisionById(Integer id) throws ProvisionException;
}
