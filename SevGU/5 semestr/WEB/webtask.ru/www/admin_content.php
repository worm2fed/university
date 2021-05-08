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
				<h2><a href="?action=add"><img src="/img/add.png"></a> | Список фото</h2>

				<div class="clearfix"></div>
			</div>

			<div class="x_content">
				<table class="table table-striped">
					<thead>
						<tr class="headings">
							<th class="column-title" width="50px"> # </th>
							<th class="column-title" width="375px">Фото</th>
							<th class="column-title">Информация</th>
							<th class="column-title">Дата</th>
							<th class="column-title">Действия</th>
						</tr>
					</thead>

					<tbody>
<?php
$photo_list = $photo_handler->getPhotoList();
for ($i = 0; $i < $photo_list['num_rows']; $i++) {
?>
						<tr>
							<td><?php echo $i + 1; ?></td>
							<td><img src="/img/gal/<?php echo $photo_list[$i]['photo_file']; ?>" width="350px"></td>
							<td><h3><?php echo $photo_list[$i]['photo_name']; ?></h3><p><?php echo $photo_list[$i]['photo_description']; ?></p></td>
							<td><?php echo $photo_list[$i]['photo_date']; ?></td>
							<td>
								<a href="?action=edit&id=<?php echo $photo_list[$i]['photo_id']; ?>">Изменить</a>
								<br>
								<?php if ($user_info['user_id'] == $photo_list[$i]['user_id']) { ?><a href="?action=del&id=<?php echo $photo_list[$i]['photo_id']; ?>">Удалить</a><?php } ?>
							</td>
						</tr>
<?php 
}
?>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>