<div class="title-block">
    <h3 class="title"> Add new task
        <span class="sparkline bar" data-type="bar"></span>
    </h3>
</div>

<form method="post" action="/?post">
    <div class="card card-block">
        <div class="form-group row">
            <label class="col-sm-2 form-control-label text-xs-right"> Name: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control boxed" name="username" id="name" required="">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 form-control-label text-xs-right"> Email: </label>
            <div class="col-sm-10">
                <input type="email" class="form-control boxed" name="email" id="email" required="" <?php if (!empty($user_email)): ?> readonly <?php endif; ?> value="<?= $user_email ?>">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 form-control-label text-xs-right"> Text: </label>
            <div class="col-sm-10">
                <textarea class=" form-control wyswyg" name="text" id="text" style="resize: none;" required=""></textarea>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 form-control-label text-xs-right"> Image: </label>
            <div class="col-sm-10">
                <div class="upload-container">
                    <div class="col-xs-2">
                        <button type="button" id="uploadBtn" class="btn btn-large btn-primary-outline">Choose File</button>
                        <input type="hidden" name="image" id="image_name" value="">
                    </div>
                    <div class="col-xs-10">
                        <div id="progressOuter" class="progress progress-striped active" style="display:none;">
                            <div id="progressBar" class="progress-bar progress-bar-success"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" style="padding-top:10px;">
                    <div class="col-xs-10">
                        <div id="msgBox"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-10 col-sm-offset-2">
                <a href="/" class="btn btn-secondary"> Cancel </a>
                <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#preview-modal" onclick="preview()"> Preview </a>
                <button type="submit" class="btn btn-warning"> Submit </button>
            </div>
        </div>
    </div>
</form>

<!-- Start preview modal -->
<div class="modal fade" id="preview-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"> Preview </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <div class="form-group row">
                    <label class="col-sm-2 form-control-label text-xs-right"> Name: </label>
                    <div class="col-sm-10" id="name-preview"></div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2 form-control-label text-xs-right"> Email: </label>
                    <div class="col-sm-10" id="email-preview"></div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2 form-control-label text-xs-right"> Text: </label>
                    <div class="col-sm-10" id="text-preview"></div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2 form-control-label text-xs-right"> Image: </label>
                    <div class="col-sm-10">
                        <img src="" id="image-preview" style="max-width: 100%">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="reset" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- End preview modal -->

<script src="/static/js/SimpleAjaxUploader.js"></script>
<script>
    function preview() {
        $('#name-preview').text(''+$('#name').val());
        $('#email-preview').text(''+$('#email').val());
        $('#text-preview').text(''+$('#text').val());
        $('#image-preview').attr('src', '/static/images/'+$('#image_name').val());
    }
    function escapeTags( str ) {
        return String( str )
            .replace( /&/g, '&amp;' )
            .replace( /"/g, '&quot;' )
            .replace( /'/g, '&#39;' )
            .replace( /</g, '&lt;' )
            .replace( />/g, '&gt;' );
    }
    window.onload = function() {
        var btn = document.getElementById('uploadBtn'),
            progressBar = document.getElementById('progressBar'),
            progressOuter = document.getElementById('progressOuter'),
            msgBox = document.getElementById('msgBox');
        var uploader = new ss.SimpleUpload({
            button: btn,
            url: '/?image',
            name: 'image',
            multipart: true,
            hoverClass: 'hover',
            focusClass: 'focus',
            responseType: 'json',
            startXHR: function() {
                progressOuter.style.display = 'block'; // make progress bar visible
                this.setProgressBar( progressBar );
            },
            onSubmit: function() {
                msgBox.innerHTML = ''; // empty the message box
                btn.innerHTML = 'Uploading...'; // change button text to "Uploading..."
            },
            onComplete: function( filename, response ) {
                btn.innerHTML = 'Choose Another File';
                progressOuter.style.display = 'none'; // hide progress bar when upload is completed
                if ( !response ) {
                    msgBox.innerHTML = 'Unable to upload file';
                    return;
                }
                if ( response.success === true ) {
                    $('#image_name').val(response.image_name);
                    msgBox.innerHTML = '<strong>' + escapeTags( filename ) + '</strong>' + ' successfully uploaded.';
                } else {
                    $('#image_name').val('');
                    if ( response.msg )  {
                        msgBox.innerHTML = escapeTags( response.msg );
                    } else {
                        msgBox.innerHTML = 'An error occurred and the upload failed.';
                    }
                }
            },
            onError: function() {
                progressOuter.style.display = 'none';
                msgBox.innerHTML = 'Unable to upload file';
            }
        });
    };
</script>