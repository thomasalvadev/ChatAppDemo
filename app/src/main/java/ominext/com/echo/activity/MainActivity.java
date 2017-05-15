package ominext.com.echo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ominext.com.echo.R;
import ominext.com.echo.fragment.LoginFragment;
import ominext.com.echo.model.UserInfo;
import ominext.com.echo.utils.ConfigManager;
import ominext.com.echo.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        ConfigManager configManager = new ConfigManager(this);
        UserInfo userInfo = configManager.getUserInfoShared(Constants.KEY_USER);
        if (userInfo != null && userInfo.getAccessToken() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fr_content, LoginFragment.getInstance()).addToBackStack(null).commit();
        }
    }
}
