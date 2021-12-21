<?php

/**
 * @file This file is an entry point to app
 */

use core\ErrorHandler;
use core\exceptions\RouterException;
use core\Router;
use core\SessionHandler;
use core\View;

// Plug Config and Loader
require_once 'Config.php';
require_once 'core/Loader.php';

// Register autoloader
spl_autoload_register('core\Loader::autoload');
// Set error handler
new ErrorHandler();
// Start session
$session = SessionHandler::getInstance();
// Create router
$router = new Router();
// Add routes
$router->registerRoute('', ['controller' => 'PollController', 'action' => 'index']);
$router->registerRoute('create', ['controller' => 'PollController', 'action' => 'create']);
$router->registerRoute('post', ['controller' => 'PollController', 'action' => 'post']);

// Start routing
try {
    $router->dispatch($_SERVER['QUERY_STRING']);
} catch (RouterException $e) {
    View::renderView('error.php', ['error_code' => $e->getCode(), 'error_message' => $e->getMessage()]);
}
