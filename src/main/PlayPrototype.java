package main;

import java.awt.Color;
import java.awt.Font;

public class PlayPrototype extends ButtonPrototype {
	
	protected PlayPrototype(PlayPrototype playPrototype) {
		this.xCoord1 = playPrototype.getxCoord1();
		this.xCoord2 = playPrototype.getxCoord2();
		this.yCoord1 = playPrototype.getyCoord1();
		this.yCoord2 = playPrototype.getyCoord2();
		this.color = playPrototype.getColor();
		this.text = playPrototype.getText();
		this.font = playPrototype.getFont();
	}
	
	public PlayPrototype(int wdwWidth, int btnWidth, int btnHeight, Color btnColor, Font btnFont) {
		xCoord1 = (wdwWidth - btnWidth) / 2;
		yCoord1 = 300;
		xCoord2 = (wdwWidth - btnWidth) / 2 + btnWidth;
		yCoord2 = 300 + btnHeight;
		color = btnColor;
		text = "Play";
		font = btnFont;
	}
	
	@Override
	public ButtonPrototype clonar() {
		return new PlayPrototype(this);
	}
	
}
