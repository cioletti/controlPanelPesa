package com.controlPanel.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controlPanel.bean.UsuarioBean;
import com.controlPanel.business.UsuarioBusiness;

import flex.messaging.FlexContext;

/**
 * Servlet implementation class LoginUsuario
 */
public class LoginUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String matricula = request.getParameter("matricula");
		String senha = request.getParameter("senha");
		UsuarioBusiness business = new UsuarioBusiness();
		UsuarioBean bean = business.loginUsuario(matricula, senha, request.getProtocol().split("/")[0].concat("://").concat(request.getServerName()).concat(
		                                                                                                				":").concat(
		                                                                                                				String.valueOf(request.getServerPort())));
		if(bean != null){
			request.getSession().setAttribute("usuario", bean);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ControlPanelPesa.html");
			dispatcher.forward(request,response);
		}else{
			response.sendRedirect("/ControlPanelPesa/ControlPanelPesa.html");
		}
	}

}
