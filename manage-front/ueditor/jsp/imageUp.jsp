<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%--<%@ page import="ueditor.Uploader" %>--%>
<%@ page import="ueditor.OssUploader" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.zcpj.servlets.SystemInitParameter" %>
<%@ page import="java.io.InputStream" %>
<%
    InputStream is = SystemInitParameter.class.getClassLoader()
            .getResourceAsStream("zc.properties");
    Properties p = new Properties();
    p.load(is);
    String ossPath = p.getProperty("imageWeb.readUrl");
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    OssUploader up = new OssUploader(request);
    up.setSavePath("upload"); //保存路径
    String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};  //允许的文件类型
    up.setAllowFiles(fileType);
    up.setMaxSize(10000);        //允许的文件最大尺寸，单位KB
    up.upload();
    //   response.getWriter().print(up.getPath());
    response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+up.getPath()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"','path':'"+up.getUrl()+"'}");
    request.setAttribute("ossPath",up.getPath());
%>
<%--<%
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    Uploader up = new Uploader(request);
    up.setSavePath("upload");
    String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
    up.setAllowFiles(fileType);
    up.setMaxSize(10000); //单位KB
    up.upload();
    response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
%>--%>
