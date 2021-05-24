/*jshint esversion: 6 */

function buildPhotoBlock(file, label) {
  let span = document.createElement("span");
  $(span).text(label);

  let img = document.createElement("img");
  $(img).attr("src", file).attr("alt", label);

  let block = document.createElement("div");
  $(block)
    .addClass("photo")
    .attr("data-name", label)
    .append(img)
    .append(span);

  return block;
}

function createAlbum(files, labels, inRaw) {
  let table = document.createElement("table");

  for (let i = 0, index = 0; i < Math.round(files.length / inRaw); i++) {
    let row = table.insertRow();

    for (let j = 0; j < inRaw; j++, index++) {
      let cell = row.insertCell();

      if (files.length === index) continue;
      let data = buildPhotoBlock(files[index], labels[index]);
      $(cell).append(data);
    }

    $(table).append(row);
  }

  return table;
}

const photos = [
  "https://rastenievod.com/wp-content/uploads/2016/12/3-47-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/2-46-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/17-3-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/1-45-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/09/5-63-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/07/1-69-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/05/1-95-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2019/09/1-1-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/1-39-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/1-38-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/1-37-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/1-36-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/12/1-35-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/10/1-35-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/09/1-73-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/09/1-54-325x170.jpg",
  "https://rastenievod.com/wp-content/uploads/2016/06/2-76-325x170.jpg",
];

const titles = [
  "Кактус цереус",
  "Ферокактус",
  "Рипсалидопсис",
  "Переския",
  "Астрофитум",
  "Эпифиллум",
  "Опунция",
  "Гилоцереус",
  "Ребуция",
  "Гимнокалициум",
  "Эхинокактус",
  "Эхинопсис",
  "Хатиора",
  "Рипсалис",
  "Маммиллярия",
  "Декабрист (Шлюмбергера)",
  "Стапелия",
];

$(function () {
  recordVisit("photo");

  const album = createAlbum(photos, titles, 6);
  $(album).insertAfter("#album-anchor");
});
