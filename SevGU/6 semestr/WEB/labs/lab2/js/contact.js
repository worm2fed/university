/*jshint esversion: 6 */

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector('.contact');
  const fields = ["name", "age", "email", "phone", "body"];

  const validator = new FormValidator(form, fields);
  validator.initialize();
});
