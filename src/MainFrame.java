import javax.swing.*;

import java.awt.*;

public class MainFrame extends JFrame {
    public String[] PanelNames = {"mainPanel", "subPanel", "subsubPanel"};

    //パネルの作成
    MainPanel mainPanel = new MainPanel(this, PanelNames[0]);
    SubPanel subPanel = new SubPanel(this, PanelNames[1]);
    SubSubPanel subsubPanel = new SubSubPanel(this, PanelNames[2]);

    public MainFrame() {
        this.add(mainPanel);
        mainPanel.setVisible(true);
        this.add(subPanel);
        subPanel.setVisible(false);
        this.add(subsubPanel);
        subsubPanel.setVisible(false);

        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setTitle("EV3 Delivery System");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public void reloadPanel(String str) {
        if (str == PanelNames[0]) {
            this.remove(this.mainPanel);
            MainPanel mainPanel = new MainPanel(this, PanelNames[0]);
            this.add(mainPanel);
        } else if (str == PanelNames[1]) {
            this.remove(this.subPanel);
            SubPanel subPanel = new SubPanel(this, PanelNames[1]);
            this.add(subPanel);
        } else if (str == PanelNames[2]) {
            this.remove(this.subsubPanel);
            SubSubPanel subsubPanel = new SubSubPanel(this, PanelNames[2]);
            this.add(subsubPanel);
        }
    }

    //Mainパネルを表示
    public void showMainPanel(JPanel nowPanel) {
        nowPanel.setVisible(false);
        mainPanel.setVisible(true);
    }

    //Subパネルを表示
    public void showSubPanel(JPanel nowPanel) {
        nowPanel.setVisible(false);
        subPanel.setVisible(true);
    }

    //SubSubパネルを表示
    public void showSubSubPanel(JPanel nowPanel) {
        nowPanel.setVisible(false);
        subsubPanel.setVisible(true);
    }
}