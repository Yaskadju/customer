package me.yumiya.javaee.backend.project.customer.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.yumiya.javaee.backend.project.customer.infra.ConexaoJDBC;
import me.yumiya.javaee.backend.project.customer.infra.ConexaoPostgresJDBC;;

public class CustomerDAO {
	
	private final ConexaoJDBC conexao;
	
	public CustomerDAO() throws ClassNotFoundException, SQLException {
		this.conexao = new ConexaoPostgresJDBC();
	}
	
	public Long inserir(Customer customer) throws SQLException {
		
		Long id = null;
		String sqlQuery = "INSERT INTO customer(first_name, last_name, phone, email, address, city, state) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
		
		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setString(1, customer.getFirst_name());
			stmt.setString(2, customer.getLast_name());
			stmt.setString(3, customer.getPhone());
			stmt.setString(4, customer.getEmail());
			stmt.setString(5, customer.getAddress());
			stmt.setString(6, customer.getCity());
			stmt.setString(7, customer.getState());
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				id = rs.getLong("id");
			}
			
			this.conexao.commit();
			
		} catch(SQLException e) {
			this.conexao.rollback();
			throw e;
		}
		
		return id;
	}
	
	public int alterar(Customer customer) throws SQLException, ClassNotFoundException {
		
		String sqlQuery = "UPDATE customer SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ?, city = ?, state = ? WHERE id = ?";
		int linhasAfetadas = 0;
		
		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setString(1, customer.getFirst_name());
			stmt.setString(2, customer.getLast_name());
			stmt.setString(3, customer.getPhone());
			stmt.setString(4, customer.getEmail());
			stmt.setString(5, customer.getAddress());
			stmt.setString(6, customer.getCity());
			stmt.setString(7, customer.getState());
			stmt.setLong(8, customer.getId());
			
			linhasAfetadas = stmt.executeUpdate();
			this.conexao.commit();
			
		} catch (SQLException e) {
			this.conexao.rollback();
			throw e;
		}
		
		return linhasAfetadas;
	}
	
	public int excluir(long id) throws SQLException {
		
		int linhasAfetadas = 0;
		String sqlQuery = "DELETE FROM customer WHERE id = ?";
		
		try {
			
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setLong(1, id);
			linhasAfetadas = stmt.executeUpdate();
			this.conexao.commit();
			
		} catch(SQLException e) {
			this.conexao.rollback();
			throw e;
		}
		
		return linhasAfetadas;
	}
	
	public Customer selecionar(long id) throws SQLException {
		
		String sqlQuery = "SELECT * FROM customer WHERE id = ?";
		
		try {
			
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return parser(rs);
			}
			
		} catch(SQLException e) {
			throw e;
		}
		
		this.conexao.commit();
		
		return null;
	}
	
	private Customer parser(ResultSet resultSet) throws SQLException {
		Customer c = new Customer();
		
		c.setId(resultSet.getLong("id"));
		c.setFirst_name(resultSet.getString("first_name"));
		c.setLast_name(resultSet.getString("last_name"));
		c.setPhone(resultSet.getString("phone"));
		c.setEmail(resultSet.getString("email"));
		c.setAddress(resultSet.getString("address"));
		c.setCity(resultSet.getString("city"));
		c.setState(resultSet.getString("state"));
		
		return c;
	}
	
	public List <Customer> listar() throws SQLException, ClassNotFoundException {
		
		String sqlQuery = "SELECT * FROM customer ORDER BY id";
		
		try {
			PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
			ResultSet rs = stmt.executeQuery();
			
			List <Customer> customers = new ArrayList<>();
			
			while(rs.next()) {
				customers.add(parser(rs));
			}
			
			this.conexao.commit();
			
			return customers;
			
		} catch(SQLException e) {
			this.conexao.rollback();
			throw e;
		}
		
	}
	
}
