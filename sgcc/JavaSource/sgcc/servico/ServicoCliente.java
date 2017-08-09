package sgcc.servico;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import sgcc.entidades.CartaoCredito;
import sgcc.entidades.Cliente;

@Stateless
public class ServicoCliente {

	
	@PersistenceContext(name="sgcc")
	EntityManager entityManager;
	

	/**
	 * Realiza o cadastro de um cliente.
	 * @param cliente - cliente a ser cadastrado
	 * @return - cliente cadastrado
	 * @throws Exception - não é possível cadastrar dois clientes com o mesmo cpf
	 */
	public Cliente CadastrarCliente(Cliente cliente) throws Exception{

		if(ConsultarCliente(cliente.getCpf())!=null)
			throw new Exception("CPF já cadastrado!");
		else{
			entityManager.persist(cliente);
			return cliente;
		}
		
	}
	
	/**
	 * Consulta cliente cadastrado pelo cpf
	 * @param cpf
	 * @return
	 */
	public Cliente ConsultarCliente(String cpf){
		Query query = entityManager.createQuery("FROM Cliente c WHERE c.cpf=:p1");
		query.setParameter("p1", cpf);
		try{
			return (Cliente) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	/**
	 * Lista clientes cadastrados
	 * @return
	 */
	public List<Cliente> ListarClientes(){
		
		Query query = entityManager.createQuery("FROM Cliente c");
		try{
			return query.getResultList();
		}catch(NoResultException e){
			return null;
		}	
	}
	
	/**
	 * Remove o cliente passado por parametro
	 * @param cliente
	 * @throws Exception - não é possivel remover um cliente com cartões validos
	 */
	public Cliente RemoverCliente(Cliente cliente) throws Exception{
		List<CartaoCredito> lista = ConsultarCartaoCredito(cliente);
		if(!lista.isEmpty())
			throw new Exception("Cliente ainda possui cartões validos!");
		else{
			entityManager.remove(entityManager.merge(cliente));
			return cliente;
		}
	}
	public List<CartaoCredito> ConsultarCartaoCredito(Cliente cliente){
		Query query = entityManager.createQuery("FROM CartaoCredito cc WHERE cc.cliente=:cliente");
		query.setParameter("cliente", cliente);
		try{
			return query.getResultList();
		}catch(NoResultException e){
			return null;
		}
		
	}
	
	
}
