'use strict';

const server_host = "localhost";
const server_port = "3000";

const express = require("express");
const app = express();

require("./config/enviroment.js")(app, express);

const mysql = require("mysql2/promise");
const conn = {
  host: "localhost",
  port: 3306,
  database: "mir_lab6",
  user: "mir",
  password: "mir",
};

app.get("/", runAsyncWrapper(async (req, res) => {
  var master = await mysql.createConnection(conn);

  let title = Math.random().toString(36).replace(/[^a-z]+/g, '');
  let posts_count = (Math.random() * 1000).toFixed(0);

  await master.execute(
    'INSERT INTO blogs SET title = ?, posts_count = ?',
    [title, posts_count]
  );

  res.render("master", {
    title: `Writer to master at ${conn.host}:${conn.port}`,
    text: `New entry has been added to the master database with the following:
           title '${title}' and posts_count '${posts_count}'`,
  });
}));

app.listen(server_port, server_host, () => {
  console.log(
    `Master writer running at http://${server_host}:${server_port}/, press Ctrl-C to exit`
  );
});

function runAsyncWrapper(callback) {
  return function (req, res, next) {
    callback(req, res, next).catch(next);
  }
}

module.exports = app;
