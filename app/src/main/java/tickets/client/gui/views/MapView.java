package tickets.client.gui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

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

    public MapView(Context context, IGameMapPresenter presenter) {
        super(context);
        this.presenter = presenter;
        init();
    }

    public MapView(Context context, AttributeSet attrs, IGameMapPresenter presenter) {
        super(context, attrs);
        this.presenter = presenter;
        init();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr, IGameMapPresenter presenter) {
        super(context, attrs, defStyleAttr);
        this.presenter = presenter;
        init();
    }

    private void init() {
        // load the map image into this view's bitmap
        mGameMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.map);
        if (mGameMap == null) {
            throw new RuntimeException("the map was not loaded into the bitmap!");
        }
        mMapClickHandler = new MapClickHandler(mGameMap.getWidth(), mGameMap.getHeight());

        // set the gesture detector - it will handle all touch events
        mDetector = new GestureDetector(this.getContext(), new MapView.MapClickListener());

        return;
    }


    //--------------------------------------------------------------------------------
    //  Android callbacks

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        // calculate the size allocated to this view
        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();
        mViewWidth = getMeasuredWidth() - xPad;
        mViewHeight = getMeasuredHeight() - yPad;
        Log.e("onSizeChanged", "view size:" + mViewWidth + ", " + mViewHeight);
        Log.e("onSizeChanged", "bitmap size:" + mGameMap.getWidth() + ", " + mGameMap.getHeight());
        mMapClickHandler.setDrawRatios(mViewWidth, mViewHeight);
        return;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the map to the screen, stretching the image to fill the screen area allocated to this view
        canvas.drawBitmap(mGameMap, null, new Rect(0, 0, mViewWidth, mViewHeight),
                new Paint(Paint.FILTER_BITMAP_FLAG & Paint.ANTI_ALIAS_FLAG));
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
//        public boolean onContextClick(MotionEvent e)
//        public boolean onDoubleTap(MotionEvent e)
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
//        public boolean onDoubleTapEvent(MotionEvent e)
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
//        public boolean onSingleTapUp(MotionEvent e)
//        public void onLongPress(MotionEvent e)
//        public void onShowPress(MotionEvent e)

        @Override
        public boolean onDown(MotionEvent event) {
            // default returns false, disregarding all further events related to this action
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            mMapClickHandler.onClick(event.getX(), event.getY());
            return true;
        }
    }


    // Handles how to map responds to clicks; this
    //  defines locations for areas of interest within the map
    //  in terms of the image's bitmap and the conversion
    //  between on-screen pixels and bits in the image bitmap
    private class MapClickHandler {

        // TODO: find a way for handler to use presenter
        // no valid area clicked
        final String IGNORE = "No city clicked";

        // size of the bitmap
        int mapWidth;
        int mapHeight;

        // size conversion to the size of the view
        float widthRatio;
        float heightRatio;

        // keep track of selected cities
        // TODO: add something to canvas to add highlight the selected cities
        String city1;
        String city2;

        MapClickHandler(int bitmapX, int bitmapY) {
            mapWidth = bitmapX;
            mapHeight = bitmapY;
        }

        void setDrawRatios(int viewX, int viewY) {
            widthRatio = (float) mapWidth / (float) viewX;
            heightRatio = (float) mapHeight / (float) viewY;
            Log.e("setDrawRatios", "ratios set:" + widthRatio + ", " + heightRatio);
            return;
        }

        void onClick(float xP, float yP) {
            // Convert a point (x, y) in pixels to within the Bitmap
            Log.e("onClick", "point clicked:" + xP + ", " + yP);
            int x = getX(xP);
            int y = getY(yP);
            if (x == -1 || y == -1)
                return;

            // Find what city (map point) is closest to the click
            Log.e("onClick", "map location calculated:" + x + ", " + y);
            MapPoints selected = findClosest(x, y);
            Log.d("City clicked", "city code: " + selected.getName());

            if (selected == null) {
                city1 = null;
                city2 = null;
            }

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
                if (route.equals(city1, city2))
                    presenter.claimRoute(route);
            }

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
                return null;
            else
                return current;
        }

        // TODO: changing target isn't actually changing the caller's value, find a way to fix this
        MapPoints compareDistance(MapPoints current, MapPoints next, int x, int y) {
            int currentDistance = current.getDistance(x, y);
            int nextDistance = next.getDistance(x, y);
            if (next.getDistance(x, y) < currentDistance) {
                return next;
            } else return current;
        }

        // Returns -1 if the given x value is incorrect.
        //   Otherwise, returns the bitmap's x index
        //   corresponding to the specified pixel value
        private int getX(float x) {
            if (x < 0) {
                return -1;
            }
            int xmap = (int) (x * widthRatio);
            if (xmap > mapWidth) {
                Log.e("getX", "x value outside of map");
                return -1;
            }
            return xmap;
        }

        // Returns -1 if the given y value is incorrect.
        //   Otherwise, returns the bitmap's y index
        //   corresponding to the specified pixel value
        private int getY(float y) {
            if (y < 0) {
                return -1;
            }
            int ymap = (int) (y * heightRatio);
            if (ymap > mapHeight) {
                Log.e("getY", "y value outside of map");
                return -1;
            }
            return ymap;
        }
    }

    // Cities and their locations on the map
    //  in terms of pixel coordinates on the actual image
    //  (which is the same as bit coordinates in the
    //   untransformed bitmap)
    private enum MapPoints {
        Jaqualind(Cities.SAN_FRANCISCO, 132, 581),
        Lin(Cities.LOS_ANGELES, 255, 891),
        Kiflamar(Cities.SALT_LAKE_CITY, 382, 520),
        Stratus(Cities.PORTLAND, 327, 311),
        Alpha_Lyrae(Cities.LAS_VEGAS, 309, 671),
        Verdona(Cities.VANCOUVER, 437, 125),
        Zee_A_Tll(Cities.SEATTLE, 489, 25),
        Magmarse(Cities.EL_PASO, 566, 923),
        Bynodia(Cities.DALLAS, 779, 877),
        Aeuoni(Cities.OKLAHOMA_CITY, 697, 732),
        Aeontacht(Cities.DENVER, 588, 46),
        Boisey(Cities.HELENA, 666, 234),
        Nonnog(Cities.CALGARY, 666, 54),
        Kerrectice(Cities.WINNIPEG, 875, 123),
        Kita_Sota(Cities.OMAHA, 794, 327),
        Ico_Col(Cities.KANSAS_CITY, 736, 499),
        Igio(Cities.PHOENIX, 377, 762),
        Fractine(Cities.SANTA_FE, 502, 711),
        Warfeld(Cities.HOUSTON, 862, 973),
        Little_Rock(Cities.LITTLE_ROCK, 855, 761),
        Zeroph(Cities.SAINT_LOUIS, 888, 562),
        Orthok(Cities.CHICAGO, 920, 427),
        Paradus(Cities.DULUTH, 971, 271),
        Ayon(Cities.SAULT_ST_MARIE, 1065, 174),
        Exen(Cities.MONTREAL, 1322, 144),
        Astern(Cities.TORONTO, 1202, 258),
        Wence(Cities.BOSTON, 1428, 253),
        Spheras(Cities.NEW_YORK, 1325, 348),
        Petraqa(Cities.PITTSBURG, 1132, 420),
        Kalishen(Cities.WASHINGTON, 1347, 518),
        Darkrim(Cities.RALEIGH, 1161, 602),
        Castine(Cities.NASHVILLE, 1003, 656),
        Dallaman(Cities.ATLANTA, 1093, 771),
        Brytis(Cities.CHARLESTON, 1269, 745),
        Crepusculon(Cities.NEW_ORLEANS, 980, 903),
        Altiere(Cities.MIAMI, 1231, 957);

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