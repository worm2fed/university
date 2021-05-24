/*jshint esversion: 6 */

$(function () {
  recordVisit("contact");

  const validator = new FormValidator(
    $(".contact"),
    ["name", "age", "email", "phone", "body"]
  );
  validator.initialize();

  const calendar = new Calendar(1970, $("#birthday"));
  calendar.init();
});
