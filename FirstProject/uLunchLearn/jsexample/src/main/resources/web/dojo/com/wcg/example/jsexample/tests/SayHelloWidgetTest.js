require(["com/wcg/example/jsexample/SayHelloWidget"], function(SayHelloWidget) {
	
	describe("Say Hello AMD Tests", function() {
		
		var sayHello = null;
		beforeEach(function() {
			sayHello = new SayHelloWidget();
		});
		
		it("Says Hello", function() {	
			var message = sayHello.domNode.innerHTML; 
			expect(message).toEqual("Hello Louis");
		});

	});
});