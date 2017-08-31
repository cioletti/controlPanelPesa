package com.controlPanel.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.controlPanel.bean.CargoBean;
import com.controlPanel.bean.FilialBean;
import com.controlPanel.bean.JobControlBean;
import com.controlPanel.bean.PerfilBean;
import com.controlPanel.bean.SistemaBean;
import com.controlPanel.bean.UsuarioBean;
import com.controlPanel.entity.AdmPerfil;
import com.controlPanel.entity.AdmPerfilSistemaUsuario;
import com.controlPanel.entity.AdmSistema;
import com.controlPanel.entity.PmpCentroDeCusto;
import com.controlPanel.entity.PmpDetalhesVeiculos;
import com.controlPanel.entity.TrCargo;
import com.controlPanel.entity.TwFilial;
import com.controlPanel.entity.TwFuncionario;
import com.pmp.util.JpaUtil;

public class UsuarioBusiness {
	private static final String EJBQL_ALTER_PASSWORD = "From TwFuncionario where login =:login and senha =:senha";
	public UsuarioBean loginUsuario(String login, String senha, String url){
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select * from TW_FUNCIONARIO where LOGIN COLLATE SQL_Latin1_General_CP1_CS_AS =:login and SENHA COLLATE SQL_Latin1_General_CP1_CS_AS =:senha");
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			query.getSingleResult();

			query = manager.createQuery("From TwFuncionario u where u.login =:login and u.senha=:senha");
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			TwFuncionario usuario = (TwFuncionario)query.getSingleResult();

			UsuarioBean usuarioBean = new UsuarioBean();
			usuarioBean.setEmail(usuario.getEmail());
			usuarioBean.setFilial(Integer.valueOf(usuario.getStn1()));
			usuarioBean.setMatricula(usuario.getEpidno());
			usuarioBean.setTelefone(usuario.getTelefone());
			usuarioBean.setLogin(usuario.getLogin());
			usuarioBean.setSenha(usuario.getSenha());
			usuarioBean.setCargo(usuario.getCargo());
			usuarioBean.setNome(usuario.getEplsnm());
			usuarioBean.setIsInterno("N");
			usuarioBean.setCpf(usuario.getCpf());
			usuarioBean.setTurno(usuario.getTurno());
			usuarioBean.setIdFornecedor(usuario.getIdFornecedor());
			query = manager.createQuery("From AdmPerfilSistemaUsuario where idTwUsuario.epidno =:epidno");
			query.setParameter("epidno", usuarioBean.getMatricula());
			List<AdmPerfilSistemaUsuario> admPerfilSistemaUsuarioList = query.getResultList();
			List<SistemaBean> sistemaList = new ArrayList<SistemaBean>();
			for (AdmPerfilSistemaUsuario admPerfilSistemaUsuario : admPerfilSistemaUsuarioList) {
				SistemaBean bean = new SistemaBean();
				bean.setDescricao(admPerfilSistemaUsuario.getIdSistema().getDescricao());
				bean.setId(admPerfilSistemaUsuario.getIdSistema().getId().intValue());
				bean.setSigla(admPerfilSistemaUsuario.getIdSistema().getSigla());
				bean.setDescricaoPerfil(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
				bean.setContext(admPerfilSistemaUsuario.getIdSistema().getContext());
				bean.setImg(admPerfilSistemaUsuario.getIdSistema().getImg());
				bean.setUrl(url+"/"+admPerfilSistemaUsuario.getIdSistema().getContext());
				bean.setJobControl(admPerfilSistemaUsuario.getJobControl());
				PerfilBean perfilBean = new PerfilBean();
				perfilBean.setDescricao(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
				perfilBean.setId(admPerfilSistemaUsuario.getIdPerfil().getId().intValue());
				perfilBean.setSigla(admPerfilSistemaUsuario.getIdPerfil().getSigla());
				bean.setPerfilBean(perfilBean);
				sistemaList.add(bean);				
			}
			usuarioBean.setSistemaList(sistemaList);


			return usuarioBean;
		}catch (NoResultException e) {

			try {
				Query query = manager.createNativeQuery("select t.id, t.descricao, t.telefone, t.email, t.login, t.senha, t.descricao from tr_nao_funcionario t where t.login =:login and t.senha =:senha");
				query.setParameter("login", login);
				query.setParameter("senha", senha);
				Object[] pair = (Object[])query.getSingleResult();
				UsuarioBean usuarioBean = new UsuarioBean();
				usuarioBean.setEmail((String)pair[3]);
				usuarioBean.setMatricula(((BigDecimal)pair[0]).toString());
				usuarioBean.setTelefone((String)pair[2]);
				usuarioBean.setLogin((String)pair[4]);
				usuarioBean.setSenha((String)pair[5]);
				usuarioBean.setNome((String)pair[6]);
				usuarioBean.setIsInterno("S");
				usuarioBean.setUrlExterno(url+"/treinamentoPesa/LoginUsuario");
				return usuarioBean;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	/*
	 * Recupera os sistemas que o usuário não possui
	 */
	public List<SistemaBean> findAllSistemas(String idFuncionario){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = null;;
			if(idFuncionario != null){
				query = manager.createQuery("from  AdmSistema  where id not in(select idSistema.id from  AdmPerfilSistemaUsuario  where idTwUsuario.epidno=:epidno) order by descricao");
				query.setParameter("epidno", idFuncionario);
			}else{
				query = manager.createQuery("from  AdmSistema order by descricao");
			}
			List<AdmSistema> list = (List<AdmSistema>) query.getResultList();
			List<SistemaBean> result = new ArrayList<SistemaBean>();
			for (AdmSistema sistema : list) {
				SistemaBean bean = new SistemaBean();				
				bean.setId(sistema.getId().intValue());
				bean.setDescricao(sistema.getDescricao());
				bean.setSigla(sistema.getSigla());

				result.add(bean);				
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}

	public List<PerfilBean> findAllPerfil(String idFuncionario, String tipoSistema){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			if(idFuncionario != null){
				query= manager.createQuery("from  AdmPerfil  where tipoSistema =:tipoSistema and id not in(select idPerfil.id from  AdmPerfilSistemaUsuario  where idTwUsuario.epidno=:epidno) " +
						" order by descricao");
				query.setParameter("tipoSistema", tipoSistema);
				query.setParameter("epidno", idFuncionario);
			}else{
				query= manager.createQuery("from  AdmPerfil  where tipoSistema =:tipoSistema order by descricao");
				query.setParameter("tipoSistema", tipoSistema);
			}
			List<AdmPerfil> list = (List<AdmPerfil>)query.getResultList();
			List<PerfilBean> result = new ArrayList<PerfilBean>();
			for (AdmPerfil perfil : list) {
				PerfilBean bean = new PerfilBean();				
				bean.setId(perfil.getId().intValue());
				bean.setDescricao(perfil.getDescricao());
				bean.setSigla(perfil.getSigla());
				result.add(bean);				
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}


	public UsuarioBean saveUser(UsuarioBean bean){
		EntityManager manager = null;
		try {
			String placaVeiculo = null;
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			TwFuncionario usuario = manager.find(TwFuncionario.class, bean.getMatricula().toUpperCase());
			TrCargo cargo = manager.find(TrCargo.class, bean.getIdCargo());
			if(usuario != null && (usuario.getEplsnm().trim().equals(bean.getNome().toUpperCase())
					|| usuario.getEpidno().equalsIgnoreCase(bean.getMatricula()))){
				usuario.setEplsnm(bean.getNome().toUpperCase());
				usuario.setEmail(bean.getEmail());
				usuario.setStn1(bean.getFilial().toString());
				usuario.setEpidno(bean.getMatricula().toUpperCase());
				usuario.setTelefone(bean.getTelefone());
				usuario.setLogin(bean.getLogin());
				//usuario.setCargo(bean.getCargo().toUpperCase());
				usuario.setIdTrCargo(cargo);
				usuario.setSenha(bean.getSenha());
				usuario.setCpf(bean.getCpf());
				usuario.setEstimateBy(bean.getEstimateBy().toUpperCase());
				usuario.setTurno(bean.getTurno());				
				if(bean.getPlacaVeiculo() != null && !bean.getPlacaVeiculo().equals("")){
					placaVeiculo = (bean.getPlacaVeiculo().replace("-", "")).replace(" ", "");
				}
				usuario.setPlacaVeiculo(placaVeiculo);
				if(bean.getIdFornecedor() != null && bean.getIdFornecedor() != 0){
					usuario.setIdFornecedor(bean.getIdFornecedor());
				}
				usuario.setCodigoVeiculo(bean.getCodigoVeiculo());
				usuario.setTipoVeiculo(bean.getTipoVeiculo());
				manager.merge(usuario);
				Query query = manager.createNativeQuery("select * from PMP_DETALHES_VEICULOS where ID_TW_FUNCIONARIO = '"+usuario.getEpidno()+"'");
				if(query.getResultList().size() > 0){
					query = manager.createNativeQuery("update PMP_DETALHES_VEICULOS set FROTA = '"+bean.getCodigoVeiculo()+"', PLACA = '"+(bean.getPlacaVeiculo().replace("-", "")).replace(" ", "") +"' where ID_TW_FUNCIONARIO ="+bean.getMatricula().toUpperCase());
					query.executeUpdate();
				}

			}else{
				Query query = manager.createQuery("from TwFuncionario where login =:login");
				query.setParameter("login", bean.getLogin());
				if(query.getResultList().size() > 0){
					return null;
				}
				usuario = new TwFuncionario();
				usuario.setEplsnm(bean.getNome().toUpperCase());
				usuario.setEmail(bean.getEmail());
				usuario.setStn1(bean.getFilial().toString());
				usuario.setEpidno(bean.getMatricula().toUpperCase());
				usuario.setTelefone(bean.getTelefone());
				//usuario.setCargo(bean.getCargo().toUpperCase());
				usuario.setIdTrCargo(cargo);
				usuario.setLogin(bean.getLogin());
				usuario.setSenha(bean.getSenha());
				usuario.setCpf(bean.getCpf());
				usuario.setEstimateBy(bean.getEstimateBy());

				if(bean.getPlacaVeiculo() != null && !bean.getPlacaVeiculo().equals("")){
					placaVeiculo = (bean.getPlacaVeiculo().replace("-", "")).replace(" ", "");
				}
				usuario.setPlacaVeiculo(placaVeiculo);
				usuario.setTurno(bean.getTurno());
				usuario.setIdFornecedor(bean.getIdFornecedor());
				usuario.setCodigoVeiculo(bean.getCodigoVeiculo());
				usuario.setTipoVeiculo(bean.getTipoVeiculo());
				query = manager.createNativeQuery("select * from PMP_DETALHES_VEICULOS where ID_TW_FUNCIONARIO = '"+usuario.getEpidno()+"'");
				if(query.getResultList().size() > 0){
					query = manager.createNativeQuery("update PMP_DETALHES_VEICULOS set FROTA = '"+bean.getCodigoVeiculo()+"', PLACA = '"+(bean.getPlacaVeiculo().replace("-", "")).replace(" ", "") +"' where ID_TW_FUNCIONARIO ="+bean.getMatricula().toUpperCase());
					query.executeUpdate();
				}
				manager.persist(usuario);
			}
			AdmPerfilSistemaUsuario sistemaUsuario = new AdmPerfilSistemaUsuario();
			sistemaUsuario.setIdPerfil(manager.find(AdmPerfil.class, bean.getIdPerfil()));
			sistemaUsuario.setIdSistema(manager.find(AdmSistema.class, bean.getIdSistema()));
			sistemaUsuario.setIdTwUsuario(usuario);
			sistemaUsuario.setIdCentroCusto(manager.find(PmpCentroDeCusto.class, bean.getIdCentroCusto()));
			sistemaUsuario.setJobControl(bean.getJobControl());
			manager.persist(sistemaUsuario);
			manager.getTransaction().commit();
			return findAllUsersByMatricula(bean.getMatricula());

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}

	public List<UsuarioBean> findAllUsersByName(String nome){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			//manager.getTransaction().begin();
			Query query = manager.createQuery("From TwFuncionario where eplsnm like'"+nome.toUpperCase()+"%' order by epidno");
			List<TwFuncionario> list = query.getResultList();
			List<UsuarioBean> result = new ArrayList<UsuarioBean>();
			for (TwFuncionario pair : list) {
				
				UsuarioBean usuarioBean = new UsuarioBean();
				TwFuncionario usuario = pair;
				if(usuario.getStn1() != null){
					TwFilial filial = manager.find(TwFilial.class, Long.valueOf(usuario.getStn1()));
					if(filial != null){
						usuarioBean.setFilialStr(filial.getStnm());
						usuarioBean.setFilial(Integer.valueOf(usuario.getStn1()));
					}
				}
				usuarioBean.setNome(usuario.getEplsnm());
				usuarioBean.setEmail(usuario.getEmail());
				usuarioBean.setMatricula(usuario.getEpidno());
				usuarioBean.setTelefone(usuario.getTelefone());
				usuarioBean.setLogin(usuario.getLogin());
				usuarioBean.setSenha(usuario.getSenha());
				if(usuario.getIdTrCargo() != null){
					usuarioBean.setIdCargo(usuario.getIdTrCargo().getId());
					query = manager.createQuery("From TrCargo Where id =:idCargo");
					query.setParameter("idCargo", usuarioBean.getIdCargo());
					TrCargo cargo = (TrCargo) query.getSingleResult();
					
					usuarioBean.setCargo(cargo.getDescricao());
				}
				usuarioBean.setEstimateBy(usuario.getEstimateBy());
				usuarioBean.setTurno(usuario.getTurno());
				usuarioBean.setPlacaVeiculo(usuario.getPlacaVeiculo());

				// Busca o cargo pela tabela TrCargo, pois a coluna descricão do cargo da tabela TwFuncionario não é mais usada. 


				usuarioBean.setCpf(usuario.getCpf());				
				query = manager.createQuery("From AdmPerfilSistemaUsuario u where idTwUsuario.epidno=:epidno");
				query.setParameter("epidno", usuarioBean.getMatricula());
				List<AdmPerfilSistemaUsuario> admPerfilSistemaUsuarioList = query.getResultList();
				List<SistemaBean> sistemaList = new ArrayList<SistemaBean>();
				for (AdmPerfilSistemaUsuario admPerfilSistemaUsuario : admPerfilSistemaUsuarioList) {
					SistemaBean bean = new SistemaBean();
					bean.setDescricao(admPerfilSistemaUsuario.getIdSistema().getDescricao());
					bean.setId(admPerfilSistemaUsuario.getIdSistema().getId().intValue());
					bean.setSigla(admPerfilSistemaUsuario.getIdSistema().getSigla());
					bean.setDescricaoPerfil(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
					bean.setJobControl(admPerfilSistemaUsuario.getJobControl());
					if(usuario.getTipoVeiculo()!= null && (admPerfilSistemaUsuario.getIdSistema().getId().intValue() == 6 || admPerfilSistemaUsuario.getIdSistema().getId().intValue() == 7)){
						
						if(usuario.getTipoVeiculo().equals("T")){
							bean.setTipoVeiculo("Caminhão");
						}else if(usuario.getTipoVeiculo().equals("C")){
							bean.setTipoVeiculo("Carro");
						}else if(usuario.getTipoVeiculo().equals("D")){
							bean.setTipoVeiculo("Doublo");
						}else if(usuario.getTipoVeiculo().equals("U")){
							bean.setTipoVeiculo("Ducato");
						}
						
						if(usuario.getCodigoVeiculo()!= null)
							bean.setCodigoVeiculo(usuario.getCodigoVeiculo());
					}
					PerfilBean perfilBean = new PerfilBean();
					perfilBean.setDescricao(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
					perfilBean.setId(admPerfilSistemaUsuario.getIdPerfil().getId().intValue());
					perfilBean.setSigla(admPerfilSistemaUsuario.getIdPerfil().getSigla());
					//					if(admPerfilSistemaUsuario.getIdCentroCusto() != null){
					//						usuarioBean.setIdCentroCusto(admPerfilSistemaUsuario.getIdCentroCusto().getId());						
					//					}
					//					usuarioBean.setIdSistema(admPerfilSistemaUsuario.getIdSistema().getId());
					//					usuarioBean.setIdPerfil(admPerfilSistemaUsuario.getIdPerfil().getId());
					bean.setPerfilBean(perfilBean);
					sistemaList.add(bean);				
				}
				usuarioBean.setSistemaList(sistemaList);
				result.add(usuarioBean);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public UsuarioBean findAllUsersByMatricula(String matricula){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From TwFuncionario, TwFilial where epidno ='"+matricula.toUpperCase()+"' and stn1 = stno");
			List<Object[]> list = query.getResultList();
			List<UsuarioBean> result = new ArrayList<UsuarioBean>();
			Object [] pair  =  list.get(0);
			TwFuncionario usuario = (TwFuncionario)pair[0];
			TwFilial filial = (TwFilial)pair[1];
			UsuarioBean usuarioBean = new UsuarioBean();
			usuarioBean.setFilialStr(filial.getStnm());
			usuarioBean.setNome(usuario.getEplsnm());
			usuarioBean.setEmail(usuario.getEmail());
			usuarioBean.setFilial(Integer.valueOf(usuario.getStn1()));
			usuarioBean.setMatricula(usuario.getEpidno());
			usuarioBean.setTelefone(usuario.getTelefone());
			usuarioBean.setLogin(usuario.getLogin());
			usuarioBean.setSenha(usuario.getSenha());
			usuarioBean.setIdCargo(usuario.getIdTrCargo().getId());
			usuarioBean.setEstimateBy(usuario.getEstimateBy());
			// Busca o cargo pela tabela TrCargo, pois a coluna descricão do cargo da tabela TwFuncionario não é mais usada. 
			query = manager.createQuery("From TrCargo Where id =:idCargo");
			query.setParameter("idCargo", usuarioBean.getIdCargo());

			TrCargo cargo = (TrCargo) query.getSingleResult();

			usuarioBean.setCargo(cargo.getDescricao());

			//usuarioBean.setCargo(usuario.getCargo());		
			query = manager.createQuery("From AdmPerfilSistemaUsuario u where idTwUsuario.epidno=:epidno");
			query.setParameter("epidno", usuarioBean.getMatricula());
			List<AdmPerfilSistemaUsuario> admPerfilSistemaUsuarioList = query.getResultList();
			List<SistemaBean> sistemaList = new ArrayList<SistemaBean>();
			for (AdmPerfilSistemaUsuario admPerfilSistemaUsuario : admPerfilSistemaUsuarioList) {
				SistemaBean bean = new SistemaBean();
				bean.setDescricao(admPerfilSistemaUsuario.getIdSistema().getDescricao());
				bean.setId(admPerfilSistemaUsuario.getIdSistema().getId().intValue());
				bean.setSigla(admPerfilSistemaUsuario.getIdSistema().getSigla());
				bean.setDescricaoPerfil(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
				bean.setJobControl(admPerfilSistemaUsuario.getJobControl());
				if(usuario.getTipoVeiculo()!= null && (admPerfilSistemaUsuario.getIdSistema().getId().intValue() == 6 || admPerfilSistemaUsuario.getIdSistema().getId().intValue() == 7)){
					bean.setTipoVeiculo((usuario.getTipoVeiculo().equals("T")?"Caminhão":"Carro"));
					if(usuario.getCodigoVeiculo()!= null)
						bean.setCodigoVeiculo(usuario.getCodigoVeiculo());
				}
				PerfilBean perfilBean = new PerfilBean();
				perfilBean.setDescricao(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
				perfilBean.setId(admPerfilSistemaUsuario.getIdPerfil().getId().intValue());
				perfilBean.setSigla(admPerfilSistemaUsuario.getIdPerfil().getSigla());
				bean.setPerfilBean(perfilBean);
				sistemaList.add(bean);				
			}
			usuarioBean.setSistemaList(sistemaList);


			return usuarioBean;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}



	public String alterPassword(String login, String senhaAntiga, String senhaAtual){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();

			Query query = manager.createQuery(EJBQL_ALTER_PASSWORD);
			query.setParameter("login", login);
			query.setParameter("senha", senhaAntiga);

			TwFuncionario usuario = (TwFuncionario)query.getSingleResult();
			usuario.setSenha(senhaAtual);
			manager.getTransaction().commit();
			return "Senha Alterada com sucesso!";
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public Boolean removerUsuario(String matricula){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			TwFuncionario usuario = manager.find(TwFuncionario.class, matricula);
			manager.createNativeQuery("delete from PMP_DETALHES_VEICULOS where ID_TW_FUNCIONARIO = '"+matricula+"'");
			manager.remove(usuario);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean removerPerfilSistema(String matricula, Integer idSistema, Integer idPerfil){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from adm_perfil_sistema_usuario where id_tw_usuario = '"+matricula+"' and id_perfil ="+idPerfil+" and id_sistema ="+idSistema);
			query.executeUpdate();
			if(idSistema == 6){
				query = manager.createNativeQuery("  update tw_funcionario set TIPO_VEICULO = null, CODIGO_VEICULO = null, PLACA_VEICULO= null where EPIDNO = '"+matricula+"'");
				query.executeUpdate();
			}
			query = manager.createNativeQuery("select * from PMP_DETALHES_VEICULOS where ID_TW_FUNCIONARIO = '"+matricula+"'");
			if(query.getResultList().size() > 0){
				query = manager.createNativeQuery("update PMP_DETALHES_VEICULOS set id_tw_funcionario = null where id_tw_funcionario = '"+matricula+"'");
				query.executeUpdate();
			}
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public List<FilialBean> findAllFilial(){
		List<FilialBean> beanList = new ArrayList<FilialBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from TwFilial order by stnm");
			List<TwFilial> filialList = query.getResultList();
			for (TwFilial twFilial : filialList) {
				FilialBean bean = new FilialBean();
				bean.setId(twFilial.getStno().intValue());
				bean.setDescricao(twFilial.getStnm().trim());
				beanList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return beanList;
	}
	public List<UsuarioBean> findAllUsuariosDesassociados(String idUsuarioSupervisor, Long idFilial, String epidnoSessao){
		EntityManager manager = null;
		List<UsuarioBean> result = new ArrayList<UsuarioBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;

//			query= manager.createQuery("from TwFuncionario tw ,AdmPerfilSistemaUsuario a"+
//					" where tw.epidno <> '"+idUsuarioSupervisor+"'" +
//					" and a.idTwUsuario.epidno = tw.epidno "+
//					" and tw.epidnoSupervisor is null"+
//					" and tw.stn1 = "+idFilial +
//					" order by tw.eplsnm");
			
			query= manager.createQuery(" from TwFuncionario tw "+
					" where (tw.epidnoSupervisor <> '"+idUsuarioSupervisor+"' or tw.epidnoSupervisor is null)" +
					" and tw.stn1 = "+idFilial +
					" and tw.epidno <> '"+epidnoSessao +"'"+
					" order by tw.eplsnm");



			List<TwFuncionario> list = query.getResultList();
			for (TwFuncionario func : list) {
				UsuarioBean bean = new UsuarioBean();		
				//TwFuncionario func = (TwFuncionario)tw[0];
				//AdmPerfilSistemaUsuario adm = (AdmPerfilSistemaUsuario)tw[1];
				bean.setNome(func.getEplsnm());
				bean.setMatricula(func.getEpidno());
				if(func.getIdTrCargo() != null){
					bean.setCargo(func.getIdTrCargo().getDescricao());
					bean.setIdCargo(func.getIdTrCargo().getId());
				}
				//bean.setJobControl(adm.getJobControl());
				result.add(bean);				
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public List<UsuarioBean> findAllUsuariosAssociados(String idUsuarioSupervisor){
		EntityManager manager = null;
		List<UsuarioBean> result = new ArrayList<UsuarioBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;

			query= manager.createQuery(" from TwFuncionario tw "+
					" where tw.epidnoSupervisor = '"+idUsuarioSupervisor+"'" +
					" order by tw.eplsnm");

			List<TwFuncionario> list = query.getResultList();
			for (TwFuncionario func : list) {
				UsuarioBean bean = new UsuarioBean();
				//TwFuncionario func = (TwFuncionario)tw[0];
				//AdmPerfilSistemaUsuario adm = (AdmPerfilSistemaUsuario)tw[1];
				bean.setNome(func.getEplsnm());
				bean.setMatricula(func.getEpidno());
				bean.setCargo(func.getIdTrCargo().getDescricao());
				bean.setIdCargo(func.getIdTrCargo().getId());
				//bean.setJobControl(adm.getJobControl());
				result.add(bean);				
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public Boolean desassociarFuncionario(String idUsuario){
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			TwFuncionario funcionario = manager.find(TwFuncionario.class, idUsuario);
			funcionario.setEpidnoSupervisor(null);
			manager.merge(funcionario);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public Boolean associarFuncionario(String idUsuarioSupervisor, String idUsuario){
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			TwFuncionario funcionario = manager.find(TwFuncionario.class, idUsuario);
			funcionario.setEpidnoSupervisor(idUsuarioSupervisor);
			manager.merge(funcionario);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}

	private static String HQL_FIND_ALL_CARGOS = "FROM TrCargo ORDER BY descricao";

	public Collection<CargoBean> findAllCargos() {
		Collection<CargoBean> listForm = new ArrayList<CargoBean>();

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();


			Query query = manager.createQuery(HQL_FIND_ALL_CARGOS);
			List<TrCargo> list = (List<TrCargo>) query.getResultList();
			for (TrCargo trCrg : list) {
				CargoBean cargoBean = new CargoBean();
				cargoBean.fromBean(trCrg);
				listForm.add(cargoBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}

	public List<CargoBean> findAllCargosByCampoPesquisa(String campoPesquisa) {
		List<CargoBean> list = new ArrayList<CargoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From TrCargo c WHERE c.descricao LIKE '"+campoPesquisa.toUpperCase()+"%'");
			List<TrCargo> beans =  (List<TrCargo>)query.getResultList();

			for(TrCargo cargo : beans){
				CargoBean cargoBean = new CargoBean();
				cargoBean.fromBean(cargo);

				list.add(cargoBean);
			}			

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}		

		return list;
	}

	public Collection<JobControlBean> findAllJobControl() {
		Collection<JobControlBean> listForm = new ArrayList<JobControlBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select respar from jobcontrol where respar <>'SC' order by respar");
			List<String> list = (List<String>) query.getResultList();
			for (String jbctr : list) {
				JobControlBean bean = new JobControlBean();
				bean.setDescricao(jbctr);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}


}
