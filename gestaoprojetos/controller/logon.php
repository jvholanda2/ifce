<?php
    //longon.php verifica nas demais pages
    session_start();

    //se nao existir autencicao ou nao estiver logado...
    //echo $_SESSION['autenticacao'] ;
    if(!isset($_SESSION['autenticacao']) || $_SESSION['autenticacao'] != true){
        header('Location: ../index.php?login=erro2');
    }

?>