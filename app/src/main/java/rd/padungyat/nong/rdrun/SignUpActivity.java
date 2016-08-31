package rd.padungyat.nong.rdrun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    //Explicit การประกาศตัวแปร ประกอบด้วย 1) access การเข้าถีง (public,private) 2)data type 3)name
    private EditText nameEditText, surnameEditText, userEditText, passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton avata1RadioButton, avata2RadioButton, avata3RadioButton, avata4RadioButton,
            avata5RadioButton;
    private String nameString,surnameString,userString, passwordString,avataString;
    private static final String urlPHP = "http://swiftcodingthai.com/rd/add_user_master.php";
    //private static final String urlPHP = "http://swiftcodingthai.com/rd/add_user_nongphat_31Aug.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind or Initial Widget คือการผูกความสัมพันธ์ระหว่างตัวแปร และ Widget (เช่น Text RadioBottom)
        //findViewById
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvata);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata5RadioButton = (RadioButton) findViewById(R.id.radioButton5);

        //การทำ Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        avataString = "0";
                        break;
                    case R.id.radioButton2:
                        avataString = "1";
                        break;
                    case R.id.radioButton3:
                        avataString = "2";
                        break;
                    case R.id.radioButton4:
                        avataString = "3";
                        break;
                    case R.id.radioButton5:
                        avataString = "4";
                        break;
                }
            }
        });

    }//Main Method

    public void clickSignUpSign(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space การตรวจสอบหาช่องว่าง checkSpace เป็นตัวแปร
        if (checkSpace()) {

            //True
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,R.drawable.nobita48, "มีช่องว่าง","กรุณากรอกทุกช่อง ครับผม");
        } else if (checkChoose()) {
            //True ==> Have Choose
            comfirmValue();
        } else {
            //False ==> Not Choose
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,R.drawable.doremon48,"ยังไม่เลือก Avata","กรุณาเลือกครับ");
        }

    }//clickSign

    private void comfirmValue() {

        MyConstant myConstant = new MyConstant();
        int[] avataInts = myConstant.getAvataInts();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(avataInts[Integer.parseInt(avataString)]);
        builder.setTitle("โปรดตรวจสอบข้อมูล");
        builder.setMessage("Name = " + nameString + "\n" +
        "Surname = " + surnameString + "\n" +
        "User = " + userString + "\n" +
        "Password = " + passwordString);     // \n คือให้ขี้นบรรทัดใหม่
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadValueToServer();
                dialog.dismiss();
            }
        });
        builder.show();
    }//confirmValue

    private void uploadValueToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        // isAdd เป็นตัวแปรฝั่ง PHP
        final RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("User", userString)
                .add("Password", passwordString)
                .add("Avata", avataString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("31AugV1", "Result ==> " + response.body().string());
                finish();
            }
        });

    }//upload

    private boolean checkChoose() {

        boolean result = false;
        if (avata1RadioButton.isChecked() ||
                avata2RadioButton.isChecked() ||
                avata3RadioButton.isChecked() ||
                avata4RadioButton.isChecked() ||
                avata5RadioButton.isChecked()) {
            result = true;
        }

        //Log.d("30AugV1", "result ==> " + result);
        return result;
    }

    private boolean checkSpace() {

        boolean result = false;
        // เครื่องหมาย || คือ เงื่อนไข or
        if (nameString.equals("") ||
                surnameString.equals("") ||
                userString.equals("") ||
                passwordString.equals("")) {
            result  = true;

        }
        return result;
    }
} //Main class
