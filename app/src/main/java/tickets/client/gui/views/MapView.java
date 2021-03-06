package tickets.client.gui.views;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.IGameMapPresenter;
import tickets.common.Cities;
import tickets.common.PlayerColor;
import tickets.common.Route;

/**
 * Created by Howl on 3/24/18.
 */

//1983 x 1566 - raw image dimensions

public class MapView extends View {

    // bitmap of the game's map image, and a handler to define clickable areas
    Bitmap mGameMap;
    MapClickHandler mMapClickHandler;
    List<Integer> mDrawnRoutes;

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
        mGameMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.map);
        if (mGameMap == null) {
            throw new RuntimeException("the map was not loaded into the bitmap!");
        }
        if (mGameMap.getWidth() != 1983 && mGameMap.getHeight() != 1566) {
            throw new RuntimeException("Map loaded with incorrect dimensions");
        }

        mDrawnRoutes = new ArrayList<>();
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
        Log.d("Drawing", "onDraw called");
        super.onDraw(canvas);
        // draw the pre-scaled map to the view
        canvas.drawBitmap(mGameMap, new Matrix(), new Paint());
        setClaimedRoutes(presenter.getClaimedRoutes());
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }


    //--------------------------------------------------------------------------------
    //  update the game map image (bitmap)


    public void setClaimedRoutes(List<Route> claimedRoutes) {
        boolean update = false;
        Log.d("Drawing", "calling setClaimedRoutes on MapView");

//        String routeString = "";
//        for (Route r : claimedRoutes) {
//            routeString += r.toString() + "\n";
//        }
//        Log.d("Drawing", routeString);
//        Toast msg = Toast.makeText(this.getContext(), "setting claimed routes", Toast.LENGTH_SHORT);
//        msg.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
//        msg.show();
        for(Route route : claimedRoutes) {
            int routeImg = getRouteResource(route);
            Log.d("Drawing", "image resource id: " + routeImg);
            if (! mDrawnRoutes.contains(routeImg)) {
                this.drawRoute(routeImg, route.getFirstOwner());
                mDrawnRoutes.add(routeImg);
                update = true;
            }
        }
        if (update) this.invalidate(); // signal Android to recall onDraw() for this view

        String drawn = "";
        for (int i : mDrawnRoutes) {
            drawn += i + "\n";
        }
        Log.d("Drawing", "stored resources: " + drawn);
        return;
    }


    public void drawRoute(int routeImageId, PlayerColor color) {
        Log.d("Drawing", "drawRoute called");
        Canvas canvas = new Canvas(mGameMap);

        // create the 32-bit color value
        int colVal = 0;
        switch(color) {
            case black: // 0x231F20
                colVal = 0xFF << 24 | 0x23 << 16 | 0x1F << 8 | 0x20;
                break;
            case yellow: // 0xFFF200
                colVal = 0xFF << 24 | 0xFF << 16 | 0xF2 << 8 | 0x00;
                break;
            case red: // 0xED1C24
                colVal = 0xFF << 24 | 0xED << 16 | 0x1C << 8 | 0x24;
                break;
            case green: // 0x00A651
                colVal = 0xFF << 24 | 0x00 << 16 | 0xA6 << 8 | 0x51;
                break;
            case blue: // 0x2E3192
                colVal = 0xFF << 24 | 0x2E << 16 | 0x31 << 8 | 0x92;
                break;
        }

        // get the image and scale it to the size of the screen
        Bitmap img = BitmapFactory.decodeResource(this.getResources(), routeImageId);
        img = Bitmap.createScaledBitmap(img, mViewWidth, mViewHeight, false);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(0, colVal);
        paint.setColorFilter(filter);

        // overlay the image on the game map
        canvas.drawBitmap(img, new Matrix(), paint);
        return;
    }


    public void markCity(MapPoints city, boolean selected) {
        if (city == MapPoints.No_city) return;

        invalidate();

        float scaleX = (float) (mViewWidth / 1983.0);
        float scaleY =(float) (mViewHeight / 1566.0);

        Canvas canvas = new Canvas(mGameMap);

        // Clip the canvas to just the circle that was selected
        Path circle = new Path();
        circle.addCircle((city.x * scaleX), (city.y * scaleY), (30 * Math.min(scaleX, scaleY)), Path.Direction.CW);
        canvas.clipPath(circle);
        Paint paint = new Paint();

        // Brighten selected cities
        if (selected) {
            ColorFilter filter = new LightingColorFilter(0xFFFFFFFF, 0x00666666);
            paint.setColorFilter(filter);
            canvas.drawBitmap(mGameMap, new Matrix(), paint);
        }
        // Bring unselected cities back to original
        else {
            Bitmap originalMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.map);
            Bitmap scaledMap = Bitmap.createScaledBitmap(originalMap, mViewWidth, mViewHeight, false);
            canvas.drawBitmap(scaledMap, new Matrix(), paint);
        }

        return;
    }


    //--------------------------------------------------------------------------------
    //  view functionality

    public void displayChooseColorDialog(List<String> colors) {
        mMapClickHandler.displayChooseColorDialog(colors);
    }

    public void clearSelectedCities() {
        mMapClickHandler.clearSelected();
    }

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
            mMapClickHandler.onFling();
            return true;
        }

        // For debugging, use right click or long press on emulator instead of trying to fling
        @Override
        public void onLongPress(MotionEvent e) {
            mMapClickHandler.onFling();
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
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
        MapPoints city1;
        MapPoints city2;

        String getCities() {
            String val = "";
            if (city1.equals(null)) val += "null";
            else val += city1.name;
            if (city2.equals(null)) val += "null";
            else val += city2.name;
            return val;
        }

        void onClick(int viewX, int viewY) {
            MapPoints selected = findClosest(viewX, viewY);
            Toast.makeText(MapView.this.getContext(), selected.getName(), Toast.LENGTH_SHORT).show();

            if (selected == MapPoints.No_city)
                return;

            if(city1 == null) {
                city1 = selected;
                markCity(city1, true);
            } else {
                if (city2 != null) markCity(city2, false);
                city2 = selected;
                markCity(city2, true);
            }
        }

        void clearSelected() {
            if (city1 != null) markCity(city1, false);
            if (city2 != null) markCity(city2, false);
            city1 = null;
            city2 = null;
        }

        void claimSelectedRoute() {
            for (Route route : presenter.getAllRoutes()) {
                if (route.equals(city1.name, city2.name)) {
                    presenter.claimRoute(route);
//                    clearSelected();
                    return;
                }
            }

            Toast.makeText(MapView.this.getContext(), "The cities you have selected are not adjacent", Toast.LENGTH_SHORT).show();
            clearSelected();
        }

        public void displayChooseColorDialog(List<String> colors) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose a color");

            // Set options for radio buttons
            final String[] options = colors.toArray(new String[colors.size()]);

            // Set onClickListener
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                private int selection = 0;

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        clearSelected();
                        dialog.dismiss();
                    }
                    else if (which == DialogInterface.BUTTON_POSITIVE) {
                        claimSelectedRouteWithColor(options[selection]);
                        dialog.dismiss();
                    }
                    else {
                        selection = which;
                    }
                }
            };
            builder.setSingleChoiceItems(options, 0, listener);
            builder.setPositiveButton("OK", listener);
            builder.setNegativeButton("Cancel", listener);

            builder.create();
            builder.show();
        }

        void claimSelectedRouteWithColor(String color) {
            for (Route route : presenter.getAllRoutes()) {
                if (route.equals(city1.name, city2.name)) {
                    presenter.claimRoute(route, color);
                    clearSelected();
                    return;
                }
            }
        }

        // return the city closest to the given location, or null
        //  if no city is close enough (within 100)
        private MapPoints findClosest(int x, int y) {
            Log.e("locations", "x, y click:" + x + ", " + y);
            //1983 x 1566 - raw image dimensions
            double xRatio = (double) 1983 / MapView.this.mViewWidth;
            double yRatio = (double) 1566 / MapView.this.mViewHeight;
            Log.e("locations", "ratios: " + xRatio + ", " + yRatio);
            x = (int) (x * xRatio);
            y = (int) (y * yRatio);
            Log.e("locations", "x, y calculated:" + x + ", " + y);
            MapPoints current = MapPoints.Jaqualind;
            for (MapPoints point : MapPoints.values()) {
                current = compareDistance(current, point, x, y);
            }

            if (current.getDistance(x, y) > 100)
                return MapPoints.No_city;
            else
                return current;
        }

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
        Jaqualind(Cities.SAN_FRANCISCO, 162, 819),
        //94, 333
        Lin(Cities.LOS_ANGELES, 328, 1230),
        //188, 501
        Kiflamar(Cities.SALT_LAKE_CITY, 494, 734),
        //284, 300
        Stratus(Cities.PORTLAND, 419, 455),
        // 240, 185
        Alpha_Lyrae(Cities.LAS_VEGAS, 398, 935),
        //230, 380
        Verdona(Cities.VANCOUVER, 568, 205),
        //325, 83
        Zee_A_Tll(Cities.SEATTLE, 636, 375),
        //365, 153
        Magmarse(Cities.EL_PASO, 741, 1275),
        //425, 518
        Bynodia(Cities.DALLAS, 1028, 1213),
        //590, 493
        Aeuoni(Cities.OKLAHOMA_CITY, 919, 1018),
        //528, 415
        Aeontacht(Cities.DENVER, 769, 663),
        //441, 269
        Boisey(Cities.HELENA, 878, 353),
        //544, 104
        Nonnog(Cities.CALGARY, 878, 108),
        //503, 45
        Kerrectice(Cities.WINNIPEG, 1156, 205),
        //661, 83
        Kita_Sota(Cities.OMAHA, 1047, 474),
        //600, 192
        Ico_Col(Cities.KANSAS_CITY, 972, 708),
        //557, 288
        Igio(Cities.PHOENIX, 488, 1057),
        //281, 430
        Fractine(Cities.SANTA_FE, 660, 993),
        //377, 401
        Warfeld(Cities.HOUSTON, 1140, 1340),
        //654, 545
        Little_Rock(Cities.LITTLE_ROCK, 1127, 1058),
        //646, 431
        Zeroph(Cities.SAINT_LOUIS, 1172, 788),
        //674, 322
        Orthok(Cities.CHICAGO, 1215, 610),
        //697, 248
        Paradus(Cities.DULUTH, 1285, 400),
        //737, 163
        Ayon(Cities.SAULT_ST_MARIE, 1408, 267),
        //810, 109
        Exen(Cities.MONTREAL, 1755, 229),
        //1008, 95
        Astern(Cities.TORONTO, 1592, 384),
        //913, 157
        Wence(Cities.BOSTON, 1899, 375),
        //1090, 153
        Spheras(Cities.NEW_YORK, 1762, 504),
        //1010, 205
        Petraqa(Cities.PITTSBURG, 1500, 600),
        //862, 256
        Kalishen(Cities.WASHINGTON, 1790, 730),
        //1027, 299
        Darkrim(Cities.RALEIGH, 1542, 845),
        //885, 345
        Castine(Cities.NASHVILLE, 1325, 915),
        //759, 374
        Dallaman(Cities.ATLANTA, 1448, 1070),
        //830, 436
        Brytis(Cities.CHARLESTON, 1683, 1034),
        //967, 421
        Crepusculon(Cities.NEW_ORLEANS, 1295, 1248),
        //744, 509
        Altiere(Cities.MIAMI, 1633, 1320);
        //938, 537

        private String name;
        private int x;
        private int y;

        MapPoints(@NonNull String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        @NonNull
        String getName() {
            return name;
        }

        int getDistance(int x, int y) {
            int xDif = x - this.x;
            int yDif = y - this.y;
            return (int) Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
        }

    }


    private int getRouteResource(@NonNull Route route) {
        return getRouteResource(route, false);
    }

    private int getRouteResource(@NonNull Route route, boolean getSecond) {
        String src  = route.getSrc();
        String dest = route.getDest();
        switch(src) {
            case Cities.VANCOUVER:
                switch(dest) {
                    case Cities.CALGARY:
                        if (! getSecond) return R.drawable.verdona_nonnog;
                        else             return R.drawable.verdona_nonnog;
                    case Cities.SEATTLE:
                        if (! getSecond) return R.drawable.verdona_zeeatll;
                        else             return R.drawable.verdona_zeeatll;
                }
                break;
            case Cities.SEATTLE:
                switch(dest) {
                    case Cities.VANCOUVER:
                        if (! getSecond) return R.drawable.verdona_zeeatll;
                        else             return R.drawable.verdona_zeeatll;
                    case Cities.CALGARY:
                        if (! getSecond) return R.drawable.zeeatll_nonnog;
                        else             return R.drawable.zeeatll_nonnog;
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.zeeatll_boisey;
                        else             return R.drawable.zeeatll_boisey;
                    case Cities.PORTLAND:
                        if (! getSecond) return R.drawable.stratus_zeeatll;
                        else             return R.drawable.stratus_zeeatll;
                }
                break;
            case Cities.PORTLAND:
                switch(dest) {
                    case Cities.SEATTLE:
                        if (! getSecond) return R.drawable.stratus_zeeatll;
                        else             return R.drawable.stratus_zeeatll;
                    case Cities.SAN_FRANCISCO:
                        if (! getSecond) return R.drawable.jaqualind_stratus_1;
                        else             return R.drawable.jaqualind_stratus_2;
                    case Cities.SALT_LAKE_CITY:
                        if (! getSecond) return R.drawable.kiflamar_stratus;
                        else             return R.drawable.kiflamar_stratus;
                }
                break;
            case Cities.SAN_FRANCISCO:
                switch(dest) {
                    case Cities.PORTLAND:
                        if (! getSecond) return R.drawable.jaqualind_stratus_1;
                        else             return R.drawable.jaqualind_stratus_2;
                    case Cities.SALT_LAKE_CITY:
                        if (! getSecond) return R.drawable.jaqualind_kiflamar_1;
                        else             return R.drawable.jaqualind_kiflamar_2;
                    case Cities.LOS_ANGELES:
                        if (! getSecond) return R.drawable.lin_jaqualind_1;
                        else             return R.drawable.lin_jaqualind_2;
                }
                break;
            case Cities.LOS_ANGELES:
                switch(dest) {
                    case Cities.SAN_FRANCISCO:
                        if (! getSecond) return R.drawable.lin_jaqualind_1;
                        else             return R.drawable.lin_jaqualind_2;
                    case Cities.LAS_VEGAS:
                        if (! getSecond) return R.drawable.lin_alpha_lyrae;
                        else             return R.drawable.lin_alpha_lyrae;
                    case Cities.PHOENIX:
                        if (! getSecond) return R.drawable.lin_igio;
                        else             return R.drawable.lin_igio;
                    case Cities.EL_PASO:
                        if (! getSecond) return R.drawable.lin_magmarse;
                        else             return R.drawable.lin_magmarse;
                }
                break;
            case Cities.LAS_VEGAS:
                switch(dest) {
                    case Cities.LOS_ANGELES:
                        if (! getSecond) return R.drawable.lin_alpha_lyrae;
                        else             return R.drawable.lin_alpha_lyrae;
                    case Cities.SALT_LAKE_CITY:
                        if (! getSecond) return R.drawable.alpha_lyrae_kiflamar;
                        else             return R.drawable.alpha_lyrae_kiflamar;
                }
                break;
            case Cities.SALT_LAKE_CITY:
                switch(dest) {
                    case Cities.LAS_VEGAS:
                        if (! getSecond) return R.drawable.alpha_lyrae_kiflamar;
                        else             return R.drawable.alpha_lyrae_kiflamar;
                    case Cities.SAN_FRANCISCO:
                        if (! getSecond) return R.drawable.jaqualind_kiflamar_1;
                        else             return R.drawable.jaqualind_kiflamar_2;
                    case Cities.PORTLAND:
                        if (! getSecond) return R.drawable.kiflamar_stratus;
                        else             return R.drawable.kiflamar_stratus;
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.boisey_kiflamar;
                        else             return R.drawable.boisey_kiflamar;
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.kiflamar_aeontacht_1;
                        else             return R.drawable.kiflamar_aeontacht_2;
                }
                break;
            case Cities.HELENA:
                switch(dest) {
                    case Cities.CALGARY:
                        if (! getSecond) return R.drawable.nonnog_boisey;
                        else             return R.drawable.nonnog_boisey;
                    case Cities.WINNIPEG:
                        if (! getSecond) return R.drawable.boisey_kerrectice;
                        else             return R.drawable.boisey_kerrectice;
                    case Cities.DULUTH:
                        if (! getSecond) return R.drawable.boisey_paradus;
                        else             return R.drawable.boisey_paradus;
                    case Cities.OMAHA:
                        if (! getSecond) return R.drawable.boisey_kita_sota;
                        else             return R.drawable.boisey_kita_sota;
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.boisey_aeontacht;
                        else             return R.drawable.boisey_aeontacht;
                    case Cities.SALT_LAKE_CITY:
                        if (! getSecond) return R.drawable.boisey_kiflamar;
                        else             return R.drawable.boisey_kiflamar;
                    case Cities.SEATTLE:
                        if (! getSecond) return R.drawable.zeeatll_boisey;
                        else             return R.drawable.zeeatll_boisey;
                }
                break;
            case Cities.CALGARY:
                switch(dest) {
                    case Cities.VANCOUVER:
                        if (! getSecond) return R.drawable.verdona_nonnog;
                        else             return R.drawable.verdona_nonnog;
                    case Cities.SEATTLE:
                        if (! getSecond) return R.drawable.zeeatll_nonnog;
                        else             return R.drawable.zeeatll_nonnog;
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.nonnog_boisey;
                        else             return R.drawable.nonnog_boisey;
                    case Cities.WINNIPEG:
                        if (! getSecond) return R.drawable.nonnog_kerrectice;
                        else             return R.drawable.nonnog_kerrectice;
                }
                break;
            case Cities.WINNIPEG:
                switch(dest) {
                    case Cities.DULUTH:
                        if (! getSecond) return R.drawable.kerrectice_paradus;
                        else             return R.drawable.kerrectice_paradus;
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.boisey_kerrectice;
                        else             return R.drawable.boisey_kerrectice;
                    case Cities.CALGARY:
                        if (! getSecond) return R.drawable.nonnog_kerrectice;
                        else             return R.drawable.nonnog_kerrectice;
                    case Cities.SAULT_ST_MARIE:
                        if (! getSecond) return R.drawable.kerrectice_ayon;
                        else             return R.drawable.kerrectice_ayon;
                }
                break;
            case Cities.DULUTH:
                switch(dest) {
                    case Cities.SAULT_ST_MARIE:
                        if (! getSecond) return R.drawable.ayon_paradus;
                        else             return R.drawable.ayon_paradus;
                    case Cities.TORONTO:
                        if (! getSecond) return R.drawable.paradus_astern;
                        else             return R.drawable.paradus_astern;
                    case Cities.CHICAGO:
                        if (! getSecond) return R.drawable.orthok_paradus;
                        else             return R.drawable.orthok_paradus;
                    case Cities.OMAHA:
                        if (! getSecond) return R.drawable.kita_sota_paradus_1;
                        else             return R.drawable.kita_sota_paradus_2;
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.boisey_paradus;
                        else             return R.drawable.boisey_paradus;
                    case Cities.WINNIPEG:
                        if (! getSecond) return R.drawable.kerrectice_paradus;
                        else             return R.drawable.kerrectice_paradus;
                }
                break;
            case Cities.OMAHA:
                switch(dest) {
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.kita_sota_aeontacht;
                        else             return R.drawable.kita_sota_aeontacht;
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.boisey_kita_sota;
                        else             return R.drawable.boisey_kita_sota;
                    case Cities.DULUTH:
                        if (! getSecond) return R.drawable.kita_sota_paradus_1;
                        else             return R.drawable.kita_sota_paradus_2;
                    case Cities.CHICAGO:
                        if (! getSecond) return R.drawable.orthok_kita_sota_1;
                        else             return R.drawable.orthok_kita_sota_1;
                    case Cities.KANSAS_CITY:
                        if (! getSecond) return R.drawable.ico_col_kita_sota_1;
                        else             return R.drawable.ico_col_kita_sota_2;
                }
                break;
            case Cities.KANSAS_CITY:
                switch(dest) {
                    case Cities.OMAHA:
                        if (! getSecond) return R.drawable.ico_col_kita_sota_1;
                        else             return R.drawable.ico_col_kita_sota_2;
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.aeontacht_ico_col_1;
                        else             return R.drawable.aeontacht_ico_col_2;
                    case Cities.SAINT_LOUIS:
                        if (! getSecond) return R.drawable.ico_col_zeroph_1;
                        else             return R.drawable.ico_col_zeroph_2;
                    case Cities.OKLAHOMA_CITY:
                        if (! getSecond) return R.drawable.aeuoni_ico_col_1;
                        else             return R.drawable.aeuoni_ico_col_2;
                }
                break;
            case Cities.DENVER:
                switch(dest) {
                    case Cities.HELENA:
                        if (! getSecond) return R.drawable.boisey_aeontacht;
                        else             return R.drawable.boisey_aeontacht;
                    case Cities.OMAHA:
                        if (! getSecond) return R.drawable.kita_sota_aeontacht;
                        else             return R.drawable.kita_sota_aeontacht;
                    case Cities.KANSAS_CITY:
                        if (! getSecond) return R.drawable.aeontacht_ico_col_1;
                        else             return R.drawable.aeontacht_ico_col_2;
                    case Cities.SALT_LAKE_CITY:
                        if (! getSecond) return R.drawable.kiflamar_aeontacht_1;
                        else             return R.drawable.kiflamar_aeontacht_2;
                    case Cities.OKLAHOMA_CITY:
                        if (! getSecond) return R.drawable.aeontacht_aeuoni;
                        else             return R.drawable.aeontacht_aeuoni;
                    case Cities.SANTA_FE:
                        if (! getSecond) return R.drawable.aeontacht_fractine;
                        else             return R.drawable.aeontacht_fractine;
                    case Cities.PHOENIX:
                        if (! getSecond) return R.drawable.aeontacht_igio;
                        else             return R.drawable.aeontacht_igio;
                }
                break;
            case Cities.PHOENIX:
                switch(dest) {
                    case Cities.LOS_ANGELES:
                        if (! getSecond) return R.drawable.lin_igio;
                        else             return R.drawable.lin_igio;
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.aeontacht_igio;
                        else             return R.drawable.aeontacht_igio;
                    case Cities.SANTA_FE:
                        if (! getSecond) return R.drawable.fractine_igio;
                        else             return R.drawable.fractine_igio;
                    case Cities.EL_PASO:
                        if (! getSecond) return R.drawable.igio_magmarse;
                        else             return R.drawable.igio_magmarse;
                }
                break;
            case Cities.SANTA_FE:
                switch(dest) {
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.aeontacht_fractine;
                        else             return R.drawable.aeontacht_fractine;
                    case Cities.PHOENIX:
                        if (! getSecond) return R.drawable.fractine_igio;
                        else             return R.drawable.fractine_igio;
                    case Cities.OKLAHOMA_CITY:
                        if (! getSecond) return R.drawable.fractine_aeuoni;
                        else             return R.drawable.fractine_aeuoni;
                    case Cities.EL_PASO:
                        if (! getSecond) return R.drawable.fractine_magmarse;
                        else             return R.drawable.fractine_magmarse;
                }
                break;
            case Cities.EL_PASO:
                switch(dest) {
                    case Cities.LOS_ANGELES:
                        if (! getSecond) return R.drawable.lin_magmarse;
                        else             return R.drawable.lin_magmarse;
                    case Cities.PHOENIX:
                        if (! getSecond) return R.drawable.igio_magmarse;
                        else             return R.drawable.igio_magmarse;
                    case Cities.SANTA_FE:
                        if (! getSecond) return R.drawable.fractine_magmarse;
                        else             return R.drawable.fractine_magmarse;
                    case Cities.OKLAHOMA_CITY:
                        if (! getSecond) return R.drawable.magmarse_aeuoni;
                        else             return R.drawable.magmarse_aeuoni;
                    case Cities.DALLAS:
                        if (! getSecond) return R.drawable.magmarse_bynodia;
                        else             return R.drawable.magmarse_bynodia;
                    case Cities.HOUSTON:
                        if (! getSecond) return R.drawable.magmarse_warfeld;
                        else             return R.drawable.magmarse_warfeld;
                }
                break;
            case Cities.HOUSTON:
                switch(dest) {
                    case Cities.EL_PASO:
                        if (! getSecond) return R.drawable.magmarse_warfeld;
                        else             return R.drawable.magmarse_warfeld;
                    case Cities.DALLAS:
                        if (! getSecond) return R.drawable.warfeld_bynodia_1;
                        else             return R.drawable.warfeld_bynodia_2;
                    case Cities.NEW_ORLEANS:
                        if (! getSecond) return R.drawable.crepusculon_warfeld;
                        else             return R.drawable.crepusculon_warfeld;
                }
                break;
            case Cities.DALLAS:
                switch(dest) {
                    case Cities.EL_PASO:
                        if (! getSecond) return R.drawable.magmarse_bynodia;
                        else             return R.drawable.magmarse_bynodia;
                    case Cities.HOUSTON:
                        if (! getSecond) return R.drawable.warfeld_bynodia_1;
                        else             return R.drawable.warfeld_bynodia_2;
                    case Cities.OKLAHOMA_CITY:
                        if (! getSecond) return R.drawable.aeuoni_bynodia_1;
                        else             return R.drawable.aeuoni_bynodia_2;
                    case Cities.LITTLE_ROCK:
                        if (! getSecond) return R.drawable.littlerock_bynodia;
                        else             return R.drawable.littlerock_bynodia;
                }
                break;
            case Cities.OKLAHOMA_CITY:
                switch(dest) {
                    case Cities.EL_PASO:
                        if (! getSecond) return R.drawable.magmarse_aeuoni;
                        else             return R.drawable.magmarse_aeuoni;
                    case Cities.SANTA_FE:
                        if (! getSecond) return R.drawable.fractine_aeuoni;
                        else             return R.drawable.fractine_aeuoni;
                    case Cities.DENVER:
                        if (! getSecond) return R.drawable.aeontacht_aeuoni;
                        else             return R.drawable.aeontacht_aeuoni;
                    case Cities.KANSAS_CITY:
                        if (! getSecond) return R.drawable.aeuoni_ico_col_1;
                        else             return R.drawable.aeuoni_ico_col_2;
                    case Cities.DALLAS:
                        if (! getSecond) return R.drawable.aeuoni_bynodia_1;
                        else             return R.drawable.aeuoni_bynodia_2;
                    case Cities.LITTLE_ROCK:
                        if (! getSecond) return R.drawable.aeuoni_littlerock;
                        else             return R.drawable.aeuoni_littlerock;
                }
                break;
            case Cities.LITTLE_ROCK:
                switch(dest) {
                    case Cities.DALLAS:
                        if (! getSecond) return R.drawable.littlerock_bynodia;
                        else             return R.drawable.littlerock_bynodia;
                    case Cities.OKLAHOMA_CITY:
                        if (! getSecond) return R.drawable.aeuoni_littlerock;
                        else             return R.drawable.aeuoni_littlerock;
                    case Cities.SAINT_LOUIS:
                        if (! getSecond) return R.drawable.zeroph_littlerock;
                        else             return R.drawable.zeroph_littlerock;
                    case Cities.NASHVILLE:
                        if (! getSecond) return R.drawable.littlerock_castine;
                        else             return R.drawable.littlerock_castine;
                    case Cities.NEW_ORLEANS:
                        if (! getSecond) return R.drawable.crepusculon_littlerock;
                        else             return R.drawable.crepusculon_littlerock;
                }
                break;
            case Cities.NEW_ORLEANS:
                switch(dest) {
                    case Cities.HOUSTON:
                        if (! getSecond) return R.drawable.crepusculon_warfeld;
                        else             return R.drawable.crepusculon_warfeld;
                    case Cities.LITTLE_ROCK:
                        if (! getSecond) return R.drawable.crepusculon_littlerock;
                        else             return R.drawable.crepusculon_littlerock;
                    case Cities.ATLANTA:
                        if (! getSecond) return R.drawable.crepusculon_dallaman_1;
                        else             return R.drawable.crepusculon_dallaman_2;
                    case Cities.MIAMI:
                        if (! getSecond) return R.drawable.crepusculon_altiere;
                        else             return R.drawable.crepusculon_altiere;
                }
                break;
            case Cities.SAINT_LOUIS:
                switch(dest) {
                    case Cities.KANSAS_CITY:
                        if (! getSecond) return R.drawable.ico_col_zeroph_1;
                        else             return R.drawable.ico_col_zeroph_2;
                    case Cities.LITTLE_ROCK:
                        if (! getSecond) return R.drawable.zeroph_littlerock;
                        else             return R.drawable.zeroph_littlerock;
                    case Cities.CHICAGO:
                        if (! getSecond) return R.drawable.orthok_zeroph_1;
                        else             return R.drawable.orthok_zeroph_2;
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.zeroph_petraqa;
                        else             return R.drawable.zeroph_petraqa;
                    case Cities.NASHVILLE:
                        if (! getSecond) return R.drawable.castine_zeroph;
                        else             return R.drawable.castine_zeroph;
                }
                break;
            case Cities.CHICAGO:
                switch(dest) {
                    case Cities.OMAHA:
                        if (! getSecond) return R.drawable.orthok_kita_sota_1;
                        else             return R.drawable.orthok_kita_sota_1;
                    case Cities.DULUTH:
                        if (! getSecond) return R.drawable.orthok_paradus;
                        else             return R.drawable.orthok_paradus;
                    case Cities.SAINT_LOUIS:
                        if (! getSecond) return R.drawable.orthok_zeroph_1;
                        else             return R.drawable.orthok_zeroph_2;
                    case Cities.TORONTO:
                        if (! getSecond) return R.drawable.orthok_astern;
                        else             return R.drawable.orthok_astern;
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.orthok_petraqa_1;
                        else             return R.drawable.orthok_petraqa_2;
                }
                break;
            case Cities.SAULT_ST_MARIE:
                switch(dest) {
                    case Cities.WINNIPEG:
                        if (! getSecond) return R.drawable.kerrectice_ayon;
                        else             return R.drawable.kerrectice_ayon;
                    case Cities.DULUTH:
                        if (! getSecond) return R.drawable.ayon_paradus;
                        else             return R.drawable.ayon_paradus;
                    case Cities.TORONTO:
                        if (! getSecond) return R.drawable.ayon_astern;
                        else             return R.drawable.ayon_astern;
                    case Cities.MONTREAL:
                        if (! getSecond) return R.drawable.exen_ayon_1;
                        else             return R.drawable.exen_ayon_2;
                }
                break;
            case Cities.TORONTO:
                switch(dest) {
                    case Cities.CHICAGO:
                        if (! getSecond) return R.drawable.orthok_astern;
                        else             return R.drawable.orthok_astern;
                    case Cities.DULUTH:
                        if (! getSecond) return R.drawable.paradus_astern;
                        else             return R.drawable.paradus_astern;
                    case Cities.SAULT_ST_MARIE:
                        if (! getSecond) return R.drawable.ayon_astern;
                        else             return R.drawable.ayon_astern;
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.astern_petraqa;
                        else             return R.drawable.astern_petraqa;
                    case Cities.MONTREAL:
                        if (! getSecond) return R.drawable.exen_astern;
                        else             return R.drawable.exen_astern;
                }
                break;
            case Cities.PITTSBURG:
                switch(dest) {
                    case Cities.SAINT_LOUIS:
                        if (! getSecond) return R.drawable.zeroph_petraqa;
                        else             return R.drawable.zeroph_petraqa;
                    case Cities.CHICAGO:
                        if (! getSecond) return R.drawable.orthok_petraqa_1;
                        else             return R.drawable.orthok_petraqa_2;
                    case Cities.TORONTO:
                        if (! getSecond) return R.drawable.astern_petraqa;
                        else             return R.drawable.astern_petraqa;
                    case Cities.NEW_YORK:
                        if (! getSecond) return R.drawable.spheras_petraqa_1;
                        else             return R.drawable.spheras_petraqa_2;
                    case Cities.WASHINGTON:
                        if (! getSecond) return R.drawable.petraqa_kalishen;
                        else             return R.drawable.petraqa_kalishen;
                    case Cities.RALEIGH:
                        if (! getSecond) return R.drawable.petraqa_darkrim;
                        else             return R.drawable.petraqa_darkrim;
                    case Cities.NASHVILLE:
                        if (! getSecond) return R.drawable.castine_petraqa;
                        else             return R.drawable.castine_petraqa;
                }
                break;
            case Cities.NASHVILLE:
                switch(dest) {
                    case Cities.LITTLE_ROCK:
                        if (! getSecond) return R.drawable.littlerock_castine;
                        else             return R.drawable.littlerock_castine;
                    case Cities.SAINT_LOUIS:
                        if (! getSecond) return R.drawable.castine_zeroph;
                        else             return R.drawable.castine_zeroph;
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.castine_petraqa;
                        else             return R.drawable.castine_petraqa;
                    case Cities.RALEIGH:
                        if (! getSecond) return R.drawable.castine_darkrim;
                        else             return R.drawable.castine_darkrim;
                    case Cities.ATLANTA:
                        if (! getSecond) return R.drawable.castine_dallaman;
                        else             return R.drawable.castine_dallaman;
                }
                break;
            case Cities.ATLANTA:
                switch(dest) {
                    case Cities.NEW_ORLEANS:
                        if (! getSecond) return R.drawable.crepusculon_dallaman_1;
                        else             return R.drawable.crepusculon_dallaman_2;
                    case Cities.RALEIGH:
                        if (! getSecond) return R.drawable.darkrim_dallaman_1;
                        else             return R.drawable.darkrim_dallaman_2;
                    case Cities.CHARLESTON:
                        if (! getSecond) return R.drawable.dallaman_brytis;
                        else             return R.drawable.dallaman_brytis;
                    case Cities.MIAMI:
                        if (! getSecond) return R.drawable.dallaman_altiere;
                        else             return R.drawable.dallaman_altiere;
                    case Cities.NASHVILLE:
                        if (! getSecond) return R.drawable.castine_dallaman;
                        else             return R.drawable.castine_dallaman;
                }
                break;
            case Cities.MIAMI:
                switch(dest) {
                    case Cities.ATLANTA:
                        if (! getSecond) return R.drawable.dallaman_altiere;
                        else             return R.drawable.dallaman_altiere;
                    case Cities.NEW_ORLEANS:
                        if (! getSecond) return R.drawable.crepusculon_altiere;
                        else             return R.drawable.crepusculon_altiere;
                    case Cities.CHARLESTON:
                        if (! getSecond) return R.drawable.brytis_altiere;
                        else             return R.drawable.brytis_altiere;
                }
                break;
            case Cities.CHARLESTON:
                switch(dest) {
                    case Cities.ATLANTA:
                        if (! getSecond) return R.drawable.dallaman_brytis;
                        else             return R.drawable.dallaman_brytis;
                    case Cities.MIAMI:
                        if (! getSecond) return R.drawable.brytis_altiere;
                        else             return R.drawable.brytis_altiere;
                    case Cities.RALEIGH:
                        if (! getSecond) return R.drawable.darkrim_brytis;
                        else             return R.drawable.darkrim_brytis;
                }
                break;
            case Cities.RALEIGH:
                switch(dest) {
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.petraqa_darkrim;
                        else             return R.drawable.petraqa_darkrim;
                    case Cities.WASHINGTON:
                        if (! getSecond) return R.drawable.kalishen_darkrim_1;
                        else             return R.drawable.kalishen_darkrim_2;
                    case Cities.NASHVILLE:
                        if (! getSecond) return R.drawable.castine_darkrim;
                        else             return R.drawable.castine_darkrim;

                }
                break;
            case Cities.WASHINGTON:
                switch(dest) {
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.petraqa_kalishen;
                        else             return R.drawable.petraqa_kalishen;
                    case Cities.RALEIGH:
                        if (! getSecond) return R.drawable.kalishen_darkrim_1;
                        else             return R.drawable.kalishen_darkrim_2;
                    case Cities.NEW_YORK:
                        if (! getSecond) return R.drawable.spheras_kalishen_1;
                        else             return R.drawable.spheras_kalishen_2;
                }
                break;
            case Cities.NEW_YORK:
                switch(dest) {
                    case Cities.PITTSBURG:
                        if (! getSecond) return R.drawable.spheras_petraqa_1;
                        else             return R.drawable.spheras_petraqa_2;
                    case Cities.WASHINGTON:
                        if (! getSecond) return R.drawable.spheras_kalishen_1;
                        else             return R.drawable.spheras_kalishen_2;
                    case Cities.MONTREAL:
                        if (! getSecond) return R.drawable.exen_spheras;
                        else             return R.drawable.exen_spheras;
                    case Cities.BOSTON:
                        if (! getSecond) return R.drawable.wence_spheras_1;
                        else             return R.drawable.wence_spheras_2;
                }
                break;
            case Cities.BOSTON:
                switch(dest) {
                    case Cities.NEW_YORK:
                        if (! getSecond) return R.drawable.wence_spheras_1;
                        else             return R.drawable.wence_spheras_2;
                    case Cities.MONTREAL:
                        if (! getSecond) return R.drawable.exen_wence;
                        else             return R.drawable.exen_wence;
                }
                break;
            case Cities.MONTREAL:
                switch(dest) {
                    case Cities.SAULT_ST_MARIE:
                        if (! getSecond) return R.drawable.exen_ayon_1;
                        else             return R.drawable.exen_ayon_2;
                    case Cities.TORONTO:
                        if (! getSecond) return R.drawable.exen_astern;
                        else             return R.drawable.exen_astern;
                    case Cities.NEW_YORK:
                        if (! getSecond) return R.drawable.exen_spheras;
                        else             return R.drawable.exen_spheras;
                    case Cities.BOSTON:
                        if (! getSecond) return R.drawable.exen_wence;
                        else             return R.drawable.exen_wence;
                }
                break;

        }
        return 0; // Android returns zero when no resource can be found
    }
}