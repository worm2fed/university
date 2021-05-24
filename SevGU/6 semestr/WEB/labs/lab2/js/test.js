/*jshint esversion: 6 */

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector('.test');
  const fields = ["name", "group", "theory", "de-morgan"];

  const validator = new FormValidator(form, fields);
  validator.initialize();
});
