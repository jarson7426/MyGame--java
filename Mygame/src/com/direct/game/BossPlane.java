package com.direct.game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BossPlane extends View
{
	BufferedImage[] imgs=new BufferedImage[24];//�ɻ�ͼƬ
	List<BossBullet> bossbullets =new ArrayList<BossBullet>();
	private int count=0;
	private int key = 0;
	private int index =0;
	private int flag=0;
	
	public BossPlane (int x ,int y)//�ɻ��Ĺ��캯����imgPath�Ƿɻ�ͼƬ·����
	{
		this.x = x;
		this.y = y;
		for(int i=0;i<imgs.length;i++)
		{
			if(i<9)
			{
				try {
					imgs[i] = ImageIO.read(BossPlane.class.getResource("/images/Boss/0"+(i+1)+".gif"));
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			else
			{
				try {
					imgs[i] =ImageIO.read(BossPlane.class.getResource("/images/Boss/"+(i+1)+".gif"));
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			
		}
		this.img = imgs[0];
	}
	public void drawImage(Graphics g)
	{
		switch (key) 
		{
		case 0://������̬boss
			staticBoss(g);
			break;
        case 1://�ƶ�Boss
        	moveBoss(g);
			break;	
        case 2://Boss����
        	changeBoss(g);
			break;
        case 3://Boss��ǰײ��
			hitBoss(g);
			break;
		default:
			break;
		}
		if(flag %20==0)
		{
			bossbullets.add(new BossBullet(x,y));
		}
		flag++;
	}
	public void staticBoss(Graphics g)
	{
		g.drawImage(img,x,y,480,260,null);
		index++;
		if(index %20==0 )
		{
			key =1;
			index=0;
		}
	}
	public void moveBoss(Graphics g)
	{
		if(index<=30)
		{
			x=x+3;y=y-5;
		}
		else if(index >30 && index <=60)
		{
			x=x+3;y=y+5;
		}
		else if(index >60 && index <=90)
		{
			x=x-3;y=y+5;
		}
		else if(index >90 && index <=120)
		{
			x=x-3;y=y-5;
		}
		else {
			key =2;
		}
		g.drawImage(img,x,y,480,260,null);
		index++;
	}
	public void changeBoss(Graphics g)
	{
		g.drawImage(imgs[count],this.x,this.y,480,260,null);
		count ++;
		if(count < 23)
		{
			count++;
		}
		else {
			key = 3;
		}
	}
	public void hitBoss(Graphics g)
	{
		g.drawImage(imgs[count],x,y,480,260,null);
		x = x-30;
		if(x <-700)
		{
			x = 380;
			key = 0;
			count =0;
		}
	}
	
}
