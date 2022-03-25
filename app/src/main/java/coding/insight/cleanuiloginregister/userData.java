package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class userData extends AppCompatActivity {
    RecyclerView recyclerview;
    UserAdapter useradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        recyclerview = (RecyclerView)findViewById(R.id.rv);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), userModel.class).build();


        useradapter = new UserAdapter(options);
        recyclerview.setAdapter(useradapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        useradapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        useradapter.stopListening();
    }
}
