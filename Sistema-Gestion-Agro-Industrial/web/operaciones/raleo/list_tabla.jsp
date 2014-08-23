 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="Entidades.entRaleo"%>
<%@page import="Entidades.entSesion"%>
<%@page import="Com.clsGestor"%>
<%@page import="Entidades.entVivero"%>
<%@page import="java.util.List"%>
<%
entSesion objSession =(entSesion) request.getSession().getAttribute("SessionUsuario");
if(objSession!=null)
{
if(objSession.getObjRaleo()!=null)   
    if(objSession.getObjRaleo().isSeleccion())
    out.print("<script type='text/javascript'>tablaTemp(0);</script>");
    else
        out.print("<script type='text/javascript'>vertablaTemp();</script>");
else
{  
    
SimpleDateFormat  fecha=new SimpleDateFormat("dd/MM/yyyy");
List<entRaleo> list=clsGestor.ListarRaleo(false);
if(list!=null)
{%>
<div id="frame">
 <button class="btn btn-invert" onclick="tablaTemp(0)" type="button">Agregar Raleo</button>
<div class="row-fluid">
    
<div class="span12">
<table id="lista" class="table table-striped location_table">
    <thead>
            <tr>
                    <th>Id</th>
                    <th>Lote</th>
                    <th>Campa�a</th>
                    <th>Variedad</th>
                    <th>Cultivo</th>
                    <th>Evaluador</th>
                    <th>F. Registro</th>
                    <th>Estado</th>
                    <th>Acciones</th>
            </tr>
    </thead> 
     <tbody>      
    <%
    for(entRaleo entidad : list)
    {
    %>
                                                                   
        <tr>
            <td><%=entidad.getId_raleo()%></td>
            <td><%=entidad.getObjCampaniaLote().getObjLote().getNombre()%></td>
            <td><%=entidad.getObjCampaniaLote().getObjCampania().getNombre()%></td>
            <td><%=entidad.getObjCampaniaLote().getObjLote().getObjVariedad().getNombre()%></td>
            <td><%=entidad.getObjCampaniaLote().getObjLote().getObjVariedad().getObjCultivo().getNombre()%></td>
            <td><%=entidad.getObjEvaluador().getNombre()%> <%=entidad.getObjEvaluador().getApellido()%></td>
            <td><%=fecha.format(entidad.getFecha_registro())%></td>
            <td>
                 <%
                   if(entidad.isEstado())
                   out.print(" <span class='label label-success'>Activado</span>");
                   else
                       out.print(" <span class='label label-important'>Desactivado</span>");
                %>


            </td>
            <td>
                <a href="javascript:void(0)" onclick="editar(<%=entidad.getId_raleo()%>,0)" class="comp_edit btn btn-primary btn-mini">Editar</a>
                <a href="javascript:void(0)" onclick="editar(<%=entidad.getId_raleo()%>,1)" class="comp_edit btn btn-warning btn-mini">Ver</a>
            </td>
        </tr>


    <%
    }
    %>
     </tbody>
    </table>
</div>
</div>
<script type="text/javascript">
$(function () { 

   $('#lista').dataTable({
       "sDom": "<'row'<'span2'><'span2'f>r>t<'row'<'span2'i><'span2'>S>",
        "sScrollY": "320px",
        "bDestroy": true,
        "bDeferRender": true
    }); 
 
});

</script>

<%}
else
    out.print("<button class='btn btn-invert' onclick='tablaTemp(0)' type='button'>Agregar Raleo</button>");
}}%>  
                                                                        
                                                                       
                                                                            