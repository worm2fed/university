module.exports = function (app, express) {
  var path = require("path");

  app.set("view engine", "pug");
  app.set("views", path.join(__dirname, "../views"));

  app.use(express.json());
  app.use(express.urlencoded({ extended: false }));
  app.use(express.static(path.join(__dirname, "../public")));
};
