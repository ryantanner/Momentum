<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
		<script type="text/javascript">
			function changeAction(){
				document.wunderForm.action = "/momentum?loc=" + document.wunderForm.location.value;
			}
		</script>
	</head>
	
	<body>
		OMG ITS A DEFAULT JSP
		<form name="wunderForm" action="" method="GET">
			<input name="location" type="text"/>
			<input type="submit" value="Servlet" onClick="changeAction()"/>
		</form>
	</body>
</html>