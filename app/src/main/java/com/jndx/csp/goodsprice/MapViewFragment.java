package com.jndx.csp.goodsprice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by jszx on 2018/10/18.
 */
public class MapViewFragment extends Fragment {
    @Nullable
    MapView mMapView=null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //String name = this.getArguments().getString("title");
        View contentView = inflater.inflate(R.layout.fragment_map_view, container, false);
        mMapView = (MapView)contentView.findViewById(R.id.bmapView);

        //修改百度地图的初始位置
        BaiduMap mBaidumap = mMapView.getMap();

        LatLng cenpt = new LatLng(22.2559,113.541112);//设定中心点坐标

        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
            .target(cenpt).zoom(17).build();//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaidumap.setMapStatus(mMapStatusUpdate);//改变地图状态

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.a1);
        //准备 marker option 添加 marker 使用
        MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(cenpt);
        //获取添加的 marker 这样便于后续的操作
        Marker marker = (Marker) mBaidumap.addOverlay(markerOption);

        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50).fontColor(0xFFFF00FF).text("百度地图SDK").rotate(0).position(cenpt);
        mBaidumap.addOverlay(textOption);

        mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return contentView;
        //return webView;
    }
    public void MDestroy() {
        if(mMapView!=null)mMapView.onDestroy();
    }
    public void MResume() {
        if(mMapView!=null)mMapView.onResume();
    }
    public void MPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mMapView!=null)mMapView.onPause();
    }
}
