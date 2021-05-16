<#ftl encoding="UTF-8"/>
<#import "mcrs/bodyScripts.ftlh" as b>

<#import "mcrs/headScripts.ftlh" as h>
<#import  "spring.ftl" as spring/>
<html>
<head>


    <title>$Title$</title>

    <@h.headScript></@h.headScript>


  <style>
      .error{
          color: red;
      }
  </style>
    <meta  name = "_csrf"  content = "${_csrf.token}" />
    <! - имя заголовка по умолчанию - X-CSRF-TOKEN ->
    <meta  name = "_csrf_header"  content = "${_csrf.headerName}" />
</head>
<body>

<div class="container">
    <div id="main">


        <div class="row" id="real-estates-detail">
            <div class="col-lg-4 col-md-4 col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <header class="panel-title">
                            <div class="text-center">
                                <strong>Пользователь сайта</strong>
                            </div>
                        </header>
                    </div>
                    <div class="panel-body">
                        <div class="text-center" id="author">
                            <img id="avatar" src="https://otvet.imgsmail.ru/download/249189998_8236d81a89da1a8719bf4d71eb60dd01_800.jpg" height="400" width="300">

                            <#if user.nickname??>
                            <h3 id="viewNickname">
                                    ${user.nickname}

                            </h3>
                            <#else>
                            <h3 id="viewNickname">   ${user.email}</h3>
                            </#if>



                            <small class="label label-warning">Российская Федерация</small>
                            <p>.</p>
                            <p class="sosmed-author">
                                <a href="#"><i class="fa fa-facebook" title="Facebook"></i></a>
                                <a href="#"><i class="fa fa-twitter" title="Twitter"></i></a>
                                <a href="#"><i class="fa fa-google-plus" title="Google Plus"></i></a>
                                <a href="#"><i class="fa fa-linkedin" title="Linkedin"></i></a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8 col-md-8 col-xs-12">
                <div class="panel">
                    <div class="panel-body">
                        <ul id="myTab" class="nav nav-pills">
                            <li class="active"><a href="#detail" data-toggle="tab">О пользователе</a></li>
                            <li class=""><a href="#contact" data-toggle="tab">Отправить сообщение</a></li>
                            <li class=""><a href="#settings" data-toggle="tab">Настройки профиля</a></li>
                            <li class="youTube"><a href="#youtubeApi" data-toggle="tab">YouTube search</a></li>
                        </ul>
                        <a href = "<@spring.url '/user/messenger' />" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Мессенджер</a>
                        <div id="myTabContent" class="tab-content">
                            <hr>
                            <div class="tab-pane fade active in" id="detail">
                                <h4>История профиля</h4>
                                <table class="table table-th-block">
                                    <tbody>
                                    <tr><td class="active">Зарегистрирован:</td><td>12-06-2016</td></tr>
                                    <tr><td class="active">Последняя активность:</td><td>12-06-2016 / 09:11</td></tr>
                                    <tr><td class="active">Страна:</td><td>Россия</td></tr>
                                    <tr><td class="active">Город:</td><td>Волгоград</td></tr>
                                    <tr><td class="active">Пол:</td><td>Мужской</td></tr>
                                    <tr><td class="active">Полных лет:</td><td>43</td></tr>
                                    <tr><td class="active">Семейное положение:</td><td>Женат</td></tr>
                                    <tr><td class="active">Рейтинг пользователя:</td><td><i class="fa fa-star" style="color:red"></i> <i class="fa fa-star" style="color:#ff0000"></i> <i class="fa fa-star" style="color:red"></i> <i class="fa fa-star" style="color:red"></i> 4/5</td></tr>
                                    <tr><td class="active">Плагин рейтинга:</td><td><a href="https://bootstraptema.ru/stuff/plugins_bootstrap/improvement/bootstrap_star_rating/12-1-0-73" target="_blank">http://goo.gl/bGGXuw</a></td></tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="tab-pane fade" id="contact">
                                <p></p>

                                <@spring.bind "messageForm"/>
                                <form role="form" method="post" action="sendMessage?${_csrf.parameterName}=${_csrf.token}">

                                    <div class="form-group">
                                        <input type="text" class="form-control rounded" placeholder="Укажите Ваше Имя" name="from-email" value="${user.email} " hidden>

                                    </div>

                                    <#if user.phone??>
                                    <div class="form-group">
                                        <input type="text" name="from-phone" class="form-control rounded" placeholder="(+7)(095)123456" value="${user.phone}" hidden>
                                    </div>
                                    </#if>
                                    <#if user.nickname??>
                                    <div class="form-group">
                                        <input type="text" name="from-nickname" class="form-control rounded" placeholder="(+7)(095)123456" value="${user.nickname}" hidden>
                                    </div>
                                    </#if>

                                    <div class="form-group">
                                        <label>Ник принимающего(опционально)</label>
                                        <input type="text" class="form-control rounded" placeholder="Укажите  Имя" name="to-nickname">
                                    </div>
                                    <div class="form-group">
                                        <label>Укажите email получателя</label>
                                        <@spring.formInput "messageForm.toEmail" />

                                        <@spring.showErrors "<br>" "error"/>
                                       <#-- <label>email адрес адресата(обязательно)</label>
                                        <input type="email" class="form-control rounded" placeholder="Е-майл" name="toEmail">-->
                                    </div>
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox"> Согласен с условиями
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Текст Вашего сообщения</label>
                                        <@spring.formTextarea "messageForm.message"/>
                                       <h1> <@spring.showErrors "<br>" "error"/> </h1>
                                        <#--<textarea class="form-control rounded" style="height: 100px;" name="message"></textarea>-->
                                        <p class="help-block">Текст сообщения будет отправлен пользователю</p>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" data-original-title="" title="">Отправить</button>
                                    </div>
                                </form>
                            </div>


                            <div class="tab-pane fade" id="settings">
                                <p></p>
                                <#if passNamesErrors??>
                                    <p style=".error">${passNamesErrors}</p>
                                </#if>
                                <@spring.bind "userSettingsForm"/>
                                <form role="form" method="post" action="usettings?${_csrf.parameterName}=${_csrf.token}">

                                    <@spring.formHiddenInput "userSettingsForm.id"/>


                                    <div class="form-group">
                                        <label>Псевдоним</label>
                                      <#--  <@spring.formInput "userSettingsForm.nickname name='nickname' placeholder='Укажите Ваше Имя' class='form-control rounded' value=<#if user.nickname??>'${user.nickname}' <#else>\" \"</#if>"/>-->
                                        <@spring.formInput "userSettingsForm.nickname"/>
                                        <#--   <input type="text" name="nickname" class="form-control rounded" id="nickname" placeholder="Укажите Ваше Имя" value=<#if user.nickname??>"${user.nickname}" <#else>""</#if>>-->
                                    </div>
                                    <div class="form-group">
                                        <label>Пароль</label>
                               <#--         <@spring.formInput "userSettingsForm.password class='form-control rounded' placeholder='${user.password}'" />-->
                                        <@spring.formInput "userSettingsForm.password" />
                                      <#--  <input type="text" name="password" class="form-control rounded" placeholder="Укажите Ваше Имя" value="${user.password}">-->
                                    </div>
                                  <#--  <#if user.phone??>-->
                                    <div class="form-group">

                                        <label>Ваш телефон</label>
                                        <@spring.formInput "userSettingsForm.phone" />
                                        <#--<input type="text" name="phone" class="form-control rounded" placeholder="(+7)(095)123456" value="${user.phone}">-->
                                    </div>
                                   <#-- </#if>-->
                                    <div class="form-group">
                                        <label>E-mail адрес</label>
                                        <@spring.formInput "userSettingsForm.email" />
                                    <#--    <input type="text" name="email" class="form-control rounded" placeholder="Ваш Е-майл" value="${user.email}">-->
                                    </div>

                                   <#-- <div class="form-group">
                                        <div class="checkbox">
                                            <label>
                                             &lt;#&ndash;   <input type="checkbox"> Согласен с условиями&ndash;&gt;
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>О чём вы думаете?</label>
                                       &lt;#&ndash; <textarea class="form-control rounded" style="height: 100px;"></textarea>&ndash;&gt;
                                        <p class="help-block">Все смогут это увидеть</p>
                                    </div>-->
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" data-original-title="" title="" id="saveChanges">Сохранить</button>
                                    </div>
                                </form>
                            </div>


                            <div class="tab-pane fade" id="youtubeApi">


                                <form id="employeeForm" onsubmit="return false;" method="POST">
                                    <div class="form-body">
                                        <div>
                                            <div class="mt-element-ribbon bg-grey-steel">
                                                <div class="ribbon ribbon-color-success uppercase">Search Criteria</div>
                                                <p class="ribbon-content">Please enter a keyword or phrase to search for.</p>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label>Search:</label>
                                            <div>
                                                <i  class="fa fa-exclamation tooltips" data-original-title="please enter a valid search term" data-container="body"></i>
                                                <input type="text" class="form-control" id="YBSearchCriteria" maxlength="64" placeholder="For example: tom brady"/>
                                                <span  class="help-block"></span>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="form-actions">
                                        <button type="submit" id="youTubeSearch" class="btn blue">Search YouTube</button>
                                    </div>
                                </form>

                                <div id="youTubeSearchResults">
                                    <#--<div class="list-group">
                                        <a href="#" class="list-group-item list-group-item-action active" >Active
                                            item</a>
                                        <a href="#" class="list-group-item list-group-item-action">Item</a>
                                        <a href="#" class="list-group-item list-group-item-action disabled">Disabled
                                            item</a>
                                    </div>-->
                                </div>


                            </div>




                            <div class="tab-pane fade" id="messenger">

                            </div>



                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div><!-- /.main -->
<!-- /.container -->

<@b.bodyScript></@b.bodyScript>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<script src="<@spring.url '/js/profile.js' />"></script>
<script src="<@spring.url '/js/youtubeSearch.js' />"></script>
</script>


</body>
</html>
