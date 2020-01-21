import javax.swing.*;
import java.util.LinkedList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.IOException;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.awt.*;

public class MainFrame extends JFrame{
    public String[] PanelNames = {"mainPanel", "subPanel", "subsubPanel"};

    //パネルの作成
    MainPanel mainPanel = new MainPanel(this, PanelNames[0],report);
    SubPanel subPanel = new SubPanel(this, PanelNames[1],report);
    SubSubPanel subsubPanel = new SubSubPanel(this, PanelNames[2],report);

    public static LinkedList < LinkedList <String> > report = new LinkedList<LinkedList<String>>();

    static Date date;
    static SimpleDateFormat sdf;
    static Calendar calendar;

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

        date = new Date();
        sdf = new SimpleDateFormat("HH:mm");
        calendar = Calendar.getInstance();
        calendar.setTime(date);

        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle("EV3 Delivery System");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        Sub sub = new Sub(report);
        Thread thread = new Thread(sub);
        thread.start();


        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){

        }



    }

    public void reloadPanel(String str) {
        if (str == PanelNames[0]) {
            this.remove(this.mainPanel);
            MainPanel mainPanel = new MainPanel(this, PanelNames[0],report);
            this.add(mainPanel);
        } else if (str == PanelNames[1]) {
            this.remove(this.subPanel);
            SubPanel subPanel = new SubPanel(this, PanelNames[1],report);
            this.add(subPanel);
        } else if (str == PanelNames[2]) {
            this.remove(this.subsubPanel);
            SubSubPanel subsubPanel = new SubSubPanel(this, PanelNames[2],report);
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

    /**
     * 報告を受け取る（正）
     * 報告型のインスタンスを100個受け取る
     * 2147483646+1の報告型のインスタンスを受け取る（誤）
     *
     * テスト番号:9-1-1
     * テスト種別:A
     * テストデータ:"報告"
     * テスト手順:メソッドを呼び出す
     * 期待されるテスト結果:報告型のデータを受け取る
     * テスト結果の確認方法:結果を目視で確認する
     *
     */

    public static void get_Report(String rcvData) {

        //String rcvData = eV3Connection.rcvData();
        if(rcvData.contains("HOUKOKU")){
            // 報告ごとに分割する
            String[] str = rcvData.split("\n");

            // 荷物で回す
            for(int i = 0 ; i<str.length ; i++){
                // 要素ごとに分割する
                String[] uepon = str[i].split("/");
                // 報告が既存のものであるか判断する変数
                boolean judge = true;

                // 識別番号が登録されているか確認する
                for(int index=0 ; index<report.size() ; index++){
                    if(uepon[1].equals(report.get(index).get(0))){
                        // 荷物の状態が変更されているか確認
                        if(uepon[2].equals(report.get(index).get(1))){

                            // 中継所発送のとき
                            if(uepon[3].contains("delivery")){
                                if(report.get(index).size()<5)
                                    report.get(index).add("");
                            }
                            // 中継所到着のとき
                            else if(uepon[3].contains("relaystation")){
                                if(report.get(index).size()>5){
                                    String[] split = uepon[3].split("_");
                                    report.get(index).set(4,split[0] + "_" + dateAdder(split[1]));
                                    break;
                                }
                            }

                            // 最新の時間を追加する
                            String[] split = uepon[3].split("_");
                            report.get(index).add(split[0] + "_" + dateAdder(split[1]));
                            judge = false;
                            break;
                        }
                        // 荷物の状態が変更されていたとき
                        else{
                            report.get(index).set(1,uepon[2]);
                            judge = false;
                            break;
                        }
                    }
                }

                // 識別番号がなかったとき
                if(judge){
                    report.add(new LinkedList<String>());
                    int index = report.size()-1;

                    // 識別番号を追加
                    report.get(index).add(uepon[1]);
                    // 状態の追加
                    report.get(index).add(uepon[2]);

                    // 受付時間の追加
                    if(uepon[3].contains("acceptance")){
                        String[] split = uepon[3].split("_");
                        report.get(index).add(split[0] + "_" + dateAdder(split[1]));
                    }
                    else{
                        // 空白の要素を追加しておく
                        report.get(index).add("");
                        String[] split = uepon[3].split("_");
                        report.get(index).add(split[0] + "_" + dateAdder(split[1]));
                    }
                }

            }

        }
    }

    /**
     * 正しい識別番号
     * 誤った識別番号
     * 数値以外の引数を受け取る
     * 目当ての荷物が無いとき
     * int型の値域外の値を受け取る
     *
     * テスト番号:9-1-2
     * テスト種別:D
     * テストデータ:"識別番号"
     * テスト手順:メソッドを呼び出す
     * 期待されるテスト結果：各テストデータをメソッドに渡し、それぞれの分岐に置いたメッセージが出力されるか確認する
     * テスト結果の確認方法:結果を目視で確認する
     *
     * テスト番号:9-1-3
     * テスト種別:D
     * テストデータ:"識別番号"
     * テスト手順:メソッドを呼び出す
     * 期待されるテスト結果：各テストデータをメソッドに渡し、それぞれの分岐に置いたメッセージが出力されるか確認する
     * テスト結果の確認方法:結果を目視で確認する
     */
    public static String judge_Delivery_State(String requestInfo) {

        String result = "";
        int index = -1;
        // 識別番号が登録されているか確認する
        for(int i=0 ; i<report.size() ; i++){
            if(report.get(i).get(0).equals(requestInfo)){
                index = i;
                break;
            }
        }

        // 識別番号が見つかったとき
        if(index != -1){
            // 状態が変更されていないとき
            if(report.get(index).get(1).equals("false_false_false")){
                // 最新の時間が保存されている要素の番号を取得する
                int i = report.get(index).size()-1;
                String[] str = report.get(index).get(i).split("_");

                switch(str[0]){
                    case "acceptance":
                        result += "受付時刻";
                        break;

                    case "collect":
                        result += "受付所発送時刻";
                        break;

                    case "relaystation":
                        result += "中継所到着時刻";
                        break;

                    case "delivery":
                        result += "中継所発送時刻";
                        break;

                    case "receipt":
                        result += "配達完了時刻";

                }
                result += "  " + str[1];
            }
            // 状態が変更されているとき
            else{
                String[] str = report.get(index).get(1).split("_");
                int state=0;
                for(int i=0 ; i<3 ; i++)
                    if(str[i].equals("true")){
                        state = i;
                        break;
                    }
                switch(state){
                    case 0:
                        result = "中継所の応答なしにより再配達待ちです";
                        break;

                    case 1:
                        result = "住所間違いにより再配達待ちです";
                        break;

                    case 2:
                        result = "不在のため再配達待ちです";
                }
            }
        }

        else
            result = "識別番号が見つかりませんでした";
        System.out.println(result);

        return result;
    }

    public static String dateAdder(String time){
        Date result;
        Calendar startTime = calendar;
        int num = Integer.parseInt(time);

        startTime.add(Calendar.SECOND,num);
        result = calendar.getTime();

        return sdf.format(result);
    }

    /*
     * 荷物型、報告型のインスタンスを＿で区切って、String型に変換する
     * 報告をString型に変換する（正）
     * 荷物をString型に変換する（正）
     * "AIUEO"をString型に変換する（誤）
     *
     * テスト番号:9-1-4
     * テスト種別:A
     * テストデータ:
     * テスト手順:メソッドを呼び出す
     * 期待されるテスト結果:対応する
     * テスト結果の確認方法:結果を目視で確認する
     */
// public String reformatted_Data() {
//  return null;
// }

}