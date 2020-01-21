import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Sub implements Runnable{

    LinkedList<LinkedList<String>> report;

    Sub(LinkedList<LinkedList<String>> report){
        this.report = report;
    }
    public void run(){
            System.out.println("スレッド開始");
            while(true){
                try{
                    //サーバーのポート番号を指定
                    ServerSocket svSock = new ServerSocket(10000);

                    //アクセスを待ち受け
                    Socket sock = svSock.accept();

                    //受信ストリームの取得(DataInputStreamでラップ)
                    DataInputStream in = new DataInputStream(sock.getInputStream());

                    //String型データを受信
                    String strData = in.readUTF();

                    System.out.println("「"+strData+"」を受信しました。");

                    MainFrame.get_Report(strData);

                    //受信ストリームの終了
                    in.close();

                    //サーバー終了
                    svSock.close();

                }catch(IOException e){
                    System.err.println("エラー");
                    System.exit(1);
                }
            }
    }
}
