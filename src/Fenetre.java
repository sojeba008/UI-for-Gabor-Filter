import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ij.ImagePlus;

public  class Fenetre extends JFrame implements ActionListener{
	static ImagePlus getImage; //ImagePlus
	private JPanel tpage=new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("File");
	private JMenu about = new JMenu("About");
	private JMenuItem item1 = new JMenuItem("Open Image");
	private JMenuItem item4 = new JMenuItem("Filter");
	private JMenuItem item2 = new JMenuItem("Exit");
	private JMenuItem item3 = new JMenuItem("About");
	private JMenuItem item5 = new JMenuItem("Save");
    private BufferedImage Imaged;
    private BufferedImage Imagef;
    private JLabel image2=null;
    private JLabel image1=null;
    private String link;
    private BufferedImage images;
	public Fenetre(){
	fichier.add(item1);
	fichier.addSeparator();
	fichier.add(item4);
	fichier.addSeparator();
	fichier.add(item5);
	fichier.addSeparator();
	fichier.add(item2);
	about.add(item3);
	this.menuBar.add(fichier);
	this.menuBar.add(about);
	setJMenuBar(menuBar);
	item1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc=new JFileChooser();
			
			int returnVal=fc.showOpenDialog(null);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				
				try {
					File image=fc.getSelectedFile();
					Imaged = ImageIO.read(image);
									} catch (IOException e) {
					e.printStackTrace();
				}
			image1.setIcon(new ImageIcon(Imaged));
            

			}
		}
		});
	item2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			int option=JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter?", "Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option != JOptionPane.NO_OPTION){
				System.exit(0);
			}
			if(option != JOptionPane.YES_OPTION){
				
			}
		}
		});
	
	item5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		
			try {
				JFileChooser save=new JFileChooser();
				int back=save.showSaveDialog(save);
				//GaborFilter.GaborFilter2(link).show();
				if(back==JFileChooser.APPROVE_OPTION){
					try{
						File Filetosave=save.getSelectedFile();
					ImageIO.write(images, "JPG", new File( Filetosave.getPath()+".jpg"));
					}
					catch(Exception e){
			
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		});
	
	
	item4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			  JOptionPane jop1 = null;
				//	image1.setIcon(new ImageIcon(Imaged));
				jop1.showMessageDialog(null, "Veuillez patienter pendant quelque secondes s'il vous plait...\n Pour continuer veuillez cliquer sur OK", "Information",JOptionPane.INFORMATION_MESSAGE);
				//GaborFilter gbf=new GaborFilter(link);
				Imagef = new GaborFilter(link).getpic().getBufferedImage();
							
		image2.setIcon(new ImageIcon(Imagef));
		}
		});
	
	
	item3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
				
			
		JOptionPane.showMessageDialog(null, "Interface Homme Machine réalisée dans le cadre du cours de Traitement Numérique de l'Image \n                                Realisé par SOSSOU Jean-Baptiste\n                                Sous la direction de Dr DJARA Tahirou","About",JOptionPane.INFORMATION_MESSAGE);
		}
		});
	
	JPanel Page=new JPanel();
	JPanel pb=new JPanel();
	JPanel pc=new JPanel();
	JLabel yo=new JLabel(" Image source") ;
	JLabel ya=new JLabel("                                                 Image Filtrée   ") ;
    JButton bt1=new JButton("Open Image");
    JButton bt4=new JButton("Filter");
    JButton bt2=new JButton("Save");
    JButton bt3=new JButton("Exit");
	setTitle("Interface de filtrage de Gabor");
	setSize(1024, 720);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Page.setLayout(new GridLayout (1,2));
	 pb.add(bt1);
	 pb.add(bt4);
     pb.add(bt2);
     pb.add(bt3);
	 bt1.addActionListener(new bt1listener());
	 bt2.addActionListener(new bt2listener());
	 bt3.addActionListener(new bt3listener());
	 bt4.addActionListener(new bt4listener());
	 
	 JFileChooser fc=new JFileChooser();
		
	 
	 pc.add(yo,BorderLayout.EAST);
	 pc.add(ya,BorderLayout.WEST);
	 tpage.setBackground(Color.white);
	 tpage.setLayout(new BorderLayout());
	 //Page.add(image1,BorderLayout.CENTER);
	 //Page.add(image2,BorderLayout.CENTER);   
	 tpage.add(Page, BorderLayout.CENTER);
	 tpage.add(pc, BorderLayout.NORTH);
	 tpage.add(pb, BorderLayout.SOUTH);
	 this.setContentPane(tpage);
	 

	 
 // GaborFilter ToFilterImage = new GaborFilter();
 // Imagef = ToFilterImage.GaborFilter2(path).getBufferedImage();
 // this.image2.setIcon(new ImageIcon(Imagef));

    
    
    
    image1=new JLabel();
    
    image2=new JLabel();
    JScrollPane scroll1=new JScrollPane(image1);
    scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    
    JScrollPane scroll2=new JScrollPane(image2);
    scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    
    
	Page.add(scroll1,BorderLayout.CENTER);
	Page.add(scroll2,BorderLayout.CENTER);

	setVisible(true);
	go();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	private void go() {
		// TODO Auto-generated method stub
		
	}
	
     class bt1listener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {

JFileChooser fc=new JFileChooser();
			
			int returnVal=fc.showOpenDialog(null);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				
				try {
					File image=fc.getSelectedFile();
					link=fc.getSelectedFile().toString();
					Imaged = ImageIO.read(image);
									} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			image1.setIcon(new ImageIcon(Imaged));
		//	image1.setIcon(new ImageIcon(Imaged));

			}
			
		}
	}
     
class bt2listener implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		try {
			JFileChooser save=new JFileChooser();
			int back=save.showSaveDialog(save);
			//GaborFilter.GaborFilter2(link).show();
			if(back==JFileChooser.APPROVE_OPTION){
				try{
					File Filetosave=save.getSelectedFile();
				//	ToFilterImage.getPicture().getBufferedImage();
			ImageIO.write( images, "JPG", new File( Filetosave.getPath()+".jpg"));
				}
				catch(Exception e){
		
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//GaborFilter.GaborFiltersave(link);
	}	 
  }
class bt4listener implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		  JOptionPane jop1 = null;
			//	image1.setIcon(new ImageIcon(Imaged));
			jop1.showMessageDialog(null, "Veuillez patienter pendant quelque secondes s'il vous plait...\n Pour continuer veuillez cliquer sur OK", "Information",JOptionPane.INFORMATION_MESSAGE);
			//GaborFilter gbf=new GaborFilter(link);
			images=Imagef = new GaborFilter(link).getpic().getBufferedImage();
						
	image2.setIcon(new ImageIcon(Imagef));
    
		
	//	 Fenetre f = new Fenetre(chemin_d,GaborFilter.GaborFilter(chemin_d));
	       	 //setVisible(false);
	}	 
  }

class bt3listener implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		int option=JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter?", "Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(option != JOptionPane.NO_OPTION){
			System.exit(0);
			
		}
		if(option != JOptionPane.YES_OPTION){
		}
	}	 
  }

public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}	
}
