<#ftl encoding="UTF-8"/>
<#import "mcrs/bodyScripts.ftlh" as b>

<#import "mcrs/headScripts.ftlh" as h>
<#import "mcrs/LoginCss.ftlh" as c>
<#import  "spring.ftl" as spring/>
<html lang="eng">
<head>
    <title>Login Page</title>
    <meta charset="UTF-8">
    <!--Made with love by Mutiullah Samim -->

    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <meta  name = "_csrf"  content = "${_csrf.token}" />
    <! - имя заголовка по умолчанию - X-CSRF-TOKEN ->
    <meta  name = "_csrf_header"  content = "${_csrf.headerName}" />
    <style>
        <@c.logCss></@c.logCss>
    </style>


    <@h.headScript></@h.headScript>


</head>
<body>


<div class="container">
    <div class="d-flex justify-content-center h-100">
        <h2><a href="?lang=ru">РУ   </a> </h2>
        <h2><a href="?lang=en">EN</a></h2>
        <div class="card">
            <div class="card-header">
                <h3><@spring.message 'sign_in_page.sign_in.title'/></h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i   class="fab fa-facebook-square"></i></span>
                    <span onclick="location.href='http://localhost:8080/oauth2/authorization/google'"><i class="fab fa-google-plus-square"></i></span>
                    <span><i class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <div class="card-body">
                <form  action='someaction' method="POST" onsubmit="return false;">
                    <div  class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="email" class="form-control" placeholder=<@spring.message 'sign_in_page.email.title'/> name="email" id="login">

                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>

                        <input type="password" class="form-control" placeholder=<@spring.message 'sign_in_page.password.title'/> name="password" id="password">
                    </div>
                    <div class="row">
                        <h1><a style="color: #fc6d00" id ="x"></a></h1>
                    </div>
                    <div class="row align-items-center remember">
                        <input type="checkbox" id="chex" name="chex" value="true"><@spring.message 'sign_in_page.rememberMe.title'/>
                    </div>
                    <div class="form-group">
                        <input id="submit" type="submit" value="<@spring.message 'sign_in_page.login.title'/>" class="btn float-right login_btn" />
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links" style="color: red">
                    Don't have an account?<a href="registration"><@spring.message 'sign_in_page.registration.title'/></a>
                </div>
                <div class="d-flex justify-content-center">
                    <a >Forgot your password?</a>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<@b.bodyScript></@b.bodyScript>
<script src="<@spring.url '/js/login.js' />"></script>

</body>
</html>
