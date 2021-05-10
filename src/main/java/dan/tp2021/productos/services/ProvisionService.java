package dan.tp2021.productos.services;

import java.util.List;

import dan.tp2021.productos.domain.Provision;


public interface ProvisionService {

	class ProvisionException extends Exception { ProvisionException(String message){super(message);}}
	class ProvisionNotFoundException extends ProvisionException { ProvisionNotFoundException(String message){super(message);}}

	public Provision getProvisionById(Integer id) throws ProvisionException;
	public List<Provision> getListaProvisiones();
	public Provision saveProvision(Provision p) throws ProvisionException;
	public Provision deleteProvisionById(Integer id) throws ProvisionException;
}
