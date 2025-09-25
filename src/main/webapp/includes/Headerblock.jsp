<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .main-header {
           /* background-color: #fff;*/
            background-color: #000000;
            border-bottom: 1px solid #ddd;
            padding: px 0%; /* Add padding to the header itself */
            display: flex; /* Use flexbox for layout */
            justify-content: space-between; /* Space out items */
            align-items: center; /* Vertically align items */
            max-width: 1550px; /* Limit header width */
            margin: 0 auto; /* Center the header */
            position: relative; /* For absolute positioning of promo if needed */
        }

        .header-top-promo {
            background-color: #007bff; /* Example blue */
            color: #fff;
            text-align: center;
            padding: 8px 0;
            font-size: 0.9em;
            /* If this is a separate bar *above* the main header */
            width: 100%;
            position: absolute; /* Position relative to body or a wrapper */
            top: 0;
            left: 0;
            z-index: 10;
        }
        /* If header-top-promo is *within* main-header, remove position absolute */


        /* New Logo Container - This is where the logo goes */
        .header-left-section {
            display: flex;
            align-items: center;
            gap: 15px; /* Space between logo and other elements if any */
        }

        .header-logo img {
            height: 100px; /* Adjust logo height as desired */
            width: auto;
            display: block; /* Remove extra space below image */
        }

        .header-logo-text {
            font-size: 1.8em; /* Adjust size for "SAKARA PUBLISHERS!" */
            font-weight: bold;
            color: #333; /* Or your brand color */
        }
        .search-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.search-input {
  width: 300px;
  padding: 10px;
  border: 2px solid #555;
  border-radius: 5px 0 0 5px;
  font-size: 16px;
}

.search-button {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: 2px solid #28a745;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
  font-size: 16px;
}

.search-button:hover {
  background-color: #218838;
}


        /* --- Existing Navigation and Action Buttons (adjust as needed) --- */
        .main-menu ul {
            display: flex;
        }

        .main-menu ul li {
            margin-right: 20px;
        }

        .main-menu ul li a {
            font-weight: bold;
            padding: 5px 0;
            transition: color 0.3s ease;
        }

        .main-menu ul li a:hover {
            color: #007bff;
        }

        .header-actions {
            display: flex;
            gap: 20px;
            align-items: center; /* Align buttons vertically */
        }

</style>

</head>
<body>
<header class="main-header">
<div class="header-left-section">
            <div class="header-logo">
                 <img src="images/Sakaralogo.jpg" alt="SAKARA Logo">
            </div>
            <div class="header-logo-text">sakarapublisers.in</div>
        </div>
<div class="search-container">
            <form action="SearchServlet" method="get" > <!--  style="text-align: center; margin-bottom: 20px;">-->
        <input type="text" name="keyword" placeholder="Search books..." class="search-input"> <!--   style="padding: 8px; width: 500px; border-radius: 50px; border: 1px solid #ccc;" /> -->
        <button type="submit" class="search-button">Search</button> <!--   style="padding: 8px 15px; border-radius: 10px; border: none; background-color: #4CAF50; color: white;">Search</button> -->
    </form>
</div>

<!-- 👤 Profile Button -->
<div style="text-align: right; margin: 10px 30px;">
    <a href="Profile.jsp" 
       style="text-decoration: none; background-color: black; color: white; padding: 8px 16px; border-radius: 4px; font-weight: bold;">
        👤 My Account
    </a>
</div>
<!-- Cart Button -->
<div style="text-align: right; margin: 10px 30px;">
    <a href="Cart.jsp" 
       style="text-decoration: none; background-color: black; color: white; padding: 8px 16px; border-radius: 4px; font-weight: bold;">
        🛒 My Cart
    </a>
</div>

</header>

</body>
</html>