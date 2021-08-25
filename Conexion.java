package login;


import CRUD.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.*;


public class Conexion{
    
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://db4free.net:3306/almacenitlafinal";
    public static final String USUARIO = "estuditlafinal";
    public static final String CONTRASENA = "itla123.";
    public boolean resultado = false;
    private DefaultTableModel DT;
    
    
    
    public static Connection getConnection(){
        Connection Conect = null;
        try{
            Class.forName(DRIVER);
            Conect = (Connection)DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        
        }catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error al conectar con la BD" + e);
        }
        return Conect;
    }
    
    private DefaultTableModel setDatos(){
        DT = new DefaultTableModel();
        DT.addColumn("ID");
        DT.addColumn("Nombre de Usuario");
        DT.addColumn("Nombre");
        DT.addColumn("Apellido");
        DT.addColumn("Telefono");
        DT.addColumn("Correo Electronico");
        
        return DT;        
    }
    
    private DefaultTableModel setDatosProductos(){
        DT = new DefaultTableModel();
        DT.addColumn("ID");
        DT.addColumn("Nombre");
        DT.addColumn("Marca");
        DT.addColumn("Categoria");
        DT.addColumn("Precio");
        DT.addColumn("Cantidad Disponible");
        
        return DT;        
    }
    
    
    public void validarDatosLogin(String usuario, String contrasena){
        frmUsuariosRegistrados frmUR = new frmUsuariosRegistrados();
        frmRegistroUsuarios frmRU = new frmRegistroUsuarios();
        frmPantallaPrincipal frmPanPrin = new frmPantallaPrincipal();
        frmLogin frmlogin = new frmLogin();
        Connection Conect = getConnection();
        
        try{
        String sql = "SELECT * FROM usuarios WHERE UserName = '"+usuario+"' and Password = '"+contrasena+"' ";
        Statement st = Conect.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
            if(rs.next()){
                resultado = true;
                }
            if(resultado == true){
                    frmPanPrin.setVisible(true);
                    frmlogin.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Debe ingresar su usuario y contraseña, si no está registrado debe registrarse");
                    frmlogin.setVisible(true);
            }

        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"Error al conectar con la DB" + e);
        }finally{
            CerrarConexion();
        }
    }
    
    
    
    public void CerrarConexion(){
        Connection conect = getConnection();
        try{
            conect.close();
        }catch(SQLException e){
         JOptionPane.showMessageDialog(null,"error al cerrar conexion "+ e);
        }
        
    }
    
    public void RegistrarUsuarios(String nombreUsuario, String nombre, String apellido, String telefono, String correo, String contrasena){
            Connection CN = getConnection();
            String sql = "INSERT INTO usuarios (UserName, Nombre, Apellido, Telefono, Email, Password) values(?,?,?,?,?,?)";
        
            try{
            PreparedStatement PS;
            PS = CN.prepareStatement(sql);
            PS.setString(1, nombreUsuario);
            PS.setString(2, nombre);
            PS.setString(3, apellido);
            PS.setString(4, telefono);
            PS.setString(5, correo);
            PS.setString(6, contrasena);
            int respuesta = PS.executeUpdate();
            
            if(respuesta > 0){
                JOptionPane.showMessageDialog(null,"Registro guardado de manera exitosa");
            }

            }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Error al guardar en la Base de Datos "+ e);
            }finally{
                CerrarConexion();
            }
        }
    
        public void RegistrarProductos(String nombre, String marca, String categoria, double precio, double cantidad){
            Connection CN = getConnection();
            String sql = "INSERT INTO productos (NombreProducto, MarcaProducto, CategoriaProducto, PrecioProducto, StockProducto) values(?,?,?,?,?)";
        
            try{
            PreparedStatement PS;
            PS = CN.prepareStatement(sql);
            PS.setString(1, nombre);
            PS.setString(2, marca);
            PS.setString(3, categoria);
            PS.setDouble(4, precio);
            PS.setDouble(5, cantidad);
            int respuesta = PS.executeUpdate();
            
            if(respuesta > 0){
                JOptionPane.showMessageDialog(null,"Registro guardado de manera exitosa");
            }

            }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Error al guardar en la Base de Datos "+ e);
            }finally{
                CerrarConexion();
            }
        }
        
        public void actualizarUsuarios(String ID, String nombreUsuario, String nombre, String apellido, String telefono, String correo){
            frmUsuariosRegistrados frmURr = new frmUsuariosRegistrados();
            Connection CN = getConnection();
            Statement ST;
            
            String sql = "UPDATE usuarios SET UserName='"+nombreUsuario+"', Nombre='"+nombre+"', Apellido='"+apellido+"', "
                       + "Telefono='"+telefono+"', Email='"+correo+"' WHERE IdUser ="+ID ;
        
            try{
            ST=CN.createStatement();
            ST.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Registro actualizado de manera exitosa");
            
            
            
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al guardar en la Base de Datos: "+e.getMessage());
            }finally{
                CerrarConexion();
            }
        }
    
    
        public void eliminarUsuarios(String ID){
            frmUsuariosRegistrados frmURr = new frmUsuariosRegistrados();
            Connection CN = getConnection();
            String sql = "DELETE FROM usuarios WHERE IdUser= '"+ID+"'";
        
            try{
            PreparedStatement PS;
            PS = CN.prepareStatement(sql);
            int respuesta = PS.executeUpdate();
            
            if(respuesta > 0){
                JOptionPane.showMessageDialog(null,"Registro eliminado de manera exitosa");
                
            }

            }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"error al guardar en la Base de Datos "+ e);
            }finally{
                CerrarConexion();
            }
        }
        
        
    
        
        
        public DefaultTableModel getDatos(){
        Connection CN = getConnection();
            String SQLSELECT = "SELECT * from usuarios";
            
            
            try{
                
                setDatos();
                Statement sta = CN.createStatement();
                ResultSet rs = sta.executeQuery(SQLSELECT);
                Object[] filas = new Object[6];
                while(rs.next()){
                    filas[0]= rs.getInt(1);
                    filas[1]= rs.getString(2);
                    filas[2]= rs.getString(3);
                    filas[3]= rs.getString(4);
                    filas[4]= rs.getString(5);
                    filas[5]= rs.getString(6);
                    DT.addRow(filas); 
                }
           
            }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"error al guardar en la Base de Datos "+ e);
            }finally{
                CerrarConexion();
                
            }  
            return DT;
        }
        
        public DefaultTableModel getDatosProductos(){
        Connection CN = getConnection();
            String SQLSELECT = "SELECT * from productos";
            
            
            try{
                
                setDatosProductos();
                Statement sta = CN.createStatement();
                ResultSet rs = sta.executeQuery(SQLSELECT);
                Object[] filas = new Object[6];
                while(rs.next()){
                    filas[0]= rs.getInt(1);
                    filas[1]= rs.getString(2);
                    filas[2]= rs.getString(3);
                    filas[3]= rs.getString(4);
                    filas[4]= rs.getString(5);
                    filas[5]= rs.getString(6);
                    DT.addRow(filas); 
                }
           
            }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Error al guardar en la Base de Datos "+ e);
            }finally{
                CerrarConexion();
                
            }  
            return DT;
        }
    }
    
   
