import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;

public class Gearth {

    JTextArea zjk_jtr = new JTextArea();
    JScrollPane zjk_jsp = new JScrollPane(zjk_jtr);
    JPanel jp_south = new JPanel();
    JPanel jp_north = new JPanel();
    JPanel jp_card = new JPanel(new CardLayout());
    CardLayout cl = (CardLayout) jp_card.getLayout();

    // tsoft install
    String tsoft_src = "TSoft";
    String tsoft_dest = "D:\\TSoft";

    // run tsoft.exe and ANALYZE.EXE
    String tsoft_str =  "cmd /c D:\\TSoft\\tsoft.exe";
    String analyze_str =  "cmd /c Eterna34\\bin\\ANALYZE.EXE";

    // zjk.ini path and ZJK2012.PRN path
    File zjk_file = new File("Eterna34\\bin\\zjk.INI");
    File zjk2012_file = new File("Eterna34\\bin\\ZJK2012.PRN");

    public  void createAndShowGUI() {
        JFrame f = new JFrame("地震重力数据处理系统");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,400);
        f.setVisible(true);
        f.setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);

        // add jpanel
        JPanel jp_background = new JPanel();
        JPanel jp_button = new JPanel();

        // card layout
        JPanel jp_card_zjk = new JPanel();
        JPanel jp_card_ZJK2012_PRN = new JPanel();
        jp_card.add(jp_card_zjk, "zjk");
        jp_card.add(jp_card_ZJK2012_PRN, "zjk2012_PRN");

        JPanel jp_left = new JPanel(),
               jp_right = new JPanel();
        f.add(jp_background, BorderLayout.CENTER);
        f.add(jp_south, BorderLayout.SOUTH);
        f.add(jp_north, BorderLayout.NORTH);
        f.add(jp_left, BorderLayout.WEST);
        f.add(jp_right, BorderLayout.EAST);
        jp_south.setLayout(new BorderLayout());
        jp_south.add(new JPanel(), BorderLayout.NORTH);
        jp_south.add(jp_button, BorderLayout.CENTER);
        jp_south.add(new JPanel(), BorderLayout.SOUTH);
        jp_south.setVisible(false);

        // menubar
        JMenuBar menubar = new JMenuBar();
        f.add(menubar, BorderLayout.NORTH);

        // add jmenu
        JMenu menu1=new JMenu("Gearth(G)");
        menu1.setMnemonic(KeyEvent.VK_G);

        // jmenu
        JMenu menu2=new JMenu("数据采集(S)");
        menu2.setMnemonic(KeyEvent.VK_S);

        JMenu menu3=new JMenu("预处理(Y)");
        menu3.setMnemonic(KeyEvent.VK_Y);

        JMenu menu4=new JMenu("精密处理(J)");
        menu4.setMnemonic(KeyEvent.VK_J);

        JMenu menu5=new JMenu("结果显示(I)");
        menu5.setMnemonic(KeyEvent.VK_I);

        JMenu menu6=new JMenu("帮助(H)");
        menu6.setMnemonic(KeyEvent.VK_B);

        menubar.add(menu1);
        menubar.add(menu2);
        menubar.add(menu3);
        menubar.add(menu4);
        menubar.add(menu5);
        menubar.add(menu6);

        //menuitem
        JMenuItem item11_1=new JMenuItem("退出");
        menu1.add(item11_1);
        item11_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                ActionEvent.CTRL_MASK));

        item11_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem item12_1 = new JMenuItem("参数设置"),
                item12_2 = new JMenuItem("数据采集"),
                item12_3 = new JMenuItem("数据有效性");
        menu2.add(item12_1);menu2.add(item12_2);menu2.add(item12_3);

        JMenuItem item13_0 = new JMenuItem("安装Tsoft插件"),
                item13_1 = new JMenuItem("管理与分析"),
                item13_2 = new JMenuItem("Tsoft"),
                item13_3 = new JMenuItem("调和分析");
        menu3.add(item13_0);menu3.add(item13_1);menu3.add(item13_2);menu3.add(item13_3);

        JMenuItem item14_1 = new JMenuItem("获取非潮汐分量"),
                item14_2 = new JMenuItem("计算功率谱"),
                item14_3 = new JMenuItem("地震信号提取"),
                item14_4 = new JMenuItem("分析噪声相关性");
        menu4.add(item14_1);menu4.add(item14_2);menu4.add(item14_3);menu4.add(item14_4);

        JMenuItem item15_1 = new JMenuItem("结果显示"),
                item15_2 = new JMenuItem("结果应用");
        menu5.add(item15_1);menu5.add(item15_2);

        JMenuItem item16_1 = new JMenuItem("关于"),
                item16_2 = new JMenuItem("帮助");
        menu6.add(item16_1);menu6.add(item16_2);

        // zjk.ini jtextarea
        jp_background.setLayout(new BorderLayout(10, 10));
        jp_background.add(jp_card, BorderLayout.CENTER);
        jp_card_zjk.setLayout(new BorderLayout());
        jp_card_zjk.add(zjk_jsp, BorderLayout.CENTER);
        zjk_jsp.setVisible(false);

        // jbutton
        JButton jb_save = new JButton("Save"),
                jb_run = new JButton("Run");
        //jb_save.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        jp_button.setLayout(new GridLayout(1, 5));
        jp_button.add(new JPanel());
        jp_button.add(jb_save);
        jp_button.add(new JPanel());
        jp_button.add(jb_run);
        jp_button.add(new JPanel());
        //jp_background.add(jb_save, BorderLayout.SOUTH);

        // open result
        jp_card_ZJK2012_PRN.setLayout(new BorderLayout());
        JButton jb_zjk2012_prn_button = new JButton("打开ZJK2012.PRN");
        jp_card_ZJK2012_PRN.add(jb_zjk2012_prn_button, BorderLayout.NORTH);
        JTextArea zjk2012_jtr = new JTextArea();
        JScrollPane ZJK2012_PRN_jsp = new JScrollPane(zjk2012_jtr);
        jp_card_ZJK2012_PRN.add(ZJK2012_PRN_jsp, BorderLayout.CENTER);

        // function copy tsoft dir to d:
        item13_0.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                        gearth.copyFolder(tsoft_src, tsoft_dest);
                        JOptionPane.showMessageDialog(null, "TSoft安装完毕！");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // run soft.exe
        item13_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    Runtime.getRuntime().exec(tsoft_str);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // run Eterna34
        item13_3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    zjk_jsp.setVisible(true);
                    jp_south.setVisible(true);
                    cl.show(jp_card, "zjk");

                    BufferedReader in = new BufferedReader(new FileReader(zjk_file));
                    String line = in.readLine();
                    zjk_jtr.setText("");
                    while(line != null){
                        zjk_jtr.append(line + "\n");
                        line = in.readLine();
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // button save
        jb_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               try {
                    gearth.btn_save();
                    JOptionPane.showMessageDialog(null, "文件保存完毕！");
                }
                catch (Exception gg)
                {
                    gg.printStackTrace();
                }
            }
        });

        // button run
        jb_run.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    Runtime.getRuntime().exec(analyze_str);
                    jp_south.setVisible(false);
                    cl.show(jp_card, "zjk2012_PRN");
                }
                catch (Exception gg)
                {
                    gg.printStackTrace();
                }
            }
        });

        // open ZJK2012.PRN
        jb_zjk2012_prn_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    BufferedReader in = new BufferedReader(new FileReader(zjk2012_file));
                    String line = in.readLine();
                    while(line != null){
                        zjk_jtr.append(line + "\n");
                        line = in.readLine();
                    }
                }
                catch (Exception gg)
                {
                    gg.printStackTrace();
                }
            }
        });
    }

    // file save
    public void btn_save(){
       // File file = null;
        FileWriter out=null;
        try {
            out = new FileWriter(zjk_file);
            out.write(zjk_jtr.getText());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFolder(String oldPath, String newPath) { 
       try { 
           (new File(newPath)).mkdirs(); // create file folder
           File a=new File(oldPath); 
           String[] file=a.list(); 
           File temp=null; 
           for (int i = 0; i < file.length; i++) { 
               if(oldPath.endsWith(File.separator)){ 
                   temp=new File(oldPath+file[i]); 
               } 
               else{ 
                   temp=new File(oldPath+File.separator+file[i]); 
               } 
               if(temp.isFile()){ 
                   FileInputStream input = new FileInputStream(temp); 
                   FileOutputStream output = new FileOutputStream(newPath + "/" + 
                           (temp.getName()).toString()); 
                   byte[] b = new byte[1024 * 5]; 
                   int len; 
                   while ( (len = input.read(b)) != -1) { 
                       output.write(b, 0, len); 
                   } 
                   output.flush(); 
                   output.close(); 
                   input.close(); 
               } 
               if(temp.isDirectory()){ // sub folder
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
               } 
           } 
       } 
       catch (Exception e) { 
           e.printStackTrace(); 
       } 
   }

    static Gearth gearth;

    public static void main(String[] args){
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        gearth = new Gearth();
                        gearth.createAndShowGUI();
                    }
                }
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
