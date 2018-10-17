package com.example.cardholder.cards;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;

import com.example.cardholder.R;
import com.example.cardholder.base.BaseFragment;
import com.example.cardholder.model.CardModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.mvp_dev.image_cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewCardFragment extends BaseFragment {

    final int REQUEST_CODE_PHOTO = 0x2565;

    private Uri mUri;

    @BindView(R.id.image_card_view)
    CropImageView imageCardView;
    @BindView(R.id.text_code)
    EditText textCode;
    @BindView(R.id.card_name)
    EditText cardName;

    public static AddNewCardFragment getInstance() {
        AddNewCardFragment fragment = new AddNewCardFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO) {
            Uri selectedImage = mUri;
            getActivity().getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(cr, selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageCardView.setImageBitmap(bitmap);
        } else if (result != null) {
            String scanContent = result.getContents();
            textCode.setText(scanContent);
        }
    }

    @OnClick(R.id.button_scanner)
    public void onClickButtonScanner() {
        barCodeIntent();
    }

    @OnClick(R.id.add_card_icon)
    public void onClickAddCardIcon() {
        cameraIntent();
    }

    @OnClick(R.id.ok)
    public void onClickOk() {
        Bitmap bitmap = imageCardView.getCroppedImage();
        CardModel viewClass = new CardModel();
        viewClass.setName(cardName.getText().toString());
        viewClass.setPhoto(bitmap);
        viewClass.setCode(textCode.getText().toString());
        ((CardsActivity) getActivity()).addCard(viewClass);

        getActivity().onBackPressed();
    }

    @OnClick(R.id.cancel)
    public void onClickCancel() {
        getActivity().onBackPressed();
    }

    private void barCodeIntent() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        mUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName(), photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        getActivity().startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    @LayoutRes
    @Override
    protected int layoutResId() {
        return R.layout.fragment_card_add;
    }
}



