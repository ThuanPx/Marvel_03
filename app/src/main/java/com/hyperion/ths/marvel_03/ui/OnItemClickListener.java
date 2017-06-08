package com.hyperion.ths.marvel_03.ui;

/**
 * Created by ths on 07/06/2017.
 */

import com.hyperion.ths.marvel_03.data.model.Hero;

/**
 * Listener click button favorite
 */
public interface OnItemClickListener
        extends BaseRecyclerView.OnRecyclerViewItemClickListener<Hero> {
    void onItemFavoriteClick(Hero hero);
}
