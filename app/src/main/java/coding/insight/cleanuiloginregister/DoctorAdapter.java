package coding.insight.cleanuiloginregister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.*;

import java.lang.reflect.Array;

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
        holder.DocHospital.setText("DocHospital: " + model.getHospital());
        holder.DocTime.setText("DocTime: " + model.getDoctorTime());
        holder.DocSpecification.setText("Doctor Specification : " + model.getSpecification());
        final String[] selectedTime = {""};

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(view.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_doc_popup))
                        .setExpanded(true,1200)
                        .create();

                View v = dialogPlus.getHolderView();
                Spinner Doctimespinner = v.findViewById(R.id.Doctime);
                EditText HospitalName = v.findViewById(R.id.txtName);
                Button btnUpdate = v.findViewById(R.id.btnuupdate);


                ArrayAdapter<String> time_adapter = new ArrayAdapter<>( holder.DocTime.getContext() ,
                        R.layout.support_simple_spinner_dropdown_item , holder.DocTime.getResources().getStringArray(R.array.DoctorTime));
                Doctimespinner.setAdapter(time_adapter);

                HospitalName.setText(holder.DocHospital.getText().toString().substring(13));

                List<String> time = Arrays.asList(
                        view.getResources().getStringArray(R.array.DoctorTime)
                );

                Doctimespinner.setSelection(time.indexOf(new String(holder.DocTime.getText().toString().substring(9))));
                Doctimespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedTime[0] = view.getResources().getStringArray(R.array.DoctorTime)[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedTime[0] = view.getResources().getStringArray(R.array.DoctorTime)[0];
                    }
                });


                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Hospitaltext = HospitalName.getText().toString();
                        Map<String,Object> map = new HashMap<>();
                        map.put("Hospital",Hospitaltext);
                        map.put("DoctorTime",selectedTime[0]);

                        FirebaseDatabase.getInstance().getReference().child("doctors")
                                .child(getRef(holder.getLayoutPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.DocName.getContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.DocName.getContext(),"Error while Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are You Sure???");
                builder.setMessage("Deleted Data can't be Undo!!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("doctors").child(getRef(holder.getLayoutPosition()).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(),"Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

            }
        });
    }

    @NonNull
    @Override
    public DoctorAdapter.doctorviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,parent,false);
        return new doctorviewholder(view);
    }

    class doctorviewholder extends RecyclerView.ViewHolder {
        TextView DocName,DocExper,DocHospital,DocTime ,DocSpecification;
        Button editBtn , deleteBtn;
        public doctorviewholder(View view) {

            super(view);

            DocName = view.findViewById(R.id.DocName);
            DocExper = view.findViewById(R.id.doctorExper);
            DocHospital = view.findViewById(R.id.DoctType);
            DocTime = view.findViewById(R.id.DoctTime);
            DocSpecification = view.findViewById(R.id.DocSpecification);
            editBtn = view.findViewById(R.id.btnEdit);
            deleteBtn = view.findViewById(R.id.btnDelete);

        }
    }
}