package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import entities.Order;
import entities.OrderStatus;
import entities.Product;

public class Program {

	public static void main(String[] args) throws SQLException {

		Connection conn = DB.getConnection();

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("SELECT * FROM tb_order "
				+ "INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
				+ "INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");

		while (rs.next()) {

			// Product product = instantiateProduct(rs);

			Order orders = instantiateOrder(rs);

			System.out.println(orders);
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

	private static Order instantiateOrder(ResultSet rs) throws SQLException {

		Order order = new Order();
		order.setId(rs.getLong("Id"));
		order.setLatitude(rs.getDouble("latitude"));
		order.setLongitude(rs.getDouble("longitude"));
		order.setMoment(rs.getTimestamp("moment").toInstant());
		order.setStatus(OrderStatus.values()[rs.getInt("status")]);

		return order;

	}

}
