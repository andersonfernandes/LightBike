package main;

import java.awt.Color;
import java.awt.Font;

public class Button extends Element {
	public String text;

	public Button(int x1, int y1, int x2, int y2, Color btnColor, String btnText, Font btnFont) {
		xCoord1 = x1;
		yCoord1 = y1;
		xCoord2 = x2;
		yCoord2 = y2;
		color = btnColor;
		text = btnText;
		font = btnFont;
	}

	public String getText() {
		return text;
	}
}
