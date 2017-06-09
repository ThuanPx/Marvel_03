package com.hyperion.ths.marvel_03.ui.heroinfo;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.hyperion.ths.marvel_03.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ths on 09/06/2017.
 */

public class HeroInfoViewPageAdapter extends PagerAdapter {
    private Context mContext;
    private String[] mTabIcon = new String[] {
            "/standard_fantastic.jpg", "/portrait_xlarge.jpg", "/standard_xlarge.jpg",
            "/landscape_xlarge.jpg"
    };
    private LayoutInflater inflater;
    private String mImageName;

    public HeroInfoViewPageAdapter(Context context, String imageName) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mImageName = imageName;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.custom_sliding_images, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_viewpage);

        Picasso.with(mContext).load(mImageName + mTabIcon[position]).into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
