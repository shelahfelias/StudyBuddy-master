package adamson.studybuddy.gui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import adamson.studybuddy.R;
import adamson.studybuddy.logic.DatabaseHelper;
import adamson.studybuddy.logic.DatabaseHelperImpl;
import adamson.studybuddy.logic.Settings;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    @SuppressWarnings({"FieldNever", "unused"})
    private OnFragmentInteractionListener mListener;
    private Settings settings;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        settings = Settings.getInstance(view.getContext());
        initGui();
        initToolbarTitle();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_buttonSave:
                readGui();
                settings.saveSettings();
                Toast.makeText(getContext(), R.string.string_settings_saved, Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_buttonResetDB:
                Toast.makeText(getContext(), "dodo", Toast.LENGTH_SHORT).show();
                System.out.println("pressed");
                DatabaseHelper dbHelper = new DatabaseHelperImpl(getContext());
                System.out.println(dbHelper.toString());
                dbHelper.resetDatabase();
                System.out.println(dbHelper.toString());
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        @SuppressWarnings({"FieldNever", "unused"})
        void onFragmentInteraction(Uri uri);
    }

    //region private methods

    /**
     * method to initialise components of the GUI
     */
    private void initGui() {
        initSeekBar();
        initDateFormatSpinner();
        GuiHelper.defineButtonOnClickListener(view, R.id.settings_buttonSave, this);

    }

    /**
     * initialises the {@link SeekBar} which displays the {@link Settings#periodsAtDay}
     */
    private void initSeekBar() {
        GuiHelper.defineSeekBarOnChangeListener(view,
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        GuiHelper.setTextToTextView(view, R.id.settings_textviewSeekbarPeriodsPos, String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //ignore
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //ignore
                    }
                }
        ).setProgress(settings.getPeriodsAtDay());
    }

    /**
     * initialises the {@link Settings#DATE_FORMAT} {@link Spinner}
     */
    private void initDateFormatSpinner() {
        Spinner spinner = GuiHelper.fillSpinnerFromArray(view, R.id.settings_spinnerDate,
                new String[]{Settings.DATE_FORMAT_DDMMYYYY, Settings.DATE_FORMAT_MMDDYYYY, Settings.DATE_FORMAT_YYYYMMDD});
        switch (settings.getActiveDateFormat()) {
            case Settings.DATE_FORMAT_DDMMYYYY:
                spinner.setSelection(0);
                break;
            case Settings.DATE_FORMAT_MMDDYYYY:
                spinner.setSelection(1);
                break;
            case Settings.DATE_FORMAT_YYYYMMDD:
                spinner.setSelection(2);
        }
    }

    /**
     * updates {@link SettingsFragment#settings} with values in GUI
     */
    private void readGui() {
        SeekBar seekBar = view.findViewById(R.id.settings_seekbarPeriods);
        settings.setPeriodsAtDay(seekBar.getProgress());

        Spinner spinner = view.findViewById(R.id.settings_spinnerDate);
        settings.setActiveDateFormat((String) spinner.getSelectedItem());
    }

    /**
     * method to adjust appbar title for selected fragment
     */

    private void initToolbarTitle() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.string_settings);
    }
    //endregion
}
