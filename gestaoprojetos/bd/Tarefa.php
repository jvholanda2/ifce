
<?php

    class Tarefa extends ComandosSql {

        public function __construct(){
        }

        public function enviar($tab,$campos,$valores){
            ConexaoBD::abrirMysqli();
            $db =ConexaoBD::getDBname();

            $sql = "INSERT INTO $db.$tab($campos) values  ($valores)";
            echo "<br>Tarefa: enviar(sql) ".$sql;
            $resultado=ComandosSql::insertsql($sql);
            ConexaoBD::fecharMySqli();
            return $resultado;
        }

        public function receber($tab,$campo){
            ConexaoBD::abrirMysqli();
            $db =ConexaoBD::getDBname();

            $sql = "SELECT * FROM $db.$tab ORDER BY $campo DESC";
            echo "<br>Tarefa: receber(sql) ".$sql;
            $resultado=ComandosSql::selectSql($sql);

            //echo $sql;

            ConexaoBD::fecharMySqli();

            return $resultado;
        }
        public function receberWhere($tab,$campo,$valor){
            ConexaoBD::abrirMysqli();
            $db =ConexaoBD::getDBname();

            $sql = "SELECT * FROM $db.$tab WHERE $campo='$valor'";
            echo "<br>Tarefa: receber(sql) ".$sql;
            $resultado=ComandosSql::selectSql($sql);

            //echo $sql;

            ConexaoBD::fecharMySqli();

            return $resultado;
        }
        public function atualizar($tab,$campos,$id_Valor){
            ConexaoBD::abrirMysqli();
            $db =ConexaoBD::getDBname();

            $sql = "UPDATE $db.$tab SET $campos WHERE $id_Valor";
            echo "<br>Tarefa: receber(sql) ".$sql;
            $resultado=ComandosSql::updateSql($sql);

            //echo $sql;

            ConexaoBD::fecharMySqli();

            return $resultado;
        }

        public function apagar($tab,$campos,$id){
            ConexaoBD::abrirMysqli();
            $db =ConexaoBD::getDBname();

            $sql = "DELETE FROM $db.$tab WHERE $id";
            echo "<br>Tarefa: receber(sql) ".$sql;
            $resultado=ComandosSql::deleteSql($sql);

            //echo $sql;

            ConexaoBD::fecharMySqli();

            return $resultado;
        }
    }
?>
