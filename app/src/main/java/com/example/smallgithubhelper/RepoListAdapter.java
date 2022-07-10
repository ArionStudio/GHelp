package com.example.smallgithubhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter class for Recycle View, manage putting content and init view
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {
    private ArrayList<RepoCard> repoCards = new ArrayList<>();
    final private Context context;
    final private String username;

    public RepoListAdapter(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Set field data
        holder.name.setText(String.format("Repository: %s", repoCards.get(position).getName()));
        holder.type.setText(String.format("Visibility: %s", repoCards.get(position).getType()));
        holder.desc.setText(String.format("%s", (repoCards.get(position).getDesc().equals("null") ? "" : repoCards.get(position).getDesc())));
        holder.lang.setText(String.format("Lang: %s", (!repoCards.get(position).getLang().equals("null") ? repoCards.get(position).getLang() : "Unspecified")));
        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(context, Repo.class);
            intent.putExtra("Reponame", repoCards.get(position).getName());
            intent.putExtra("Username", username);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return repoCards.size();
    }

    public void setRepoCards(ArrayList<RepoCard> repoCards) {
        this.repoCards = repoCards;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final private TextView name, type, desc, lang;
        final private ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.txtRepoName);
            type = itemView.findViewById(R.id.txtType);
            desc = itemView.findViewById(R.id.txtDesc);
            lang = itemView.findViewById(R.id.txtLang);
        }
    }
}
