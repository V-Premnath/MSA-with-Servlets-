<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload Image</title>
</head>
<body>
    <form action="/uploadImage" method="post" enctype="multipart/form-data">
        <label for="file">Choose an image to upload:</label>
        <input type="file" id="file" name="file" accept="image/*" required>
        <input type="text" name="uploadedBy" placeholder="Your Name" required>
        <button type="submit">Upload</button>
    </form>
</body>
</html>