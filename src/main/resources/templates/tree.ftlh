<!doctype html>
<html lang="en">
<meta charset="windows-1251">

<head>
	<title>Lab3</title>
</head>

<body>
<style>
    body {
        width: min-content;
        height: min-content;
        padding: 5px 10px 5px 10px;
        border: 1px solid #999;
        font-size: 12px;
        font-family: "Consolas", monospace;
    }

    h1 {
        text-align: center;
    }

    .file {
        white-space: pre-wrap;
        width: max-content;
    }
</style>

<h1>Variant III. Display ftp server directory</h1>
<hr>
<form action="/tree" method="post">
	Hostname: <input type="text" name="hostname" placeholder="Hostname"
	                 value="<#if hostname??>${hostname}</#if>" size="70"
	                 required><br><br>

	Port: <input type="text" name="port" placeholder="Port" step="1"
	             value="<#if port??>${port}</#if>" size="70" min="0" max="65535"
	             required><br><br>

	Username: <input type="text" name="username" placeholder="Login"
	                 value="<#if username??>${username}</#if>" size="70"><br><br>

	Password: <input type="text" name="password" placeholder="Password"
	                 value="<#if password??>${password}</#if>" size="70"><br><br>
	<input type="submit" value="Display directory"><br><br><br>
</form>

<#if !error??>
	Directory:<br><br>
    <#if directory??>
        <#list directory as file>
					<div class="file">${file}<br></div>
        </#list>
    </#if>
<#else>
  Error: ${error}
</#if>

</body>
</html>