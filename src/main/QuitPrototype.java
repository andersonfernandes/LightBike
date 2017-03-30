package main;

import java.awt.Color;
import java.awt.Font;

public class QuitPrototype extends ButtonPrototype {
	
	protected QuitPrototype(QuitPrototype quitPrototype) {
		this.xCoord1 = quitPrototype.getxCoord1();
		this.xCoord2 = quitPrototype.getxCoord2();
		this.yCoord1 = quitPrototype.getyCoord1();
		this.yCoord2 = quitPrototype.getyCoord2();
		this.color = quitPrototype.getColor();
		this.text = quitPrototype.getText();
		this.font = quitPrototype.getFont();
	}
	
	public QuitPrototype(int wdwWidth, int btnWidth, int btnHeight, Color btnColor, Font btnFont) {
		xCoord1 = (wdwWidth - btnWidth) / 2;
		yCoord1 = 500;
		xCoord2 = (wdwWidth - btnWidth) / 2 + btnWidth;
		yCoord2 = 500 + btnHeight;
		color = btnColor;
		text = "Quit";
		font = btnFont;
	}
	
	@Override
	public ButtonPrototype clonar() {
		return new QuitPrototype(this);
	}
}
