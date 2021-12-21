/***********************************************
 *        Animation Settings
 ***********************************************/
function animate(options) {
  var animationName = "animated " + options.name;
  var animationEnd = "webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend";
  $(options.selector)
    .addClass(animationName)
    .one(animationEnd,
      function () {
        $(this).removeClass(animationName);
      }
    );
}

$(function () {
  var $itemActions = $(".item-actions-dropdown");

  $(document).on('click', function (e) {
    if (!$(e.target).closest('.item-actions-dropdown').length) {
      $itemActions.removeClass('active');
    }
  });

  $('.item-actions-toggle-btn').on('click', function (e) {
    e.preventDefault();
    var $thisActionList = $(this).closest('.item-actions-dropdown');
    $itemActions.not($thisActionList).removeClass('active');
    $thisActionList.toggleClass('active');
  });
});

/***********************************************
 *        Editor Settings
 ***********************************************/
$(function () {
  $(".wyswyg").each(function () {
    var $toolbar = $(this).find(".toolbar");
    var $editor = $(this).find(".editor");

    var editor = new Quill($editor.get(0), {
      theme: 'snow'
    });

    editor.addModule('toolbar', {
      container: $toolbar.get(0)     // Selector for toolbar container
    });
  });
});
