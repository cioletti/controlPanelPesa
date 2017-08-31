package bean
{
	[RemoteClass(alias="com.controlPanel.bean.CargoBean")]
	public class CargoBean
	{
		private var _id: Number;
		private var _descricao: String;
		
		public function CargoBean(){}
		
		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get descricao(): String{return _descricao};
		public function set descricao(descricao: String): void{this._descricao = descricao}; 
	}
}