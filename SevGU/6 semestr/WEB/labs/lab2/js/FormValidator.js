/*jshint esversion: 6 */

class FormValidator {
  constructor(form, fields) {
    this.form = form;
    this.fields = fields;
  }

  initialize() {
    this.validateOnEntry();
    this.validateOnSubmit();
  }

  validateOnSubmit() {
    this.form.addEventListener('submit', e => {
      e.preventDefault();
      this.fields.forEach(field => {
        const input = document.querySelector(`#${field}`);
        this.validateFields(input);
      });
    });
  }

  validateOnEntry() {
    this.fields.forEach(field => {
      const input = document.querySelector(`#${field}`);

      input.addEventListener('input', event => {
        this.validateFields(input);
      });
    });
  }

  validateFields(field) {
    // Check presence of values
    if (field.value.trim() === "") {
      this.setStatus(field, `${field.previousElementSibling.innerText} cannot be blank`, "error");
    } else {
      this.setStatus(field, null, "success");
    }

    // check for a valid name
    if (field.id === "name") {
      const nameRe = /\S+\s\S+\s\S+/;
      if (nameRe.test(field.value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Please enter valid name", "error");
      }
    }

    // check for a valid email address
    if (field.type === "email") {
      const emailRe = /\S+@\S+\.\S+/;
      if (emailRe.test(field.value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Please enter valid email address", "error");
      }
    }

    // check for a valid phone number
    if (field.id === "phone") {
      const phoneRe = /^((\+3|\+7)+([0-9]){9,11})$/;
      if (phoneRe.test(field.value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Please enter valid phone number", "error");
      }
    }

    // check not contains digits
    if (field.classList.contains("noDigits")) {
      const noDigitsRe = /(?!^\d+$)^.+$/;
      if (noDigitsRe.test(field.value)) {
        this.setStatus(field, null, "success");
      } else {
        this.setStatus(field, "Please enter valid value", "error");
      }
    }
  }

  setStatus(field, message, status) {
    const errorIcon = field.parentElement.querySelector(".error-icon");
    const errorMessage = document.querySelector("#errorMessage");

    if (status === "success") {
      if (errorIcon) { errorIcon.classList.add('hidden'); }
      if (errorMessage) { errorMessage.innerText = ""; }
      field.classList.remove("input-error");
    }

    if (status === "error") {
      field.focus();
      // errorMessage.innerText += message + "\n";
      errorMessage.innerText = "Исправьте ошибки!";
      errorIcon.classList.remove("hidden");
      field.classList.add("input-error");
    }
  }
}
