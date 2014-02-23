package de.repat.mosf;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ToggleButton;
import android.app.Activity;
import android.content.pm.ActivityInfo;

public class Flashlight extends Activity {
    private Camera cam;
    private Parameters p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // portrait Mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // delete title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // start GUI
        setContentView(R.layout.activity_flashlight);
    }

    public void onToggleLEDClicked(View v) {
        // Button pressed
        boolean isChecked = ((ToggleButton) v).isChecked();

        // turn off
        if (!isChecked) {
            if (cam != null) {
                p.setFlashMode(Parameters.FLASH_MODE_OFF);
                cam.setParameters(p);
                cam.stopPreview();
                cam.setPreviewCallback(null);
                cam.release();
                cam = null;
            }

        }
        // turn on
        else {
            if (cam == null) {
                try {
                    cam = Camera.open();
                    // Yeah, this could be more specific maybe.
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    finish();
                    return;
                }
                p = cam.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        }
    }

}
