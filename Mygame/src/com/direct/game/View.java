package com.direct.game;

import java.awt.Image;
import java.awt.Rectangle;

public class View 
{
	protected  boolean status=true;
	protected Image img;//�ɻ�ͼƬ
	protected int x=8;//�ɻ���x����
	protected int y=200;//�ɻ���y����
	
	public Rectangle getRect()
	{
		return new Rectangle(x,y,img.getWidth(null),img.getHeight(null));
	}
}
