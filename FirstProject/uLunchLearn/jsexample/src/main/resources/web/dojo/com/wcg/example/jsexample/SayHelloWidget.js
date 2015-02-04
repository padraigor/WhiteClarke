define(["dojo/_base/declare", // declare
        "dojo/_base/lang", // lang.getObject...
        "dojo/dom-construct",
        "dijit/_WidgetBase"], 
function(declare, 
		lang, 
		domConstruct,
		_WidgetBase) {

	return declare("com.wcg.example.jsexample.SayHelloWidget", [_WidgetBase], {
	
		postCreate: function() {
			this.inherited(arguments);
			this.selected = false;
		},
	
		buildRendering: function() {
			this.inherited(arguments);
			this.domNode = domConstruct.create("div", {"class": "helloWidget"});
			this.domNode.innerHTML = "Hello Louis";
		}
	});
	
});