package sgcc.entidades;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_CARTAO_CREDITO")
public class CartaoCredito {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String numero;
	@Column(name="CODIGO_SEGURANCA")
	private String codSeguranca;
	@Column(name="DATA_CRIACAO")
	@Temporal(TemporalType.DATE)
	private Date dataCriacao;
	@Column(name="DATA_VENCIMENTO")
	private Date dataVencimento;
	@NotNull
	@Column(name="VALOR_CREDITO")
	private Double valorCredito;
	@ManyToOne
	private Cliente cliente;
	@OneToMany(mappedBy="cartaoCredito", targetEntity=Transacao.class)
	private List<Transacao> transacoes;
	
	public CartaoCredito(){
		
		dataCriacao = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dataCriacao);
		c.add(Calendar.YEAR, 5);
		dataVencimento = c.getTime();
		GerarNumero();
	}
	
	private void GerarNumero(){
		String grupoNumeros = "";
		for(int i=0;i<3;i++){
			int grupo = new Random().nextInt(10000);
				
			if(grupo >= 0 && grupo < 10)
				grupoNumeros += "000" + grupo;
			if(grupo >= 10 && grupo < 100)
				grupoNumeros += "00" + grupo;
			if(grupo >= 100 && grupo < 1000)
				grupoNumeros += "0" + grupo;
			if(grupo >= 1000)
				grupoNumeros += grupo;
			grupoNumeros += ".";
		}
		
		int grupo = new Random().nextInt(10000);
		
		if(grupo >= 0 && grupo < 10)
			grupoNumeros += "000" + grupo;
		if(grupo >= 10 && grupo < 100)
			grupoNumeros += "00" + grupo;
		if(grupo >= 100 && grupo < 1000)
			grupoNumeros += "0" + grupo;
		if(grupo >= 1000)
			grupoNumeros += grupo;
		this.numero = grupoNumeros;
		Integer cod = new Random().nextInt(1000);
		if(cod<100){
			if(cod<10){
				this.codSeguranca = "00" + cod;
			}else
				this.codSeguranca = "0" + cod;
		}else
			this.codSeguranca = cod.toString();
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCodSeguranca() {
		return codSeguranca;
	}
	public void setCodSeguranca(String codSeguranca) {
		this.codSeguranca = codSeguranca;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Double getValorCredito() {
		return valorCredito;
	}
	public void setValorCredito(Double valorCredito) {
		this.valorCredito = valorCredito;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaoCredito other = (CartaoCredito) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}
	
}
