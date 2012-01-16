package com.example.web;

import java.io.*;
import java.net.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class codeRedirectServlet extends HttpServlet{
String apiKey = "YOUR_APIKEY_HERE";
String secretKey = "YOUR_SECRETKEY_HERE";
String redirectUri = "YOUR_REDIRECT_URI_HERE";
String urlForCode = "https://www.facebook.com/dialog/oauth?client_id="+apiKey+"&redirect_uri="+redirectUri+"&scope=email,read_stream";

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
response.setContentType("text/html");
PrintWriter out = response.getWriter();
response.sendRedirect(urlForCode);
}
}