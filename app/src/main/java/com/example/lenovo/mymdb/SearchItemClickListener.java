package com.example.lenovo.mymdb;

import android.view.View;

interface SearchItemClickListener {
    void movieResultItemClicked (View view, int position);
    void tvResultItemClicked (View view, int position);
    void personResultItemClicked (View view, int position);
}
