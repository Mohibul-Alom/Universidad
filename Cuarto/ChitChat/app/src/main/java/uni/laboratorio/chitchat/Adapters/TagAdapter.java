package uni.laboratorio.chitchat.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.protobuf.StringValue;

import java.util.List;

import uni.laboratorio.chitchat.R;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mTags;
    private List<String> mTagsCount;


    public TagAdapter(Context mContext, List<String> mTags, List<String> mTagsCount) {
        this.mContext = mContext;
        this.mTags = mTags;
        this.mTagsCount = mTagsCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item,parent,false);
        return new TagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tag.setText(String.valueOf(mTags.get(position)));
        holder.noOfPosts.setText(String.valueOf(mTagsCount.get(position)));
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //estaba en public
        public TextView tag;
        public TextView noOfPosts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = (TextView)itemView.findViewById(R.id.hashtag);
            noOfPosts = (TextView)itemView.findViewById(R.id.no_of_posts);
        }
    }

    public void filtrar(List<String>filtrarTags,List<String>filtroTagsCount){
        this.mTags = filtrarTags;
        this.mTagsCount = filtroTagsCount;

        notifyDataSetChanged();
    }

}
