<%@page import="Entidades.entSesion"%>
<%@page import="Entidades.entPatron"%>
<%@page import="Com.clsGestor"%>
<%@page import="java.util.List"%>
<select id="cbPatron" name="cbPatron" title="Por favor selecione un Patr�n!" required>
   <option value="">Selecione una Opci�n</option>

<%
    entSesion objSession =(entSesion) request.getSession().getAttribute("SessionUsuario");
if(objSession!=null)
{   
    List<entPatron> list=clsGestor.ListarPatron(true);
    if(list!=null)

        for(entPatron entidad : list)
            out.print("<option value='"+entidad.getId_patron()+"'>"+entidad.getNombre()+"</option>");
}
 %>
</select>