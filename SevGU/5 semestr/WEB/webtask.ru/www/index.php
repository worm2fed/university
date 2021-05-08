<?php
require_once "include/config.php";
$photo_list = $photo_handler->getPhotoList();
?>

<!DOCTYPE html>
<html>
<head>
	<title>Alphine Design</title>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
   	
	<link rel="shortcun icon" type="image/png" href="favicon.png">
	<link href="css/main.css" rel="stylesheet">

	<script src="js/jquery.min.js"></script>
</head>

<body>
	<header id="main_header">
		<h1 class="logo">Alphine Design</h1>
		
		<nav>
			<ul class="main_menu">
				<li><a href="/">HOME</a>
				<li><a href="#">PORTFOLIO</a>
				<li><a href="#">ABOUT</a>
				<li><a href="#">SERVICE</a>
				<li><a href="#">NEWS</a>
				<li><a href="#">CONTACT</a>
			</ul>
		</nav>
	</header>

	<main role="main" id="main">
		<p class="intro">Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Donec sed odio dui. Integer posuere erat a ante venenatis dapibus aliquet.</p>

		<div id="filter">FILTER: WEB DESIGN / MOTION GRAPHICS / PHOTOGRAPHY</div>

		<div id="content">
			<table class="photo" id="photo_table">
<?php
$j = 0;
$all_photo = true;
for ($i = 0; $i < 2; $i++) {
	if ($i % 4 == 0)
		echo "\t\t\t\t<tr>\n";

	for ($j, $c = 0; $c < 4; $j++, $c++) {
		if (!isset($photo_list[$j]))
			break;
?>
					<td><img src="/img/gal/<?php echo $photo_list[$j]['photo_file']; ?>"></td>
<?php 
	}
	if ($i % 4 == 0)
		echo "\t\t\t\t</tr>\n";

	if (!isset($photo_list[$j])) {
		$all_photo = false;
		break;
	}
}
?>
			</table>
<?php 
if ($all_photo) {
?>
			<footer class="photo_footer"><button class="content_button green_button" id="more_photo_btn">More</button></footer>
<?php 
}
?>
		</div>
	</main>

	<div id="main_footer">
		<div id="footer_content">
			<div id="second_col">
				<div id="services">
					<article>
						<header class="footer_content_header">SERVICES <span>/ what do i do</span></header>

						<p>Nullam id dolor id nibh ultricies vehicula ut id elit. Etiam porta sem malesuada magna mollis euismod. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vestibulum id ligula porta felis euismod semper.</p>

						
						<p><span class="sub_header">PHOTOGRAPHY</span> Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>

						<p><span class="sub_header">WEB DESIGN</span> Sed posuere consectetur est at lobortis. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum.</p>

						<p><span class="sub_header">MOTION GRAPHICS</span> Aenean lacinia bibendum nulla sed consectetur. Donec ullamcorper nulla non metus auctor fringilla. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.</p>
					</article>
				</div>

				<div id="popular">
					<article>
						<header class="footer_content_header">POPULAR POSTS <span>/ posts got most attention</span></header>

						<table id="popular_table">
							<tr>
								<td width="90px"><img src=""></td>
								<td>
									<h4>INCEPTOS PELLENTESQUE RISIS IPSUM</h4>
									<span>12 November 2011 / 117 Comments</span>
								</td>
							</tr>
							<tr>
								<td><img src=""></td>
								<td>
									<h4>LIGULA IPSUM MOLLIS RIDICULUS PARTURIENT</h4>
									<span>12 November 2011 / 117 Comments</span>
								</td>
							</tr>
							<tr>
								<td><img src=""></td>
								<td>
									<h4>PHARETRA CONDIMENT UMLOREMELIT DOLOR SEM EUISMOD TORTOR INCEPTOS ELIT</h4>
									<span>12 November 2011 / 117 Comments</span>
								</td>
							</tr>
						</table>
					</article>
				</div>
			</div>

			<div id="first_col">
				<div id="about">
					<article>
						<header class="footer_content_header">ABOUT US <span>/ company biography</span></header>

						<p>Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vestibulum id ligula porta felis euismod semper. Nullam id dolor id nibh ultricies vehicula ut id elit. Aenean eu leo quam. Pellentesque ornare sem lacinia quam.</p>

						<p>Aenean lacinia bibendum nulla sed consectetur. Integer posuere erat a ante venenatis dapibus posuere velit aliquet.</p>

						<footer class="footer_content_footer"><button class="content_button green_button">Hire Us</button></footer>
					</article>
				</div>

				<div id="contact">
					<article>
						<header class="footer_content_header">CONTACT US <span>/ get in touch with us</span></header>

						<p>Vestibulum  id ligula porta felis euismod semper. Integer posuere erat a ante venenatis dapibus posuere velit aliquet.</p>

						<!--<form method="post" action="">-->
							<table id="contact_table">
								<tr>
									<td width="100px">Name:</td>
									<td id="contact_name_td"><input type="text" name="name" id="contact_name" onclick="$('#contact_name').removeClass('input_error'); $('#name_err').remove()"></td>
								</tr>
								<tr>
									<td>Email:</td>
									<td id="contact_email_td"><input type="email" name="email" id="contact_email" onclick="$('#contact_email').removeClass('input_error'); $('#email_err').remove()"></td>
								</tr>
								<tr>
									<td>Subject:</td>
									<td id="contact_subject_td"><input type="text" name="subject" id="contact_subject" onclick="$('#contact_subject').removeClass('input_error'); $('#subject_err').remove()"></td>
								</tr>
								<tr class="textarea_tr">
									<td>Message:</td>
									<td id="contact_message_td"><textarea name="message" id="contact_message" onclick="$('#contact_message').removeClass('input_error'); $('#message_err').remove()"></textarea></td>
								</tr>
								<tr>
									<td></td>
									<td><button type="submit" class="content_button purple_button" name="send" id="contact_send">Submit</button></td>
								</tr>
							</table>	
						<!--</form>-->
					</article>
				</div>
			</div>

			<div class="clear-fix"></div>
		</div>
	</div>

	<footer id="footer">&copy; 2011 Zeences Design. All Right Reserved</footer>
