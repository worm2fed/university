<div class="title-block">
    <h3 class="title"> Add new poll
        <span class="sparkline bar" data-type="bar"></span>
    </h3>
</div>

<form method="post" action="/?post">
    <div class="card card-block">
        <div class="form-group row">
            <label class="col-sm-2 form-control-label text-xs-right"> Title: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control boxed" name="title" id="title" required="">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 form-control-label text-xs-right"> Questions (json): </label>
            <div class="col-sm-10">
                <textarea class=" form-control" name="questions" id="questions" style="height: 350px" required=""></textarea>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-10 col-sm-offset-2">
                <a href="/" class="btn btn-secondary"> Cancel </a>
                <button type="submit" class="btn btn-warning"> Submit </button>
            </div>
        </div>
    </div>
</form>

