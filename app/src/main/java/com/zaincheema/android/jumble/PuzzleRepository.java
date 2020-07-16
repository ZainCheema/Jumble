package com.zaincheema.android.jumble;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PuzzleRepository {

    ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();

    private static PuzzleRepository sPuzzleRepository;
    private Context mApplicationContext;

    private JSONArray puzzleNames;
    private JSONArray pictureSets;

    private String indexUrl = "https://goparker.com/600096/jumble/index.json";
    private String basePuzzleUrl = "https://goparker.com/600096/jumble/puzzles/";
    private String baseLayoutUrl = "https://goparker.com/600096/jumble/layouts/";
    private String fullLayoutUrl;
    private String baseImagesUrl = "https://goparker.com/600096/jumble/images/";

    // TODO: mPuzzles will be set after the index has been gone through in its entirety, below method is a placeholder
    private MutableLiveData<ArrayList<Puzzle>> mPuzzles = new MutableLiveData<>();

    private MutableLiveData<Puzzle> mSelectedPuzzle;

    private int puzzleDataIndex = 0;

    private String mName;
    private int mRows;
    private int mColumns;
    private ArrayList<ArrayList<String>> mLayout = new ArrayList<ArrayList<String>>();
    private ArrayList<String> mTilePaths = new ArrayList<String>();

    private PuzzleRepository(Context pApplicationContext) {
        this.mApplicationContext = pApplicationContext;
    }

    public static PuzzleRepository getInstance(Context pApplicationContext) {
        AcceptSSLCerts.accept();
        if (sPuzzleRepository == null) {
            sPuzzleRepository = new PuzzleRepository(pApplicationContext);
        }
        return sPuzzleRepository;
    }

    // TODO: Problem area, why mPuzzle is null
    public LiveData<Puzzle> getPuzzle(final int pPuzzleIndex) {

//        LiveData<Puzzle> transformedPuzzle = Transformations.switchMap(mPuzzles, puzzles -> {
//            MutableLiveData<Puzzle> puzzleData = new MutableLiveData<Puzzle>();
//            Puzzle puzzle = puzzles.get(pPuzzleIndex);
//            puzzleData.setValue(puzzle);
//            loadTiles(puzzle.getTilePaths(), puzzleData);
//            return puzzleData;
//        });

        Puzzle puzzle = puzzles.get(pPuzzleIndex);
        mSelectedPuzzle.setValue(puzzle);

        loadTiles(puzzle.getTilePaths(), mSelectedPuzzle);
       // mSelectedPuzzle = transformedPuzzle;
        return mSelectedPuzzle;
    }

    public void loadPuzzlesFromJSON() {
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                indexUrl,
                null,
                response -> {
                    Log.d("loadPuzzlesFromJSON()", "Response received");
                    try {
                        puzzleNames = response.getJSONArray("PuzzleIndex");
                        // When puzzle index is loaded, parse the first one
                        parsePuzzleData(puzzleDataIndex);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    String errorResponse = "That didn't work!";
                    Log.e("VOLLEY", error.getMessage());
                });

        queue.add(jsonObjectRequest);
    }

    private void parsePuzzleData(int pIndex) {
        Log.e("PARSEPUZZLEDATA()", "parsePuzzleData(---) entered");

        String pUrl = "";

        try {
            if (pIndex < puzzleNames.length()) {
                String fileName = (String) puzzleNames.get(pIndex);
                Log.d("PARSING", fileName);
                pUrl = basePuzzleUrl + fileName;

                Log.e("PUZZLE URL", pUrl);
            } else {
                Log.e("Puzzle Index ended", "setPuzzles() entered");
                mPuzzles.postValue(puzzles);
                return;
            }

        } catch (JSONException e) {
            Log.e("VOLLEY", e.getMessage());
        }

        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);


        ArrayList<String> pictureSetUrls = new ArrayList<String>();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                pUrl,
                null,
                response -> {
                    try {
                        mName = response.getString("name");

                        Log.e("NAME", mName);

                        fullLayoutUrl = baseLayoutUrl + response.getString("layout");

                        Log.d("parsePuzzleData()", fullLayoutUrl);


                        JSONArray pictureSets = response.getJSONArray("PictureSet");
                        for (int i = 0; i < pictureSets.length(); i++) {

                            String pictureUrl = (String) pictureSets.get(i);
                            String fullPictureUrl = baseImagesUrl + pictureUrl;

                            Log.d("parsePuzzleData()", fullPictureUrl);
                            pictureSetUrls.add(fullPictureUrl);
                        }

                        parseLayoutData(fullLayoutUrl, pictureSetUrls);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("VOLLEY", e.getMessage());
                    }
                },
                error -> {
                });

        queue.add(jsonObjectRequest);
    }

    private void parseLayoutData(String pUrl, ArrayList<String> pPictureSetUrls) {

        Log.e("PARSELAYOUTDATA()", "parseLayoutData(---) entered");

        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                pUrl,
                null,
                response -> {
                    try {
                        //Log.e("ROW", response.getString("rows"));
                        mRows = Integer.valueOf(response.getString("rows"));
                        //Log.e("COLUMNS", response.getString("columns"));
                        mColumns = Integer.valueOf(response.getString("columns"));

                        ArrayList<ArrayList<String>> layout = new ArrayList<ArrayList<String>>();
                        JSONArray jsonLayout = response.getJSONArray("layout");

                        for (int i = 0; i < jsonLayout.length(); i++) {
                            JSONArray jsonRow = jsonLayout.getJSONArray(i);
                            ArrayList<String> row = new ArrayList<String>();

                            for (int y = 0; y < jsonRow.length(); y++) {
                                String value = (String) jsonRow.get(y);
                                row.add(value);
                            }

                            layout.add(row);
                        }

                        mLayout = layout;

                        int dimensions = mRows * mColumns;

                        parsePictureSet(pPictureSetUrls, dimensions);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {

                });

        queue.add(jsonObjectRequest);
    }

    private void parsePictureSet(ArrayList<String> pUrls, int pDimensions) {

        Log.e("PARSEPICTURESET()", "parsePictureSet(---) entered");


        ArrayList<String> tilePaths = new ArrayList<String>();

        for (int i = 0; i < pUrls.size(); i++) {
            String fullPictureUrl;
            for (int y = 0; y == pDimensions; y++) {
                String imageFile = String.valueOf(y) + ".png";
                fullPictureUrl = pUrls.get(i) + imageFile;
                tilePaths.add(fullPictureUrl);
            }
        }

        mTilePaths = tilePaths;

        puzzles.add(makePuzzleObject());

        parsePuzzleData(puzzleDataIndex++);
    }

    private void loadTiles(ArrayList<String> pUrls, MutableLiveData<Puzzle> pPuzzleData) {
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        final MutableLiveData<Puzzle> mutablePuzzle = pPuzzleData;

        for (int i = 0; i < pUrls.size(); i++) {

            ImageRequest imageRequest = new ImageRequest(
                    pUrls.get(i), bitmap -> {
                Puzzle puzzle = mutablePuzzle.getValue();
                puzzle.addTile(bitmap);
                mutablePuzzle.setValue(puzzle);
            },
                    0, 0,
                    ImageView.ScaleType.CENTER_CROP,
                    Bitmap.Config.RGB_565,
                    error -> {
                        String errorResponse = "That didn't work!";
                        Log.e("loadTiles()", errorResponse);
                    });

            queue.add(imageRequest);
        }
    }

    private Puzzle makePuzzleObject() {
        Puzzle puzzle = new Puzzle(mName, mRows, mColumns, mLayout, mTilePaths);

        Log.e("makePuzzleObject()", "PuzzleObjectMade");

        if (mName == null) {
            Log.e("makePuzzleObject()", "mName is NULL");
        }

        return puzzle;
    }

    public MutableLiveData<ArrayList<Puzzle>> getPuzzles() {
        return mPuzzles;
    }
}
