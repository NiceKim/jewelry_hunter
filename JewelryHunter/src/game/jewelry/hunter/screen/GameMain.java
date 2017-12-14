package game.jewelry.hunter.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.jewelry.hunter.objects.GameMap;
import game.jewelry.hunter.objects.GameObject;
import game.jewelry.hunter.objects.Jewelry;
import game.jewelry.hunter.objects.Monster;

import game.jewelry.hunter.objects.User;
import game.jewelry.hunter.objects.Rock; 

public class GameMain extends JFrame { 
	//UI
	private URL btnImgURL = getClass().getResource("img/PixelArt.png");
	private JLayeredPane layeredPane;
	private JLayeredPane gameMain;
	private JButton start;
	private JButton explan;
	private JButton introExit;
	private ExplainDialog explainDialog; 
	private boolean change = false;

	public JPanel GameGround; 
	public JPanel GameMessage; 

	//������Ʈ ��ü ����
	public User user;
	public Rock[] rocks;
	public Monster monster;

	//GUI�� ���� JLabel���� ->Repaint�� ��ü�� ����
	public JLabel userInfo; 
	public JButton exit; 

	//Info�� ��� �� ������
	public int time;
	public int jewelLeft;
	public int score;
	//public int detector; ���� ���� ���� ���� ����

	Map<String, ArrayList<GameObject>> objectsMap = new HashMap();

	GameMain(){ 
		//Frame ���� 
		Scanner s = new Scanner(System.in);
		setTitle(GameMap.strGameTitle); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLayout(null); 

		setSize(1000 ,700); 
		setLocationRelativeTo(null);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1000, 700);
		layeredPane.setLayout(null);

		JPanel intro = null;

