package com.worm2fed.sp.kursach;

import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Assembler extends javax.swing.JFrame {
	private Structure 			struct;
	private boolean 			cards 	= true;
	private boolean 			tabsymb = true;
	private char 				a[];
	private GraphicsEnvironment gr 		= GraphicsEnvironment.getLocalGraphicsEnvironment();

	public Assembler() {
		initComponents();
		setTitle("Транслятор");
		ImageIcon image = new ImageIcon("wolf.jpg");
		setIconImage(image.getImage());
		this.setLocation((int) gr.getCenterPoint().getX() - 300, (int) gr.getCenterPoint().getY() - 300);
	}

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		OSFrame 		= new javax.swing.JFrame();
		Chooser 		= new javax.swing.JFileChooser();
		TranslateFrame 	= new javax.swing.JFrame();
		ChooserT 		= new javax.swing.JFileChooser();
		Panel1 			= new javax.swing.JPanel();
		OpenButton 		= new javax.swing.JButton();
		SaveButton 		= new javax.swing.JButton();
		TranslateButton = new javax.swing.JButton();
		Panel3 			= new javax.swing.JPanel();
		jScrollPane1 	= new javax.swing.JScrollPane();
		WorkArea 		= new javax.swing.JTextArea();

		Chooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ChooserActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout OSFrameLayout = new javax.swing.GroupLayout(OSFrame.getContentPane());
		OSFrame.getContentPane().setLayout(OSFrameLayout);
		OSFrameLayout.setHorizontalGroup(OSFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(OSFrameLayout.createSequentialGroup().addContainerGap()
						.addComponent(Chooser, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		OSFrameLayout.setVerticalGroup(OSFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(OSFrameLayout.createSequentialGroup().addContainerGap()
						.addComponent(Chooser, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		ChooserT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ChooserTActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout TranslateFrameLayout = new javax.swing.GroupLayout(TranslateFrame.getContentPane());
		TranslateFrame.getContentPane().setLayout(TranslateFrameLayout);
		TranslateFrameLayout
				.setHorizontalGroup(TranslateFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(TranslateFrameLayout.createSequentialGroup().addContainerGap()
								.addComponent(ChooserT, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		TranslateFrameLayout
				.setVerticalGroup(TranslateFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(TranslateFrameLayout.createSequentialGroup().addContainerGap()
								.addComponent(ChooserT, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(204, 204, 255));
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		Panel1.setBackground(new java.awt.Color(204, 204, 255));
		Panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Опции",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 0, 255))); // NOI18N

		OpenButton.setText("Открыть");
		OpenButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OpenButtonActionPerformed(evt);
			}
		});

		SaveButton.setText("Сохранить");
		SaveButton.setEnabled(false);
		SaveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SaveButtonActionPerformed(evt);
			}
		});

		TranslateButton.setText("Транслировать");
		TranslateButton.setEnabled(false);
		TranslateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TranslateButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
		Panel1.setLayout(Panel1Layout);
		Panel1Layout.setHorizontalGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(Panel1Layout.createSequentialGroup().addContainerGap().addComponent(OpenButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(SaveButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(TranslateButton).addContainerGap(488, Short.MAX_VALUE)));

		Panel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { OpenButton, SaveButton, TranslateButton });

		Panel1Layout.setVerticalGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(Panel1Layout.createSequentialGroup()
						.addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(OpenButton).addComponent(SaveButton).addComponent(TranslateButton))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		Panel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { OpenButton, SaveButton, TranslateButton });

		Panel3.setBackground(new java.awt.Color(201, 208, 208));
		Panel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Исходный текст программы:",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 255, 102))); // NOI18N
		Panel3.setMaximumSize(new java.awt.Dimension(300, 300));

		WorkArea.setColumns(20);
		WorkArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		WorkArea.setRows(5);
		WorkArea.setCaretColor(new java.awt.Color(0, 0, 153));
		WorkArea.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				WorkAreaKeyTyped(evt);
			}
		});
		jScrollPane1.setViewportView(WorkArea);

		javax.swing.GroupLayout Panel3Layout = new javax.swing.GroupLayout(Panel3);
		Panel3.setLayout(Panel3Layout);
		Panel3Layout
				.setHorizontalGroup(Panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								Panel3Layout.createSequentialGroup()
										.addContainerGap().addComponent(jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
										.addContainerGap()));
		Panel3Layout.setVerticalGroup(Panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(Panel3Layout.createSequentialGroup()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
						.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(Panel3, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(Panel1, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(Panel3, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// File Opening
	private void OpenButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_OpenButtonActionPerformed
		OSFrame.setBounds(300, 100, 600, 450);
		Chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		Chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith("asm") || f.isDirectory())
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return ".asm";
			}
		});
		OSFrame.setVisible(true);
	}// GEN-LAST:event_OpenButtonActionPerformed

	// File Saving
	private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SaveButtonActionPerformed
		OSFrame.setBounds(300, 100, 600, 450);
		Chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		Chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith("asm") || f.isDirectory())
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return ".asm";
			}
		});
		OSFrame.setVisible(true);
	}// GEN-LAST:event_SaveButtonActionPerformed

	// Debugger Call
	// Command Explanation
	// Translation button

	private void ChooserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ChooserActionPerformed
		String ev = evt.getActionCommand();
		if (ev.equals("CancelSelection"))
			OSFrame.dispose();

		if (ev.equals("ApproveSelection")) {
			if (Chooser.getDialogType() == JFileChooser.OPEN_DIALOG) {
				BufferedReader in = null;
				a = new char[65535];
				try {
					in = new BufferedReader(new FileReader(Chooser.getSelectedFile()));
					int len = in.read(a);
					WorkArea.setText(String.copyValueOf(a, 0, len));
					OSFrame.dispose();
				} catch (Exception e) {
				}
			} else if (Chooser.getDialogType() == JFileChooser.SAVE_DIALOG) {
				PrintWriter out = null;
				try {
					String text = WorkArea.getText();
					out = new PrintWriter(Chooser.getSelectedFile());
					out.println(text);
					OSFrame.dispose();
				} catch (Exception e) {
				}
				if (out != null) {
					out.close();
				}
			}
		}
		if (WorkArea.getText().equals("") == false) {
			SaveButton.setEnabled(true);
			TranslateButton.setEnabled(true);
		} else
			OSFrame.dispose();
	}// GEN-LAST:event_ChooserActionPerformed

	private void ChooserTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ChooserTActionPerformed
		struct = new Structure();
		struct.PrintCards = cards;
		struct.PrintSymb = tabsymb;
		
		if (evt.getActionCommand().equals("ApproveSelection")) {
			try {
				struct.file = ChooserT.getSelectedFile().toString();
				if (struct.Begin(WorkArea.getText())) {
					TranslateFrame.dispose();
					JOptionPane.showMessageDialog(TranslateFrame, "Трансляция успешно завершена!", "Внимание!", 1);
				} else {
					TranslateFrame.dispose();
					JOptionPane.showMessageDialog(TranslateFrame, "Трансляция завершилась с ошибкой!", "Внимание!", 1);
				}
			} catch (IOException ex) {
			}
		} else if (evt.getActionCommand().equals("CancelSelection"))
			TranslateFrame.dispose();
	}// GEN-LAST:event_ChooserTActionPerformed

	private void WorkAreaKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WorkAreaKeyTyped
		if (WorkArea.getText().equals("") == false)
			TranslateButton.setEnabled(true);
		else
			TranslateButton.setEnabled(false);
	}// GEN-LAST:event_WorkAreaKeyTyped

	private void TranslateButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_TranslateButtonActionPerformed
		TranslateFrame.setBounds(300, 100, 600, 450);
		ChooserT.setDialogTitle("Выберите каталог, в который будет транслирована информация");
		ChooserT.setDialogType(JFileChooser.SAVE_DIALOG);
		ChooserT.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "dir";
			}
		});
		TranslateFrame.setVisible(true);
	}// GEN-LAST:event_TranslateButtonActionPerformed

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				/*
				 * try {// com.sun.java.swing.plaf.windows.WindowsLookAndFeel
				 * UIManager.setLookAndFeel(
				 * "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); new
				 * Assembler().setVisible(true); } catch (ClassNotFoundException
				 * ex) {
				 * Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE,
				 * null, ex); } catch (InstantiationException ex) {
				 * Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE,
				 * null, ex); } catch (IllegalAccessException ex) {
				 * Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE,
				 * null, ex); } catch (UnsupportedLookAndFeelException ex) {
				 * Logger.getLogger(Assembler.class.getName()).log(Level.SEVERE,
				 * null, ex); }
				 */
				new Assembler().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JFileChooser Chooser;
	private javax.swing.JFileChooser ChooserT;
	private javax.swing.JFrame OSFrame;
	private javax.swing.JButton OpenButton;
	private javax.swing.JPanel Panel1;
	private javax.swing.JPanel Panel3;
	private javax.swing.JButton SaveButton;
	private javax.swing.JButton TranslateButton;
	private javax.swing.JFrame TranslateFrame;
	private javax.swing.JTextArea WorkArea;
	private javax.swing.JScrollPane jScrollPane1;
	// End of variables declaration//GEN-END:variables

}