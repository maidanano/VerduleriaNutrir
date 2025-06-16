package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SalesDao {

    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar una nueva venta
    public boolean registerSaleQuery(int customer_id, int employee_id, double total) {

        String query = "INSERT INTO sales (customer_id, employee_id, total, sale_date) VALUES (?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    //Registrar Detalle de Venta
    public boolean registerSaleDetailQuery(int product_id, int sale_id, int sale_quantity, double sale_price, double sale_subtotal) {

        String query = "INSERT INTO sales_details (product_id, sale_id, sale_quantity, sale_price, sale_subtotal) VALUES (?,?,?,?,?)";

        try {
            Connection conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product_id);
            pst.setInt(2, sale_id);
            pst.setInt(3, sale_quantity);
            pst.setDouble(4, sale_price);
            pst.setDouble(5, sale_subtotal);
            System.out.println("En SalesDao funcion registerSaleDEtailQuery el prodcut_id: " + product_id);
            pst.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("Error al registrar detalle de venta: " + e.getMessage());

            return false;
        }
    }

    //Obtener id de la venta
    public int saleId() {

        int id = 0;
        String query = "SELECT MAX(id) AS id FROM sales";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    //Listar todas las ventas realizadas
    public List<Sales> listAllSalesQuery() {

        List<Sales> list_sales = new ArrayList<>();

        String query = "SELECT s.id AS invoice, c.full_name AS customer, e.full_name AS employee, s.total, s.sale_date FROM sales s INNER JOIN customers c ON s.customer_id = c.id INNER JOIN employees e ON s.employee_id = e.id ORDER BY s.id ASC";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            //Recorrer 
            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("invoice"));
                sale.setCustomer_name(rs.getString("customer"));
                sale.setEmployee_name(rs.getString("employee"));
                sale.setTotal_to_pay(rs.getDouble("total"));
                sale.setSale_date(rs.getString("sale_date"));

                list_sales.add(sale);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_sales;
    }

    //Obtener Venta por Id
    public Sales getSaleById(int saleId) {
        Sales sale = new Sales();
        String query = "SELECT s.id, e.full_name AS employee_name, c.full_name AS customer_name, s.sale_date "
                + "FROM sales s "
                + "INNER JOIN employees e ON s.employee_id = e.id "
                + "INNER JOIN customers c ON s.customer_id = c.id "
                + "WHERE s.id = ?";
        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, saleId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    sale.setId(rs.getInt("id"));
                    sale.setEmployee_name(rs.getString("employee_name"));
                    sale.setCustomer_name(rs.getString("customer_name"));
                    sale.setSale_date(rs.getString("sale_date"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return sale;
    }

    //Listar las Ventas para imprimir factura
  public List<Sales> listSalesDetailsquery(int id) throws SQLException {
    List<Sales> list_sales = new ArrayList<>();
    String query = "SELECT sa.sale_date, sade.sale_price, sade.sale_quantity, sade.sale_subtotal, em.username AS employee_name, "
            + "pro.name AS product_name, cu.full_name FROM sales sa INNER JOIN sales_details sade ON sa.id = sade.sale_id "
            + "INNER JOIN products pro ON sade.product_id = pro.id INNER JOIN employees em ON sa.employee_id = em.id "
            + "INNER JOIN customers cu ON sa.customer_id = cu.id WHERE sa.id = ?";

    try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setInt(1, id);
        try (ResultSet rs = pst.executeQuery()) {
            // Validar si la consulta devuelve resultados
            if (!rs.isBeforeFirst()) { 
                System.err.println("❌ No se encontraron detalles de venta para ID: " + id);
                return list_sales; // Evita procesar un resultado vacío
            }

            while (rs.next()) {
                Sales sale = new Sales();
                sale.setProduct_name(rs.getString("product_name"));
                sale.setSale_quantity(rs.getInt("sale_quantity"));
                sale.setSale_price(rs.getDouble("sale_price"));
                sale.setSale_subtotal(rs.getDouble("sale_subtotal"));
                sale.setCustomer_name(rs.getString("full_name"));
                sale.setSale_date(rs.getString("sale_date"));
                sale.setEmployee_name(rs.getString("employee_name"));
                
                list_sales.add(sale);
                System.out.println("✅ Producto encontrado en factura: " + sale.getProduct_name()); // Log de depuración
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error en listSalesDetailsquery: " + e.getMessage());
    }

    return list_sales;
}

}
