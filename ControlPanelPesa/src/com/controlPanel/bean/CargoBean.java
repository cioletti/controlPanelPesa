package com.controlPanel.bean;

import java.io.Serializable;

import com.controlPanel.entity.TrCargo;

public class CargoBean implements Serializable {

	private static final long serialVersionUID = -3160560731905577823L;
	private Long id; 
	private String descricao;

	public CargoBean() { 
	}
 
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
		this.descricao = descricao.toUpperCase();
	}

	public void fromBean(TrCargo entity) {
		setId(entity.getId());
		setDescricao(entity.getDescricao());
	}

	public TrCargo toBean() {
		TrCargo crg = new TrCargo();
		crg.setDescricao(getDescricao());
		return crg;
	}
}
