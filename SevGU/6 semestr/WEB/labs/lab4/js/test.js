/*jshint esversion: 6 */

$(function () {
  recordVisit("test");

  const validator = new FormValidator(
    $('.test'),
    ["name", "group", "theory", "de-morgan"]);
  validator.initialize();
});
