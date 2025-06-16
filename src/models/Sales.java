
package models;

public class Sales {
    private int id;
    private String sale_date;
    private double total_to_pay;
    private int customer_id;
    private String customer_name;
    private int employee_id;
    private String employee_name;
    private String product_name;
    private double sale_price;
    private double sale_subtotal;
    private int sale_quantity;

    public Sales() {
    }

    public Sales(int id, String sale_date, double total_to_pay, int customer_id, String customer_name, int employee_id, String employee_name, String product_name, double sale_price, double sale_subtotal, int sale_quantity) {
        this.id = id;
        this.sale_date = sale_date;
        this.total_to_pay = total_to_pay;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.product_name = product_name;
        this.sale_price = sale_price;
        this.sale_subtotal = sale_subtotal;
        this.sale_quantity = sale_quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

    public double getTotal_to_pay() {
        return total_to_pay;
    }

    public void setTotal_to_pay(double total_to_pay) {
        this.total_to_pay = total_to_pay;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public double getSale_subtotal() {
        return sale_subtotal;
    }

    public void setSale_subtotal(double sale_subtotal) {
        this.sale_subtotal = sale_subtotal;
    }

    public int getSale_quantity() {
        return sale_quantity;
    }

    public void setSale_quantity(int sale_quantity) {
        this.sale_quantity = sale_quantity;
    }

    
    
    
}