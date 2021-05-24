/*jshint esversion: 6 */

const months = [
  'Январь',
  'Февраль',
  'Март',
  'Апрель',
  'Май',
  'Июнь',
  'Июль',
  'Август',
  'Сентябрь',
  'Октябрь',
  'Ноябрь',
  'Декабрь',
];

function clock() {
  let date = new Date();
  let year = date.getFullYear();
  let month = months[date.getMonth()];
  let day = date.getDate();
  let time = date.toLocaleTimeString();

  document.querySelector('.clock').innerHTML =
    `${time}, ${day} ${month} ${year}`;
}

document.addEventListener("DOMContentLoaded", () => {
  setInterval(clock, 1000);
});
