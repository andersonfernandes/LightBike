package main;

import java.awt.Color;
import java.awt.Font;

public class RetryPrototype extends ButtonPrototype {
	
	protected RetryPrototype(RetryPrototype retryPrototype) {
		this.xCoord1 = retryPrototype.getxCoord1();
		this.xCoord2 = retryPrototype.getxCoord2();
		this.yCoord1 = retryPrototype.getyCoord1();
		this.yCoord2 = retryPrototype.getyCoord2();
		this.color = retryPrototype.getColor();
		this.text = retryPrototype.getText();
		this.font = retryPrototype.getFont();
	}
	
	public RetryPrototype(int wdwWidth, int btnWidth, int btnHeight, Color btnColor, Font btnFont) {
		xCoord1 = (wdwWidth - btnWidth) / 2;
		yCoord1 = 500;
		xCoord2 = (wdwWidth - btnWidth) / 2 + btnWidth;
		yCoord2 = 500 + btnHeight;
		color = btnColor;
		text = "Retry";
		font = btnFont;
	}
	
	@Override
	public ButtonPrototype clonar() {
		return new RetryPrototype(this);
	}
}
