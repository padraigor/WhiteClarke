require(["com/wcg/example/jsexample/SayHelloAmd"], function(SayHelloAmd) {
	
	describe("Say Hello AMD Tests", function() {
		
		var sayHello = null;
		beforeEach(function() {
			sayHello = new SayHelloAmd();
		});
		
		it("Says Hello", function() {	
			var message = sayHello.sayHello(); 
			expect(message).toEqual("hello");
		});
		it("Says Hello to Jim", function() {	
			var message = sayHello.sayHello("Jim"); 
			expect(message).toEqual("hello Jim");
		});
		it("Says Goodbye", function() {	
			var message = sayHello.sayGoodbye(); 
			expect(message).toEqual("goodbye");
		});
	});
});