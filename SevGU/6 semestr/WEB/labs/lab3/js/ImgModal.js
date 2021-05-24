/*jshint esversion: 6 */

class ImgModal {
  constructor(file, label, id) {
    this.file = file;
    this.label = label;
    this.id = id;
  }

  generate() {
    let modal = document.createElement("div");
    modal.className = "modal";
    modal.id = "img" + this.id;

    let modalContent = document.createElement("div");
    modalContent.className = "modal-content";

    let close = document.createElement("span");
    close.className = "close";
    close.innerHTML = "&times;";
    close.onclick = function () {
      modal.style.display = "none";
    }

    let img = document.createElement("img");
    img.alt = this.label;
    img.src = this.file;

    modalContent.appendChild(close);
    modalContent.appendChild(img);
    modal.appendChild(modalContent);

    return modal;
  }
}
