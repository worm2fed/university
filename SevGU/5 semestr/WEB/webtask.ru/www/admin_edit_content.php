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
				<h2>Вы действительно хотите изменить информацию о фото?</h2>

				<div class="clearfix"></div>
			</div>

			<div class="x_content">
				<form action="" method="post" class="form-horizontal form-label-left" id="edit_photo">
					<div class="form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12">Название фотографии <span class="required">*</span></label>

						<div class="col-md-6 col-sm-6 col-xs-12">
							<input type="text" name="photo_name" required class="form-control col-md-7 col-xs-12" value="<?php echo $photo_info['photo_name']; ?>">
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12">Описание фотографии</label>

						<div class="col-md-6 col-sm-6 col-xs-12">
							<textarea class="form-control" name="photo_description" style="resize: none;" maxlength="500" rows="3"><?php echo $photo_info['photo_name']; ?></textarea>
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"></label>


						<div class="col-md-6 col-sm-6 col-xs-12" style="text-align: center;">
							<img src="/img/gal/<?php echo $photo_info['photo_file']; ?>" width="350px" align="center">
						</div>
					</div>

					<div class="ln_solid"></div>

					<div class="form-group">
						<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="text-align: center;">
							<button type="submit" name="edit_photo_btn" class="btn btn-warning">Сохранить изменения</button>
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
</div>