<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Employee</title>
</head>
<body>
<form method='post' action='add'>
<input type='hidden' name='action' value='delete'/>
<table>
<tr><td>ID:</td><td><input type='text' name='id'/></td></tr>

<tr><td></td><td><button>Submit</button></td></tr>
</table>
</form>
<div>
${message }
</div>
</body>
</html>