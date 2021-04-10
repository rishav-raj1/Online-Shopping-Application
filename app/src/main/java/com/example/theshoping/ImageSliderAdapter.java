package com.example.theshoping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {

    private Context context;

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        //viewHolder.textViewDescription.setText("This is slider item " + position);

        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c1)
                        .into(viewHolder.imageView);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c8)
                        .into(viewHolder.imageView);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c2)
                        .into(viewHolder.imageView);
                break;

           /* case 3:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c4)
                        .into(viewHolder.imageView);
                break;
            case 4:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c5)
                        .into(viewHolder.imageView);
                break;
            case 5:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c6)
                        .into(viewHolder.imageView);
                break;
            case 6:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c7)
                        .into(viewHolder.imageView);
                break;

            case 7:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.c8)
                        .into(viewHolder.imageView);
                break;

           /* case 8:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.p8)
                        .into(viewHolder.imageView);
                break;

           /* case 9:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.p9)
                        .into(viewHolder.imageView);
                break;

            case 10:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.p10)
                        .into(viewHolder.imageView);
                break;
           /* case 11:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.games)
                        .into(viewHolder.imageView);
                break;
            case 12:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.message)
                        .into(viewHolder.imageView);
                break;
            case 13:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.rishav1)
                        .into(viewHolder.imageView);*/


            default:

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 3;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageView;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
        }
    }
}
