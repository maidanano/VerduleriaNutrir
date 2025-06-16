/*
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



public class ProductsDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar producto
    public boolean registerProductQuery(Products product){
        String query = "INSERT INTO products (code, name, description, unit_price, created, updated, category_id) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDecription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar el producto" + e);
            return false;
        }
    }
    
    
   //Listar producto
    public List listProductsQuery(String value){
        List<Products> list_products = new ArrayList();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca WHERE pro.category_id = ca.id";
        String query_search_product = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.name LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();              
            }else{
                pst = conn.prepareStatement(query_search_product);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDecription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_products;
    }
   
   //Modificar producto 
    public boolean updateProductQuery(Products product){
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, updated= ?, category_id= ? WHERE id = ? ";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDecription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategory_id());
            pst.setInt(7, product.getId());
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del producto");
            return false;
        }
    }
    
   //Eliminar producto
    public boolean deleteProductQuery(int id){
        String query = "DELETE FROM products WHERE id = "+ id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No puede eliminar a un producto que tenga relacion con otra tabla");
            return false;
        }
    }
      
   //Buscar producto
    public Products searchProduct(int id){
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        Products product = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDecription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));           
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
   //Buscar producto por código
    public Products searchCode(int code){
        String query = "SELECT pro.id, pro.name FROM products pro WHERE pro.code = ?";
        Products product = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));           
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
   //Traer la cantidad de productos
    public Products searchId(int id){
        String query = "SELECT pro.product_quantity FROM products pro WHERE pro.id = ?";
        Products product = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                product.setProduct_quantity(rs.getInt("product_quantity"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
   //Actualizar Stock
    public Boolean updateStockQuery(int amount, int product_id){
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";
    
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    
    }
    
}
 */
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

public class ProductsDao {

    // Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // Registrar producto
    public boolean registerProductQuery(Products product) {
        String query = "INSERT INTO products (code, name, description, unit_price, product_quantity, created, updated, category_id, percentage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDecription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5, product.getProduct_quantity());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.setInt(8, product.getCategory_id());
            pst.setInt(9, product.getPercentage());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto: " + e.getMessage());
            return false;
        }
    }

    // Listar productos
    public List<Products> listProductsQuery(String value) {
        List<Products> list_products = new ArrayList<>();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id";

        String query_search_product = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.name LIKE ?";

        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(value.isEmpty() ? query : query_search_product)) {

            if (!value.isEmpty()) {
                pst.setString(1, "%" + value + "%");
            }
            rs = pst.executeQuery();

            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDecription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());
        }
        return list_products;
    }

    // Modificar producto
    public boolean updateProductQuery(Products product) {
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, product_quantity = ?, updated = ?, category_id = ?, percentage = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDecription());
            pst.setDouble(4, product.getUnit_price());
            pst.setInt(5, product.getProduct_quantity());
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.setInt(8, product.getId());
            pst.setInt(9, product.getPercentage());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar producto: " + e.getMessage());
            return false;
        }
    }

    // Eliminar producto
    public boolean deleteProductQuery(int id) {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar un producto con relación en otra tabla: " + e.getMessage());
            return false;
        }
    }

    // Buscar producto por ID
    public Products searchProduct(int id) {
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        Products product = new Products();

        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setDecription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
                product.setPercentage(rs.getInt("percentage"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e.getMessage());
        }
        return product;
    }

    // Buscar producto por código (¡CORREGIDO!)
    public Products searchCode(String code) {
        Products product = null;
        String query = "SELECT * FROM products WHERE code = ?";

        try {
            Connection conn = cn.getConnection();
            System.out.println("Buscando producto en BD con código: " + code); // ✅ Antes de la consulta

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, code);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getString("code"));
                product.setName(rs.getString("name"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));

                System.out.println("✅ Producto encontrado: " + product.getName()); // ✅ Confirmación
            } else {
                System.err.println("❌ No se encontró producto con código: " + code); // ✅ Caso de error
            }

        } catch (SQLException e) {
            System.err.println("❌ Error en searchCode: " + e.getMessage());
        }

        return product;
    }

    // Actualizar stock
    public Boolean updateStockQuery(int amount, int product_id) {
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";

        try (Connection conn = cn.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
}
