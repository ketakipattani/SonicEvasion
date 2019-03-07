package com.jonas.stopcollaboratelisten;

/*
 * 	This is an Android application based on the pentatonic codec
 *  available at:
 *    http://github.com/diva/digital-voices
 *    
 *  The app uses an acoustic channel for device-to-device communication.
 *  When a broadcast is incorrectly decoded the receiver will signal
 *  for help at which point another device with the correct broadcast
 *  will automatically lend a hand.
 * 
 * Modfied April 2012 by jonasrmichel@mail.utexas.edu
 * 
 * Usage notes:
 * 
 *  - Type something in and hit Play to have it encoded and played
 *  
 *  - Hit Listen to start a collaborative listen session
 *  	A status message will appear below the Listen button.
 *  	When input is decoded, it'll show up below that.
 *  
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jonas.stopcollaboratelisten.SessionService.SessionStatus;

public class Main extends Activity {
	/** Called when the activity is first created. */
	// Loopback l = null;

	private SessionService mSessionService;
	private boolean mIsBound;

	private ServiceConnection mSessionConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			mSessionService = ((SessionService.SessionBinder) service).getService();
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			mSessionService = null;
		}
	};

	void doBindService() {
		// Establish a connection with the service. We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).
		bindService(new Intent(this, SessionService.class), mSessionConnection,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			unbindService(mSessionConnection);
			mIsBound = false;
		}
	}

	Timer refreshTimer = null;

	Handler mHandler = new Handler();

	TextView textStatus, textListen;

	List<RadioButton> radioButtons = new ArrayList<RadioButton>();

	Uri mCreateDataUri = null;
	String mCreateDataType = null;
	String mCreateDataExtraText = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textStatus = (TextView) findViewById(R.id.TextStatus);
		textListen = (TextView) findViewById(R.id.TextListen);

		Button t = (Button) findViewById(R.id.ButtonPlay);
		t.setOnClickListener(mPlayListener);

		t = (Button) findViewById(R.id.ButtonListen);
		t.setOnClickListener(mListenListener);

		radioButtons.add((RadioButton) findViewById(R.id.RadioPlaying));
		radioButtons.add((RadioButton) findViewById(R.id.RadioListening));
		radioButtons.add((RadioButton) findViewById(R.id.RadioHelping));
		radioButtons.add((RadioButton) findViewById(R.id.RadioSOS));
		radioButtons.add((RadioButton) findViewById(R.id.RadioFinished));

