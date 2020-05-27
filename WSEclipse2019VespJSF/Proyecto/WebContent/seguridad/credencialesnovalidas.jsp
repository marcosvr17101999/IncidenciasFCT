<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />

<title>Pagina de identificacion</title>
<style>
    body{
        background-color: #333333;
        font: italic small-caps bold 4px Georgia, sans-serif;
    }
    .v{
        background-color: white;
        color: #333333;
        text-align: center;
        font-size: 20px;
    }
    .btn{
        font: italic small-caps  35px Georgia, sans-serif;
 
    }
    .h{
        font: italic small-caps  65px Georgia, sans-serif;
 
    }
</style>
</head>
<body>
   <div class="container-fluid row justify-content-center">
        
		
		
      <div id="header"></div>
      
      <form action="j_security_check" method="POST" class="login col-6 mt-5 pt-5 v ">
      <div id="diverror">
         <p>
            <strong><c:out value="Error" /></strong> <br>
            <c:out value="Usuario o password incorrectos " />
         </p>
      </div>
         <table cellspacing="2" cellpadding="3" border="0" width="100%">
            <tr>
               <td colspan="2">
                  <h2>Introduzca sus datos de usuario</h2>
               </td>
            </tr>
            <tr>
               <td width="11%">*Username</td>
               <td><input type="text" name="j_username" /></td>
            </tr>
            <tr>
               <td>*Password</td>
               <td><input type="password" name="j_password"/></td>
            </tr>
            <tr>
               <td>&nbsp;</td>
               <td colspan="2"><input type="submit" name="login"
                  value="Login" /> <a href="../user/home.xhtml">Registrese</a>
               </td>
            </tr>
         </table>
      </form>
   </div>
</body>
</html>