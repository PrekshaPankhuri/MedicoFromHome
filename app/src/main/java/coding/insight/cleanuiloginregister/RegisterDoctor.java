package coding.insight.cleanuiloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.app.ProgressDialog;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterDoctor extends AppCompatActivity {
    Spinner Doctime;
    EditText docname, dochos, docspec, docexp;
    Button docreg;
    DatabaseReference docDtabaseref;
    String doc="doctors";
    String Selectedtime = "0-0";
    boolean flag=true;
    FirebaseAuth fauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);

        docname = findViewById(R.id.DoctorRegName);
        docspec = findViewById(R.id.Doctorspecification);
        dochos = findViewById(R.id.Doctorhospital);
        docexp = findViewById(R.id.doctorExpertise);
        docreg = findViewById(R.id.doctorRegister);
        Doctime = findViewById(R.id.Doctime);

        ArrayAdapter<CharSequence> adapterfordoctime = ArrayAdapter.createFromResource(this, R.array.DoctorTime, android.R.layout.simple_spinner_item);
        adapterfordoctime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Doctime.setAdapter(adapterfordoctime);

        Doctime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Selectedtime = getResources().getStringArray(R.array.DoctorTime)[i];
            }
        });

        fauth=FirebaseAuth.getInstance();
        Log.e("Logging","Run Succeeded");

        docreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = docname.getText().toString().trim();
                String Hospital = dochos.getText().toString().trim();
                String Specification = docspec.getText().toString().trim();
                String Expertise = docexp.getText().toString().trim();

                if(TextUtils.isEmpty(Name)){
                    docname.setError("Field is required.");
                    flag = false;
                }

                if(TextUtils.isEmpty(Hospital)){
                    dochos.setError("Field is required.");
                    flag = false;
                }

                if(TextUtils.isEmpty(Specification)){
                    docspec.setError("Field is required.");
                    flag = false;
                }
                if(TextUtils.isEmpty(Expertise)){
                    docspec.setError("Field is required.");
                    flag = false;
                }

                if(flag){
                    docDtabaseref= FirebaseDatabase.getInstance().getReference().child("doctors").push();
                    HashMap DocInfo=new HashMap();
                    DocInfo.put("Name",Name);
                    DocInfo.put("Hospital",Hospital);
                    DocInfo.put("Specification",Specification);
                    DocInfo.put("Expertise",Expertise);
                    DocInfo.put("DoctorTime",Selectedtime);

                    docDtabaseref.setValue(DocInfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterDoctor.this,"Doctor Added...!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                                finish();
                            }
                            else{
                                Toast.makeText(RegisterDoctor.this,"Unable to add...! Try again.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


}

