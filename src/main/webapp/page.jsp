<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>BACKBONE TEST</title>
  <meta name="description" content="">
  <base href="http://localhost:8080/checklist-app/">
  <script type="text/template" id="item-template">
    <div>
      <input id="todo_complete" type="checkbox" <@= completed ? 'checked="checked"' : '' @>>
      <@- title @>
    </div>
  </script>
  <script src="js/jquery-1.9.1.min.js"></script>
  <script src="js/underscore-min.js"></script>
  <script src="js/json2.js"></script>
  <script src="js/backbone-min.js"></script>
  <script src="js/cl-common.js"></script>
  <script src="page.js"></script>
</head>
<body>
  <div id="todo">
  </div>
</body>
</html>