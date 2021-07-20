<#ftl encoding="UTF-8"/>
<!------ Include the above in your HEAD tag ---------->
<#import "mcrs/headScripts.ftlh" as h>

<#import  "spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.js"></script>
    <@h.headScript></@h.headScript>

    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/chatCss.css' />"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/fileUpload.css' />"/>

    <meta name="_csrf" content="${_csrf.token}"/>
    <! - имя заголовка по умолчанию - X-CSRF-TOKEN ->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>


</head>
<!--Coded With Love By Mutiullah Samim-->
<body>
<div class="container-fluid h-100">
    <div class="row justify-content-center h-100">
        <div class="col-md-4 col-xl-3 chat">
            <div class="card mb-sm-3 mb-md-0 contacts_card">
                <div class="card-header">
                    <div class="input-group">
                        <input type="text" placeholder="Search..." name="" class="form-control search">
                        <div class="input-group-prepend">
                            <span class="input-group-text search_btn"><i class="fas fa-search"></i></span>
                        </div>
                    </div>
                </div>
                <div class="card-body contacts_body">
                    <ui class="contacts">
                        <#list  ListChats as user >
                            <li class="active" id="${user.id}">
                                <a onclick="changeChat(${mainUser.id},${user.id},<#if user.nickname??> '${user.nickname}'<#else>'${user.email}'</#if>)">
                                    <div class="d-flex bd-highlight">
                                        <div class="img_cont">
                                            <img src="https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg"
                                                 class="rounded-circle user_img">
                                            <span class="online_icon"></span>
                                        </div>
                                        <div class="user_info">
                                            <#if user.nickname??>
                                                <span>
                                                ${user.nickname}
                                            </span>
                                                <p> ${user.nickname} is online</p>
                                                <p>  ${user.id} is online</p>

                                            <#else>
                                                <span>
                                                ${user.email}
                                            </span>
                                                <p> ${user.email}</p>
                                                <p> ${user.id}</p>



                                            </#if>

                                        </div>
                                    </div>
                                </a>
                            </li>
                        </#list>
                    </ui>
                </div>
                <div class="card-footer"></div>
            </div>
        </div>
        <div class="col-md-8 col-xl-6 chat">
            <div class="card">
                <div class="card-header msg_head">
                    <div class="d-flex bd-highlight">
                        <div class="img_cont">
                            <img src="https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg"
                                 class="rounded-circle user_img">
                            <span class="online_icon"></span>
                        </div>
                        <div class="user_info">
                            <span id="chat-right-header">Click to users on right side to start chat with</span>
                            <p>Messages</p>
                        </div>
                        <div class="video_cam">
                            <span><i class="fas fa-video"></i></span>
                            <span><i class="fas fa-phone"></i></span>
                        </div>
                    </div>
                    <span id="action_menu_btn"><i class="fas fa-ellipsis-v"></i></span>
                    <div class="action_menu">
                        <ul>
                            <li><i class="fas fa-user-circle"></i> View profile</li>
                            <li><i class="fas fa-users"></i> Add to close friends</li>
                            <li><i class="fas fa-plus"></i> Add to group</li>
                            <li><i class="fas fa-ban"></i> Block</li>
                        </ul>
                    </div>
                </div>
                <div class="card-body msg_card_body" id="messageBox">


                </div>
                <div class="card-footer">
                    <div class="input-group">
                        <div class="input-group-append">
                            <span class="input-group-text attach_btn">

                                <input name="myFile" class="upload-photo-input" id="upload-photo-input" type="file">
                            </span>
                            <div class="image-preview" id="image-preview">
                                <img src="" alt="Image Preview" class="image-preview__image" id="image-preview__image">
                            </div>
                        </div>
                        <textarea id="message-input" name="" class="form-control type_msg"
                                  placeholder="Type your message..."></textarea>
                        <div class="input-group-append">
                            <span id="message-send-btn" class="input-group-text send_btn"><i
                                        class="fas fa-location-arrow"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<script src="<@spring.url '/js/chat.js' />"></script>
<script src="<@spring.url '/js/photosUpload.js' />"></script>
</body>
</html>