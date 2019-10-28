package zm.gov.moh.common.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.common.R;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.entity.derived.DrawerProvider;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

import static zm.gov.moh.core.model.Key.PERSON_ADDRESS;

public class BaseActivity extends AppCompatActivity {


    protected static final String START_SUBMODULE_KEY = "start_submodule";
    public static final String CLIENT_ID_KEY = "PERSON_ID";
    public static final String JSON_FORM = "JSON_FORM";
    public static final String ACTION_KEY = "ACTION_KEY";
    protected LocalBroadcastManager broadcastManager;
    protected static BaseReceiver baseReceiver;
    protected BaseAndroidViewModel viewModel;
    protected ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    protected ListView drawerList;
    protected String[] layers;
    protected ProgressDialog progressDialog;
    BaseEventHandler toolBarEventHandler;

    protected Drawer drawer;

    Toolbar toolbar;
    String firstname,lastname,userName;
    AccountHeader provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawerLayout != null) {
            toolbar = findViewById(R.id.base_toolbar);

            drawerToggle = new BaseActionBarDrawerToggle((Activity) this, drawerLayout, toolbar, 0, 0) {
                public void onDrawerClosed(View view) {
                    getActionBar().setTitle(R.string.app_name);
                }

                public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle("cool");
                }
            };
            drawerLayout.setDrawerListener(drawerToggle);
        }



        if(baseReceiver == null) {
            baseReceiver = new BaseReceiver();
            broadcastManager = LocalBroadcastManager.getInstance(this);

            IntentFilter intentFilter = new IntentFilter(IntentAction.INSUFFICIENT_IDENTIFIERS_FAILED_REGISTRATION);
            IntentFilter syncIntentFilter = new IntentFilter(IntentAction.REMOTE_SYNC_COMPLETE);


            broadcastManager.registerReceiver(baseReceiver, intentFilter);
            broadcastManager.registerReceiver(baseReceiver, syncIntentFilter);
            broadcastManager.registerReceiver(baseReceiver, new IntentFilter(IntentAction.REMOTE_SERVICE_INTERRUPTED));
            broadcastManager.registerReceiver(baseReceiver, new IntentFilter(IntentAction.REMOTE_SERVICE_METADATA_SYNC_COMPLETE));
        }
    }

    public void startModule(Module module, Bundle bundle) {

        Intent intent = new Intent(this, module.getClassInstance());

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void startModule(String moduleName, Bundle bundle) {

        BaseApplication baseApplication = (BaseApplication) getApplication();

        Intent intent = new Intent(this, baseApplication.getModule(moduleName).getClassInstance());

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void startModule(String moduleName) {

        BaseApplication baseApplication = (BaseApplication) getApplication();
        Intent intent = new Intent(this, baseApplication.getModule(moduleName).getClassInstance());
        this.startActivity(intent);
    }

    public void startModule(Module module) {

        this.startActivity(new Intent(this, module.getClassInstance()));
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public BaseEventHandler getToolbarHandler(Context context){

        if(toolBarEventHandler == null)
         toolBarEventHandler = new BaseEventHandler(context);
        return toolBarEventHandler;
    }

    public BaseAndroidViewModel getViewModel() {
        return viewModel;
    }

    protected void setViewModel(BaseAndroidViewModel viewModel) {
        this.viewModel = viewModel;
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
            viewModel.setBundle(bundle);
    }

    public void initBundle(Bundle bundle) {

        final long SESSION_LOCATION_ID = getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);
        final long PROVIDER_ID = getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(Key.PROVIDER_ID, 1);
        bundle.putLong(Key.LOCATION_ID, SESSION_LOCATION_ID);

        Long personId = bundle.getLong(Key.PERSON_ID);
        bundle.putLong(Key.PROVIDER_ID, PROVIDER_ID);

        if (personId != null) {

            getViewModel()
                    .getRepository()
                    .getDatabase()
                    .clientDao()
                    .findById(personId)
                    .observe(this, client -> {


                        bundle.putString(Key.PERSON_GIVEN_NAME, client.getGivenName());
                        bundle.putString(Key.PERSON_FAMILY_NAME,client.getFamilyName());

                        //Added to pass client gender with bundle
                        bundle.putString(Key.PERSON_GENDER, client.getGender());
                        //Added to pass client age with bundle

                        bundle.putString(Key.PERSON_AGE, calculateClientAge(client.getBirthDate()).toString());
                        bundle.putString(Key.PERSON_DOB, client.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


                    });

            getViewModel()
                    .getRepository()
                    .getDatabase()
                    .personAddressDao()
                    .findByPersonIdObservable(personId)
                    .observe(this, personAddress -> {

                        bundle.putString(PERSON_ADDRESS,(personAddress != null)?personAddress.getAddress1()+" "+personAddress.getCityVillage()+" "+personAddress.getStateProvince():this.getResources().getString(R.string.unknown));

                    });
        }
    }

    public class BaseActionBarDrawerToggle extends ActionBarDrawerToggle {

        public BaseActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }
    }

    public void showProgressIndicator(String label){
        progressDialog = Utils.showProgressDialog(this, label);
    }

    public void dismissProgressIndicator(){
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    //Calculate client age as Integer
    public Integer calculateClientAge(LocalDateTime birthdate) {
        //String.valueOf(LocalDate.now().getYear - client.birthdate.getYear())+`years`}"
        int age = LocalDateTime.now().getYear() - birthdate.getYear();
        return age;
    }

    public void addDrawer(Activity activity)
    {
        Long providerId;
        providerId=getViewModel().getRepository().getDefaultSharePrefrences().getLong(Key.PROVIDER_ID,0);
       //setup the header of the navigation bar
      getViewModel().getRepository().getDatabase().providerUserDao().getDrawerUser(providerId).observe(this, new Observer<DrawerProvider>() {
          @Override
          public void onChanged(DrawerProvider drawerProvider) {

              buildDrawer(BaseActivity.this, drawerProvider);

          }
      });



    }


    public void buildDrawer(Activity activity, DrawerProvider drawerProvider){

        //navigation names with their icons
        PrimaryDrawerItem home=new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.home_icon);
        PrimaryDrawerItem allClients=new PrimaryDrawerItem().withIdentifier(2).withName("Records").withIcon(R.drawable.file_icon);
        PrimaryDrawerItem logOut=new PrimaryDrawerItem().withIdentifier(3).withName("Log out").withIcon(R.drawable.ic_logout);


        if (drawerProvider==null)
        {
            provider=new AccountHeaderBuilder().withActivity(activity).addProfiles(new ProfileDrawerItem().withName("User").withIcon(getResources().getDrawable(R.drawable.ic_openmrs))).build();
        }else
        {
            //get image of initials from api
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                    Glide.with(imageView.getContext()).load("https://ui-avatars.com/api/?name="+drawerProvider.getFirstName()+"+"+drawerProvider.getLastName()).diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(placeholder).into(imageView);
                }

                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                    super.set(imageView, uri, placeholder, tag);
                }

                @Override
                public void cancel(ImageView imageView) {
                    super.cancel(imageView);
                }

                @Override
                public Drawable placeholder(Context ctx) {
                    return super.placeholder(ctx);
                }

                @Override
                public Drawable placeholder(Context ctx, String tag) {
                    return super.placeholder(ctx, tag);
                }
            });
            //create the navigation header
            provider=new AccountHeaderBuilder().withActivity(activity).addProfiles(new ProfileDrawerItem().withName(drawerProvider.getUserName()).withEmail(drawerProvider.getFirstName()+" "+drawerProvider.getLastName()).withIcon("https://ui-avatars.com/api/?name=Open+mrs")).build();
        }

        drawer = new DrawerBuilder()
                .withActivity(activity).withCloseOnClick(true)
                .addDrawerItems(home,allClients,logOut).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier()==1)
                        {
                            startModule(BaseApplication.CoreModule.HOME);

                        }else if(drawerItem.getIdentifier()==2)
                        {
                            startModule(BaseApplication.CoreModule.REGISTER);
                        }
                        else if(drawerItem.getIdentifier()==3)
                        {
                            startModule(BaseApplication.CoreModule.BOOTSTRAP);
                        }
                        return  false;
                    }
                }).withAccountHeader(provider).build();
    }



}
