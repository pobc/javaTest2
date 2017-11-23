<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
  <head>

      <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
      <title>2</title>
      <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/1.9.9/skins/default/index-min.css">
      <script type="text/javascript" src="https://g.alicdn.com/de/prismplayer/1.9.9/prism-min.js">

      </script>
  </head>
  <body>
oooo
  <%--
  http://szztca.b0.upaiyun.com/mp4/20170818/ztcbffff0000@@20170818181404752.mp4
  --%>
<div id="J_prismPlayer" class="prism-player"></div>
<!— prism-player为h5播放器皮肤的钩子类名，请务必加上 —>
<button id="J_clickToPlay" style="font-size: 20px;width: 90px;height: 90px;" type="button">播放</button>
<script>
    // 初始化播放器
    var player = new prismplayer({
        id: "J_prismPlayer", // 容器id
        source: "<%=basePath%>/output.mp4",// 视频地址
        autoplay: true,    //自动播放：否
        width: "100%",       // 播放器宽度
        height: "400px"      // 播放器高度
    });
    var clickDom = document.getElementById("J_clickToPlay");
    clickDom.addEventListener("click", function(e) {
        // 调用播放器的play方法
        player.play();
    });
    // 监听播放器的pause事件
    player.on("pause", function() {
        //alert("播放器暂停啦！");
    });
</script>
  </body>
</html>
