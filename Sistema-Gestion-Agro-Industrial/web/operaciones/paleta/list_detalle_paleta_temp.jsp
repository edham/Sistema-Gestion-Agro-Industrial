 <%@page import="Entidades.entSesion"%>
<%@page import="Com.clsGestor"%>
<%@page import="Entidades.entVivero"%>
<%@page import="java.util.List"%>
<%
entSesion objSession =(entSesion) request.getSession().getAttribute("SessionUsuario");
if(objSession!=null)
{
   
if(objSession.getObjPaleta()!=null)
{%>
<div id="tabla">
<table id="lista" class="table table-striped location_table">
    <thead>
            <tr>
                <td>N�</td>
                <th>C�digo Control</th>
                <th>C�digo Lote</th>
                <th>Nombre Calibre</th>
                <th>Nombre Variedad</th>
                <th>Acciones</th>
            </tr>
    </thead> 
     <tbody>      
    <%
    for(int i=0;i<objSession.getObjPaleta().getListaDetallePaleta().size();i++)
    {
    %>
                                                                   
        <tr>
            <td><%=(i+1)%></td>
            <td><%=objSession.getObjPaleta().getListaDetallePaleta().get(i).getObjProductoTerminado().getCodigo_control()%></td>
            <td><%=objSession.getObjPaleta().getListaDetallePaleta().get(i).getObjProductoTerminado().getUsuario_responsable()%></td>
            <td><%=objSession.getObjPaleta().getListaDetallePaleta().get(i).getObjProductoTerminado().getEmbalador()%></td>
            <td><%=objSession.getObjPaleta().getListaDetallePaleta().get(i).getObjProductoTerminado().getSeleccionador()%></td>
            <td>
               
            </td>
        </tr>


    <%
    }
    %>
     </tbody>
    </table>
</div>
<script type="text/javascript">
$(function () { 

   $('#lista').dataTable(); 
 
});
</script>

<%} }%>  
                                                                        
                                                                       
                                                                            