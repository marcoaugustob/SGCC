package sgcc.web;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import sgcc.entidades.CartaoCredito;
import sgcc.entidades.Transacao;
import sgcc.servico.ServicoCartaoCredito;
import sgcc.servico.ServicoTransacao;

@Named
@RequestScoped
public class TransacaoBean {

	@EJB
	private ServicoTransacao servicoTransacao;
	@EJB
	private ServicoCartaoCredito servicoCC;
	
	private Transacao transacao;
	private String numero;
	private String cod;

	
	public TransacaoBean(){
		transacao = new Transacao();
	}
	
	
	
	
	public Transacao EfetuarTransacao(){
		CartaoCredito c = servicoCC.ConsultaCartao(numero,cod);
		
		//Verifica se o cartão existe
		
		if(c==null){
			Messages.create("Cartão não encontrado!").warn().add();
			return null;
		}
			
		if(this.transacao.getTipo().equalsIgnoreCase("Débito")){
			Double diferenca = c.getValorCredito() - this.transacao.getValor();
			
			//Verifica se existem fundos do cartão
			
			if(diferenca < 0){
				Messages.create("Fundos insuficientes!").warn().add();
				return null;
			}
			
			c.setValorCredito(diferenca);
			this.transacao.setCartaoCredito(c);
			servicoCC.AtualizarCartao(c);
			servicoTransacao.CadastrarTransacao(transacao);
			
			Messages.create("Transação bem sucedida!").add();
		}
		
		if(this.transacao.getTipo().equalsIgnoreCase("Crédito")){
			
			Double montante = c.getValorCredito() + this.transacao.getValor();
			
			c.setValorCredito(montante);
			this.transacao.setCartaoCredito(c);
			servicoCC.AtualizarCartao(c);
			servicoTransacao.CadastrarTransacao(transacao);
			Messages.create("Transação bem sucedida!").add();
			
		}
		
		return transacao;
	}
	

	public Transacao getTransacao() {
		return transacao;
	}

	public void setTransacao(Transacao transacao) {
		this.transacao = transacao;
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