</body>
</html>

<script>
// More photo 
$('#more_photo_btn').click(function() {
	$('#photo_table').append('<?php for ($i = 0; $i < 2; $i++) { if ($i % 4 == 0) echo "\t\t\t\t<tr>\\n"; for ($j, $c = 0; $c < 4; $j++, $c++) { if (!isset($photo_list[$j])) break; ?> <td><img src="/img/gal/<?php echo $photo_list[$j]['photo_file']; ?>"></td><?php } if ($i % 4 == 0) echo "\t\t\t\t</tr>\\n"; if (!isset($photo_list[$j])) { $all_photo = false; break; } } ?>').hide().fadeIn('slow');

	if ('<?php echo $all_photo; ?> == 0')
		$('#more_photo_btn').remove();
});

// Send message
$('#contact_send').click(function() {
	if ($('#contact_name').val() == '' || $('#contact_name').val().length == 0) {
		$('#contact_name').addClass('input_error');
		$('#contact_name_td').append('<span class="error" id="name_err">This field can\'t be empty</span>');
	} 
	else if ($('#contact_name').val().length < 5) {
		$('#contact_name').addClass('input_error');
		$('#contact_name_td').append('<span class="error" id="name_err">Type at least 5 symbols</span>');
	}
	else if ($('#contact_email').val() == '' || $('#contact_email').val().length == 0) {
		$('#contact_email').addClass('input_error');
		$('#contact_email_td').append('<span class="error" id="email_err">This field can\'t be empty</span>');
	} 
	else if ($('#contact_email').val().search(/^\w+@\w+\.\w{2,4}$/i) == -1) {
		$('#contact_email').addClass('input_error');
		$('#contact_email_td').append('<span class="error" id="email_err">Incorrect email</span>');
	}
	else if ($('#contact_subject').val() == '' || $('#contact_subject').val().length == 0) {
		$('#contact_subject').addClass('input_error');
		$('#contact_subject_td').append('<span class="error" id="subject_err">This field can\'t be empty</span>');
	} 
	else if ($('#contact_subject').val().length < 5) {
		$('#contact_subject').addClass('input_error');
		$('#contact_subject_td').append('<span class="error" id="subject_err">Type at least 5 symbols</span>');
	}
	else if ($('#contact_message').val() == '' || $('#contact_message').val().length == 0) {
		$('#contact_message').addClass('input_error');
		$('#contact_message_td').append('<span class="error" id="message_err">This field can\'t be empty</span>');
	} 
	else if ($('#contact_message').val().split(/ +(?:\S)/).length < 5) {
		$('#contact_message').addClass('input_error');
		$('#contact_message_td').append('<span class="error" id="message_err">Type at least 5 words</span>');
	}
});
</script>