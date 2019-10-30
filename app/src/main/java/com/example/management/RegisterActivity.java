package com.example.management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText phnumberText = (EditText) findViewById(R.id.phnumberText);
        final EditText addressText = (EditText) findViewById(R.id.addressText);

        Button duplicationButton = (Button) findViewById(R.id.duplicationButton);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        RadioButton denyRadio = (RadioButton) findViewById(R.id.denyRadio);
        final RadioButton acceptRadio = (RadioButton) findViewById(R.id.acceptRadio);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", passwordText.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "비밀번호는 4~16자 영문 대소문자, 숫자, 특수문자만 가능합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!acceptRadio.isChecked()){
                    Toast.makeText(RegisterActivity.this, "약관에 동의하십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userPW = passwordText.getText().toString();
                String userID = idText.getText().toString();
                String userName = nameText.getText().toString();
                String userPhnNumber = phnumberText.getText().toString();
                String userAdress = addressText.getText().toString();

                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + "userInfoList.txt", true));
                    writer.write(userName+"/"+userID+"/"+userPW+"/"+userPhnNumber+"/"+userAdress+"\n");
                    writer.close();

                    Toast.makeText(RegisterActivity.this,"회원가입 완료", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        duplicationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(getFilesDir()+"userInfoList.txt"));
                    String readStr = "";
                    String line = null;
                    String[] splitedStr = null;
                    while((line = reader.readLine()) != null) {
                        splitedStr = null;
                        splitedStr = line.split("/");
                        for (int i = 0; i < splitedStr.length; i++) {
                            splitedStr[i] = splitedStr[i].trim();
                        }
                    }
                    reader.close();

                    Toast.makeText(RegisterActivity.this, readStr.substring(0, readStr.length()-1), Toast.LENGTH_SHORT).show();

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
