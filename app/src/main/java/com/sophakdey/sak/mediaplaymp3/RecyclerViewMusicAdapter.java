package com.sophakdey.sak.mediaplaymp3;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerViewMusicAdapter extends RecyclerView.Adapter<RecyclerViewMusicAdapter.ViewHolder>{

    /*
    * TODO Entities
    */
    protected AdapterListener adapterListener;
    private List<Song> songList;

    public RecyclerViewMusicAdapter(List<Song> songList){
        this.songList = songList;
    }

    public void setAdapterListener(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    public RecyclerViewMusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewMusicAdapter.ViewHolder holder, int position) {
        Log.d("DATABASE ","POSITION "+position);
        Song songGetFromMobile = songList.get(position);
        holder.txtTitle.setText(songGetFromMobile.getTitle());
//        holder.txtPath.setText(songGetFromMobile.getSongDuration());
        holder.txtAlbums.setText(songGetFromMobile.getArtist());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtPath ;
        private TextView txtAlbums;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle =(TextView) itemView.findViewById(R.id.textName);
//            txtPath =(TextView) itemView.findViewById(R.id.textsongDuration);
            txtAlbums =(TextView)itemView.findViewById(R.id.textAlbums);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int  position = getAdapterPosition();
                    if (adapterListener != null)
                    {
                        adapterListener.onItemClick(position);
                    }
                    else
                    {
                        Log.d(TAG, "= ERROR");
                    }
                }
            });
        }
    }
}
