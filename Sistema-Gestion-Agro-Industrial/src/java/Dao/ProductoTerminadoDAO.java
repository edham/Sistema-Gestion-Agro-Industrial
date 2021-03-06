/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Com.Operaciones;
import Entidades.entCalibre;
import Entidades.entCategoria;
import Entidades.entColor;
import Entidades.entEnvase;
import Entidades.entLineaProduccion;
import Entidades.entLote;
import Entidades.entProductoTerminado;
import Entidades.entProductoTerminadoTemp;
import Entidades.entReceta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rosemary
 */
public class ProductoTerminadoDAO {
    
public static List<entProductoTerminado> ListarReempacado() throws Exception
{
        List<entProductoTerminado> lista = null;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
                    String sql="SELECT PT.ID_PRODUCTO_TERMINADO,PT.CODIGO_CONTROL,PT.ID_LOTE,C.NOMBRE,E.CANT_CAJAS_PALETA \n" +
                            "FROM PRODUCTO_TERMINADO PT join CALIBRE C ON PT.ID_CALIBRE=C.ID_CALIBRE JOIN ENVASE E ON \n" +
                            "PT.ID_ENVASE=E.ID_ENVASE where PT.ESTADO=0 AND PT.REEMPAQUE=1"; 
                    
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            while(dr.next())
            {
                if(lista==null)
                    lista= new ArrayList<entProductoTerminado>();                    
                    entProductoTerminado entidad = new entProductoTerminado();
                    
                    entCalibre objCalibre = new entCalibre();
                    objCalibre.setNombre(dr.getString(4));
                    
                    entLote objLote = new entLote();
                    objLote.setId_lote(dr.getInt(3));
                    
                    entidad.setId_producto_terminado(dr.getInt(1));
                    entidad.setCodigo_control(dr.getString(2)); 
                    entidad.setObjLote(objLote);
                    entidad.setObjCalibre(objCalibre);
                    entidad.setId_dia_recepcion(dr.getInt(5));
                    lista.add(entidad);
            }

        } catch (Exception e) {
            throw new Exception("Listar "+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return lista;
    }     
public static entProductoTerminadoTemp buscarOrigen(String Codigo) throws Exception
{
        entProductoTerminadoTemp entidad = null;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
                    String sql="select PT.ID_PRODUCTO_TERMINADO,PT.CODIGO_CONTROL,PT.EMBALADOR,PT.SELECCIONADOR,"
                            + "PT.FECHA_PRODUCCION,L.CODIGO_CONTROL,LP.NOMBRE,E.NOMBRE,CA.NOMBRE,P.FECHA_PRODUCCION,"
                            + "P.CODIGO_CONTROL,P.ESTADO_PALETA from PALETA P JOIN DET_PALETA DP ON P.ID_PALETA=DP.ID_PALETA RIGHT "
                            + "JOIN PRODUCTO_TERMINADO PT ON PT.ID_PRODUCTO_TERMINADO=DP.ID_PRODUCTO_TERMINADO JOIN "
                            + "LOTE L ON L.ID_LOTE=PT.ID_LOTE JOIN LINEA_PRODUCCION LP ON PT.ID_LINEA_PRODUCCION=LP.ID_LINEA_PRODUCCION "
                            + "JOIN ENVASE E ON E.ID_ENVASE=PT.ID_ENVASE JOIN CATEGORIA C ON C.ID_CATEGORIA=PT.ID_CATEGORIA "
                            + "JOIN CALIBRE CA ON CA.ID_CALIBRE=PT.ID_CALIBRE JOIN COLOR CO ON CO.ID_COLOR=PT.ID_COLOR WHERE "
                            + "PT.CODIGO_CONTROL='"+Codigo+"'"; 
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            if(dr.next())
            {
                entidad = new entProductoTerminadoTemp();
                entidad.setId_producto_terminado(dr.getInt(1));
                entidad.setCodigo_control(dr.getString(2));
                entidad.setEmbalador(dr.getString(3));
                entidad.setSeleccionador(dr.getString(4));
                entidad.setFecha_produccion(dr.getTimestamp(5)); 
                entidad.setCodigo_control_lote(dr.getString(6));
                entidad.setNombre_linea_produccion(dr.getString(7));
                entidad.setNombre_envase(dr.getString(8));
                entidad.setNombre_calibre(dr.getString(9));
                entidad.setCodigo_control_paleta(dr.getString(11));
                entidad.setFecha_produccion_paleta(dr.getTimestamp(10)); 
                entidad.setEstado_paleta(dr.getInt(12));
            }

        } catch (Exception e) {
            throw new Exception("Listar "+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return entidad;
    }  
public static List<entProductoTerminado> Listar(int id_dia_recepcion,int idLineaProduccion) throws Exception
{
        List<entProductoTerminado> lista = null;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
                    String sql=" SELECT TOP 30 P.ID_PRODUCTO_TERMINADO,P.ID_DIA_RECEPCION,P.SELECCIONADOR,P.EMBALADOR,P.CODIGO_CONTROL,"
                            + "E.ID_ENVASE,E.NOMBRE,E.CODIGO_CONTROL,C.ID_CALIBRE,C.CODIGO_CONTROL,CAT.ID_CATEGORIA,CAT.NOMBRE,CAT.CODIGO_CONTROL,COL.ID_COLOR,COL.CODIGO_CONTROL,"
                            + "L.ID_LOTE,L.CODIGO_CONTROL,LP.ID_LINEA_PRODUCCION,LP.NOMBRE FROM PRODUCTO_TERMINADO P JOIN ENVASE E ON P.ID_ENVASE=E.ID_ENVASE "
                            + "JOIN CALIBRE C ON P.ID_CALIBRE=C.ID_CALIBRE JOIN CATEGORIA CAT ON P.ID_CATEGORIA=CAT.ID_CATEGORIA JOIN COLOR COL "
                            + "ON P.ID_COLOR=COL.ID_COLOR JOIN LOTE L ON P.ID_LOTE=L.ID_LOTE JOIN LINEA_PRODUCCION LP ON P.ID_LINEA_PRODUCCION=LP.ID_LINEA_PRODUCCION "
                            + " where P.ID_DIA_RECEPCION="+id_dia_recepcion+" and LP.ID_LINEA_PRODUCCION="
                            +idLineaProduccion+" order by P.ID_PRODUCTO_TERMINADO desc"; 
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            while(dr.next())
            {
                if(lista==null)
                    lista= new ArrayList<entProductoTerminado>();                    
                    entProductoTerminado entidad = new entProductoTerminado();
                    //Receta

                    //Envase
                    entEnvase objEnvase = new entEnvase();
                    objEnvase.setId_envase(dr.getInt(6));
                    objEnvase.setNombre(dr.getString(7));
                    objEnvase.setCodigo_control(dr.getString(8));
                    //Calibre
                    entCalibre objCalibre = new entCalibre();
                    objCalibre.setId_calibre(dr.getInt(9));
                    objCalibre.setCodigo_control(dr.getString(10));
                    //Categoria
                    entCategoria objCategoria = new entCategoria();
                    objCategoria.setId_categoria(dr.getInt(11));
                    objCategoria.setNombre(dr.getString(12));
                    objCategoria.setCodigo_control(dr.getString(13));
                    //Color
                    entColor objColor = new entColor();
                    objColor.setId_color(dr.getInt(14));
                    objColor.setCodigo_control(dr.getString(15));
                    //lote
                    //"L.ID_LOTE,L.CODIGO_CONTROL,L.NOMBRE,L.ESTADO,\n" +
                    entLote objLote = new entLote();
                    objLote.setId_lote(dr.getInt(16));
                    objLote.setCodigo_control(dr.getString(17));
                    //linea produccion
                    entLineaProduccion objLineaProduccion = new entLineaProduccion();
                    objLineaProduccion.setId_linea_produccion(dr.getInt(18));
                    objLineaProduccion.setNombre(dr.getString(19));
                    //entidad
                    entidad.setId_producto_terminado(dr.getInt(1));
                    entidad.setId_dia_recepcion(dr.getInt(2));
                    entidad.setSeleccionador(dr.getString(3));
                    entidad.setEmbalador(dr.getString(4));  
                    entidad.setCodigo_control(dr.getString(5)); 
                    entidad.setObjCalibre(objCalibre);                    
                    entidad.setObjCategoria(objCategoria);
                    entidad.setObjColor(objColor);
                    entidad.setObjEnvase(objEnvase);
                    entidad.setObjLineaProduccion(objLineaProduccion);
                    entidad.setObjLote(objLote);
                    lista.add(entidad);
            }

        } catch (Exception e) {
            throw new Exception("Listar "+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return lista;
    }          
public static List<entProductoTerminado> ListarReempaque(int idLineaProduccion) throws Exception
{
        List<entProductoTerminado> lista = null;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
                    String sql="SELECT TOP 30 P.ID_PRODUCTO_TERMINADO,P.ID_DIA_RECEPCION,P.SELECCIONADOR,P.EMBALADOR,P.CODIGO_CONTROL,\n" +
                        "E.ID_ENVASE,E.NOMBRE,E.CODIGO_CONTROL,C.ID_CALIBRE,C.CODIGO_CONTROL,CAT.ID_CATEGORIA,CAT.NOMBRE,CAT.CODIGO_CONTROL,COL.ID_COLOR,COL.CODIGO_CONTROL,\n" +
                        "L.ID_LOTE,L.CODIGO_CONTROL,LP.ID_LINEA_PRODUCCION,LP.NOMBRE FROM PRODUCTO_TERMINADO P JOIN ENVASE E ON P.ID_ENVASE=E.ID_ENVASE \n" +
                        "JOIN CALIBRE C ON P.ID_CALIBRE=C.ID_CALIBRE JOIN CATEGORIA CAT ON P.ID_CATEGORIA=CAT.ID_CATEGORIA JOIN COLOR COL \n" +
                        "ON P.ID_COLOR=COL.ID_COLOR JOIN LOTE L ON P.ID_LOTE=L.ID_LOTE JOIN LINEA_PRODUCCION LP ON P.ID_LINEA_PRODUCCION=LP.ID_LINEA_PRODUCCION \n" +
                        " where P.REEMPAQUE=1 and P.ESTADO=0 and LP.ID_LINEA_PRODUCCION="+idLineaProduccion+" order by P.ID_PRODUCTO_TERMINADO desc"; 
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            while(dr.next())
            {
                if(lista==null)
                    lista= new ArrayList<entProductoTerminado>();                    
                    entProductoTerminado entidad = new entProductoTerminado();
                    //Receta

                    //Envase
                    entEnvase objEnvase = new entEnvase();
                    objEnvase.setId_envase(dr.getInt(6));
                    objEnvase.setNombre(dr.getString(7));
                    objEnvase.setCodigo_control(dr.getString(8));
                    //Calibre
                    entCalibre objCalibre = new entCalibre();
                    objCalibre.setId_calibre(dr.getInt(9));
                    objCalibre.setCodigo_control(dr.getString(10));
                    //Categoria
                    entCategoria objCategoria = new entCategoria();
                    objCategoria.setId_categoria(dr.getInt(11));
                    objCategoria.setNombre(dr.getString(12));
                    objCategoria.setCodigo_control(dr.getString(13));
                    //Color
                    entColor objColor = new entColor();
                    objColor.setId_color(dr.getInt(14));
                    objColor.setCodigo_control(dr.getString(15));
                    //lote
                    //"L.ID_LOTE,L.CODIGO_CONTROL,L.NOMBRE,L.ESTADO,\n" +
                    entLote objLote = new entLote();
                    objLote.setId_lote(dr.getInt(16));
                    objLote.setCodigo_control(dr.getString(17));
                    //linea produccion
                    entLineaProduccion objLineaProduccion = new entLineaProduccion();
                    objLineaProduccion.setId_linea_produccion(dr.getInt(18));
                    objLineaProduccion.setNombre(dr.getString(19));
                    //entidad
                    entidad.setId_producto_terminado(dr.getInt(1));
                    entidad.setId_dia_recepcion(dr.getInt(2));
                    entidad.setSeleccionador(dr.getString(3));
                    entidad.setEmbalador(dr.getString(4));  
                    entidad.setCodigo_control(dr.getString(5)); 
                    entidad.setObjCalibre(objCalibre);                    
                    entidad.setObjCategoria(objCategoria);
                    entidad.setObjColor(objColor);
                    entidad.setObjEnvase(objEnvase);
                    entidad.setObjLineaProduccion(objLineaProduccion);
                    entidad.setObjLote(objLote);
                    lista.add(entidad);
            }

        } catch (Exception e) {
            throw new Exception("Listar "+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return lista;
    }     
public static List<entProductoTerminado> ListarPorDiaRecepccion(int id_dia_recepcion) throws Exception
{
        List<entProductoTerminado> lista = null;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
                    String sql="SELECT PT.ID_PRODUCTO_TERMINADO,PT.CODIGO_CONTROL,PT.ID_LOTE,"
                            + "C.NOMBRE,E.CANT_CAJAS_PALETA FROM "
                            + "PRODUCTO_TERMINADO PT "
                            + " join CALIBRE C ON PT.ID_CALIBRE=C.ID_CALIBRE"
                            + " JOIN ENVASE E ON PT.ID_ENVASE=E.ID_ENVASE where "
                            + "PT.ESTADO=0 AND ID_DIA_RECEPCION="+id_dia_recepcion; 
                    
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            while(dr.next())
            {
                if(lista==null)
                    lista= new ArrayList<entProductoTerminado>();                    
                    entProductoTerminado entidad = new entProductoTerminado();
                    
                    entCalibre objCalibre = new entCalibre();
                    objCalibre.setNombre(dr.getString(4));
                    
                    entLote objLote = new entLote();
                    objLote.setId_lote(dr.getInt(3));
                    
                    entidad.setId_producto_terminado(dr.getInt(1));
                    entidad.setCodigo_control(dr.getString(2)); 
                    entidad.setObjLote(objLote);
                    entidad.setObjCalibre(objCalibre);
                    entidad.setId_dia_recepcion(dr.getInt(5));
                    lista.add(entidad);
            }

        } catch (Exception e) {
            throw new Exception("Listar "+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return lista;
    }    
public static List<entProductoTerminado> ListarA(int id_dia_recepcion,int idLineaProduccion) throws Exception
{
        List<entProductoTerminado> lista = null;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
                    String sql="SELECT P.ID_PRODUCTO_TERMINADO,P.ID_DIA_RECEPCION,P.SELECCIONADOR,P.EMBALADOR,P.CODIGO_CONTROL,\n" +
                    "P.FECHA_PRODUCCION,P.ESTADO,P.USUARIO_RESPONSABLE,P.FECHA_MODIFICACION,\n" +
                    "R.ID_RECETA,R.NOMBRE,R.ESTADO,\n"+
                    "E.ID_ENVASE,E.CODIGO_CONTROL,E.NOMBRE,E.PESO,E.PESO_CARGA,E.ESTADO,\n" +
                    "C.ID_CALIBRE,C.CODIGO_CONTROL,C.NOMBRE,C.ESTADO,\n" +
                    "CAT.ID_CATEGORIA,CAT.NOMBRE,CAT.ESTADO,CAT.CODIGO_CONTROL,\n" +
                    "COL.ID_COLOR,COL.CODIGO_CONTROL,COL.NOMBRE,COL.ESTADO,\n" +
                    "L.ID_LOTE,L.CODIGO_CONTROL,L.NOMBRE,L.ESTADO,\n" +
                    "LP.ID_LINEA_PRODUCCION,LP.NOMBRE,LP.ESTADO\n" +
                    "FROM PRODUCTO_TERMINADO P\n" +
                    "JOIN ENVASE E ON P.ID_ENVASE=E.ID_ENVASE\n" +
                    "JOIN RECETA R ON E.ID_RECETA=R.ID_RECETA\n" +
                    "JOIN CALIBRE C ON P.ID_CALIBRE=C.ID_CALIBRE\n" +
                    "JOIN CATEGORIA CAT ON P.ID_CATEGORIA=CAT.ID_CATEGORIA\n" +
                    "JOIN COLOR COL ON P.ID_COLOR=COL.ID_COLOR\n" +
                    "JOIN LOTE L ON P.ID_LOTE=L.ID_LOTE\n" +
                    "JOIN LINEA_PRODUCCION LP ON P.ID_LINEA_PRODUCCION=LP.ID_LINEA_PRODUCCION"
                            + " where P.ID_DIA_RECEPCION="+id_dia_recepcion+" and LP.ID_LINEA_PRODUCCION="
                            +idLineaProduccion+" order by P.ID_PRODUCTO_TERMINADO desc"; 
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            while(dr.next())
            {
                if(lista==null)
                    lista= new ArrayList<entProductoTerminado>();                    
                    entProductoTerminado entidad = new entProductoTerminado();
                    //Receta
                    entReceta objReceta = new entReceta();
                    objReceta.setId_receta(dr.getInt(10));
                    objReceta.setNombre(dr.getString(11));
                    objReceta.setEstado(dr.getBoolean(12));
                    //Envase
                    entEnvase objEnvase = new entEnvase();
                    objEnvase.setId_envase(dr.getInt(13));
                    objEnvase.setCodigo_control(dr.getString(14));
                    objEnvase.setNombre(dr.getString(15));
                    objEnvase.setPeso(dr.getDouble(16));
                    objEnvase.setPeso_carga(dr.getDouble(17));
                    objEnvase.setEstado(dr.getBoolean(18));
                    //Calibre
                    entCalibre objCalibre = new entCalibre();
                    objCalibre.setId_calibre(dr.getInt(19));
                    objCalibre.setCodigo_control(dr.getString(20));
                    objCalibre.setNombre(dr.getString(21));
                    objCalibre.setEstado(dr.getBoolean(22));
                    //Categoria
                    entCategoria objCategoria = new entCategoria();
                    objCategoria.setId_categoria(dr.getInt(23));
                    objCategoria.setNombre(dr.getString(24));
                    objCategoria.setEstado(dr.getBoolean(25));
                    objCategoria.setCodigo_control(dr.getString(26));
                    //Color
                    entColor objColor = new entColor();
                    objColor.setId_color(dr.getInt(27));
                    objColor.setCodigo_control(dr.getString(28));
                    objColor.setNombre(dr.getString(29));
                    objColor.setEstado(dr.getBoolean(30));
                    //lote
                    //"L.ID_LOTE,L.CODIGO_CONTROL,L.NOMBRE,L.ESTADO,\n" +
                    entLote objLote = new entLote();
                    objLote.setId_lote(dr.getInt(31));
                    objLote.setCodigo_control(dr.getString(32));
                    objLote.setNombre(dr.getString(33));
                    objLote.setEstado(dr.getBoolean(34));
                    //linea produccion
                    entLineaProduccion objLineaProduccion = new entLineaProduccion();
                    objLineaProduccion.setId_linea_produccion(dr.getInt(35));
                    objLineaProduccion.setNombre(dr.getString(36));
                    objLineaProduccion.setEstado(dr.getBoolean(37));
                    //entidad
                    entidad.setId_producto_terminado(dr.getInt(1));
                    entidad.setId_dia_recepcion(dr.getInt(2));
                    entidad.setSeleccionador(dr.getString(3));
                    entidad.setEmbalador(dr.getString(4));  
                    entidad.setCodigo_control(dr.getString(5)); 
                    entidad.setFecha_produccion(dr.getTimestamp(6)); 
                    entidad.setEstado(dr.getInt(7));  
                    entidad.setUsuario_responsable(dr.getString(8)); 
                    entidad.setFecha_modificacion(dr.getTimestamp(9)); 
                    entidad.setObjCalibre(objCalibre);
                    entidad.setObjCategoria(objCategoria);
                    entidad.setObjColor(objColor);
                    entidad.setObjEnvase(objEnvase);
                    entidad.setObjLineaProduccion(objLineaProduccion);
                    entidad.setObjLote(objLote);
////                    entidad.setObjReceta(objReceta);
                    lista.add(entidad);
            }

        } catch (Exception e) {
            throw new Exception("Listar "+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return lista;
    }        
public  static int insertar(entProductoTerminado entidad) throws Exception
{
        int rpta = 0;
        Connection conn =null;
        PreparedStatement  stmt = null;
        try {
            
           String sql="INSERT INTO producto_terminado(id_dia_recepcion,id_envase,id_calibre,id_categoria"
                   + ",id_color,id_lote,id_linea_produccion,seleccionador,embalador"
                   + ",fecha_produccion,estado,usuario_responsable,fecha_modificacion,CODIGO_CONTROL,reempaque)"
                   + " VALUES(?,?,?,?,?,?,?,?,?,GETDATE(),?,?,GETDATE(),?,?);";
           
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, entidad.getId_dia_recepcion());
            stmt.setInt(2, entidad.getObjEnvase().getId_envase());
            stmt.setInt(3, entidad.getObjCalibre().getId_calibre());
            stmt.setInt(4, entidad.getObjCategoria().getId_categoria());
            stmt.setInt(5, entidad.getObjColor().getId_color());
            stmt.setInt(6, entidad.getObjLote().getId_lote());
            stmt.setInt(7, entidad.getObjLineaProduccion().getId_linea_produccion());
            stmt.setString(8, entidad.getSeleccionador());
            stmt.setString(9, entidad.getEmbalador());
            stmt.setInt(10, 0);
            stmt.setString(11, entidad.getUsuario_responsable());
            stmt.setString(12, Operaciones.getCodigoControl(true,entidad.getObjLineaProduccion().getId_linea_produccion()));
            stmt.setBoolean(13, entidad.isReempaque());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();            
            if (rs.next()){
                rpta=rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            throw new Exception("Insertar"+e.getMessage(), e);
        }
        finally{
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return rpta;
    } 
public static boolean actualizar(entProductoTerminado entidad) throws Exception
{
        boolean rpta = false;
        Connection conn =null;
        CallableStatement stmt = null;
        try {
             String sql="UPDATE producto_terminado SET ID_DIA_RECEPCION = ?,ID_ENVASE= ?,ID_CALIBRE=?,ID_CATEGORIA= ?,"
                     + "ID_COLOR = ?,ID_LOTE= ?,ID_LINEA_PRODUCCION=?,SELECCIONADOR= ?,"
                     + "EMBALADOR = ?,ESTADO= ?,"
                     + "usuario_responsable = ?,fecha_modificacion = GETDATE(),reempaque = ? WHERE id_producto_terminado = ?;";
             
            conn = ConexionDAO.getConnection();
            stmt = conn.prepareCall(sql);             
            stmt.setInt(1, entidad.getId_dia_recepcion());
            stmt.setInt(2, entidad.getObjEnvase().getId_envase());
            stmt.setInt(3, entidad.getObjCalibre().getId_calibre());
            stmt.setInt(4, entidad.getObjCategoria().getId_categoria());
            stmt.setInt(5, entidad.getObjColor().getId_color());
            stmt.setInt(6, entidad.getObjLote().getId_lote());
            stmt.setInt(7, entidad.getObjLineaProduccion().getId_linea_produccion());
            stmt.setString(8, entidad.getSeleccionador());
            stmt.setString(9, entidad.getEmbalador());
            stmt.setInt(10, entidad.getEstado());            
            stmt.setString(11, entidad.getUsuario_responsable());
            stmt.setBoolean(12, entidad.isReempaque());
            stmt.setInt(13,entidad.getId_producto_terminado());
                
           rpta = stmt.executeUpdate() == 1;
        } catch (Exception e) {
            throw new Exception("Error Actualizar "+e.getMessage(), e);
        }
        finally{
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return rpta;
    }
public static List<entProductoTerminado> GraficoAcumulativoDiaProduccion(int idLinea,int tiempo,boolean acumulativo)throws Exception
{
        List<entProductoTerminado> lista = null;
        int contador=0;
        Connection conn =null;
        CallableStatement stmt = null;
        ResultSet dr = null;
        try {
            String sql="SELECT TOP 1 PT.FECHA_PRODUCCION,DR.HORA_INICIO FROM PRODUCTO_TERMINADO PT\n" +
                        "JOIN DIA_RECEPCION DR ON PT.ID_DIA_RECEPCION=DR.ID_DIA_RECEPCION WHERE PT.ID_DIA_RECEPCION=\n" +
                        "(select top 1 ID_DIA_RECEPCION from DIA_RECEPCION order by ID_DIA_RECEPCION DESC)\n" +
                        "ORDER BY PT.FECHA_PRODUCCION DESC";

            conn = ConexionDAO.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareCall(sql);
            dr = stmt.executeQuery();

            while(dr.next())
            {
               final long ONE_MINUTE_IN_MILLIS=60000;  
               
               long fin = dr.getTimestamp(1).getTime();
               long inicio=dr.getTimestamp(2).getTime();
               long temp=0;
               
               while(fin>=inicio)
               {
                   temp=inicio + (tiempo * ONE_MINUTE_IN_MILLIS);
                    sql="SELECT COUNT(*) FROM PRODUCTO_TERMINADO WHERE (FECHA_PRODUCCION BETWEEN ? AND ?)";
                       if(idLinea>0)
                           sql+=" AND ID_LINEA_PRODUCCION="+idLinea;
                    CallableStatement csContador = conn.prepareCall(sql);
                    csContador.setTimestamp(1, new Timestamp(inicio));
                    csContador.setTimestamp(2, new Timestamp(temp));
                    
                    ResultSet drContador = csContador.executeQuery();
                       if(drContador.next())
                       {  
                           if(lista==null)
                                 lista= new ArrayList<entProductoTerminado>();  
                           contador+=drContador.getInt(1);
                           
                            entProductoTerminado entidad = new entProductoTerminado();     
                            if(acumulativo)
                                entidad.setId_producto_terminado(contador);
                                else
                                entidad.setId_producto_terminado(drContador.getInt(1));
                            entidad.setFecha_produccion(new Date(temp));                                    
                            lista.add(entidad);

                       }
                    csContador.close();
                    drContador.close();
                    inicio=temp;
                }

            }

        conn.commit();
        } catch (Exception e) {
             if (conn != null) {
                    conn.rollback();
                }
            throw new Exception("Insertar"+e.getMessage(), e);
        }
        finally{
            try {
                dr.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
         if(lista!=null)
             lista.get(0).setId_dia_recepcion(contador); 
         
        return lista;
    }  
public static List<entProductoTerminado> GraficoEnvasexLineaProduccion(int idLineaProduccion) throws Exception
{
    List<entProductoTerminado> lista = null;
    Connection conn =null;
    CallableStatement stmt = null;
    ResultSet dr = null;
    try {
        String sql="select COUNT(*),E.CODIGO_CONTROL from PRODUCTO_TERMINADO PT JOIN ENVASE E ON PT.ID_ENVASE=E.ID_ENVASE\n" +
                    "join LINEA_PRODUCCION LP ON LP.ID_LINEA_PRODUCCION=PT.ID_LINEA_PRODUCCION\n" +
                    "WHERE PT.ID_DIA_RECEPCION=(select top 1 ID_DIA_RECEPCION from DIA_RECEPCION order by ID_DIA_RECEPCION DESC)\n";
                    if(idLineaProduccion>0)
                    sql+="AND LP.ID_LINEA_PRODUCCION="+idLineaProduccion+"\n";
                    sql+= "GROUP BY E.CODIGO_CONTROL";

        conn = ConexionDAO.getConnection();
        stmt = conn.prepareCall(sql);
        dr = stmt.executeQuery();

        while(dr.next())
        {
            if(lista==null)
                lista= new ArrayList<entProductoTerminado>();
            //ProductoTerminado
            entProductoTerminado entidad = new entProductoTerminado(); 
            entidad.setId_producto_terminado(dr.getInt(1));
            entidad.setCodigo_control(dr.getString(2));
            lista.add(entidad);
        }

    } catch (Exception e) {
        throw new Exception("Listar "+e.getMessage(), e);
    }
    finally{
        try {
            dr.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}  
 public static List<entProductoTerminado> GraficoVariedadxLineaProduccion(int idLineaProduccion) throws Exception
{
    List<entProductoTerminado> lista = null;
    Connection conn =null;
    CallableStatement stmt = null;
    ResultSet dr = null;
    try {
        String sql="select COUNT(PT.ID_PRODUCTO_TERMINADO),V.CODIGO_CONTROL from PRODUCTO_TERMINADO PT JOIN LOTE L\n" +
                    "ON L.ID_LOTE=PT.ID_LOTE JOIN VARIEDAD V ON L.ID_VARIEDAD=V.ID_VARIEDAD JOIN LINEA_PRODUCCION LP\n" +
                    "ON LP.ID_LINEA_PRODUCCION=PT.ID_LINEA_PRODUCCION\n" +
                    "WHERE PT.ID_DIA_RECEPCION=(select top 1 ID_DIA_RECEPCION from DIA_RECEPCION order by ID_DIA_RECEPCION DESC)\n";
                    if(idLineaProduccion>0)
                    sql+="AND LP.ID_LINEA_PRODUCCION="+idLineaProduccion+"\n";
                    sql+="GROUP BY V.CODIGO_CONTROL";

        conn = ConexionDAO.getConnection();
        stmt = conn.prepareCall(sql);
        dr = stmt.executeQuery();

        while(dr.next())
        {
            if(lista==null)
                lista= new ArrayList<entProductoTerminado>();
            //ProductoTerminado
            entProductoTerminado entidad = new entProductoTerminado(); 
            entidad.setId_producto_terminado(dr.getInt(1));
            entidad.setCodigo_control(dr.getString(2));
            lista.add(entidad);
        }

    } catch (Exception e) {
        throw new Exception("Listar "+e.getMessage(), e);
    }
    finally{
        try {
            dr.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}  
public static List<entProductoTerminado> GraficoCalibredxLineaProduccion(int idLineaProduccion) throws Exception
{
    List<entProductoTerminado> lista = null;
    Connection conn =null;
    CallableStatement stmt = null;
    ResultSet dr = null;
    try {
        String sql="select COUNT(*),C.CODIGO_CONTROL from PRODUCTO_TERMINADO PT JOIN CALIBRE C ON PT.ID_CALIBRE=C.ID_CALIBRE\n" +
                    "join LINEA_PRODUCCION LP ON LP.ID_LINEA_PRODUCCION=PT.ID_LINEA_PRODUCCION\n" +
                    "WHERE PT.ID_DIA_RECEPCION=(select top 1 ID_DIA_RECEPCION from DIA_RECEPCION order by ID_DIA_RECEPCION DESC)\n";
                    if(idLineaProduccion>0)
                    sql+="AND LP.ID_LINEA_PRODUCCION="+idLineaProduccion+"\n";
                    sql+="GROUP BY C.CODIGO_CONTROL";

        conn = ConexionDAO.getConnection();
        stmt = conn.prepareCall(sql);
        dr = stmt.executeQuery();

        while(dr.next())
        {
            if(lista==null)
                lista= new ArrayList<entProductoTerminado>();
            //ProductoTerminado
            entProductoTerminado entidad = new entProductoTerminado(); 
            entidad.setId_producto_terminado(dr.getInt(1));
            entidad.setCodigo_control(dr.getString(2));
            lista.add(entidad);
        }

    } catch (Exception e) {
        throw new Exception("Listar "+e.getMessage(), e);
    }
    finally{
        try {
            dr.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}
public static List<entProductoTerminado> GraficoCategoriaxLineaProduccion(int idLineaProduccion) throws Exception
{
    List<entProductoTerminado> lista = null;
    Connection conn =null;
    CallableStatement stmt = null;
    ResultSet dr = null;
    try {
        String sql="select COUNT(*),C.CODIGO_CONTROL from PRODUCTO_TERMINADO PT JOIN CATEGORIA C ON PT.ID_CATEGORIA=C.ID_CATEGORIA    \n" +
                    "join LINEA_PRODUCCION LP ON LP.ID_LINEA_PRODUCCION=PT.ID_LINEA_PRODUCCION    \n" +
                    "WHERE PT.ID_DIA_RECEPCION=(select top 1 ID_DIA_RECEPCION from DIA_RECEPCION order by ID_DIA_RECEPCION DESC) \n";
                    if(idLineaProduccion>0)
                    sql+="AND LP.ID_LINEA_PRODUCCION="+idLineaProduccion+"\n";
                    sql+="GROUP BY C.CODIGO_CONTROL";

        conn = ConexionDAO.getConnection();
        stmt = conn.prepareCall(sql);
        dr = stmt.executeQuery();

        while(dr.next())
        {
            if(lista==null)
                lista= new ArrayList<entProductoTerminado>();
            //ProductoTerminado
            entProductoTerminado entidad = new entProductoTerminado(); 
            entidad.setId_producto_terminado(dr.getInt(1));
            entidad.setCodigo_control(dr.getString(2));
            lista.add(entidad);
        }

    } catch (Exception e) {
        throw new Exception("Listar "+e.getMessage(), e);
    }
    finally{
        try {
            dr.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}
public static List<entProductoTerminado> GraficoSelecionador() throws Exception
{
    List<entProductoTerminado> lista = null;
    Connection conn =null;
    CallableStatement stmt = null;
    ResultSet dr = null;
    try {
        String sql="select TOP 5 COUNT(PT.ID_PRODUCTO_TERMINADO),E.NOMBRE,E.APELLIDO from PRODUCTO_TERMINADO PT\n" +
                    "JOIN EMPLEADO E ON E.DNI=PT.SELECCIONADOR group by PT.SELECCIONADOR,E.NOMBRE,E.APELLIDO \n" +
                    "ORDER BY  COUNT(PT.ID_PRODUCTO_TERMINADO) DESC";

        conn = ConexionDAO.getConnection();
        stmt = conn.prepareCall(sql);
        dr = stmt.executeQuery();

        while(dr.next())
        {
            if(lista==null)
                lista= new ArrayList<entProductoTerminado>();
            //ProductoTerminado
            entProductoTerminado entidad = new entProductoTerminado(); 
            entidad.setId_producto_terminado(dr.getInt(1));
            entidad.setCodigo_control(dr.getString(2)+" "+dr.getString(3));
            lista.add(entidad);
        }

    } catch (Exception e) {
        throw new Exception("Listar "+e.getMessage(), e);
    }
    finally{
        try {
            dr.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}  
public static List<entProductoTerminado> GraficoEmbalador() throws Exception
{
    List<entProductoTerminado> lista = null;
    Connection conn =null;
    CallableStatement stmt = null;
    ResultSet dr = null;
    try {
        String sql="select TOP 5 COUNT(PT.ID_PRODUCTO_TERMINADO),E.NOMBRE,E.APELLIDO from PRODUCTO_TERMINADO PT\n" +
                    "JOIN EMPLEADO E ON E.DNI=PT.EMBALADOR group by PT.SELECCIONADOR,E.NOMBRE,E.APELLIDO \n" +
                    "ORDER BY  COUNT(PT.ID_PRODUCTO_TERMINADO) DESC";

        conn = ConexionDAO.getConnection();
        stmt = conn.prepareCall(sql);
        dr = stmt.executeQuery();

        while(dr.next())
        {
            if(lista==null)
                lista= new ArrayList<entProductoTerminado>();
            //ProductoTerminado
            entProductoTerminado entidad = new entProductoTerminado(); 
            entidad.setId_producto_terminado(dr.getInt(1));
            entidad.setCodigo_control(dr.getString(2)+" "+dr.getString(3));
            lista.add(entidad);
        }

    } catch (Exception e) {
        throw new Exception("Listar "+e.getMessage(), e);
    }
    finally{
        try {
            dr.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}  

}
