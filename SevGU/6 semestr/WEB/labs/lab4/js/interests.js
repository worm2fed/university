/*jshint esversion: 6 */

const hobby = ["Психология", "Растения", "Чтение", "Путешествия"];
const books = [
  "Братья Стругацкие - практически всё",
  "Alexander Granin - Functional Design And Architecture"
];

function createList(items, parent = undefined) {
  let list = document.createElement("ul");

  for (let i = 0; i < items.length; i++) {
    let item = document.createElement("li");
    $(item).text(items[i]);
    $(list).append(item);
  }

  if (parent === undefined) return list;

  $(parent).append(list);
}

$(function () {
  recordVisit("interests");

  createList(hobby, $("#hobby"));
  createList(books, $("#books"));
});
