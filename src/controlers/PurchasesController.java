package controlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicCombobox;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import models.Products;
import models.ProductsDao;
import models.Purchases;
import models.PurchasesDao;
import views.PrintCompras;
import views.PrintVentas;
import views.SystemView;

public class PurchasesController implements KeyListener, ActionListener, MouseListener {

    private Purchases purchase;
    private PurchasesDao purchaseDao;
    private SystemView views;
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;
    //Instanciar el modelo producto
    Products product = new Products();
    ProductsDao productDao = new ProductsDao();
    String rol = rol_user;

    public PurchasesController(Purchases purchase, PurchasesDao purchaseDao, SystemView views) {
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.views = views;

        //Botón de agregar
        this.views.btn_add_product_to_buy.addActionListener(this);

        //Botón de comprar
        this.views.btn_confirm_purchase.addActionListener(this);

        //Botón de Eliminar Compra
        this.views.btn_remove_purchase.addActionListener(this);

        this.views.txt_purchase_product_code.addKeyListener(this);
        this.views.txt_purchase_price.addKeyListener(this);
        this.views.btn_new_purchase.addActionListener(this);
        this.views.btnPurchases.addMouseListener(this);
        this.views.btnReports.addMouseListener(this);
        this.views.table_all_purchases.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_to_buy) {
            DynamicCombobox supplier_cmb = (DynamicCombobox) views.cmb_purchase_supplier.getSelectedItem();
            int supplier_id = supplier_cmb.getId();

            if (getIdSupplier == 0) {
                getIdSupplier = supplier_id;
            } else {
                if (getIdSupplier != supplier_id) {
                    JOptionPane.showMessageDialog(null, "No puede realizar una misma compra a varios proveedores");
                } else {
                    int amount = Integer.parseInt(views.txt_purchase_amount.getText());
                    String product_name = views.txt_purchase_product_name.getText();
                    double price = Double.parseDouble((views.txt_purchase_price.getText()));
                    int purchase_id = Integer.parseInt(views.txt_purchase_id.getText());
                    String supplier_name = views.cmb_purchase_supplier.getSelectedItem().toString();

                    if (amount > 0) {
                        temp = (DefaultTableModel) views.purchases_table.getModel();
                        for (int i = 0; i < views.purchases_table.getRowCount(); i++) {
                            if (views.purchases_table.getValueAt(i, 1).equals(views.txt_purchase_product_name.getText())) {
                                JOptionPane.showMessageDialog(null, "El producto ya está registrado en la tabla de compras");
                                return;
                            }
                        }
                        ArrayList list = new ArrayList();
                        String product_code = views.txt_purchase_product_code.getText().trim(); // ✅ Obtener código del producto correctamente
                        list.add(product_code);
                        list.add(product_name);
                        list.add(amount);
                        list.add(price);
                        list.add(amount * price);
                        list.add(supplier_name);

                        Object[] obj = new Object[list.size()];
                        for (int j = 0; j < list.size(); j++) {
                            obj[j] = list.get(j);
                        }

                        temp.addRow(obj);
                        views.purchases_table.setModel(temp);
                        cleanFieldsPurchases();
                        views.cmb_purchase_supplier.setEditable(true);
                        views.txt_purchase_product_code.requestFocus();
                        calculatePurchase();

                    }
                }
            }
        } else if (e.getSource() == views.btn_confirm_purchase) {

            insertPurchase();
        } else if (e.getSource() == views.btn_remove_purchase) {
            model = (DefaultTableModel) views.purchases_table.getModel();
            model.removeRow(views.purchases_table.getSelectedRow());
            calculatePurchase();
            views.txt_purchase_product_code.requestFocus();
        } else if (e.getSource() == views.btn_new_purchase) {
            cleanTableTemp();
            cleanFieldsPurchases();
        }
    }

    private void insertPurchase() {
        double total = Double.parseDouble(views.txt_purchase_total_pay.getText());
        int employee_id = id_user;

        // Registrar la compra en la base de datos
        if (purchaseDao.registerPurchaseQuery(getIdSupplier, employee_id, total)) {
            int purchase_id = purchaseDao.purchaseId();
            System.out.println("➡️ Compra registrada con ID: " + purchase_id);

            // Verificar si hay productos en la tabla de compras
            if (views.purchases_table.getRowCount() == 0) {
                System.err.println("❌ Error: No hay productos en la lista de compras.");
                JOptionPane.showMessageDialog(null, "No hay productos en la compra.");
                return;
            }

            // Recorrer la tabla y registrar los detalles de compra
            for (int i = 0; i < views.purchases_table.getRowCount(); i++) {
                // Ajustamos la columna correcta para obtener el código del producto
                String product_code = views.purchases_table.getValueAt(i, 0).toString().trim(); // Ajustar índice si es necesario
                System.out.println("🔍 Código extraído desde la tabla: " + product_code);

                // Verificación en consola para asegurar que extraemos el código correcto
                System.out.println("🔍 Código extraído desde la tabla: " + product_code);

                int purchase_amount = Integer.parseInt(views.purchases_table.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(views.purchases_table.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;

                // Buscar producto por código
                product = productDao.searchCode(product_code);

                if (product != null) {
                    int product_id = product.getId();
                    System.out.println("✅ Producto encontrado: " + product.getName() + " (ID: " + product_id + ")");
                    System.out.println("📝 Guardando detalle de compra: cantidad=" + purchase_amount + ", precio=" + purchase_price);

                    // Registrar detalle de la compra
                    boolean detailRegistered = purchaseDao.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id);

                    if (!detailRegistered) {
                        System.err.println("❌ Error al registrar el detalle de compra para el producto: " + product.getName());
                    }

                    // Actualizar stock del producto
                    int updatedAmount = product.getProduct_quantity() + purchase_amount;
                    productDao.updateStockQuery(updatedAmount, product_id);
                } else {
                    System.err.println("❌ Producto no encontrado con código: " + product_code);
                    JOptionPane.showMessageDialog(null, "Producto no encontrado: " + product_code);
                }
            }

            // Mensaje de éxito y limpieza de datos
            cleanTableTemp();
            JOptionPane.showMessageDialog(null, "Compra generada con éxito");
            cleanFieldsPurchases();

            // Mostrar impresión de la compra
            PrintCompras print = new PrintCompras(purchase_id);
            print.setVisible(true);
        } else {
            System.err.println("❌ Error al registrar la compra.");
            JOptionPane.showMessageDialog(null, "Error al registrar la compra.");
        }
    }

    //Método para listar las compras realizadas
    public void listAllPurchases() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Purchases> list = purchaseDao.listAllPurchasesQuery();
            model = (DefaultTableModel) views.table_all_purchases.getModel();
            Object[] row = new Object[4];
            //Recorrer con ciclo for
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getSupplier_name_product();
                row[2] = list.get(i).getTotal();
                row[3] = list.get(i).getCreated();
                model.addRow(row);
            }
            views.table_all_purchases.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.btnPurchases) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(3);
                cleanTable();

            } else {
                views.jTabbedPane1.setEnabledAt(1, false);
                views.btnPurchases.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tiene privilegios de Administrador para ingresar a esta vista");

            }
        } else if (e.getSource() == views.btnReports) {
            views.jTabbedPane1.setSelectedIndex(5);
            cleanTable();
            listAllPurchases();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_product_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_purchase_product_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese el código del producto a comprar");
                } else {
                    String code = views.txt_purchase_product_code.getText(); // ✅ ahora es String
                    product = productDao.searchCode(code); // ✅ correcto

                    if (product.getName() != null) {
                        views.txt_purchase_product_name.setText(product.getName());
                        views.txt_purchase_id.setText(String.valueOf(product.getId()));
                        views.txt_purchase_amount.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado");
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_price) {
            int quantity;
            double price = 0;

            if (views.txt_purchase_amount.getText().equals("")) {
                quantity = 1;
                views.txt_purchase_price.setText("" + price);
            } else {
                quantity = Integer.parseInt(views.txt_purchase_amount.getText());
                price = Double.parseDouble(views.txt_purchase_price.getText());

                views.txt_purchase_subtotal.setText("" + price * quantity);
            }
        }
    }

    //Limpiar campos
    public void cleanFieldsPurchases() {
        views.txt_purchase_product_name.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_amount.setText("");
        views.txt_purchase_product_code.setText("");
        views.txt_purchase_subtotal.setText("");
        views.txt_purchase_id.setText("");
        views.txt_purchase_total_pay.setText("");
    }

    //Calcular total a pagar
    public void calculatePurchase() {
        double total = 0.0;
        int numRow = views.purchases_table.getRowCount();

        for (int i = 0; i < numRow; i++) {
            //Pasar el índice de la a columna que sumará
            total = total + Double.parseDouble(String.valueOf(views.purchases_table.getValueAt(i, 4)));
        }
        views.txt_purchase_total_pay.setText("" + total);
    }

    //Método de Limpiar tabla temporal
    public void cleanTableTemp() {
        for (int i = 0; i < temp.getRowCount(); i++) {
            temp.removeRow(i);
            i = i - 1;
        }
    }

    //Método Limpiar tabla 
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
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
