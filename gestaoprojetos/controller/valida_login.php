<?

    require_once '../bd/ConexaoBD.php';
    require_once '../bd/ComandosSql.php';
    require_once '../bd/Tarefa.php';

    session_start();

    $conexaobd = new ConexaoBD();
    $comandosql = new ComandosSql();
    $tarefa = new Tarefa();



    //VARIAVEL QUE VERIFICA SE A AUTENTICAÇÃO FOI REALIZADA
    $idemail = null;
    $idusu=null;
    $email = null;
    $senha = null;
    $autenticacao = false;
    $perfilid=null;

    $usuarios_app=$tarefa->receber('email','idemail');
    //echo "email: ".$_POST['email']." senha: ".$_POST['senha'];
    while($user = mysqli_fetch_row($usuarios_app)){
            if ($user[2] == $_POST['email'] && $user[3] == $_POST['senha']) {

                //echo "email: ".$user[2]." senha: ".$user[3];
                $idemail = $user[0];//separando o id logado
                $idusu = $user[1];
                $email = $user[2];
                $senha = $user[3];
                $autenticacao = true;//4
                $perfilid=$user[5];
            }
    }
    //$descricaoperfis = $tarefa->receber('perfil',$perfil_id);//se errado o fica $perfil_id=null

    //CRIANDO INDICE NA SESSION
    if($autenticacao){//autenticacao = true
        echo '<br>Usuário autenticado.';

        $_SESSION['idemail'] = $idemail;//CRIANDO O ID NO SESSION
        $_SESSION['idusu']=$idusu;
        $_SESSION['email']=$email;
        $_SESSION['senha']=$senha;
        $_SESSION['autenticacao']=$autenticacao;
        $_SESSION['perfilid']=$perfilid;
        echo $_SESSION['autenticacao'] .' '. $autenticacao;
       header('Location: ../view/home.php');
    }else{
        echo '<br>Usuário NAO foi autenticado.';
        $_SESSION['autenticacao'] = false;
       header('Location: ../index.php?login=erro');
    }

?>