package mojeong.example.test2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
public class MainActivity extends AppCompatActivity {

    private EditText et1, et2, et3, et4, et5;
    private Socket socket;
    private DataOutputStream writeSocket;
    private DataInputStream readSocket;
    private Handler mHandler = new Handler();
    private Button t_button;
    private Button h_button;
    private Button m_button;
    private Button tud_button;
    private Button hud_button;
    private Button mud_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlelayout);
        t_button = (Button)findViewById(R.id.button3);
        h_button = (Button)findViewById(R.id.button10);
        m_button = (Button)findViewById(R.id.button11);
        tud_button = (Button)findViewById(R.id.button8);
        hud_button = (Button)findViewById(R.id.button20);
        mud_button = (Button)findViewById(R.id.button21);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText4);
        et4 = (EditText) findViewById(R.id.editText5);
        et5 = (EditText) findViewById(R.id.editText6);
    }
    String count="up";
    String count2="up";
    String count3="up";
    String OX="O";
    String OX2="O";
    String OX3="O";
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                (new Connect()).start();
                break;
            case R.id.button2:
                (new Disconnect()).start();
                break;
            case R.id.button3:
                if (OX == "O") {
                    OX = "X";
                    t_button.setText("X");
                } else {
                    OX = "O";
                    t_button.setText("O");
                }
                (new sendMessage()).start();
                break;
            case R.id.button10:
                if (OX2 == "O") {
                    OX2 = "X";
                    h_button.setText("X");
                } else{
                    OX2 = "O";
                    h_button.setText("O");
                }
                (new sendMessage2()).start();
                break;
            case R.id.button11:
                if (OX3 == "O") {
                    OX3 = "X";
                    m_button.setText("X");
                } else {
                    OX3 = "O";
                    m_button.setText("O");
                }
                (new sendMessage3()).start();
                break;
            case R.id.button8:
                if (count == "up") {
                    tud_button.setText("DOWN");
                    count = "down";
                } else {
                    tud_button.setText("UP");
                    count = "up";
                }
                break;
            case R.id.button20:
                if (count2 == "up") {
                    hud_button.setText("DOWN");
                    count2 = "down";
                } else {
                    hud_button.setText("UP");
                    count2 = "up";
                }
                break;
            case R.id.button21:
                if (count3 == "up") {
                    mud_button.setText("DOWN");
                    count3 = "down";
                } else {
                    mud_button.setText("UP");
                    count3 = "up";
                }
                break;
        }

    }

    class Connect extends Thread {
        public void run() {
            Log.d("Connect", "Run Connect");
            String ip = null;
            int port = 0;

            try {
                ip = et1.getText().toString();
                port = Integer.parseInt(et2.getText().toString());
            } catch (Exception e) {
                final String recvInput = "정확히 입력하세요!";
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }
                });
            }
            try {
                socket = new Socket(ip, port);
                writeSocket = new DataOutputStream(socket.getOutputStream());


                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast("연결에 성공하였습니다.");
                    }

                });
            } catch (Exception e) {
                final String recvInput = "연결에 실패하였습니다.";
                Log.d("Connect", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }

        }
    }

    class Disconnect extends Thread {
        public void run() {
            try {
                if (socket != null) {
                    socket.close();
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            setToast("연결이 종료되었습니다.");
                        }
                    });

                }

            } catch (Exception e) {
                final String recvInput = "연결을 끊는데 실패했습니다.";
                Log.d("Connect", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }

        }
    }

    class sendMessage extends Thread {
        public void run() {
            String msg1 = et3.getText().toString();
            String msg2 = "t" + count + OX;

            try {
                byte[] b1;
                byte[] b2;
                b1 = msg1.getBytes();
                b2 = msg2.getBytes();
                writeSocket.write(b2);
                sleep(1);
                writeSocket.write(b1);

            } catch (Exception e) {
                final String recvInput = "메시지 전송에 실패하였습니다.";
                Log.d("Message", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }

        }
    }

    class sendMessage2 extends Thread {
        public void run() {
            String msg1 = et4.getText().toString();
            String msg2 = "h" + count2 + OX2;

            try {
                byte[] b1;
                byte[] b2;
                b1 = msg1.getBytes();
                b2 = msg2.getBytes();
                writeSocket.write(b2);
                sleep(1);
                writeSocket.write(b1);

            } catch (Exception e) {
                final String recvInput = "메시지 전송에 실패하였습니다.";
                Log.d("Message", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }

        }
    }

    class sendMessage3 extends Thread {
        public void run() {
            String msg1 = et5.getText().toString();
            String msg2 = "m" + count3 + OX3;

            try {
                byte[] b1;
                byte[] b2;
                b1 = msg1.getBytes();
                b2 = msg2.getBytes();
                writeSocket.write(b2);
                sleep(1);
                writeSocket.write(b1);

            } catch (Exception e) {
                final String recvInput = "메시지 전송에 실패하였습니다.";
                Log.d("Message", e.getMessage());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        setToast(recvInput);
                    }

                });

            }

        }
    }

    void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }}