<?php
    //Caso erro no login ira exibir alguma mensagem de erro no contaneir do login
    function alerta_login(){
        $texto_alerta = "";
        //Verificar caso o atribuir login exista...
        if(isset($_GET['login'])) {
            if ($_GET['login'] == 'erro') {

                $texto_alerta = "Usuário ou senha inválido(s)";
            }

            if ($_GET['login'] == 'erro0') {

                $texto_alerta = "Banco Desconetado!";
            }

            if ($_GET['login'] == 'erro2') {

                $texto_alerta = "Faça o login!";
            }
        }

        if(isset($_GET['cadastro'])){
            if ($_GET['cadastro'] == '1') {

                $texto_alerta = " Cadastro realizado com sucesso!";
            }
            if ($_GET['cadastro'] == '0') {

                $texto_alerta = " Erro, cadastro não realizado!";
            }

        }

        if(isset($_GET['editado'])){
            if ($_GET['editado'] == '1') {

                $texto_alerta = " Cadastro editado com sucesso!";
            }
            if ($_GET['editado'] == '0') {

                $texto_alerta = " Erro, cadastro não editado!";
            }

        }

        if(isset($_GET['email'])){
            if ($_GET['email'] == '1') {

                $texto_alerta = " Email enviado com sucesso!";
            }
            if ($_GET['editado'] == '0') {

                $texto_alerta = " Erro, email não pode ser enviado!";
            }

        }
        return $texto_alerta;
    }

?>
