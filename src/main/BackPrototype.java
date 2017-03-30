package main;

import java.awt.Color;
import java.awt.Font;

class BackPrototype extends ButtonPrototype {
	
	protected BackPrototype(BackPrototype backPrototype) {
		this.xCoord1 = backPrototype.getxCoord1();
		this.yCoord1 = backPrototype.getyCoord1();
		this.xCoord2 = backPrototype.getxCoord2();
		this.yCoord2 = backPrototype.getyCoord2();
		this.color = backPrototype.getColor();
		this.text = backPrototype.getText();
		this.font = backPrototype.getFont();
	}
	
	public BackPrototype(int wdwWidth, int btnWidth, int btnHeight, Color btnColor, Font btnFont) {
		xCoord1 = (wdwWidth - btnWidth) / 2;
		yCoord1 = 600;
		xCoord2 = (wdwWidth - btnWidth) / 2 + btnWidth;
		yCoord2 = 600 + btnHeight;
		color = btnColor;
		text = "Back";
		font = btnFont;
	}
	
	@Override
	public ButtonPrototype clonar() {
		return new BackPrototype(this);
	}
}
