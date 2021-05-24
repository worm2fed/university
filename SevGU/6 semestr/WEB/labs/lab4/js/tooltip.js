/*jshint esversion: 6 */
$.wait = function (callback, seconds) {
  return window.setTimeout(callback, seconds * 1000);
}

$(function () {
  $('.with-help').hover(function () {
    var help = $(this).attr('help');
    $(this).data('tipText', help).removeAttr('help');
    $('<p class="tooltip"></p>')
      .text(help)
      .appendTo('body')
      .fadeIn('slow');
  }, function () {
    $(this).attr('help', $(this).data('tipText'));
    $.wait(function () { $('.tooltip').remove() }, 0.5);
  }).mousemove(function (e) {
    var mousex = e.pageX + 20; //Get X coordinates
    var mousey = e.pageY + 10; //Get Y coordinates
    $('.tooltip').css({ top: mousey, left: mousex })
  });
});
