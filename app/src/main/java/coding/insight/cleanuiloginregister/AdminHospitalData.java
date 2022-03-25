package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AdminHospitalData extends AppCompatActivity {
    RecyclerView recyclerViewh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hospital_data);

        recyclerViewh = (RecyclerView)findViewById(R.id.ahrv);
        recyclerViewh.setLayoutManager(new LinearLayoutManager(this));
    }
}