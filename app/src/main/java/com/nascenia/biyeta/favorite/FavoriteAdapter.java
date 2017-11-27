package com.nascenia.biyeta.favorite;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.FavoriteActivity;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.activity.RequestSentFromMe;
import com.nascenia.biyeta.adapter.CommunicationRequestFromMeAdapter;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.communication_request_from_me.CommuncationRequestFromMeModel;
import com.nascenia.biyeta.model.newuserprofile.UserProfile;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Tracker mTracker;
    private AnalyticsApplication application;
    Context context;
    private LayoutInflater inflater;
    private FavoriteProfile favoriteProfile;
    private SharePref sharePref;
    private UserProfile userProfile;

//    private Button actionButton;
//    List<Profile> mFavoriteProfile = Collections.emptyList();


    public FavoriteAdapter(Context context, FavoriteProfile favoriteProfile, AnalyticsApplication application, Tracker mTracker) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.favoriteProfile = favoriteProfile;
        sharePref = new SharePref(context);
        this.application = application;
        this.mTracker = mTracker;
        this.progressDialog = new ProgressDialog(context);
    }

    public abstract void onClickProfile(int id, int position);


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        Log.d("Fav", "onCreateViewHolder");
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Profile profile = favoriteProfile.getProfiles().get(position);

        holder.mRequest.setVisibility(View.GONE);
        holder.call.setVisibility(View.GONE);
        holder.message.setVisibility(View.GONE);
        holder.connectoion.setVisibility(View.GONE);
        holder.status.setVisibility(View.GONE);
        Log.d("Fav", "onBindViewHolder " + position);

        holder.userNameFav.setText(profile.getDisplayName());
        if(profile.getRealName()!=null)
            holder.userNameFav.setText(profile.getRealName());
        holder.detailsFav.setText(profile.getAge() + " বছর" + ", " + profile.getHeightFt() + "'" +
                profile.getHeightInc() + "''" + ", " + profile.getOccupation() + ", " + profile.getProfessionalGroup()
                + ", " + profile.getSkinColor() + ", " + profile.getHealth() + ", " + profile.getLocation());

        Log.d("image", Utils.Base_URL + profile.getImage());

        String gender = new SharePref(holder.imageFav.getContext()).get_data("gender");
        Glide.with(holder.imageFav.getContext())
                .load(Utils.Base_URL + profile.getImage())
                .placeholder(gender.equalsIgnoreCase("female") ? R.drawable.profile_icon_male : R.drawable.profile_icon_female)
                .into(holder.imageFav);


        holder.imageFav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickProfile(profile.getId(), position);
            }
        });

        holder.userNameFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProfile(profile.getId(), position);
            }
        });
        holder.detailsFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProfile(profile.getId(), position);
            }
        });

        if ((profile.getRequestStatus().getName().equals("profile request"))
                && (profile.getRequestStatus().getProfileRequestId() == null)
                && (profile.getRequestStatus().getAccepted() == false)
                && (profile.getRequestStatus().getRejected() == false)
                && (profile.getRequestStatus().getReceiver() == null)
                && (profile.getRequestStatus().getSender() == null)){

            Log.d("message 1 Profile_req", "biodata dekhun");
            holder.mRequest.setVisibility(View.VISIBLE);
            holder.mRequest.setText(profile.getRequestStatus().getMessage());
            holder.mRequest.setTag(Utils.sendBiodataRequest);
            holder.mRequest.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (v.getTag().equals(Utils.sendBiodataRequest)){
                        NetWorkOperation.createProfileReqeust(context, Utils.PROFILES_URL + profile.getId()
                                        + "/profile_request", holder.mRequest, context.getResources().getString(R.string.after_sending_biodata_request_text),
                                application, mTracker);
                    }
                    application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                }
            });

        }

        else if (profile.getRequestStatus().getName().equals("profile request")
                && (profile.getRequestStatus().getReceiver()
                == Integer.parseInt(sharePref.get_data("user_id")))
                && (!profile.getRequestStatus().getAccepted())
                && (!profile.getRequestStatus().getRejected())
                && (profile.getRequestStatus().getCommunicationRequestId() == null)
                && (profile.getRequestStatus().getExpired()==null)
                && (profile.getRequestStatus().getReceiver()!=null)
                && (profile.getRequestStatus().getSender()!=null)) {

            Log.d("message 5 Profile_req", "biodata dekhun to you");
            Toast.makeText(context, profile.getRequestStatus().getMessage(), Toast.LENGTH_LONG).show();
            holder.mRequest.setVisibility(View.GONE);
            holder.accept.setVisibility(View.VISIBLE);
            holder.cancel.setVisibility(View.VISIBLE);
// holder.accept.setText(profile.getRequestStatus().getMessage());
// holder.cancel.setText(profile.getRequestStatus().getMessage());
            holder.accept.setTag(Utils.profileRequestAccept);
            holder.cancel.setTag(Utils.profileRequestCancel);
            holder.accept.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (v.getTag().equals(Utils.profileRequestAccept)){

                        new SendRequestTask().execute(Utils.PROFILE_REQUEST_URL +
                                        profile.getRequestStatus().getProfileRequestId() + "/accept",
                                context.getResources().getString(R.string.send_communication_request_text),
                                Utils.sendCommunicationRequest,
                                Utils.profileRequestAccept);
                    }
                    application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                }
            });
            holder.cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (v.getTag().equals(Utils.profileRequestCancel)){


                        new SendRequestTask().execute(Utils.PROFILE_REQUEST_URL +
                                        profile.getRequestStatus().getProfileRequestId() + "/reject",
                                context.getResources().getString(R.string.biodata_request_text),
                                Utils.sendBiodataRequest,
                                Utils.profileRequestCancel);

                    }
                    application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                }
            });

        }

        else if (profile.getRequestStatus().getName().equals("profile request")
                && (profile.getRequestStatus().getSender()
                == Integer.parseInt(sharePref.get_data("user_id")))
                && (!profile.getRequestStatus().getAccepted())
                && (!profile.getRequestStatus().getRejected())){

            Log.d("message 2 Profile_req", "biodata dekhun from you");
            holder.mRequest.setVisibility(View.VISIBLE);
            holder.mRequest.setText(profile.getRequestStatus().getMessage());

            holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
            holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
            holder.mRequest.setEnabled(false);

        } else if ((profile.getRequestStatus().getName().equals("profile request"))
                && (profile.getRequestStatus().getProfileRequestId() != null)
                && (profile.getRequestStatus().getAccepted() == false)
                && (profile.getRequestStatus().getRejected() == false)){

            Log.d("message 3 Profile_req", "biodata request has been sent");
            holder.mRequest.setVisibility(View.VISIBLE);
            holder.mRequest.setText(profile.getRequestStatus().getMessage());
            holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
            holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
            holder.mRequest.setEnabled(false);

        } else if (profile.getRequestStatus().getName().equals("profile request")
                && (profile.getRequestStatus().getSender()
                == Integer.parseInt(sharePref.get_data("user_id")))
                && (!profile.getRequestStatus().getAccepted())
                && (profile.getRequestStatus().getRejected())) {

            Log.d("message 4 Profile_req", "biodata request has been sent bt rejected");
            holder.mRequest.setVisibility(View.VISIBLE);
            holder.mRequest.setText(profile.getRequestStatus().getMessage());
            holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
            holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
            holder.mRequest.setEnabled(false);

        }

        /*else if (profile.getRequestStatus().getName().equals("profile request")
                && (profile.getRequestStatus().getReceiver()
                == Integer.parseInt(sharePref.get_data("user_id")))
                && (!profile.getRequestStatus().getAccepted())
                && (!profile.getRequestStatus().getRejected())) {

            Log.d("message 5 Profile_req", "biodata dekhun to you");
            Toast.makeText(context, profile.getRequestStatus().getMessage(), Toast.LENGTH_LONG).show();
            holder.mRequest.setVisibility(View.GONE);
            holder.accept.setVisibility(View.VISIBLE);
            holder.cancel.setVisibility(View.VISIBLE);
            holder.accept.setText(profile.getRequestStatus().getMessage());
            holder.cancel.setText(profile.getRequestStatus().getMessage());
            holder.accept.setTag(Utils.profileRequestAccept);
            holder.cancel.setTag(Utils.profileRequestCancel);
            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag().equals(Utils.profileRequestAccept)) {

                        new SendRequestTask().execute(Utils.PROFILE_REQUEST_URL +
                                        profile.getRequestStatus().getProfileRequestId() + "/accept",
                                context.getResources().getString(R.string.send_communication_request_text),
                                Utils.sendCommunicationRequest,
                                Utils.profileRequestAccept);
                    }
                    application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                }
            });
            holder.cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(v.getTag().equals(Utils.profileRequestCancel)){


                        new SendRequestTask().execute(Utils.PROFILE_REQUEST_URL +
                                        profile.getRequestStatus().getProfileRequestId() + "/reject",
                                context.getResources().getString(R.string.biodata_request_text),
                                Utils.sendBiodataRequest,
                                Utils.profileRequestCancel);

                    }
                    application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                }
            });
     } */
        else if (profile.getRequestStatus().getName().equals("profile request")
                && (profile.getRequestStatus().getReceiver()
                == Integer.parseInt(sharePref.get_data("user_id")))
                && (!profile.getRequestStatus().getAccepted())
                && (profile.getRequestStatus().getRejected())){

            Log.d("message 6 Profile_req", "ami reject korsi");
            Toast.makeText(context, profile.getRequestStatus().getMessage(), Toast.LENGTH_LONG).show();
            holder.mRequest.setVisibility(View.VISIBLE);
            holder.mRequest.setEnabled(true);
            holder.mRequest.setText(profile.getRequestStatus().getMessage());
            holder.mRequest.setTag(Utils.sendBiodataRequest);
            holder.mRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag().equals(Utils.sendBiodataRequest)) {
                        NetWorkOperation.createProfileReqeust(context, Utils.PROFILES_URL + profile.getId()
                                + "/profile_request", holder.mRequest, context.getResources().getString(R.string.after_sending_biodata_request_text), application, mTracker);
                    }
                    application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                }
            });

        }

       /* if ((profile.getRequestStatus().getName().equals("communication request"))
                && (profile.getRequestStatus().getCommunicationRequestId() == null)
                && (profile.getRequestStatus().getExpired() != null)){

            if ((profile.getRequestStatus().getExpired() == true)
                        && profile.getRequestStatus().getReceiver()
                        == Integer.parseInt(sharePref.get_data("user_id"))) {

                    Log.i("expired receiver", "got");
                    holder.mRequest.setVisibility(View.VISIBLE);
                    holder.mRequest.setText(profile.getRequestStatus().getMessage());
                    holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                    holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                    holder.mRequest.setEnabled(false);
                } else if ((profile.getRequestStatus().getExpired() == true)
                        && profile.getRequestStatus().getSender()
                        == Integer.parseInt(sharePref.get_data("user_id"))) {

                    Log.i("expired sender", "got");
                    holder.mRequest.setVisibility(View.VISIBLE);
                    holder.mRequest.setText(profile.getRequestStatus().getMessage());
                    holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                    holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                    holder.mRequest.setEnabled(false);}

        }*/
        if ((profile.getRequestStatus().getName().equals("communication request"))
                && (profile.getRequestStatus().getCommunicationRequestId() == null)) {

            Log.d("message 6 Commu_req", "apni jogajoger onurodh koresen");
            holder.mRequest.setVisibility(View.VISIBLE);
            holder.mRequest.setText(profile.getRequestStatus().getMessage());
            holder.mRequest.setEnabled(true);
            holder.mRequest.setTag(Utils.sendCommunicationRequest);
            holder.mRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag().equals(Utils.sendCommunicationRequest)) {

                        NetWorkOperation.createCommunicationReqeust(context,
                                Utils.COMMUNICATION_REQUEST_URL,
                                profile.getId() + "",
                                holder.mRequest,
                                context.getResources().getString(R.string.after_sending_communication_request_text), application, mTracker);

                        application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                    }

                }
            });

            //for expired user because of incomplete task

            if(profile.getRequestStatus().getExpired() != null){

                if ((profile.getRequestStatus().getExpired() == true)
                        && profile.getRequestStatus().getReceiver()
                        == Integer.parseInt(sharePref.get_data("user_id"))) {

                    // have to do
                    Log.d("expired receiver", "got");
                    holder.mRequest.setVisibility(View.VISIBLE);
                    holder.mRequest.setText(profile.getRequestStatus().getMessage());
                    holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                    holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                    holder.mRequest.setEnabled(false);
                } else if ((profile.getRequestStatus().getExpired() == true)
                        && profile.getRequestStatus().getSender()
                        == Integer.parseInt(sharePref.get_data("user_id"))) {

                    Log.d("expired sender", "got");
//                    holder.connectoion.setVisibility(View.VISIBLE);
//                    holder.status.setVisibility(View.VISIBLE);
//                    holder.connectoion.setText("আবারো যোগাযোগ করুন");
                    /*holder.connectoion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SendConnection().execute(Utils.Base_URL + "/api/v1/communication_requests");

                            application.setEvent("Action", "Click", "Make Connection from you", mTracker);
                        }
                    });*/
                    holder.mRequest.setText(profile.getRequestStatus().getMessage());
                    holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                    holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                    holder.mRequest.setEnabled(false);}
            }
        }
        else {
            //sender =appuser
            if (profile.getRequestStatus().getName().equals("communication request")
                    && (profile.getRequestStatus().getSender()
                    == Integer.parseInt(sharePref.get_data("user_id")))
                    && (profile.getRequestStatus().getAccepted())) {

                holder.mRequest.setVisibility(View.GONE);
                holder.call.setVisibility(View.VISIBLE);
                holder.message.setVisibility(View.VISIBLE);
//                holder.call.setTag(Utils.sendmessage);
//                holder.message.setTag(Utils.call);
                holder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + profile.getMobileNumber()));
                        if (ActivityCompat.checkSelfPermission(holder.call.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        } else {
                            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            int simState = telMgr.getSimState();
                            if(simState==TelephonyManager.SIM_STATE_ABSENT)
                            {
                                Toast.makeText(context,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
                            }
                            else if(simState==TelephonyManager.SIM_STATE_UNKNOWN)
                            {
                                Toast.makeText(context,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
                            }
                            else
                                holder.call.getContext().startActivity(callIntent);
                        }
                        application.setEvent("Action", "Click", "Phone Called", mTracker);
                    }
                });
                holder.message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context, "message" + position, Toast.LENGTH_SHORT).show();
