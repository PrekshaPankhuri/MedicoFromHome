package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HospitalScreen extends AppCompatActivity {
    Button doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_screen);
        doctor = findViewById(R.id.buttondoct);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalScreen.this, DoctorsUpdate.class);
                startActivity(intent);
            }
        });
    }
}