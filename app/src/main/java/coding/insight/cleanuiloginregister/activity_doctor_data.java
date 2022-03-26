package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class activity_doctor_data extends AppCompatActivity {

    RecyclerView recyclerview;
    DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_data);

        recyclerview = (RecyclerView)findViewById(R.id.rv_doctor);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<DoctorModel> options =
                new FirebaseRecyclerOptions.Builder<DoctorModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctors"), DoctorModel.class).build();

        doctorAdapter = new DoctorAdapter(options);
        recyclerview.setAdapter(doctorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doctorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doctorAdapter.stopListening();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<DoctorModel> options =
                new FirebaseRecyclerOptions.Builder<DoctorModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctors").orderByChild("Name").startAt(str).endAt(str+"~"), DoctorModel.class).build();

        doctorAdapter = new DoctorAdapter(options);
        doctorAdapter.startListening();
        recyclerview.setAdapter(doctorAdapter);
    }

}