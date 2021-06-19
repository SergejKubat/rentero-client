package com.metropolitan.rentero_client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.adapter.FAQAdapter;
import com.metropolitan.rentero_client.model.Question;

import java.util.ArrayList;
import java.util.List;

public class FAQFragment extends Fragment {

    private RecyclerView faqRecyclerView;
    private FAQAdapter faqAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f_a_q, container, false);

        faqRecyclerView = view.findViewById(R.id.questionsList);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        faqAdapter = new FAQAdapter(view.getContext());
        faqRecyclerView.setAdapter(faqAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveFAQ();
    }

    private void retrieveFAQ() {

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Naslov 1",
                "Nulla et sem pharetra, laoreet dolor ac, efficitur sapien. Aliquam mattis enim sit amet viverra vehicula. In hac habitasse platea dictumst?",
                "Vivamus accumsan dolor a faucibus placerat. Nulla finibus tempor massa, quis lacinia nunc egestas sit amet. Nulla bibendum ligula risus."));
        questions.add(new Question("Naslov 2",
                "Pellentesque ante quam, vehicula at odio pharetra, aliquam lobortis nisi. Vestibulum hendrerit fringilla porttitor.",
                "Vestibulum hendrerit elit eu turpis ultrices malesuada. Fusce placerat gravida metus et hendrerit."));
        questions.add(new Question("Naslov 3",
                "Mauris laoreet est a arcu luctus faucibus. In placerat mattis velit, eu tempus eros vulputate et. Morbi non eros nec quam iaculis vehicula.",
                "Donec vel egestas nibh, sed hendrerit sapien. Nullam ut mattis arcu, ac tempus elit. Aenean nibh sapien, egestas id sapien in, iaculis vestibulum massa."));

        faqAdapter.setTasks(questions);

    }

}