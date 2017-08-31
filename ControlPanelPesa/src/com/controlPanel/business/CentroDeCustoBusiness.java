package com.controlPanel.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.controlPanel.bean.CentroDeCustoBean;
import com.controlPanel.entity.PmpCentroDeCusto;
import com.pmp.util.JpaUtil;

public class CentroDeCustoBusiness {

	public List<CentroDeCustoBean> findAllCentroDeCusto(){
		List<CentroDeCustoBean> cc = new ArrayList<CentroDeCustoBean>();

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From PmpCentroDeCusto");
			List<PmpCentroDeCusto> result = query.getResultList();
			for (PmpCentroDeCusto centroDeCusto : result) {
				CentroDeCustoBean bean = new CentroDeCustoBean();
				bean.formBean(centroDeCusto);
				cc.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null){
				manager.close();
			}
		}
		return cc;
	}
	
}
