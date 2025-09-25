<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login & Registration</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: 'Segoe UI', sans-serif;
    }

    body {
      background: url("https://wallpaperaccess.com/full/1692736.jpg") no-repeat center center fixed;
      background-size: cover;
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .container {
      width: 400px;
      background: #fff;
      padding: 30px;
      border-radius: 20px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.2);
      position: relative;
      overflow: hidden;
    }

    .banner {
      background-color: #ACB6E5;
      color: #fff;
      padding: 10px;
      border-radius: 10px;
      margin-bottom: 15px;
      font-weight: bold;
      font-size: 14px;
      overflow: hidden;
    }

    .form-box {
      transition: transform 0.5s ease-in-out;
      width: 200%;
      display: flex;
    }

    form {
      width: 50%;
      padding: 20px;
      position: relative;
    }

    h2 {
      text-align: center;
      margin-bottom: 20px;
      color: #333;
    }

    input {
      width: 100%;
      padding: 12px 40px 12px 12px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 10px;
      outline: none;
      transition: 0.3s;
    }

    input:focus {
      border-color: #74ebd5;
      box-shadow: 0 0 5px #74ebd5;
    }

    .input-wrapper {
      position: relative;
    }

    .eye-icon {
      position: absolute;
      top: 50%;
      right: 12px;
      transform: translateY(-50%);
      cursor: pointer;
      font-size: 18px;
      color: #555;
    }

    .error-msg {
      font-size: 12px;
      color: red;
      margin-top: -8px;
      margin-bottom: 8px;
      display: none;
    }

    button {
      width: 100%;
      padding: 12px;
      margin-top: 10px;
      background-color: #74ebd5;
      border: none;
      border-radius: 10px;
      font-weight: bold;
      color: #fff;
      cursor: pointer;
      transition: 0.3s;
    }

    button:hover {
      background-color: #57c6c6;
    }

    .toggle-btns {
      text-align: center;
      margin-bottom: 20px;
    }

    .toggle-btns button {
      width: 120px;
      margin: 0 10px;
      padding: 10px;
      background: #ACB6E5;
      color: #fff;
      border: none;
      border-radius: 20px;
      cursor: pointer;
      font-weight: bold;
      transition: 0.3s;
    }

    .toggle-btns button.active {
      background: #74ebd5;
    }

    /* Logo wrapper */
    /* Header logo & brand */
    .header-logo {
      text-align: center;
      margin-bottom: 30px;
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 15px;
    }

    .logo {
      max-width: 120px;
      height: auto;
    }

    .brand-name {
      font-family: 'Segoe UI', sans-serif;
      font-size: 28px;
      font-weight: bold;
      color: #fff;
    }
  </style>
</head>
<body>

<!-- Logo -->
<!--  
 <div class="header-left-section">
<div class="header-logo">
  <img src="< %=request.getContextPath()%>/images/Sakara_publishers.png" alt="Sakara Publishers Logo" class="logo" />
 <span class="brand-name">SAKARA</span>
</div>
</div>
-->
<!-- Your existing login block here -->

<!-- fefshiutyczmwyjd -->

