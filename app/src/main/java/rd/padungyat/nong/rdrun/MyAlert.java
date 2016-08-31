package rd.padungyat.nong.rdrun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by user on 8/30/2016.
 */
public class MyAlert {

    public void myDialog (Context context,
                          int intIcon,
                          String strTitle,
                          String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);//ปุ่ม Undo จะไม่ Active
        builder.setIcon(intIcon);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //เมื่อกด OK ให้ dialog หายไป
            }
        });
        builder.show();
    }

} //Main Class