//                        SharePref sharePref = new SharePref(context);
                        String currentUserSignedIn = sharePref.get_data("user_id");
                        Intent in = new Intent(holder.imageFav.getContext(), InboxSingleChat.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("sender_id", profile.getRequestStatus().getSender());
                        bundle.putInt("receiver_id", profile.getRequestStatus().getReceiver());
                        bundle.putInt("current_user", Integer.parseInt(currentUserSignedIn));
                        bundle.putString("userName", profile.getDisplayName());
                        in.putExtras(bundle);
                        holder.imageFav.getContext().startActivity(in);

                        application.setEvent("Action", "Click", "Send Message", mTracker);
                    }
                });
            } else if (profile.getRequestStatus().getName().equals("communication request")
                    && (profile.getRequestStatus().getSender()
                    == Integer.parseInt(sharePref.get_data("user_id")))
                    && (profile.getRequestStatus().getRejected())) {

                holder.mRequest.setVisibility(View.VISIBLE);
                holder.mRequest.setText(profile.getRequestStatus().getMessage());
                holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                holder.mRequest.setEnabled(false);

            } else if (profile.getRequestStatus().getName().equals("communication request")
                    && (profile.getRequestStatus().getSender()
                    == Integer.parseInt(sharePref.get_data("user_id")))
                    && (!profile.getRequestStatus().getAccepted())
                    && (!profile.getRequestStatus().getRejected())) {

                holder.mRequest.setVisibility(View.VISIBLE);
                holder.mRequest.setText(profile.getRequestStatus().getMessage());
                holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                holder.mRequest.setEnabled(false);

            } else if (profile.getRequestStatus().getName().equals("communication request")
                    && (profile.getRequestStatus().getReceiver()
                    == Integer.parseInt(sharePref.get_data("user_id")))
                    && (profile.getRequestStatus().getAccepted())) {

                holder.mRequest.setVisibility(View.GONE);
                holder.call.setVisibility(View.VISIBLE);
                holder.message.setVisibility(View.VISIBLE);
//                holder.call.setTag(Utils.sendmessage);
//                holder.message.setTag(Utils.call);
                holder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + profile.getMobileNumber()));
                        if (ActivityCompat.checkSelfPermission(holder.call.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        } else {
                            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            int simState = telMgr.getSimState();
                            if(simState==TelephonyManager.SIM_STATE_ABSENT)
                            {
                                Toast.makeText(context,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
                            }
                            else if(simState==TelephonyManager.SIM_STATE_UNKNOWN)
                            {
                                Toast.makeText(context,"সিম কার্ড নেই",Toast.LENGTH_LONG).show();
                            }
                            else
                                holder.call.getContext().startActivity(callIntent);
                        }
                        application.setEvent("Action", "Click", "Phone Called", mTracker);
                    }
                });
                holder.message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context, "message" + position, Toast.LENGTH_SHORT).show();
