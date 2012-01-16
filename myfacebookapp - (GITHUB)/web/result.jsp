<%@page import="java.util.*"%>
<%@page import="com.example.model.*"%>
<%@page import="com.restfb.types.Post"%>
<%@page import="com.restfb.Connection"%>
<%@page import="java.util.*"%>
<html>
<head><title>Facebook Personal Edition</title>
<style type="text/css">
h4{
color: blue;
font-style: italic;
}
</style>
</head>
<body>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_GB/all.js#xfbml=1&appId=YOUR_APPID_HERE";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<table style="background-color: #3B5998" width="100%" height="80px">
<tr>
<td align="center" style="font-weight:bold">
<a href="http://alihome.mine.nu:8081/myfacebookapp/" style="text-decoration:none"><font color="white" size="4">Facebook Personal Edition</font></a>
</td>
</tr>
<tr>
<td valign="bottom">&nbsp;
<a href="http://alihome.mine.nu:8081/myfacebookapp/" style="text-decoration:none"><font style="color:white">Home</font></a>
</td>
</tr>
</table>

<%! ArrayList<String> friends; %>

<%!
public void jspInit(){
friends = new ArrayList<String>();
}
%>

<%
HttpSession sess = request.getSession();
friends = (ArrayList)sess.getAttribute("yourFriends");
synchronized(sess){

int k=1;
out.println("<table border='1' width='100%'>");
out.println("<tr>");
out.println("<th>Facebook Friends</th>");
out.println("<th>Facebook Wall Posts</th>");
out.println("</tr>");
out.println("<tr>");
out.println("<td valign='top' width='20%'>");

out.println("<table border='1' width='100%'>");
out.println("<tr>");
out.println("<td>");
for(int i=0; i<friends.size(); i++){
			out.println("<font color='blue'>" + k + ": " + friends.get(i) + "</font><br>");
			k++;
		}
out.println("</td>");
out.println("</tr>");
out.println("<tr>");
out.println("<br>Total Friends: " + friends.size());
out.println("</tr>");
out.println("</table>");		
		friends.clear();		
        out.println("<br>");	
}
out.println("</td>");
out.println("<td>");

out.println("<table border='1'>");
out.println("<tr>");
out.println("<td valign='top'>");
Connection<Post> myFeeds = (Connection<Post>)sess.getAttribute("feeds");
 for(List<Post> myFeedConnectionPage: myFeeds){
	    	 for(Post post: myFeedConnectionPage){
	    		 out.println("<br>Name:" + post.getFrom().getName());
	             out.println("<br>Post: "+post.getMessage());
				 out.println("<br>Likes:" + post.getLikesCount());
				 out.println("<br>Post published on:" + post.getCreatedTime());	            
				out.println("<br>");
				out.println("<br>");
				}
				}
out.println("</td>");
out.println("<td valign='top'>");
out.println("<div class='fb-live-stream' data-event-app-id='YOUR_APPID_HERE' data-width='350' data-height='300' data-always-post-to-friends='false'></div>");
out.println("</td>");
out.println("</tr>");
out.println("</table>");
out.println("</td>");
out.println("</tr>");
out.println("</table>");
%>
</body>
</html>