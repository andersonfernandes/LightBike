package main;

import java.awt.Color;
import java.awt.Font;

public class SettingsPrototype extends ButtonPrototype {
	
	protected SettingsPrototype(SettingsPrototype settingsPrototype) {
		this.xCoord1 = settingsPrototype.getxCoord1();
		this.xCoord2 = settingsPrototype.getxCoord2();
		this.yCoord1 = settingsPrototype.getyCoord1();
		this.yCoord2 = settingsPrototype.getyCoord2();
		this.color = settingsPrototype.getColor();
		this.text = settingsPrototype.getText();
		this.font = settingsPrototype.getFont();
	}
	
	public SettingsPrototype(int wdwWidth, int btnWidth, int btnHeight, Color btnColor, Font btnFont) {
		xCoord1 = (wdwWidth - btnWidth) / 2;
		yCoord1 = 400;
		xCoord2 = (wdwWidth - btnWidth) / 2 + btnWidth;
		yCoord2 = 400 + btnHeight;
		color = btnColor;
		text = "Settings";
		font = btnFont;
	}
	
	@Override
	public ButtonPrototype clonar() {
		return new SettingsPrototype(this);
	}
}
