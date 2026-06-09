<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webapp/css/product.css">
</head>
<body>

<button id=onCamera>カメラ起動</button>
<button id=offCamera style="display:none;">カメラ停止</button>
<div id="cameraView" style="display:none;">
  <video id="camera"></video>
</div>

<input type="checkbox" id="checkAll">
<table>
  <tr>
    <th></th>
    <th><img src="#"></th>
    <th>JAN</th>
    <th>商品名</th>
  </tr>
  <tr>
    <th><input type="checkbox" id="checck"></th>
    <th></th>
    <th>JAN</th>
    <th>商品名</th>
  </tr>
</table>
</body>
</html>