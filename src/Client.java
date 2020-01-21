
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            //送信先のIPアドレス(ドメインなどの名前)とポートを指定
            Socket sock = new Socket("localhost", 10000);

            //送信ストリームの取得(DataOutputStreamでラップ)
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());

            // Scanner scanner = new Scanner(System.in);

            //送信データ
            String strData = "HOUKOKU/3/false_false_false/acceptance_100\nHOUKOKU/5/false_false_false/acceptance_200";

            //scanner.close();

            //String型送信
            out.writeUTF(strData);

            System.out.println("基本型を送信しました。");

            //送信ストリームを表示
            out.close();

            //終了
            sock.close();

        } catch (IOException e) {
            System.err.println("エラー");
        }
    }
}
