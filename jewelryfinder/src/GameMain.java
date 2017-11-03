import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;


public class GameMain extends JFrame {
	public JPanel GameGround;
	public JPanel GameMessage;

	//������Ʈ ��ü ����
	public Player User;
	
	//GUI�� ���� JLabel����
	public 	JLabel UserLabel;
	public JLabel UserInfo;
	public JTextField UserLocation;
	public JButton exit;

	GameMain(){
		//Frame ����
		setTitle(GameMap.strGameTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		GameGround = new JPanel();
		GameMessage = new JPanel();
		GameGround.setLayout(null);
		GameMessage.setLayout(null);
		
		GameGround.setBounds(0,0,GameMap.MAX_WIDTH,GameMap.HEIGHT);
		System.out.printf("GameGround: %d, %d, \n", GameGround.getWidth(), GameGround.getHeight());
		
		GameMessage.setBounds(0,GameMap.HEIGHT,GameMap.MAX_HEIGHT,80);
		System.out.printf("GameMessage: %d, %d, \n", GameMessage.getWidth(), GameMessage.getHeight());
		
		GameGround.setBackground(Color.WHITE);
		GameMessage.setBackground(Color.GRAY);
		
		setSize(GameMap.MAX_WIDTH,GameMap.MAX_HEIGHT);
		
		//���ΰ� ��ü ����
		User= new Player("�÷��̾�",2,2);
		System.out.printf("%s�� �ʱ� ��ġ�� (%d, %d) �Դϴ�. \n", User.name, User.getX(), User.getY());
		
		//���ΰ� JLabel ��ü ���� �� Frame�� Add
		UserLabel= new JLabel(User.name);
		UserLabel.setLocation(User.getX(),User.getY());
		UserLabel.setSize(GameObject.WIDTH,GameObject.HEIGHT);
		UserLabel.setForeground(Color.BLUE);
		GameGround.add(UserLabel);
		
		//������ġ�� TextBox�� ���
		UserInfo= new JLabel("���� ��ġ: (0, 0)");
		UserInfo.setLocation(10,20);
		UserInfo.setSize(150,20);
		GameMessage.add(UserInfo);
		
		//�����ư
		exit = new JButton("����");
		exit.setLocation(400,15);
		exit.setSize(80,30);
		GameMessage.add(exit);
		
		GameGround.addKeyListener(new GameKeyListener());	
		exit.addActionListener(new GameActionListener());
		
		add(GameMessage);
		add(GameGround);
		
		setResizable(false);
		setVisible(true);
		GameGround.requestFocus();
	}

	//KeyEvent
	class GameKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keyCode = e.getKeyCode();
			switch(keyCode){
			case KeyEvent.VK_UP: User.move(0, -User.MOVING_UNIT); break;
			case KeyEvent.VK_DOWN: User.move(0, +User.MOVING_UNIT); break;
			case KeyEvent.VK_LEFT: User.move(-User.MOVING_UNIT, 0); break;
			case KeyEvent.VK_RIGHT: User.move(+User.MOVING_UNIT, 0); break;
			default: return; 
			}
			UserLabel.setLocation(User.getX(),User.getY());
			System.out.printf("%s�� (%d,%d)�� �̵��߽��ϴ�. \n", User.name, (User.getX()/100), (User.getY()/100));
			UserInfo.setText("���� ��ġ: (" + (User.getX()/100) +", " + (User.getY()/100) + ")");
		}
	}
	
	class GameActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JButton b = (JButton)e.getSource();
			if(b.getText().equals("����"))
				System.exit(0);
		}
	}

	public static void main(String args[]){
		new GameMain();
	}
}
