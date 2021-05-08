package kursach_vp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.io.*;

class createGUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JTextField nt, dt, tt, Dt, Res11t, Res21t, Res12t, Res22t, time1t, time2t;
	JLabel nl, dl, tl, Dl, Res11l, Res21l, Res12l, Res22l;
	JButton start, exit;
	
	public createGUI() { 
	    JFrame frame = new JFrame("Курсовая работа");
	    frame.setSize(1000, 250);
	    setResizable(false);
	    
	    Res11t = new JTextField(10);
	    Res21t = new JTextField(10);
	    Res12t = new JTextField(10);
	    Res22t = new JTextField(10);
	    
	    time1t = new JTextField(10);
	    time2t = new JTextField(10);
	    
	    nl = new JLabel("    Количество требований");
	    dl = new JLabel("    Время поступления");
	    tl = new JLabel("    Время выполнения");
	    Dl = new JLabel("    Директивный срок");
	    
	    Res11l = new JLabel("    Штраф");
	    Res21l = new JLabel("    Последовательность");
	    Res12l = new JLabel("    Штраф");
	    Res22l = new JLabel("    Последовательность");
	    
	    start = new JButton("Вычислить");
	    exit = new JButton("Выход");
	    
	    start.addActionListener(this);
	    exit.addActionListener(new ExitHandler());
	    
	    JLabel alg1 = new JLabel("    Алгоритм полного перебора:");
	    JLabel alg2 = new JLabel("    Генетический алгоритм перебора:");
	    JLabel time1l = new JLabel("    Время");
	    JLabel time2l = new JLabel("    Время");
	    new JLabel("");
	    new JLabel("");
	    JLabel N7 = new JLabel("");
	    
	    frame.getContentPane();
	    JPanel bord = new JPanel(new GridLayout(6, 3));
	   
        nt = new JTextField("");
        dt = new JTextField("");
        tt = new JTextField("");
        Dt = new JTextField("");
	    
	    frame.setLayout(new BorderLayout());
	    JPanel np = new JPanel(new GridLayout(1, 1, 10, 0)); //1 2
	    np.add(nl);
	    np.add(nt);
	    
	    JPanel dp = new JPanel(new GridLayout(1, 1, 10, 0)); //1 2
	    dp.add(dl);
	    dp.add(dt);
	    
	    JPanel tp = new JPanel(new GridLayout(1, 1, 10, 10)); //1 2
	    tp.add(tl);
	    tp.add(tt);
	    
	    JPanel Dp = new JPanel(new GridLayout(1, 1, 10, 10)); //1 2
	    Dp.add(Dl);
	    Dp.add(Dt);
	    
	    JPanel Restp1 = new JPanel(new GridLayout(1, 2, 10, 0)); //1 алгоритм штраф 1
	    Restp1.add(Res11l);
	    Restp1.add(Res11t);
	    
	    JPanel Restp11 = new JPanel(new GridLayout(1, 2, 10, 0)); //1 алгоритм послед 1
	    Restp11.add(Res21l);
	    Restp11.add(Res21t);
	    
	    JPanel Restp2 = new JPanel(new GridLayout(1, 2, 10, 0)); //2 алгоритм штраф 2
	    Restp2.add(Res12l);
	    Restp2.add(Res12t);
	    
	    JPanel Restp22 = new JPanel(new GridLayout(1, 2, 10, 0)); //послед 2
	    Restp22.add(Res22l);
	    Restp22.add(Res22t);
	    
	    JPanel Time1 = new JPanel(new GridLayout(1, 2, 10, 0));
	    Time1.add(time1l);
	    Time1.add(time1t);
	    
	    JPanel Time2 = new JPanel(new GridLayout(1, 2, 10, 0));
	    Time2.add(time2l);
	    Time2.add(time2t);
	    
	    bord.add(np);
	    bord.add(alg1);
	    bord.add(alg2);
	    bord.add(dp);
	    bord.add(Restp1);
		bord.add(Restp2);
		bord.add(tp);
		bord.add(Restp11);
		bord.add(Restp22);
		bord.add(Dp);
		bord.add(Time1);
		bord.add(Time2);
		bord.add(start);
		bord.add(N7);
		bord.add(exit);
		
		frame.add(bord, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}

	class ExitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	System.exit(0);
        }
    }
	
	public void actionPerformed(ActionEvent e) { 
		try {
			int n = Integer.parseInt(nt.getText());
			String str = dt.getText();
			String words[] = str.split("\\s+");
			int dk[] = new int [words.length];
          
			for(int i = 0; i < words.length; i++)
				dk[i] = Integer.parseInt(words[i]);

			str = tt.getText();
			words = str.split("\\s+");
			int tk[] = new int[words.length];
			for(int i = 0; i < words.length; i++)
				tk[i] = Integer.parseInt(words[i]);
 
			str = Dt.getText();
			words = str.split("\\s+");
			int Dk[] = new int[words.length];
          
			for(int i = 0; i < words.length; i++)
				Dk[i] = Integer.parseInt(words[i]);
          
			long startTime = System.currentTimeMillis();  
          
			Perebor perebor = new Perebor(n, dk, tk, Dk);
			perebor.perform();
          
			Res11t.setText(Pack.s1);
			Res21t.setText(Pack.s2);
          
			long startTime1 = System.currentTimeMillis(); 
	          
	        Genetic ga = new Genetic(n, dk, tk, Dk);
	        ga.perform2();
          
	        Res12t.setText(Pack.s3);
	        Res22t.setText(Pack.s4);
          
	        long timeEnd = (System.currentTimeMillis() - startTime);
	        long timeEnd1 = (System.currentTimeMillis() - startTime1);
          
	        String str1 = Long.toString(timeEnd);
	        String str2 = Long.toString(timeEnd1);
	        String str3 = " ms";
          
	        time1t.setText(str1 + str3);
	        time2t.setText(str2 + str3);
		}
		catch (NumberFormatException ex) {
			System.out.println("Данные введены некорректно!");
			System.exit(0);
		}
		catch (ArrayIndexOutOfBoundsException el) {
			System.out.println("Данные введены некорректно!");
		}
	}
	
	public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.invokeLater(new Runnable(){
            	public void run() {
            		new createGUI();
            	}
            }); 
        }
        
        catch (UnsupportedLookAndFeelException e) {}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}   
    }   
}
