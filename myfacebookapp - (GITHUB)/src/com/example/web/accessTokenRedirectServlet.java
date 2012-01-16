package com.example.web;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.example.model.facebookDataBean;

import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.GitHubTokenResponse;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.message.types.GrantType;


import com.restfb.FacebookClient;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.Page;
import com.restfb.types.User;
import com.restfb.types.User.Education;
import com.restfb.types.Post;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.exception.FacebookException;
import com.restfb.Connection;
import com.restfb.Parameter;

public class accessTokenRedirectServlet extends HttpServlet{
String apiKey = "YOUR_APIKEY_HERE";
String secretKey = "YOUR_SECRETKEY_HERE";
String redirectUri = "YOUR_REDIRECT_URI_HERE";
String accessToken, expiresIn;
User user,user2;
Page page;
Connection<User> myFriends;
Connection<Post> myFeeds;
ArrayList<String> f = new ArrayList<String>();

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
response.setContentType("text/html");
PrintWriter out = response.getWriter();
out.println("<br><h4>we are getting data</h4>");
String code = request.getParameter("code");
out.println("<br>code: " + code);
out.println("<br>");
try{
OAuthClientRequest requestOAuth = OAuthClientRequest
			.tokenLocation("https://graph.facebook.com/oauth/access_token")
			.setGrantType(GrantType.AUTHORIZATION_CODE)
			.setClientId(apiKey)
			.setClientSecret(secretKey)
			.setRedirectURI(redirectUri)
			.setCode(code)
			.buildBodyMessage();
			
OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        
		GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(requestOAuth, GitHubTokenResponse.class);
		accessToken = oAuthResponse.getAccessToken();
		expiresIn = oAuthResponse.getExpiresIn();
		}catch(OAuthSystemException ae){ae.printStackTrace();}
		 catch(OAuthProblemException pe){pe.printStackTrace();}

//out.println("<br>Access Token: " + accessToken);
//out.println("<br>Expires In: " + expiresIn);

try{
FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
myFriends = facebookClient.fetchConnection("me/friends", User.class);
myFeeds = facebookClient.fetchConnection("me/home", Post.class);

   for(User myFriend: myFriends.getData()){
        f.add(myFriend.getName());
	     out.println("<br>id: " + myFriend.getId() + " Name: " + myFriend.getName());	
		}
	//	out.println("<br>");
        out.println("<br>f count: " + f.size()); 	
}catch(FacebookException e){e.printStackTrace();}

facebookDataBean fdb = new facebookDataBean();
fdb.setName("zishan ali khan");
HttpSession session = request.getSession();
if(session != null){
session.setAttribute("myfdb", fdb);
session.setAttribute("yourFriends", f);
session.setAttribute("feeds", myFeeds);
RequestDispatcher view = request.getRequestDispatcher("result.jsp");
view.forward(request, response);
f.clear();
//out.println("<br>I am in");
}else{
//out.println("<br>Session Over");
}
}
}
