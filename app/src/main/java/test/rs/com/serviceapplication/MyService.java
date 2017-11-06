package test.rs.com.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.Toast;
import android.os.Messenger;
import android.support.annotation.Nullable;


/**
 * Created by akash.sharma on 4/10/2017.
 */

public class MyService extends Service
{


    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg)
        {

            Bundle data = msg.getData();
            String dataString = data.getString("MyString");
            Toast.makeText(getApplicationContext(),
                    "Recieved Message :"+dataString, Toast.LENGTH_SHORT).show();


            try {
                Message respMsg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("ResponseString", "Service : Hello ,"+dataString);
                respMsg.setData(bundle);
                msg.replyTo.send(respMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }


   final Messenger messanger = new Messenger(new IncomingHandler());


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messanger.getBinder();
    }
}
