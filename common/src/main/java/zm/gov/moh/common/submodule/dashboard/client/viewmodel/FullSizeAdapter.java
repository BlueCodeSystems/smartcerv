package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import zm.gov.moh.common.R;

public class FullSizeAdapter extends PagerAdapter
{
    Context context;
    String[] images;
    LayoutInflater inflater;

    public FullSizeAdapter (Context context, String[] images)
    {
        this.context = context;
        this.images = images;
    }
    @Override
    public int getCount()
    {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.full_item,null);

        ImageView imageView = (ImageView)v.findViewById(R.id.img);

        Glide.with(context).load(images[position]).apply(new RequestOptions().centerInside())
                .into(imageView);

        ViewPager vp = (ViewPager)container;
        vp.addView(v,0);
        return v;
        //return super.instantiateItem(container, position);
    }   

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);

        ViewPager viewPager = (ViewPager)container;
        View v = (View)object;
        viewPager.removeView(v);
    }
}
