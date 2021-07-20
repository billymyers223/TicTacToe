import java.util.Random;
import static java.lang.System.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class tictactoe extends JFrame implements ActionListener
{
	Container win;
	private static JComponent contentPane;
	private static Insets no_margin = new Insets(-5,-5,-5,-5);

	private ImageIcon black = new ImageIcon("biggerBlack.jpg");
	private ImageIcon O = new ImageIcon("O.jpg");
	private ImageIcon X = new ImageIcon("X.jpg");
	private  ImageIcon start = new ImageIcon("Start.jpg");
	private  ImageIcon xwins = new ImageIcon("XWINS.jpg");
	private  ImageIcon owins = new ImageIcon("OWINS.jpg");
	private  ImageIcon tie = new ImageIcon("TIE.jpg");
	private int compR, compC, count, reGame = 0;
 	private JButton buttonXWINS = new JButton(xwins);
 	private JButton buttonOWINS = new JButton(owins);
 	private JButton buttonTIE = new JButton(tie);
 	private JButton buttonSTART = new JButton(start);
 	private JButton buttonAGAIN = new JButton("play again?");
	private int turn,storeR,storeC;
	private JLabel labelX = new JLabel(X);
	private JLabel labelO = new JLabel(O);
	private  ArrayList <Integer> playerR = new  ArrayList <Integer>();
	private ArrayList <Integer> compRow = new ArrayList <Integer>();  
	private ArrayList <Integer> compCol = new ArrayList <Integer>(); 
	private  ArrayList <Integer> playerC = new  ArrayList <Integer>();  
 	private JTextField display = new JTextField(20);

   	private boolean userTurn, winner,sub,compTurn,gameDone;
   	/*
   	I recommend using two matrices: one that is the visual representation on the screen (matrix) and the other would be  used for calcuations, storing
   	1 for user and 4 for the computer in the 3x3 grid. Row 4 is used for column totals and one diagonal total, and column 3 is used for
   	row totals and one diagonal total.
   	
   	User is 1; computer is 4.
   	*/

   	private int matrixSol[][];		   //Calculations for AI.
	private JButton matrix[][];		   //What you see.

	//Constructor
   	public tictactoe()
   	{
   		
   		super("Tic Tac Toe by Billy Myers");
		win = getContentPane();
		win.setLayout(null);
		matrixSol = new int[5][4];
		matrix = new JButton[3][3];
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
			{
				matrix[r][c]= new JButton(black);		//Instantiates each button
				matrix[r][c].setSize(100,100);
				matrix[r][c].setActionCommand(Integer.toString(r) + "," + Integer.toString(c));
				matrix[r][c].setLocation(r * 105 + 10, c * 105 + 10);
				matrix[r][c].addActionListener(this);
			}
		
		userTurn = true;
		winner = false;
		
		buttonSTART.setMargin(no_margin);
		buttonSTART.setBorderPainted(false);
		buttonSTART.setMnemonic(KeyEvent.VK_S);
		buttonSTART.setActionCommand("start");		//start will be returned by getActionCommand  (See below on e.getActionCommand())
		buttonSTART.setLocation(10,10);
		buttonSTART.setSize(440,440);
		buttonSTART.addActionListener(this);
		win.add(buttonSTART);
		
		
			buttonAGAIN.setSize(100,40);
			buttonAGAIN.setLocation(20,350);
			buttonAGAIN.setMnemonic(KeyEvent.VK_S);
			buttonAGAIN.setActionCommand("again");
			buttonAGAIN.addActionListener(this);
			win.add(buttonAGAIN);
			buttonAGAIN.setVisible(false);
       	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,450);
		setVisible(true);
	}



 	public void actionPerformed(ActionEvent e)
    {
    	
    		
    		
    	
    	if ("start".equals(e.getActionCommand()))							//Start game
    	{
  			win.remove(buttonSTART);
			for (int r = 0; r < 3; r++)
				for(int c=0;c<3;c++)
				{
					matrix[r][c].setIcon(black);
					win.add(matrix[r][c]);
				}

			repaint();
			
			display.setText("Start Game! X's Move.");
			display.setLocation(200,350);
			display.setSize(200,20);
			
			
			
			win.add(display);
			
			
			
			
    	}
    	else if("again".equals(e.getActionCommand()))				//Play again
    	{
    		System.out.println("AGAIN");
    	
    		if(e.getSource() == buttonAGAIN){
    			System.out.println("ButtonAgain");
    		
				for(int t = 0; t<= 5 -1; t++){
				for(int j = 0; j<= 4 -1; j++){
					matrixSol[t][j] = 0;
					
				}
			}
		
		
					userTurn = true;
					
					reGame = 1;
			
			
				
			for (int g = 0; g < 3; g++)
				for(int b=0;b<3;b++)
				{
					matrix[g][b].setIcon(black);
					win.add(matrix[g][b]);
					
				}
				
				
		
     			compR = 0;
     			compC = 0;
     			count = 0;
     			gameDone = false;
     			repaint(); 
		
		
			buttonAGAIN.setVisible(false);
		display.setText("Start Game! X's Move.");
					
					
				
     				
			}
    				
     		
    	}


    	else
			
			/*********************   C Y C L E    T U R N S  **********************/
			
			
			if(!gameDone){
			
       		if (!done())
       		{
				userTurn = true;
       			display.setText("Game in Progress");
       			String s = e.getActionCommand();
       			System.out.println(s);
       			int r = Integer.parseInt(""+s.charAt(0));
       			int c = Integer.parseInt(""+s.charAt(2));
       			storeR = r;
       			storeC = c;
       			if (!position(r,c))					
    				{
    					display.setText("Choose another square");
    					
    					
    					userTurn = true;
    				}
    			else
    					userTurn = false;
				
				playerC.add(c);
    			playerR.add(r);
				System.out.println(playerR);
    			System.out.println(playerC);
				if (!done() && !userTurn)
				{
					count++;
					computerGo();
					done();
					userTurn = true;
				}
    			
    		}
			}
		
    }



	
		
	
    public void computerGo()
    {
    	compTurn = true;
    	boolean  now = false;
    	if(!gameDone){
    	if(count !=5){
    	
    	while(now == false && compTurn == true){
    		
    	
    	
    	
    	int range = 3 - 0;
    	int rand = (int)(Math.random() * range) + 0;
    	int randC = (int)(Math.random() * range) + 0;
    	System.out.println("R: " +rand);
    	System.out.println("C: " + randC);
    	int r = 0;
    	int c = 0;
    	
    	if(matrixSol[4][0] == 2){
    		r = 0;
    		c =randC;
    		System.out.println("Comp C: " + c);
    	}
    	else if(matrixSol[4][1] == 2){
    		r = 1;
    		c =randC;
    		System.out.println("Comp C: " + c);
    	}
    	else if(matrixSol[4][2] == 2){
    		r = 2;
    		c =randC;
    		System.out.println("Comp C: " + c);
    	}
    	else if(matrixSol[1][3] == 2){
    		r = rand;
    		c =0;
    		System.out.println("Comp R: " + r);
    	}
    	else if(matrixSol[2][3] == 2){
    		r = rand;
    		c =1;
    		System.out.println("Comp R: " + r);
    	}
    	else if(matrixSol[3][3] == 2){
    		r = rand;
    		c =2;
    		System.out.println("Comp R: " + r);
    	}
    	else if(matrixSol[0][3] == 2){
    		
    		
    		if(matrixSol[2][1] != 1 && matrixSol[2][1] != 4){
    			r = 1;
    			c = 1;
    			System.out.println(" ERROR D: " + r);
    		}
    		else if(matrixSol[3][0] != 1 && matrixSol[3][0] != 4){
    			r = 2;
    			c = 0;
    			System.out.println(" ERROR R: " + r);
    		}
    		else if(matrixSol[1][2] !=1 && matrixSol[1][2] !=4){
    			r = 0;
    			c = 2;
    			System.out.println(" ERROR F: " + r + " " + c);
    		}
    		
    		
    	}
    	else if(matrixSol[4][3] == 2){
    		if(matrixSol[1][0] !=1 && matrixSol[1][0] != 4){
    			r =0;
    			c = 0;
    		}
    		else if(matrixSol[2][1] !=1 && matrixSol[2][1] != 4){
    			r =1;
    			c = 1;
    		}
    		else if(matrixSol[3][2] !=1 && matrixSol[3][2] != 4){
    			r =2;
    			c = 2;
    		}
    		
    		
    	}	
    	else{
    	
    	
		
    	
    	r = rand;
    	c = randC;
    	}
    	
    	if (!position(r,c))						
    				{
    					display.setText("Choose another square");
    					
    				}
    	else{
    		now = true;
    					compTurn = false;	
    	}
    	
    
	}
    	}
	else if(count == 5){
		System.out.println("Its a tie:" );
		display.setText("It's a tie!");
		gameDone = true;
	}
    	}
    }


	public boolean done ()
	{
		//Finish this method or delete if you choose not to use it.

	   	//Calculate totals********************************************************************
	   	
	   	matrixSol[0][3] = (matrixSol[1][2] + matrixSol[2][1] + matrixSol[3][0]);//Top right to left diagonal
	   	System.out.println("Diagonal" + matrixSol[0][3]);
		matrixSol[4][0] = (matrixSol[1][0] + matrixSol[1][1] + matrixSol[1][2]);// Each Colum
		matrixSol[4][1] = (matrixSol[2][0] + matrixSol[2][1] + matrixSol[2][2]);
		matrixSol[4][2] = (matrixSol[3][0] + matrixSol[3][1] + matrixSol[3][2]);
		
		matrixSol[1][3] = (matrixSol[1][0] + matrixSol[2][0] + matrixSol[3][0]);//Each Row
		matrixSol[2][3] = (matrixSol[1][1] + matrixSol[2][1] + matrixSol[3][1]);
		matrixSol[3][3] = (matrixSol[1][2] + matrixSol[2][2] + matrixSol[3][2]);
		
		matrixSol[4][3] = (matrixSol[1][0] + matrixSol[2][1] + matrixSol[3][2]);//Top left diagonal
	
		if(matrixSol[0][3] == 3 || matrixSol[4][0] == 3 || matrixSol[4][1] == 3 || matrixSol[4][2] == 3 || matrixSol[1][3] == 3 || matrixSol[2][3] == 3 || matrixSol[3][3] == 3 || matrixSol[4][3] == 3){
			gameDone = true;
			display.setText("X wins!");
			
		}
		if(matrixSol[0][3] == 12 || matrixSol[4][0] == 12 || matrixSol[4][1] == 12 || matrixSol[4][2] == 12 || matrixSol[1][3] == 12 || matrixSol[2][3] == 12 || matrixSol[3][3] == 12 || matrixSol[4][3] == 12){
			gameDone = true;
			display.setText("O wins!");
			
		}
	
		if(gameDone == true){
    			//display.setText("again");
    			
    		
			buttonAGAIN.setVisible(true);
    		}	

		return false;
    }

    public boolean position(int r, int c)
    {
    	if (userTurn)
    		if (matrixSol[r+1][c] == 0)
    		{
			matrixSol[r+1][c] = 1;		//user value
			matrix[r][c].setIcon(X);
			return true;
			}
			else return false;
		else
    	if (!userTurn)
    		if (matrixSol[r+1][c] == 0)
			{
			matrixSol[r+1][c] = 4;		//computer value
			matrix[r][c].setIcon(O);
			return true;
			}
			return false;

    }



    public static void main(String[] args)
    {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               new tictactoe();
            }
        });
    }
}
