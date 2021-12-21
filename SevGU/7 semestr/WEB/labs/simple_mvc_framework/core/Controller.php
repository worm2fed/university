<?php

namespace core;


use core\exceptions\ControllerException;

/**
 * Class Controller is base class for all controllers
 * @package core
 */
abstract class Controller
{
    protected $route_args = [];

    public function __construct(array $route_args)
    {
        $this->route_args = $route_args;
    }

    /**
     * Before action hook
     *
     * @return bool
     */
    protected function beforeAction()
    : bool {
        return true;
    }

    /**
     * After action hook
     *
     * @return bool
     */
    protected function afterAction()
    : bool {
        return true;
    }

    /**
     * Calls beforeAction and afterAction hooks on action methods
     *
     * @param string $action
     * @param array $args
     * @return void
     * @throws ControllerException
     */
    public function __call(string $action, array $args)
    : void {
        $action = $action . 'Action';
        if (method_exists($this, $action)) {
            if ($this->beforeAction() !== false) {
                call_user_func_array([$this, $action], $args);
                $this->afterAction();
            } else {
                throw new ControllerException("`Something strange was happened due afterAction hook`",
                    E_USER_ERROR);
            }
        } else {
            throw new ControllerException("`Action $action was not found in controller " .get_class($this). "`",
                E_ERROR);
        }
    }
}