//                        SharePref sharePref = new SharePref(context);
                        String currentUserSignedIn = sharePref.get_data("user_id");
                        Intent in = new Intent(holder.imageFav.getContext(), InboxSingleChat.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("sender_id", profile.getRequestStatus().getSender());
                        bundle.putInt("receiver_id", profile.getRequestStatus().getReceiver());
                        bundle.putInt("current_user", Integer.parseInt(currentUserSignedIn));
                        bundle.putString("userName", profile.getDisplayName());
                        in.putExtras(bundle);
                        holder.imageFav.getContext().startActivity(in);

                        application.setEvent("Action", "Click", "Send Message", mTracker);
                    }
                });
            } else if (profile.getRequestStatus().getName().equals("communication request")
                    && (profile.getRequestStatus().getReceiver()
                    == Integer.parseInt(sharePref.get_data("user_id")))
                    && (profile.getRequestStatus().getRejected())){

                holder.mRequest.setEnabled(true);
                holder.mRequest.setVisibility(View.VISIBLE);
                holder.mRequest.setText(profile.getRequestStatus().getMessage());
                holder.mRequest.setTag(Utils.sendCommunicationRequest);
                holder.mRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        if (v.getTag().equals(Utils.sendCommunicationRequest)){

                            NetWorkOperation.createCommunicationReqeust(context,
                                    Utils.COMMUNICATION_REQUEST_URL,
                                    profile.getRequestStatus().getCommunicationRequestId() + "",
                                    holder.mRequest,
                                    context.getResources().getString(R.string.after_sending_communication_request_text), application, mTracker);

                            application.setEvent("Action", "Click", profile.getRequestStatus().getMessage(), mTracker);
                        }
                    }
                });
            } else if (profile.getRequestStatus().getName().equals("communication request")
                    && (profile.getRequestStatus().getReceiver()
                    == Integer.parseInt(sharePref.get_data("user_id")))
                    && (!profile.getRequestStatus().getAccepted())
                    && (!profile.getRequestStatus().getRejected())) {

                holder.mRequest.setVisibility(View.GONE);
                holder.accept.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.VISIBLE);
                holder.accept.setTag(Utils.commRequestAccept);
                holder.cancel.setTag(Utils.commRequestCancel);
                holder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag().equals(Utils.commRequestCancel)) {


                            new SendRequestTask().execute(Utils.COMMUNICATION_REQUEST_URL +
                                            profile.getRequestStatus().getCommunicationRequestId() + "/reject",
                                    context.getResources().getString(R.string.send_communication_request_text),
                                    Utils.sendCommunicationRequest,
                                    Utils.commRequestCancel);

                            application.setEvent("Action", "Click", "Communication Request Rejected", mTracker);
                        }
                    }
                });
                holder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag().equals(Utils.commRequestAccept)) {


                            new SendRequestTask().execute(Utils.COMMUNICATION_REQUEST_URL +
                                            profile.getRequestStatus().getCommunicationRequestId()
                                            + "/accept",
                                    "",
                                    "",
                                    Utils.MESSAGE_CALL_BLOCK);

                            application.setEvent("Action", "Click", "Communication Request Accepted", mTracker);
                        }
                    }
                });

            }

            if (profile.getRequestStatus().getExpired() != null) {
                if ((profile.getRequestStatus().getExpired() == true)
                        && profile.getRequestStatus().getReceiver()
                        == Integer.parseInt(sharePref.get_data("user_id"))) {

//                    Log.i("expired receiver", "got");
                    holder.mRequest.setVisibility(View.VISIBLE);
                    holder.mRequest.setText(profile.getRequestStatus().getMessage());
                    holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                    holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                    holder.mRequest.setEnabled(false);
                } else if ((profile.getRequestStatus().getExpired() == true)
                        && profile.getRequestStatus().getSender()
                        == Integer.parseInt(sharePref.get_data("user_id"))) {

//                    Log.i("expired sender", "got");
                    holder.mRequest.setVisibility(View.VISIBLE);
                    holder.mRequest.setText(profile.getRequestStatus().getMessage());
                    holder.mRequest.setBackgroundColor(Color.parseColor("#F2F1F1"));
                    holder.mRequest.setTextColor(Color.parseColor("#716E6E"));
                    holder.mRequest.setEnabled(false);

                }

            }

        }


    }


    @Override
    public int getItemCount() {
        if(favoriteProfile.getProfiles()!=null)
            return favoriteProfile.getProfiles().size();
        else{
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageFav;
        public TextView userNameFav;
        public TextView detailsFav;
        public Button mRequest;
        public Button call, message;
        public Button accept, cancel;
        Button connectoion;
        TextView status;


        public ViewHolder(View itemView) {
            super(itemView);
            imageFav = (ImageView) itemView.findViewById(R.id.profile_image_fav);
            userNameFav = (TextView) itemView.findViewById(R.id.user_name_fav);
            detailsFav = (TextView) itemView.findViewById(R.id.details_fav);
            mRequest = (Button) itemView.findViewById(R.id.request_biodata_fav);
            call = (Button) itemView.findViewById(R.id.phone);
            message = (Button) itemView.findViewById(R.id.message);
            accept = (Button) itemView.findViewById(R.id.acceptBtn);
            cancel = (Button) itemView.findViewById(R.id.cancelBtn);
            connectoion = (Button) itemView.findViewById(R.id.connection_button);
            status = (TextView) itemView.findViewById(R.id.status);
        }


    }


    private ProgressDialog progressDialog;

    public class SendRequestTask extends AsyncTask<String, Void, String>{

        String btnText;
        String btnTag;
        String userResponseCase;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.favorite_item, null, false);

        ViewHolder viewHolder = new ViewHolder(view);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls){
            btnText = urls[1];
            btnTag = urls[2];
            userResponseCase = urls[3];

            Response response;
            SharePref sharePref = new SharePref(context);
            String token = sharePref.get_data("token");
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urls[0])
                        .addHeader("Authorization", "Token token=" + token)
                        .get()
                        .build();
                response = client.newCall(request).execute();

                return response.body().string();

            } catch (Exception e){
                e.printStackTrace();
//                Log.i("asynctaskdata", e.getMessage());
//                application.trackEception(e, "SendRequestTask/doInBackground", "FavoriteAdapter", e.getMessage().toString(), mTracker);
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            try{

                if(s == null){
                    Utils.ShowAlert(context, context.getResources().getString(R.string.no_internet_connection));
                } else {

                    JSONObject jsonObject = new JSONObject(s);

                    if (userResponseCase.equals(Utils.profileRequestAccept) ||
                            userResponseCase.equals(Utils.profileRequestCancel) ||
                            userResponseCase.equals(Utils.commRequestCancel)) {


                        viewHolder.mRequest.setVisibility(View.VISIBLE);
                        viewHolder.mRequest.setEnabled(true);
                        viewHolder.mRequest.setTag(btnTag);
                        viewHolder.mRequest.setText(btnText);


                        if (jsonObject.has("message")) {
                            Toast.makeText(context,
                                    jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"),
                                    Toast.LENGTH_LONG).show();

                        }


                    } else if (userResponseCase.equals(Utils.MESSAGE_CALL_BLOCK)) {


//                        acceptTextView.setText(getResources().getString(R.string.make_message));
//                        cancelTextView.setText(getResources().getString(R.string.make_call));

//                        acceptImageView.setTag(Utils.sendmessage);
//                        cancelImageView.setTag(Utils.call);

                        if (jsonObject.has("message")) {
                            Toast.makeText(context,
                                    jsonObject.getJSONArray("message").getJSONObject(0).getString("detail"),
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
//                        Log.i("casetest", "no case match");
                    }


                    ((FavoriteActivity)context).callFavoriteAPI();
                }


            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "SendRequestTask/onPostExecute", "FavoriteAdapter", e.getMessage().toString(), mTracker);
            }

        }
    }

    CommuncationRequestFromMeModel communicationResponse;
    CommunicationRequestFromMeAdapter communicationRequestFromMeAdapter;
    public final OkHttpClient client = new OkHttpClient();
    class SendConnection extends AsyncTask<String, String, String> {

        int listposition;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("message")) {
                    ///  Toast.makeText(RequestSentFromMe.this, " sent", Toast.LENGTH_SHORT).show();
                    communicationResponse.getProfiles().get(listposition).getRequestStatus().setExpired(false);
                    communicationResponse.getProfiles().get(listposition).getRequestStatus().setMessage("আপনি যোগাযোগের  অনুরোধ  করেছেন");

                    communicationRequestFromMeAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                application.trackEception(e, "SendConnection/onPostExecute", "FavoriteAdapter", e.getMessage().toString(), mTracker);
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            //  Log.e(" id", strings[1]);

            Integer id = Integer.parseInt(strings[1]);
            listposition = Integer.parseInt(strings[2]);
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("profile_id", id + "")
                    .build();


            Response response;
            SharePref sharePref = new SharePref(context);
            String token = sharePref.get_data("token");

            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(requestBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                return jsonData;

            } catch (Exception e){
//                application.trackEception(e, "SendConnection/doInBackground", "FavoriteAdapter", e.getMessage().toString(), mTracker);
                return null;

            }


        }
    }
}
