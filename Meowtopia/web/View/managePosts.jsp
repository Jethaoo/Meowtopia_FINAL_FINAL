<%@page import="Model.Post" %>
<%@page import="Model.PostService" %>
<%@page import="java.util.*"%>
<%@page import="java.util.Base64" %>
<%@page import="java.text.SimpleDateFormat" %>

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
    <style>
        .btn{
            font-weight:600;
            padding:.5rem 1rem;
            font-size:.875rem;
            border:none
        }

        .app-btn-primary{
            background:#15a362;
            color:#fff;
            border-color:#15a362;
        }

        .app-btn-primary:hover,.app-btn-primary:active{
            color:#fff;
            background:#18ba70;
            border-color:#18ba70;
        }

        .col-auto{
            flex:0 0 auto;
            width:auto
        }

        .tab-content>.tab-pane{
            display:none
        }
        .tab-content>.active{
            display:block
        }

        .app-card{
            position:relative;
            background:#fff;
            border-radius:.25rem
        }

        .app-card-orders-table .table{
            font-size:18px;
        }
        .app-card-orders-table .table .cell{
            border-color:#e7e9ed;
            color:#5d6778;
            vertical-align:middle
        }
        .app-card-orders-table .cell span{
            display:inline-block
        }
        .app-card-orders-table .cell .note{
            display:block;
            color:#828d9f;
            font-size:16px;
        }
        .app-card-orders-table .btn-sm,.app-card-orders-table .btn-group-sm>.btn{
            padding:.125rem .5rem;
            font-size:16px;
        }
        .app-card-orders-table .truncate{
            max-width:250px;
            display:inline-block;
            overflow:hidden;
            text-overflow:ellipsis;
            white-space:nowrap
        }
        .shadow-sm{
            box-shadow:0 .125rem .25rem rgba(0,0,0,.075) !important
        }

        .app-card .app-card-body.has-card-actions{
            position:relative;
            padding-right:1rem !important
        }
        .app-card .app-card-body .app-card-actions{
            display:inline-block;
            width:30px;
            height:30px;
            text-align:center;
            border-radius:50%;
            position:absolute;
            z-index:10;
            right:.75rem;
            top:.75rem
        }
        .app-card .app-card-body .app-card-actions:hover{
            background:#f5f6fe
        }
        .app-card .app-card-body .app-card-actions .dropdown-menu{
            font-size:18px
        }
        .table-responsive{
            overflow-x:auto;
            -webkit-overflow-scrolling:touch
        }
        
        .dlt-btn{
            background-color: #EF3159; 
            margin-left: 20px;
        }
        
        .dlt-btn:hover {
            background-color: red;
        }
    </style>
    <body>

        <%@ include file="header.jsp" %>

        <section class="hero-wrap hero-wrap-2" style="background-image: url('pusheen/wallpaper1.png');" data-stellar-background-ratio="0.5">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text align-items-end">
                    <div class="col-md-9 ftco-animate pb-5">
                        <h1 class="mb-0 bread">Manage Your Posts</h1>                       
                    </div>
                </div>
            </div>
        </section>

        <div class="container" style="max-width: 1220px">
            <div class="app-wrapper">
                <div class="app-content pt-3 p-md-3 p-lg-4">
                    <div id="successMessage" class="alert alert-success alert-dismissible fade show" role="alert" style="display:none;">
                        <strong>${sessionScope.successMessage}</strong>
                    </div>

                    <div class="container-xl">
                        <div class="row g-3 mb-4 align-items-center justify-content-between">
                            <div class="col-auto">
                                <div class="page-utilities">
                                    <div class="row g-2 justify-content-start justify-content-md-end align-items-center" style="position:relative; margin-bottom: 30px; margin-top: 30px;">
                                        <div class="col-auto" style="position: absolute; right: 0%; display: flex;">				
                                            <a class="btn btn-secondary" href="../DisplayAllPosts" style="margin-right: 20px;">
                                                Back
                                            </a>
                                            
                                            <a class="btn app-btn-primary" href="addPostForm.jsp">
                                                <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="20" height="20" viewBox="0 0 50 50" fill="currentColor" style="padding-bottom: 3px">
                                                <path d="M 25 2 C 12.309295 2 2 12.309295 2 25 C 2 37.690705 12.309295 48 25 48 C 37.690705 48 48 37.690705 48 25 C 48 12.309295 37.690705 2 25 2 z M 25 4 C 36.609824 4 46 13.390176 46 25 C 46 36.609824 36.609824 46 25 46 C 13.390176 46 4 36.609824 4 25 C 4 13.390176 13.390176 4 25 4 z M 24 13 L 24 24 L 13 24 L 13 26 L 24 26 L 24 37 L 26 37 L 26 26 L 37 26 L 37 24 L 26 24 L 26 13 L 24 13 z"></path>
                                                </svg>
                                                Share a Post
                                            </a>
                                        </div>

                                    </div><!--//row-->
                                </div><!--//table-utilities-->
                            </div><!--//col-auto-->
                        </div><!--//row-->
                        <!--Table content start -->
                        <div class="tab-content" id="orders-table-tab-content">
                            <div class="tab-pane fade show active" id="orders-all" role="tabpanel" aria-labelledby="orders-all-tab">
                                <div class="app-card app-card-orders-table shadow-sm mb-5">
                                    <div class="app-card-body">
                                        <div class="table-responsive">
                                            <table class="table app-table-hover mb-0 text-left">
                                                <thead>
                                                    <tr>
                                                        <th class="cell">No</th>
                                                        <th class="cell">Image</th>
                                                        <th class="cell">Description</th>
                                                        <th class="cell">Total Likes</th>
                                                        <th class="cell">Date Posted/Edited</th>
                                                        <th class="cell">Action</th>
                                                        <th class="cell"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% int rowNum = 1; %>
                                                    <%
                                                        List<Post> userPostList = (List<Post>) session.getAttribute("userPostList");
                                                        if (userPostList != null && !userPostList.isEmpty() ) {
                                                        for (Post posts : userPostList) {
                                                    %>
                                                    <tr>
                                                        <td class="cell"><%= rowNum++ %></td>
                                                        <td class="cell">
                                                            <% if (posts.getImage() == null) { %>
                                                            <img class="image" src="assets/images/default-profile-photo.jpg" alt="Default Image" style="width: 60px; height: 60px">
                                                            <% } else { %>
                                                            <img src="data:image/png;base64, <%= posts.getPicBase64() %>" style="width: 60px; height: 60px">
                                                            <% } %>
                                                        </td>                                                
                                                        <td class="cell"><%= posts.getDescription() %></td>
                                                        <td class="cell"><%= posts.getTotallike() %></td>
                                                        <% SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy"); %>
                                                        <td class="cell"><%= sdf.format(posts.getPostdate()) %></td>
                                                        <td class="cell">    
                                                            <a class="btn-sm app-btn-primary" href="editPostForm.jsp?postId=<%= posts.getPostid() %>">Edit</a>                                                        
                                                            <a class="btn-sm app-btn-primary dlt-btn" href="#" onclick="confirmDelete('<%= posts.getPostid() %>')">Delete</a>
                                                        </td>

                                                    </tr>
                                                    <% } }else { %>
                                                    <tr><td colspan="6">No posts found.</td></tr>
                                                    <% } %>
                                                </tbody>
                                            </table>
                                        </div><!--//table-responsive-->

                                    </div><!--//app-card-body-->		
                                </div><!--//app-card-->
                            </div><!--//tab-pane-->
                        </div><!--//tab-content-->
                    </div><!--//container-fluid-->
                </div><!--//app-content-->
            </div><!--//app-wrapper-->
        </div>

        <%@ include file="footer.jsp" %>

        <!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>



        <!-- Javascript -->          
        <script src="assets/plugins/popper.min.js"></script>
        <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>  
        <!-- Page Specific JS -->
        <script src="assets/js/app.js"></script> 

        <script>
            function confirmDelete(postId) {
                if (confirm("Are you sure you want to delete this post?")) {
                // Redirect to DeletePost.java servlet with the postId as query string
                window.location.href = "../DeletePost?postId=" + encodeURIComponent(postId);
                }
            }
            
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
        </script>
        
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


    </body>
</html>