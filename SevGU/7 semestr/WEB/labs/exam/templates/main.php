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
        <h4><?= Config::APP_NAME ?></h4>
      </div>
    </header>

    <article class="content <?= $page_class ?? '' ?>">
      <?php require $__view ?>
    </article>
  </div>
</div>

<script src="/static/js/vendor.js"></script>
<script src="/static/js/app.js"></script>

</body>
</html>
