package com.grameenphone.hello.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grameenphone.hello.Fragments.Fragment_MainPage;
import com.grameenphone.hello.R;
import com.grameenphone.hello.Utils.DateTimeUtility;
import com.grameenphone.hello.model.ChatRoom;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class RoomListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<ChatRoom> rooms = new ArrayList<ChatRoom>();

    private LayoutInflater inflater;
    private Fragment_MainPage fragment_mainPage;

    public RoomListAdapter(Context context, ArrayList<ChatRoom> rooms, Fragment_MainPage fragment_mainPage) {
        this.context = context;
        this.rooms = rooms;
        this.fragment_mainPage = fragment_mainPage;

    }

    public void clear() {
        int size = rooms.size();
        rooms.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.item_friend, parent, false);
        final ChatRoomHolder holder = new ChatRoomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ChatRoomHolder itemHolder = (ChatRoomHolder) holder;
        final ChatRoom current = rooms.get(position);


        itemHolder.nameTextView.setText(current.getName());
        String lilname = current.getName().trim().split("\\s+")[0];



        if(current.getLastChat() != null){
            itemHolder.message.setText(current.getLastChat());
        } else {
            itemHolder.message.setText(lilname + "-কে হ্যালো বলুন");
        }

        if(current.getTimestamp() != 0){
            itemHolder.timeStamp.setText( DateTimeUtility.getFormattedTimeFromTimestamp( current.getTimestamp() ) );
        }






        if (current.getPhotoUrl() != null) {

            Glide.with(itemHolder.roomImage.getContext()).load(current.getPhotoUrl()).bitmapTransform(new CropCircleTransformation(context))
                    .placeholder(R.drawable.hello1)
                    .into(itemHolder.roomImage);

        } else {
            itemHolder.roomImage.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.hello1));
        }


        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (fragment_mainPage).StartP2p(current.getRoomId(), current.getName());


            }
        });

    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }


    //return the filter class object

    public void refresh() {
        //manipulate list
        notifyDataSetChanged();

    }

    public int refreshfromswipe() {
        //manipulate list
        notifyDataSetChanged();
        return 1;
    }

    private class ChatRoomHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView message;
        private TextView unReadMessageCount;
        private TextView timeStamp;
        private ImageView roomImage;
        private ImageView deliveryStatus;

        private ChatRoomHolder(View v) {
            super(v);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            message = (TextView) itemView.findViewById(R.id.un_read_messaage);
            unReadMessageCount = (TextView) itemView.findViewById(R.id.un_read_message_count);
            timeStamp = (TextView) itemView.findViewById(R.id.time_stamp_un_read_message);
            roomImage = (ImageView) itemView.findViewById(R.id.friendImageView);


            deliveryStatus = (ImageView) itemView.findViewById(R.id.delivery_status);
            deliveryStatus.setVisibility(View.GONE);
        }
    }

}