String s = this.getUniqueIMEIId(getBaseContext());
final EditText text1 = (EditText) findViewById(R.id.EditTextToPlay);
text1.setText(s);

		
		for (RadioButton button : radioButtons) {
			button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked)
						processRadioButtonClick(buttonView);
				}
			});
		}

		final Intent intent = getIntent();
		final String action = intent.getAction();
		if (Intent.ACTION_SEND.equals(action)) {

			mCreateDataUri = intent.getData();
			mCreateDataType = intent.getType();

			if (mCreateDataUri == null) {
				mCreateDataUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);

			}

			mCreateDataExtraText = intent.getStringExtra(Intent.EXTRA_TEXT);

			if (mCreateDataUri == null)
				mCreateDataType = null;

			// The new entry was created, so assume all will end well and
			// set the result to be returned.
			setResult(RESULT_OK, (new Intent()).setAction(null));
		}

		doBindService();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

	View.OnClickListener mPlayListener = new View.OnClickListener() {
		public void onClick(View v) {
			EditText e = (EditText) findViewById(R.id.EditTextToPlay);
			String s = e.getText().toString();
			String str =s ;//.substring(0,3);
			
		//e.setText(str);
		
		mSessionService.startSession(str);
			/*int i=0,j=3;
			
			for(int ij=0;ij<5;ij++){
				
				String str =s.substring(i,j);
				i=i+3;j=j+3;
			e.setText(str);
			
			mSessionService.startSession(str);
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}*/
		}
	};

	View.OnClickListener mListenListener = new View.OnClickListener() {
		public void onClick(View v) {
			mSessionService.sessionReset();
			if (mSessionService.getStatus() == SessionStatus.NONE
					|| mSessionService.getStatus() == SessionStatus.FINISHED) {
				mSessionService.listen();
				((Button) v).setText("Stop listening");
			} else {
				mSessionService.stopListening();
				((Button) v).setText("Listen");
			}

		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {

		// if( l != null )
		// l.stopLoop();

		super.onPause();

		if (refreshTimer != null) {
			refreshTimer.cancel();
			refreshTimer = null;
		}

		mSessionService.stopListening();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		String sent = null;

		if (mCreateDataExtraText != null) {
			sent = mCreateDataExtraText;
		} else if (mCreateDataType != null
				&& mCreateDataType.startsWith("text/")) {
			// read the URI into a string

			byte[] b = readDataFromUri(this.mCreateDataUri);
			if (b != null)
				sent = new String(b);

		}

		if (sent != null) {
			EditText e = (EditText) findViewById(R.id.EditTextToPlay);
			e.setText(sent);
		}

		refreshTimer = new Timer();

		refreshTimer.schedule(new TimerTask() {
			@Override
			public void run() {

				mHandler.post(new Runnable() // have to do this on the UI thread
				{
					public void run() {
						updateResults();
					}
				});

			}
		}, 500, 500);

	}

	private void processRadioButtonClick(CompoundButton buttonView) {
		for (RadioButton button : radioButtons) {
			if (button != buttonView)
				button.setChecked(false);
		}
	}

	private void setRadioGroupUnchecked() {
		for (RadioButton button : radioButtons) {
			button.setChecked(false);
		}
	}

	private void setRadioGroupChecked(SessionStatus s) {
		RadioButton rb = null;
		switch (s) {
		case PLAYING:
			rb = (RadioButton) findViewById(R.id.RadioPlaying);
			break;
		case LISTENING:
			rb = (RadioButton) findViewById(R.id.RadioListening);
			break;
		case HELPING:
			rb = (RadioButton) findViewById(R.id.RadioHelping);
			break;
		case SOS:
			rb = (RadioButton) findViewById(R.id.RadioSOS);
			break;
		case FINISHED:
			rb = (RadioButton) findViewById(R.id.RadioFinished);
			break;
		case NONE:
			setRadioGroupUnchecked();
			return;
		}
		rb.setChecked(true);
	}

	private void updateResults() {
		if (mSessionService.getStatus() == SessionStatus.LISTENING) {
			textStatus.setText(mSessionService.getBacklogStatus());
			textListen.setText(mSessionService.getListenString());
			
			Button b = (Button) findViewById(R.id.ButtonListen);
			b.setText("Stop listening");
		} else if (mSessionService.getStatus() == SessionStatus.FINISHED) {
			Button b = (Button) findViewById(R.id.ButtonListen);
			b.setText("Listen");
			textStatus.setText("");
		} else {
			textStatus.setText("");
		}
		setRadioGroupChecked(mSessionService.getStatus());
	}

	/*
	 * private void encode( String inputFile, String outputFile ) {
	 * 
	 * try {
	 * 
	 * //There was an output file specified, so we should write the wav
	 * System.out.println("Encoding " + inputFile);
	 * AudioUtils.encodeFileToWav(new File(inputFile), new File(outputFile));
	 * 
	 * } catch (Exception e) { System.out.println("Could not encode " +
	 * inputFile + " because of " + e); }
	 * 
	 * }
	 */

	private byte[] readDataFromUri(Uri uri) {
		byte[] buffer = null;

		try {
			InputStream stream = getContentResolver().openInputStream(uri);

			int bytesAvailable = stream.available();
			// int maxBufferSize = 1024;
			int bufferSize = bytesAvailable; // Math.min(bytesAvailable,
												// maxBufferSize);
			int totalRead = 0;
			buffer = new byte[bufferSize];

			// read file and write it into form...
			int bytesRead = stream.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				bytesRead = stream.read(buffer, totalRead, bufferSize);
				totalRead += bytesRead;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return buffer;
	}

	
	 public String getUniqueIMEIId(Context context) {
		    try {
		        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		        
		        BigInteger imei = new BigInteger(telephonyManager.getDeviceId().toString());
		        String binaryX = imei.toString(2);
		        String R = "";	
		      //  Log.e("imei", "=" + imei);
		        int num = 0;
				for (int j = 0; j < binaryX.length(); j++) {
					
					int n = getRandomNumberInRange(3, 20);
					
					if (binaryX.charAt(j) == '1') {
						
						    int candidate, count;
						    for(candidate = 2, count = 0; count < n; ++candidate) {
						        if (isPrime(candidate)) {
						            ++count;
						        }
						    }
						    num=candidate-1;
						   // System.out.println("1: "+n+"th Prime: "+(num));
						    // The candidate has been incremented once after the count reached n
						   
					  }//if ends
					
					else if(binaryX.charAt(j) == '0') {
						 int candidate, count;
						    for(candidate = 2, count = 0; count < n; ++candidate) {
						        if (isComposite(candidate)) {
						            ++count;
						        }
						    }
						    num=candidate-1;
						    //System.out.println("0: "+n+"th Composite: "+(num));
						    // The candidate has been incremented once after the count reached n		
					
					}
				
				
					
					if (isPrime(num)) {
						R += "1";
						//flag.set(i, true);
					} else if (isComposite(num)){
						R += "0";
						//flag.set(i, true);
					}
				}
				
		        if (R != null && !R.isEmpty()) {
		            return R;
		        } else {
		            return android.os.Build.SERIAL;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return "not_found";
		}
	 private static boolean isPrime(int n) {
		    for(int i = 2; i < n; ++i) {
		        if (n % i == 0) {
		            // We are naive, but not stupid, if
		            // the number has a divisor other
		            // than 1 or itself, we return immediately.
		            return false;
		        }
		    }
		    return true;
		}
		
		private static boolean isComposite(int n) {
			
		        // Corner cases 
		        if (n <= 1)  
		        //System.out.println("False"); 
		          
		        if (n <= 3)  
		        //System.out.println("False"); 
		  
		        // This is checked so that we can skip  
		        // middle five numbers in below loop 
		        if (n % 2 == 0 || n % 3 == 0) return true; 
		  
		        for (int i = 5; i * i <= n; i = i + 6) {
		            if (n % i == 0 || n % (i + 2) == 0) 
		            return true; 
		        }
		        return false; 
		   
		}
		
		private static int getRandomNumberInRange(int min, int max) {

			if (min >= max) {
				throw new IllegalArgumentException("max must be greater than min");
			}

			Random r = new Random();
			return r.nextInt((max - min) + 1) + min;
		}

}