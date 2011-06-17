<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	If you <c:out value="${CalcObject.getOperator()}"/>  <c:out value="${CalcObject.getNumber1()}"/> and/by <c:out value="${CalcObject.getNumber2()}"/> you get <c:out value="${CalcObject.getAnswer()}"/>!
</h1>
</body>
</html>
