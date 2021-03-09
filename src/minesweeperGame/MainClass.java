package minesweeperGame;

public class MainClass implements Runnable{

	Minesweeper gui = new Minesweeper();
	
	public static void main(String[] args) {
		new  Thread(new MainClass()).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			gui.repaint();
			if(gui.resetter == false) {
				gui.checkVictoryStatus();
//				System.out.println("Victory: " + gui.victory + ", Defeat: " + gui.defeat);
			}
		}
	}

}
