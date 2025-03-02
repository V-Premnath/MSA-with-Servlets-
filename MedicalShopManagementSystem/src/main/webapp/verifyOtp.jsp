<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTP Verification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f2f5;
            margin: 0;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .otp-input {
            width: 40px;
            padding: 10px;
            font-size: 18px;
            text-align: center;
            margin: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body style="background-color:#97befc;">
    <div class="container" style="">
    <h1>Medical Shop Automation</h1><img src="includes/msa_logo.png" width="70px" height="70px">
        <h2>OTP Verification</h2>
        <form id="otpForm" action="verifyOtp" method="post">
            <input type="text" maxlength="1" class="otp-input" oninput="moveToNext(this, 'otp2')" id="otp1" name="otp1" >
            <input type="text" maxlength="1" class="otp-input" oninput="moveToNext(this, 'otp3')" id="otp2" name="otp2">
            <input type="text" maxlength="1" class="otp-input" oninput="moveToNext(this, 'otp4')" id="otp3" name="otp3">
            <input type="text" maxlength="1" class="otp-input" oninput="moveToNext(this, 'otp5')" id="otp4" name="otp4">
            <input type="text" maxlength="1" class="otp-input" oninput="moveToNext(this, 'otp6')" id="otp5" name="otp5">
            <input type="text" maxlength="1" class="otp-input" oninput="moveToNext(this,'verifyotp')" id="otp6" name="otp6">
            <button type="submit" id="verifyotp">Verify</button>
        </form>
    </div>

    <script>
        function moveToNext(current, nextFieldId) {
            if (current.value.length === 1) {
                document.getElementById(nextFieldId).focus();
            }
        }

    </script>
</body>
</html>
