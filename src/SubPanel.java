

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class SubPanel extends JPanel {

    //static String btnFinalCheck_code = "Final Check";

    static JLabel labelCheckNum = new JLabel("<配達確認番号>");
    TextGuide textCheckNum = new TextGuide("配達確認番号を入力");

    static JButton btnCheckNum;
    static JButton btnBack;

    //フレームを作成
    MainFrame mainFrame;
    String string;


    public SubPanel(MainFrame mf, String str) {
        mainFrame = mf;
        string = str;

        //パネルの名前をセット
        this.setName("subPanel");

        //レイアウトを追加
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(150, 3, 3, 3);

        //ラベル・テキストフィールドを追加
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        add(labelCheckNum, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        textCheckNum.setColumns(15);
        add(textCheckNum, gridBagConstraints);


        //戻るボタンを追加
        btnBack = new JButton("戻る");
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new Insets(200, 3, 3, 50);
        //ボタンが押されたときの処理
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.reloadPanel(mainFrame.PanelNames[0]);
                panelChangeToMainPanel(mainFrame.PanelNames[0]);
            }
        });
        add(btnBack, gridBagConstraints);

        //確認ボタンを追加
        btnCheckNum = new JButton("確認");
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new Insets(200, 50, 3, 3);
        //ボタンが押されたときの処理
        btnCheckNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String strNum = "0123456789";
                final String title = "エラー";
                final String selectValues[] = {"確定", "再入力"};

                final String str = SubPanel.this.textCheckNum.getText();

                if (str.indexOf("配達確認番号を入力") == 0) {

                        JLabel label = new JLabel("配達確認番号欄が空欄です");
                        label.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(mainFrame, label, title, JOptionPane.WARNING_MESSAGE);
                        return;

                }else if(str.indexOf("配達確認番号を入力") != 0){

                    for (int i = 0; i < str.length(); i++) {
                        if (strNum.indexOf(str.substring(i, i + 1)) == -1) {
                            JLabel label = new JLabel("数字を入力してください");
                            label.setForeground(Color.RED);
                            JOptionPane.showMessageDialog(mainFrame, label, title, JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }

                }


                int select = JOptionPane.showOptionDialog(
                        mainFrame,
                                "\n"+
                                "注文確認番号: " + textCheckNum.getText() +
                                        "\n"+
                                        "\n"+
                        "※ 上記の番号でよろしいですか",
                        "確認画面",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        selectValues,
                        selectValues[0]);

                if (select == JOptionPane.YES_OPTION) {

                    mainFrame.reloadPanel(mainFrame.PanelNames[2]);
                    panelChangeToSubSubPanel(mainFrame.PanelNames[2]);

                } else if (select == JOptionPane.NO_OPTION) {
                }
                return;

            }
        });
        add(btnCheckNum, gridBagConstraints);

    }


    public void panelChangeToSubSubPanel(String str) {
        mainFrame.showSubSubPanel((JPanel) this);
    }

    public void panelChangeToMainPanel(String str) {
        mainFrame.showMainPanel((JPanel) this);
    }

    static class TextGuide extends JTextField implements FocusListener {
        private static final long serialVersionUID = 1L;
        String helpmsg;
        String bakstr = "";

        TextGuide(String msg) {
            helpmsg = msg;
            addFocusListener(this);
            drawmsg();
        }

        void drawmsg() {
            setForeground(Color.LIGHT_GRAY);
            setText(helpmsg);
        }

        @Override
        public void focusGained(FocusEvent arg0) {
            setForeground(Color.BLACK);
            setText(bakstr);
        }

        @Override
        public void focusLost(FocusEvent arg0) {
            bakstr = getText();
            if (bakstr.equals("")) {
                drawmsg();
            }
        }
    }
}



