<?

require_once "../controller/alerta_login.php";

    $descricaoperfil = array("administrador", "usuario", "empregador");
?>
<html>
  <head>
    <meta charset="utf-8" />
    <title>Secretária Jovem Maracanaú</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style>
      .card-abrir-chamado {
        padding: 30px 0 0 0;
        width: 100%;
        margin: 0 auto;
      }
    </style>
  </head>

  <body >

    <nav class="navbar navbar-dark bg-dark">
      <a class="navbar-brand" href="#">
        <img src="../img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
          Secretaria Jovem Maracanaú
      </a>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a href="../controller/logoff.php" class="nav-link">
          SAIR
          </a>
        </li>
      </ul>
    </nav>

    <div class="container">    
      <div class="row">

        <div class="card-abrir-chamado">
          <div class="card">
            <div class="card-header">
              Cadastro de login
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col">
                  
                  <form method="POST" action="../controller/registrar_login.php">


                      <div class="form-row">
                          <div class="form-group col-md-6">
                              <label for="inputEmail4">Usuário</label>
                              <input name="cad_email" type="email" class="form-control" id="inputEmail4" placeholder="Email">
                          </div>
                          <div class="form-group col-md-6">
                              <label for="inputPassword4">Senha</label>
                              <input name="cad_senha" type="password" class="form-control" id="inputPassword4" placeholder="Senha">
                          </div>
                      </div>

                      <div class="form-row">
                          <div class="form-group col-md-6">
                              <label for="logra">Tipo Usuário</label>
                              <select id="logra" name="cad_perfilid" class="form-control">
                                  <option value="">Selecione </option>
                                  <?
                                  $x = 0;
                                  while ($x<  count($descricaoperfil)){ ?>
                                      <option selected="selected" value="<?=$x+1?>"><?= $descricaoperfil[$x] ?></option>
                                      <?php  $x++;
                                  }?>
                              </select>
                          </div>
                          <div class="form-group col-md-6">
                          </div>
                      </div>

                      <div class="text-danger">
                          <?=alerta_login();?>
                      </div>

                      <div class="row mt-5">
                      <div class="col-6">
                          <a href="home.php" class="btn btn-lg btn-warning btn-block">Voltar</a>
                      </div>

                      <div class="col-6">
                          <button class="btn btn-lg btn-info btn-block" type="submit">Cadastrar</button>
                      </div>
                  </div>
                  </form>

                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
  </body>
</html>