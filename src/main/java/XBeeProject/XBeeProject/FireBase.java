package XBeeProject.XBeeProject;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBase {
	public FireBase() {
		try {
			
			FirebaseOptions options = FirebaseOptions.builder()
			    .setCredentials(GoogleCredentials.getApplicationDefault())
			    .setDatabaseUrl("https://xbeeproject-1f5f9-default-rtdb.firebaseio.com/")
			    .build();

			FirebaseApp.initializeApp(options);
			
			DatabaseReference ref = FirebaseDatabase.getInstance()
			    .getReference("restricted_access/secret_document");
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				
			  public void onDataChange(DataSnapshot dataSnapshot) {
			    Object document = dataSnapshot.getValue();
			    System.out.println(document);
			  }

			  public void onCancelled(DatabaseError error) {
			  }
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
