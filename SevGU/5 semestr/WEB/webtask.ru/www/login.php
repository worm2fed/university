<div class="login_box">
   <div class="x_panel">
      <div class="x_title">
         <h1>Добро пожаловать!</h1>
         <div class="clearfix"></div>
      </div>

      <div class="x_content form">
         <form class="form-horizontal form-label-left" name="sign_in_user" method="post" action="">
            <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
               <input type="text" class="form-control has-feedback-left" placeholder="Имя пользователя" name="login" autofocus  required>
               <span class="fa form-control-feedback left" aria-hidden="true">@</span>
            </div>
            
            <div class="col-md-6 col-sm-6 col-xs-12 form-group has-feedback">
               <input type="password" class="form-control has-feedback-left" placeholder="Пароль" name="password"  required>
               <span class="fa form-control-feedback left" aria-hidden="true">#</span>
            </div>

            <div class="x_title">
               <div class="clearfix"></div>
            </div>

            <div class="login_button_box">
               <button type="submit" class="btn btn-success btn-lg login_button" name="sign_in">Войти в систему</button>
            </div>
         </form>
         <?php echo $error; ?>
      </div>
   </div>
</div>