		try {
			intro = new IntroPanel("img/lava-anim-dribbble.png");
			explainDialog = new ExplainDialog(this, "Explain");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.add(intro);
		intro.setBounds(0, 0, 1000, 700);

		start = new JButton(new ImageIcon("img/PixelArt.png"));
		start.setBounds(400, 330, 200, 78);
		layeredPane.add(start);

		explan = new JButton(new ImageIcon("img/PixelArt.png"));
		explan.setBounds(400, 430, 200, 78);
		layeredPane.add(explan);

		explainDialog.setLocationRelativeTo(this);

		introExit = new JButton(new ImageIcon("img/PixelArt.png"));
		introExit.setBounds(400, 530, 200, 78);
		layeredPane.add(introExit);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				change = true;
				getContentPane().removeAll();
				getContentPane().add(GameGround);
				getContentPane().add(GameMessage);
				revalidate();
				repaint();
				//Start Timer
				//��׶��� ������ ����
				( new BackGroundThread() ).start();

			}
		});

		explan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				explainDialog.setVisible(true); //���̾�α� ���
			}
		});

		introExit.addActionListener(new ExitActionListener());

		layeredPane.add(intro);
		add(layeredPane);
		setVisible(true);

		GameGround = new JPanel(); 
		GameMessage = new JPanel(); 
		GameGround.setLayout(null); 
		GameMessage.setLayout(null); 


		GameGround.setBounds(0,0,GameMap.MAX_WIDTH,GameMap.HEIGHT); 
		System.out.printf("GameGround: %d, %d, \n", GameGround.getWidth(), GameGround.getHeight()); 

		GameMessage.setBounds(GameMap.WIDTH,0,300,GameMap.HEIGHT); 
		System.out.printf("GameMessage: %d, %d, \n", GameMessage.getWidth(), GameMessage.getHeight()); 

		GameGround.setBackground(Color.WHITE); 
		GameMessage.setBackground(Color.GRAY); 


		//���ΰ� ��ü ���� 
		user= new User("�÷��̾�", new Point(GameMap.XCENTER,GameMap.YCENTER));
		System.out.printf("%s�� �ʱ� ��ġ�� (%d, %d) �Դϴ�. \n", user.name, user.getLocation().x, user.getLocation().y); 

		//  ���� ��ü ����
		monster = new Monster("Monster", new Point(2,2), 10);
		GameGround.add(monster.getObjectDisplay());

		//���ΰ� JLabel ��ü ���� �� Frame�� Add 
		GameGround.add(user.getObjectDisplay()); 

		//������ġ�� TextBox�� ��� 
		userInfo= new JLabel(updatedInfo());  
		userInfo.setLocation(10,20); 
		userInfo.setSize(700,20); 
		GameMessage.add(userInfo); 

		//�����ư
		exit = new JButton("����"); 
		exit.setLocation(200, 600); 
		exit.setSize(80,30);  
		GameMessage.add(exit);

		GameGround.addKeyListener(new GameKeyListener());	 
		exit.addActionListener(new GameActionListener()); 

		add(GameMessage); 
		add(GameGround); 

		//�������� ������Ʈ ����
		newStage();

		setResizable(false); 
		setVisible(true); 
		GameGround.requestFocus(); 

		
	} 
	class ExitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int exit = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "����â",
					JOptionPane.YES_NO_OPTION);
			if (exit == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "Goodbye");
				System.exit(0);
			}
		}
	}


	class IntroPanel extends JPanel {
		private Image background;

		public IntroPanel(String fileName) throws IOException {
			background = ImageIO.read(new File(fileName));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(background, 0, 0, null);

		}
	}

	class ExplainDialog extends JDialog {
		private Image background;
		private JButton okBtn;
		private JLabel explainImg;


		public ExplainDialog (JFrame frame, String title) throws IOException {
			super(frame, title);

			setLayout(null);
			explainImg = new JLabel((new ImageIcon("img/tmp_explain.png")));
			explainImg.setBounds(0, 0, 500, 500);
			add(explainImg);
			okBtn = new JButton("OK");
			okBtn.setBounds(220, 400, 60, 30);
			add(okBtn);
			setSize(500, 500);

			okBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}

		public void paintComponent(Graphics g) {
			super.paintComponents(g);

			g.drawImage(background, 0, 0, null);

		}
	}

	public void newStage() {

		// ���� ������ ��ġ�� �߰�.
		for(int i=0; i<5; i++) {
			boolean overLapError = true;
			while(overLapError){ //overLapError�� ����� �ٽ�
				int x = (int) (Math.random() * GameMap.XSIZE-1);
				int y = (int) (Math.random() * GameMap.YSIZE-1);
				if(GameMap.isCenter(x, y))
					continue;//�÷��̾� ��ġ���� ������ ������ �� ����
				// Get Array of objects of the point
				ArrayList<GameObject>objArray = objectsMap.get(x+","+y);
				boolean hasJewelry = false;
				if(objArray==null) objArray = new ArrayList<GameObject>();
				for(GameObject obj : objArray) {
					if(obj instanceof Jewelry) {
						hasJewelry = true;
						break;
					}
				}
				if(!hasJewelry) {
					int type = (int) (Math.random() * 10);
					Jewelry jewelry;
					if( type == 0)
						jewelry = new Jewelry("�÷�Ƽ��" ,new Point(x, y), 500);
					else if( type < 4)
						jewelry = new Jewelry("���" ,new Point(x, y), 200);
					else if ( type < 7)
						jewelry = new Jewelry("�ǹ�" ,new Point(x, y), 100);
					else
						jewelry = new Jewelry("�����" ,new Point(x, y), 10);
					//^���� Ÿ�� ����
					objArray.add(jewelry);
					GameGround.add(jewelry.getObjectDisplay());
					objectsMap.put(x+","+y, objArray);
					jewelLeft ++;
					overLapError = false;

				}
			}
		}

		for(int x=0; x<GameMap.XSIZE; x++){
			for(int y=0; y<GameMap.YSIZE; y++){
				//�߽��� ������ ��� ���� ������ ä���.
				if(!GameMap.isCenter(x,y)){
					Rock rock = new Rock("����",new Point(x,y),1);
					// Get Array of objects of the point
					ArrayList<GameObject>objArray = objectsMap.get(x+","+y);
					if(objArray==null) objArray = new ArrayList<GameObject>();
					objArray.add(rock);
					GameGround.add(rock.getObjectDisplay());
					objectsMap.put(x+","+y, objArray);
				}
			}
		}

	}

	public String updatedInfo() 
	{ return "���� �ð�: " + time/10 + " / ���� ��ġ: (" + (user.getLocation().x) +", " + (user.getLocation().y) + ")" + " / ����: " + score + "/ ���� ����: " + jewelLeft; }

	public void refreshStage() { 

		GameGround.removeAll();
		GameGround.revalidate();
		GameGround.repaint();
		objectsMap.clear();
		user.getLocation().x = GameMap.XCENTER;
		user.getLocation().y = GameMap.YCENTER;
		GameGround.add(user.getObjectDisplay());
		user.getObjectDisplay().setLocation(user.computeX(), user.computeY()); 
	}

	// Ű���� �̺�Ʈ ó�� 
	class GameKeyListener extends KeyAdapter{ 

		public void keyPressed(KeyEvent e){ 
			int keyCode = e.getKeyCode(); 
			int moveX=0, moveY=0;

			if(user.canMove){
				switch(keyCode){ 
				case KeyEvent.VK_UP: moveY= -1; break; 
				case KeyEvent.VK_DOWN: moveY= +1; break; 
				case KeyEvent.VK_LEFT: moveX= -1; break; 
				case KeyEvent.VK_RIGHT: moveX= +1; break; 
				default: return;  
				} 
				user.move(moveX, moveY);
				userInfo.setText(updatedInfo());

				ArrayList<GameObject>objArray = objectsMap.get(user.getLocation().x+","+user.getLocation().y);
				// ���� ������Ʈ ��ȣ �ۿ� ���� 
				if(objArray!=null) {
					for(GameObject obj : objArray) {

						if(obj instanceof Rock) {
							((Rock) obj).hit(1);
							if(((Rock) obj).getDurability() <= 0) {
								System.out.println("Removing Rock");
								objArray.remove(obj);
								GameGround.remove(obj.getObjectDisplay());
								objectsMap.put(user.getLocation().x+","+user.getLocation().y, objArray);
							}
							user.move(-moveX, -moveY);
							return;
						}
					}
					for(GameObject obj : objArray) {
						if(obj instanceof Jewelry) {
							jewelLeft --;
							score += ((Jewelry)obj).getScore();
							System.out.println("Removing Jewelry");
							objArray.remove(obj);
							GameGround.remove(obj.getObjectDisplay());
							objectsMap.put(user.getLocation().x+","+user.getLocation().y, objArray);
							//���� ������ ������ 0�϶� �� ��������
							if(jewelLeft == 0){
								refreshStage(); 
								newStage();
								time += 100; //10�� �߰�
							}
							break;
						}
					}
				}

				//���� ���� 
				detect(objArray);
			} 

		}
	}


	class GameActionListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e){ 
			JButton b = (JButton)e.getSource(); 
			if(b.getText().equals("����")) 
				System.exit(0); 
		} 
	} 


	//0.1�ʸ��� �ѹ��� ����
	class BackGroundThread extends Thread{
		public void run(){
			for(time = 300; time>=0; time--){
				try {Thread.sleep(100);}
				catch (InterruptedException e)
				{ e.printStackTrace(); }	
				userInfo.setText(updatedInfo());
				//repaint
				user.canMove=true;
				GameGround.requestFocus(); 
			}
			System.out.println("�ð� �ʰ�");
		}
	}

	//���� ���� �޼ҵ�
	public void detect(ArrayList<GameObject>objArray) {
		boolean detected=false;

		for(int i = user.getLocation().x-1; i <= user.getLocation().x+1; i++)
			for(int j = user.getLocation().y-1; j <= user.getLocation().y+1; j++){
				if(!detected){
					objArray = objectsMap.get(i+","+j);
					if(objArray!=null) {
						for(GameObject obj : objArray)
							if(obj instanceof Jewelry){
								System.out.println("�ſ� �����: �ʷϻ�");
								detected=true;
							}
					}
				}
			}
		for(int i = user.getLocation().x-2; i <= user.getLocation().x+2; i++)
			for(int j = user.getLocation().y-2; j <= user.getLocation().y+2; j++){
				if(!detected){
					objArray = objectsMap.get(i+","+j);
					if(objArray!=null) {
						for(GameObject obj : objArray)
							if(obj instanceof Jewelry){
								System.out.println("�����: �����");
								detected = true;
							} 
					}
				}
			}
		if(!detected)
			System.out.println("������ ���� ����");
	}


	public static void main(String args[]){ 
		new GameMain(); 
	} 

} 

