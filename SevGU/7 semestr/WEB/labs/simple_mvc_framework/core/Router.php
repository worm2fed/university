<?php

namespace core;


use core\exceptions\RouterException;

/**
 * Class Router provides request handling. It parses request and then load appropriate controller
 * @package core
 * @author Dave Hollingworth
 * @note this class was found at GitHub (https://github.com/daveh/php-mvc/blob/master/Core/Router.php) and was modified
 * a bit
 */
class Router {
    protected $routes = [];
    protected $args = [];

    /**
     * Register a new route
     *
     * @param string $route
     * @param array $args
     */
    public function registerRoute(string $route, array $args = [])
    : void {
        // Convert the route to a regular expression: escape forward slashes
        $route = preg_replace('/\//', '\\/', $route);
        // Convert variables e.g. {controller}
        $route = preg_replace('/\{([a-z]+)\}/', '(?P<\1>[a-z-]+)', $route);
        // Convert variables with custom regular expressions e.g. {id:\d+}
        $route = preg_replace('/\{([a-z]+):([^\}]+)\}/', '(?P<\1>\2)', $route);
        // Add start and end delimiters, and case insensitive flag
        $route = '/^' . $route . '$/i';
        $this->routes[$route] = $args;
    }

    /**
     * Get routes
     *
     * @return array
     */
    public function getRoutes()
    : array {
        return $this->routes;
    }

    /**
     * Get current route args
     *
     * @return array
     */
    public function getArgs(): array
    {
        return $this->args;
    }

    /**
     * Match route and; set $args if matched
     *
     * @param string $url
     * @return bool
     */
    public function matchRoute(string $url)
    : bool {
        foreach ($this->routes as $route => $args) {
            if (preg_match($route, $url, $matches)) {
                // Get captured group values
                foreach ($matches as $key => $match) {
                    if (is_string($key)) {
                        $args[$key] = $match;
                    }
                }
                $this->args = $args;
                return true;
            }
        }
        return false;
    }

    /**
     * Dispatches route, creates controller object and runs action
     *
     * @param string $url
     * @throws RouterException
     */
    public function dispatch(string $url)
    : void {
        $url = $this->removeQueryStringVariables($url);
        if ($this->matchRoute($url)) {
            $controller = $this->args['controller'];
            $controller = $this->convertToStudlyCaps($controller);
            $controller = $this->getNamespace() . $controller;
            if (class_exists($controller)) {
                $controller_object = new $controller($this->args);
                $action = $this->args['action'];
                $action = $this->convertToCamelCase($action);
                if (preg_match('/action$/i', $action) == 0) {
                    $controller_object->$action();
                } else {
                    throw new RouterException("`Method $action in controller $controller can not be called " . "
                        directly - remove the Action suffix to call this method`", E_USER_ERROR);
                }
            } else {
                throw new RouterException("`Controller class $controller was not found`", E_ERROR);
            }
        } else {
            throw new RouterException('There are no matched routes', 404);
        }
    }

    /**
     * Convert the string with hyphens to StudlyCaps, e.g. post-authors => PostAuthors
     *
     * @param string $string The string to convert
     * @return string
     */
    protected function convertToStudlyCaps(string $string)
    : string {
        return str_replace(' ', '', ucwords(str_replace('-', ' ', $string)));
    }

    /**
     * Convert the string with hyphens to camelCase, e.g. add-new => addNew
     *
     * @param string $string The string to convert
     * @return string
     */
    protected function convertToCamelCase(string $string)
    : string {
        return lcfirst($this->convertToStudlyCaps($string));
    }

    /**
     * Remove the query string variables from the URL (if any). As the full query string is used for the route, any
     * variables at the end will need to be removed before the route is matched to the routing table. For example:
     *
     *   URL                           $_SERVER['QUERY_STRING']  Route
     *   -------------------------------------------------------------------
     *   localhost                     ''                        ''
     *   localhost/?                   ''                        ''
     *   localhost/?page=1             page=1                    ''
     *   localhost/tasks?page=1        tasks&page=1              tasks
     *   localhost/tasks/index         tasks/index               tasks/index
     *   localhost/tasks/index?page=1  tasks/index&page=1        tasks/index
     *
     * A URL of the format localhost/?page (one variable name, no value) won't work however. (NB. The .htaccess file
     * converts the first ? to a & when it's passed through to the $_SERVER variable).
     *
     * @param string $url The full URL
     * @return string The URL with the query string variables removed
     */
    protected function removeQueryStringVariables(string $url)
    : string {
        if ($url != '') {
            $parts = explode('&', $url, 2);
            if (strpos($parts[0], '=') === false) {
                $url = $parts[0];
            } else {
                $url = '';
            }
        }
        return $url;
    }

    /**
     * Get the namespace for the controller class. The namespace defined in the route args will be added if present
     *
     * @return string The request URL
     */
    protected function getNamespace()
    : string {
        // Default namespace
        $namespace = 'controllers\\';
        if (array_key_exists('namespace', $this->args)) {
            $namespace .= $this->args['namespace'] . '\\';
        }
        return $namespace;
    }
}
