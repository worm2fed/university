'use strict';

const server_host = "localhost";
const server_port = "3000";

const express = require("express");
const app = express();

require("./config/enviroment.js")(app, express);

const n_servers = 4
const n_records = 5;
const server_names = ['db1', 'db2', 'db3', 'db4'];

const mysql = require("mysql2/promise");
const conn = [
  {
    host: "localhost",
    port: 3301,
    database: "mir_lab7",
    user: "mir",
    password: "mir",
  },
  {
    host: "localhost",
    port: 3302,
    database: "mir_lab7",
    user: "mir",
    password: "mir",
  },
  {
    host: "localhost",
    port: 3303,
    database: "mir_lab7",
    user: "mir",
    password: "mir",
  },
  {
    host: "localhost",
    port: 3304,
    database: "mir_lab7",
    user: "mir",
    password: "mir",
  }
];

app.get("/", runAsyncWrapper(async (req, res) => {
  var db = [];
  for (let i = 0; i < conn.length; i++) {
    db[i] = await mysql.createConnection(conn[i]);
  }

  let rows = [];
  for (let i = 0; i < db.length; i++) {
    [rows[i]] = await db[i].execute('SELECT * FROM blogs ORDER BY id');
  }

  res.render("index", {
    title: `Database content from all servers`,
    names: server_names,
    conn: conn,
    rows: rows,
  });
}));

app.get("/id/:id", runAsyncWrapper(async (req, res) => {
  let id = req.params.id;
  let max_id = n_servers * n_records - 1;

  if ((id < 0) || (id > max_id)) {
    res.render('record', {
      title: `Record ${id} is out of range`,
      msg: `Valid range is ${0}..${max_id}`,
      rows: [],
    });
    return;
  }

  let server_id = Math.floor(id / n_records);
  var db = await mysql.createConnection(conn[server_id]);

  const [rows] = await db.execute(
    'SELECT * FROM blogs WHERE id = ?',
    [id]
  );

  if (rows.length) {
    res.render('record', {
      title: `Record ${id} was found on ${server_names[server_id]}`,
      rows: rows,
    });
  } else {
    res.render('record', {
      title: `Record ${id} is expected to be on ${server_names[server_id]},
              but was not found`,
      rows: rows,
    });
  }
}));

app.listen(server_port, server_host, () => {
  console.log(
    `Server running at http://${server_host}:${server_port}/, press Ctrl-C to exit`
  );
});

function runAsyncWrapper(callback) {
  return function (req, res, next) {
    callback(req, res, next).catch(next);
  }
}

module.exports = app;
