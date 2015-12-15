<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="http://cdn.webix.com/edge/webix.css" type="text/css"> 
		<script src="http://cdn.webix.com/edge/webix.js" type="text/javascript" charset="utf-8"></script>
		<title>Creating Basic App. Step 2</title>
	</head>
<body>

<script type="text/javascript" charset="utf-8">


var members = []


webix.ui({
	id:"layout",
	width:500, 
    rows: [
   		{ view:"toolbar", id:"mybar", height:60, elements:[]},
		{ id:"container", cols:[
		{ 
				view:"datatable", 
				id:"data",
			
				columns:[
					{ id:"title",	editor:"text",		header:"Name",width:200}
				],

				editable:true,
				editaction:"custom",
				autoheight:true,
				autowidth:true,
				
				on:{
					"onItemClick":function(id){
						this.editRow(id);
					},
					"onAfterEditStop":function(id){
						var last = this.getItem(this.getLastId()).title;
						if (last && id.value == last) {
							$$('data').add({}, -1);	
						}
					}
				},
				data: [{}]
       }]},
        { view:"toolbar", id:"footer", cols:[
            { view:"button", value:"next", width:70, click:"next", align:"right"},
        ]},
    ]
});


function next() {
	var dtable =  $$("data");
    dtable.eachRow( 
    function (row){ 
    	var name = dtable.getItem(row).title;
    	if (name) members.push({name:name});
    });
    
    addNameList("container", -1);
    
    dtable.attachEvent("onItemClick", function(id){
    	var pos = $$("container").index($$("data"));
    	console.log(pos);
    	
	});
	
	

}

function addNameList(container, pos) {
	$$(container).addView(
	{ id:"container", cols:[{			
					view: "list",
					id:"nameList",
					autoheight:true,
					autowidth:true,
					template: "#name#",
					data: members,		
	}]} , pos);
}
		


</script>
</body>
</html>