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
import tickets.common.Cities;

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
        mViewWidth = w - xPad;
        mViewHeight = h - yPad;

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
            String citySelected = mMapClickHandler.onClick(event.getX(), event.getY());
            String msg = "response is " + citySelected;
            Log.d("MapClickListener", msg);
            return true;
        }
    }


    // Handles how to map responds to clicks; this
    //  defines locations for areas of interest within the map
    //  in terms of the image's bitmap and the conversion
    //  between on-screen pixels and bits in the image bitmap
    private static class MapClickHandler {
        // no valid area clicked
        final String IGNORE = "No city clicked";

        // size of the bitmap
        int mapWidth;
        int mapHeight;

        // size conversion to the size of the view
        float widthRatio;
        float heightRatio;

        MapClickHandler(int bitmapX, int bitmapY) {
            mapWidth = bitmapX;
            mapHeight = bitmapY;
        }

        void setDrawRatios(int viewX, int viewY) {
            widthRatio = (float) mapWidth / (float) viewX;
            heightRatio = (float) mapHeight / (float) viewY;
            return;
        }

        String onClick(float xP, float yP) {
            //Convert a point (x, y) in pixels to within the Bitmap
            int x = getX(xP);
            int y = getY(yP);
            if (x == -1 || y == -1) {
                return IGNORE;
            }
            MapPoints selected = findClosest(x, y);
//            Log.d("City clicked", "city code: " + selected.getCode());
            if (selected == null) return IGNORE;
            else return selected.getName();
        }

        // return the city closest to the given location, or null
        //  if no city is close enough (within 100)
        private MapPoints findClosest(int x, int y) {
            MapPoints current = MapPoints.Jaqualind;
            current = compareDistance(current, MapPoints.Lin, x, y);
            current = compareDistance(current, MapPoints.Kiflamar, x, y);
            current = compareDistance(current, MapPoints.Stratus, x, y);
            current = compareDistance(current, MapPoints.Alpha_Lyrae, x, y);
            current = compareDistance(current, MapPoints.Verdona, x, y);
            current = compareDistance(current, MapPoints.Zee_A_Tll, x, y);
            current = compareDistance(current, MapPoints.Magmarse, x, y);
            current = compareDistance(current, MapPoints.Bynodia, x, y);
            current = compareDistance(current, MapPoints.Aeuoni, x, y);
            current = compareDistance(current, MapPoints.Aeontacht, x, y);
            current = compareDistance(current, MapPoints.Boisey, x, y);
            current = compareDistance(current, MapPoints.Nonnog, x, y);
            current = compareDistance(current, MapPoints.Kerrectice, x, y);
            current = compareDistance(current, MapPoints.Kita_Sota, x, y);
            current = compareDistance(current, MapPoints.Ico_Col, x, y);
            current = compareDistance(current, MapPoints.Igio, x, y);
            current = compareDistance(current, MapPoints.Fractine, x, y);
            current = compareDistance(current, MapPoints.Warfeld, x, y);
            current = compareDistance(current, MapPoints.Little_Rock, x, y);
            current = compareDistance(current, MapPoints.Zeroph, x, y);
            current = compareDistance(current, MapPoints.Orthok, x, y);
            current = compareDistance(current, MapPoints.Paradus, x, y);
            current = compareDistance(current, MapPoints.Ayon, x, y);
            current = compareDistance(current, MapPoints.Exen, x, y);
            current = compareDistance(current, MapPoints.Astern, x, y);
            current = compareDistance(current, MapPoints.Wence, x, y);
            current = compareDistance(current, MapPoints.Spheras, x, y);
            current = compareDistance(current, MapPoints.Petraqa, x, y);
            current = compareDistance(current, MapPoints.Kalishen, x, y);
            current = compareDistance(current, MapPoints.Darkrim, x, y);
            current = compareDistance(current, MapPoints.Castine, x, y);
            current = compareDistance(current, MapPoints.Dallaman, x, y);
            current = compareDistance(current, MapPoints.Brytis, x, y);
            current = compareDistance(current, MapPoints.Crepusculon, x, y);
            current = compareDistance(current, MapPoints.Altiere, x, y);

            if (current.getDistance(x, y) > 100) return null;
            else return current;
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
                return -1;
            }
            return ymap;
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
}