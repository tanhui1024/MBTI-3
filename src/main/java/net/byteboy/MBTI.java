package net.byteboy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


/**
 * MBTI性格测试软件
 * 创建于 2016/8/12
 * @version 1.0
 * @author 梁凯文
 * 
 * 最后修改于 2016/8/12
 * 修改人: 梁凯文
 * 
 * 版权所有 梁凯文
 */
public class MBTI {
	//默认分数设置
	public static final int[][] defaultScore = {
			{2,4,6},
			{6,4,7,2,1},
			{4,2,5,7,6},
			{4,6,2,1},
			{6,4,3,5},
			{6,4,2},
			{6,2,4},
			{6,7,5,4,3,2,1},
			{7,6,4,2,1},
			{4,2,3,5,6,1}
	};
	//问题
	public static final String[] questions = {
			"你何时感觉最好？",
			"你走路时是",
			"和人说话时，你",
			"坐著休息时，你的",
			"碰到你感到发笑的事时，你的反应是",
			"当你去一个派对或社交场合时，你",
			"当你非常专心工作时，有人打断你，你会",
			"下列颜色中，你最喜欢哪一颜色？",
			"临入睡的前几分钟，你在床上的姿势是",
			"你经常梦到你在"
	};
	//问题选项
	public static final String[][] answers = {
			{"早晨","下午及傍晚","夜里"},
			{"大步的快走","小步的快走","不快，仰著头面对著世界","不快，低著头","很慢"},
			{"手臂交叠的站著","双手紧握著","一只手或两手放在臀部","碰著或推著与你说话的人","玩著你的耳朵、摸著你的下巴、或用手整理头发"},
			{"两膝盖并拢","两腿交叉","两腿伸直","一腿卷在身下"},
			{"一个欣赏的大笑","笑著，但不大声","轻声的咯咯地笑","羞怯的微笑"},
			{"很大声地入场以引起注意","安静地入场，找你认识的人","非常安静地入场，尽量保持不被注意"},
			{"欢迎他","感到非常恼怒","在上两极端之间"},
			{"红或橘色","黑色","黄或浅蓝色","绿色","深蓝或紫色","白色","棕或灰色"},
			{"仰躺，伸直","俯躺，伸直","侧躺，微卷","头睡在一手臂上","被盖过头"},
			{"落下","打架或挣扎","找东西或人","飞或漂浮","你平常不做梦","你的梦都是愉快的"}
	};
	
	public static final String[] depts = {
			"常务办公室",
			"人事办公室",
			"园区办公室",
			"新闻办公室",
			"宣传部",
			"论坛部",
			"公关部",
			"创业竞赛部",
			"实践服务部",
			"科技实践部",
			"项目部",
			"主持团"
	};
	
	private String conclusion = null;
	
	private String detail = null;
	
	private int scores[] = new int[10];
	
	private JFrame frame = null;
	
	private JDialog dialog = null;
	
	private JPanel startPanel = null;
	
	private JPanel answeringPanel = null;
	
	private BackgroundPanel bgPanel = null;
	
	private JLabel IDLabel = null;
	
	private JLabel nameLabel = null;
	
	private JLabel deptLabel = null;
	
	private JTextField IDField = null;
	
	private JTextField nameField = null;
	
	private JComboBox<String> deptBox = null;
	
	private JButton startBtn = null;
	
	private JButton nextBtn = null;
	
	private Font font = null;
	
	private int score = 0;
	
	private int questionNumber = 0;
	
	private String selectedAnswer = null;
	
	private ButtonGroup bg = new ButtonGroup();
	
	private JTextArea detailArea = null;
	
	private JLabel conclusionLabel = null;
	
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	private boolean hasSelected = false;
	
	private JButton sureBtn = null;
	
	public static void main(String[] args) {
		new MBTI();
	}
	
	public MBTI(){
		show();
	}
	
