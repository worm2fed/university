<?php

namespace controllers;


use core\Controller;
use core\SystemTools;
use core\View;
use models\PollModel;

class PollController extends Controller
{
    /**
     * Show the index page - page with all polls
     */
    public function indexAction()
    : void {
        $data['polls'] = PollModel::find();
        $data['page_class'] = 'items-list-page';
        View::renderView('poll/index.php', $data, 'main.php');
    }

    /**
     * Create a poll
     */
    public function createAction()
    : void {
        $data['page_class'] = 'items-editor-page';
        View::renderView('poll/create.php', $data, 'main.php');
    }

    /**
     * Post a poll
     */
    public function postAction()
    : void {
        $poll = new PollModel();
        unset($_REQUEST['post']);
        $poll->load($_POST);
        $poll->create();
        SystemTools::redirect('/');
    }
}
