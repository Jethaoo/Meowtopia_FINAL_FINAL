<%@page import="Model.Post" %>
<%@page import="Model.PostService" %>
<%@page import="Model.PostlikedService" %>
<%@page import="jakarta.persistence.Persistence" %>
<%@page import="jakarta.persistence.EntityManager" %>
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
    </style>
    <body>

        <%@ include file="header.jsp" %>

        <!-- END nav -->
        <section class="hero-wrap hero-wrap-2" style="background-image: url('pusheen/wallpaper1.png');" data-stellar-background-ratio="0.5">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text align-items-end">
                    <div class="col-md-9 ftco-animate pb-5">
                        <h1 class="mb-0 bread">Meow Community</h1>
                    </div>
                </div>
            </div>
        </section>

        <section class="ftco-section bg-light">
            <% List<Post> postList = (List<Post>) session.getAttribute("allPostList"); %>
            <div class="container mt-4">
                <div class="col-auto" style="margin-bottom: 35px; position: relative; height: 50px;">				
                    <a class="btn app-btn-primary" href="../DisplayUserPosts" style="position: absolute; left: 0%;">Manage Your Posts</a>
                </div>
                <div class="row justify-content-center mb-4">
                    <% if (postList != null && !postList.isEmpty()) {
                    for (Post post : postList) { 
                        String modalId = "imageModal" + post.getPostid();
                    %>
                    <div class="col-md-7 ftco-animate" style="margin-left: 25%; margin-right: 25%;">
                        <div class="blog-entry align-self-stretch">
                            <% if (post.getImage() == null) { %>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#<%= modalId %>" class="block-20 rounded" style="background-image: url('assets/images/default-profile-photo.jpg'); height: 300px; background-size: cover; background-position: center;"></a>
                            <% } else { %>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#<%= modalId %>" class="block-20 rounded" style="background-image: url('data:image/png;base64,<%= post.getPicBase64() %>'); height: 300px; background-size: cover; background-position: center;"></a>
                            <% } %>
                            <div class="text p-4">
                                <div class="meta mb-2">
                                    <div><a>Posted by <%= post.getEmail().getAccname() %></a></div>
                                    <%
                                        EntityManager em = Persistence.createEntityManagerFactory("MeowtopiaPU").createEntityManager();
                                        PostlikedService pls = new PostlikedService(em);
                                        String userEmail = (String) session.getAttribute("userEmail");
                                        boolean liked = pls.hasUserLikedPost(userEmail, post.getPostid());
                                    %>
                                    <% SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy"); %>
                                    <div><a href="#">ON <%= sdf.format(post.getPostdate()) %></a></div>
                                    <div id="like-container-<%= post.getPostid() %>">
                                        <button
                                            class="meta-chat"
                                            style="background:none; border:none; cursor:pointer;"
                                            onclick="likePost('<%= post.getPostid() %>'); return false;"
                                            >
                                            <span id="like-icon-<%= post.getPostid() %>" class="<%= liked ? "fas fa-heart" : "far fa-heart"%>"></span>
                                            <span id="like-count-<%= post.getPostid() %>"><%= post.getTotallike() %></span>
                                        </button>
                                    </div>

                                </div>
                                <h3 class="heading"><%= post.getDescription() %></h3>
                            </div>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="<%= modalId %>" tabindex="-1" aria-labelledby="<%= modalId %>Label" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content bg-dark">
                                <div class="modal-body text-center">
                                    <% if (post.getImage() == null) { %>
                                    <img src="assets/images/default-profile-photo.jpg" class="img-fluid rounded" alt="Post Image" />
                                    <% } else { %>
                                    <img src="data:image/png;base64,<%= post.getPicBase64() %>" class="img-fluid rounded" alt="Post Image" />
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>

                    <% } } else { %>
                    <div class="text-center">
                        <p>No posts available.</p>
                    </div>
                    <% } %>
                </div>
            </div>
        </section>

        <%@ include file="footer.jsp" %>


        <!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


        <script>
            function likePost(postId) {
                fetch('../LikePost', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'postid=' + encodeURIComponent(postId)
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                const icon = document.getElementById('like-icon-' + postId);
                                const count = document.getElementById('like-count-' + postId);

                                count.textContent = data.totalLikes;
                                icon.className = data.liked ? 'fas fa-heart' : 'far fa-heart';
                            } else {
                                alert("Failed to like post.");
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
            }

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
        <script src="js/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>