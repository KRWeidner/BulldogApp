package com.example.ktabe.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {

    private RecyclerView votesList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter VotesAdapter;

    public RankingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rankings, container, false);

        votesList = (RecyclerView)view.findViewById(R.id.votes_list);
        layoutManager = new LinearLayoutManager(getContext());
        votesList.setLayoutManager(layoutManager);
        refreshList();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshList();
    }

    private void refreshList(){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Vote> votes= realm.where(Vote.class).findAll();
        VotesAdapter adapter = new VotesAdapter(getActivity(), votes);
        votesList.setAdapter(adapter);
    }

}
