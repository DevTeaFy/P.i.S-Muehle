package de.pis.muehle.gui;

import java.util.ArrayList;
import java.util.function.Function;

import de.pis.muehle.IMuehle;
import de.pis.muehle.Move;
import de.pis.muehle.Muehle;
import processing.core.PApplet;

public class GUI extends PApplet{

	public static void main(String[] args) {
		PApplet.runSketch(new String[]{""}, new GUI(new Muehle()));
	}
	IMuehle muehle;
	
	public GUI(IMuehle m) {
		this.muehle = m;
	}
	
	@Override
	public void settings() {
		size(1200, 1200);
	}
	
	@Override
	public void setup() {
		background(color(50,50,50));
	}
	@Override
	public void draw() {
		drawMuehle();
	}
	
	private void drawMuehle() {
		int HofSet = 90;
		int VofSet = 90;
		int circleSize = 60;
		int sticklenght = 410;
		int stickhight = 20;
		int verbindungssticklenght = sticklenght/6;
		circle(HofSet, VofSet, sticklenght, stickhight, circleSize, verbindungssticklenght,0);
		verbindunssticks(HofSet, VofSet, sticklenght, stickhight, circleSize, verbindungssticklenght);
		sticklenght /= 3;
		HofSet += sticklenght;
		VofSet += sticklenght;
		sticklenght *= 2;
		circle(HofSet, VofSet, sticklenght, stickhight, circleSize, verbindungssticklenght,1);
		verbindunssticks(HofSet, VofSet, sticklenght, stickhight, circleSize, verbindungssticklenght);
		sticklenght /= 2;
		HofSet += sticklenght;
		VofSet += sticklenght;
		circle(HofSet, VofSet,sticklenght, stickhight, circleSize, verbindungssticklenght,2);
	}
	
	@Override
	public void keyPressed() {
		if(key == 'g')System.out.println(muehle.generatePerfactMove().toString());
	}
	
	@Override
	public void mouseClicked() {
		
		int c = mouseIsInACirlce();
		if(c == -1)return;
//		if(muehle.getBoard()[c] != muehle.getCurrentPlayer().getValue())return;
		Point p = cords.get(c);
		fill(89,89,89);
		circle(p.x()-30, p.y()-30, 60);
		Move m = new Move(-1, c);
		if(!this.muehle.isPossibleMove(m))return;
		this.muehle = muehle.play(m);
	}
	
	private int mouseIsInACirlce() {
		for (int i = 0; i < cords.size(); i++) {
			if(mouseY >= cords.get(i).y()-30 && 
			   mouseY <= cords.get(i).y()+30 && 
			   mouseX >= cords.get(i).x()-30 && 
			   mouseX <= cords.get(i).x()+30) return i;
		}
		return -1;
	}
	
	private ArrayList<Point> cords = new ArrayList<>();
	final int empty = color(255,255,255);
	private void circle(int HofSet, int VofSet, int sticklenght, int stickhight, int circleSize,
			int verbindungssticklenght,int ring) {
		final Function<Integer, Integer> task = (playerValue) -> 
				playerValue == 1 ? color(0,255,0) : playerValue == -1 ? color(255,0,0) : empty;
		//Oberelinie
		fill(task.apply(muehle.getBoard()[ring*8]));
		circle(HofSet, VofSet, circleSize);
		if(cords.size() <= 23) registerCircles(HofSet, VofSet, sticklenght, stickhight, circleSize);
		
		fill(empty);
		rect(HofSet+((circleSize/2)+5), VofSet-stickhight/2, sticklenght, stickhight);
		fill(task.apply(muehle.getBoard()[ring*8+1]));
		circle(HofSet+sticklenght+(((circleSize/2)+5)*2), VofSet, circleSize);
		fill(empty);
		rect(HofSet+sticklenght+(((circleSize/2)+5)*3), VofSet-stickhight/2, sticklenght, stickhight);
		fill(task.apply(muehle.getBoard()[ring*8+2]));
		circle(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4), VofSet, circleSize);
		
