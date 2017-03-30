package main;

public abstract class ButtonPrototype extends Element {
	
	protected String text;
	
	public abstract ButtonPrototype clonar();
	
	public String getText() {
		return text;
	}
	
}
