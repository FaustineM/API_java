package fr.ecp.sio.filrougeapi.auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter, declared in web.xml, that will intercept requests to our API and check for valid credentials before the request reaches the servlet. -->
 */
public class AuthFilter implements Filter {

    private String login;
    private String password;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do when the filter is initialized (at server's startup or first request).
    }

    // All requests and responses are forwarded to the doFilter() method.
    // It is your responsibility to call filterChain.doFilter() somewhere in the implementation for the request to continue to the servlet.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Get the HTTP Authorization header from the request and inspect it.
        String auth = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        // Check if the HTTP Authorization header is present
        if (auth == null) {
            // If authentication or authorization fails, send an HTTP error response and do NOT call filterChain.doFilter().
            ((HttpServletResponse) servletResponse).sendError(401, "Auth required");
            return;
        }

        /*
        In every request, the client must send its token to the server.
        1. extraction of the token from the incoming request
        2. Looks up the user details to perform authentication and authorization.
            a. If the token is valid, the server accepts the request.
            b. If the token is invalid, the server refuses the request.
         */

        // TODO à vérifier
        // Extract the token from the HTTP Authorization header
        String token = auth.substring("Bearer".length()).trim();

            try {

                // Validate the token
                validateToken(token);

            } catch (Exception e) {
                ((HttpServletResponse) servletResponse).sendError(401, "Token not recognized");
                return;
            }

        filterChain.doFilter(servletRequest, servletResponse);
}

    private void validateToken(String token) throws Exception {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    }

    @Override
    public void destroy() { }


}
