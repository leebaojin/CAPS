package sg.edu.iss.caps.interceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.util.UserSessionUtil;

@Component
public class AuthenticateInterceptor implements HandlerInterceptor {
	
	@Autowired
	HttpSession session;
	
	private List<String> noblocklist = Arrays.asList("/home","/login","/logout");
	private List<String> adminlist = Arrays.asList("/manage");
	private List<String> studentlist = Arrays.asList("/student");
	private List<String> lecturerlist = Arrays.asList("/lecturer");
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //Get path
		String path = request.getRequestURI();
		
		//Allow all request for css and js to pass
		if(path.endsWith(".css")||path.endsWith(".js")) {
			return true;
		}
		
		for(String p : noblocklist) {
			if(path.startsWith(p)) {
				return true;
			}
		}
		
		//Check user with session
		User user = UserSessionUtil.findUser(session);
		if(user == null) {
			//Redirect if user is not found
			response.sendRedirect("/home");
	        return false;
		}
		
		if(user.getRole() == Role.ADMIN) {
			for(String p : adminlist) {
				if(path.startsWith(p)) {
					return true;
				}
			}
		}
		else if(user.getRole() == Role.STUDENT) {
			return true;
		}
		else if(user.getRole() == Role.LECTURER) {
			return true;
		}
		
		//Redirect home for all other web
		response.sendRedirect("/home");
		
		//response.sendRedirect(request.getContextPath() + "/" + redirection);
        return false;
    }
 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
      
    }
 
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exceptionIfAny){
       // NO operation. 
    }

}
