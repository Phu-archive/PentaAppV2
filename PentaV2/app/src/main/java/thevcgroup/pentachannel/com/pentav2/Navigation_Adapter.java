package thevcgroup.pentachannel.com.pentav2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Navigation_Adapter extends RecyclerView.Adapter<Navigation_Adapter.ViewHolder> {

    private ArrayList<TagQuery> tagQueryList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tagTextView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tagTextView = (TextView) itemView.findViewById(R.id.tag_name);
        }

        public TextView getTagTextView() {
            return tagTextView;
        }
    }



    public Navigation_Adapter(Context context, ArrayList<TagQuery> tag){
        tagQueryList = tag;
        mContext = context;
    }

    @Override
    public Navigation_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.adapter_navigation, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Navigation_Adapter.ViewHolder holder, int position) {
        TextView textView = holder.getTagTextView();
        textView.setText(tagQueryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tagQueryList.size();
    }
}
