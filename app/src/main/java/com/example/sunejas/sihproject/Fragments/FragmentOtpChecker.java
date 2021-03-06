package com.example.sunejas.sihproject.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sunejas.sihproject.R;
import com.example.sunejas.sihproject.Utilities.IncomingSms;
import com.example.sunejas.sihproject.Utilities.SmsListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FragmentOtpChecker extends DialogFragment {
    EditText et1, et2, et3, et4, et5, et6;
    TextView submitOtp, resendOtp;
    String otp,otpSms;
    private String otpNum;
    private String phoneNum;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ProgressBar bar;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_otp_checker, container, false);
        phoneNum = getArguments().getString("phone");

        if(mContext == null){
            mContext = getActivity();
        }

        bar = (ProgressBar) rootView.findViewById(R.id.pb_otp);
        et1 = (EditText) rootView.findViewById(R.id.et_otp_dig_1);
        et2 = (EditText) rootView.findViewById(R.id.et_otp_dig_2);
        et3 = (EditText) rootView.findViewById(R.id.et_otp_dig_3);
        et4 = (EditText) rootView.findViewById(R.id.et_otp_dig_4);
        et5 = (EditText) rootView.findViewById(R.id.et_otp_dig_5);
        et6 = (EditText) rootView.findViewById(R.id.et_otp_dig_6);
        submitOtp = (TextView) rootView.findViewById(R.id.tv_check_otp);
        resendOtp = (TextView) rootView.findViewById(R.id.tv_otp_resend);

        addOnTextChangeListener();
        otpNum = getOTP();
        sendSMS(phoneNum);
        submitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               checkOtp();
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(phoneNum);
            }
        });

        return rootView;
    }

    private String getOTP() {
        //get otp
        Random rnd = new Random();
        Integer num = 100000 + rnd.nextInt(900000);
        return num.toString();
    }

    private void sendSMS(final String phone) {
        bar.setVisibility(View.VISIBLE);
        String url = getResources().getString(R.string.send_sms_api);
        url=url+phoneNum +"/" + otpNum + "/culmyca18";
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest smsReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        // TODO
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO
                bar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                return params;
            }
        };
        queue.add(smsReq);
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            bar.setVisibility(View.GONE);
        } else {
            IncomingSms.bindListener(new SmsListener() {
                @Override
                public void messageReceived(String messageText) {
                    if (messageText.contains("Culmyca")) {
                        otpSms = messageText.substring(0, 6);
                        if (otpSms.length() == 6) {
                            et1.setText(otpSms.substring(0, 1));
                            et2.setText(otpSms.substring(1, 2));
                            et3.setText(otpSms.substring(2, 3));
                            et4.setText(otpSms.substring(3, 4));
                            et5.setText(otpSms.substring(4, 5));
                            et6.setText(otpSms.substring(5, 6));
                        } else {
                            bar.setVisibility(View.GONE);
                        }
                    } else {
                        bar.setVisibility(View.GONE);
                    }
                }
            });
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bar.setVisibility(View.GONE);
                    et1.requestFocus();
                }
            }, 10000);
        }
    }


    private void addOnTextChangeListener() {
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et2.requestFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et3.requestFocus();
                } else if (s.toString().length() == 0) {
                    et2.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et4.requestFocus();
                } else if (s.toString().length() == 0) {
                    et3.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et5.requestFocus();
                } else if (s.toString().length() == 0) {
                    et4.requestFocus();
                }
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    et6.requestFocus();
                } else if (s.toString().length() == 0) {
                    et5.requestFocus();
                }
            }
        });


        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    et6.requestFocus();
                } else if (s.toString().length() == 1) {
                    checkOtp();
                }
            }
        });

        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                    deleteOtp();
                return false;
            }
        });

        et3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                    deleteOtp();
                return false;
            }
        });

        et4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                    deleteOtp();
                return false;
            }
        });

        et5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                    deleteOtp();
                return false;
            }
        });

        et6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                    deleteOtp();
                return false;
            }
        });
    }

    private void deleteOtp() {
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        et6.setText("");
        et1.requestFocus();
    }

    private void checkOtp() {
        final otpCheckStatus activity = (otpCheckStatus) mContext;
        bar.setVisibility(View.VISIBLE);
        String enteredOtp = retrieveEnteredText();
            if (enteredOtp.equals(otpNum)) {
                activity.updateResult(true);
                bar.setVisibility(View.GONE);
            } else {
                Log.d("prerna","h");
                MDToast.makeText(mContext, "OTP did not match", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                bar.setVisibility(View.GONE);
            }
    }

    private String retrieveEnteredText() {
        otp = "";
        otp += et1.getText().toString();
        otp += et2.getText().toString();
        otp += et3.getText().toString();
        otp += et4.getText().toString();
        otp += et5.getText().toString();
        otp += et6.getText().toString();
        return otp;
    }

    public interface otpCheckStatus {
        void updateResult(boolean status);
    }

    @Override
    public void onDestroy() {
        otpCheckStatus activity = (otpCheckStatus) mContext;
        activity.updateResult(false);

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        otpCheckStatus activity = (otpCheckStatus) mContext;
        activity.updateResult(false);
        IncomingSms.removeListenr();
        super.onDestroyView();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCancelable(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("OTPChecker", "fragment paused");
        IncomingSms.removeListenr();
    }
}
