<?

    require_once "./controller/logon_index.php";
    require_once "./controller/alerta_login.php";

    ?>
<!DOCTYPE html>
	<html lang="pt-br">
		<head>
			<meta charset="utf-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Secretaria Jovem</title>
			<link rel="stylesheet" type="text/css" href="css/style.css">
            <link rel="stylesheet" type="text/css" href="css/carousel.css">
			<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
			<link rel="stylesheet" type="text/css" href="fontawesome/css/fontawesome.min.css">	
			<link rel="stylesheet" type="text/css" href="fontawesome/css/all.css">
			<link href="https://fonts.googleapis.com/css?family=Raleway&display=swap" rel="stylesheet">
		</head>
		<body>
        <header>
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark m-0 border-0">
                <a class="navbar-brand" href="#">Secretaria Jovem</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarCollapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#banner">Página Inicial <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#about">Sobre</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#services">Módulos</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#equipe" >Equipe</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#contato" >Contato</a>
                        </li>
                    </ul>
                    <div class="form-login">
                        <form class="form-inline mt-2 mt-md-0" action="./controller/valida_login.php" method="post">
                            <div class="text-danger">
                                <?=alerta_login();?>
                            </div>
                            <div class="form-login">
                                <a class=" mr-sm-1" href="./view/cadastrar_login.php" >Cadastro</a>
                                <input class="form-control mr-sm-1" name="email" type="text" placeholder="email" aria-label="email">
                                <input class="form-control mr-sm-1" name="senha" type="password" placeholder="senha" aria-label="senha">
                                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Entrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </nav>
        </header>


        <main id="banner" role="main">

            <div id="myCarousel" class="carousel slide m-0" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="first-slide" src="img/service01.jpg" alt="First slide">
                        <div class="container">
                            <div class="carousel-caption text-left">
                                <h1></h1>
                                <p></p>
                                <p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img class="second-slide" src="img/service02.jpg" alt="Second slide">
                        <div class="container">
                            <div class="carousel-caption">
                                <h1></h1>
                                <p></p>
                                <p><a class="btn btn-lg btn-primary" href="#" role="button">Learn more</a></p>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img class="third-slide" src="img/service04.jpg" alt="Third slide">
                        <div class="container">
                            <div class="carousel-caption text-right">
                                <h1></h1>
                                <p></p>
                                <p><a class="btn btn-lg btn-primary" href="#" role="button">Browse gallery</a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

        </main>


        <!--
			<section id="banner" class="masthead">
				<div class="container h-100">

					<div class="row h-100 align-items-center">
						<div class="col-md-12 text-center text-white ">
                            <div class="card-login">
                                <div class="card">
                                    <div class="card-header">
                                        <span>Login</span>
                                    </div>
                                    <div class="card-body">
                                        <form action="controller/valida_login.php" method="post">
                                            <div class="form-group">
                                                <input name="email" type="email" class="form-control" placeholder="E-mail">
                                            </div>
                                            <div class="form-group">
                                                <input name="senha" type="password" class="form-control" placeholder="Senha">
                                            </div>

                                            <div class="text-danger">
                                                <?=alerta_login();?>
                                            </div>

                                            <button class="btn btn-lg btn-info btn-block" type="submit">Entrar</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
						</div>
					</div>

                </div>

			</section>
		-->

			<section id="about" class="page-section bg-about">
				<div class="container">
					<div class="row justify-content-center">
						<div class="col-md-8 text-center text-white">
							<h3 class="">O bootstrap tem o que você precisa</h3>
							<hr class="divider my-4">
							<p class="font-weight-light my-4"> Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
							tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
							quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
							consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
							cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
							proident, sunt in culpa qui officia deserunt mollit anim id est laborum. 	</p>
						</div>
					</div>
				</div>
			</section>
			<section id="services">
				<div class="container-fluid p-0">
					<div class="row no-gutters justify-content-center">
						<div class="col-lg-4 col-sm-6">
							<a href="img/service01.jpg">	
								<img src="img/service01.jpg" class="img-fluid ">
								<div class="box-caption">
									<h2>HTML</h2>
								<p>Otimize o seu HTML utilizando o Bootstrap</p>
								</div>
							</a>							
						</div>
						<div class="col-lg-4 col-sm-6">
							<a href="img/service02.jpg">
							<img src="img/service02.jpg" class="img-fluid">
							<div class="box-caption">
								<h2>CSS</h2>
								<p>Estruturas completas de css dentro do Bootstrap</p>
							</div>
							</a>
						</div>
						<div class="col-lg-4 col-sm-6">
							<a href="img/service03.jpg">
							<img src="img/service03.jpg" class="img-fluid">
							<div class="box-caption">
								<h2>JS</h2>
								<p>Ações específicas com Javascript</p>
							</div>
							</a>
						</div>
						<div class="col-lg-4 col-sm-6">
							<a href="img/service04.jpg">	
								<img src="img/service04.jpg" class="img-fluid ">
								<div class="box-caption">
									<h2>BOOTSTRAP</h2>
								<p>Otimize o seu HTML utilizando o Bootstrap</p>
								</div>
							</a>							
						</div>
						<div class="col-lg-4 col-sm-6">
							<a href="img/service05.jpg">
							<img src="img/service05.jpg" class="img-fluid">
							<div class="box-caption">
								<h2>WORDPRESS</h2>
								<p>Estruturas completas com CMS</p>
							</div>
							</a>
						</div>
						<div class="col-lg-4 col-sm-6">
							<a href="img/service01.jpg">
							<img src="img/service01.jpg" class="img-fluid">
							<div class="box-caption">
								<h2>ARTIGO FINAL</h2>
								<p>NOSSA PROVAAAA</p>
							</div>
							</a>
						</div>
					</div>
				</div>
			</section>
			<section id="equipe" class="equipe">
				<div class="container p-5">
					<div class="row">
						<div class="col-12 text-equipe p-5  justify-content-center text-center text-white">
							<h2>Equipe de colaboradores</h2>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
						</div>
					</div>
					<div class="row text-white text-center">
						<div class="col-md-6 col-lg-3 ">
							<div class="box-equipe">
								<figure>
									<img src="img/Equipe/equipe1.jpg" class="img-fluid">
								</figure>
								<h3>Jane Doe</h3>
								<p>Diretora</p>
								<div class="social">
									<i class="fab fa-linkedin"></i>
									<i class="fab fa-facebook"></i>
									<i class="fab fa-twitter"></i>
									<i class="fab fa-instagram"></i>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-lg-3 ">
							<div class="box-equipe">
								<figure>
									<img src="img/Equipe/equipe2.jpg" class="img-fluid">
								</figure>
								<h3>John Doe</h3>
								<p>Programador</p>
								<div class="social">
									<i class="fab fa-linkedin"></i>
									<i class="fab fa-facebook"></i>
									<i class="fab fa-twitter"></i>
									<i class="fab fa-instagram"></i>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-lg-3 ">
							<div class="box-equipe">
								<figure>
									<img src="img/Equipe/equipe3.jpg" class="img-fluid">
								</figure>
								<h3>John Doe two</h3>
								<p>Estagiário</p>
								<div class="social">
									<i class="fab fa-linkedin"></i>
									<i class="fab fa-facebook"></i>
									<i class="fab fa-twitter"></i>
									<i class="fab fa-instagram"></i>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-lg-3 ">
							<div class="box-equipe">
								<figure>
									<img src="img/Equipe/equipe4.jpg" class="img-fluid">
								</figure>
								<h3>Jane Doe two</h3>
								<p>Presidente</p>
								<div class="social">
									<i class="fab fa-linkedin"></i>
									<i class="fab fa-facebook"></i>
									<i class="fab fa-twitter"></i>
									<i class="fab fa-instagram"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<section id="contato" class="contact bg-about">
				<div class="container">
					<div class="row">
						<div class="col-lg-5 mr-auto">
							<div class="text-contact p-4">
								<h2>Contate-nos agora mesmo e <span>Saiba mais sobre o nosso curso e o Bootstrap</span></h2>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
								tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
								quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
								consequat.</p>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="formulario">
								<form action="controller/envia_email.php" method="post">
									<div class="form-group">
										<input name="nome_remetente" type="text" placeholder="Digite o seu nome" class="form-control">
									</div>
                                    <div class="form-group">
                                        <input name="assunto" type="text" class="form-control" id="assunto" placeholder="Assundo do e-mail">
                                    </div>
									<div class="form-group">
										<input name="seu_email" type="email" placeholder="Digite o seu Email" class="form-control">
									</div>
									<div class="form-group">
										<textarea name="mensagem" class="form-control" placeholder="Descreva a sua dùvida" col="30" row="2">
										</textarea>
									</div>
									<div class="form-group">
										<input type="submit" class="btn btn-primary" value="Enviar">
									</div>
                                    <div class="text-danger">
                                        <?=alerta_login();?>
                                    </div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</section>
			<footer class="midias">
				<div class="container">
					<div class="row p-4">
						<div class="col-lg-6 mx-auto p-5">
								<h4>Siga-nos em nossas redes sociais</h4>
								<div class="row">
									<div class="col-md-6">
										<h4>Navegação</h4>
										<ul>
											<li><a href="#home">Banner</a></li>
											<li><a href="#home">Banner</a></li>
											<li><a href="#home">Banner</a></li>
											<li><a href="#home">Banner</a></li>
										</ul>
									</div>
									<div class="col-md-6">
										<i class="fab fa-instagram"></i>
										<i class="fab fa-facebook"></i>
										<i class="fab fa-twitter"></i>
										<i class="fab fa-youtube"></i>
									</div>
								</div>
							</div>
						<div class="col-lg-6 p-5">
							<div class="row">
								<h4>Curso Wed Design - Bootstrap</h4>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
								tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
								quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
								consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
								cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
								proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
								</p>
							</div>
						</div>
					</div>
				</div>
			</footer>    

			<script type="text/javascript" src="js/jquery.js"></script>
			<script type="text/javascript" src="js/bootstrap.min.js"></script>
			<script type="text/javascript" src="js/main.js"></script>
			<script type="text/javascript" src="fontawesome/js/fontawesome.min.js"></script>
			<script type="text/javascript" src="fontawesome/js/all.min.js"></script>
		</body>
</html>