package us.prontotype.btctest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.Random;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;

public class WalletService extends Service {

    private static Address forwardingAddress;
    private static WalletAppKit kit;
    private static Thread thread;
    private final Random gen = new Random();

    @Override
    public void onCreate() {
        System.out.println("TEST \n TEST TEST TEST \t TEST TEST \n\n TEST \n TEST TEST");
        new LinuxSecureRandom();
        try {
            final long start = System.currentTimeMillis();
            String BIP39_WORDLIST_FILENAME = "bip39-wordlist.txt";
            MnemonicCode.INSTANCE = new MnemonicCode(getAssets().open(BIP39_WORDLIST_FILENAME), null);
            Log.v("WalletService OnCreate", "BIP39 wordlist loaded from: '" + BIP39_WORDLIST_FILENAME + "', took " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            System.out.println("ERROR ERROR ERROR " + e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gen.nextInt(110);
                    NetworkParameters params = MainNetParams.get();
                    forwardingAddress = new Address(params, "1sproFExWZY5GnyjHpB6kVFznDTpFQ7gm");
                    kit = new WalletAppKit(params, getFilesDir(), "btc-forward-test-2");// {
                        // @Override
                        // protected void onSetupCompleted() {
                        //     kit.setBlockingStartup(false);
                        //     kit.setDownloadListener(new DownloadListener() {
                        //         @Override
                        //         protected void startDownload(int blocks) {
                        //             Log.v("[ M Y D O W N L O A D ]", "startDownload");
                        //         }

                        //         @Override
                        //         protected void progress(double pct, int blocksSoFar, Date date) {
                        //             Log.v("[ M Y D O W N L O A D ]", "progress =" + pct + "% on " + date.toString());
                        //         }
                        //     });
                        // }
                    // };
                    kit.startAsync();
                    kit.awaitRunning();

                    Address sendToAddress = kit.wallet().currentReceiveKey().toAddress(params);
                    System.out.println("Send coins to: " + sendToAddress);
                } catch (Exception e) {
                    System.out.println("ERROR ERROR ERROR " + e);

                }
            }
        });
        thread.start();

        return super.onStartCommand(intent, flags, startId);
        // return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
