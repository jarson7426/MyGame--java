package com.direct.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Boom 
{
	private Image img;//��ըͼƬ
	private int x=8;//�ɻ���x����
	private int y=200;//�ɻ���y����
	
	public Boom (String imgpath, int x,int y)//�ӵ��Ĺ��캯����imgPath���ӵ�ͼƬ·����
	{
		this.x = x;
		this.y = y;
		this.img=Toolkit.getDefaultToolkit().getImage(Boom.class.getResource(imgpath));	
	}
	
	public void drawImage(Graphics g)//����ըͼ�ķ�����
	{
		g.drawImage(img,x,y,120,100,null);
	}
}
