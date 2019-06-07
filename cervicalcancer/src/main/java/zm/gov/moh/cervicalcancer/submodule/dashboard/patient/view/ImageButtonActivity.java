package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.common.collect.LinkedHashMultimap;

import org.threeten.bp.Instant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.IRecyclerViewClickListener;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.submodule.form.utils.MediaStorageUtil;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;

import static zm.gov.moh.cervicalcancer.R2.id.imageView;


/*public class ImageButtonActivity extends Fragment implements View.OnClickListener {


}*/