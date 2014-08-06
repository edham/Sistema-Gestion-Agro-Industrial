 <%@page import="Entidades.entCampaniaLote"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Entidades.entSesion"%>
<%@page import="Com.clsGestor"%>
<%@page import="java.util.List"%>
<%
entSesion objSession =(entSesion) request.getSession().getAttribute("SessionUsuario");
if(objSession!=null)
{  
SimpleDateFormat  fecha=new SimpleDateFormat("dd/MM/yyyy");
List<entCampaniaLote> list=clsGestor.ListarCampaniaLote();
if(list!=null)
{%>
<table id="tabla" class="table table-striped location_table">
    <thead>
            <tr>
                    <th>Id</th>
                    <th>N�mero de Plantas</th>
                    <th>Fecha de Poda</th>
                    <th>Lote</th>
                    <th>Campa�a</th>
                    <th>Responsable</th>
                    <th>Acciones</th>
            </tr>
    </thead>   
    <%
    for(entCampaniaLote entidad : list)
    {
    %>

    <tbody>                                                                                
        <tr>
            <td><%=entidad.getId_campania_lote()%></td>
            <td><%=entidad.getNumero_plantas()%></td>
            <td><%=fecha.format(entidad.getFechaPodaFormacion())%></td>
            <td><%=entidad.getObjLote().getNombre()%></td>
            <td><%=entidad.getObjCampania().getNombre()%></td>
            <td><%=entidad.getUsuario_responsable()%></td>
            <td>
                <a href="javascript:void(0)" onclick="edit_form(<%=entidad.getId_campania_lote()%>,'<%=entidad.getNumero_plantas()%>','<%=fecha.format(entidad.getFechaPodaFormacion())%>',<%=entidad.getObjLote().getId_lote()%>,'<%=entidad.getObjLote().getNombre()%>',<%=entidad.getObjCampania().getId_campania()%>,'<%=entidad.getObjCampania().getNombre()%>')" class="comp_edit btn btn-primary btn-mini">Editar</a>

            </td>
        </tr>

    </tbody>


    <%
    }
    %>
    </table>
<%} }%>  
                                                                        
                                                                       
                                                                            