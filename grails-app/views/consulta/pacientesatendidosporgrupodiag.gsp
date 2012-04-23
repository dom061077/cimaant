<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'consulta.label', default: 'Consulta')}" />
        <script type="text/javascript" src="${resource(dir:"js/script/consulta",file:"pacientesatendidosporgrupodiag.js")}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.jlookupfield.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery/chart',file:'jgcharts.pack.js')}"></script>        
        <script type="text/javascript" src="${resource(dir:'js/jquery/chart',file:'jquery.metadata.pack.js')}"></script>        
        <title>Pacientes Atendidos por Grupo de Diagnóstico</title>
        <script type="text/javascript">
        	var buscar=<%out << "${buscar}"%>;
        	$(document).ready(function(){
        		$("#cie10Id").lookupfield({
        			source:"<% out << g.createLink(controller:'cie10',action:'listsearchjson')%>",
        			title:'Búsqueda de CIE10',
        			colnames:['Id','Descripción'],
        			colModel:[
        					{name:'id',index:'id', width:10, sorttype:"int", sortable:true,hidden:false,search:false},
        					{name:'cie10',index:'cie10', width:100,  sortable:true,search:true},
        					{name:'descripcion',index:'descripcion', width:100,  sortable:true,search:true}	
        				],
        			hiddenid:'cie10IdId',
        			descid:'cie10Id',
        			hiddenfield:'id',
        			descfield:['descripcion']	
        		});	
        		
        		$("#cie10Id").autocomplete({
        				source: "<% out << g.createLink(controller:"cie10",action:'listjsonautocomplete')%>",
        				minLength:2,
        				select: function(event,ui){
        					if(ui.item){
        						$("#cie10IdId").val(ui.item.id);
        					}
        				}
        	        });


    	         
            });
        </script>
	</head>
	<body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
		<div class="body">
            <h1>Pacientes Atendidos por Grupo de Diagnóstico</h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cmdInstance}">
	            <div class="ui-state-error ui-corner-all" style="padding: 0pt 0.7em;">
	                <g:renderErrors bean="${cmdInstance}" as="list" />
	            </div>
            </g:hasErrors>
            <g:form action="pacientesatendidosporgrupodiagbuscar">
		            <div class="span-20">
		   				<fieldset>
							<div class="span-2 spanlabel">
								<label for="fechaDesde">Desde:</label>
							</div>
							<div class="span-2">
								<g:textField id="fechaDesdeId" class="ui-widget ui-corner-all ui-widget-content" name="fechaDesde" value="${cmdInstance.fechaDesde}"/>
							</div>        				
							<div class="clear"></div>
							<div class="span-2 spanlabel">
								<label for="fechaHasta">Hasta:</label>
							</div>
							<div class="span-3">
								<g:textField id="fechaHastaId" class="ui-widget ui-corner-all ui-widget-content" name="fechaHasta" value="${cmdInstance.fechaHasta}"/>
							</div>        				
							<div class="clear"></div>
							
							<div class="span-2 spanlabel">
								<label for="cie10">Diagnástico:</label>
							</div>
							<div class="span-4">
								<g:textField id="cie10Id" class="ui-widget ui-corner-all ui-widget-content" name="cie10" value="${cmdInstance.cie10}"/>
								<g:hiddenField id="cie10IdId" name="cie10Id" value="${cmdInstance.cie10Id}"/>
							</div>   
							<div class="clear"></div>
							
							<div class="span-3  prepend-2"><g:submitButton class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"  name="create" 
															value="${message(code: 'default.button.search.label', default: 'Create')}"/> </div>
												        				
												        				
		      			</fieldset>
		      		</div>	
      		</g:form>
      		<div class="clear"></div>
            <div class="span-20">
	            <div id="tabs">
	            	<ul>
	            		<li><a href="#tabs-1">Detalle</a></li>
	            		<li><a href="#tabs-2">Gráficos</a></li>
	            	</ul>
	        		<div id="tabs-1">
	        			<table id="detallegrid">
	        			</table>
	        			<div id="pagerdetalle">
	        			</div>
	        		</div>
	        		<div id="tabs-2">
	        			<g:if test="${diagGraph?.size()>0}">
	        						<img alt="" src="${resource(dir:"images",file:"piechartdiag.png")}?dummy=${new java.util.Random().nextInt()}">
       					</g:if>
	        		</div>
	            </div>
            </div>
		</div>	
	</body>
</html>