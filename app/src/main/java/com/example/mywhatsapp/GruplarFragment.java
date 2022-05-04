package com.example.mywhatsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GruplarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GruplarFragment extends Fragment {

    private  View grupCerceveView;
 private ListView list_view;
 private ArrayAdapter<String> arrayAdapter;
 private ArrayList<String> grup_listeleri=new ArrayList<>();

//FIREBASE
    private DatabaseReference grupYolu;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GruplarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GruplarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GruplarFragment newInstance(String param1, String param2) {
        GruplarFragment fragment = new GruplarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      grupCerceveView=inflater.inflate(R.layout.fragment_gruplar, container, false);

      //firebase tanimlama
        grupYolu= FirebaseDatabase.getInstance().getReference().child("Gruplar");



      //tanimlamalar

        list_view=grupCerceveView.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,grup_listeleri);
        list_view.setAdapter(arrayAdapter);


        //gruplari alama kodlari
        GruplariAlVeGoster();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mevcutGrupAdi=parent.getItemAtPosition(position).toString();
                Intent grupChatActivity=new Intent(getContext(),GrupChatActivity.class);
                grupChatActivity.putExtra("grupAdi",mevcutGrupAdi);
                startActivity(grupChatActivity);

            }
        });


      return  grupCerceveView;
    }

    private void GruplariAlVeGoster() {


        grupYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set=new HashSet<>();
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){

                    set.add(((DataSnapshot)iterator.next()).getKey());

                }
                grup_listeleri.clear();
                grup_listeleri.addAll(set);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}