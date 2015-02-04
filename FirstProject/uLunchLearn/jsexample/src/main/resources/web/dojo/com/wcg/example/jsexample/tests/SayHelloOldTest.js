require(["com/wcg/example/jsexample/SayHelloOld"], function(SayHelloOld) {
	
	describe("Say Hello Old Tests", function() {
		
		var sayHello = null;
		beforeEach(function() {
			sayHello = new SayHelloOld();
		});
		
		it("Say Hello", function() {	
			var message = sayHello.sayHello(); 
			expect(message).toEqual("hello");
		});
	});
});