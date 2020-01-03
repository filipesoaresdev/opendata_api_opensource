package br.ufpi.dadosabertosapi.database.local.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufpi.dadosabertosapi.utils.ConstantesCKAN;

@Entity
public class DataSetInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition="TEXT")
	private String query;
	
	//nome sem espaços- nome do arquivo
	@Column(unique=true)
	private String nome; 
	
	@Column(name="nome_package", unique=true)
	private String nomePackage;
	
	
	//nome descritivo, pode possuir espaço
	@Column(unique=true)
	private String descricao;
	
	/* em br.ufpi.dadosabertosapi.utils.ConstantesCKAN
	 * 
	 * public static final String EXECUTA_POR_ANO = "Por Ano";
	 * public static final String EXECUTA_POR_MES = "Por Mês";
	 * public static final String EXECUTA_POR_SEMESTRE = "Por Semestre";
	 * public static final String EXECUTA_POR_BIMESTRE = "Por Bimestre";
	 */
	private String intervaloAtualizacao;
	
	//Data de quando foi inserido 
	private LocalDate createdDate;
	
	//data de ultima atualização
	private LocalDate lastUpdate;
	
	/* em br.ufpi.dadosabertosapi.utils.ConstantesCKAN
	 * 
	 * public static final String BANCO_SIGAA="SIGAA";
	 * public static final String BANCO_ADMINISTRATIVO="ADMINISTRATIVO";
	 * public static final String BANCO_REDMINE="REDMINE";
	 * public static final String BANCO_SISTEMASCOMUM="SISTEMAS_COMUM";
	 */
	private String tipoDataBase;
	
	
	
	public DataSetInfo() {
		
	}

	public DataSetInfo(String path, String nome, String nome_package,  String descricao,
			String itervaloAtualizacao) {
		super();
		this.nome = nome;
		this.nomePackage = nome_package;
		this.descricao = descricao;
		this.intervaloAtualizacao = itervaloAtualizacao;
	}
	
	//Verifica se já está em tempo de atualizar
	//Ou seja, se a data da ultima atualização + o intervalo é anterior à hoje
	@JsonIgnore
	public boolean isAvailableToUpload() {
		
		LocalDate dateAtualiza = lastUpdate;
		switch (intervaloAtualizacao) {
		case ConstantesCKAN.EXECUTA_POR_ANO:
			dateAtualiza = dateAtualiza.plusYears(1);
			break;
		case ConstantesCKAN.EXECUTA_POR_BIMESTRE:
			dateAtualiza = dateAtualiza.plusMonths(2);
			break;
		case ConstantesCKAN.EXECUTA_POR_MES:
			dateAtualiza = dateAtualiza.plusMonths(1);
			break;
		case ConstantesCKAN.EXECUTA_POR_SEMESTRE:
			dateAtualiza = dateAtualiza.plusMonths(6);
			break;
		default:
			return false;
		}
		if(dateAtualiza.isBefore(LocalDate.now()) || dateAtualiza.isEqual(LocalDate.now())) {
			return true;
		}
		return false;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNomePackage() {
		return nomePackage;
	}

	public void setNomePackage(String nomePackage) {
		this.nomePackage = nomePackage;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getIntervaloAtualizacao() {
		return intervaloAtualizacao;
	}
	public void setIntervaloAtualizacao(String intervaloAtualizacao) {
		this.intervaloAtualizacao = intervaloAtualizacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DataSetInfo other = (DataSetInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getTipoDataBase() {
		return tipoDataBase;
	}

	public void setTipoDataBase(String tipoDataBase) {
		this.tipoDataBase = tipoDataBase;
	}
	
	
	
}
