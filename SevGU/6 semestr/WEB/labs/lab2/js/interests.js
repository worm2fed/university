/*jshint esversion: 6 */

function createList(items, parent = undefined) {
  let list = document.createElement("ul");

  for (let i = 0; i < items.length; i++) {
    var item = document.createElement("li");
    item.innerText = items[i];
    list.appendChild(item);
  }

  if (parent === undefined) return list;

  parent.appendChild(list);
}

const hobby = ["Психология", "Растения", "Чтение", "Путешествия"];
const books = [
  "Братья Стругацкие - практически всё",
  "Alexander Granin - Functional Design And Architecture"
];

document.addEventListener("DOMContentLoaded", () => {
  createList(hobby, document.getElementById("hobby"));
  createList(books, document.getElementById("books"));
});
