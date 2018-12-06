<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>Title</title>
    <!--图标库-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/3.1.1/jquery.min.js"></script>

    <script src="js/jquery.js"></script>

    <script src="js/laydate/laydate.js"></script>

    <script src="js/main.js"></script>

    <link href="css/main.css" rel="stylesheet">
</head>

<body>
<header></header>
<main id="time">
    <header id="terminfo">
        <label for="date">开学日期</label><input type="text" id="date">
        <label for="term">学期</label><select id="term">
        <option value="1" >第一学期</option>
        <option value="2" >第二学期</option>
    </select>
        <div class="btn btn-success btn-lg text-center" id="commit">提交</div>
    </header>
    <table class="table table-bordered table-responsive">
        <caption class="text-center">学期上课时间表</caption>
        <thead>
        <tr>
            <th>节次</th>
            <th>时间</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td class="time">08:00 - 08:45</td>
        </tr>
        <tr>
            <td>2</td>
            <td class="time">08:55 - 09:40</td>
        </tr>
        <tr>
            <td>3</td>
            <td class="time">10:00 - 10:45</td>
        </tr>
        <tr>
            <td>4</td>
            <td class="time">10:55 - 11:40</td>
        </tr>
        <tr>
            <td>5</td>
            <td class="time">14:00 - 14:45</td>
        </tr>
        <tr>
            <td>6</td>
            <td class="time">14:55 - 15:40</td>
        </tr>
        <tr>
            <td>7</td>
            <td class="time">15:50 - 16:35</td>
        </tr>
        <tr>
            <td>8</td>
            <td class="time">16:45 - 17:30</td>
        </tr>
        </tbody>
    </table>
</main>

    <h1>${hello}</h1>

</body>
</html>
