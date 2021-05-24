/*jshint esversion: 6 */

document.addEventListener("DOMContentLoaded", () => {
  recordVisit("history");

  const sessionRecords = document.querySelector('#sessionRecords');
  displaySessionVisitsRecords(sessionRecords);

  const allRecords = document.querySelector('#allRecords');
  displayAllVisitsRecords(allRecords);
});
