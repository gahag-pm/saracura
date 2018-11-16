package br.ufmg.dcc.pm.saracura.ui.views;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.ufmg.dcc.pm.saracura.clinic.Specialty;


public class SpecialtyPickWindow extends JFrame {
	protected final Dimension dButton = new Dimension(250, 75);

	  protected final JButton select = new JButton("Selecionar") {{
	    setSize(dButton);
	    setMaximumSize(dButton);
	    setAlignmentX(Component.CENTER_ALIGNMENT);
	  }};

	  protected JList <String> list;
	  protected JScrollPane listScroller;
	  
	  protected void setList() {
		  this.list = new JList<String>(Specialty.sorted.values().toArray(new String[0]));
		  list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  list.setSelectedIndex(0);
		  list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		  list.setVisibleRowCount(-1);
		  		  
	  }
	  
	  protected void setScroller() {
		  this.listScroller = new JScrollPane(this.list);
		  this.listScroller.setPreferredSize(new Dimension(250, 75));
	  }

	  public SpecialtyPickWindow(){
	    super("Especialidades médicas");
	    setList();
	    setScroller();
	    JPanel pane = new JPanel();
	    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
	    pane.setBorder(new EmptyBorder(20, 50, 20, 50));
	    pane.add(listScroller);
	    pane.add(Box.createRigidArea(new Dimension(10,10)));
	    pane.add(select);
	    this.add(pane);

	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	  }

}
