package com.woozam.wdthelper.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class DataArrayList<E extends AbsData> extends ArrayList<AbsData> {

	private static final long serialVersionUID = -6564125506801727928L;
	
	public DataArrayList(String jsonString, Class<? extends AbsData> mclass) {
		super();
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			int size = jsonArray.length();
			Constructor<? extends AbsData> constructor = mclass.getConstructor(String.class);
			for (int i = 0; i < size; i++) {
				add(constructor.newInstance(jsonArray.getString(i)));
			}
		} catch (JSONException e) {
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
	}
}