<?php

    require "../lib/PHPMailer/Exception.php";
    require "../lib/PHPMailer/OAuth.php";
    require "../lib/PHPMailer/PHPMailer.php";
    require "../lib/PHPMailer/POP3.php";
    require "../lib/PHPMailer/SMTP.php";

    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;

    class Mensagem{
        private $nome_remetente = null;
        private $seu_email = null;
         private $assunto = null;
         private $mensagem = null;

         public function __get($atributo)
         {
             return $this->$atributo;
         }

         public function __set($atributo, $valor)
         {
             $this->$atributo =$valor;
         }

         public function mensagemValida(){
             if(empty($this->seu_email) || empty($this->assunto) || empty($this->mensagem)){
                 return false;
             }
             return true;
         }

    }


    $mensagem = new Mensagem();

    $mensagem->__set('nome_remetente',$_POST['nome_remetente']);
    $mensagem->__set('seu_email',$_POST['seu_email']);
    $mensagem->__set('assunto',$_POST['assunto']);
    $mensagem->__set('mensagem',$_POST['mensagem']);

    if(!$mensagem->mensagemValida()){
        echo "Mensagem não é válida!";
        die();//mata a app, caso ocorra erro
    }

    $mail = new PHPMailer(true);
    try {
        //Server settings
        $mail->SMTPDebug = false;                                 // Enable verbose debug output
        $mail->isSMTP();                                      // Set mailer to use SMTP
        $mail->Host = 'smtp.gmail.com';  // Specify main and backup SMTP servers
        $mail->SMTPAuth = true;                               // Enable SMTP authentication
        $mail->Username = 'nodemcuesp12e@gmail.com';                 // SMTP username
        $mail->Password = 'esp826612e';                           // SMTP password
        $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
        $mail->Port = 587;                                    // TCP port to connect to

        //Recipients
       // $mail->setFrom('nodemcuesp12e@gmail.com', 'JJR');
       // $mail->addAddress($mensagem->__get('para'));     // Add a recipient
        $mail->setFrom($mensagem->__get('seu_email'), $mensagem->__get('nome_remetente').' - '.$mensagem->__get('seu_email'));
        $mail->addAddress('nodemcuesp12e@gmail.com');     // Add a recipient
        //$mail->addAddress('ellen@example.com');               // Name is optional
        //$mail->addReplyTo('info@example.com', 'Information');
        //$mail->addCC('cc@example.com');
        //$mail->addBCC('bcc@example.com');

        //Attachments
        //$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
        //$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name

        //Content
        $mail->isHTML(true);                                  // Set email format to HTML
        $mail->Subject = $mensagem->__get('assunto');
        $mail->Body    = $mensagem->__get('mensagem');
        $mail->AltBody = 'Por favor use um client de  email com suporte  HTML!';

        $mail->send();
        echo 'Email enviado com sucesso!';
        header('Location: ../index.php?email=1');
    } catch (Exception $e) {
        echo 'Não foi possível enviar este email!';
        echo 'Detalhes do Error: ' . $mail->ErrorInfo;
        header('Location: ../index.php?email=0');
    }

?>
