package com.reactnativecomponent.barcode;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.reactnativecomponent.barcode.decoding.DecodeUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RCTCaptureModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    RCTCaptureManager captureManager;


    public RCTCaptureModule(ReactApplicationContext reactContext, RCTCaptureManager captureManager) {
        super(reactContext);
        mContext = reactContext;

        this.captureManager = captureManager;

    }

    @Override
    public String getName() {
        return "CaptureModule";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("barCodeTypes", getBarCodeTypes());
            }

            private Map<String, Object> getBarCodeTypes() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("upce", BarcodeFormat.UPC_E.toString());
                        put("code39", BarcodeFormat.CODE_39.toString());
//                        put("code39mod43",BarcodeFormat. );
                        put("ean13", BarcodeFormat.EAN_13.toString());
                        put("ean8", BarcodeFormat.EAN_8.toString());
                        put("code93", BarcodeFormat.CODE_93.toString());
                        put("code128", BarcodeFormat.CODE_128.toString());
                        put("pdf417", BarcodeFormat.PDF_417.toString());
                        put("qr", BarcodeFormat.QR_CODE.toString());
                        put("aztec", BarcodeFormat.AZTEC.toString());
//                        put("interleaved2of5", BarcodeFormat.);
                        put("itf14", BarcodeFormat.ITF.toString());
                        put("datamatrix", BarcodeFormat.DATA_MATRIX.toString());
                    }


                });
            }
        });
    }


    @ReactMethod
    public void startSession() {

        if (captureManager.cap != null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                public void run() {
                    captureManager.cap.startQR();
                }
            });
        }
    }


    @ReactMethod
    public void stopSession() {
        if (captureManager.cap != null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                public void run() {
                    captureManager.cap.stopScan();
                }
            });
        }
    }

    @ReactMethod
    public void stopFlash() {
        if (captureManager.cap != null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                public void run() {
                    captureManager.cap.CloseFlash();
                }
            });
        }
    }

    @ReactMethod
    public void startFlash() {
        if (captureManager.cap != null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                public void run() {
                    captureManager.cap.OpenFlash();
                }
            });
        }
    }

    @ReactMethod
    public void DecodeFromPath(final String path,
                               final Callback errorCallback,
                               final Callback successCallback) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String resultStr;
                    resultStr = DecodeUtil.getStringFromQRCode(path);
                    successCallback.invoke(resultStr);
                } catch (NotFoundException e) {
                    // 无法识别图片中的二维码
                    e.printStackTrace();
                    errorCallback.invoke("无法识别图片中的二维码");
                } catch (Exception e) {
                    e.printStackTrace();
                    errorCallback.invoke(e.getMessage());
                }
            }
        }).start();
    }


/*
    @ReactMethod
    public void changeWidthHeight(final int width,final int height) {

        if (captureManager.cap != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    captureManager.cap.setCHANGE_WIDTH(width, height);
                }
                });
        }
    }*/
}
