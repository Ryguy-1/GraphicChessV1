
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class ChessSim2 {

	GamePanel panel;

	JFrame frame;

	public static JProgressBar progressBar;

	static int width = 1200;
	static int height = 840;

	public static void main(String[] args) {

		ChessSim2 sim = new ChessSim2();

	}

	ChessSim2() {

		frame = new JFrame();

		panel = new GamePanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		panel.add(progressBar);

		frame.add(panel);
		frame.addMouseListener(panel);

		setup();
	}

	void setup() {

		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.startSim();

		frame.setBackground(Color.gray);

	}

}

//the king castle swap is not working right now at least with the black pieces and the computer cannot yet control the black pieces.



