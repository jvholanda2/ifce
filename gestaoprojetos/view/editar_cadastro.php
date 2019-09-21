<? 
    require_once "../controller/logon.php";
    require_once "../controller/alerta_login.php";
    //require_once "../controller/get_user.php";

    $uf = array("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA",
        "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");

    $tipologradouro = array( "Aeroporto","Alameda","Área","Avenida","Campo","Chácara", "Colônia", "Condomínio", "Conjunto",
        "Distrito","Esplanada", "Estação","Estrada","Favela","Fazenda","Feira","Jardim", "Ladeira", "Lago",
        "Lagoa",  "Largo", "Loteamento",  "Morro", "Núcleo", "Parque", "Passarela","Pátio", "Praça",
        "Quadra","Recanto","Residencial","Rodovia","Rua","Setor","Sítio","Travessa","Trecho",
        "Trevo","Vale","Vereda","Via","Viaduto","Viela", "Vila");


 ?>

<html>
  <head>
    <meta charset="utf-8" />
    <title>Secretaria Jovem Maracanaú</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style>
      .card-abrir-chamado {
        padding: 30px 0 0 0;
        width: 100%;
        margin: 0 auto;
      }
    </style>
  </head>

  <body>

    <nav class="navbar navbar-dark bg-dark">
      <a class="navbar-brand" href="#">
        <img src="../img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
          Secretaria Jovem Maracanaú
      </a>
      <ul class="navbar-nav">
          <li class="nav-item">
              <?=$_SESSION['nome'].' '.
              $_SESSION['sobrenome']
              ?>
          </li>
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
              Editar Curriculum Vitae
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col">
                  
                  <form method="POST" action="../controller/atualizar_cadastro.php">


                      <div class="form-row">

                          <div class="form-group col-md-6">
                              <label for="inputEmail4">Usuário</label>
                              <input name="email" type="email" class="form-control" id="inputEmail4" placeholder="Email" value="<?=$_SESSION['email']?>">
                          </div>
                          <div class="form-group col-md-6">
                              <label for="inputPassword4">Senha</label>
                              <input name="senha" type="password" class="form-control" id="inputPassword4" placeholder="Senha" value="<?=$_SESSION['senha']?>">
                          </div>
                      </div>

                      <div class="form-row">
                          <?if ($_SESSION['perfilid']==2){?>
                              <div class="form-group col-md-4">
                                  <label for="cpf">CPF</label>
                                  <input type="text" class="form-control" id="cpf" name="cpf" value="<?=$_SESSION['cpf']?>" >
                              </div>
                              <div class="form-group col-md-4">
                                  <label for="rg">RG</label>
                                  <input type="text" class="form-control" id="rg" name="rg" value="<?=$_SESSION['rg']?>">
                              </div>

                              <div class="form-group col-md-2 ml-4">
                                  <label>Sexo </label>
                                  <div class="form-check-inline">
                                      <div class="form-check form-check-inline">
                                          <input class="form-check-input" type="radio" name="sexo" id="inlineRadio1" value="M" <?php if("M"==$_SESSION['sexo']){echo 'checked';}?>>
                                          <label class="form-check-label" for="inlineRadio1">Masculino</label>
                                      </div>
                                      <div class="form-check form-check-inline">
                                          <input class="form-check-input" type="radio" name="sexo" id="inlineRadio2" value="F" <?php if("F"==$_SESSION['sexo']){echo 'checked';} ?>>
                                          <label class="form-check-label" for="inlineRadio2">Feminino</label>
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <div class="form-row">
                              <div class="form-group col-md-6">
                                  <label for="nome">Nome</label>
                                  <input name="nome" type="text" class="form-control" id="nome" value="<?=$_SESSION['nome']?>">
                              </div>
                              <div class="form-group col-md-6">
                                  <label id="sobrenome">Sobrenome</label>
                                  <input name="sobrenome" type="text" class="form-control" id="sobrenome" value="<?=$_SESSION['sobrenome']?>">
                              </div>
                          </div>

                      <?}elseif ($_SESSION['perfilid']==3){?>
                          <div class="form-group col-md-4">
                              <label for="cnpj">CNPJ</label>
                              <input type="text" class="form-control" id="cnpj" name="cnpj" value="<?=$_SESSION['cnpj']?>" >
                          </div>
                          <div class="form-group col-md-4">
                          </div>

                          <div class="form-group col-md-2 ml-4">
                              <label></label>
                              <div class="form-check-inline">
                                  <div class="form-check form-check-inline">
                                  </div>
                                  <div class="form-check form-check-inline">
                                  </div>
                              </div>
                          </div>
                        </div>
                          <div class="form-row">
                              <div class="form-group col-md-6">
                                  <label for="nome">Razão Social</label>
                                  <input name="razaosocial" type="text" class="form-control" id="razasocial" value="<?=$_SESSION['razaosocial']?>">
                              </div>
                              <div class="form-group col-md-6">
                                  <label id="sobrenome">Nome Fantasia</label>
                                  <input name="nomefantasia" type="text" class="form-control" id="sobrenome" value="<?=$_SESSION['nomefantasia']?>">
                              </div>
                        </div>
                    <?}?>



                  <div class="form-group">
                          <label>Descrição</label>
                          <textarea name="descricao" class="form-control" rows="3" placeholder="Uma breve descrição de suas habilidades e interesses..."><?=$_SESSION['descricao']?></textarea>
                      </div>
                      <div class="form-row">
                          <div class="form-group col-md-6">
                              <label for="foneres">Tel. residêncial</label>
                              <input type="tel" class="form-control" id="foneres" name="fone" value="<?=$_SESSION['fone']?>">
                          </div>
                          <div class="form-group col-md-6">
                              <label for="celular">Celular</label>
                              <input type="tel" class="form-control" id="celular" name="fonecel" value="<?=$_SESSION['fonecel']?>">
                          </div>
                      </div>

                      <div class="form-row">
                          <div class="form-group col-md-3">
                              <label for="logra">Tipo Lograd.</label>

                              <select id="logra" name="tipologradouro" class="form-control">
                                  <option value="">Selecione </option>
                                  <?php $x = 0;
                                  while ($x<  count($tipologradouro)){ ?>
                                      <option <?php if($tipologradouro[$x]==$_SESSION['tipologradouro']){echo 'selected';} ?> value="<?=$tipologradouro[$x]?>"><?=$tipologradouro[$x]?></option>
                                      <?php  $x++;
                                  }?>
                              </select>
                          </div>
                          <div class="form-group col-md-9">
                              <label for="inputAddress">Endereço</label>
                              <input name="rua" type="text" class="form-control" id="inputAddress" placeholder="Rua dos Bobos, nº 0" value="<?=$_SESSION['rua']?>">
                          </div>
                      </div>

                      <div class="form-row">
                          <div class="form-group col-md-6">
                              <label for="inputCity">Cidade</label>
                              <input name="cidade" type="text" class="form-control" id="inputCity" value="<?=$_SESSION['cidade']?>">
                          </div>
                          <div class="form-group col-md-2">
                              <label for="inputEstado">Estado</label>
                              <select id="inputEstado" name="uf" class="form-control" value="<?=$_SESSION['uf']?>">
                                  <optgroup label="UF">
                                      <option value="">UF</option>
                                      <?
                                      $x = 0;
                                      while ($x<27){ ?>
                                          <option <?php if($uf[$x]==$_SESSION['uf']){echo 'selected';} ?> value="<?=$uf[$x]?>"><?=$uf[$x]?></option>
                                      <?  $x++;
                                      }?>
                                  </optgroup>
                              </select>
                          </div>
                          <div class="form-group col-md-2">
                              <label for="inputCEP">CEP</label>
                              <input  name="cep" type="text" class="form-control" id="inputCEP" value="<?=$_SESSION['cep']?>">
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
                        <button class="btn btn-lg btn-info btn-block" type="submit">Atualizar</button>
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