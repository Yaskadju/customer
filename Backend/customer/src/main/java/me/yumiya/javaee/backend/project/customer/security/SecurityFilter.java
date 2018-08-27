package me.yumiya.javaee.backend.project.customer.security;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import me.yumiya.javaee.backend.project.customer.data.Login;
import me.yumiya.javaee.backend.project.customer.infra.ConexaoJDBC;
import me.yumiya.javaee.backend.project.customer.infra.ConexaoPostgresJDBC;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic "; 
	private static final String SECURED_URL_PREFIX = "customers";
	
	private ConexaoJDBC conexao;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List <String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if(authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString = Base64.decodeAsString(authToken);

				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();

//				if("user1".equals(username) && "password1".equals(password)) {
//					return;
//				}
				
				try {
					
					this.conexao = new ConexaoPostgresJDBC();
					
					Long id = null;
					Login login = new Login();
					String sqlQuery = "SELECT username, password FROM login WHERE username = ? AND password = ?";
					
					PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
					stmt.setString(1, login.getUsername());
					stmt.setString(2, login.getPassword());
					
					ResultSet rs = stmt.executeQuery();
					
					if(rs.next()) {
						
						if(login.getUsername().equals(username) && login.getPassword().equals(password)) {
							return;
						}
						
					}
					
					this.conexao.commit();
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
					
			}

			Response unauthorizedStatus = Response
					.status(Response.Status.UNAUTHORIZED)
					.entity("User cannot access the resource")
					.build();

			requestContext.abortWith(unauthorizedStatus);

		}
	}
}