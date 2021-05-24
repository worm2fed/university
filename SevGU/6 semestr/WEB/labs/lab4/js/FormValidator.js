/*jshint esversion: 6 */

class FormValidator {
  constructor(form, fields) {
    this.form = form;
    this.fields = fields;
  }

  initialize() {
    this.validateOnEntry();
    this.validateOnFocusOut();
    this.validateOnSubmit();
  }

  validateOnEntry() {
    this.fields.forEach(field => {
      const input = $(`#${field}`);

      $(input).on("input", () => {
        this.validateFields(input);
      });
    });
  }

  validateOnFocusOut() {
    this.fields.forEach(field => {
      const input = $(`#${field}`);

      $(input).on("focusout", () => {
        this.validateFields(input);
      });
    });
  }

  validateOnSubmit() {
    $(this.form).on('submit', e => {
      e.preventDefault();
      this.fields.forEach(field => {
        const input = $(`#${field}`);
        this.validateFields(input);
      });
    });
  }

  allValid() {
    return $(this.form).find(".input-error").length === 0;
  }

  validateFields(field) {
    let value = $(field).val();

    // check not contains digits
    if ($(field).hasClass("noDigits")) {
      const noDigitsRe = /(?!^\d+$)^.+$/;
      if (noDigitsRe.test(value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Запрещено вводить цифры", "error");
      }
    }

    // Check presence of values
    if ($.trim(value) === "" || value === null) {
      this.setStatus(field, `${$(field).prev().text()} не может быть пустым`, "error");
    } else {
      this.setStatus(field, null, "success");
    }

    // check for a valid name
    if ($(field).attr("id") === "name") {
      const nameRe = /\S+\s\S+\s\S+/;
      if (nameRe.test(value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Имя должно состоять из трёх слов", "error");
      }
    }

    // check for a valid email address
    if ($(field).attr("type") === "email") {
      const emailRe = /\S+@\S+\.\S+/;
      if (emailRe.test(value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Email адрес содержит ошибку", "error");
      }
    }

    // check for a valid phone number
    if ($(field).attr("id") === "phone") {
      const phoneRe = /^((\+3|\+7)+([0-9]){9,11})$/;
      if (phoneRe.test(value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Номер телефона должен начинаться с +3 или +7 и может содержать от 9 до 11 цифр", "error");
      }
    }
  }

  setStatus(field, message, status) {
    const errorIcon = $(field).parent(".error-icon");
    const errorMessage = $("#errorMessage");

    if (status === "success") {
      if (errorIcon) { $(errorIcon).addClass('hidden'); }
      if (errorMessage) { $(errorMessage).text(""); }
      $(field).removeClass("input-error");
    }

    if (status === "error") {
      $(errorMessage).text(message);
      $(errorIcon).removeClass("hidden");
      $(field).addClass("input-error");
    }

    let submit = $(this.form).find("button[type='submit']")
    if (this.allValid()) {
      $(submit).removeAttr("disabled");
    } else {
      $(submit).attr("disabled", true);
    }
  }
}
