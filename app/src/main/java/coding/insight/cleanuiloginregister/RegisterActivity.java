package coding.insight.cleanuiloginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    EditText RegisterName, RegisterNumber,RegisterEmail,RegisterPassword;
    Button UserRegister;
    FirebaseAuth fauth;
    DatabaseReference userDatabaseRef;
    String us="user";
    ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        RegisterName=findViewById(R.id.RegName);
        RegisterPassword=findViewById(R.id.RegPassword);
        RegisterNumber=findViewById(R.id.RegMobile);
        RegisterEmail=findViewById(R.id.RegEmail);
        UserRegister=findViewById(R.id.cirRegisterButtonUser);
        fauth=FirebaseAuth.getInstance();
        loader=new ProgressDialog(this);

        UserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=RegisterName.getText().toString().trim();
                String email=RegisterEmail.getText().toString().trim();
                String password=RegisterPassword.getText().toString().trim();
                String number=RegisterNumber.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    RegisterName.setError("Field is required.");
                }
                if(TextUtils.isEmpty(email)){
                    RegisterEmail.setError("Field is required.");
                }
                if(TextUtils.isEmpty(password)){
                    RegisterPassword.setError("Field is required.");
                }
                if(TextUtils.isEmpty(number)){
                    RegisterNumber.setError("Field is required.");
                }
                if(password.length()<6){
                    RegisterPassword.setError("Should be at least 6 digits.");
                }
                else{
                    loader.setMessage("Registering You...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Error...! Try again.",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserID=fauth.getCurrentUser().getUid();
                                userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
                                HashMap userInfo=new HashMap();
                                userInfo.put("ID",currentUserID);
                                userInfo.put("Name",name);
                                userInfo.put("Email",email);
                                userInfo.put("Password",password);
                                userInfo.put("Number",number);
                                userInfo.put("type",us);

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this,"User Created...!",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                                            loader.dismiss();
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this,"Unable to create...! Try again.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
    public void onRegisterClick(View view){
        startActivity(new Intent(this,HospitalRegister.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }



}
