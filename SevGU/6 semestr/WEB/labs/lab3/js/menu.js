/*jshint esversion: 6 */

function isActiveOrDropdown(el) {
  return el.classList.contains("dropdown")
    ? true
    : el.childNodes[0].classList.contains("active");
}

function activate(event) {
  if (!isActiveOrDropdown(event.target)) {
    event.target.classList.add("border-bottom");
  }
}

function deactivate(event) {
  if (!isActiveOrDropdown(event.target)) {
    event.target.classList.remove("border-bottom");
  }
}

function show(event) {
  let menu = event.target.parentNode.querySelector(".dropdown-content");
  menu.style.display = "block";
}

function hide(event, dropdown) {
  let menu = document.querySelector(".dropdown-content");
  let targetElement = event.target;

  do {
    if (targetElement == dropdown) return;
    targetElement = targetElement.parentNode;
  } while (targetElement);

  menu.style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
  const menu = document.querySelector('.nav');
  menu.childNodes.forEach((item) => {
    item.addEventListener("mouseenter", (e) => activate(e), false);
    item.addEventListener("mouseleave", (e) => deactivate(e), false);
  });

  const dropdown = document.querySelector(".dropdown");
  dropdown.addEventListener("click", (e) => show(e), false);
  document.addEventListener("click", (e) => hide(e, dropdown), false);
});
