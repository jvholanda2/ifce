<?php
    session_start();
    //Se estiver logado sera redirecionado para home
    if(isset($_SESSION['autenticacao']) && $_SESSION['autenticacao'] == true){
        header('Location: ./view/home.php');
    }
?>