	public void init(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
        }
		frame = new JFrame("黑大创协2016MBTI性格测试软件");
		dialog = new JDialog(frame, "测试结果", true);
		startPanel = new JPanel();
		answeringPanel = new JPanel();
		bgPanel = new BackgroundPanel();
		IDLabel = new JLabel("学号：");
		nameLabel = new JLabel("姓名：");
		deptLabel = new JLabel("报名部门：");
		IDField = new JTextField();
		nameField = new JTextField();
		deptBox = new JComboBox(depts);
		startBtn = new JButton("开始答题");
		nextBtn = new JButton("下一题");
		font = new Font("Microsoft Yahei", Font.PLAIN, 20);
		conclusionLabel = new JLabel();
		detailArea = new JTextArea();
		sureBtn = new JButton("确定");
		
	}
	
	public void setAttr(){
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		answeringPanel.setLayout(new BoxLayout(answeringPanel, BoxLayout.Y_AXIS));
		bgPanel.setLayout(new BorderLayout());
		IDLabel.setFont(font);
		nameLabel.setFont(font);
		deptLabel.setFont(font);
		deptBox.setFont(font);
		IDField.setFont(font);
		nameField.setFont(font);
		startBtn.setFont(new Font("Microsoft yahei", Font.PLAIN, 16));
		nextBtn.setFont(new Font("Microsoft Yahei", Font.PLAIN, 16));
		startPanel.setOpaque(false);
		IDField.setMaximumSize(new Dimension(180, 30));
		nameField.setMaximumSize(new Dimension(180, 30));
		deptBox.setMaximumSize(new Dimension(140, 30));
		answeringPanel.setOpaque(false);
		conclusionLabel.setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
		detailArea.setFont(new Font("Microsoft Yahei", Font.PLAIN, 16));
		detailArea.setLineWrap(true);
		detailArea.setOpaque(false);
		detailArea.setEditable(false);
		sureBtn.setFont(new Font("Microsoft Yahei", Font.PLAIN, 16));
	}
	
	public void addComp(){
		//辅助面板
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		
		panel0.setOpaque(false);
		panel1.setOpaque(false);
		
		panel0.setLayout(new BoxLayout(panel0, BoxLayout.X_AXIS));
		
		panel0.add(IDLabel);
		panel0.add(Box.createRigidArea(new Dimension(20, 0)));
		panel0.add(IDField);
		panel0.add(Box.createRigidArea(new Dimension(20, 0)));
		panel0.add(nameLabel);
		panel0.add(Box.createRigidArea(new Dimension(20, 0)));
		panel0.add(nameField);
		panel0.add(Box.createRigidArea(new Dimension(20, 0)));
		panel0.add(deptLabel);
		panel0.add(Box.createRigidArea(new Dimension(20, 0)));
		panel0.add(deptBox);
		
		panel1.add(startBtn);
		
		startPanel.add(Box.createVerticalStrut((int)(bgPanel.getImageHeight() * 0.4)));
		startPanel.add(panel0);
		startPanel.add(Box.createVerticalStrut(40));
		startPanel.add(panel1);
		
		bgPanel.add(startPanel);
		frame.add(bgPanel);
		
		//为JDialog添加组件
		//辅助面板
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
				
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel3.setLayout(new BorderLayout());
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		
		panel2.add(conclusionLabel);
		panel2.add(Box.createHorizontalGlue());
		panel3.add(detailArea);
		
		panel5.add(sureBtn);
		
		panel4.add(panel2);
		panel4.add(Box.createVerticalStrut(30));
		panel4.add(panel3);
		panel4.add(panel5);
		
		dialog.add(panel4);
	}
	
	public void addListener(){
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(IDField.getText().length() > 0){
					try {
						Integer.parseInt(IDField.getText());
						
						if(nameField.getText().length() > 0){
							bgPanel.remove(startPanel);
							bgPanel.setFilePath("images/answeringbackground.jpg");
							bgPanel.repaint();
							bgPanel.add(answeringPanel);
							answeringPanel.removeAll();
							JPanel centerPanel = new JPanel();
							JPanel panel0 = new JPanel();
							JPanel panel1 = new JPanel();
							JPanel panel2 = new JPanel();
							JPanel panel3 = new JPanel();
							
							centerPanel.setOpaque(false);
							panel0.setOpaque(false);
							panel1.setOpaque(false);
							panel2.setOpaque(false);
							panel3.setOpaque(false);
							
							centerPanel.setLayout(new BorderLayout());
							panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
							panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
							
							JLabel question0 = new JLabel(questions[0]);
							JRadioButton answer0 = new JRadioButton(answers[0][0]);
							JRadioButton answer1 = new JRadioButton(answers[0][1]);
							JRadioButton answer2 = new JRadioButton(answers[0][2]);
							

							answer0.setOpaque(false);
							answer1.setOpaque(false);
							answer2.setOpaque(false);
							
							bg.add(answer0);
							bg.add(answer1);
							bg.add(answer2);
							
							question0.setFont(font);
							answer0.setFont(font);
							answer1.setFont(font);
							answer2.setFont(font);
							
							panel0.add(Box.createVerticalStrut((int)(bgPanel.getImageHeight() * 0.3)));
							panel0.add(question0);
							panel0.add(answer0);
							panel0.add(answer1);
							panel0.add(answer2);
							panel1.add(nextBtn);
							panel1.add(Box.createVerticalStrut((int)(bgPanel.getImageHeight() * 0.15)));
							
							panel2.add(panel0);
							panel3.add(panel1);
							centerPanel.add(panel2);
							centerPanel.add(panel3, BorderLayout.SOUTH);
							answeringPanel.add(centerPanel);
							bgPanel.validate();
							bgPanel.updateUI();
						}else{
							JOptionPane.showMessageDialog(frame, "姓名不能为空");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(frame, "输入学号有误");
					}
				}else{
					JOptionPane.showMessageDialog(frame, "学号不能为空");
				}
			}
		});
		
		
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hasSelected = false;
				Enumeration<AbstractButton> enumer = bg.getElements();
				while(enumer.hasMoreElements()){
					if(enumer.nextElement().isSelected()){
						hasSelected = true;
					}
				}
				if(hasSelected){
					if(questionNumber < 9){
						int index = -1;
						Enumeration<AbstractButton> en = bg.getElements();
						int length = answers[questionNumber].length;
						while(en.hasMoreElements()){
							AbstractButton ab = en.nextElement();
							if(ab.isSelected()){
								hasSelected = true;
								for(int i = 0;i<length;i++){
									if(answers[questionNumber][i].equals(ab.getText())){
										scores[questionNumber] = i;
									}
								}
							}
							
						}
						questionNumber++;
						
						if(questionNumber == 9){
							nextBtn.setText("完成");
						}
						
						answeringPanel.removeAll();
						//辅助面板
						JPanel centerPanel = new JPanel();
						JPanel panel0 = new JPanel();
						JPanel panel1 = new JPanel();
						JPanel panel2 = new JPanel();
						JPanel panel3 = new JPanel();
						
						centerPanel.setOpaque(false);
						panel0.setOpaque(false);
						panel1.setOpaque(false);
						panel2.setOpaque(false);
						panel3.setOpaque(false);
						
						centerPanel.setLayout(new BorderLayout());
						panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
						panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
						
						JLabel question0 = new JLabel(questions[questionNumber]);
						panel0.add(Box.createVerticalStrut((int)(bgPanel.getImageHeight() * 0.3)));
						panel0.add(question0);
						question0.setFont(font);
						length = answers[questionNumber].length;
						bg = new ButtonGroup();
						for(int i = 0;i<length;i++){
							JRadioButton answer = new JRadioButton(answers[questionNumber][i]);
							answer.setOpaque(false);
							answer.setFont(font);
							bg.add(answer);
							panel0.add(answer);
						}
						
						panel1.add(nextBtn);
						panel1.add(Box.createVerticalStrut((int)(bgPanel.getImageHeight() * 0.15)));
						
						panel2.add(panel0);
						panel3.add(panel1);
						centerPanel.add(panel2);
						centerPanel.add(panel3, BorderLayout.SOUTH);
						answeringPanel.add(centerPanel);
						answeringPanel.validate();
						answeringPanel.updateUI();
					}else{
						showResult();
						bgPanel.remove(answeringPanel);
						bgPanel.setFilePath("images/startbackground.jpg");
						bgPanel.repaint();
						bgPanel.add(startPanel);
						bgPanel.validate();
						bgPanel.updateUI();
						nextBtn.setText("下一题");
						questionNumber = 0;
						IDField.setText("");
						nameField.setText("");
					}
					
				}else{
					JOptionPane.showMessageDialog(frame, "请选择");
				}
			}
		});
		
		sureBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
	}
	
	public void showResult(){
		int sum = 0;
		int length = defaultScore.length;
		for(int i = 0;i<length;i++){
			sum += defaultScore[i][scores[i]];
		}
		
		if(sum < 21){
			conclusion = "内向的悲观者";
			detail = "人们认为你是一个害羞的、神经质的、优柔寡断的，是须人照顾、永远要别人为你做决定、不想与任何事或任何人有关。他们认为你是一个杞人忧天者，一个永远看到不存在的问题的人。有些人认为你令人乏味，只有那些深知你的人知道你不是这样的人。";
		}else if(sum >= 21 && sum <30){
			conclusion = "缺乏信心的挑剔者";
			detail = "你的朋友认为你勤勉刻苦、很挑剔。他们认为你是一个谨慎的、十分小心的人，一个缓慢而稳定辛勤工作的人。如果你做任何冲动的事或无准备的事，你会令他们大吃一惊。他们认为你会从各个角度仔细地检查一切之后仍经常决定不做。 他们认为对你的这种反应一部分是因为你的小心的天性所引起的。";
		}else if(sum >= 30 && sum <40){
			conclusion = "以牙还牙的自我保护者";
			detail = "别人认为你是一个明智、谨慎、注重实效的人。也认为你是一个伶俐、有天赋有才干且谦虚的人。你不会很快、很容易和人成为朋友，但是是一个对朋友非常忠诚的人，同时要求朋友对你也有忠诚的回报。那些真正有机会了解你的人会知道要动摇你对朋友的信任是很难的，但相等的，一旦这信任被破坏，会使你很难熬过。";
		}else if(sum >= 40 && sum <50){
			conclusion = "平衡的中道";
			detail = "别人认为你是一个新鲜的、有活力的、有魅力的、好玩的、讲究实际的、而永远有趣的人；一个经常是群众注意力的焦点，但是你是一个足够平衡的人，不至於因此而昏了头。他们也认为你亲切、和蔼、体贴、能谅解人；一个永远会使人高兴起来并会帮助别人的人。";
		}else if(sum >= 50 && sum <60){
			conclusion = "吸引人的冒险家";
			detail = "别人认为你是一个令人兴奋的、高度活泼的、相当易冲动的个性；你是一个天生的领袖、一个做决定会很快的人，虽然你的决定不总是对的。 他们认为你是大胆的和冒险的，会愿意试做任何事至少一次；是一个愿意尝试机会而欣赏冒险的人。因为你散发的刺激，他们喜欢跟你在一起。";
		}else{
			conclusion = "傲慢的孤独者";
			detail = "别人认为对你必须「小心处理」。在别人的眼中，你是自负的、自我中心的、是个极端有支配欲、统治欲的。别人可能钦佩你，希望能多像你一点，但不会永远相信你，会对与你更深入的来往有所踌躇及犹豫.世界本来就是层层嵌套，周而复始；不以任何的意志而改变。";
		}
		
		conclusionLabel.setText("  "+conclusion);
		detailArea.setText(detail);
		
		
		dialog.setSize(600, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setVisible(true);
		saveMessgae();
	}
	
	public void saveMessgae(){
		Properties p = new Properties();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("data/"+(String)deptBox.getSelectedItem()+IDField.getText()+".xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		p.setProperty("ID", IDField.getText());
		p.setProperty("Name", nameField.getText());
		p.setProperty("Department", (String)deptBox.getSelectedItem());
		p.setProperty("Conclusion", conclusion);
		p.setProperty("Detail", detail);
		
		try {
			p.storeToXML(fos, "Student_Info");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void show(){
		init();
		setAttr();
		addComp();
		addListener();
		frame.setSize(bgPanel.getImageWidth(), bgPanel.getImageHeight()+10);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(new ImageIcon("images/titleicon.png").getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}


class BackgroundPanel extends JPanel{
	private String filePath = "images/startbackground.jpg";
	
	private Image image = new ImageIcon(filePath).getImage();
	
	private int imageWidth = imageWidth = image.getWidth(this);
	
	private int imageHeight = imageHeight = image.getHeight(this);
	
	@Override
	public void paintComponent(Graphics g){
		image = new ImageIcon(filePath).getImage(); 
		g.drawImage(image, 0, 0, this);
	}
	
	public int getImageWidth(){
		return imageWidth;
	}
	
	public int getImageHeight(){
		return imageHeight;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
}
