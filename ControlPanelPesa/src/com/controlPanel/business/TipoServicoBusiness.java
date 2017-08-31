package com.controlPanel.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.controlPanel.bean.FornecedorServicoTerceirosBean;
import com.controlPanel.entity.GeFornTipoServTerceiros;
import com.controlPanel.entity.GeFornecedorServTerceiros;
import com.controlPanel.entity.GeFornecedorServTerceiros;
import com.controlPanel.entity.GeSegmentoServTerceiros;
import com.controlPanel.entity.GeSegmentoServTerceirosPK;
import com.controlPanel.entity.GeStatusServTerceiros;
import com.pmp.util.JpaUtil;

public class TipoServicoBusiness {
	
	
	
	public List<FornecedorServicoTerceirosBean> findAllFornecedorServTerceiro(){
		List<FornecedorServicoTerceirosBean> fornecedores = new ArrayList<FornecedorServicoTerceirosBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 

			Query query = manager.createQuery("From GeFornecedorServTerceiros order by descricao");
			List<GeFornecedorServTerceiros> result = query.getResultList();
			for (GeFornecedorServTerceiros fornecedorObj : result) { 
				FornecedorServicoTerceirosBean bean = new FornecedorServicoTerceirosBean();
				bean.fromBean(fornecedorObj);
				fornecedores.add(bean);
			}
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return fornecedores;
	}
	
	/**
	 * Recupera todos os fornecedores associados a um tipo de serviço de terceiros
	 * @param idTipoServTerceiros
	 * @return
	 */
}
