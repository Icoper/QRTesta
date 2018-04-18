package com.example.android_dev.qrtest.ui.adapter.mediaAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.example.android_dev.qrtest.R;
import com.example.android_dev.qrtest.model.AssetTypes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DocTypeAdapter extends RecyclerView.Adapter<DocTypeAdapter.DocTypeViewHolder> {
    private final static String LOG_TAG = "DocTypeAdapter";
    private List<AssetTypes> assetTypes;
    private String resFolderFilePath;

    public DocTypeAdapter(List<AssetTypes> assetTypes, String resFolderFilePath) {
        this.resFolderFilePath = resFolderFilePath;
        this.assetTypes = assetTypes;
    }

    @Override
    public DocTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder is called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doc_type_list,
                parent, false);
        return new DocTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DocTypeViewHolder holder, int position) {
        holder.title.setText(assetTypes.get(position).getFileName());
        String filepath = resFolderFilePath + assetTypes.get(position).getFileName();
        String text = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String line = bufferedReader.readLine();
            while (line != null) {
                text += line;
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.documentView.setText(text);
    }

    @Override
    public int getItemCount() {
        return assetTypes.size();
    }

    class DocTypeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        DocumentView documentView;
        TextView text;

        public DocTypeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.idtl_doc_title);
            documentView = (DocumentView) itemView.findViewById(R.id.idtl_doc_text);
//            text = (TextView) itemView.findViewById(R.id.idtl_doc_text);
        }
    }
}