<div class="container">
  <div class="banner">
    <marquee behavior="scroll" direction="left">
      📚 Welcome to Sakara Publishers! | 🎉 Sign up and get 30% off your first purchase! | 💖 Find your next favorite read!
    </marquee>
  </div>

  <div class="toggle-btns">
    <button id="loginBtn" class="active">Login</button>
    <button id="registerBtn">Register</button>
  </div>

  <div class="form-box" id="formBox">

    <!-- Login Form -->
    <form id="loginForm" method="post" onsubmit="return validateLoginChoice()">
      <h2>Login</h2>

      <div class="input-wrapper">
        <input type="email" id="loginEmail" name="emailOrMobile" placeholder="Email">
      </div>
      <div class="error-msg" id="loginEmailError">Please enter a valid email</div>

      <div class="input-wrapper">
        <input type="password" id="loginPassword" name="password" placeholder="Password">
        <span class="eye-icon" onclick="togglePassword('loginPassword', this)">👁️‍🗨️</span>
      </div>

      <h2>Login via Mobile</h2>
      <input type="text" id="mobileNumber" name="mobile" placeholder="Enter Mobile Number" pattern="[0-9]{10}">

      <div class="error-msg" id="loginErrorMsg">Please fill either email + password OR mobile number only.</div>

      <button type="submit">Login / Send OTP</button>
    </form>

    <!-- Register Form -->
    <!--  
    <form id="registerForm" action="RegisterServlet" method="post" onsubmit="return validateRegisterForm()">
      <h2>Register</h2>
      -->
    <form id="registerForm" action="<%=request.getContextPath()%>/RegisterServlet" method="post" onsubmit="return validateRegisterForm()">
      <input type="text" name="fullName" placeholder="Full Name" required>

      <input type="text" name="mobileNumber" placeholder="Mobile Number" pattern="[0-9]{10}" title="Enter 10-digit mobile number" required>

      <div class="input-wrapper">
        <input type="email" id="registerEmail" name="emailOrMobile" placeholder="Email" required>
      </div>

      <div class="error-msg" id="registerEmailError">Please enter a valid email</div>

      <div class="input-wrapper">
        <input type="password" id="registerPassword" name="password" placeholder="Password" required>
        <span class="eye-icon" onclick="togglePassword('registerPassword', this)">👁️‍🗨️</span>
      </div>

      <div class="input-wrapper">
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
        <span class="eye-icon" onclick="togglePassword('confirmPassword', this)">👁️‍🗨️</span>
      </div>
      <div class="error-msg" id="passwordMismatchError">Passwords do not match</div>

      <button type="submit">Register</button>
    </form>
  </div>
</div>

<script>
  const loginBtn = document.getElementById("loginBtn");
  const registerBtn = document.getElementById("registerBtn");
  const formBox = document.getElementById("formBox");

  loginBtn.addEventListener("click", () => {
    formBox.style.transform = "translateX(0%)";
    loginBtn.classList.add("active");
    registerBtn.classList.remove("active");
  });

  registerBtn.addEventListener("click", () => {
    formBox.style.transform = "translateX(-50%)";
    registerBtn.classList.add("active");
    loginBtn.classList.remove("active");
  });

  function togglePassword(inputId, icon) {
    const input = document.getElementById(inputId);
    if (input.type === "password") {
      input.type = "text";
      icon.textContent = "🙈";
    } else {
      input.type = "password";
      icon.textContent = "👁️‍🗨️";
    }
  }

  function validateEmail(email) {
    const regex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    return regex.test(email);
  }

  function validateLoginChoice() {
    const email = document.getElementById("loginEmail").value.trim();
    const password = document.getElementById("loginPassword").value.trim();
    const mobile = document.getElementById("mobileNumber").value.trim();
    const errorMsg = document.getElementById("loginErrorMsg");

    if (email !== "" && password !== "" && mobile === "") {
      document.getElementById("loginForm").action = "LoginServlet";
      errorMsg.style.display = "none";
      return true;
    }

    if (email === "" && password === "" && mobile !== "") {
      document.getElementById("loginForm").action = "SendOtpServlet";
      errorMsg.style.display = "none";
      return true;
    }

    errorMsg.style.display = "block";
    return false;
  }

  function validateRegisterForm() {
    const email = document.getElementById("registerEmail").value;
    const emailError = document.getElementById("registerEmailError");
    const password = document.getElementById("registerPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const passwordError = document.getElementById("passwordMismatchError");

    let valid = true;

    if (!validateEmail(email)) {
      emailError.style.display = "block";
      valid = false;
    } else {
      emailError.style.display = "none";
    }

    if (password !== confirmPassword) {
      passwordError.style.display = "block";
      valid = false;
    } else {
      passwordError.style.display = "none";
    }

    return valid;
  }

  document.getElementById("confirmPassword").addEventListener("input", function () {
    const password = document.getElementById("registerPassword").value;
    const confirmPassword = this.value;
    const error = document.getElementById("passwordMismatchError");

    if (password !== confirmPassword) {
      error.style.display = "block";
    } else {
      error.style.display = "none";
    }
  });
</script>

</body>
</html>
