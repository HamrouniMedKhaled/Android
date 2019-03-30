package pfe.mobilebanking.atbmobile;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity {
	
	private static final long DELAY = 3000;
    private boolean scheduled = false;
    private Timer splashTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		splashTimer = new Timer();
        splashTimer.schedule(new TimerTask()
        {
            public void run()
            {
                SplashActivity.this.finish();
                startActivity(new Intent(SplashActivity.this, AccueilActivity.class));
            }
         }, DELAY);
       scheduled = true;
		
	}

	@Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (scheduled)
            splashTimer.cancel();
        splashTimer.purge();
    }

}
