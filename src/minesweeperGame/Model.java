package minesweeperGame;

import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;

public class Model extends JFrame {

	public boolean resetter = false;
	
	public boolean flagger = false;
	
	Date startDate = new Date();
	Date endDate ;

	int spacing = 3;
	
	int neighs = 0;
	
	String vicMes = "Nothing yet...!";
		
	public int mx = -100;
	public int my = -100;
	
	public int smileyX = 374;
	public int smileyY = 2;

	public int smileyCenterX = smileyX + 35;
	public int smileyCenterY = smileyY + 35;

	public int flaggerX = 245;
	public int flaggerY = 2;
	
	public int flaggerCenterX = flaggerX + 35;
	public int flaggerCenterY = flaggerY + 35;

	
	public int timeX = 732;
	public int timeY = 15;
	
	public int vicMesX = 495;
	public int vicMesY = -40;
	
	public int sec = 0;
	
	public boolean happiness = true;
	
	public boolean victory = false;
	public boolean defeat = false;

	
	Random random = new Random();
	
	int[][] mines = new int[16][9];
	int[][] neighbours = new int[16][9];
	boolean[][] revealed = new boolean[16][9];
	boolean[][] flagged = new boolean[16][9];
	
	
//All methods are included here.....	
	
	public void checkVictoryStatus() {
		if(defeat == false) {
			for(int i=0; i < 16; i++) {
				for(int j = 0; j < 9; j++) {
					if(mines[i][j] == 1 && revealed[i][j] == true) {
						defeat = true;
						happiness = false;
						endDate = new Date();
					}
				}
			}
		}
		
		if(totalBoxesRevealed() >= 144 - totalMines() && victory == false) {
			victory = true;
			endDate = new Date();

		}
	}
	
	public int totalMines() {
		int total = 0;
		for(int i=0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(mines[i][j] == 1) {
					total++;
				}
			}
		}
		return total;
	}
	
	public int totalBoxesRevealed() {
		int total = 0;
		for(int i=0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(revealed[i][j] == true) {
					total++;
				}
			}
		}
		return total;
	}
	
	public void resetAll() {
		
		resetter = true;
		
		flagger = false;
		
		startDate = new Date();
		
		vicMesY = -20;
		
		vicMes = "Nothing yet...!";
		
		happiness = true;
		victory = false;
		defeat = false;
		
		for(int i=0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(random.nextInt(100) < 20) {
					mines[i][j] = 1;
				}else {
					mines[i][j] = 0;
				}
				revealed[i][j] = false;
				flagged[i][j] = false;
			}
		}
		
		for(int i=0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				neighs = 0;
				for(int m = 0; m < 16; m++) {
					for(int n = 0; n < 9; n++) {
						if(!(m == i && n == j)){
							if(isN(i,j,m,n) == true)
								neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}
		resetter = false;
	}
	
	public boolean inSmiley() {
		int dif = (int) Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX) +
				Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
		if(dif < 35) {
			return true;
		}
		return false;
	}
	
	public boolean inFlagger() {
		int dif = (int) Math.sqrt(Math.abs(mx-flaggerCenterX)*Math.abs(mx-flaggerCenterX) +
				Math.abs(my-flaggerCenterY)*Math.abs(my-flaggerCenterY));
		if(dif < 35) {
			return true;
		}
		return false;
	}
	
	public int ifInBoxX() {
		for(int i=0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(mx >= spacing+i*50 && mx < spacing+i*50+50-2*spacing && my >= spacing+j*50+50+26 && 
						my < spacing+j*50+50+50+26-2*spacing) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int ifInBoxY() {
		for(int i=0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(mx >= spacing+i*50 && mx < spacing+i*50+50-2*spacing && my >= spacing+j*50+50+26 && 
						my < spacing+j*50+50+50+26-2*spacing) {
					return j;
				}
			}
		}
		return -1;
	}
	
	protected boolean isN(int mX, int mY, int cX, int cY) {
		if(mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1) {
			return true;
		}
		return false;
	}
	
	
	
}
