//Akira Panyawongkhanti 6381306
//Natthaphat Teanchai 6381322
//Pimmat Chatvichai 6480565

////////////////////////////////////////////////////////////////////////////////

package Project3_6381306;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;  
import javax.swing.event.*;

////////////////////////////////////////////////////////////////////////////////

public class MainFrame extends JFrame
{

    //components
    private JPanel              contentpane;
    private JLabel              drawpane, walkpane;
    private JComboBox           combo;                                          //for color of cat
    private JToggleButton       [] number;
    private JToggleButton       [] sett;
    private ButtonGroup         bgroup, sgroup;
    private JButton             walkButton, pantryButton, creditButton, giveButton;
    private JList               objList;
    private JTextField          nameText, happyText;
    private MyImageIcon         backgroundImg, parkImg;    
    private MySoundEffect       themeSound, meowSound, alarm;
    private JDialog             walkBox, pantryBox, creditBox;

    private catLabel            cat;
    private miniLabel           mini;
    private MainFrame           currentFrame;
    
    //variables
    private int frameWidth = 700, frameHeight = 700;
    private int happiness = 100, hap, fin;
    private String name;
    private String credit = "By: \nAkira Panyawongkhanti 6381306    \nNatthaphat Teanchai 6381322   \nPimmat Chatvichai 6480565\n*In park, use 1-5 to change color";
    private String  [] obj = {"Food +5", "Treats +10", "Toys +15", "Medicines +2", "Catnips +20"};
    
