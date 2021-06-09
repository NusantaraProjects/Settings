package id.nusantara.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by DELTALABS on 08,June,2021
 * DELTALABS STUDIO
 */
public class Tools {

    private static Context mContext;

    private Tools(){}

    public static float getFloat(final float value, final float minValue, final float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        if (mContext == null) {
            mContext = CurrentApplicationHolder.INSTANCE;
        }
        return notNull(mContext);
    }

    static class CurrentApplicationHolder {
        static final Application INSTANCE;

        static {
            try {
                Class<?> clazz = Class.forName("android.app.ActivityThread");
                Method method = getMethod(clazz, "currentApplication");
                INSTANCE = cast(invokeStaticMethod(method));
            } catch (Throwable ex) {
                throw new AssertionError(ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }

    public static <T> T notNull(T object) {
        if (object == null) {
            throw new NullPointerException();
        }

        return object;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Class<?> cl = clazz;
        while (cl != null) {
            try {
                return cl.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException ex) {
                cl = cl.getSuperclass();
            }
        }

        return null;
    }

    public static Object invokeStaticMethod(Method method, Object... args) {
        return invokeMethod(method, null, args);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Throwable ex) {
            return null;
        }
    }

    private static DisplayMetrics mDisplayMetrics = null;

    public static int toScreenPixels(Resources res, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isRtl(Resources res) {
        return res.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static int dpToPx(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int spToPx(Context context, float sp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplayMetrics(context));
        return px;
    }

    public static Point getScreenSize(Context context) {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        return point;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }

    public static void showToast(int toast){
        Toast.makeText(getContext(), String.valueOf(toast), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String toast){
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public static int getResource(String atts, String type) {
        return getContext().getResources().getIdentifier(atts, type, getContext().getPackageName());
    }

    public static int intAttr(String attrs) {
        return getResource(attrs, "attr");
    }

    public static int intDimen(String dimen) {
        return getResource(dimen, "dimen");
    }

    public static int intAnim(String anim) {
        return getResource(anim, "anim");
    }

    public static int intStyleable(String styleable) {
        return getResource(styleable, "styleable");
    }

    public static int intLayout (String layout) {
        return getResource(layout, "layout");
    }

    public static int intId (String id) {
        return getResource(id, "id");
    }

    public static int intXml (String xml) {
        return getResource(xml, "xml");
    }

    public static int intStyle(String style) {
        return getResource(style, "style");
    }

    public static int intColor(String color) {
        return getResource(color, "color");
    }

    public static int intDrawable(String drawable) {
        return getResource(drawable, "drawable");
    }

    public static int intString(String string) {
        return getResource(string, "string");
    }

    public static String getString(String string) {
        return getContext().getString(intString(string));
    }

    public static Drawable getDrawable(String string) {
        return getContext().getResources().getDrawable(intDrawable(string));
    }

    public static Drawable colorDrawable(String drawable, int color, PorterDuff.Mode mode){
        Drawable cd = getDrawable(drawable);
        cd.setColorFilter(color, mode);
        return cd;
    }

    public static Drawable colorDrawable(int drawable, int color, PorterDuff.Mode mode){
        Drawable cd = getContext().getResources().getDrawable(drawable);
        cd.setColorFilter(color, mode);
        return cd;
    }

    public static int getColor(String color) {
        return getContext().getResources().getColor(intColor(color));
    }

    public static String CHECK(String key){
        return key+"_check";
    }

    public static String ENDCOLOR(String key){
        return key+"_GC";
    }

    public static String ISGRADIENT(String key){
        return key+"_Gactive";
    }

    public static String ORIENTATION(String key){
        return key+"_GOrient";
    }

    public static boolean ISTESTMODE(){
        if(getContext().getPackageName().equals("id.nusantara")){
            return true;
        }else {
            return false;
        }
    }

    public static String trimFront(String input) {
        if (input == null) return input;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isWhitespace(input.charAt(i)))
                return input.substring(i);
        }
        return "";
    }

    public static String capitizeString(String name){
        String captilizedString="";
        if(!name.trim().equals("")){
            captilizedString = name.substring(0,1).toUpperCase() + name.substring(1);
        }
        return captilizedString;
    }


    public static void addFragment(AppCompatActivity dialogToastActivity, Fragment fragment, int layoutId){
        dialogToastActivity.getSupportFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
    }


    public static void setupRecyclerView(Context context, RecyclerView mList, int mOrientation){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, mOrientation, false);
        mList.setLayoutManager(mLayoutManager);
    }

    public static void setupGridView(Context context, RecyclerView mList, int spanCount){
        GridLayoutManager mLayoutManager = new GridLayoutManager(context, spanCount);
        mList.setLayoutManager(mLayoutManager);
    }


    public static Activity getActivity(Fragment fragment){
        return fragment.getActivity();
    }

    public static Bitmap setGradientBackground(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, height, 0xFFF0D252, 0xFFF07305, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);

        return updatedBitmap;
    }
/*
    public static void setImageView(ImageView mImageView, int resource){
        Picasso.with(mImageView.getContext()).load(resource).into(mImageView);
    }

    public static void setImageView(ImageView imageView, File file){
        Picasso.with(imageView.getContext()).load(file).into(imageView);
    }
 */
    public static int getViewHeight(View view) {
        WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int deviceWidth;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        } else {
            deviceWidth = display.getWidth();
        }

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight(); //        view.getMeasuredWidth();
    }

    public static void updateDrawableColor(@Nullable Drawable drawable, int color) {
        if (drawable == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            drawable.setTint(color);
        else
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static <V extends View> Collection<V> findChildrenByClass(ViewGroup viewGroup, Class<V> clazz) {

        return gatherChildrenByClass(viewGroup, clazz, new ArrayList<V>());
    }

    private static <V extends View> Collection<V> gatherChildrenByClass(ViewGroup viewGroup, Class<V> clazz, Collection<V> childrenFound) {

        for (int i = 0; i < viewGroup.getChildCount(); i++)
        {
            final View child = viewGroup.getChildAt(i);
            if (clazz.isAssignableFrom(child.getClass())) {
                childrenFound.add((V)child);
            }
            if (child instanceof ViewGroup) {
                gatherChildrenByClass((ViewGroup) child, clazz, childrenFound);
            }
        }

        return childrenFound;
    }
}
