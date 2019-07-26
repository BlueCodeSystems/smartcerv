/*package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
*/
/*public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback
{
    CameraActivity camera;
    SurfaceHolder holder;
    public ShowCamera(Context context,CameraActivity camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
            CameraActivity.Parameters params = camera.getParameters();

            // change the orientation of the camera..

        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE)
        {
            params.set("orientation","")
        }
    }
}*/


