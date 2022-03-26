package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHospitalData extends AppCompatActivity {
    RecyclerView recyclerViewh;
    AdminHosAdapter AhAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hospital_data);

        recyclerViewh = (RecyclerView)findViewById(R.id.ahrv);
        recyclerViewh.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<AdminHodpitalModel> options =
                new FirebaseRecyclerOptions.Builder<AdminHodpitalModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("hospital"), AdminHodpitalModel.class).build();


        AhAdapter = new AdminHosAdapter(options);
        recyclerViewh.setAdapter(AhAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AhAdapter.startListening();
    }

    @Override
    protected void onStop() {

        super.onStop();
        AhAdapter.stopListening();
    }
}