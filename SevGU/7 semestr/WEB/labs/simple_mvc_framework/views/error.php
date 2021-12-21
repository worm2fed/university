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
<div class="app blank sidebar-opened">
    <article class="content">
        <div class="error-card global">
            <div class="error-title-block">
                <h1 class="error-title"> <?= $error_code ?> </h1>
                <h2 class="error-sub-title"> <?= $error_message ?> </h2>
            </div>
        </div>
    </article>
</div>
</body>
</html>