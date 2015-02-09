package us.prontotype.btctest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BTCTestHomeActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent walletServiceIntent = new Intent(this, WalletService.class);
        startService(walletServiceIntent);

    }
}
