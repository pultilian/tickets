package tickets.client.gui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.IGameMapPresenter;
import tickets.common.Cities;
import tickets.common.Route;

/**
 * Created by Howl on 3/24/18.
 */

public class MapView extends View {

    // bitmap of the game's map image, and a handler to define clickable areas
    Bitmap mGameMap;
    MapClickHandler mMapClickHandler;

    // view dimensions
    int mViewWidth;
    int mViewHeight;

    // gesture detector for registering clicks on the image
    GestureDetector mDetector;
    IGameMapPresenter presenter;

    //--------------------------------------------------------------------------------
    //  constructors and setup functions

    public MapView(Context context) {
        super(context);
        init();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPresenter(IGameMapPresenter presenter) {
        this.presenter = presenter;
    }

    private void init() {
        // load the map image into this view's bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inDensity = 100;

        mGameMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.map, options);
        if (mGameMap == null) {
            throw new RuntimeException("the map was not loaded into the bitmap!");
        }

        mMapClickHandler = new MapClickHandler();
        // set the gesture detector - it will handle all touch events
        mDetector = new GestureDetector(this.getContext(), new MapView.MapClickListener());
        return;
    }


    //--------------------------------------------------------------------------------
    //  Android callbacks

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        // width and height allocated to this view
        //   it's easier to just ignore the possibility of padding
        mViewWidth = w;
        mViewHeight = h;

