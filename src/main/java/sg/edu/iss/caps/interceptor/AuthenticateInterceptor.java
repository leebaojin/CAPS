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
import sg.edu.iss.caps.service.UserSessionService;

@Component
public class AuthenticateInterceptor implements HandlerInterceptor {
	
	@Autowired
	HttpSession session;
	
	private List<String> noblocklist = Arrays.asList("/home","/login","/logout");
	private List<String> adminlist = Arrays.asList("/lecturer-management");
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //response.sendRedirect(request.getContextPath() + "/" + offerRedirect);
		String path = request.getRequestURI();
		if(path.endsWith(".css")||path.endsWith(".js")) {
			return true;
		}
		for(String p : noblocklist) {
			if(path.startsWith(p)) {
				return true;
			}
		}
		User user = UserSessionService.findUser(session);
		if(user == null) {
			//Redirect if user is not found
			response.sendRedirect("/home");
	        return false;
		}
		if(user.getRole() == Role.ADMIN) {
			return true;
		}
		response.sendRedirect("/home");
        return false;
    }
 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
      
    }
 
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exceptionIfAny){
       // NO operation. 
    }

}
