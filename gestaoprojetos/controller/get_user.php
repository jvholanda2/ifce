<?

    require_once '../bd/ConexaoBD.php';
    require_once '../bd/ComandosSql.php';
    require_once '../bd/Tarefa.php';

    session_start();

    $conexaobd = new ConexaoBD();
    $comandosql = new ComandosSql();
    $tarefa = new Tarefa();

    $tab="";
    $idx="";

    //VARIAVEL QUE VERIFICA SE A AUTENTICAÇÃO FOI REALIZADA
    $idemail=$_SESSION['idemail'];
    $email = $_SESSION['email'];
    $senha = $_SESSION['senha'];
    $autenticacao = $_SESSION['autenticacao'];
    $perfilid = $_SESSION['perfilid'];

    $id = null;
    $nome = null;
    $sobrenome = null;
    $cpf = null;
    $rg = null;
    $sexo=null;

    $razaosocial=null;
    $nomefantasia=null;
    $cnpj=null;

    $fone = null;
    $fonecel = null;
    //$idemail = null;
    $tipologradouro = null;
    $rua = null;
    $cidade = null;
    $uf =null;
    $cep = null;
    $descricao = null;

    //Decidir de qual tabela
    if($perfilid==1){//adm
        $tab = "administrador";
        $idx="idadm";
    }elseif ($perfilid==2){//usuario
        $tab = "usuario";
        $idx = "idusu";
    }else if ($perfilid==3){//empregador
        $tab="empregador";
        $idx="idemp";
    }

    //Peguando os dados da tabela de acordo com o perfil
    $usuarios_app=$tarefa->receber($tab,$idx);

    while($user = mysqli_fetch_row($usuarios_app)){
        if ($perfilid==2) {//usuario

            if ($user[8] == $idemail) {

                $id = $user[0];//separando o id logado
                $nome = $user[1];
                $sobrenome = $user[2];
                $sexo = $user[3];
                $cpf = $user[4];
                $rg = $user[5];
                $fone = $user[6];
                $fonecel = $user[7];
                $idemail = $user[8];
                $tipologradouro = $user[9];
                $rua = $user[10];
                $cidade = $user[11];
                $uf = $user[12];
                $cep = $user[13];
                $descricao = $user[14];
            }
        }else if($perfilid==3) {//3=empregador

            if ($user[6] == $idemail) {

                $id = $user[0];//separando o id logado
                $razaosocial = $user[1];
                $nomefantasia = $user[2];
                $cnpj = $user[3];
                $fone = $user[4];
                $fonecel = $user[5];
                $idemail = $user[6];
                $tipologradouro = $user[7];
                $rua = $user[8];
                $cidade = $user[9];
                $uf = $user[10];
                $cep = $user[11];
                $descricao = $user[12];
            }
        }
    }
    //$descricaoperfis = $tarefa->receber('perfil',$perfil_id);//se errado o fica $perfil_id=null
echo '<br>fone'.$fone;
    //CRIANDO INDICE NA SESSION
    if($autenticacao){//autenticacao = true
        echo '<br>Usuário autenticado.';

        $_SESSION['id'] = $id;//CRIANDO O ID NO SESSION

        if ($perfilid==2) {
            $_SESSION['nome'] = $nome;
            $_SESSION['sobrenome'] = $sobrenome;
            $_SESSION['cpf'] = $cpf;
            $_SESSION['rg'] = $rg;
            $_SESSION['sexo'] = $sexo;
        }elseif($perfilid==3){
            $_SESSION['razaosocial'] = $razaosocial;
            $_SESSION['nomefantasia'] = $nomefantasia;
            $_SESSION['cnpj'] = $cnpj;
        }

        $_SESSION['fone'] = $fone;
        $_SESSION['fonecel']=$fonecel;
        $_SESSION['idemail']=$idemail;
        $_SESSION['tipologradouro']=$tipologradouro ;
        $_SESSION['rua']=$rua ;
        $_SESSION['cidade']=$cidade ;
        $_SESSION['uf']=$uf;
        $_SESSION['cep']=$cep ;
        $_SESSION['descricao']=$descricao;

        //echo $_SESSION['autenticacao'] .' '. $autenticacao;
        header('Location: ../view/editar_cadastro.php');
    }else{
        $_SESSION['autenticacao'] = false;
        header('Location: ../index.php?login=erro');
    }

?>