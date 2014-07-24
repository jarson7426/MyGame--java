package com.direct.game;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;


public class sound
{
	private InputStream music;
	private AdvancedPlayer player;
	private boolean flag;
	private String musicpath;
	
	public sound(InputStream stram)//���캯����������Ǹ����ļ�·���õ����ļ�����
	{
		this.music = stram;
	}
	public sound(String musicpath)//���캯��������������ļ���·����
	{
		this.musicpath = musicpath;
	}
	
	public void play()//��������һ��
	{
		flag = false;
		new PlayThread().start();
	}
	public void playLoop()//����ѭ������
	{
		flag = true;
		new PlayThread().start();
	}
	
	class PlayThread extends Thread 
	{
		public void run ()
		{
			do
			{
				try
				{
					music = sound.class.getResourceAsStream(musicpath);
					player = new AdvancedPlayer(music);
					player.play();
				}
				catch(JavaLayerException e )
				{
					e.printStackTrace();
				}
			}
			while(flag);
		}
	}
	
}
