import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

public class MainPanel extends JPanel {

    JButton btnEnter;
    MainFrame mainFrame;
    String string;
    JLabel jLabel;
    JLabel title;

    LinkedList<LinkedList<String>> report;

    public MainPanel(MainFrame mf, String str, LinkedList<LinkedList<String>> report) {
        mainFrame = mf;
        string = str;
        this.setName("mainPanel");
        setSize(700, 500);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        ImageIcon imageIcon = new ImageIcon("./HeadImage.png");
        jLabel = new JLabel(imageIcon);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0d;
        gridBagConstraints.weighty = 1.0d;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        add(jLabel, gridBagConstraints);

        title = new JLabel("EV3 Delivery System");
        title.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        add(title, gridBagConstraints);

        JLabel label = new JLabel("配達状況確認はこちらから→");


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        gridBagConstraints.anchor=GridBagConstraints.EAST;
        gridBagConstraints.insets=new Insets(0,0,45,180);
        add(label,gridBagConstraints);


        btnEnter = new JButton("依頼内容確認");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(0, 0, 45, 50);

        btnEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.reloadPanel(mainFrame.PanelNames[1]);
                panelChangeToSubPanel(mainFrame.PanelNames[1]);
            }
        });
        add(btnEnter, gridBagConstraints);

        this.report = report;

    }
    public void panelChangeToSubPanel(String toPanelName) {
        mainFrame.showSubPanel((JPanel) this);
    }
}
