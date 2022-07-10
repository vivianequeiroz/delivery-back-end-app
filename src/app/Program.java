package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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

		// interface -> class
		Map<Long, Order> mapOrder = new HashMap<>();
		Map<Long, Product> mapProducts = new HashMap<>();

		while (rs.next()) {

			Long orderId = rs.getLong("order_id");
			if (mapOrder.get(orderId) == null) {
				Order order = instantiateOrder(rs);
				mapOrder.put(orderId, order);
			}

			Long productId = rs.getLong("product_id");
			if (mapProducts.get(productId) == null) {
				Product product = instantiateProduct(rs);
				mapProducts.put(productId, product);
			}

			mapOrder.get(orderId).getProducts().add(mapProducts.get(productId));

		}

		for (Long orderId : mapOrder.keySet()) {
			System.out.println(mapOrder.get(orderId));

			for (Product product : mapOrder.get(orderId).getProducts()) {
				System.out.println(product);
			}

			System.out.println();
		}

	}

	private static Product instantiateProduct(ResultSet rs) throws SQLException {

		Product product = new Product();
		product.setId(rs.getLong("product_id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getDouble("price"));
		product.setDescription(rs.getString("description"));
		product.setImageUri(rs.getString("image_uri"));

		return product;

	}

	private static Order instantiateOrder(ResultSet rs) throws SQLException {

		Order order = new Order();
		order.setId(rs.getLong("order_id"));
		order.setLatitude(rs.getDouble("latitude"));
		order.setLongitude(rs.getDouble("longitude"));
		order.setMoment(rs.getTimestamp("moment").toInstant());
		order.setStatus(OrderStatus.values()[rs.getInt("status")]);

		return order;

	}

}
