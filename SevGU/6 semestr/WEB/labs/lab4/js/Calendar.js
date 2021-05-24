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

    this.calendar = $(".calendar");
  }

  init() {
    this.generateControls();
    this.generateContent();
  }

  generateControls() {
    let prev = document.createElement("button");
    $(prev)
      .attr("type", "button")
      .addClass("previous")
      .text("ᐊ")
      .on("click", () => this.previous());

    let monthSelect = document.createElement("select");
    $(monthSelect).addClass("monthSelect").on("change", () => this.jump());
    this.generateMonthRange(monthSelect);

    let yearSelect = document.createElement("select");
    $(yearSelect).addClass("yearSelect").on("change", () => this.jump());
    this.generateYearRange(yearSelect, this.startYear, this.currentYear);

    let next = document.createElement("button");
    $(next)
      .attr("type", "button")
      .addClass("next")
      .text("ᐅ")
      .on("click", () => this.next());

    let container = document.createElement("div");
    $(container)
      .addClass("calendar-controls")
      .append(prev)
      .append(monthSelect)
      .append(yearSelect)
      .append(next);

    $(this.calendar).append(container);
  }

  generateMonthRange(parent) {
    for (let i = 0; i < this.months().length; i++) {
      let option = document.createElement("option");
      $(option).text(this.months()[i]).val(i);
      $(parent).append(option);
    }
  }

  generateYearRange(parent, start, end) {
    for (let year = start; year <= end; year++) {
      let option = document.createElement("option");
      $(option).text(year).val(year);
      $(parent).append(option);
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
    if ($(this.calendar).find(".calendar-controls").length !== 0) {
      let month = parseInt($(this.calendar).find(".monthSelect").val());
      let year = parseInt($(this.calendar).find(".yearSelect").val());

      this.generateCalendar(month, year);
    }
  }

  generateContent() {
    this.generateWeekDays();
    this.generateBody();
    this.generateCalendar(this.currentMonth, this.currentYear);

    $(this.input).click(() => {
      $(this.calendar).slideToggle("fast");
    });
    $(this.calendar).find(".calendar-body").click(() => {
      $(this.calendar).slideUp("fast");
    });
  }

  generateWeekDays() {
    let container = document.createElement("div");
    $(container).addClass("week-days");

    for (let day in this.days()) {
      if (this.days().hasOwnProperty(day)) {
        let block = document.createElement("div");
        $(block).text(this.days()[day]);
        $(container).append(block);
      }
    }

    $(this.calendar).append(container);
  }

  generateBody() {
    let container = document.createElement("div");
    $(container).addClass("calendar-body")
    $(this.calendar).append(container);
  }

  generateCalendar(month, year) {
    if ($(this.calendar).find(".calendar-controls").length !== 0) {
      $(this.calendar).find(".monthSelect").val(month);
      $(this.calendar).find(".yearSelect").val(year);
    }

    let body = $(this.calendar).find(".calendar-body");
    $(body).html("");

    let day = 1;
    let firstDay = (new Date(year, month)).getDay();

    for (let i = 0; i < 6; i++) {
      let row = document.createElement("div");
      $(row).addClass("date-raw");

      for (let j = 0; j < 7; j++) {
        let cell = document.createElement("div");
        $(cell).addClass("date-picker");

        if (
          (i !== 0 || j >= firstDay) &&
          day <= this.daysInMonth(month, year)
        ) {
          $(cell).text(day).on("click", (e) => this.select(e));

          if (this.isCurrentDay(day, month, year)) {
            $(cell).addClass("selected");
          }
          day++;
        }

        $(row).append(cell);
      }

      $(body).append(row);
    }
  }

  select(event) {
    $(this.calendar).find('.selected').removeClass('selected');

    let element = event.target;
    $(element).addClass("selected");
    this.currentDay = $(element).text();

    $(this.input)
      .val(`${this.currentMonth}/${this.currentDay}/${this.currentYear}`);
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
