<%-- 
    Document   : index
    Created on : May 3, 2017, 12:38:29 PM
    Author     : Davorin Horvat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unos IoT uređaja</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
    </head>
    <body class="container">
        <h1>Unos IoT uređaja</h1>
        <form class="row" action="${pageContext.servletContext.contextPath}/DodajUredjaj" method="POST">
            <div class="col-xs-10 row">
                <div class="labels col-xs-2">
                    <label for="naziv">Naziv i adresa:</label>
                    <label id="geoLabel" for="geoLokacija">Geo lokacija:</label>
                </div>
                <div class="inputs col-xs-10">
                    <input class="form-control" id="naziv" name="naziv" value="${naziv}"/>
                    <input class="form-control" id="adresa" name="adresa" value="${adresa}"/> <br />
                    <input class="form-control" id="geoLokacija" value="${lokacija}" name="geoLokacija" disabled/> <br />
                </div>
            </div>
            <div class="col-xs-2">
                <input class="btn btn-primary" type="submit" name="geoLokacija" value="Geo lokacija" /> <br/>
                <input class="btn btn-primary" type="submit" name="spremi" value="Spremi" /> <br />
                <input class="btn btn-primary" type="submit" name="meteoPodaci" value="MeteoPodaci" /> <br />
            </div>
            <div class="weather col-xs-12 row">
                ${meteoPodaci}
            </div>
            <div class="error col-xs-12">
                ${greska}
                ${spremi}
            </div>
        </form>
    </body>
</html>
