package sgcc.servico;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import sgcc.entidades.CartaoCredito;
import sgcc.entidades.Transacao;

@Stateless
public class ServicoCartaoCredito {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void CadastrarCartao(CartaoCredito cartao){
		entityManager.persist(cartao);
	}
	
	public CartaoCredito ConsultaCartao(CartaoCredito cartao){
		Query query = entityManager.createQuery("FROM CartaoCredito cc WHERE cc.numero=:numero AND cc.codSeguranca=:cod");
		query.setParameter("numero", cartao.getNumero());
		query.setParameter("cod", cartao.getCodSeguranca());
		try{
			return (CartaoCredito) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	public CartaoCredito ConsultaCartao(String numero, String cod){
		Query query = entityManager.createQuery("FROM CartaoCredito cc WHERE cc.numero=:p1 AND cc.codSeguranca=:cod");
		query.setParameter("p1", numero);
		query.setParameter("cod", cod);
		try{
			return (CartaoCredito) query.getSingleResult();
		}catch(NoResultException e){
			query.setParameter("p1", numero);
			return null;
		}
	}
	/**
	 * Atualiza os dados do cartao passando um novo cartao
	 * @param cartao
	 */
	public void AtualizarCartao(CartaoCredito cartao){
		entityManager.merge(cartao);
	}
	
	public List<Transacao> ConsultarTransacoes(CartaoCredito cartao){
		Query query = entityManager.createQuery("FROM Transacao t WHERE t.cartaoCredito=:p1");
		query.setParameter("p1", cartao);
		try{
			return query.getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
	
}
