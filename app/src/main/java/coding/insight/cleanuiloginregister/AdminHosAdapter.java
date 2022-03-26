package coding.insight.cleanuiloginregister;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminHosAdapter extends FirebaseRecyclerAdapter<AdminHodpitalModel, AdminHosAdapter.Ahosviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminHosAdapter(@NonNull FirebaseRecyclerOptions<AdminHodpitalModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Ahosviewholder holder, int position, @NonNull AdminHodpitalModel model) {
        holder.Name.setText(model.getName());
        holder.Email.setText(model.getEmail());
        holder.Number.setText(model.getNumber());
        holder.Address.setText(model.getAddress());
        holder.City.setText(model.getCity());
        holder.Beds.setText(model.getBeds());
        holder.Cylinders.setText(model.getCylinders());
        holder.BloodBank.setText(model.getBloodBank());
    }

    @NonNull
    @Override
    public Ahosviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminhos_item,parent,false);
        return new Ahosviewholder(view);

    }

    class Ahosviewholder extends RecyclerView.ViewHolder{
         TextView Name, Email, Number, Address, City, Beds, Cylinders, BloodBank;

        public Ahosviewholder(@NonNull View itemView) {
            super(itemView);

            Name = (TextView)itemView.findViewById(R.id.AHosName);
            Email = (TextView)itemView.findViewById(R.id.AHosEmail);
            Number = (TextView)itemView.findViewById(R.id.AHosNumber);
            Address = (TextView)itemView.findViewById(R.id.AHosAddress);
            City = (TextView)itemView.findViewById(R.id.AHosCity);
            Beds = (TextView)itemView.findViewById(R.id.AHosBeds);
            Cylinders = (TextView)itemView.findViewById(R.id.AHosCylinder);
            BloodBank = (TextView)itemView.findViewById(R.id.AHosBB);



        }
    }
}
