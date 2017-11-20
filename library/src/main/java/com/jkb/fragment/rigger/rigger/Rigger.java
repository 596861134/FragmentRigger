package com.jkb.fragment.rigger.rigger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.jkb.fragment.rigger.annotation.Puppet;
import com.jkb.fragment.rigger.exception.RiggerException;
import com.jkb.fragment.rigger.utils.Logger;
import java.util.HashMap;
import java.util.Map;

/**
 * Rigger class.use this class to rig the puppet class.
 *
 * @author JingYeoh
 *         <a href="mailto:yangjing9611@foxmail.com">Email me</a>
 *         <a href="https://github.com/justkiddingbaby">Github</a>
 *         <a href="http://blog.justkiddingbaby.com">Blog</a>
 * @since Nov 18,2017
 */

public final class Rigger {

  private static final String TAG_HEADER = "<<Rigger>>";
  private static volatile Rigger sInstance = null;
  private Map<Object, IRigger> mPuppetMap;

  /**
   * Prevents this class from being instantiated.
   */
  private Rigger() {
    mPuppetMap = new HashMap<>();
  }

  /**
   * Returns the instance of Rigger.
   */
  private static Rigger getInstance() {
    if (sInstance == null) {
      synchronized (Rigger.class) {
        if (sInstance == null) {
          sInstance = new Rigger();
        }
      }
    }
    return sInstance;
  }

  /**
   * Returns the Rigger that can rig the puppet object.
   *
   * @param object puppet class.must be a child class of {@link Fragment} or {@link AppCompatActivity}
   */
  @NonNull
  public static IRigger getRigger(Object object) {
    //filter the unsupported class
    if (!(object instanceof AppCompatActivity) && !(object instanceof Fragment)) {
      throw new RiggerException(
          "Puppet Annotation class can only used on android.app.Activity or android.support.v4.app.Fragment");
    }
    //filter the unsupported class
    Class<?> clazz = object.getClass();
    Puppet puppet = clazz.getAnnotation(Puppet.class);
    if (puppet == null) {
      throw new RiggerException("Can not find Puppet annotation.please add Puppet annotation in this class");
    }
    IRigger rigger = getInstance().mPuppetMap.get(object);
    if (rigger == null) {
      throw new RiggerException(
          "UnKnown error,please make sure your config is right.or you can contact the author or post a issue.");
    }
    return rigger;
  }

  /**
   * Inject the Fragment's lifecycle method {@link Fragment#onAttach(Context)} ()} to rigger class.
   *
   * @param fragment the fragment puppet.
   */
  private void onAttach(Fragment fragment, Context context) {

  }

  /**
   * Inject the Activity/Fragment's lifecycle method
   * {@link AppCompatActivity#onCreate(Bundle)}/{@link Fragment#onAttach(Context)} ()} to rigger class.
   *
   * @param object             the puppet class.
   * @param savedInstanceState If the activity/fragment is being re-created from
   *                           a previous saved state, this is the state.
   */
  private void onCreate(Object object, Bundle savedInstanceState) {
    Logger.i(object, TAG_HEADER + "onCreate");
    _Rigger rigger = (_Rigger) mPuppetMap.get(object);
    if (rigger == null) {
      rigger = _Rigger.create(object);
      mPuppetMap.put(object, rigger);
    }
    rigger.onCreate(savedInstanceState);
  }

  /**
   * Inject the Fragment's lifecycle method {@link Fragment#onResume()} to rigger class.
   */
  private void onResume(Object object) {
    Logger.i(object, TAG_HEADER + "onCreate");
  }

  /**
   * Inject the Activity's lifecycle method {@link AppCompatActivity#onPostResume()} to rigger class.
   */
  private void onPostResume(AppCompatActivity activity) {
    Logger.i(activity, TAG_HEADER + "onPostResume");
  }

  /**
   * Inject the Activity's lifecycle method {@link AppCompatActivity#onPostResume()} to rigger class.
   */
  private void onResumeFragments(AppCompatActivity activity) {
    Logger.i(activity, TAG_HEADER + "onResumeFragments");
  }

  /**
   * Inject the AppCompatActivity's lifecycle method {@link AppCompatActivity#onPause()} to rigger class.
   */
  private void onPause(AppCompatActivity activity) {
    Logger.i(activity, TAG_HEADER + "onPause");
  }

  /**
   * Inject the Fragment's lifecycle method {@link AppCompatActivity#onSaveInstanceState(Bundle)}
   * /{@link Fragment#onSaveInstanceState(Bundle)} to rigger class.
   *
   * @param object   the puppet class.
   * @param outState in which to place your saved state.
   */
  private void onSaveInstanceState(Object object, Bundle outState) {
    Logger.i(object, TAG_HEADER + "onSaveInstanceState");
  }

  /**
   * Inject the Activity/Fragment's lifecycle method
   * {@link AppCompatActivity#onDestroy()}/{@link Fragment#onDestroy()} to rigger class.
   */
  private void onDestroy(Object object) {
    Logger.i(object, TAG_HEADER + "onDestroy");
  }

  /**
   * Inject the Activity's lifecycle method
   * {@link AppCompatActivity#onBackPressed()} to rigger class.
   */
  private void onBackPressed(AppCompatActivity object) {
    Logger.i(object, TAG_HEADER + "onBackPressed");
  }
}