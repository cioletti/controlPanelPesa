package com.controlPanel.controller;

import java.util.Collection;
import java.util.List;

import com.controlPanel.bean.CargoBean;
import com.controlPanel.bean.CentroDeCustoBean;
import com.controlPanel.bean.FilialBean;
import com.controlPanel.bean.JobControlBean;
import com.controlPanel.bean.PerfilBean;
import com.controlPanel.bean.SistemaBean;
import com.controlPanel.bean.UsuarioBean;
import com.controlPanel.business.CentroDeCustoBusiness;
import com.controlPanel.business.UsuarioBusiness;
import com.controlPanel.bean.FornecedorServicoTerceirosBean;
import com.controlPanel.business.TipoServicoBusiness;
import com.pmp.util.ExceptionLogin;

import flex.messaging.FlexContext;

public class ServiceController {

	private UsuarioBean usuarioBean;
	public ServiceController() {
		usuarioBean = (UsuarioBean)FlexContext.getFlexSession().getAttribute("usuario");
	}
	public String getUrlLogintServer() {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
				.concat("://");
		return url.concat(FlexContext.getHttpRequest().getServerName()).concat(
				":").concat(
				String.valueOf(FlexContext.getHttpRequest().getServerPort()))
				.concat(FlexContext.getHttpRequest().getContextPath());
	}
	public String alterPassword(String senhaAntiga, String senhaAtual) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.alterPassword(usuarioBean.getLogin(), senhaAntiga, senhaAtual);
	}
	public UsuarioBean loginUsuario(String login, String senha){
		UsuarioBusiness business = new UsuarioBusiness();
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
		                                       				.concat("://")
		.concat(FlexContext.getHttpRequest().getServerName()).concat(
		":").concat(
		String.valueOf(FlexContext.getHttpRequest().getServerPort()));
		
		UsuarioBean bean = business.loginUsuario(login, senha, url);
		if(bean != null){
			FlexContext.getFlexSession().setAttribute("usuario", bean);
		}
		//new EmailHelper().sendSimpleMail("teste", "Ação teste", "cioletti.rodrigo@gmail.com");
		return bean;
	}
	
	public List<SistemaBean> findAllSistemas(String idFuncionario) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllSistemas(idFuncionario);
	}
	
	public List<PerfilBean> findAllPerfil(String idFuncionario, String tipoSistema) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllPerfil(idFuncionario, tipoSistema);
	}
	
	public UsuarioBean saveUser(UsuarioBean bean) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.saveUser(bean);
	}
	
	public List<UsuarioBean> findAllUsersByName(String nome) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllUsersByName(nome);
	}
	public Boolean removerUsuario(String matricula) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.removerUsuario(matricula);
	}
	public Boolean removerPerfilSistema(String matricula, Integer idSistema, Integer idPerfil) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.removerPerfilSistema(matricula, idSistema, idPerfil);
	}
	private void validarUsuario() throws Exception{
		try {
			if(usuarioBean == null){
				throw new Exception();
			}
		} catch (Exception e) {
			ExceptionLogin exception = new ExceptionLogin("false");	
			throw exception;
		}
	}
	public List<FilialBean> findAllFilial() throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllFilial();
	}
	public void invalidarSessao(){
		FlexContext.getFlexSession().invalidate();
	}
	public  UsuarioBean verificarLogin() throws Exception{
		return usuarioBean;
	}
	public List<UsuarioBean> findAllUsuariosDesassociados(String idUsuarioSupervisor, Long idFilial) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllUsuariosDesassociados(idUsuarioSupervisor, idFilial, this.usuarioBean.getMatricula());
	}
	public List<UsuarioBean> findAllUsuariosAssociados(String idUsuarioSupervisor) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllUsuariosAssociados(idUsuarioSupervisor);
	}
	public Boolean desassociarFuncionario(String idUsuario) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.desassociarFuncionario(idUsuario);
	}
	public Boolean associarFuncionario(String idUsuarioSupervisor, String idUsuario) throws Exception{
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.associarFuncionario(idUsuarioSupervisor, idUsuario);
	}
	
	public List<CentroDeCustoBean> findAllCentroDeCusto() throws Exception{
		validarUsuario();
		CentroDeCustoBusiness business = new CentroDeCustoBusiness();
		return business.findAllCentroDeCusto();
	}
	
	public Collection<CargoBean> findAllCargos() throws Exception {
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllCargos();
	}
	
	public Collection<JobControlBean> findAllJobControl() throws Exception {
		validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllJobControl();
	}
	
	public List<CargoBean> findAllCargosByCampoPesquisa(String campoPesquisa) throws Exception {
		this.validarUsuario();
		UsuarioBusiness business = new UsuarioBusiness();
		return business.findAllCargosByCampoPesquisa(campoPesquisa);
	}
	
	public List<FornecedorServicoTerceirosBean> findAllFornecedorServTerceiro() throws Exception{
		validarUsuario();
		TipoServicoBusiness business = new TipoServicoBusiness(); 
		return business.findAllFornecedorServTerceiro();
	}
	
//	public void importarPl(){
//		ReadMaquinaPlJob job = new ReadMaquinaPlJob();
//		job.execute();
//	}
}
