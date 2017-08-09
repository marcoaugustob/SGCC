package sgcc.web;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import sgcc.entidades.CartaoCredito;
import sgcc.entidades.Transacao;
import sgcc.servico.ServicoCartaoCredito;

@Named
@RequestScoped
public class CartaoCreditoBean {

	@EJB
	private ServicoCartaoCredito servicoCC;
	
	private CartaoCredito cartao;
	private String numero;
	private String cod;
	private List<Transacao> lista;
	
	public CartaoCreditoBean(){
		cartao = new CartaoCredito();
	}
	
	/**
	 * Gera um cartão de crédito
	 * @return
	 */
	public CartaoCredito GerarCartaoCredito(){
		
		CartaoCredito cartao;
		do{
		cartao = new CartaoCredito();	
		cartao.setCliente(this.cartao.getCliente());
		cartao.setValorCredito(this.cartao.getValorCredito());
		}while(servicoCC.ConsultaCartao(cartao) != null);
		servicoCC.CadastrarCartao(cartao);
		Messages.create("O número do cartão é: " + cartao.getNumero()).add();
		return cartao;
	}
	
	public void ListarTransacoes(){
		CartaoCredito cartao = servicoCC.ConsultaCartao(this.numero,this.cod);
		if(cartao != null)
			this.lista = servicoCC.ConsultarTransacoes(cartao);
		else
			Messages.create("Cartão não encontrado!").warn().add();
	}

	public CartaoCredito getCartao() {
		return cartao;
	}

	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}

	public List<Transacao> getLista() {
		return lista;
	}

	public void setLista(List<Transacao> lista) {
		this.lista = lista;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}
}
