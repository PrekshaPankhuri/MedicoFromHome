package coding.insight.cleanuiloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class HospitalRegister extends AppCompatActivity {

    EditText HospitalName,HospitalAddress,HospitalNumber,HospitalEmail,HospitalPassword,HospitalBeds,HospitalCylinders,HospitalBlood;
    Button Hospitalregister;
    FirebaseAuth fauth;
    DatabaseReference userDatabaseRef;
    String us="hospital";
    ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_hospital_register);
        changeStatusBarColor();

        Hospitalregister=findViewById(R.id.cirRegisterButtonHospital);
        HospitalName=findViewById(R.id.HospitalRegName);
        HospitalAddress=findViewById(R.id.HospitalRegAddress);
        HospitalEmail=findViewById(R.id.HospitalRegEmail);
        HospitalNumber=findViewById(R.id.HospitalRegMobile);
        HospitalPassword=findViewById(R.id.HospitalRegPassword);
        HospitalCylinders=findViewById(R.id.HospitalRegCylinders);
        HospitalBeds=findViewById(R.id.HospitalRegBeds);
        HospitalBlood=findViewById(R.id.HospitalRegBlood);
        fauth=FirebaseAuth.getInstance();
        loader=new ProgressDialog(this);

        Hospitalregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=HospitalName.getText().toString().trim();
                String address=HospitalAddress.getText().toString().trim();
                String number=HospitalNumber.getText().toString().trim();
                String email=HospitalEmail.getText().toString().trim();
                String password=HospitalPassword.getText().toString().trim();
                String beds=HospitalBeds.getText().toString().trim();
                String cylinders=HospitalCylinders.getText().toString().trim();
                String bloodBank=HospitalBlood.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    HospitalName.setError("Field is required.");
                }
                if(TextUtils.isEmpty(address)){
                    HospitalAddress.setError("Field is required.");
                }
                if(TextUtils.isEmpty(email)){
                    HospitalEmail.setError("Field is required.");
                }
                if(TextUtils.isEmpty(number)){
                    HospitalNumber.setError("Field is required.");
                }
                if(TextUtils.isEmpty(password)){
                    HospitalPassword.setError("Field is required.");
                }
                if(TextUtils.isEmpty(beds)){
                    HospitalBeds.setError("Field is required.");
                }
                if(TextUtils.isEmpty(cylinders)){
                    HospitalCylinders.setError("Field is required.");
                }
                if(TextUtils.isEmpty(bloodBank)){
                    HospitalBlood.setError("Field is required.");
                }
                if(password.length()<6){
                    HospitalPassword.setError("Should be at least 6 digits.");
                }
                else{
                    loader.setMessage("Registering You...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(HospitalRegister.this,"Error occurred...!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserID=fauth.getCurrentUser().getUid();
                                userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("hospital").child(currentUserID);
                                HashMap userInfo=new HashMap();
                                userInfo.put("ID",currentUserID);
                                userInfo.put("Name",name);
                                userInfo.put("Address",address);
                                userInfo.put("Email",email);
                                userInfo.put("Number",number);
                                userInfo.put("Password",password);
                                userInfo.put("Beds",beds);
                                userInfo.put("Cylinders",cylinders);
                                userInfo.put("BloodBank",bloodBank);
                                userInfo.put("type",us);

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(HospitalRegister.this,"User Created...!",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),HospitalHomePage.class));
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(HospitalRegister.this,"Unable to create, Try again.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    loader.dismiss();
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

    public void onLoginClick(View View){
        startActivity(new Intent(HospitalRegister.this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }
}
