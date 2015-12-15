import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Gearth {
    JFrame mainJF = new JFrame("地震重力数据处理系统");
    CardLayout cl = new CardLayout();

    JPanel mainJP = new JPanel();
    JPanel backgroundJP = new JPanel();
    JPanel eternaJP     = new JPanel();

    String tsoftSrc = "TSoft";
    String tsoftDest = "D:\\TSoft";
    String tsoftExec = "cmd /c D:\\TSoft\\tsoft.exe";

    String eternaExec = "cmd /c Eterna34\\bin\\ANALYZE.EXE";
    String eternaInput = "Eterna34\\bin\\zjk.DAT";
    String eternaConf = "Eterna34\\bin\\zjk.INI";
    String eternaOutput = "Eterna34\\bin\\ANALYZE.PRN";

    JLabel eternaStep1Label = new JLabel("Step 1: 输入数据");
    JLabel eternaStep2Label = new JLabel("Step 2: 参数配置");
    JLabel eternaStep3Label = new JLabel("Step 3: 调和分析");
    JLabel eternaStep4Label = new JLabel("Step 4: 结果显示");
    JLabel eternaStep1TimeShow = new JLabel();
    JLabel eternaStep2TimeShow = new JLabel();
    JLabel eternaStep3TimeShow = new JLabel();
    JLabel eternaStep4TimeShow = new JLabel();
    JButton eternaStep1Pick = new JButton("选择");
    JButton eternaStep2Pick = new JButton("选择");
    JButton eternaStep2Edit = new JButton("编辑");
    JButton eternaStep3Exec = new JButton("运行");
    JButton eternaStep4Disp = new JButton("显示");

    JFileChooser eternaInputJC = new JFileChooser();
    JFileChooser eternaConfigJC = new JFileChooser();

//------
/*
    //JTextArea zjk_jtr = new JTextArea();
    //JScrollPane zjk_jsp = new JScrollPane(zjk_jtr);
    JPanel jp_south = new JPanel();
    JPanel jp_north = new JPanel();
    JPanel jp_card = new JPanel(new CardLayout());
    CardLayout cl = (CardLayout) jp_card.getLayout();
*/

    public  void createAndShowGUI() {
        // init main frame
        mainJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJF.setSize(600, 400);
        mainJF.setVisible(true);
        mainJF.setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainJF.setLocation(dim.width/2 - mainJF.getSize().width/2, dim.height/2 - mainJF.getSize().height/2);
        mainJP.setLayout(cl);
        mainJF.add(mainJP);

        // set background panel
        backgroundJP.setLayout(new BorderLayout(mainJF.getSize().width, mainJF.getSize().height));
        backgroundJP.setBackground(Color.WHITE);
        mainJP.add(backgroundJP, "1");

        // set menubar and menu
        JMenuBar menubar = new JMenuBar();
        mainJF.add(menubar, BorderLayout.NORTH);
        // menu1
        JMenu menu1 = new JMenu("Gearth(G)");
        menubar.add(menu1);
        menu1.setMnemonic(KeyEvent.VK_G);
        JMenuItem item1_1 = new JMenuItem("退出");
        menu1.add(item1_1);
        item1_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        item1_1.addActionListener(new ExitListener());
        // menu2
        JMenu menu2=new JMenu("数据采集(S)");
        menubar.add(menu2);
        menu2.setMnemonic(KeyEvent.VK_S);
        JMenuItem item2_1 = new JMenuItem("参数设置");
        JMenuItem item2_2 = new JMenuItem("数据采集");
        JMenuItem item2_3 = new JMenuItem("数据有效性");
        menu2.add(item2_1);
        menu2.add(item2_2);
        menu2.add(item2_3);
        // menu3
        JMenu menu3=new JMenu("预处理(Y)");
        menubar.add(menu3);
        menu3.setMnemonic(KeyEvent.VK_Y);
        JMenuItem item3_1 = new JMenuItem("安装Tsoft插件");
        JMenuItem item3_2 = new JMenuItem("管理与分析");
        JMenuItem item3_3 = new JMenuItem("Tsoft");
        JMenuItem item3_4 = new JMenuItem("调和分析");
        menu3.add(item3_1);
        menu3.add(item3_2);
        menu3.add(item3_3);
        menu3.add(item3_4);
        item3_1.addActionListener(new InstallTsoftListener());
        item3_3.addActionListener(new RunTsoftListener());
        item3_4.addActionListener(new RunEnternaListener());
        // menu4
        JMenu menu4=new JMenu("精密处理(J)");
        menubar.add(menu4);
        menu4.setMnemonic(KeyEvent.VK_J);
        JMenuItem item4_1 = new JMenuItem("获取非潮汐分量");
        JMenuItem item4_2 = new JMenuItem("计算功率谱");
        JMenuItem item4_3 = new JMenuItem("地震信号提取");
        JMenuItem item4_4 = new JMenuItem("分析噪声相关性");
        menu4.add(item4_1);
        menu4.add(item4_2);
        menu4.add(item4_3);
        menu4.add(item4_4);
        // menu5
        JMenu menu5=new JMenu("结果显示(I)");
        menubar.add(menu5);
        menu5.setMnemonic(KeyEvent.VK_I);
        JMenuItem item5_1 = new JMenuItem("结果显示");
        JMenuItem item5_2 = new JMenuItem("结果应用");
        menu5.add(item5_1);
        menu5.add(item5_2);
        // menu6
        JMenu menu6=new JMenu("帮助(H)");
        menubar.add(menu6);
        menu6.setMnemonic(KeyEvent.VK_B);
        JMenuItem item6_1 = new JMenuItem("关于");
        JMenuItem item6_2 = new JMenuItem("帮助");
        menu6.add(item6_1);
        menu6.add(item6_2);

        eternaJP.setLayout(null);
        eternaStep1Label.setBounds(100, 0, 100, 20);
        eternaStep2Label.setBounds(100, 50, 100, 20);
        eternaStep3Label.setBounds(100, 100, 100, 20);
        eternaStep4Label.setBounds(100, 150, 100, 20);
        eternaStep1TimeShow.setBounds(200, 0, 200, 20);
        eternaStep2TimeShow.setBounds(200, 50, 200, 20);
        eternaStep3TimeShow.setBounds(200, 100, 200, 20);
        eternaStep4TimeShow.setBounds(200, 150, 200, 20);
        eternaStep1Pick.setBounds(140, 20, 80, 20);
        eternaStep2Pick.setBounds(140, 70, 80, 20);
        eternaStep2Edit.setBounds(230, 70, 80, 20);
        eternaStep3Exec.setBounds(140, 120, 80, 20);
        eternaStep4Disp.setBounds(140, 170, 80, 20);
        eternaStep1TimeShow.setForeground(Color.RED);
        eternaStep2TimeShow.setForeground(Color.RED);
        eternaStep3TimeShow.setForeground(Color.RED);
        eternaStep4TimeShow.setForeground(Color.RED);
        eternaStep1Pick.addActionListener(new PickInputListener());
        eternaStep2Pick.addActionListener(new PickArgsListener());
        eternaStep2Edit.addActionListener(new EditArgsListener());
        eternaStep3Exec.addActionListener(new ExecEternaListener());
        eternaStep4Disp.addActionListener(new DispOutputListener());
        eternaJP.add(eternaStep1Label);
        eternaJP.add(eternaStep2Label);
        eternaJP.add(eternaStep3Label);
        eternaJP.add(eternaStep4Label);
        eternaJP.add(eternaStep1TimeShow);
        eternaJP.add(eternaStep2TimeShow);
        eternaJP.add(eternaStep3TimeShow);
        eternaJP.add(eternaStep4TimeShow);
        eternaJP.add(eternaStep1Pick);
        eternaJP.add(eternaStep2Pick);
        eternaJP.add(eternaStep2Edit);
        eternaJP.add(eternaStep3Exec);
        eternaJP.add(eternaStep4Disp);
        mainJP.add(eternaJP, "2");

        cl.show(mainJP, "1");
//------    
/*

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


        // zjk.ini jtextarea
        jp_background.setLayout(new BorderLayout(10, 10));
        jp_background.add(jp_card, BorderLayout.CENTER);
        jp_card_zjk.setLayout(new BorderLayout());
        //jp_card_zjk.add(zjk_jsp, BorderLayout.CENTER);
        //zjk_jsp.setVisible(false);

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


        // run Eterna34
        item13_3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    /*
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
                    * /
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
                    /*
                    BufferedReader in = new BufferedReader(new FileReader(zjk2012_file));
                    String line = in.readLine();
                    while(line != null){
                        zjk_jtr.append(line + "\n");
                        line = in.readLine();
                    }
                    * /
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
        /*
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
        */
    }

    class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class InstallTsoftListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                gearth.copyFolder(tsoftSrc, tsoftDest);
                JOptionPane.showMessageDialog(null, "TSoft安装完毕！");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class RunTsoftListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Runtime.getRuntime().exec(tsoftExec);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class RunEnternaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateShow = df.format(new Date());
                eternaStep1TimeShow.setText(dateShow);
                eternaStep2TimeShow.setText(dateShow);
                eternaStep3TimeShow.setText(dateShow);
                eternaStep4TimeShow.setText(dateShow);
                cl.show(mainJP, "2");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class  PickInputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int state = eternaInputJC.showOpenDialog(backgroundJP);
                if (state == 1) {
                    return;
                }
                else {
                    File f = eternaInputJC.getSelectedFile();
                    copyFile(f, eternaInput);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateShow = df.format(new Date());
                    eternaStep1TimeShow.setText(dateShow);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class  PickArgsListener  implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int state = eternaConfigJC.showOpenDialog(backgroundJP);
                if (state == 1) {
                    return;
                }
                else {
                    File f = eternaConfigJC.getSelectedFile();
                    copyFile(f, eternaConf);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateShow = df.format(new Date());
                    eternaStep2TimeShow.setText(dateShow);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class  EditArgsListener  implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                JFrame editConfJF = new JFrame("调和分析参数配置");
                editConfJF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editConfJF.setSize(600, 400);
                editConfJF.setVisible(true);
                editConfJF.setLayout(new BorderLayout());
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                editConfJF.setLocation(dim.width/2 - editConfJF.getSize().width/2, dim.height/2 - editConfJF.getSize().height/2);
                JPanel editConfJP = new JPanel();
                editConfJP.setSize(editConfJF.getSize().width, editConfJF.getSize().height);
                editConfJP.setLayout(new BorderLayout());
                editConfJP.setBackground(Color.WHITE);
                editConfJF.add(editConfJP);
                JTextArea eternaConfJTA = new JTextArea();
                eternaConfJTA.setBounds(10, 0, 500, 350);
                editConfJP.add(eternaConfJTA);
                JButton eternaConfBtn = new JButton("保存");
                eternaConfBtn.setBounds(10, 360, 500, 30);
                editConfJP.add(eternaConfBtn);
                /*
                BufferedReader in = new BufferedReader(new FileReader(eternaConf));
                String line = in.readLine();
                eternaConfJTA.setText("");
                while(line != null){
                    eternaConfJTA.append(line + "\n");
                    line = in.readLine();
                }
                */
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateShow = df.format(new Date());
                eternaStep2TimeShow.setText(dateShow);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class  ExecEternaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateShow = df.format(new Date());
                eternaStep3TimeShow.setText(dateShow);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class  DispOutputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateShow = df.format(new Date());
                eternaStep4TimeShow.setText(dateShow);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void copyFolder(String oldPath, String newPath) { 
        try { 
            (new File(newPath)).mkdirs(); // create file folder
            File a=new File(oldPath); 
            String[] file=a.list(); 
            File temp=null; 
            for (int i = 0; i < file.length; i++) { 
                if(oldPath.endsWith(File.separator)) { 
                    temp=new File(oldPath+file[i]); 
                } 
                else { 
                    temp=new File(oldPath+File.separator+file[i]); 
                } 
                if(temp.isFile()) {
                    copyFile(temp, newPath + "/" + (temp.getName()).toString());
                }
                if(temp.isDirectory()) { // sub folder
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]); 
                } 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }

    public void copyFile(File oldFile, String newFilePath) {
        try {
            FileInputStream input = new FileInputStream(oldFile);
            FileOutputStream output = new FileOutputStream(newFilePath);
            byte[] b = new byte[1024 * 5];
            int len;
            while ( (len = input.read(b)) != -1 ) {
                output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
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
