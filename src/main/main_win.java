package main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


public class main_win extends JFrame{
	private static final long serialVersionUID = 1L;
	public static main_win win;
	public static JPanel all_panel = new JPanel(new BorderLayout());
		public static JPanel file_panel = new JPanel(new BorderLayout());
			public static JSplitPane file_spane;
				public static JPanel dir_panel = new JPanel();
					public static JPanel dir_button_panel = new JPanel();
						public static JButton dir_last_button = new JButton("←");
						public static JButton dir_home_button = new JButton("home");
						public static JButton dir_sysdir_button = new JButton(explorer.folder.getPath());
							public static JPopupMenu dir_sysdir_menu = new JPopupMenu();
					public static JScrollPane dir_dir_scroll = new JScrollPane();
						public static JPanel dir_dir_panel = new JPanel();
				public static JScrollPane mfile_scroll = new JScrollPane();
					public static JPanel mfile_panel = new JPanel();

	public main_win(){
		dir_dir_scroll.setViewportView(dir_dir_panel);
		dir_dir_scroll.getVerticalScrollBar().setUnitIncrement(16);
		mfile_scroll.setViewportView(mfile_panel);
		mfile_scroll.getVerticalScrollBar().setUnitIncrement(16);
		file_spane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dir_panel, mfile_scroll);
		file_panel.add(file_spane, BorderLayout.CENTER);
		all_panel.add(file_panel, BorderLayout.CENTER);
		this.add(all_panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setLocationByPlatform(true);
		this.setVisible(true);
		
		dir_last_button.addActionListener(e->{
			explorer.move_last();
			refresh_dir_panel();
			refresh_mfile_panel();
		});
		dir_last_button.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				dir_last_button.setBackground(Color.YELLOW);
			}
		    public void mouseExited(MouseEvent e) {
		    	dir_last_button.setBackground(Color.WHITE);
		    }
		});
		dir_home_button.addActionListener(e->{
			explorer.move_home();
			refresh_dir_panel();
			refresh_mfile_panel();
		});
		dir_home_button.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				dir_home_button.setBackground(Color.YELLOW);
			}
		    public void mouseExited(MouseEvent e) {
		    	dir_home_button.setBackground(Color.WHITE);
		    }
		});
		for(File i:explorer.sysdirs){
			dir_sysdir_menu.add(new JMenuItem(new AbstractAction(i.getPath()){
				private static final long serialVersionUID = -7814716850064573159L;
				@Override
				public void actionPerformed(ActionEvent e) {
					dir_sysdir_button.setText(i.getPath());
					explorer.move_next(i);
					refresh_dir_panel();
					refresh_mfile_panel();
				}
			}));
		}
		dir_sysdir_button.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
            	dir_sysdir_menu.show(e.getComponent(), e.getX(), e.getY());
            }
			public void mouseEntered(MouseEvent e){
				dir_sysdir_button.setBackground(Color.YELLOW);
			}
		    public void mouseExited(MouseEvent e) {
		    	dir_sysdir_button.setBackground(Color.WHITE);
		    }
		});
		dir_button_panel.setLayout(new BoxLayout(dir_button_panel,BoxLayout.LINE_AXIS));
		dir_last_button.setBorder(new LineBorder(Color.black));
		dir_last_button.setOpaque(true);
		dir_last_button.setBackground(Color.WHITE);
		dir_home_button.setBorder(new LineBorder(Color.black));
		dir_home_button.setOpaque(true);
		dir_home_button.setBackground(Color.WHITE);
		dir_sysdir_button.setBorder(new LineBorder(Color.black));
		dir_sysdir_button.setOpaque(true);
		dir_sysdir_button.setBackground(Color.WHITE);
		dir_button_panel.add(dir_last_button);
		dir_button_panel.add(dir_home_button);
		dir_button_panel.add(dir_sysdir_button);
		refresh_dir_panel();
		refresh_mfile_panel();
		dir_panel.setLayout(new BorderLayout());
		dir_panel.add(dir_button_panel,BorderLayout.NORTH);
		dir_panel.add(dir_dir_scroll,BorderLayout.CENTER);
		
		this.revalidate();
		file_spane.setDividerLocation(0.25);
	}
	private void refresh_dir_panel(){
		dir_dir_panel.removeAll();
		dir_dir_panel.setLayout(new BoxLayout(dir_dir_panel,BoxLayout.PAGE_AXIS));
		dir_dir_panel.setBackground(Color.WHITE);
		JButton[] dirs = new JButton[explorer.dirs.length];
		for( int i = 0 ; i < explorer.dirs.length ; i ++){
			int index = i;
			dirs[i] = new JButton(explorer.dirs[i].getName());
			dirs[i].setOpaque(true);
			dirs[i].setBackground(Color.WHITE);
			dirs[i].addActionListener(e->{
				System.out.println(explorer.dirs[index].getName());
				explorer.move_next(explorer.dirs[index]);
				refresh_dir_panel();
				refresh_mfile_panel();
			});
			dirs[i].addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					dirs[index].setBackground(Color.LIGHT_GRAY);
				}
			    public void mouseExited(MouseEvent e) {
			    	dirs[index].setBackground(Color.WHITE);
			    }
			});
			dirs[i].setBorder(null);
			dir_dir_panel.add(dirs[i]);
		}
		this.revalidate();
		file_spane.setDividerLocation(0.25);
	}
	private void refresh_mfile_panel(){
		mfile_panel.removeAll();
		mfile_panel.setLayout(new BoxLayout(mfile_panel,BoxLayout.PAGE_AXIS));
		mfile_panel.setBackground(Color.WHITE);
		JButton[] items = new JButton[explorer.items.length];
		for( int i = 0 ; i < explorer.items.length ; i++){
			int index = i;
			items[i] = new JButton(explorer.items[i].getName());
			items[i].setOpaque(true);
			items[i].setBackground(Color.WHITE);
			items[i].addActionListener(e->{
				System.out.println(explorer.items[index].getPath());
				music.open(explorer.items[index]);
			});
			items[i].addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					items[index].setBackground(Color.LIGHT_GRAY);
				}
			    public void mouseExited(MouseEvent e) {
			    	items[index].setBackground(Color.WHITE);
			    }
			});
			items[i].setBorder(null);
			mfile_panel.add(items[i]);
		}
		this.revalidate();
		file_spane.setDividerLocation(0.25);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		win = new main_win();
	}
	public static class explorer{
		private static File folder = new File(System.getProperty("user.home"));
		private static File[] set_dirs(){
			return folder.listFiles(new FileFilter(){
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory() && pathname.getName().charAt(0) != '.';
				}
			});
			
		}
		private static File[] set_items(){
			return folder.listFiles(new FileFilter(){
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile() && pathname.getName().charAt(0) != '.' && pathname.getName().contains(".mp3");
				}
			});
		}
		public static File[] set_sys_dirs(){
			if(System.getProperty("os.name").equals("Mac OS X")){
				System.out.println("This is "+System.getProperty("os.name"));
				File vol = new File("/Volumes");
				File[] voldir = vol.listFiles();
				File[] sysdirs = new File[voldir.length+1];
				sysdirs[0] = new File(System.getProperty("user.home"));
				for(int i = 1 ; i < sysdirs.length ; i++){
					sysdirs[i] = voldir[i-1];
				}
				return sysdirs;
			}else if(System.getProperty("os.name").equals("Windows 10" )){
				System.out.println("This is "+System.getProperty("os.name"));
				int length = 0;
				for(int i = 0 ; i < 26 ; i++){
					if((new File((char)('A'+i)+":/")).exists()){
						length++;
					}
				}
				File[] sysdirs = new File[length];
				for(int i = 0,j=0 ; i < 26 ; i++){
					if((new File((char)('A'+i)+":/")).exists()){
						sysdirs[j] = (new File((char)('A'+i)+":/"));
						j++;
					}
				}
				return sysdirs;
			}else{
				System.out.println("Unsuport System "+System.getProperty("os.name"));
				return new File[0];
			}
		}
		private static File[] dirs = set_dirs();

		private static File[] items = set_items();

		private static File[] sysdirs = set_sys_dirs();

		//private static File[] sys_dirs = set_sys_dirs();
		public static void move_next(File next_dir){
			if( next_dir.isDirectory() ){
				folder = next_dir;
				dirs = set_dirs();
				items = set_items();
			}
		}
		public static void move_last(){
			if(folder.getParentFile() != null){
				folder = folder.getParentFile();
				dirs = set_dirs();
				items = set_items();
			}
		}
		public static void move_home(){
			folder = new File(System.getProperty("user.home"));
			dirs = set_dirs();
			items = set_items();
		}

	}
	public static class music{
		static private BasicPlayer player = new BasicPlayer();
		static public void open( File music){
			try {
				player.open(music);
				player.play();
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
