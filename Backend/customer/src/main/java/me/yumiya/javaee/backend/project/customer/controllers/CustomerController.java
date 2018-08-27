package me.yumiya.javaee.backend.project.customer.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.yumiya.javaee.backend.project.customer.data.Customer;
import me.yumiya.javaee.backend.project.customer.data.CustomerDAO;

@Path("customers")
public class CustomerController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List <Customer> listCustomer(){

		try {
			CustomerDAO customerDAO = new CustomerDAO();
			return customerDAO.listar();
		} catch(SQLException | ClassNotFoundException ex) {
			Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/")
	public Customer getChamado(@PathParam("id") long id) throws ClassNotFoundException, SQLException {

		try {
			CustomerDAO chamadoDAO = new CustomerDAO();
			return chamadoDAO.selecionar(id);
			
		} catch(SQLException | ClassNotFoundException ex) {
			Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response create(Customer chamado) {
		
		try {
			//chamado.setStatus(Status.NOVO);
			
			CustomerDAO chamadoDAO = new CustomerDAO();
			chamadoDAO.inserir(chamado);
			return Response.status(Response.Status.OK).build();
			
		} catch(SQLException | ClassNotFoundException ex) {
			Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}
		
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/")
	public Response update(@PathParam("id") long id, Customer customer) {
		
		try {
			//chamado.setStatus(Status.PENDENTE);
			
			CustomerDAO chamadoDAO = new CustomerDAO();
			customer.setId(id);
			chamadoDAO.alterar(customer);
			return Response.status(Response.Status.OK).build();
			
		} catch(SQLException | ClassNotFoundException ex) {
			Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}
		
	}

	@DELETE
	@Path("{id}/")
	public Response delete(@PathParam("id") long id) {
		
		try {
			CustomerDAO chamadoDAO = new CustomerDAO();
			chamadoDAO.excluir(id);
			return Response.status(Response.Status.OK).build();
			
		} catch(SQLException | ClassNotFoundException ex) {
			Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}	
	}
	
	@PUT
	@Path("{id}/")
	public Response concluir(@PathParam("id") long id) {
		
		try {
			CustomerDAO customerDAO = new CustomerDAO();
			
			Customer c = customerDAO.selecionar(id);
			//c.setStatus(Status.FECHADO);
			
			customerDAO.alterar(c);
			return Response.status(Response.Status.OK).build();
			
		} catch(SQLException | ClassNotFoundException ex) {
			Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);			
		}
		
	}
	
}
