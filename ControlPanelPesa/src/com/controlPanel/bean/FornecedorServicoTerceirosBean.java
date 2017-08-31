package com.controlPanel.bean;

import com.controlPanel.entity.GeFornecedorServTerceiros;

public class FornecedorServicoTerceirosBean {
	private Long id;
	private String descricao;
	private String endereco;
	private String telefone;
	private String email;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void fromBean (GeFornecedorServTerceiros fornecedor){
		
		setId(fornecedor.getId());
		setDescricao(fornecedor.getDescricao());
		setEndereco(fornecedor.getEndereco());
		setTelefone(fornecedor.getTelefone());
		setEmail(fornecedor.getEmail());
	}
	
	public void toBeanPrefixo (GeFornecedorServTerceiros fornecedor){
		fornecedor.setId(getId());
		fornecedor.setDescricao(getDescricao());	
		fornecedor.setEndereco(getEndereco());
		fornecedor.setEmail(getEmail());
		fornecedor.setTelefone(getTelefone());
	}

}
