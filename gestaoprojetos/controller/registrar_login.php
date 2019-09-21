<?php

    require_once '../bd/ConexaoBD.php';
    require_once '../bd/ComandosSql.php';
    require_once '../bd/Tarefa.php';

    session_start();

    $conexaobd = new ConexaoBD();
    $comandosql = new ComandosSql();
    $tarefa =new Tarefa();

    echo $_POST['cad_email'];
    echo $_POST['cad_senha'];
    echo $_POST['cad_perfilid'];

    if(!empty($_POST['cad_email']) && !empty($_POST['cad_senha']) && !empty($_POST['cad_perfilid']) ) {

        $tab="email";
        //Atualiza no BD
        $idemail=null;
        $email=$_POST['cad_email'];
        $senha=$_POST['cad_senha'];
        $autenticacao=0;
        $perfilid=$_POST['cad_perfilid'];

        $campos = "email,senha,autenticacao,perfilid";
        $valores ="'$email','$senha',$autenticacao,$perfilid";
        $tarefa->enviar($tab, $campos, $valores);

        //Atualizar SESSION
        //$_SESSION['id'] = $_POST[''];//CRIANDO O ID NO SESSION
        $_SESSION['email'] = $email;
        $_SESSION['senha'] = $senha;
        $_SESSION['autenticado'] = false;
        $_SESSION['perfilid'] = $perfilid;


        //Cadastrar o user vazio deste email para depois fazer update
        $tab2="";

        if($perfilid==1){
            $tab2="administrador";
        }elseif($perfilid==2){
            $tab2="usuario";
        }elseif($perfilid==3){
            $tab2="empregador";
        }

        $emails=$tarefa->receberWhere('email','email',$email);

        while($em = mysqli_fetch_row($emails)){
            if ($em[2] == $email && $em[3] == $senha) {

                $idemail = $em[0];//separando o id logado
                //$idusu = $em[1];
                //$email = $em[2];
                //$senha = $em[3];
                //$autenticacao = true;//4
                //$perfilid=$em[5];
            }
        }
        //echo "idemail:".$idemail;
        $tarefa->enviar($tab2, 'idemail', $idemail);

        header('Location: ../view/cadastrar_login.php?cadastro=1');
    }else{
        echo "Errooo";
        header('Location: ../view/cadastrar_login.php?cadastro=0');
    }
 ?>