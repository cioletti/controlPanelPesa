package bean
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="com.controlPanel.bean.UsuarioBean")]
	public class UsuarioBean
	{
		private var _id:Number;
		private var _sistemaList:ArrayCollection;
		private var _nome:String;
		private var _matricula:String;
		private var _login:String;
		private var _senha:String;
		private var _msg:String;
		private var _filial:Number;
		private var _filialStr:String;
		private var _email:String;
		private var _telefone:String;
		private var _idSistema:Number;
		private var _idPerfil:Number;
		private var _cargo:String;
		private var _isInterno:String;
		private var _urlExterno:String;
		private var _idCentroCusto:Number;
		private var _cpf:String;
		private var _jobControl:String;
		private var _idCargo:Number;
		private var _estimateBy:String;
		private var _turno:Number;
		private var _placaVeiculo:String;
		private var _idFornecedor:Number;
		private var _tipoVeiculo:String;
		private var _codigoVeiculo:String;
		
		public function UsuarioBean()
		{
			
		}
		
		public function get turno():Number
		{
			return _turno;
		}

		public function set turno(value:Number):void
		{
			_turno = value;
		}

		public function get estimateBy():String
		{
			return _estimateBy;
		}

		public function set estimateBy(value:String):void
		{
			_estimateBy = value;
		}

		public function get cpf():String
		{
			return _cpf;
		}

		public function set cpf(value:String):void
		{
			_cpf = value;
		}

		public function get idCentroCusto():Number
		{
			return _idCentroCusto;
		}

		public function set idCentroCusto(value:Number):void
		{
			_idCentroCusto = value;
		}

		public function get id():Number{return _id};
		public function set id(id:Number):void{this._id = id}; 

		public function get idSistema():Number{return _idSistema};
		public function set idSistema(idSistema:Number):void{this._idSistema = idSistema}; 

		public function get idPerfil():Number{return _idPerfil};
		public function set idPerfil(idPerfil:Number):void{this._idPerfil = idPerfil}; 

		public function get filial():Number{return _filial};
		public function set filial(filial:Number):void{this._filial = filial}; 

		public function get email():String{return _email};
		public function set email(email:String):void{this._email = email}; 

		public function get telefone():String{return _telefone};
		public function set telefone(telefone:String):void{this._telefone = telefone}; 
		
		public function get sistemaList():ArrayCollection{return _sistemaList};
	    public function set sistemaList(sistemaList:ArrayCollection):void{this._sistemaList = sistemaList}; 
		
		public function get nome():String{return _nome};
	    public function set nome(nome:String):void{this._nome = nome}; 
		
		public function get matricula():String{return _matricula};
	    public function set matricula(matricula:String):void{this._matricula = matricula}; 
		
		public function get login():String{return _login};
	    public function set login(login:String):void{this._login = login}; 

		public function get senha():String{return _senha};
	    public function set senha(senha:String):void{this._senha = senha}; 

		public function get msg():String{return _msg};
	    public function set msg(msg:String):void{this._msg = msg}; 

		public function get filialStr():String{return _filialStr};
	    public function set filialStr(filialStr:String):void{this._filialStr = filialStr}; 

		public function get cargo():String{return _cargo};
	    public function set cargo(cargo:String):void{this._cargo = cargo}; 

		public function get isInterno():String{return _isInterno};
	    public function set isInterno(isInterno:String):void{this._isInterno = isInterno}; 

		public function get urlExterno():String{return _urlExterno};
	    public function set urlExterno(urlExterno:String):void{this._urlExterno = urlExterno};
		public function get jobControl():String
		{
			return _jobControl;
		}

		public function set jobControl(value:String):void
		{
			_jobControl = value;
		}

		public function get idCargo():Number
		{
			return _idCargo;
		}

		public function set idCargo(value:Number):void
		{
			_idCargo = value;
		} 

		public function get placaVeiculo():String
		{
			return _placaVeiculo;
		}

		public function set placaVeiculo(value:String):void
		{
			_placaVeiculo = value;
		}

		public function get idFornecedor():Number
		{
			return _idFornecedor;
		}

		public function set idFornecedor(value:Number):void
		{
			_idFornecedor = value;
		}

		public function get tipoVeiculo():String
		{
			return _tipoVeiculo;
		}

		public function set tipoVeiculo(value:String):void
		{
			_tipoVeiculo = value;
		}

		public function get codigoVeiculo():String
		{
			return _codigoVeiculo;
		}

		public function set codigoVeiculo(value:String):void
		{
			_codigoVeiculo = value;
		}


	}
}