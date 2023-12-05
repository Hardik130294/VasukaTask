package com.hardik.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.hardik.utils.JsonUtils;
import com.hardik.utils.MyApplication;
import com.hardik.vasukatask.model.Collections;
import com.hardik.vasukatask.model.Index;
import com.hardik.vasukatask.model.ResultModel;
import com.hardik.vasukatask.model.Smart;
import com.hardik.vasukatask.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ReadJsonDataRepository {
    private static final String TAG = ReadJsonDataRepository.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    public static ReadJsonDataRepository instance;
    Context context;

    private ReadJsonDataRepository() {
        this.context = MyApplication.getAppContext();
    }

    public static synchronized ReadJsonDataRepository getInstance() {
        if (instance == null) {
            instance = new ReadJsonDataRepository();
        }
        return instance;
    }

    ResultModel resultModel = null;
    ResultModel.Result result = null;
    List<Index> indexList = new ArrayList<>();
    Collections collections = null;
    List<Smart> smartList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    public ResultModel getData() {

        String fileName = "data.json";
        JSONObject json = JsonUtils.getJsonObjectFromAsset(context, fileName);
        if (json != null) {
            try {
                JSONObject resultObject = json.getJSONObject("result");
                JSONArray indexArray = resultObject.getJSONArray("index");
                JSONObject collectionsObject = resultObject.getJSONObject("collections");

                for (int i = 0; i < indexArray.length(); i++) {
                    JSONObject item = indexArray.getJSONObject(i);
                    int downloadid = item.getInt("downloadid");
                    int cd_downloads = item.getInt("cd_downloads");
                    int id = item.getInt("id");
                    String title = item.getString("title");
                    int status = item.getInt("status");
                    String release_date = item.getString("release_date");
                    int authorid = item.getInt("authorid");
                    int video_count = item.getInt("video_count");


                    List<String> styleTagsList = new ArrayList<>();
                    JSONArray style_tagsArray = item.getJSONArray("style_tags");
                    for (int j = 0; j < style_tagsArray.length(); j++) {
                        String style_tags_item = style_tagsArray.getString(j);
                        Log.w(TAG, "onResume: style_tags_item: " + style_tags_item);
                        styleTagsList.add(style_tags_item);
                    }

                    List<String> skillTagsList = new ArrayList<>();
                    JSONArray skill_tagsArray = item.getJSONArray("skill_tags");
                    for (int j = 0; j < skill_tagsArray.length(); j++) {
                        String skill_tags_item = skill_tagsArray.getString(j);
                        Log.w(TAG, "onResume: skill_tags_item: " + skill_tags_item);
                        skillTagsList.add(skill_tags_item);
                    }

                    List<String> seriesTagsList = new ArrayList<>();
                    JSONArray series_tagsArray = item.getJSONArray("series_tags");
                    for (int j = 0; j < series_tagsArray.length(); j++) {
                        String series_tags_item = series_tagsArray.getString(j);
                        Log.w(TAG, "onResume: series_tags_item: " + series_tags_item);
                        seriesTagsList.add(series_tags_item);
                    }

                    List<String> curriculumTagsList = new ArrayList<>();
                    JSONArray curriculum_tagsArray = item.getJSONArray("curriculum_tags");
                    for (int j = 0; j < curriculum_tagsArray.length(); j++) {
                        String curriculum_tags_item = curriculum_tagsArray.getString(j);
                        Log.w(TAG, "onResume: curriculum_tags_item: " + curriculum_tags_item);
                        curriculumTagsList.add(curriculum_tags_item);
                    }

                    String educator = item.getString("educator");
                    int owned = item.getInt("owned");
                    int sale = item.getInt("sale");
                    Object purchase_order = null;

                    try {
                        if (item.has("purchase_order")) {
                            if (item.get("purchase_order") instanceof Boolean) {
                                boolean purchaseOrderBoolean = item.getBoolean("purchase_order");
                                purchase_order = purchaseOrderBoolean ? 1 : 0;
                            } else if (item.get("purchase_order") instanceof Integer) {
                                purchase_order = item.getInt("purchase_order");
                            }
                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int watched = item.getInt("watched");
                    int progress_tracking = item.getInt("progress_tracking");

                    Log.i(TAG, "onResume: title: " + title);
                    /*Log.i(TAG, "onResume: downloadid: " + downloadid);
                    Log.i(TAG, "onResume: cd_downloads: " + cd_downloads);
                    Log.i(TAG, "onResume: id: " + id);
                    Log.i(TAG, "onResume: status: " + status);
                    Log.i(TAG, "onResume: release_date: " + release_date);
                    Log.i(TAG, "onResume: authorid: " + authorid);
                    Log.i(TAG, "onResume: video_count: " + video_count);
                    Log.i(TAG, "onResume: educator: " + educator);
                    Log.i(TAG, "onResume: owned: " + owned);
                    Log.i(TAG, "onResume: sale: " + sale);
                    Log.i(TAG, "onResume: purchase_order: " + purchase_order);
                    Log.i(TAG, "onResume: watched: " + watched);
                    Log.i(TAG, "onResume: progress_tracking: " + progress_tracking);
*/
                    Index index = new Index(downloadid, cd_downloads, id, title, status, release_date, authorid, video_count, styleTagsList, skillTagsList, seriesTagsList, curriculumTagsList, educator, owned, sale, (Integer) purchase_order, watched, progress_tracking);
                    indexList.add(index);
                }

                JSONArray smartArray = collectionsObject.getJSONArray("smart");
                processJSONArray(smartArray, "smart", true);

                JSONArray userArray = collectionsObject.getJSONArray("user");
                processJSONArray(userArray, "user", false);

                JSONArray curatedArray = collectionsObject.getJSONArray("curated");
                processJSONArray(curatedArray, "curated", false);

                collections = new Collections(smartList, userList, null);

                result = new ResultModel.Result(indexList, collections);

                resultModel = new ResultModel(result);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resultModel;
    }

    private void processJSONArray(JSONArray jsonArray, String arrayName, boolean hasSmartField) {
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                Object id = hasSmartField ? item.getString("id") : item.getInt("id");
                String label = item.getString("label");

                String additionalFieldSmart = hasSmartField ? item.getString("smart") : "";

                int is_default = item.getInt("is_default");
                int is_archive = item.getInt("is_archive");
                String description = item.getString("description");

                List<Integer> coursesIntList = new ArrayList<>();
                JSONArray coursesArray = item.getJSONArray("courses");
                for (int j = 0; j < coursesArray.length(); j++) {
                    int courses_item = coursesArray.getInt(j);
                    Log.w(TAG, "onResume: courses_item: " + courses_item);
                    coursesIntList.add(courses_item);
                }

                Log.i(TAG, "onResume (" + arrayName + "): label: " + label);
                /*Log.i(TAG, "onResume (" + arrayName + "): id: " + id);
                Log.i(TAG, "onResume (" + arrayName + "): additionalFieldSmart: " + additionalFieldSmart);
                Log.i(TAG, "onResume (" + arrayName + "): is_default: " + is_default);
                Log.i(TAG, "onResume (" + arrayName + "): is_archive: " + is_archive);
                Log.i(TAG, "onResume (" + arrayName + "): description: " + description);*/
                if (hasSmartField) {
                    Smart smart = new Smart((String) id, label, additionalFieldSmart, coursesIntList, is_default, is_archive, description);
                    smartList.add(smart);
                } else {
                    User user = new User((Integer) id, label, is_default, is_archive, description, coursesIntList);
                    userList.add(user);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
