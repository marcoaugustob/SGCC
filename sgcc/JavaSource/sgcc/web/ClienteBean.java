package sgcc.web;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import sgcc.entidades.CartaoCredito;
import sgcc.entidades.Cliente;
import sgcc.servico.ServicoCliente;

@Named
@RequestScoped
public class ClienteBean {

	@EJB
	ServicoCliente servicoCliente;
	
	private Cliente cliente;

	public ClienteBean(){
		cliente = new Cliente();
	}
	
	public void CadastrarCliente(){
		
		try {
			servicoCliente.CadastrarCliente(cliente);
			Messages.create("Cliente cadastrado com sucesso!").add();
		} catch (Exception e) {
			Messages.create(e.getMessage()).warn().add();
		}
	}
	
	public List<Cliente> ListarClientes(){
		return servicoCliente.ListarClientes();
	}
	
	public void RemoverCliente(Cliente cliente){
		try {
			servicoCliente.RemoverCliente(cliente);
			Messages.create("Cliente removido com sucesso!").add();
		} catch (Exception e) {
			Messages.create(e.getMessage()).warn().add();
		}
	}

	public List<CartaoCredito> ListarCartoesCliente(){
		return servicoCliente.ConsultarCartaoCredito(cliente);
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
