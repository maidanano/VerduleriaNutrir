package models;

public class Products {

    private int id;
    private String code;
    private String name;
    private String decription;
    private double unit_price;
    private int product_quantity;
    private String created;
    private String updated;
    private int category_id;
    private String category_name;

    public Products() {
    }

    public Products(int id, String code, String name, String decription, double unit_price, int product_quantity, String created, String updated, int category_id, String category_name) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.decription = decription;
        this.unit_price = unit_price;
        this.product_quantity = product_quantity;
        this.created = created;
        this.updated = updated;
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

}
