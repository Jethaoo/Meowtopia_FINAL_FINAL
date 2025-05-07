<%-- 
    Document   : header
    Created on : 4 Mar 2025, 10:35:28 pm
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
            <a class="navbar-brand" href="index.jsp"><span class="flaticon-pawprint-1 mr-2"></span>Meowtopia</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="fa fa-bars"></span> Menu
            </button>
            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item"><a href="index.jsp" class="nav-link">Meow Sweet Home</a></li>
                    <li class="nav-item"><a href="../DisplayAllPosts" class="nav-link">Meow Community</a></li>
                </ul>

                <div class="d-flex m-3 me-0">            
                    <a href="account.jsp" class="position-relative me-4 my-auto" style="margin: 0px 20px 0px 10px;">
                        <i class="fas fa-user fa-2x"></i>
                    </a>

                    <a href="../logout" class="my-auto" style="margin: 0px 20px 0px 10px;">
                        <i class="fa fa-sign-out-alt fa-2x"></i>
                    </a>
                </div>
            </div>


        </div>
    </nav>
</html>
