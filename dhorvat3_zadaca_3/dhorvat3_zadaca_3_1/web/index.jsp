<%-- 
    Document   : index
    Created on : May 3, 2017, 12:38:29 PM
    Author     : Davorin Horvat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unos IoT uređaja</title>
    </head>
    <body>
        <h1>Unos IoT uređaja</h1>
        <form action="${pageContext.servletContext.contextPath}/DodajUredjaj" method="POST">
            <label for="naziv">Nazi i adresa</label>
            <input id="naziv" name="naziv"/>
            <input id="adresa" name="adresa"/> <br />
            <input type="submit" name="geoLokacija" value="Geo lokacija" /> <br/>
            <input type="submit" name="spremi" value="Spremi" /> <br />
            <input type="submit" name="meteoPodaci" value="MeteoPodaci" /> <br />
        </form>
    </body>
</html>
