<html lang="ru">
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>Welcome ${user}!</h1>
<p>Our latest product:
    <#list tables as name, value>
<p>${name} = <#list value.queryExplainResult as query, res>
<p>${query} - <#list res.stackTrace as element> ${element}</#list> </p>
</#list>
</#list>
</body>
</html>