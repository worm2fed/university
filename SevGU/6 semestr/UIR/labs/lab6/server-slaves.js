'use strict';

const server_host = "localhost";
const server_port = "3001";

const express = require("express");
const app = express();

require("./config/enviroment.js")(app, express);

const mysql = require("mysql2/promise");
const conn = [
  {
    host: "localhost",
    port: 3306,
    database: "mir_lab6",
    user: "mir",
    password: "mir",
  },
  {
    host: "localhost",
    port: 3310,
    database: "mir_lab6",
    user: "mir",
    password: "mir",
  },
  {
    host: "localhost",
    port: 3311,
    database: "mir_lab6",
    user: "mir",
    password: "mir",
  },
  {
    host: "localhost",
    port: 3312,
    database: "mir_lab6",
    user: "mir",
    password: "mir",
  },
];

app.get("/", runAsyncWrapper(async (req, res) => {
  var master = await mysql.createConnection(conn[0]);
  var slave1 = await mysql.createConnection(conn[1]);
  var slave2 = await mysql.createConnection(conn[2]);
  var slave3 = await mysql.createConnection(conn[3]);

  let command = 'SELECT * FROM blogs ORDER BY id';
  let rows = [];

  [rows[0]] = await master.execute(command);
  [rows[1]] = await slave1.execute(command);
  [rows[2]] = await slave2.execute(command);
  [rows[3]] = await slave3.execute(command);

  res.render("slaves", {
    title: `Database content from all servers`,
    names: ['master', 'slave1', 'slave2', 'slave3'],
    conn: conn,
    rows: rows,
  });
}));

app.listen(server_port, server_host, () => {
  console.log(
    `Slave reader running at http://${server_host}:${server_port}/, press Ctrl-C to exit`
  );
});

function runAsyncWrapper(callback) {
  return function (req, res, next) {
    callback(req, res, next).catch(next);
  }
}

module.exports = app;
