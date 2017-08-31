package com.controlPanel.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;



public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -6498621729773118526L;

	private Integer id;
	private List<SistemaBean> sistemaList;
	private String nome;
    private String login;
    private String senha;
    private String matricula;
    private String msg;
    private Integer filial;
    private String filialStr;
    private String email;
    private String telefone;
    private Long idSistema;
    private Long idPerfil;
    private String cargo;
    private String isInterno;
    private String urlExterno;
    private Long idCentroCusto;
    private String cpf;
    private String jobControl;
    private Long idCargo;
    private String estimateBy;
    private Long turno;
    private String placaVeiculo;
    private Long idFornecedor;
    private String tipoVeiculo;
    private String codigoVeiculo;
    
    public Long getIdCentroCusto() {
		return idCentroCusto;
	}
	public void setIdCentroCusto(Long idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}
	public Long getIdSistema() {
		return idSistema;
	}
	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
    public Integer getFilial() {
		return filial;
	}
	public void setFilial(Integer filial) {
		this.filial = filial;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public List<SistemaBean> getSistemaList() {
		return sistemaList;
	}
	public void setSistemaList(List<SistemaBean> sistemaList) {
		this.sistemaList = sistemaList;
	}
	public String getFilialStr() {
		return filialStr;
	}
	public void setFilialStr(String filialStr) {
		this.filialStr = filialStr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getIsInterno() {
		return isInterno;
	}
	public void setIsInterno(String isInterno) {
		this.isInterno = isInterno;
	}
	public String getUrlExterno() {
		return urlExterno;
	}
	public void setUrlExterno(String urlExterno) {
		this.urlExterno = urlExterno;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getJobControl() {
		return jobControl;
	}
	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}
	public Long getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}
	public String getEstimateBy() {
		return estimateBy;
	}
	public void setEstimateBy(String estimateBy) {
		this.estimateBy = estimateBy;
	}
	public Long getTurno() {
		return turno;
	}
	public void setTurno(Long turno) {
		this.turno = turno;
	}
	public String getPlacaVeiculo() {
		return placaVeiculo;
	}
	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}
	public Long getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public String getTipoVeiculo() {
		return tipoVeiculo;
	}
	public void setTipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	public String getCodigoVeiculo() {
		return codigoVeiculo;
	}
	public void setCodigoVeiculo(String codigoVeiculo) {
		this.codigoVeiculo = codigoVeiculo;
	}


	
}
