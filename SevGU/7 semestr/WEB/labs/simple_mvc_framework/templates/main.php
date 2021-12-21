<!doctype html>
<html class="no-js" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title> <?= Config::APP_NAME ?> </title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/static/css/vendor.css">
    <!-- Theme initialization -->
    <link rel="stylesheet" id="theme-style" href="/static/css/app.css">
</head>

<body>
<div class="main-wrapper">
    <div class="app" id="app">
        <header class="header">
            <div class="header-block header-block-search">
                <h4><?= Config::APP_NAME ?> <?php if (!\models\UserModel::isGuest()): ?> ( <?= \models\UserModel::getUserIfLoggedIn()->username ?> ) <?php endif; ?></h4>
            </div>
            <div class="header-block header-block-nav">
                <?php if (\models\UserModel::isGuest()): ?>
                    <a href="#" class="btn btn-success"  data-toggle="modal" data-target="#login-modal"><i class="fa fa-sign-in icon"></i> Login</a>
                <?php else: ?>
                    <a href="/?logout" class="btn btn-danger-outline"><i class="fa fa-sign-out icon"></i> Logout</a>
                <?php endif; ?>
            </div>
        </header>

        <article class="content <?= $page_class ?? '' ?>">
            <?php require $__view ?>
        </article>

        <!-- Start login modal -->
        <div class="modal fade" id="login-modal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="post" action="/?login"">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                <i class="fa fa-sign-in"></i> Login</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" class="form-control underlined" name="username" id="username" placeholder="Your login or email address" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control underlined" name="password" id="password" placeholder="Your password" required>
                            </div>

                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Login</button>
                            <button type="reset" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- End login modal -->

    </div>
</div>
<script src="/static/js/vendor.js"></script>
<script src="/static/js/app.js"></script>
</body>
</html>