<div class="title-block">
    <div class="row">
        <div class="col-md-6">
            <h3 class="title"> Polls
                <a href="/?create" class="btn btn-primary btn-sm rounded-s"> Add New </a>
            </h3>
            <p class="title-description"> List of polls... </p>
        </div>
    </div>
</div>

<div class="card items">
    <ul class="item-list striped">
        <li class="item item-list-header">
            <div class="item-row">
                <div class="item-col item-col-header item-col-title">
                    <div class="no-overflow"><span>Title</span></div>
                </div>

                <div class="item-col item-col-header item-col-title">
                    <div class="no-overflow"><span>Questions</span></div>
                </div>

                <div class="item-col item-col-header item-col-date">
                    <div><span>Created</span></div>
                </div>
            </div>
        </li>

        <?php foreach ($polls as $poll): ?>
            <li class="item">
                <div class="item-row">
                    <div class="item-col fixed pull-left item-col-title">
                        <div> <?= $poll->title ?> </div>
                    </div>

                    <div class="item-col fixed pull-left item-col-title">
                        <div> <?= $poll->questions ?> </div>
                    </div>

                    <div class="item-col item-col-date">
                        <div class="no-overflow"> <?= $poll->created_at ?> </div>
                    </div>
                </div>
            </li>
        <?php endforeach; ?>

    </ul>
</div>
