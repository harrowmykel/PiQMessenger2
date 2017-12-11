package ng.com.coursecode.piqmessenger.ExtLib;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import br.com.goncalves.pugnotification.notification.PugNotification;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Searches.ConvoSearchAct;

/**
 * Created by harro on 24/11/2017.
 */

public class GoogleUpload {
    Context context;
    Uri tempUri=Uri.EMPTY;
    int NOT_INT=227332;
    int small_icon=R.drawable.group_error;
    Stores stores;
    String Store_loc;
    GoogleUploadListener googleUploadListener;
    PendingIntent pendingIntent;

    public GoogleUpload() {

    }

    public GoogleUpload(Context context1, String loc1, int NOT_INT1, int small_icon1, Uri tempUri1, GoogleUploadListener googleUploadListener1) {
        context=context1;
        Store_loc=loc1;
        NOT_INT=NOT_INT1;
        small_icon=small_icon1;
        tempUri=tempUri1;
        googleUploadListener=googleUploadListener1;
        stores=new Stores(context);
        Intent intent1=new Intent(context, context.getClass());
        pendingIntent=PendingIntent.getActivity(context, NOT_INT, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void alert(final int Resid) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), tempUri);
            PugNotification.with(context)
                    .load()
                    .identifier(NOT_INT)
                    .title(Resid)
                    .smallIcon(small_icon)
                    .largeIcon(bitmap)
                    .click(pendingIntent)
                    .flags(Notification.DEFAULT_ALL)
                    .autoCancel(true)
                    .simple()
                    .build();
        } catch (Exception e) {
            Stores._reportException(e, "editprof", context);
        }
    }

    public void sendToGoogle() {
        // File or Blob
        final Uri file = tempUri;

// Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(Stores.CLOUD_URL);

// Upload file and metadata to the path 'images/mountains.jpg'
        UploadTask uploadTask = storageRef.child(stores.getFirebaseStore(Store_loc) + file.getLastPathSegment()).putFile(file, metadata);

// Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), tempUri);

                    PugNotification.with(context)
                            .load()
                            .identifier(NOT_INT)
                            .title(R.string.uploading_status)
                            .smallIcon(small_icon)
                            .largeIcon(bitmap)
                            .click(pendingIntent)
                            .progress()
                            .value(progress, 100, false)
                            .build();
                } catch (Exception e) {
                    Stores._reportException(e, "Createstatus", context);
                }
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                alert(R.string.upload_paused);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

                alert(R.string.upload_failed);
                googleUploadListener.onError();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                alert(R.string.upload_successful);
                googleUploadListener.onSuccess(downloadUrl);
            }
        });
    }

    public interface GoogleUploadListener{
        void onError();
        void onSuccess(Uri uri);
    }
}