    public MainFrame()
    {
        setTitle("Ai_Taow_meow");
        setBounds(50, 50, frameWidth, frameHeight);
        setResizable(false);
	setVisible(true);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        
        addWindowListener( new WindowListener() {
            public void windowClosed( WindowEvent e )		{ }
            public void windowActivated( WindowEvent e )	{ }
            public void windowDeactivated( WindowEvent e )	{ }
            public void windowIconified( WindowEvent e )        { }
            public void windowDeiconified( WindowEvent e )      { }
            public void windowOpened ( WindowEvent e )          { 
                name = JOptionPane.showInputDialog("What's the cat name?");
                if(name == null) name = "Pants";
                nameText.setText(name);
            }
            public void windowClosing ( WindowEvent e ) {
                themeSound.stop();
                meowSound.stop();
                alarm.stop();
                JOptionPane.showMessageDialog( new JFrame(),"Come again next time!","Ai_Taow_meow", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );    
        
        AddComponents();
    }
    
    public void AddComponents(){
        
        String path = "src/main/java/Project3_6381306/resources/";
        
        //background
        backgroundImg  = new MyImageIcon(path + "game_background.png").resize(frameWidth, frameHeight);
	drawpane = new JLabel();
	drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        
        //sound
        themeSound = new MySoundEffect(path + "themesound.wav"); themeSound.playLoop();
        meowSound = new MySoundEffect(path + "meow.wav");
        alarm = new MySoundEffect(path + "alarm.wav");
        
        //cat
        cat = new catLabel(currentFrame);
        
        cat.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed( MouseEvent e )	{ 
                cat.setPet(true);
                cat.Petting();
                meowSound.playOnce();
            }
            @Override
            public void mouseReleased( MouseEvent e )	{ 
                cat.setPet(false);
                cat.Petting();
                meowSound.stop();
            }
            public void mouseClicked( MouseEvent e )    { }
            public void mouseEntered( MouseEvent e )	{ }	
            public void mouseExited( MouseEvent e )	{ }
        });

        drawpane.add(cat);
        
        //control
        JPanel control  = new JPanel();
        control.setBounds(0,0,700,50);
        
        //name
        control.add(new JLabel("Name: "));
        nameText = new JTextField("", 5);		
	nameText.setEditable(false);
        control.add(nameText);
        
        //happiness
        control.add(new JLabel("Happiness: "));
        happyText = new JTextField("100", 3);		
	happyText.setEditable(false);
        control.add(happyText);

        //combobox
        String[] color = {"Ginger", "Calico", "Black", "White", "Siamese"};
        combo = new JComboBox(color);
        combo.setSelectedIndex(0);
        combo.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ){
                switch(combo.getSelectedIndex()){
                    case 0: cat.Ginger(); break;
                    case 1: cat.Calico(); break;
                    case 2: cat.Black(); break;
                    case 3: cat.White(); break;
                    case 4: cat.Siamese(); break;
                }
            }
        });
        control.add(combo);
        
        //walk button
        walkButton = new JButton("Park");
        walkButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e){
                
                //new dialog
                walkBox = new JDialog(currentFrame, "Public park");
                walkBox.setSize(400,400);
                walkBox.setVisible(true);
                walkBox.addKeyListener(new KeyListener(){
                    public void keyTyped ( KeyEvent e )         { }
                    @Override
                    public void keyPressed ( KeyEvent e )       
                    { 
                        switch(e.getKeyCode()){
                            case KeyEvent.VK_LEFT: {mini.Left(); break;}
                            case KeyEvent.VK_A: {mini.Left(); break;}
                            case KeyEvent.VK_RIGHT: {mini.Right(); break;}
                            case KeyEvent.VK_D: {mini.Right(); break;}
                            case KeyEvent.VK_UP: {mini.Up(); break;}
                            case KeyEvent.VK_W: {mini.Up(); break;}
                            case KeyEvent.VK_DOWN: {mini.Down(); break;}
                            case KeyEvent.VK_S: {mini.Down(); break;}
                            case KeyEvent.VK_1: {mini.Gin(); break;}
                            case KeyEvent.VK_2: {mini.Cal(); break;}
                            case KeyEvent.VK_3: {mini.Bla(); break;}
                            case KeyEvent.VK_4: {mini.Whi(); break;}
                            case KeyEvent.VK_5: {mini.Sia(); break;}
                        }
                    }
                    public void keyReleased ( KeyEvent e)       { }
                });
                
                //background
                parkImg  = new MyImageIcon(path + "walkbg.png").resize(400, 400);
                walkpane = new JLabel();
                walkpane.setIcon(parkImg);
                walkpane.setLayout(null);
                
                //minicat
                mini = new miniLabel(walkBox);
                walkpane.add(mini);

                walkBox.add(walkpane, BorderLayout.CENTER);
                
            }
        });
        control.add(walkButton);
        
        //pantry button
        pantryButton = new JButton("Pantry");
        pantryButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ){
                pantryBox = new JDialog(currentFrame, "Pantry");
                pantryBox.setSize(500,150);
                pantryBox.setVisible(true);
                pantryBox.setResizable(false);
                
                //panel inside pantry
                JPanel pantry = new JPanel();
                pantry.setBounds(0, 0, 250, 200);
                
                //Obj List
                objList = new JList(obj);
                objList.setVisibleRowCount(5);
                objList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
                objList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged ( ListSelectionEvent e ){
                        if(objList.getSelectedValue() == "Food +5"){ hap = 5; }
                        if(objList.getSelectedValue() == "Treats +10") { hap = 10; }
                        if(objList.getSelectedValue() == "Toys +15") { hap = 15; }
                        if(objList.getSelectedValue() == "Medicines +2") { hap = 2; }
                        if(objList.getSelectedValue() == "Catnips +20") { hap = 20; }
                    }
                });
                pantry.add(objList);
                
                //Button Group
                number = new JToggleButton[5];
                bgroup = new ButtonGroup();
                number[0] = new JRadioButton("1");  number[0].setName("1");
                number[1] = new JRadioButton("2");  number[0].setName("2");
                number[2] = new JRadioButton("3");  number[0].setName("3");
                number[3] = new JRadioButton("4");  number[0].setName("4");
                number[4] = new JRadioButton("5");  number[0].setName("5");
                //number[0].setSelected(true);
                
                /*
                    There is a bug where if you select a new item from the list you need to select a new quantity
                    then select the desired quantity again to get the correct amount!
                */
                
                for (int i=0; i < 5; i++)
                {
                    bgroup.add( number[i] );
            
                    number[i].addItemListener( new ItemListener() 
                    {
                        @Override
                        public void itemStateChanged ( ItemEvent e ){
                            JRadioButton temp = (JRadioButton)e.getItem();
                            if(temp == number[0]) fin = hap*1;
                            if(temp == number[1]) fin = hap*2;
                            if(temp == number[2]) fin = hap*3;
                            if(temp == number[3]) fin = hap*4;
                            if(temp == number[4]) fin = hap*5;
                        }
                    });
                    
                    pantry.add(number[i]);
                }
                
                //Button give
                giveButton = new JButton("Give");
                giveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e ){
                        updateHappiness(fin);
                    }
                });
                pantry.add(giveButton);
                
                pantryBox.add(pantry, BorderLayout.NORTH);

                
            }
        });
        control.add(pantryButton);    
        
        //credit button
        creditButton = new JButton("Setting");
        creditButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ){
                creditBox = new JDialog(currentFrame, "Setting");
                creditBox.setSize(400,200);
                creditBox.setVisible(true);
                
                //panel inside Setting
                JPanel setting = new JPanel();
                setting.setBounds(0,0, 200, 200);
                
                JTextArea cred = new JTextArea(credit);
                cred.setEditable(false);
                cred.setFont(new Font("Comic Sans MS", Font.BOLD+Font.ITALIC, 18));
                
                //panel inside Setting
                JPanel sound = new JPanel();
                sound.setBounds(0,0, 200, 200);
                
                sett = new JToggleButton[2];
                sgroup = new ButtonGroup();
                sett[0] = new JRadioButton("Mute"); sett[0].setName("Mute");
                sett[1] = new JRadioButton("Unmute"); sett[1].setName("Unmute");
                
                for(int i=0; i<2; i++){
                    sgroup.add(sett[i]);
                    
                    sett[i].addItemListener( new ItemListener() {
                        @Override
                        public void itemStateChanged( ItemEvent e ){
                            JRadioButton temp = (JRadioButton)e.getItem();
                            if(temp == sett[0]) themeSound.stop();
                            if(temp == sett[1]) themeSound.playLoop();
                        }
                    });
                    
                    sound.add(sett[i]);
                }
                
                
                setting.add(cred);
                creditBox.add(sound, BorderLayout.NORTH);
                creditBox.add(cred, BorderLayout.SOUTH);
                
            }
        });
        control.add(creditButton);
        
        
        //add to frame
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
        
        setThread();
        
    }
    
    synchronized public void updateHappiness (int x)
    {
        happiness = happiness + x;
        happyText.setText(""+happiness);
    }
    
    public void timePass()
    {
        updateHappiness(-1);
        try { Thread.sleep(2500); } 
        catch (InterruptedException e) { e.printStackTrace(); }
        
        if(happiness <= 0) 
        {
            themeSound.stop();
            meowSound.stop();
            alarm.stop();
            JOptionPane.showMessageDialog( new JFrame(),"Come again next time!","Ai_Taow_meow", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        
        if(happiness == 20)
        {
            alarm.playLoop();
        }
        
        if(happiness > 20)
        {
            alarm.stop();
        }

    }
    
    public void setThread()
    {
        Thread time = new Thread()
        {
            public void run()
            {
                while(happiness > 0)
                {
                    timePass();
                }
            }
        };
        time.start();
    }
    
    public static void main(String[] args) {
        new MainFrame();

    }
    
}

////////////////////////////////////////////////////////////////////////////////

class catLabel extends JLabel
{
    private MyImageIcon         Idle, Pet;
    private MainFrame           parentFrame;
    
    private String [] imageFiles = {"src/main/java/Project3_6381306/resources/c1.png", 
                                    "src/main/java/Project3_6381306/resources/cc1.png",
                                    "src/main/java/Project3_6381306/resources/c2.png",
                                    "src/main/java/Project3_6381306/resources/cc2.png",
                                    "src/main/java/Project3_6381306/resources/c3.png",
                                    "src/main/java/Project3_6381306/resources/cc3.png",
                                    "src/main/java/Project3_6381306/resources/c4.png",
                                    "src/main/java/Project3_6381306/resources/cc4.png",
                                    "src/main/java/Project3_6381306/resources/c5.png",
                                    "src/main/java/Project3_6381306/resources/cc5.png"};
    
    private int width = 250, height = 250; 
    private int curX  = 250, curY   = 300;
    private boolean pet = false;
    
    public catLabel(MainFrame pf)
    {
        parentFrame = pf;
        
        Idle = new MyImageIcon(imageFiles[0]).resize(width, height);
        Pet = new MyImageIcon(imageFiles[1]).resize(width, height);
        setIcon(Idle);
        setBounds(curX, curY, width, height);
    }
    
    public void setPet(boolean t){
        pet = t;
    }

    public void Petting()
    {
        if(pet == false) 
        {
            setIcon(Idle);
            repaint();
        }
        else 
        {
            setIcon(Pet);
            repaint();
        }
    }
    
    public void Ginger()
    {
        Idle = new MyImageIcon(imageFiles[0]).resize(width, height);
        Pet = new MyImageIcon(imageFiles[1]).resize(width, height);
        setIcon(Idle);
        repaint();
    }
    
    public void Calico()
    {
        Idle = new MyImageIcon(imageFiles[2]).resize(width, height);
        Pet = new MyImageIcon(imageFiles[3]).resize(width, height);
        setIcon(Idle);
        repaint();
    }
    
    public void Black()
    {
        Idle = new MyImageIcon(imageFiles[4]).resize(width, height);
        Pet = new MyImageIcon(imageFiles[5]).resize(width, height);
        setIcon(Idle);
        repaint();
    }
    
    public void White()
    {
        Idle = new MyImageIcon(imageFiles[6]).resize(width, height);
        Pet = new MyImageIcon(imageFiles[7]).resize(width, height);
        setIcon(Idle);
        repaint();
    }
    
    public void Siamese()
    {
        Idle = new MyImageIcon(imageFiles[8]).resize(width, height);
        Pet = new MyImageIcon(imageFiles[9]).resize(width, height);
        setIcon(Idle);
        repaint();
    }

}

////////////////////////////////////////////////////////////////////////////////

class miniLabel extends JLabel
{
    private MyImageIcon     mini;
    private JDialog         maindialog;
    
    private int width = 100, height = 100;
    private int curX  = 150, curY   = 200;
    
    private String [] images = {"src/main/java/Project3_6381306/resources/mini1.png",
                                "src/main/java/Project3_6381306/resources/mini2.png",
                                "src/main/java/Project3_6381306/resources/mini3.png",
                                "src/main/java/Project3_6381306/resources/mini4.png",
                                "src/main/java/Project3_6381306/resources/mini5.png"};
    
    public miniLabel(JDialog jd)
    {
        maindialog = jd;
        
        mini = new MyImageIcon(images[0]).resize(width, height);
        setIcon(mini);
        setBounds(curX, curY, width, height);
        
    }
    
    public void Gin()
    {
        mini = new MyImageIcon(images[0]).resize(width, height);
        setIcon(mini);
        repaint();
    }
    
    public void Cal()
    {
        mini = new MyImageIcon(images[1]).resize(width, height);
        setIcon(mini);
        repaint();
    }
    
    public void Bla()
    {
        mini = new MyImageIcon(images[2]).resize(width, height);
        setIcon(mini);
        repaint();
    }
    
    public void Whi()
    {
         mini = new MyImageIcon(images[3]).resize(width, height);
        setIcon(mini);
        repaint();
    }
    
    public void Sia()
    {
        mini = new MyImageIcon(images[4]).resize(width, height);
        setIcon(mini);
        repaint();
    }
    
    public void Left()
    {
        if(curX <= 0)
        {
            curX = 400 - width;
            setBounds(curX, curY, width, height);
        }
        else
        {
            curX = curX - 20;
            setBounds(curX, curY, width, height);
        }
    }
    
    public void Right()
    {
        if(curX + width >= 400)
        {
            curX = 0;
            setBounds(curX, curY, width, height);
        }
        else
        {
            curX = curX + 20;
            setBounds(curX, curY, width, height);
        }
    }
    
    public void Up()
    {
        if(curY <= 0)
        {
            curY = 0;
            setBounds(curX, curY, width, height);
        }
        else
        {
            curY = curY - 20;
            setBounds(curX, curY, width, height);
        }
    }
    
    public void Down()
    {
        if(curY + height >= 400)
        {
            curY = 400 - height;
            setBounds(curX, curY, width, height);
        }
        else
        {
            curY = curY + 20;
            setBounds(curX, curY, width, height);
        }
    }
    
}

////////////////////////////////////////////////////////////////////////////////

// auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
};

////////////////////////////////////////////////////////////////////////////////

// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip clip;

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()   { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()   { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()       { clip.stop(); }
}

////////////////////////////////////////////////////////////////////////////////

/*
    List of handlers that we have:
        - ActionListener
        - ItemListener
        - MouseListener
        - KeyListener
        - WindowListener
        - ListSelectionListener

    What is already done:
        - WalkButton, PantryButton, CreditButton                                (JButton)
        - ComboBox                                                              (JComboBox)
        - TextField                                                             (JTextField)
        - WalkDialog, PantryDialog, CreditDialog                                (JDialog)
        - TextArea for credit                                                   (JTextArea)
        - List for things such as food, treats, medicine, toy, catnips          (JList)
        - RadioButton for picking the amount                                    (JRadioButton)
        - Happiness increase when give items
        - Game over, when happiness reaches 0 -> play arabian cat meow
        - Thread as time pass -> reduce happiness
        - Mute/Unmute button -> change credit to setting
        - Cat walk in parkpane

*/