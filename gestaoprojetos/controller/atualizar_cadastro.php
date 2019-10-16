<?php

    require_once '../bd/ConexaoBD.php';
    require_once '../bd/ComandosSql.php';
    require_once '../bd/Tarefa.php';

    session_start();
    $conexaobd = new ConexaoBD();
    $comandosql = new ComandosSql();
    $tarefa =new Tarefa();

    $tab="";
    $id_valor="";
    $campos="";

    $idemail=$_SESSION['idemail'];
    $email = $_SESSION['email'];
    $senha = $_SESSION['senha'];
    $autenticacao = $_SESSION['autenticacao'];
    $perfilid = $_SESSION['perfilid'];

    //usuario
    $nome="";
    $sobrenome = '';
    $cpf = '';
    $rg = '';
    $sexo = '';

    //PJ
    $razaosocial =  '';
    $nomefantasia =  '';
    $cnpj =  '';

    //campos em comum para ambos os casos
    $id=$_SESSION['id'];
    $fone=$_POST['fone'];
    $fonecel=$_POST['fonecel'];
    $tipologradouro=$_POST['tipologradouro'];
    $rua=$_POST['rua'];
    $cidade=$_POST['cidade'];
    $uf=$_POST['uf'];
    $cep=$_POST['cep'];
    $descricao=$_POST['descricao'];

    //Atualiza no BD
    if ($perfilid==2) {
        $tab="usuario";
        $id_valor="idusu=$id";

        $nome = $_POST['nome'];
        $sobrenome = $_POST['sobrenome'];
        $cpf = $_POST['cpf'];
        $rg = $_POST['rg'];
        $sexo = $_POST['sexo'];

        $campos = "nome='$nome',sobrenome='$sobrenome',sexo='$sexo',cpf='$cpf',rg='$rg',
                foneres='$fone',fonecel='$fonecel',idemail=$idemail,tipologradouro='$tipologradouro',rua='$rua',cidade='$cidade',uf='$uf',
                cep='$cep',descricao='$descricao'";

    }elseif($perfilid==3){
        $tab="empregador";
        $id_valor="idemp=$id";
        $razaosocial = $_POST['razaosocial'];
        $nomefantasia = $_POST['nomefantasia'];
        $cnpj = $_POST['cnpj'];

        $campos = "razaosocial='$razaosocial',nomefantasia='$nomefantasia',cnpj='$cnpj',
                fonecom='$fone',fonecel='$fonecel',idemail=$idemail,tipologradouro='$tipologradouro',rua='$rua',cidade='$cidade',uf='$uf',
                cep='$cep',descricao='$descricao'";

    }


    if($tarefa->atualizar($tab,$campos,$id_valor)) {

        //Atualizar SESSION
        //$_SESSION['id'] = $_POST[''];//CRIANDO O ID NO SESSION
        $_SESSION['email'] = $email;
        $_SESSION['senha'] = $senha;
        if ($perfilid == 2) {
            $_SESSION['nome'] = $nome;
            $_SESSION['sobrenome'] = $sobrenome;
            $_SESSION['cpf'] = $cpf;
            $_SESSION['rg'] = $rg;
            $_SESSION['sexo'] = $sexo;
        } elseif ($perfilid == 3) {
            $_SESSION['razaosocial'] = $razaosocial;
            $_SESSION['nomefantasia'] = $nomefantasia;
            $_SESSION['cnpjf'] = $cnpj;
        }

        $_SESSION['fone'] = $fone;
        $_SESSION['fonecel'] = $fonecel;
        $_SESSION['tipologradouro'] = $tipologradouro;
        $_SESSION['rua'] = $rua;
        $_SESSION['cidade'] = $cidade;
        $_SESSION['uf'] = $uf;
        $_SESSION['cep'] = $cep;
        $_SESSION['descricao'] = $descricao;
        $_SESSION['autenticacao'] = $autenticacao;
        $_SESSION['perfilid'] = $perfilid;
        echo $autenticacao;
        header('Location: ../view/editar_cadastro.php?editado=1');

    }else{
        header('Location: ../view/editar_cadastro.php?editado=0');
    }
?>