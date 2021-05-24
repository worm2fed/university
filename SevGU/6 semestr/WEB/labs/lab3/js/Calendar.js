/*jshint esversion: 6 */

class Calendar {
  days() {
    return ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  }

  months() {
    return ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
  }

  constructor(startYear, input) {
    this.today = new Date();
    this.currentDay = this.today.getDate();
    this.currentMonth = this.today.getMonth();
    this.currentYear = this.today.getFullYear();
    this.startYear = startYear;
    this.input = input;

    this.calendar = document.querySelector(".calendar");
  }

  init() {
    this.generateControls();
    this.generateContent();
  }

  generateControls() {
    let container = document.createElement("div");
    container.className = "calendar-controls";

    let prev = document.createElement("button");
    prev.type = "button";
    prev.className = "previous";
    prev.innerText = "ᐊ";
    prev.addEventListener("click", () => this.previous());
    container.appendChild(prev);

    let monthSelect = document.createElement("select");
    monthSelect.className = "monthSelect";
    this.generateMonthRange(monthSelect);
    monthSelect.addEventListener("change", () => this.jump());
    container.appendChild(monthSelect);

    let yearSelect = document.createElement("select");
    yearSelect.className = "yearSelect";
    this.generateYearRange(yearSelect, this.startYear, this.currentYear);
    yearSelect.addEventListener("change", () => this.jump());
    container.appendChild(yearSelect);

    let next = document.createElement("button");
    next.type = "button";
    next.className = "next";
    next.innerText = "ᐅ";
    next.addEventListener("click", () => this.next());
    container.appendChild(next);

    this.calendar.appendChild(container);
  }

  generateMonthRange(parent) {
    for (let i = 0; i < this.months().length; i++) {
      let option = document.createElement("option");
      option.value = i;
      option.innerText = this.months()[i];

      parent.appendChild(option);
    }
  }

  generateYearRange(parent, start, end) {
    for (let year = start; year <= end; year++) {
      let option = document.createElement("option");
      option.value = year;
      option.innerText = year;

      parent.appendChild(option);
    }
  }

  next() {
    this.currentYear = this.currentMonth === 11 ?
      this.currentYear + 1 : this.currentYear;
    this.currentMonth = (this.currentMonth + 1) % 12;
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  previous() {
    this.currentYear = this.currentMonth === 0 ?
      this.currentYear - 1 : this.currentYear;
    this.currentMonth = this.currentMonth === 0 ?
      11 : this.currentMonth - 1;
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  jump() {
    if (this.calendar.querySelector(".calendar-controls") !== null) {
      let month = parseInt(this.calendar.querySelector(".monthSelect").value);
      let year = parseInt(this.calendar.querySelector(".yearSelect").value);

      this.generateCalendar(month, year);
    }
  }

  generateContent() {
    this.generateWeekDays();
    this.generateBody();
    this.generateCalendar(this.currentMonth, this.currentYear);

    this.input.addEventListener("click", (e) => {
      this.calendar.style.display = "block";
    });
    this.calendar.querySelector(".calendar-body")
      .addEventListener("click", (e) => {
        this.calendar.style.display = "none";
      });
  }

  generateWeekDays() {
    let container = document.createElement("div");
    container.className = "week-days";

    for (let day in this.days()) {
      if (this.days().hasOwnProperty(day)) {
        let block = document.createElement("div");
        block.innerText = this.days()[day];
        container.appendChild(block);
      }
    }

    this.calendar.appendChild(container);
  }

  generateBody() {
    let container = document.createElement("div");
    container.className = "calendar-body";
    this.calendar.appendChild(container);
  }

  generateCalendar(month, year) {
    if (this.calendar.querySelector(".calendar-controls") !== null) {
      this.calendar.querySelector(".monthSelect").value = month;
      this.calendar.querySelector(".yearSelect").value = year;
    }

    let body = this.calendar.querySelector(".calendar-body");
    body.innerHTML = "";

    let day = 1;
    let firstDay = (new Date(year, month)).getDay();

    for (let i = 0; i < 6; i++) {
      let row = document.createElement("div");
      row.className = "date-raw";

      for (let j = 0; j < 7; j++) {
        let cell = document.createElement("div");
        cell.className = "date-picker";

        if (
          (i !== 0 || j >= firstDay) &&
          day <= this.daysInMonth(month, year)
        ) {
          cell.innerText = day;
          cell.addEventListener("click", (e) => this.select(e));

          if (this.isCurrentDay(day, month, year)) {
            cell.classList.add("selected");
          }
          day++;
        }

        row.appendChild(cell);
      }

      body.appendChild(row);
    }
  }

  select(event) {
    this.calendar.querySelectorAll('.selected').forEach(function (e) {
      e.classList.remove('selected');
    });

    let element = event.target;
    element.classList.add("selected");
    this.currentDay = element.innerText;

    this.input.value = `${this.currentMonth}/${this.currentDay}/${this.currentYear}`;
  }

  isCurrentDay(day, month, year) {
    return day === this.currentDay &&
      month === this.currentMonth &&
      year === this.currentYear;
  }

  daysInMonth(iMonth, iYear) {
    return 32 - new Date(iYear, iMonth, 32).getDate();
  }
}
