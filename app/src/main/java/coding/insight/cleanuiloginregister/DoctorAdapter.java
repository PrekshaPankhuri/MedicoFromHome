package coding.insight.cleanuiloginregister;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DoctorAdapter extends FirebaseRecyclerAdapter<DoctorModel,DoctorAdapter.doctorviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<DoctorModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DoctorAdapter.doctorviewholder holder, int position, @NonNull DoctorModel model) {
        holder.DocName.setText("Name: "+ model.getName());
        holder.DocExper.setText("Expertise: " + model.getExpertise());
        holder.DocType.setText("DocHospital: " + model.getHospital());
        holder.DocTime.setText("DocTime: " + model.getDoctorTime());
        holder.DocSpecification.setText("Doctor Specification : " + model.getSpecification());
    }

    @NonNull
    @Override
    public DoctorAdapter.doctorviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,parent,false);
        return new doctorviewholder(view);
    }

    class doctorviewholder extends RecyclerView.ViewHolder {
        TextView DocName,DocExper,DocType,DocTime ,DocSpecification;
        public doctorviewholder(View view) {

            super(view);

            DocName = view.findViewById(R.id.DocName);
            DocExper = view.findViewById(R.id.doctorExper);
            DocType = view.findViewById(R.id.DoctType);
            DocTime = view.findViewById(R.id.DoctTime);
            DocSpecification = view.findViewById(R.id.DocSpecification);

        }
    }
}
