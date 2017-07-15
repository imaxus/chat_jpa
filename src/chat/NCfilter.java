package chat;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
    
/**
 * Zapobiega cache'owaniu, ¿eby zawsze po zamkniêciu strony trzeba by³o siê zalogowac ponownie
 * @author mina
 *
 */
public class NCfilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse sresponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)sresponse;
        
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
 
        response.setDateHeader("Expires", 0);
        
        chain.doFilter(request, sresponse);
    }
    
    public void destroy() {	
    }
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
