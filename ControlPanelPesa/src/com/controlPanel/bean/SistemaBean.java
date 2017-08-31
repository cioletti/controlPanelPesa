package com.controlPanel.bean;

import java.io.Serializable;

public class SistemaBean implements Serializable{

	private static final long serialVersionUID = 8561269415628934400L;
	private Integer id;
	private String descricao;
	private String descricaoPerfil;
	private String sigla;
	private String img;
	private String context;
	private String url;
	private PerfilBean perfilBean;
	private String jobControl;
	private String tipoVeiculo;
	private String codigoVeiculo;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public PerfilBean getPerfilBean() {
		return perfilBean;
	}
	public void setPerfilBean(PerfilBean perfilBean) {
		this.perfilBean = perfilBean;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricaoPerfil() {
		return descricaoPerfil;
	}
	public void setDescricaoPerfil(String descricaoPerfil) {
		this.descricaoPerfil = descricaoPerfil;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getJobControl() {
		return jobControl;
	}
	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
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
