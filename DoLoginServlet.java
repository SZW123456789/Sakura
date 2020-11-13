package szw;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.InputStreamReader;
import java.nio.charset.Charset;


@WebServlet("/DoLoginServle")
public class DoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		
		String LoginURL = "http://localhost:8081/Homework/NewFile.jsp";
		String file="D:\\eclipse-workspace\\user.txt";
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(new FileInputStream(file),Charset.forName("utf-8")));
		
		String as;
		boolean flg=false;
		while((as=bufferReader.readLine())!=null)
		{
		
			as=as.replace("\n", "");
			
			if(as.substring(0, as.indexOf(" ")).equals(username))
			{    
			
				flg=true;
				
				String pass=as.substring(as.indexOf(" ")+1);
				
				if(pass.equals(password)) {
					
					if(remember!=null) 
					{
						
						Cookie cookie=new Cookie("username",username);
						Cookie cookie1=new Cookie("password",password);
						
						response.addCookie(cookie);
						response.addCookie(cookie1);
					}
					
					
					request.setAttribute("username", username);
					request.setAttribute("password",password);
					
					
					request.getRequestDispatcher("/index.jsp").forward(request,response);	
					bufferReader.close();
					break;
				}
				else
				{
					request.setAttribute("text","密码不正确");
					request.getRequestDispatcher("/LoginURL.jsp").forward(request,response);
					bufferReader.close();
					break;
				}
			}
			
		 }
		if(!flg)
		{
			out.write("登录失败，用户名或者密码错误");
			response.setHeader("refresh", "2;url="+request.getContextPath()+"/LoginURL");
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}


