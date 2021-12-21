<?php

namespace core;


use Config;
use core\exceptions\ViewException;

/**
 * Class View
 * @package core
 */
class View
{
    /**
     * Render a view
     * If you want to use template, it must contain code `<?php require $__view; ?>` to render specified view
     *
     * @param string $view
     * @param array $data
     * @param string|null $template
     * @return void
     * @throws ViewException
     */
    public static function renderView(string $view, array $data = [], string $template = null)
    : void {
        // Extract data from array
        extract($data, EXTR_SKIP);
        $__view = Config::ROOT_DIR . "/views/$view";
        // Check whether view file exists
        if (!file_exists($__view)) {
            throw new ViewException("`View $__view was not found`", E_ERROR);
        }
        // If template doesn't specified - render view, otherwise render template with view
        if (is_null($template)) {
            require $__view;
        } else {
            $__template = Config::ROOT_DIR . "/templates/$template";
            if (file_exists($__template)) {
                require $__template;
            } else {
                throw new ViewException("`Template $__template was not found`", E_ERROR);
            }
        }
    }
}