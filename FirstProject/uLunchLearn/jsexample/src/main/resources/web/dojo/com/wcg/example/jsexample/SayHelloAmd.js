define([ "dojo", "dojo/_base/lang", "dojo/_base/declare" ], function(dojo, lang, declare) {

	return declare("com/wcg/example/jsexample/SayHelloAmd", [], {
		sayHello : function(person) {
			if (person !== undefined) {
				return "hello " + person;
			}
			return "hello";
		},
		sayGoodbye : function() {
			return "goodbye";
		}
	});
});