package minesweeperGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Minesweeper extends Model{
	
	public Minesweeper() {
		this.setTitle("Minesweeper");
		this.setSize(820, 560);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
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
		
		Board board = new Board();
		this.setContentPane(board);
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
	}
	

	public class Board extends JPanel{
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 820, 560);
			for(int i=0; i < 16; i++) {
				for(int j = 0; j < 9; j++) {
					g.setColor(Color.GRAY);
				
//					if(mines[i][j] == 1) {
//						g.setColor(Color.YELLOW);
//					}
					
					if(revealed[i][j] == true) {
						g.setColor(Color.white);
						if(mines[i][j] == 1) {
							g.setColor(Color.red);
						}
					}
					
					if(mx >= spacing+i*50 && mx < spacing+i*50+50-2*spacing && my >= spacing+j*50+50+26 && 
							my < spacing+j*50+50+50+26-2*spacing) {
						g.setColor(Color.LIGHT_GRAY);
					}
					
					g.fillRect(spacing+i*50, spacing+j*50+50, 50-2*spacing, 50-2*spacing);
					if(revealed[i][j] == true) {
						g.setColor(Color.black);
						if(mines[i][j] == 0 && neighbours[i][j] != 0) {			
							if(neighbours[i][j] == 1) {
								g.setColor(Color.blue);
							}else if(neighbours[i][j] == 2) {
								g.setColor(Color.green);
							}else if(neighbours[i][j] == 3) {
								g.setColor(Color.red);
							}else if(neighbours[i][j] == 4) {
								g.setColor(new Color(0,0,128));
							}else if(neighbours[i][j] == 5) {
								g.setColor(new Color(178,34,34));
							}else if(neighbours[i][j] == 6) {
								g.setColor(new Color(72,209,204));
							}else if(neighbours[i][j] == 8) {
								g.setColor(Color.darkGray);
							}
							g.setFont(new Font("Tahoma", Font.BOLD, 40));
							g.drawString(Integer.toString(neighbours[i][j]), i*50+12, j*50+50+40);
						}else if(mines[i][j] == 1){
							g.fillRect(i*50+15, j*50+55, 20, 40);
							g.fillRect(i*50+5, j*50+65, 40, 20);
							g.fillRect(i*50+10, j*50+60, 30, 30);

						}
					}
					
//					flags
					if(flagged[i][j] == true) {
						g.setColor(Color.black);
						g.fillRect(i*50+32, i*50+50+15, 5, 40);
						g.fillRect(i*50+20, i*50+50+50, 30, 10);
						g.setColor(Color.red);
						g.fillRect(i*50+16, i*50+50+15, 20, 15);
						g.setColor(Color.black);
						g.drawRect(i*50+16, i*50+50+15, 20, 15);
						g.drawRect(i*50+17, i*50+50+16, 18, 13);
						g.drawRect(i*50+18, i*50+50+17, 16, 11);
					}
				}
			}
			
//			smiley			
			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 45, 45);
			g.setColor(Color.black);
			g.fillOval(smileyX+10, smileyY+12, 6, 6);
			g.fillOval(smileyX+30, smileyY+12, 6, 6);
			
			if(happiness == true) {
				g.fillRect(smileyX+16, smileyY+30, 15, 4);
				g.fillRect(smileyX+13, smileyY+28, 4, 4);
				g.fillRect(smileyX+30, smileyY+28, 4, 4);
			}else {
				g.fillRect(smileyX+16, smileyY+28, 15, 3);
				g.fillRect(smileyX+13, smileyY+30, 4, 4);
				g.fillRect(smileyX+30, smileyY+30, 4, 4);
			}
			
//			flagger
			g.setColor(Color.black);
			g.fillRect(flaggerX+26, flaggerY+26, 4, 8);
			g.fillRect(flaggerX+20, flaggerY+32, 10, 3);
			
			g.setColor(Color.red);
			g.fillRect(flaggerX+12, flaggerY+10, 16, 12);
			
			g.setColor(Color.black);
			g.drawRect(flaggerX+12, flaggerY+10, 16, 14);
			g.drawRect(flaggerX+13, flaggerY+11, 16, 13);
			g.drawRect(flaggerX+14, flaggerY+12, 14, 11);

			if(flagger == true) {
				g.setColor(Color.red);
			}
				
			g.drawOval(flaggerX, flaggerY, 42, 42);
			g.drawOval(flaggerX+1, flaggerY+1, 40, 40);
			g.drawOval(flaggerX+2, flaggerY+2, 38, 38);

			
//			time counter
			g.setColor(Color.darkGray);
			g.fillRect(timeX, timeY, 56, 26);
			if(defeat == false && victory == false) {
				sec = (int)((new Date().getTime()-startDate.getTime()) / 1000);
			}
			if(sec > 999) {
				sec = 999;
			}
			g.setColor(Color.WHITE);
			if(victory == true) {
				g.setColor(Color.GREEN);
			}else if(defeat == true) {
				g.setColor(Color.RED);
			}
			
			g.setFont(new Font("Tahoma", Font.PLAIN, 40));
			if(sec < 10) {
				g.drawString("00"+Integer.toString(sec), timeX, timeY+25);
			}else if(sec < 100) {
				g.drawString("0"+Integer.toString(sec), timeX, timeY+25);
			}else {
				g.drawString(Integer.toString(sec), timeX, timeY+25);
			}
			
//			victory message
			if(victory == true) {
				g.setColor(Color.GREEN);
				vicMes = "YOU WIN";
			}else if(defeat == true) {
				g.setColor(Color.red);
				vicMes = "YOU LOSE";
			}
			
			if(victory == true || defeat == true) {
				vicMesY = -40 + (int)(new Date().getTime() - endDate.getTime()) / 10;
				if(vicMesY > 40) {
					vicMesY = 40;
				}
				g.drawString(vicMes, vicMesX, vicMesY);
			}
		}
	}
	
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
		}
	}
	
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			mx = e.getX();
			my = e.getY();
			
			if(ifInBoxX() != -1 && ifInBoxY() != -1) {
				System.out.println("the mouse is in the [" + ifInBoxX() + "," + ifInBoxY() + "], number of mine neighs: " 
						+ neighbours[ifInBoxX()][ifInBoxY()]);
				if(flagger == true && revealed[ifInBoxX()][ifInBoxY()] == false) {
					if(flagged[ifInBoxX()][ifInBoxY()] = false) {
						flagged[ifInBoxX()][ifInBoxY()] = true;
					}else {
						flagged[ifInBoxX()][ifInBoxY()] = false;
					}
				}else {
					if(flagged[ifInBoxX()][ifInBoxY()] == false) {
						revealed[ifInBoxX()][ifInBoxY()] = true;
					}
				}
			}else {
				System.out.println("the pointer is not inside of any box...!");
			}
			
			if(inSmiley() == true) {
				resetAll();
				System.out.println("In smiley = true...!");
			}
			
			if(inFlagger() == true) {
				if(flagger == false) {
					flagger = true;
					System.out.println("In flagger = true");
				}else {
					flagger = false;
					System.out.println("In flagger = false...!");
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub		
		}
		
	}
	
}