        // scale the bitmap to the view size - this changes actual bitmap to the size of the view
        mGameMap = Bitmap.createScaledBitmap(mGameMap, mViewWidth, mViewHeight, false);
        return;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the pre-scaled map to the view
        canvas.drawBitmap(mGameMap, new Matrix(), new Paint());
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }


    //--------------------------------------------------------------------------------
    //  view functionality

    // defines how this view will respond to touch events based on which gesture is performed
    private class MapClickListener extends GestureDetector.SimpleOnGestureListener {
        // other gestures that could be detected:
//        public boolean onDoubleTapEvent(MotionEvent e)
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
//        public boolean onSingleTapUp(MotionEvent e)
//        public void onShowPress(MotionEvent e)

        @Override
        public boolean onDown(MotionEvent event) {
            // default returns false, disregarding all further events related to this action
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            mMapClickHandler.onClick((int)event.getX(), (int)event.getY());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(MapView.this.getContext(), "Claiming: " + mMapClickHandler.getCities() , Toast.LENGTH_SHORT).show();
            mMapClickHandler.onFling();
            return true;
        }

        // For debugging, use right click or long press on emulator instead of trying to fling
        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(MapView.this.getContext(), "Claiming: " + mMapClickHandler.getCities() , Toast.LENGTH_SHORT).show();
            mMapClickHandler.onFling();
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Toast.makeText(MapView.this.getContext(), "Claiming: " + mMapClickHandler.getCities() , Toast.LENGTH_SHORT).show();
            mMapClickHandler.onFling();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mMapClickHandler.onDoubleClick((int)e.getX(), (int)e.getY());
            Toast.makeText(MapView.this.getContext(), "locations cleared", Toast.LENGTH_SHORT).show();
            return true;
        }


    }


    // Handles how to map responds to clicks; this
    //  defines locations for areas of interest within the map
    //  in terms of the image's bitmap and the conversion
    //  between on-screen pixels and bits in the image bitmap
    private class MapClickHandler {

        // keep track of selected cities
        // TODO: add something to canvas to add highlight the selected cities
        String city1;
        String city2;

        String getCities() {
            String val = "";
            if (city1.equals(null)) val += "null";
            else val += city1;
            if (city2.equals(null)) val += "null";
            else val += city2;
            return val;
        }

        void onClick(int viewX, int viewY) {
            MapPoints selected = findClosest(viewX, viewY);
            Toast.makeText(MapView.this.getContext(), selected.getName(), Toast.LENGTH_SHORT).show();

            if (selected == MapPoints.No_city)
                return;

            String city = selected.getName();
            if(city1 == null)
                city1 = city;
            else
                city2 = city;
        }

        //TODO: add button to canvas for this function
        void clearSelected() {
            city1 = null;
            city2 = null;
        }

        //TODO: add button to canvas for this function
        void claimSelectedRoute() {
            for (Route route : presenter.getAllRoutes()) {
                if (route.equals(city1, city2)) {
                    presenter.claimRoute(route);
                    clearSelected();
                    return;
                }
            }

            Toast.makeText(MapView.this.getContext(), "The cities you have selected are not adjacent", Toast.LENGTH_LONG).show();
            clearSelected();
        }

        // return the city closest to the given location, or null
        //  if no city is close enough (within 100)
        private MapPoints findClosest(int x, int y) {
            MapPoints current = MapPoints.Jaqualind;
            for (MapPoints point : MapPoints.values()) {
                current = compareDistance(current, point, x, y);
            }

            if (current.getDistance(x, y) > 100)
                return MapPoints.No_city;
            else
                return current;
        }

        // TODO: changing target isn't actually changing the caller's value, find a way to fix this
        MapPoints compareDistance(MapPoints current, MapPoints next, int x, int y) {
            int currentDistance = current.getDistance(x, y);
            int nextDistance = next.getDistance(x, y);
            if (nextDistance < currentDistance) {
                return next;
            } else
                return current;
        }

        void onFling() {
            if(city1 != null && city2 != null)
                claimSelectedRoute();
        }

        public void onDoubleClick(int x, int y) {
            MapPoints selected = findClosest(x, y);
            if (selected == MapPoints.No_city)
                clearSelected();
        }
    }

    // Cities and their locations on the map
    //  in terms of pixel coordinates on the actual image
    //  (which is the same as bit coordinates in the
    //   untransformed bitmap)
    private enum MapPoints {
        No_city("invalid selection", -1, -1),
        Jaqualind(Cities.SAN_FRANCISCO, 94, 333),
        Lin(Cities.LOS_ANGELES, 188, 501),
        Kiflamar(Cities.SALT_LAKE_CITY, 284, 300),
        Stratus(Cities.PORTLAND, 240, 185),
        Alpha_Lyrae(Cities.LAS_VEGAS, 230, 380),
        Verdona(Cities.VANCOUVER, 325, 83),
        Zee_A_Tll(Cities.SEATTLE, 365, 153),
        Magmarse(Cities.EL_PASO, 425, 518),
        Bynodia(Cities.DALLAS, 590, 493),
        Aeuoni(Cities.OKLAHOMA_CITY, 528, 415),
        Aeontacht(Cities.DENVER, 441, 269),
        Boisey(Cities.HELENA, 544, 104),
        Nonnog(Cities.CALGARY, 503, 45),
        Kerrectice(Cities.WINNIPEG, 661, 83),
        Kita_Sota(Cities.OMAHA, 600, 192),
        Ico_Col(Cities.KANSAS_CITY, 557, 288),
        Igio(Cities.PHOENIX, 281, 430),
        Fractine(Cities.SANTA_FE, 377, 401),
        Warfeld(Cities.HOUSTON, 654, 545),
        Little_Rock(Cities.LITTLE_ROCK, 646, 431),
        Zeroph(Cities.SAINT_LOUIS, 674, 322),
        Orthok(Cities.CHICAGO, 697, 248),
        Paradus(Cities.DULUTH, 737, 163),
        Ayon(Cities.SAULT_ST_MARIE, 810, 109),
        Exen(Cities.MONTREAL, 1008, 95),
        Astern(Cities.TORONTO, 913, 157),
        Wence(Cities.BOSTON, 1090, 153),
        Spheras(Cities.NEW_YORK, 1010, 205),
        Petraqa(Cities.PITTSBURG, 862, 256),
        Kalishen(Cities.WASHINGTON, 1027, 299),
        Darkrim(Cities.RALEIGH, 885, 345),
        Castine(Cities.NASHVILLE, 759, 374),
        Dallaman(Cities.ATLANTA, 830, 436),
        Brytis(Cities.CHARLESTON, 967, 421),
        Crepusculon(Cities.NEW_ORLEANS, 744, 509),
        Altiere(Cities.MIAMI, 938, 537);

        private String name;
        private int x;
        private int y;

        MapPoints(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        String getName() {
            return name;
        }

        int getDistance(int x, int y) {
            int xDif = x - this.x;
            int yDif = y - this.y;
            return (int) Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
        }

    }
}