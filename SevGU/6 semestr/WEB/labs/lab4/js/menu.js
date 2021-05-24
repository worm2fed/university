/*jshint esversion: 6 */

function isActiveOrDropdown(element) {
  return $(element).hasClass("dropdown") ?
    true : ($(element).children(".active").length !== 0);
}

function activate(element) {
  if (!isActiveOrDropdown(element)) {
    $(element).addClass("border-bottom");
  }
}

function deactivate(element) {
  if (!isActiveOrDropdown(element)) {
    $(element).removeClass("border-bottom");
  }
}

$(function () {
  $('.nav').children().each(function () {
    $(this)
      .on("mouseenter", function () { activate($(this)) })
      .on("mouseleave", function () { deactivate($(this)) });
  });

  $(".dropdown").click(function () {
    $(this).find(".dropdown-content").slideToggle("fast");
  });

  $(document).on("click", function (event) {
    if (!$(event.target).closest(".dropdown").length) {
      $(".dropdown-content").slideUp("fast");
    }
  });
});
