<?php
	class ConexaoBD{
		//deu certo
		//http://php.net/manual/pt_BR/mysqli.sqlstate.php
		//USO DO $this e self:
		//		self:: se usa qdo temo statis, $this nao :P
		
	    private static $dbtype;
		private static $host;
		private static $port;
		private static $socket;
		private static $user;
		private static $password;
		private static $dbname;
		
		private static $mysqli;
		
	    /*Método construtor do banco de dados*/
		/*PHP Nao aceita sobrecarga de construtores*/
		public function __construct(){
			self::$dbtype  	= "mysql";
			self::$host     	= "localhost";
			self::$port     	= "3306";
			self::$socket		= "4000";
			self::$user     	= "admin";
			self::$password	    = "112233-Mysql";
			self::$dbname		= "secjov";
		}
		
		/*Evita que a classe seja clonada*/
		public function __clone(){}


        public function setArquivo($dbtype,$host,$port,$socket,$user,$password,$dbname){
            self::$dbtype  		= $dbtype ;
            self::$host     	= $host;
            self::$port     	= $port;
            self::$socket		= $socket;
            self::$user     	= $user;
            self::$password		= $password;
            self::$dbname		= $dbname;
        }

		public function abrirMysqli(){
	
			// Conecta ao banco de dados

			self::$mysqli = new mysqli(self::$host,self::$user,self::$password,self::$dbname);
			$erro = mysqli_connect_errno();
			if ($erro) {
                echo"<br>Concexão: Banco desconectado";
			    die('Conexao: Não foi possível conectar-se ao banco de dados: ' . mysqli_connect_error());
				exit();
			}else{
                echo"<br>Concexão: Banco conectado";
            }

			return $erro;// 1=erro  0=semErro
		}

		public function fecharMySqli(){
			self::$mysqli->close();
            echo"<br>Concexão: Banco Encerrado ;)";
		}

		/*Metodos que trazem o conteudo da variavel desejada
		@return   $xxx = conteudo da variavel solicitada*/
		public function getDBType()  {
			return self::$dbtype;
		}
		public function getHost()    {
			return self::$host;
		}
		public function getPort()    {
			return self::$port;
		}
		public function getSckt()    {
			return self::$socket;
		}
		public function getUser()    {
			return self::$user;
		}
		public function getPassword(){
			return self::$password;
		}
		public function getDBname(){
			return self::$dbname;
		}
		public function getMysqli(){
			return self::$mysqli;
		}
	}
?>

