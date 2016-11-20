package com.sf_lolitahag.GameComponent;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.MatchResult;

import javax.swing.JPanel;

//複数コマのアニメーション
public class Animation{
	
	Graphics g;
	JPanel p;
	int x,y;		//表示位置
	public int interval[];	//1コマ毎の表示間隔
	Image image[];	
	int exist = 0;	//0:表示 1:非表示
	int repeat = 0;	//0:繰り返さない 1:繰り返し
	int disappear = 0;	//0:再生終了時に消えない 1:消える
	int number = 0;	//現在のコマ
	int total = 0;	//総コマ数
	public int count = 0;
	public int end;
	
	//n:総コマ数
	public Animation(String filename, JPanel p){
		String aniname;
		File file = new File(filename);
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			total = readParam(br,"total = (\\d+)");
			repeat = readParam(br,"repeat = (\\d+)");
			disappear = readParam(br,"disappear = (\\d+)");
			
			image = new Image[total];
			interval = new int[total];
			for(int i=0;i<total;i++){
				try{
					br.readLine();
					image[i] = Toolkit.getDefaultToolkit().getImage(br.readLine());
					interval[i] = readParam(br,"interval = (\\d+)");
				}catch(IOException e){
				}
			}
		
			
		}catch(FileNotFoundException e){
			System.out.println(e);
		}
	
		this.repeat = repeat;
		this.disappear = disappear;
		this.interval = interval;
		this.p = p;
	}	
	
	public static int readParam(BufferedReader br,String format) {
		try{
			String str = br.readLine();
			Scanner scanner = new Scanner(str);
	 
			scanner.findInLine(format);
			MatchResult result = scanner.match();
			 
			return	Integer.parseInt(result.group(1));

		}catch(IOException e){
			System.out.println( "string is not match" );
		}
		return 0;
  }
	
	public void init(int x,int y){
		exist = 1;
		end = 0;
		number = 0;
		count = 0;
		this.x = x;
		this.y = y;
	}
	
	public void exit(){
		exist = 0;
		number = 0;
		count = 0;
	}
	
	public void idle(){
		if(exist == 1){
			if(count == interval[number]){
				number++;
				if(number == total){
					if(repeat == 0){
						end = 1;
						number--;
					}
					else {
						end = 1;
						number = 0;
					}
				}
				count = 0;
			}
			count ++;
		}
	}
	
	public void paint(Graphics g){
		if (image[number] != null && exist == 1){
         g.drawImage(image[number], x, y,p);
      }
	}
	
	public void paint(int width,int height,Graphics g){
		if (image[number] != null && exist == 1){
         g.drawImage(image[number], x, y,width,height,p);
      }
	}
	
	public int CheckEnd(){
		return end;
	}
	
	public int CheckExist(){
		return exist;
	}
}