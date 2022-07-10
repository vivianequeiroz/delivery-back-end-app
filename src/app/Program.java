package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import entities.Product;

public class Program {

	public static void main(String[] args) throws SQLException {

		Connection conn = DB.getConnection();

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("select * from tb_product");

		while (rs.next()) {

			Product product = instantiateProduct(rs);

			System.out.println(product);
		}
	}

	private static Product instantiateProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getLong("Id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getDouble("price"));
		product.setDescription(rs.getString("description"));
		product.setImageUri(rs.getString("image_uri"));
		return product;
	}
}
