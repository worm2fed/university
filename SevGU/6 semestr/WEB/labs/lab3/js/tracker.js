/*jshint esversion: 6 */

const pages = {
  "index": "Главная",
  "about": "Обо мне",
  "interests": "Мои интересы",
  "study": "Учёба",
  "photo": "Фотоальбом",
  "contact": "Контакт",
  "test": "Тест по Дискретной математике",
  "history": "История просмотра",
};

function setCookie(name, value, expire) {
  let date = new Date();
  date.setTime(date.getTime() + expire * 24 * 60 * 60 * 1000);

  let expires = "expires=" + date.toUTCString();
  document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let cookies = decodedCookie.split(';');

  for (let i = 0; i < cookies.length; i++) {
    let c = cookies[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return null;
}

function recordVisit(page) {
  if (!(page in pages)) return;

  let sessionValue = sessionStorage.getItem(page);
  if (sessionValue === null) {
    sessionStorage.setItem(page, 1);
  } else {
    sessionStorage.setItem(page, parseInt(sessionValue) + 1);
  }

  let cookieValue = getCookie(page);
  if (cookieValue === null) {
    setCookie(page, 1, 1);
  } else {
    setCookie(page, parseInt(cookieValue) + 1, 1);
  }
}

function buildTbody(tbody, all) {
  for (const [page, titleValue] of Object.entries(pages)) {
    let title = document.createElement("td");
    title.className = "title";
    title.innerText = titleValue;

    let counterValue = all ? getCookie(page) : sessionStorage.getItem(page);
    let counter = document.createElement("td");
    counter.innerText = counterValue === null ? 0 : counterValue;

    let tr = document.createElement("tr");
    tr.appendChild(title);
    tr.appendChild(counter);

    tbody.appendChild(tr);
  }
}

function displaySessionVisitsRecords(tbody) {
  buildTbody(tbody, false);
}

function displayAllVisitsRecords(tbody) {
  buildTbody(tbody, true);
}
