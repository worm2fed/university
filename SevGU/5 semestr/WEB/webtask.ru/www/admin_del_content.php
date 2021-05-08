<!-- top navigation -->
<div class="top_nav">
	<div class="nav_menu">
		<nav class="" role="navigation">
			<ul class="nav navbar-nav navbar-right" style="font-size: 1.5em;">
				<li><a href="" id="top_menu_manual">| Привет, <?php echo $user_info['user_name']; ?></a></li>
				<li><a href="?action=logout" id="top_menu_manual">Выход</a></li>
				<li><a href="/admin.php" id="top_menu_manual">Главная</a></li>
			</ul>
		</nav>
	</div>
</div>
<!-- /top navigation -->

<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12 admin_body">
		<div class="x_panel">
			<div class="x_title">
				<h2>Вы действительно хотите удалить фото?</h2>

				<div class="clearfix"></div>
			</div>

			<div class="x_content">
				<form action="" method="post" class="form-horizontal form-label-left">
					<div class="form-group">
						<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="text-align: center;">
							<h3><?php echo $photo_info['photo_name']; ?></h3>
							<img src="/img/gal/<?php echo $photo_info['photo_file']; ?>" width="350px">
						</div>
					</div>

					<div class="ln_solid"></div>

					<div class="form-group">
						<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="text-align: center;">
							<button type="submit" name="del_photo_btn" class="btn btn-warning">Удалить фотографию</button>
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
</div>