/*jshint esversion: 6 */

/*
<div id="slider">
  <a href="#" class="control_next">></a>
  <a href="#" class="control_prev"><</a>
  <ul>
      <li>SLIDE 1</li>
      <li style="background: #aaa;">SLIDE 2</li>
      <li>SLIDE 3</li>
      <li style="background: #aaa;">SLIDE 4</li>
  </ul>
</div>

<div class="slider_option">
  <input type="checkbox" id="checkbox">
  <label for="checkbox">Autoplay Slider</label>
</div>
*/

function generateSlider(files, labels, insertAfter) {
  let modal = document.createElement("div");
  $(modal).addClass("modal").attr("id", "modal");

  let close = document.createElement("span");
  $(close).addClass("close").html("&times;")
    .click(function () { $(modal).slideUp("fast"); });

  let next = document.createElement("a")
  $(next).attr("href", "#").addClass("control_next").text(">");
  let prev = document.createElement("a")
  $(prev).attr("href", "#").addClass("control_prev").text("<");

  let imgContainer = document.createElement("ul");
  for (let i = 0; i < files.length; i++) {
    let li = document.createElement("li");
    $(li).append(buildPhotoBlock(files[i], labels[i]));

    $(imgContainer).append(li);
  }

  let slider = document.createElement("div");
  $(slider).addClass("slider")
    .attr("id", "slider")
    .append(next)
    .append(prev)
    .append(imgContainer);

  let modalContent = document.createElement("div");
  $(modalContent).addClass("modal-content")
    .append(close)
    .append(slider);

  $(modal).append(modalContent).insertAfter(insertAfter);
}

function moveLeft(slideWidth) {
  $("#slider ul").animate({
    left: + slideWidth
  }, 200, function () {
    $("#slider ul li:last-child").prependTo("#slider ul");
    $("#slider ul").css("left", "");
  });
};

function moveRight(slideWidth) {
  $("#slider ul").animate({
    left: - slideWidth
  }, 200, function () {
    $("#slider ul li:first-child").appendTo("#slider ul");
    $("#slider ul").css("left", "");
  });
};

$(function () {
  generateSlider(photos, titles, "#album-anchor");
  $('.photo').children().each(function () {
    $(this).click(function () { $("#modal").slideToggle("fast"); })
  });

  const slideCount = $("#slider ul li").length;
  const slideWidth = $("#slider ul li").width();
  const slideHeight = $("#slider ul li").height();
  const sliderUlWidth = slideCount * slideWidth;

  $('#slider').css({ width: slideWidth, height: slideHeight });
  $('#slider ul').css({ width: sliderUlWidth, marginLeft: - slideWidth });
  $('#slider ul li:last-child').prependTo('#slider ul');

  $('a.control_prev').click(function () { moveLeft(slideWidth); });
  $('a.control_next').click(function () { moveRight(slideWidth); });
});
