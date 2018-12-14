package com.jndx.csp.goodsprice;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jndx.csp.goodsprice.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

public class GoodsPriceActivity extends ContextWrapper {

    private static final int REQUEST_CODE_ADD_ITEM = 10;
    private ArrayList<Goods> goodsCollection;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        menu.add(0, 1, 0, "添加");
        menu.add(0, 2, 0, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                Intent intent=new Intent(GoodsPriceActivity.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","胡萝卜");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
                break;
            case 2:
                theListAdapter.removeItem(itemInfo.position);
                theListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(ArrayList<Goods> goodsCollection) {
            itemViews = new ArrayList<View>(goodsCollection.size());

            for (int i=0; i<goodsCollection.size(); ++i){
                itemViews.add(makeItemView(goodsCollection.get(i).getName()
                        ,goodsCollection.get(i).getPrice()+"元 "+goodsCollection.get(i).getDay()
                        ,goodsCollection.get(i).getPictureId())
                );
            }

        }

        public void addItem(String itemTitle){
            Goods goods=new Goods();
            goods.setName(itemTitle);
            goods.setDay(new Date());
            goods.setPictureId(R.drawable.a1);
            goods.setPrice(1);
            goodsCollection.add(goods);
            GoodsCollectionOperater operater=new GoodsCollectionOperater();
            operater.save(GoodsPriceActivity.this.getBaseContext(),goodsCollection);

            View view=makeItemView(itemTitle
                    ,goods.getPrice()+"元 "+goods.getDay()
                    ,goods.getPictureId());
            itemViews.add(view);
        }
        public void removeItem(int positon){
            itemViews.remove(positon);
            goodsCollection.remove(positon);
            GoodsCollectionOperater operater=new GoodsCollectionOperater();
            operater.save(GoodsPriceActivity.this.getBaseContext(),goodsCollection);
        }
        public int getCount() {
            return itemViews.size();
        }

        public View getItem(int position) {
            return itemViews.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater)GoodsPriceActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.list_view_item_goods_price, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView)itemView.findViewById(R.id.itemTitle);
            title.setText(strTitle);
            TextView text = (TextView)itemView.findViewById(R.id.itemText);
            text.setText(strText);
            ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);
            image.setImageResource(resId);

            return itemView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if (convertView == null)
                return itemViews.get(position);
            //return convertView;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if (resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    theListAdapter.addItem(name);
                    theListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
    ListViewAdapter theListAdapter;

    private TabLayout tablayout;
    private ViewPager viewPager;
    //数据源
    private String[] titles = {"地图", "物价", "新闻"};
    //View listViewContainer;
    //ListView listViewGoodsPrice;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_price);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsPriceActivity.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","胡萝卜");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
            }
        });
       GoodsCollectionOperater operater=new GoodsCollectionOperater();
       goodsCollection=operater.load(getBaseContext());
       if(goodsCollection==null) {
           goodsCollection = new ArrayList<Goods>();
       }
       Goods good=new Goods();
       good.setName("x");
       good.setPictureId(R.drawable.a2);
       good.setDay(new Date());
       good.setPrice(1);
       goodsCollection.add(good);
       theListAdapter= new ListViewAdapter(goodsCollection);


       LayoutInflater inflater = (LayoutInflater)GoodsPriceActivity.this
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View itemView = inflater.inflate(R.layout.content_goods_price, null);
       /*

       //listViewContainer=itemView;
       //listViewGoodsPrice= (ListView) itemView.findViewById(R.id.listwiew_goods_price);
        //theListAdapter=new ListViewAdapter(titles,texts,resIds);
       theListAdapter= new ListViewAdapter(goodsCollection);

       listViewGoodsPrice.setAdapter(theListAdapter);
       listViewGoodsPrice.setOnItemClickListener(new mListViewItemClick2());

        registerForContextMenu(listViewGoodsPrice);
        */
        /*String[] data={
                "(1)荷塘月色"
                ,"(2)荷塘月色"
                ,"(3)荷塘月色"
                ,"(4)荷塘月色"
                ,"(5)荷塘月色"
        };
        listViewGoodsPrice.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
        listViewGoodsPrice.setOnItemClickListener(new mListViewItemClick());
        */

       tablayout = (TabLayout) findViewById(R.id.tablayout);
       viewPager = (ViewPager) findViewById(R.id.viewpager);

       MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

       viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
       viewPager.setAdapter(myPagerAdapter);

       tablayout.setupWithViewPager(viewPager);

       //每条之间的分割线
       LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);

       linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
/*
       linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
               R.drawable.a1));*/
    }

    private MapViewFragment mapViewFragment=null;
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
            {
                mapViewFragment= new MapViewFragment();
                return mapViewFragment;
            }
            if(position==1)
            {
                ListViewFragment fragment= new ListViewFragment(theListAdapter);
                return fragment;
            }
            else {
                WebViewFragment webViewFragment = new WebViewFragment();
                return webViewFragment;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    class mListViewItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(GoodsPriceActivity.this
                    ,"您选择的项目是："+((TextView)view).getText()
                    , Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MPause();
    }
}
