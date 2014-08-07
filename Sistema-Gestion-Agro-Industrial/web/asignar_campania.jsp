<%-- 
    Document   : newjsp
    Created on : 22/04/2014, 06:06:51 AM
    Author     : Toditos
--%>

<%@page import="Entidades.entCampania"%>
<%@page import="Com.clsGestor"%>
<%@page import="Entidades.entLote"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.entSesion"%>
<%   
entSesion objSession =(entSesion) request.getSession().getAttribute("SessionUsuario");
if(objSession!=null)
{
    boolean pagina=false;
    int posI=objSession.getListModulos().size();
    for(int i=0;i<posI;i++)
    {
        int posJ=objSession.getListModulos().get(i).getList().size();
        for(int j=0;j<posJ;j++)
        {
            if(20==objSession.getListModulos().get(i).getList().get(j).getControl_form())
            {
                pagina=true;
                i=posI;
                j=posJ;
            }
        }
        
    }
    if(!pagina)
        response.sendRedirect("intranet.jsp");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Sistema Gestión Agrícola</title>
        <meta name="author" content="">
        <meta name="description" content="Sistema Gestión Agrícola">
        <meta name="keywords" content="Sistema Agricola">
    
        <!-- Bootstrap framework -->
            <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
            <link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css" />
        <!-- gebo blue theme-->
            <link rel="stylesheet" href="css/blue.css" id="link_theme" />
        <!-- breadcrumbs-->
            <link rel="stylesheet" href="lib/jBreadcrumbs/css/BreadCrumb.css" />
        <!-- tooltips-->
            <link rel="stylesheet" href="lib/qtip2/jquery.qtip.min.css" />
        <!-- notifications -->
            <link rel="stylesheet" href="lib/sticky/sticky.css" />    
        <!-- splashy icons -->
            <link rel="stylesheet" href="img/splashy/splashy.css" />
        <!-- colorbox -->
            <link rel="stylesheet" href="lib/colorbox/colorbox.css" />	
		    
        <!-- main styles -->
            <link rel="stylesheet" href="css/style.css" />
			
            <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=PT+Sans" />
             	<!-- smoke_js -->
            <link rel="stylesheet" href="lib/smoke/themes/gebo.css" />
	<!-- enhanced select -->
            <link rel="stylesheet" href="lib/chosen/chosen.css" />
<!-- datepicker -->
            <link rel="stylesheet" href="lib/datepicker/datepicker.css" />
        <!-- Favicon -->
            <link rel="shortcut icon" href="favicon.ico" />
		
        <!--[if lte IE 8]>
            <link rel="stylesheet" href="css/ie.css" />
            <script src="js/ie/html5.js"></script>
			<script src="js/ie/respond.min.js"></script>
        <![endif]-->
		
		<script>
			//* hide all elements & show preloader
			document.documentElement.className += 'js';
		</script>
    <!-- Shared on MafiaShare.net  --><!-- Shared on MafiaShare.net  --></head>
    <body>
		<div id="loading_layer" style="display:none"><img src="img/ajax_loader.gif" alt="" /></div>
		
		<div id="maincontainer" class="clearfix">
			<!-- header -->
            <header id="header">

            </header>
            
            <!-- main content -->
            <div id="contentwrapper">
                <div class="main_content">
                    <nav>
                        <div id="jCrumbs" class="breadCrumb module">
                              <ul>
                                <li>
                                    <a href="intranet.jsp"><i class="icon-home"></i></a>
                                </li>                                
                                <li>
                                    <a href="#">CONFIGURACION</a>
                                </li>
                                <li>
                                    <a href="#">Empresa</a>
                                </li>
                                 <li>
                                      Asignar Campaña
                                </li>
                            </ul>
                        </div>
                    </nav>
                
                    <div class="row-fluid">
						<div class="span12">
							<div class="row-fluid">
								<div class="span3">
									<div class="row-fluid" id="g-map-top">
										<div class="span12">
											  <form  method="get" id="reg_form">
                                                                                              <div class="location_add_form well">
												<div class="formSep">
                                                                                                        <div class="input-prepend">
													<label>Número de Plantas</label>
                                                                                                        <input type="text" class="span10" id="txtNumero" name="txtNumero" />
                                                                                                        </div>
                                                                                                        <div class="input-prepend">
													<label>Fecha de Poda</label>
                                                                                                        <input type="text" class="span10" id="txtFecha" name="txtFecha" />
                                                                                                        </div>
                                                                                                        <div class="input-prepend">
                                                                                                            <label>Camapaña</label>
                                                                                                            <select id="cbCampania" class="span10" name="cbCampania" data-placeholder="Selecione una Opción"  title="Por favor selecione un Campaña!" required>
                                                                                                                <option value=""></option>
                                                                                                                <% 
                                                                                                                    List<entCampania> listCampania=clsGestor.ListarCampania(true);
                                                                                                                    if(listCampania!=null)
                                                                                                                        for(entCampania entidad : listCampania)
                                                                                                                            out.print("<option value='"+entidad.getId_campania()+"'>"+entidad.getNombre()+"</option>");
                                                                                                                 %>
                                                                                                            </select>
                                                                                                        </div>   
                                                                                                         <div class="input-prepend">
                                                                                                            <label>Lote</label>
                                                                                                            <select id="cbLote" name="cbLote" class="span10" data-placeholder="Selecione una Opción"  title="Por favor selecione un Lote!" required>
                                                                                                                <option value=""></option>
                                                                                                                <% 
                                                                                                                    List<entLote> listLote=clsGestor.ListarLote(true);
                                                                                                                    if(listLote!=null)
                                                                                                                        for(entLote entidad : listLote)
                                                                                                                            out.print("<option value='"+entidad.getId_lote()+"'>"+entidad.getNombre()+"</option>");
                                                                                                                 %>
                                                                                                            </select>
                                                                                                        </div>   
                                                                                                    <input type="hidden" id="IdCampaniaLote"  name="IdCampaniaLote" value="0" />
                                                                                                        
												</div>
                                                                                                <button class="btn btn-invert" type="submit">Grabar</button>
                                                                                                
                                                                                                <button class="btn btn-invert" onclick="clear_form()" type="button">Limpiar</button>
											</div>
                                                                                    
                                                                                    
                                                                                    </form>
										</div>
									</div>
								</div>	
								<div class="span9">
                                                                    <div id="tabla"></div>
								</div>
							</div>
						</div>
                        </div>
					

                </div>
            </div>
            
			<!-- sidebar -->
            <a href="javascript:void(0)" class="sidebar_switch on_switch ttip_r" title="Hide Sidebar">Sidebar switch</a>
           <div class="sidebar"  id="sidebar"></div>
            
            <script src="js/jquery.min.js"></script>
			<!-- smart resize event -->
			<script src="js/jquery.debouncedresize.min.js"></script>
			<!-- hidden elements width/height -->
			<script src="js/jquery.actual.min.js"></script>
			<!-- js cookie plugin -->
			<script src="js/jquery.cookie.min.js"></script>
			<!-- main bootstrap js -->
			<script src="bootstrap/js/bootstrap.min.js"></script>
			<!-- sticky messages -->
			<script src="lib/sticky/sticky.min.js"></script>
			<!-- tooltips -->
			<script src="lib/qtip2/jquery.qtip.min.js"></script>
			<!-- jBreadcrumbs -->
			<script src="lib/jBreadcrumbs/js/jquery.jBreadCrumb.1.1.min.js"></script>
			<!-- fix for ios orientation change -->
			<script src="js/ios-orientationchange-fix.js"></script>
			<!-- scrollbar -->
			<script src="lib/antiscroll/antiscroll.js"></script>
			<script src="lib/antiscroll/jquery-mousewheel.js"></script>
			<!-- lightbox -->
            <script src="lib/colorbox/jquery.colorbox.min.js"></script>
            <!-- common functions -->
			<script src="js/gebo_common.js"></script>
                         <!-- enhanced select (chosen) -->
                        <script src="lib/chosen/chosen.jquery.min.js"></script>

			<!-- maps functions -->
                        <script src="lib/validation/jquery.validate.min.js"></script>
                         <!-- smoke_js -->
			<script src="lib/smoke/smoke.js"></script>
                          <!-- datepicker -->
                        <script src="lib/datepicker/bootstrap-datepicker.js"></script>
	
			<script>
      function modulos()
{
     $.ajax({
            url: 'operaciones/sidebar.jsp',
            type: 'POST',
            success: function (data) {     
                     $('#sidebar').html(data);
            },
            contentType: false,
            processData: false
        });

     $.ajax({
            url: 'operaciones/header.jsp',
            type: 'POST',
            success: function (data) {     
                     $('#header').html(data);
            },
            contentType: false,
            processData: false
        });
};
function getMododulos(posicion)
{
    $.ajax({
            url: 'operaciones/modulos.jsp?posicion='+posicion,
            type: 'POST',
            success: function () {     
                     modulos();
            },
            contentType: false,
            processData: false
        });
};                           
function tabla()
{
     $.ajax({
        url: 'operaciones/campania_lote/list_tabla.jsp',
        type: 'POST',
        success: function (data) {     
                 $('#tabla').html(data);
        },
        contentType: false,
        processData: false
    });          
 };
                              
                          
                            
				$(document).ready(function() {
					//* show all elements & remove preloader
                                        setTimeout('$("html").removeClass("js")',1000);

                                      
                                        
                                        
                                      $('#reg_form').validate({
                                        lang: 'es',
					onkeyup: false,
					errorClass: 'error',
					validClass: 'valid',
                                        ignore: ":hidden:not(select)",
                                            submitHandler: function() {       
                                           
                                                    var url = "operaciones/campania_lote/insert.jsp"; 

                                                    $.ajax({
                                                           type: "POST",
                                                           url: url,
                                                           data: $("#reg_form").serialize(), 
                                                           success: function(data)
                                                           {
                                                               if(data==-1)
                                                                 $.sticky("Error al Registrar.", {autoclose : 5000, position: "top-center" });
                                                                else if(data==0)
                                                                {
                                                                    tabla();
                                                                    clear_form();
                                                                   $.sticky("Se Actualizo Correctamente.", {autoclose : 5000, position: "top-center" });
                                                                    
                                                               }
                                                                else if(data>0)
                                                                {
                                                                   tabla();
                                                                   clear_form();
                                                                   $.sticky("Se Registro Correctamente.", {autoclose : 5000, position: "top-center" });  
                                                                   
                                                                }
                                                           }
                                                         });    
                                            },
					rules: {
                                                txtNumero: { required: true, digits:true},
                                                txtFecha: { required: true}
					},
					highlight: function(element) {
						$(element).closest('div').addClass("f_error");
					},
					unhighlight: function(element) {
						$(element).closest('div').removeClass("f_error");
					},
					errorPlacement: function(error, element) {
						$(element).closest('div').append(error);
					}
				});
                          
                                  $("#cbLote").chosen({allow_single_deselect:true});
                                  $("#cbCampania").chosen({allow_single_deselect:true});
                                  $('#txtFecha').datepicker();
                                        
				});
                                    var comboIdLote=0;
                                    var comboIdCampania=0;
                                    function clear_form() {
                                          $('#txtNumero').val("");
                                          $('#txtFecha').val("");
                                          $("#IdCampaniaLote").val("0");  
                                          if(comboIdLote>0)
                                              $("#cbLote option[value='"+comboIdLote+"']").remove();
                                            $("select#cbLote").val(0);   
                                            
                                          if(comboIdCampania>0)
                                            $("#cbCampania option[value='"+comboIdCampania+"']").remove();
                                            $("select#cbCampania").val(0);   
                                          
                                     
                                           
                                      };
                                       function edit_form(id,numero,fecha,idLote,nLote,idCampania,nCampania) {

                                            $('#txtNumero').val(numero);
                                            $('#IdCampaniaLote').val(id);
                                             $('#txtFecha').val(fecha);
                                             $("select#cbCampania").chosen().val(idCampania); 
                                      };
                                       function buscaComboLote(valor) {
                                           var estado=false;
                                            $("#cbLote option").each(function(){
                                                if($(this).attr('value')==valor)
                                                {
                                                    estado=true;
                                                }
                                                    
                                             });
                                             return estado;
                                       };
                                        function buscaComboCampania(valor) {
                                           var estado=false;
                                            $("#cbCampania option").each(function(){
                                                if($(this).attr('value')==valor)
                                                {
                                                    estado=true;
                                                }
                                                    
                                             });
                                             return estado;
                                       };
                                  modulos(); 
                                       tabla();
			</script>
		
		</div>
	</body>
</html>
<%}else  
    response.sendRedirect("index.jsp");%> 