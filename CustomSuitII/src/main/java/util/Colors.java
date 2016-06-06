package util;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;

public class Colors {
	private static final int B_max = 1;
	
	private static final int RGB_max = 255;
	
	public static List<Color> getSpectrum(Color color, int count, float add){
		List<Color>colors = new ArrayList<Color>();
		float[]HSB = RGBtoHSB(color);
		
		float B;
		
		
		for(int c=0; c<count;c++){
			B = HSB[2]+add;
			
			if(B<0 || B> B_max){
				add*=-1;
			}
			
			HSB[2]+=add;
			colors.add(HSBtoRGB(HSB));
		}
		
		return colors;
	}
	
	public static Color getRandomColor(){
		int red = (int)ArmorUtil.random(RGB_max+1);
		int green = (int)ArmorUtil.random(RGB_max+1);
		int	blue = (int)ArmorUtil.random(RGB_max+1);
		
		return Color.fromRGB(red, green, blue);
	}
	
	public static float[] RGBtoHSB(Color color){
		float[]HSB = new float[3];
		
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		
		HSB = java.awt.Color.RGBtoHSB(red, green, blue, HSB);
		return HSB;
	}
	
	public static Color HSBtoRGB(float[]HSB ){
		int RGB = java.awt.Color.HSBtoRGB(HSB[0], HSB[1], HSB[2]);
		
		int red = (RGB >> 16) & 0xFF;
		int green = (RGB >> 8) & 0xFF;
		int blue = RGB & 0xFF;
		
		return Color.fromRGB(red, green, blue);
	}
}
