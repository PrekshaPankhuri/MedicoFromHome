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
        FirebaseRecyclerOptions<AdminHodpitalModel> options =
                new FirebaseRecyclerOptions.Builder<AdminHodpitalModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("hospital").orderByChild("Name").startAt(str).endAt(str+"~"), AdminHodpitalModel.class).build();

        AhAdapter = new AdminHosAdapter(options);
        AhAdapter.startListening();
        recyclerViewh.setAdapter(AhAdapter);
    }
}