package coding.insight.cleanuiloginregister;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserAdapter extends FirebaseRecyclerAdapter<userModel,UserAdapter.userviewholder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserAdapter(@NonNull FirebaseRecyclerOptions<userModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull userviewholder holder, int position, @NonNull userModel model) {
        holder.UserName.setText(model.getName());
        holder.UserEmail.setText(model.getEmail());
        holder.UserPhone.setText(model.getNumber());

    }

    @NonNull
    @Override
    public userviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new userviewholder(view);
    }

    class userviewholder extends RecyclerView.ViewHolder{
        TextView UserName,UserEmail,UserPhone;

        public userviewholder(@NonNull View itemView) {
            super(itemView);
            UserName = (TextView)itemView.findViewById(R.id.UserName);
            UserEmail = (TextView)itemView.findViewById(R.id.UserEmail);
            UserPhone = (TextView)itemView.findViewById(R.id.UserPhone);
        }
    }

}