		//Senkrechtelinien rechts
		fill(empty);
		rect(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4)-stickhight/2, VofSet+((circleSize/2)+5), stickhight, sticklenght);
		fill(task.apply(muehle.getBoard()[ring*8+3]));
		circle(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4), VofSet+sticklenght+(((circleSize/2)+5)*2), circleSize);
		fill(empty);
		rect(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4)-stickhight/2, VofSet+sticklenght+(((circleSize/2)+5)*3), stickhight, sticklenght);
		fill(task.apply(muehle.getBoard()[ring*8+4]));
		circle(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4), VofSet+(sticklenght*2)+(((circleSize/2)+5)*4), circleSize);
		//unterelinie
		fill(empty);
		rect(HofSet+((circleSize/2)+5), VofSet-stickhight/2+(sticklenght*2)+(((circleSize/2)+5)*4), sticklenght, stickhight);
		fill(task.apply(muehle.getBoard()[ring*8+5]));
		circle(HofSet+sticklenght+(((circleSize/2)+5)*2), VofSet+(sticklenght*2)+(((circleSize/2)+5)*4), circleSize);
		fill(empty);
		rect(HofSet+sticklenght+(((circleSize/2)+5)*3), VofSet-stickhight/2+(sticklenght*2)+(((circleSize/2)+5)*4), sticklenght, stickhight);
		//Senkrechtelinien links
		fill(empty);
		rect(HofSet-stickhight/2, VofSet+((circleSize/2)+5), stickhight, sticklenght);
		fill(task.apply(muehle.getBoard()[ring*8+7]));
		circle(HofSet, VofSet+sticklenght+(((circleSize/2)+5)*2), circleSize);
		fill(empty);
		rect(HofSet-stickhight/2, VofSet+sticklenght+(((circleSize/2)+5)*3), stickhight, sticklenght);
		fill(task.apply(muehle.getBoard()[ring*8+6]));
		circle(HofSet, VofSet+(sticklenght*2)+(((circleSize/2)+5)*4), circleSize);
	}

	private void registerCircles(int HofSet, int VofSet, int sticklenght, int stickhight, int circleSize) {
		cords.add(new Point(HofSet, VofSet));
		cords.add(new Point(HofSet+sticklenght+(((circleSize/2)+5)*2), VofSet));
		cords.add(new Point(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4), VofSet));
		cords.add(new Point(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4), VofSet+sticklenght+(((circleSize/2)+5)*2)));
		cords.add(new Point(HofSet+(sticklenght*2)+(((circleSize/2)+5)*4), VofSet+(sticklenght*2)+(((circleSize/2)+5)*4)));
		cords.add(new Point(HofSet+sticklenght+(((circleSize/2)+5)*2), VofSet+(sticklenght*2)+(((circleSize/2)+5)*4)));
		cords.add(new Point(HofSet, VofSet+(sticklenght*2)+(((circleSize/2)+5)*4)));
		cords.add(new Point(HofSet, VofSet+sticklenght+(((circleSize/2)+5)*2)));
	}
	private void verbindunssticks(int HofSet, int VofSet, int sticklenght, int stickhight, int CIRCLE_SIZE,
			int verbindungssticklenght) {
		fill(empty);
		rect(HofSet+sticklenght+(((CIRCLE_SIZE/2)+5)*2)-stickhight/2, VofSet+CIRCLE_SIZE/2+5,stickhight, verbindungssticklenght);
		rect(HofSet+CIRCLE_SIZE/2+5, VofSet+sticklenght+(((CIRCLE_SIZE/2)+5)*2)-stickhight/2, verbindungssticklenght, stickhight);
		rect(HofSet+(sticklenght*2)+(((CIRCLE_SIZE/2)+5)*4)-(verbindungssticklenght*1.5f), VofSet+sticklenght+(((CIRCLE_SIZE/2)+5)*2)-stickhight/2, verbindungssticklenght, stickhight);
		rect(HofSet+sticklenght+(((CIRCLE_SIZE/2)+5)*2)-stickhight/2, VofSet+(sticklenght*2)+(((CIRCLE_SIZE/2)+5)*4)-(verbindungssticklenght*1.5f), stickhight, verbindungssticklenght);
	}

}
record Point(int x,int y) {}
