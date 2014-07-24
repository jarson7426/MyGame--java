package com.direct.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.Random;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
/*jian_zhi_kun
2014-7-17*/
/*��Ϸ������*/
public class StartGame extends Frame
{
	 private background bgground = new background("/images/bg_01.gif");//��������ͼƬ��
	 private Plane myPlane =new Plane("/images/my_plane.gif");//�����ɻ���
	 //�����л���
	 static List<ExPlane> explanes = new ArrayList<ExPlane>();
	 private BossPlane bossPlane =new BossPlane(380,150);//����Boss��
	 static List<Bullet> bullets = new ArrayList<Bullet>();//�����ӵ��ࣨ���飩
	 private GameOver overimg = new GameOver("/images/Gameover.jpg");
	 private int width;
	 private int height;
	 private boolean flag = true;
	 private int expsum;//����ɱ���л�������
	 private int bloodwidth=120;//����Ѫ���ĳ��ȣ�
	//����ͷ��ͼƬ��
	 private Image userimg =Toolkit.getDefaultToolkit().getImage(StartGame.class.getResource("/images/user.gif"));
	 
     public StartGame (int width,int height,String title)
     {
    	 this.width = width;
    	 this.height = height;
    	 //���ô��ڴ�С��
    	 this.setSize(width,height);
    	//���ô��ڱ��⣻
    	 this.setTitle(title);
    	 //���ô��ھ�����ʾ
    	 this.setLocationRelativeTo(null);
    	 //��Ӵ��ڼ����¼�
    	 this.addWindowListener(new WindowListener());
    	 //��Ӽ��̼����¼�
    	 this.addKeyListener(new KeyListener());
    	//���ô��ڿɼ���
    	 this.setVisible(true);
    	//�����߳���;
    	 new MainThread().start();
    	 //������������
    	 new sound("/music/game_music.mp3").playLoop();//���õڶ������캯����ֱ�ӵõ�·����
     }
     @Override
     public void update(Graphics g)
     {
    	 Image bufimg = this.createImage(width,height);//����һ��ͼƬ
    	 Graphics bufGs = bufimg.getGraphics();//��ȡ��ͼƬ�Ļ���
    	 bufGs.setColor(getBackground());//������������ɫ
    	 bufGs.fillRect(0, 0, width, height);//��ͼƬ�л�һ�����Σ���߶��ɴ��ڴ�С������
    	 //�����ͼ��ȫ�������ڴ�����ͼ���У�
    	 paint(bufGs);
    	 g.drawImage(bufimg, 0, 0, this);//�����ܿ�����ִ�д��룬ʹ�ô��ڵĻ��ʻ���ͼƬ��
     }
	@Override
	public void paint(Graphics g) /*��������-----��ǰ����*/
	{
		if(bloodwidth>0)
		{
			bgground.drawImage(g);//��������ͼ
			g.setColor(Color.RED);
			g.drawString("��ǰ�о�������"+ explanes.size(),50,50);   //����ͳ����Ϣ
			g.drawString("��ǰ����о���������"+ expsum,200,50);
			g.drawImage(userimg,20,590,70,100,this);
			g.setColor(Color.red);
			g.fillRect(100,660, bloodwidth, 20);
			myPlane.drawImage(g);//�����ҵķɻ�
			//�����л�,ҲҪ�����л����ӵ�
			for(int i=0;i<explanes.size();i++)
			{
				ExPlane explane = explanes.get(i);
				
				if(explane.status)//�жϵл�״̬Ϊtrue
				{
					explane.drawImage(g);
					List<ExBullet> exBullets = explane.getBullets();//�����л��ӵ�����
					for(int j=0;j<exBullets.size();j++)
					{
						ExBullet exBullet = exBullets.get(j);//�õ�ÿһ���л��ӵ�
						if(exBullet.getRect().intersects(myPlane.getRect()))//�жϵл��ӵ��Ƿ����ҵķɻ��ཻ
						{
							bloodwidth =bloodwidth-20;
							exBullet.status = false;//�ཻ֮���ӵ�״̬��Ϊfalse
							exBullets.remove(exBullet);//�ڼ�����ɾ���ӵ�
							new sound("/music/plane_down.mp3").play();
						}
						if(exBullet.status)
						{
							exBullet.drawImage(g);
						}
					}
					//�жϵ�ǰ�л��Ƿ����ҵ��ӵ��ཻ
					for(int k =0;k<bullets.size();k++)
					{
						Bullet bullet =bullets.get(k);
						if(bullet.getRect().intersects(explane.getRect()))//�ж��ҵ��ӵ��Ƿ���ел���
						{
							expsum++;
							bullet.status=false;
							bullets.remove(bullet);
							explane.status=false;
							explanes.remove(explane);
							new Boom ("/images/bg_01.gif",explane.x,explane.y).drawImage(g);
							new sound("/music/enemy_down.mp3").play();
						}
					}
				}
			}
			
			if(expsum>=20)//����boss
			{
				bossPlane.drawImage(g);
				List<BossBullet> bossbullets = bossPlane.bossbullets;
						for(int i =0;i<bossbullets.size();i++)
						{
							BossBullet bossbullet = bossbullets.get(i);
							if(bossbullet.status)
							{
								bossbullet.drawImage(g);//�����л��ӵ�
							}
						}
				flag = false;
			}
			//�����ӵ�
			for(int i =0;i<bullets.size();i++)
			{
				Bullet bullet = bullets.get(i);
				bullet.drawImage(g);
			}
		}
		else
		{
			new Boom("/images/bg_01.gif",myPlane.x,myPlane.y).drawImage(g);//������ըͼƬ
			/**/;
			overimg.drawImage(g);
		}
		
	}
	
	/*�߳��࣬�����ظ�����paint��ʹͼ���ƶ�*/
    class MainThread extends Thread
	{
		public void run ()
		{
			Random random = new Random();
			int fg =0;
			while (true)
			{
				fg++;
				try
				{
					Thread.sleep(200);//�õ�ǰ�߳�����100������ִ��
					repaint();//repaint�������ظ�����paint();
					//��һ��ʱ�䴴���л����Ž����ϣ�
					if(fg%5==0 && flag== true)
					{
						explanes.add(new ExPlane("/images/exp_01.gif",1000,random.nextInt(450)));
					}
					
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
     
	class WindowListener extends WindowAdapter//�Զ��崰���¼�
	{
		public void windowClosing (WindowEvent e)//������ڹرհ�ťʱ�������¼�
		{
			System.exit(0);
		}
	}
  
	class KeyListener extends KeyAdapter//�Զ�������¼�
	{

		@Override  /*���¼���ʱ�������¼�*/
		public void keyPressed(KeyEvent e) 
		{
			if(bloodwidth>0)
			myPlane.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) 
		{
			if(bloodwidth>0)
			myPlane.keyReleased(e);
		}
		
	}
}
