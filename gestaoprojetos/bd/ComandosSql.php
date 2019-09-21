<?php
	class ComandosSql extends ConexaoBD {
		private static $resultado;

		public function __construct(){

		}

		/** Metodos**/
		public function selectSql($sql){
			self::$resultado = ConexaoBD::getMysqli()->query($sql) ;
			
			// Verifica se ocorreu algum erro
			if (!self::$resultado) {
				echo '<br>Comando selectSql(): Erro no SELECT: ';
             	die('<br>Comando: Erro no SELECT: ' . mysqli_connect_error());
				exit();
			}else{
				echo '<br>Comando selectSql(): SELECT OK! ';
			}
			return self:: $resultado;
		}

        public function insertSql($sql ){
            self::$resultado = ConexaoBD::getMysqli()->query($sql);

            if (!self::$resultado) {
            	echo '<br>Comando inserSQl(): Erro no INSERT: ';
                die('<br>Comando inserSQl(): Erro no insertSql(): ' . mysqli_connect_error());
                exit();
            }else{
				echo '<br>Comando updarteSql(): INSERT INTO OK! ';
			}
            return self::$resultado;
        }
		public function updateSql($sql){
			self::$resultado = ConexaoBD::getMysqli()->query($sql) ;

			// Verifica se ocorreu algum erro
			if (!self::$resultado) {
				echo '<br>Comando updateSql(): Erro no UPDATE: ';
				die('<br>Comando: Erro no updateSql(): ' . mysqli_connect_error());
				exit();
			}else{
				echo '<br>Comando updarteSql(): UPDATE OK! ';
			}
			return self:: $resultado;
		}

		public function deleteSql($sql){
			self::$resultado = ConexaoBD::getMysqli()->query($sql) ;

			// Verifica se ocorreu algum erro
			if (!self::$resultado) {
				echo '<br>Comando deleteSql(): Erro no DELETE: ';
				die('<br>Comando: Erro no deleteSql(): ' . mysqli_connect_error());
				exit();
			}else{
				echo '<br>Comando updarteSql(): DELETE OK! ';
			}
			return self:: $resultado;
		}
	}
?>

