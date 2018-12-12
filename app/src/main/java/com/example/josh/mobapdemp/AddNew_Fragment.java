package com.example.josh.mobapdemp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddNew_Fragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseCR;
    private DatabaseReference databaseUser;
    private EditText CrNameText;
    private EditText CrLocationText;
    private Button AddRoom;
    private ImageView crImage;
    private Button selectImage;
    private int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private Uri uri;
    private Bitmap bitmap;
    private int exp;
    private String userID;
    private CheckBox Bidet;
    private CheckBox Aircon;
    private CheckBox ToiletSeat;
    private CheckBox TissuePaper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CrNameText = view.findViewById(R.id.editTextCrName);
        CrLocationText = view.findViewById(R.id.editTextCrLocation);
        AddRoom = view.findViewById(R.id.buttonAddRoom);
        selectImage = view.findViewById(R.id.selectImage);
        crImage = view.findViewById(R.id.crImageView);
        Bidet = view.findViewById(R.id.checkBox);
        Aircon = view.findViewById(R.id.checkBox2);
        ToiletSeat = view.findViewById(R.id.checkBox3);
        TissuePaper = view.findViewById(R.id.checkBox4);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
        databaseCR = FirebaseDatabase.getInstance().getReference("CR");
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference();

        uri = null;

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            AddNew_Fragment.this.startActivity(intent);
        }

        AddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCr();
            }
        });
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public void addCr() {
        final String userEmail = firebaseAuth.getCurrentUser().getEmail();
        final String CrName = CrNameText.getText().toString().trim();
        final String CrLocation = CrLocationText.getText().toString().trim();
        final Boolean hasBidet = Bidet.isChecked();
        final Boolean hasAircon = Aircon.isChecked();
        final Boolean hasToiletSeat = ToiletSeat.isChecked();
        final Boolean hasTissue = TissuePaper.isChecked();

        if (!TextUtils.isEmpty(CrName) && !TextUtils.isEmpty(CrLocation) && uri!=null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            final String id = databaseCR.push().getKey();
            FirebaseUser user = firebaseAuth.getCurrentUser();
                final StorageReference filepath = storageReference.child("CRPhotos" + System.currentTimeMillis() + "." + getImageExt(uri));
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the uri
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "CR added!", Toast.LENGTH_SHORT).show();
                                CrModel cr = new CrModel(CrName, CrLocation, id, uri.toString(), hasBidet, hasAircon, hasToiletSeat, hasTissue);
                                databaseCR.child(id).setValue(cr);
                                // Wrap with Uri.parse() when retrieving
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getActivity(), "No photo", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });
                databaseUser.child(userID).child("exp").setValue(exp + 5);
                Toast.makeText(getActivity(), "Earned 5xp for adding a Comfort Room!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Empty Inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView crView = getView().findViewById(R.id.crImageView);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                crView.setImageBitmap(bitmap);
                crView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void onStart() {
        super.onStart();
        databaseUser.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel userModel = dataSnapshot.getValue(userModel.class);
                exp = userModel.exp;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
