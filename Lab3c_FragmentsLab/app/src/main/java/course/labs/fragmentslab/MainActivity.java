package course.labs.fragmentslab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// If the layout is single-pane, create the FriendsFragment 
		// and add it to the Activity

		if (!isInTwoPaneMode()) {
			
			mFriendsFragment = new FriendsFragment();

			//add the FriendsFragment to the fragment_container

			FragmentManager fragmentManager = getFragmentManager();
			if(fragmentManager!=null) {
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.fragment_container, mFriendsFragment);
				fragmentTransaction.commit();
			}
			else{
				System.out.println("fragmentManager is null");
			}
			
			

		} else {

			// Otherwise, save a reference to the FeedFragment for later use

			mFeedFragment = (FeedFragment) getFragmentManager()
					.findFragmentById(R.id.feed_frag);
		}

	}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// If there is no FeedFragment instance, then create one

		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {

			//replace the fragment_container with the FeedFragment
			// Create new fragment and transaction
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.fragment_container, mFeedFragment);
			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();

			// execute transaction now
			fragmentManager.executePendingTransactions();

		}

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}