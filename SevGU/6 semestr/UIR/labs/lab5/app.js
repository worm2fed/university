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
  database: "mir_lab5",
  user: "mir",
  password: "mir",
};

var test_id = 1500;
var queries = [
  {
    table: "blogs",
    sql: `SELECT SQL_NO_CACHE * FROM blogs`,
  },
  {
    table: "blogs",
    sql: `SELECT SQL_NO_CACHE * FROM blogs WHERE id = ${test_id} LIMIT 1`,
  },
  {
    table: "partitioned_blogs",
    sql: `SELECT SQL_NO_CACHE * FROM partitioned_blogs`,
  },
  {
    table: "partitioned_blogs",
    sql: `SELECT SQL_NO_CACHE * FROM partitioned_blogs WHERE id = ${test_id} LIMIT 1`,
  },
];

var elapsed_time = function (timer) {
  var elapsed = process.hrtime(timer)[1] / 1000000;
  return elapsed.toFixed(2) + " ms";
}

app.get("/", runAsyncWrapper(async (req, res) => {
  var db = await mysql.createConnection(conn);
  var [rows] = await db.execute(
    `SELECT COUNT(*) AS n FROM ${queries[0].table}`
  );
  var n = rows[0].n;

  let results = [];
  for (let i in queries) {
    var timer = process.hrtime();
    let [rows] = await db.execute(queries[i].sql);
    results.push({
      n: n,
      sql: queries[i].sql,
      table: queries[i].table,
      time: elapsed_time(timer),
    });
  }

  res.render("index", { title: `Query results`, results: results });
})
);

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
