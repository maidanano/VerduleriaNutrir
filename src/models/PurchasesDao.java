package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PurchasesDao {

    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar compra
    public boolean registerPurchaseQuery(int supplier_id, int employee_id, double total) {
        String query = "INSERT INTO purchases (employee_id, total, created, supplier_id) VALUES (?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(4, supplier_id);
            pst.setInt(1, employee_id);
            pst.setDouble(2, total);
            pst.setTimestamp(3, datetime);
            pst.execute();
            System.out.println("El registerPurchaseQuery está ok, se acaba de ejecutar bien");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insetar la compra" + e);
            return false;
        }
    }

    //Registrar detalles de la compra
    public boolean registerPurchaseDetailQuery(int purchase_id, double purchase_price, int purchase_amount,
        double purchase_subtotal, int product_id) {

    String query = "INSERT INTO purchase_details (purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id) VALUES (?, ?, ?, ?, ?)";

    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);

        // Mostrar los valores antes de ejecutar la consulta
        System.out.println("🔍 Preparando inserción de detalles de compra...");
        System.out.println("➡️ Valores recibidos: purchase_id=" + purchase_id + ", purchase_price=" + purchase_price
                + ", purchase_amount=" + purchase_amount + ", purchase_subtotal=" + purchase_subtotal
                + ", product_id=" + product_id);

        pst.setInt(1, purchase_id);
        pst.setDouble(2, purchase_price);
        pst.setInt(3, purchase_amount);
        pst.setDouble(4, purchase_subtotal);
        pst.setInt(5, product_id);

        // Confirmar si la ejecución se realiza correctamente
        boolean executed = pst.executeUpdate() > 0;

        if (executed) {
            System.out.println("✅ Detalles de compra insertados correctamente.");
        } else {
            System.err.println("❌ No se insertaron detalles de compra en la base de datos.");
        }

        return executed;
    } catch (SQLException e) {
        System.err.println("❌ Error al registrar los detalles de la compra: " + e.getMessage());
        return false;
    }
}

    //Obtener id de la compra
    public int purchaseId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    //Listar todas las compras realizadas
    public List listAllPurchasesQuery() {
        List<Purchases> list_purchase = new ArrayList();
        String query = "SELECT pu.*, su.name AS supplier_name FROM purchases pu, suppliers su "
                + "WHERE pu.supplier_id = su.id ORDER BY pu.id ASC";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Purchases purchase = new Purchases();
                purchase.setId(rs.getInt("id"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setTotal(rs.getDouble("total"));
                purchase.setCreated(rs.getString("created"));
                list_purchase.add(purchase);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchase;
    }

    //Listar las compras para imprimir factura
    public List listPurchaseDetailQuery(int id) {
        List<Purchases> list_purchases = new ArrayList();
        String query = "SELECT pu.created, pude.purchase_price, pude.purchase_amount, pude.purchase_subtotal, su.name AS supplier_name,\n"
                + "pro.name AS product_name, em.full_name FROM purchases pu INNER JOIN purchase_details pude ON pu.id = pude.purchase_id \n"
                + "INNER JOIN products pro ON pude.product_id = pro.id INNER JOIN suppliers su ON pu.supplier_id = su.id \n"
                + "INNER JOIN employees em ON pu.employee_id = em.id WHERE pu.id = ?";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Purchases purchase = new Purchases();
                purchase.setProduct_name(rs.getString("product_name"));
                purchase.setPurchase_amount(rs.getInt("purchase_amount"));
                purchase.setPurchase_price(rs.getDouble("purchase_price"));
                purchase.setPurchase_subtotal(rs.getDouble("purchase_subtotal"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setCreated(rs.getString("created"));
                purchase.setPurcharser(rs.getString("full_name"));
                list_purchases.add(purchase);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchases;
    }

}
