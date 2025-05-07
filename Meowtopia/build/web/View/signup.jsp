<%@page import="java.util.*" %>
<jsp:useBean id="account" scope="session" class="Model.Account" />

<!DOCTYPE html>
<html lang="en"> 
    <head>
        <title>Customer Sign Up</title>

        <!-- Meta -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <meta name="description" content="Portal - Bootstrap 5 Admin Dashboard Template For Developers">
        <meta name="author" content="Xiaoying Riley at 3rd Wave Media">    
        <link rel="shortcut icon" href="favicon.ico"> 

        <!-- FontAwesome JS-->
        <script defer src="assets/plugins/fontawesome/js/all.min.js"></script>

        <!-- App CSS -->  
        <link id="theme-style" rel="stylesheet" href="assets/css/portal.css">

    </head> 

    <body class="app app-signup p-0">    	
        <div class="row g-0 app-auth-wrapper">
            <div class="col-12 col-md-7 col-lg-6 auth-main-col text-center p-5">
                <div class="d-flex flex-column align-content-end">
                    <div class="app-auth-body mx-auto">	
                        <div class="app-auth-branding mb-4"><a class="app-logo"><img class="logo-icon me-2" src="assets/images/cat-footprint.png" alt="logo"></a></div>
                        <h2 class="auth-heading text-center mb-4">Customer Sign up</h2>					

                        <div class="auth-form-container text-start mx-auto">
                            <form class="auth-form auth-signup-form" method="post" action="../signup">         
                                <div class="email mb-3">
                                    <input id="signup-name" name="signup-name" type="text" class="form-control" placeholder="Full name">
                                    <% if (session.getAttribute("nameError") != null) { %>
                                    <p class="error-message" style="color: red"><%= session.getAttribute("nameError") %></p>
                                    <% } %>
                                </div>
                                <div class="email mb-3">
                                    <input id="signup-email" name="signup-email" type="email" class="form-control" placeholder="Email Address">
                                    <% if (session.getAttribute("emailError") != null) { %>
                                    <p class="error-message" style="color: red"><%= session.getAttribute("emailError") %></p>
                                    <% } %>
                                </div>
                                <div class="password mb-3">
                                    <input id="signup-password" name="signup-password" type="password" class="form-control" placeholder="Create a password">
                                    <% if (session.getAttribute("passwordError") != null) { %>
                                    <p class="error-message" style="color: red"><%= session.getAttribute("passwordError") %></p>
                                    <% } %>
                                </div>
                                <div class="password mb-3">                              
                                    <input id="signup-confirm-password" name="signup-confirm-password" type="password" class="form-control" placeholder="Confirm Password">
                                    <% if (session.getAttribute("confirmPasswordError") != null) { %>
                                    <p class="error-message" style="color: red"><%= session.getAttribute("confirmPasswordError") %></p>
                                    <% } %>
                                </div>                                
                                <div class="text-center">
                                    <button type="submit" class="btn app-btn-primary w-100 theme-btn mx-auto">Sign Up</button>
                                </div>
                            </form><!--//auth-form-->

                            <div class="auth-option text-center pt-5">Already have an account? 
                                <a class="text-link" href="login.jsp" >Log in</a><br>
                            </div>
                        </div><!--//auth-form-container-->	
                    </div><!--//auth-body-->
                </div><!--//flex-column-->   
            </div><!--//auth-main-col-->
            <div class="col-12 col-md-5 col-lg-6 h-100 auth-background-col">
                <div class="auth-background-holder">			    
                </div>
                <div class="auth-background-mask"></div>
            </div><!--//auth-background-col-->
        </div><!--//row-->

        <script>
            setTimeout(function () {
                var errorMessages = document.getElementsByClassName('error-message');
                for (var i = 0; i < errorMessages.length; i++) {
                    errorMessages[i].style.display = 'none';
                }
            <%
                session.removeAttribute("nameError");
                session.removeAttribute("emailError");
                session.removeAttribute("passwordError");
                session.removeAttribute("confirmPasswordError");
            %>

            }, 5000);
        </script>
    </body>
</html> 

