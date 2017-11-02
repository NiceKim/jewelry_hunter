package jewelry_finder.intro;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jewelry_finder.gamestart.GameStart;

public class IntroWindow extends JFrame{

	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 720;

	private CardLayout cards = new CardLayout();

	JPanel panel = new JPanel();
	GameStart gamePanel = new GameStart();
	JButton btnStart = new JButton("����");
	JButton btnexp = new JButton("���ӹ��");
	JButton btnExit = new JButton("����");

	IntroWindow() {
		setTitle("Jewelry Finder");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null); //����� ����â�� ȭ�� �߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����â ����� ���α׷��� �Բ� ����(�ݵ�� �ʿ�)
		setVisible(true);

		add(panel); //�г��߰�
		add(gamePanel);
		panel.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel();
			}
		});
		panel.add(btnexp);
		panel.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int exit = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "����â",
						JOptionPane.YES_NO_OPTION);
				if (exit == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Goodbye");
					System.exit(0);
				}
			}

		});
	}

	public void changePanel() {
		cards.next(this.getContentPane());
	}

	public static void main(String[] args) {
		new IntroWindow();
	}
}
