<jsp:useBean id="login" scope="session" class="Model.Account" />
<%@page import="Model.Account, java.util.*, java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Meowtopia</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Montserrat:200,300,400,500,600,700,800&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="css/animate.css">

        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">


        <link rel="stylesheet" href="css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="css/jquery.timepicker.css">

        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/style.css">

        <!-- Icon Font Stylesheet -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>

        <%@ include file="header.jsp" %>

        <!-- END nav -->
        <section class="hero-wrap hero-wrap-2" style="background-image: url('pusheen/wallpaper1.png');" data-stellar-background-ratio="0.5">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text align-items-end">
                    <div class="col-md-9 ftco-animate pb-5">
                        <h1 class="mb-0 bread">Profile</h1>
                    </div>
                </div>
            </div>
        </section>

        <section class="ftco-section bg-light">
            <div class="container">
                <div id="successMessage" class="alert alert-success alert-dismissible fade show" role="alert" style="display:none; margin-top: 15px">
                    <strong>${sessionScope.successMessage}</strong>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-12">
                        <div class="wrapper">
                            <div class="row no-gutters">
                                <div class="contact-wrap w-100 p-md-5 p-4">
                                    <h3 class="mb-4">Account Details</h3>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="label" for="name">Full Name</label>
                                                <input type="text" class="form-control" name="name" id="name" placeholder="${login.accname}" disabled>
                                            </div>
                                        </div>
                                        <div class="col-md-6"> 
                                            <div class="form-group">
                                                <label class="label" for="email">Email Address</label>
                                                <input type="email" class="form-control" name="email" id="email" placeholder="${login.email}" disabled>
                                            </div>
                                        </div>
                                        <form method="post" action="<%=request.getContextPath()%>/account" enctype="multipart/form-data" style="margin-top: 20px;width: -webkit-fill-available;"> 
                                            <div class="col-auto">
                                                <div class="item-label"><strong>Reset Password</strong></div>
                                                <div class="item-data">
                                                    <input type="hidden" name="email" value="${login.email}">
                                                    <label for="password">New Password</label><br>
                                                    <input class="form-control" type="password" name="password" id="password" oninput="togglePasswordError()" required><br>
                                                    <label for="confirmPassword">Confirm Password</label><br>
                                                    <input class="form-control" type="password" name="confirmPassword" id="confirmPassword" oninput="togglePasswordError()" required><br>                                                        
                                                </div>
                                                <ul id="passwordError" style="color: red;"></ul>
                                            </div><!--//col-->

                                            <div class="col text-end" style="align-content:center;">
                                                <button type="submit" id="submit-pw-btn" class="btn btn-primary">Change</button>
                                            </div><!--//col-->
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <%@ include file="footer.jsp" %>

        <!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-migrate-3.0.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.easing.1.3.js"></script>
        <script src="js/jquery.waypoints.min.js"></script>
        <script src="js/jquery.stellar.min.js"></script>
        <script src="js/jquery.animateNumber.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/jquery.timepicker.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/scrollax.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
        <script src="js/google-map.js"></script>
        <script src="js/main.js"></script>

        <script>
            function showSuccessMessage() {
                if (document.getElementById('successMessage') && '${not empty sessionScope.successMessage}' === 'true') {
                    document.getElementById('successMessage').style.display = 'block';
                    setTimeout(function () {
                        document.getElementById('successMessage').style.display = 'none';
                    }, 3000);
            <%
                session.removeAttribute("successMessage");
            %>
                }
            }
            window.onload = showSuccessMessage;

            document.addEventListener('input', function () {
                var passwordInput = document.getElementById('password').value;
                var confirmPasswordInput = document.getElementById('confirmPassword').value;
                var passwordError = document.getElementById('passwordError');
                var isValidPassword = true;

                passwordError.innerHTML = '';

                if (passwordInput.length < 8) {
                    addErrorMessage(passwordError, 'Password must contain at least 8 characters');
                    isValidPassword = false;
                }

                var complexityRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+}{"':;?/>.<,])(?=.*[a-zA-Z]).{8,}$/;
                if (!complexityRegex.test(passwordInput)) {
                    addErrorMessage(passwordError, 'Password must contain at least one uppercase letter, one lowercase letter, one number, and one symbol');
                    isValidPassword = false;
                }

                if (passwordInput !== confirmPasswordInput && confirmPasswordInput !== passwordInput) {
                    addErrorMessage(passwordError, 'Passwords do not match');
                    isValidPassword = false;
                }

                var submitButton = document.getElementById('submit-pw-btn');
                submitButton.disabled = !isValidPassword;
            });

            function addErrorMessage(container, message) {
                var errorMessage = document.createElement('li');
                errorMessage.textContent = message;
                container.appendChild(errorMessage);
            }

        </script>

    </body>
</html>