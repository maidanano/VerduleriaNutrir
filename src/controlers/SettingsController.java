
package controlers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static models.EmployeesDao.address_user;
import static models.EmployeesDao.email_user;
import static models.EmployeesDao.full_name_user;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.telephone_user;
import views.SystemView;


public class SettingsController implements MouseListener{

    private SystemView views;
    
    public SettingsController(SystemView views){
        this.views = views;
        this.views.btnProducts.addMouseListener(this);
        this.views.btnPurchases.addMouseListener(this);
        this.views.btnCustomers.addMouseListener(this);
        this.views.btnEmployees.addMouseListener(this);
        this.views.btnSuppliers.addMouseListener(this);
        this.views.btnCategories.addMouseListener(this);
        this.views.btnReports.addMouseListener(this);
        this.views.btnPerfil.addMouseListener(this);
        this.views.btnSales.addMouseListener(this);
        Profile();
                
            
        }
        
    
    //Asignar el perfil del usaurio
    public void Profile(){
        this.views.txt_id_profile.setText(""+id_user);
        this.views.txt_name_profile.setText(full_name_user);
        this.views.txt_address_profile.setText(address_user);
        this.views.txt_telephone_profile.setText(telephone_user);
        this.views.txt_email_profile.setText(email_user);
        
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
      
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       /* if(e.getSource() == views.btnProducts){
            views.btnProducts.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelCustomers) {
            views.jPanelCustomers.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelEmployees) {
            views.jPanelEmployees.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelSuppliers) {
            views.jPanelSuppliers.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelCategories) {
            views.jPanelCategories.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelReports) {
            views.jPanelReports.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(152, 202, 63));
        }else if(e.getSource() == views.jLabelSales) {
            views.jPanelSales.setBackground(new Color(152, 202, 63));
                
    }*/
    }

    @Override
    public void mouseExited(MouseEvent e) {
       /*if(e.getSource() == views.jLabelProducts){
         views.jPanelProducts.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelCustomers) {
            views.jPanelCustomers.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelEmployees) {
            views.jPanelEmployees.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelSuppliers) {
            views.jPanelSuppliers.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelCategories) {
            views.jPanelCategories.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelReports) {
            views.jPanelReports.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(18, 45, 61));
        }else if(e.getSource() == views.jLabelSales) {
            views.jPanelSales.setBackground(new Color(18, 45, 61));
    }*/
    }
}
    
