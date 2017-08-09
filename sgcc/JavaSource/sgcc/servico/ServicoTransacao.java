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
public class ServicoTransacao {

	@PersistenceContext(name="sgcc")
	private EntityManager entityManager;

	
	
	public void CadastrarTransacao(Transacao transacao){
	
		entityManager.persist(transacao);
	}
	
	public List<Transacao> ListarTransacoes(CartaoCredito cartao){
		Query query = entityManager.createQuery("FROM Transacao t WHERE t.cartaoCredito=:cartao");
		query.setParameter("cartao", cartao);
		try{
			return query.getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
}
