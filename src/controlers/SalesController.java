package controlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Customers;
import models.CustomersDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import models.Products;
import models.ProductsDao;
import models.Purchases;
import models.Sales;
import models.SalesDao;
import views.PrintCompras;
import views.PrintVentas;
import views.SystemView;

public class SalesController implements ActionListener, MouseListener {

    private Sales sale;
    private SalesDao saleDao;
    private SystemView views;
    private Products product = new Products();
    private ProductsDao productDao = new ProductsDao();
    private DefaultTableModel model = new DefaultTableModel();
    String rol = rol_user;

    public SalesController(Sales sale, SalesDao saleDao, SystemView views) {
        this.sale = sale;
        this.saleDao = saleDao;
        this.views = views;

        // Eventos de botones
        this.views.btn_add_product_sale.addActionListener(this);
        this.views.btn_confirm_sale.addActionListener(this);
        this.views.btn_remove_sale.addActionListener(this);
        this.views.btn_new_sale.addActionListener(this);
        this.views.btnSales.addMouseListener(this);
        this.views.btnSales.addMouseListener(this);
        this.views.btnReports.addMouseListener(this);
        this.views.table_all_sales.addMouseListener(this);

        // Eventos de entrada en los campos de texto
        this.views.txt_sale_product_code.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarProducto();
                }
            }
        });

        this.views.txt_sale_customer_id.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarCliente();
                }
            }
        });

        this.views.txt_sale_quantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularSubtotal();
            }
        });
    }

    // 🔍 Buscar Producto por Código
    private void buscarProducto() {
        if (!views.txt_sale_product_code.getText().isEmpty()) {
            String codigo = views.txt_sale_product_code.getText().trim();
            product = productDao.searchCode(codigo);

            if (product.getName() != null) {
                views.txt_sale_product_name.setText(product.getName());
                views.txt_sale_product_id.setText(String.valueOf(product.getId()));
                views.txt_sale_price.setText(String.valueOf(product.getUnit_price()));
                views.txt_sale_stock.setText(String.valueOf(product.getProduct_quantity()));
                views.txt_sale_quantity.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                views.txt_sale_product_code.setText("");
            }
        }
    }

    // 🔍 Buscar Cliente por ID
    private void buscarCliente() {
        if (!views.txt_sale_customer_id.getText().isEmpty()) {
            int customerId = Integer.parseInt(views.txt_sale_customer_id.getText());
            Customers customer = new CustomersDao().searchCustomer(customerId);

            if (customer.getFull_name() != null) {
                views.txt_sale_customer_name.setText(customer.getFull_name());
            } else {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                views.txt_sale_customer_id.setText("");
            }
        }
    }

    // 🧮 Calcular Subtotal al ingresar cantidad
    private void calcularSubtotal() {
        if (!views.txt_sale_quantity.getText().isEmpty() && !views.txt_sale_price.getText().isEmpty()) {
            try {
                int cantidad = Integer.parseInt(views.txt_sale_quantity.getText());
                double precio = Double.parseDouble(views.txt_sale_price.getText());
                double subtotal = cantidad * precio;

                views.txt_sale_subtotal.setText(String.valueOf(subtotal));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese una cantidad válida.");
                views.txt_sale_subtotal.setText("0.0");
            }
        }
    }

    // 🧹 Limpiar la tabla de ventas
    private void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) views.sales_table.getModel();
        int filas = model.getRowCount();
        for (int i = filas - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    // 🧹 Limpiar la tabla de TODAS las ventas
    private void limpiarTablaAllSales() {
        DefaultTableModel model = (DefaultTableModel) views.table_all_sales.getModel();
        int filas = model.getRowCount();
        for (int i = filas - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    // 🧹 Limpiar los campos de venta
    private void limpiarCamposVenta() {
        views.txt_sale_product_code.setText("");
        views.txt_sale_product_name.setText("");
        views.txt_sale_quantity.setText("");
        views.txt_sale_product_id.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_subtotal.setText("");
        views.txt_sale_stock.setText("");
    }

    // 🧹 Calculr el Total de la Venta
    private void calcularTotalVenta() {
        double total = 0.0;
        DefaultTableModel model = (DefaultTableModel) views.sales_table.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            Object subtotalObj = model.getValueAt(i, 4); // ✅ Acceder a la columna de subtotal

            if (subtotalObj != null && !subtotalObj.toString().isEmpty()) {
                try {
                    double subtotal = Double.parseDouble(subtotalObj.toString());
                    total += subtotal;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error en el formato del subtotal.");
                    return; // ✅ Salir si hay un error de conversión
                }
            }
        }

        views.txt_sale_total_to_pay.setText(String.valueOf(total)); // ✅ Se establece el total correctamente
    }

    // 🧹 Agregar un producto a la venta
    private void agregarProductoVenta() {
        if (views.txt_sale_quantity.getText().isEmpty() || views.txt_sale_price.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese cantidad y precio antes de agregar.");
            return;
        }

        calcularSubtotal(); // ✅ Se calcula el subtotal antes de agregar el producto

        DefaultTableModel model = (DefaultTableModel) views.sales_table.getModel();
        Object[] fila = new Object[6];

        fila[0] = views.txt_sale_product_code.getText();
        fila[1] = views.txt_sale_product_name.getText();
        fila[2] = views.txt_sale_quantity.getText();
        fila[3] = views.txt_sale_price.getText();
        fila[4] = views.txt_sale_subtotal.getText(); // ✅ Se guarda el subtotal correctamente  
        fila[5] = views.txt_sale_customer_name.getText();

        model.addRow(fila);
        views.sales_table.setModel(model);

        calcularTotalVenta(); // ✅ Se llama después de agregar el producto
        limpiarCamposVenta(); // ✅ Se limpian los campos para la siguiente entrada
    }

    // ✅ Registrar productos vendidos y actualizar stock
    private void registrarDetalleVenta(int sale_id) {
        Products product;
        for (int i = 0; i < views.sales_table.getRowCount(); i++) {
            String product_code = views.sales_table.getValueAt(i, 0).toString().trim(); // ✔️ código como string
            System.out.println("Buscando producto con código: '" + product_code + "'"); // NUEVO LOG
            int sale_quantity = Integer.parseInt(views.sales_table.getValueAt(i, 2).toString());
            double sale_price = Double.parseDouble(views.sales_table.getValueAt(i, 3).toString());
            double sale_subtotal = sale_quantity * sale_price;

            // ✔️ Buscar producto por código
            product = productDao.searchCode(product_code);
            if (product == null) {
                System.err.println("❌ No se encontró producto con código: '" + product_code + "'");
                continue; // IMPORTANTE: evitás que intente usar null y tire NullPointerException
            }
            int product_id = product.getId(); // ✔️ obtenés el ID del producto desde la base

            saleDao.registerSaleDetailQuery(product_id, sale_id, sale_quantity, sale_price, sale_subtotal);

            // ✔️ Actualizar stock después de la venta
            int nuevo_stock = product.getProduct_quantity() - sale_quantity;
            productDao.updateStockQuery(nuevo_stock, product_id);
        }
    }

    // ✅ Limpiar la tabla y los campos para iniciar una nueva venta
    private void nuevaVenta() {
        limpiarTabla();
        limpiarCamposVenta();
    }

    // ✅ Eliminar un producto de la tabla de ventas y actualizar el total
    private void eliminarProductoVenta() {
        int filaSeleccionada = views.sales_table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) views.sales_table.getModel();
            model.removeRow(filaSeleccionada);
            calcularTotalVenta();  // ✅ Recalcular el total después de eliminar
            limpiarCamposVenta();  // ✅ Limpiar los campos de entrada
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.");
        }
    }

    // 💰 Procesar venta y guardar en la base de datos
    private void vender() throws SQLException {
        if (views.sales_table.getRowCount() > 0) {
            if (views.txt_sale_total_to_pay.getText().isEmpty() || views.txt_sale_customer_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un cliente y verifique el total antes de vender.");
                return;
            }

            try {
                int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText());
                int employee_id = id_user;
                double total = Double.parseDouble(views.txt_sale_total_to_pay.getText());

                if (saleDao.registerSaleQuery(customer_id, employee_id, total)) {
                    int sale_id = saleDao.saleId();
                    registrarDetalleVenta(sale_id);
                    JOptionPane.showMessageDialog(null, "Venta registrada correctamente.");
                    limpiarTabla();
                    limpiarCamposVenta();

                    //Instanciar la vista Print
                    PrintVentas print = new PrintVentas(sale_id);
                    print.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la venta.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Verifique que todos los datos sean válidos.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La tabla de ventas está vacía.");
        }

    }

    //✅ Listar todas las Ventas
    public void listAllSales() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Sales> list = saleDao.listAllSalesQuery();
            model = (DefaultTableModel) views.table_all_sales.getModel();
            Object[] row = new Object[5];
            //Recorrer con ciclo for

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCustomer_name();
                row[2] = list.get(i).getEmployee_name();
                row[3] = list.get(i).getTotal_to_pay();
                row[4] = list.get(i).getSale_date();
                model.addRow(row);
            }
            views.table_all_sales.setModel(model);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_sale) {
            agregarProductoVenta();
        } else if (e.getSource() == views.btn_confirm_sale) {
            try {
                vender();
            } catch (SQLException ex) {
                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == views.btn_remove_sale) {
            eliminarProductoVenta();
        } else if (e.getSource() == views.btn_new_sale) {
            nuevaVenta();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.btnSales) {
            if (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Auxiliar")) {
                views.jTabbedPane1.setSelectedIndex(4);
                limpiarTabla();
            }
        } else if (e.getSource() == views.btnReports) {
            views.jTabbedPane1.setSelectedIndex(5);
            limpiarTablaAllSales();
            listAllSales();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
