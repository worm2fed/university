/*jshint esversion: 6 */

document.addEventListener("DOMContentLoaded", () => {
  recordVisit("contact");

  const form = document.querySelector('.contact');
  const fields = ["name", "age", "email", "phone", "body"];

  const validator = new FormValidator(form, fields);
  validator.initialize();

  const birthdayFiend = document.querySelector("#birthday");
  const calendar = new Calendar(1970, birthdayFiend);
  calendar.init();
});
