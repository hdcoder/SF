package hiteshdua1.codescripter.sf.Fragments;

/**
 * Created by Hitesh Dua 30/9/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hiteshdua1.codescripter.sf.Objects.Centers;
import hiteshdua1.codescripter.sf.R;
import hiteshdua1.codescripter.sf.config.GPSTracker;

public class fragment_map extends Fragment {
   SwipeRefreshLayout swipeLayout;
    Double latitude,longitude;
    com.google.android.gms.maps.MapFragment mapFragment;
    GoogleMap map;
    List<Centers> centres;
    TextView title;

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    Activity activity;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }
    LinearLayout l;
    int index=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_event_map, container, false);
        if(activity!=null) {
            InputMethodManager input = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(activity.getCurrentFocus()!=null)
                input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        centres = new ArrayList<Centers>();

        title = (TextView) rootView.findViewById(R.id.tit);
        Log.e("here", "in map");

        l = (LinearLayout) rootView.findViewById(R.id.con);
        this.mapFragment = (com.google.android.gms.maps.MapFragment) activity.getFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFragment.getMap();
        final CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(21,
                        78));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        if(map!=null) {
            map.moveCamera(center);
            map.animateCamera(zoom);
        }


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                l.setVisibility(View.VISIBLE);
                int i = 0;
                for(i=0;i<centres.size();i++)
                {
                    Centers o = centres.get(i);
                    if(o.title.equals(marker.getTitle()))
                    {
                        index = i;
                        break;
                    }
                }

                title = (TextView) rootView.findViewById(R.id.tit);
                if(centres.get(index).title!=null && !centres.get(index).title.equals("null"))
                title.setText(centres.get(index).title);
                title = (TextView) rootView.findViewById(R.id.add);
                if(centres.get(index).add!=null && !centres.get(index).add.equals("null"))
                title.setText(centres.get(index).add);
                title = (TextView) rootView.findViewById(R.id.web);
                if(centres.get(index).web!=null && !centres.get(index).web.equals("null"))
                title.setText(centres.get(index).web);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(centres.get(index).web));
                        startActivity(i);
                    }
                });
                title = (TextView) rootView.findViewById(R.id.email);
                if(centres.get(index).sontact!=null && !centres.get(index).sontact.equals("null"))
                title.setText(centres.get(index).sontact);

                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL,centres.get(index).sontact);
                        startActivity(Intent.createChooser(intent, "Send Email")); }
                });


                title = (TextView) rootView.findViewById(R.id.phone);
                if(centres.get(index).phone!=null && !centres.get(index).phone.equals("null"))
                title.setText(centres.get(index).phone);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + centres.get(index).phone));
                        startActivity(intent);
                    }
                });

                return false;
            }
        });


        GPSTracker tracker = new GPSTracker(activity);
        if (tracker.canGetLocation() == false) {
            tracker.showSettingsAlert();

        } else {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
        }

        new Down().execute();



        return rootView;
    }


    public void ShowMarker(Double LocationLat, Double LocationLong, String LocationName, Integer LocationIcon){
        LatLng Coord = new LatLng(LocationLat, LocationLong);

        if(map!=null) {
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Coord, 5));

            MarkerOptions abc = new MarkerOptions();
            MarkerOptions x = abc
                    .title(LocationName)
                    .position(Coord)
                    .icon(BitmapDescriptorFactory.fromResource(LocationIcon));
            map.addMarker(x);

        }
    }




    String text;

    public class Down extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            //pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            String URL;
//            HttpClient Client = new DefaultHttpClient();
//            HttpGet httpget = new HttpGet(URL);
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            try {
////                text = Client.execute(httpget, responseHandler);
//                text=" ";
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
                text ="\n" +
                        "{\"centers\":[{\"title\":\" New Mumbai\",\"address\":\"Maharashtra\",\"lat\":19.7514798,\"lng\":75.7138884,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/new-mumbai\",\"contact\":null,\"phone\":null},{\"title\":\"Abhayapuri\",\"address\":\"C\\/o.universal electronics, abhayapuri, bongaigaon, Abhayapur, Assam\",\"lat\":26.2006043,\"lng\":92.9375739,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/abhayapuri\",\"contact\":\"trishna.chowdhury@vvki.net\",\"phone\":\"06645-250798\"},{\"title\":\"Abohar\",\"address\":\"C\\/O Faqir Furniture Marg, St. No. 13, Abohar, Punjab\",\"lat\":30.1452928,\"lng\":74.1993043,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/abohar\",\"contact\":\"aolabohar@gmail.com\",\"phone\":\"01634 224056 \\/ 01634 234809 \\/ 98726 22056\"},{\"title\":\"Agartala\",\"address\":\"VVKI, BANAMALIPUR, Agartala, Tripura, -791001\",\"lat\":28.3141623,\"lng\":94.645035,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/agartala\",\"contact\":null,\"phone\":\"09862556338\"},{\"title\":\"Agra\",\"address\":\"Agra, Uttar Pradesh, 282 004\",\"lat\":27.1784867,\"lng\":78.0045006,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/agra\",\"contact\":\"info@artoflivingagra.org\",\"phone\":\"0562-6454469\"},{\"title\":\"Ahmedabad\",\"address\":\"Sri Sri Gyan Mandir 272 Mayurpankh Society Nr. Shivranjani Cross Roads, B\\/h Shaligram Flats, Satellite, Ahmedabad, Gujarat, 380015\",\"lat\":23.0228032,\"lng\":72.5331457,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ahmedabad\",\"contact\":\"gujaratapex@vvki.net\",\"phone\":\"079-26733031\"},{\"title\":\"Ahmednagar\",\"address\":\"Maharashtra\",\"lat\":19.7514798,\"lng\":75.7138884,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ahmednagar\",\"contact\":null,\"phone\":null},{\"title\":\"Ajmer\",\"address\":\"13,Chitrakoot Colony, Makad wali Road, Ajmer, Ajmer, Rajasthan, -\",\"lat\":26.45,\"lng\":74.64,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ajmer\",\"contact\":null,\"phone\":\"9829027310\"},{\"title\":\"Akola\",\"address\":\"Kailash enterprises,Aggrawal house,zilla parishad road, Akola, Maharashtra, 444001\",\"lat\":20.6896365,\"lng\":77.0135939,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/akola\",\"contact\":null,\"phone\":\"0724-2436138; 9422861112\"},{\"title\":\"Akot\",\"address\":\"Akot, Maharashtra\",\"lat\":21.1,\"lng\":77.06,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/akot\",\"contact\":null,\"phone\":null},{\"title\":\"Alappuzha\",\"address\":\"Chammanadu, Kodamthuruth, Kuthuathode, Alappuzha, Kerala\",\"lat\":9.4980667,\"lng\":76.3388484,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/alappuzha\",\"contact\":null,\"phone\":\"0471-2369913\\/ 2367424\"},{\"title\":\"Alibag\",\"address\":\"Alibag, Maharashtra\",\"lat\":18.6553938,\"lng\":72.8670819,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/alibag\",\"contact\":null,\"phone\":null},{\"title\":\"Aligarh\",\"address\":\"Aligarh, Uttar Pradesh\",\"lat\":27.89381,\"lng\":78.068138,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/aligarh\",\"contact\":\"stc.up.desk@gmail.com\",\"phone\":\"8868080169\"},{\"title\":\"Allahabad\",\"address\":\"Pankaj Mishra, <br \\/>Sri Sri Gyan Mandir,52 Tashkent Road, Civil Lines, Allahabad, Uttar Pradesh, 211 001\",\"lat\":25.4911662,\"lng\":81.8616146,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/allahabad\",\"contact\":\"artoflivingallahabad@gmail.com\",\"phone\":null},{\"title\":\"Almora\",\"address\":\"VVK - Almora, R\\/o Cotton County, Shagun Complex, Milan Chowk, Almora, Almora, Uttaranchal, 263601\",\"lat\":29.5892407,\"lng\":79.646666,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/almora\",\"contact\":\"sudhamanral@rediffmail.com\",\"phone\":\"9759123456; 9412092119\"},{\"title\":\"Alwar\",\"address\":\"77, Tilak Market, Alwar, Alwar, Rajasthan, -\",\"lat\":27.5609324,\"lng\":76.6250168,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/alwar\",\"contact\":null,\"phone\":\"0144-2334236;0144-2701209;9413448469\"},{\"title\":\"Amaravati\",\"address\":\"Maharashtra\",\"lat\":19.7514798,\"lng\":75.7138884,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/amaravati\",\"contact\":null,\"phone\":null},{\"title\":\"Ambala\",\"address\":\"130-E, Rani Bag, Near Durga Mandir,Ambala Cantt, Ambala, Haryana\",\"lat\":30.3610314,\"lng\":76.8485468,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ambala\",\"contact\":\"ramesh.kalia@rediffmail.com\",\"phone\":\"9416020567\"},{\"title\":\"Ambattur\",\"address\":\"Chennai, Tamil Nadu\",\"lat\":13.0826802,\"lng\":80.2707184,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ambattur\",\"contact\":null,\"phone\":null},{\"title\":\"Ambedkar Nagar\",\"address\":\"Ambedkar Nagar, Uttar Pradesh\",\"lat\":26.4683952,\"lng\":82.6915429,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ambedkar-nagar\",\"contact\":\"sksinghaol@gmail.com\",\"phone\":\"8147616875 \\/ 8004458701\"},{\"title\":\"Ambur\",\"address\":\"27\\/63, Sannathi Street, \\\"A\\\" Kaspa Ambur Vellore Dist, Ambur, Tamil Nadu, -\",\"lat\":12.7903613,\"lng\":78.7166084,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ambur\",\"contact\":null,\"phone\":\"04174-243443\\/243441;9443020154\"},{\"title\":\"Amethi\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/amethi\",\"contact\":\"binesh.tyagi@vvki.net\",\"phone\":\"9415083908\"},{\"title\":\"Amreli\",\"address\":\"Gurukrupa,7,sukhanath para,liliya road,Amreli, Amreli, Gujarat\",\"lat\":21.5965939,\"lng\":71.2232983,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/amreli\",\"contact\":null,\"phone\":\"9428798076\"},{\"title\":\"Amritsar\",\"address\":\"780,Gali Telian,Nimak Mandi,, Amritsar, Punjab, 143001\",\"lat\":31.6435428,\"lng\":74.9091853,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/amritsar\",\"contact\":\"blsnareshkumar@yahoo.com\",\"phone\":\"0183-2543457\"},{\"title\":\"Amroha\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/amroha\",\"contact\":\"sangeeta.khanna@vvki.net\",\"phone\":\"9450791056\"},{\"title\":\"Anand\",\"address\":\"21,Sardarganj, Anand, Anand, Gujarat, -\",\"lat\":22.5623189,\"lng\":72.9626014,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/anand\",\"contact\":\"rbt_21in@yahoo.co.in\",\"phone\":\"02692-240268\"},{\"title\":\"Andal Information Center\",\"address\":\"G.T. Road, Andal More, Dist. BurdwanAndalWest Bengal, 7133321\",\"lat\":23.5809015,\"lng\":87.1835933,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/andal-information-center\",\"contact\":\"andalshyam@gmail.com\",\"phone\":\"9434006432\"},{\"title\":\"Andaman and Nicobar Center\",\"address\":\"5 quarry hill phoenix bay, Port Blair, Andaman and Nicobar Islands\",\"lat\":11.6704155,\"lng\":92.7338146,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/andaman-and-nicobar-center\",\"contact\":null,\"phone\":\"9434281460\"},{\"title\":\"Anjangaon surji\",\"address\":\"Anjangaon surji, Maharashtra\",\"lat\":21.1689372,\"lng\":77.3008506,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/anjangaon-surji\",\"contact\":null,\"phone\":null},{\"title\":\"Anugul\",\"address\":\"Kalpana Palit, Indfab, FCI Road, Kulad, Nalco Nagar, Anugul, Orissa\",\"lat\":20.849,\"lng\":85.154,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/anugul\",\"contact\":\"aksahu@nalcoindia.co.in\",\"phone\":\"9437068105\"},{\"title\":\"Arakonam\",\"address\":\"2\\/50,3rd Street,A.N Kandigai Palanipet , Arakonam P.O, Vellore Dist, Arakonam, Tamil Nadu, -\",\"lat\":13.082699,\"lng\":79.670813,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/arakonam\",\"contact\":null,\"phone\":\"9894298866\"},{\"title\":\"Aralvaimozhi\",\"address\":\"Maruthi Matriculation School,Aralvaimozhi, Kanyakumari, Tamil Nadu\",\"lat\":8.2482,\"lng\":77.5278,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/aralvaimozhi\",\"contact\":null,\"phone\":\"9442042833\"},{\"title\":\"Arcot\",\"address\":\"Jewellery Mart,Elanguppam Street Arcot, Vellore Dist, Arcot, Tamil Nadu\",\"lat\":12.904441,\"lng\":79.3191576,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/arcot\",\"contact\":null,\"phone\":\"9443372027\"},{\"title\":\"Arni\",\"address\":\"1\\/131,Palayakara Street, Meyyur Post & Village, Arni Taluk, Arni, Tamil Nadu, -632317\",\"lat\":12.6928709,\"lng\":79.3240228,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/arni\",\"contact\":null,\"phone\":\"9842811055\"},{\"title\":\"Art of Living Gachtala Center\",\"address\":\"93 A Moore Avenue, Excelsior, A-1, Near Gachtala Spencer Tollygunge, Kolkata, West Bengal, 700040\",\"lat\":22.4986357,\"lng\":88.3453906,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/art-living-gachtala-center\",\"contact\":null,\"phone\":null},{\"title\":\"Arunachal Pradesh Ashram\",\"address\":\"\",\"lat\":20.593684,\"lng\":78.96288,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/arunachal-pradesh-ashram\",\"contact\":null,\"phone\":null},{\"title\":\"Aruppukottai\",\"address\":\"Sri Meenakshi Industries, Tiruchuli Road, Sempatti Post, Aruppukottai, Aruppukottai, Tamil Nadu, -626101\",\"lat\":9.5128841,\"lng\":78.1051246,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/aruppukottai\",\"contact\":null,\"phone\":\"9884034641\"},{\"title\":\"Asansol Information Center\",\"address\":\"Karunamayee Housing Estate, Sen Releigh Road., Asansol, West Bengal, 713 004\",\"lat\":23.6880155,\"lng\":86.952278,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/asansol-information-center\",\"contact\":\"abhishekkhemka001@gmail.com\",\"phone\":\"8116199991\"},{\"title\":\"Attingal\",\"address\":\"NSS Hall, Attingal, Trivandrum., Attingal, Kerala, 695 101\",\"lat\":8.6712749,\"lng\":76.8149364,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/attingal\",\"contact\":\"aolattingal@gmail.com\",\"phone\":\"+919847458508\"},{\"title\":\"Auraiya\",\"address\":\"Auriayah, Uttar Pradesh\",\"lat\":26.4667,\"lng\":79.5167,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/auraiya\",\"contact\":\"drqnaindia@yahoo.co.in\",\"phone\":\"9259052445\"},{\"title\":\"Aurangabad\",\"address\":\"6-mahesh apt,sarang society, near swagat hall,Gajanan Maharaj mandir road, Aurangabad, Maharashtra, 431005\",\"lat\":19.8701018,\"lng\":75.3466021,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/aurangabad\",\"contact\":null,\"phone\":\"0240-2337281\"},{\"title\":\"Avadi\",\"address\":\"Tamil Nadu\",\"lat\":11.1271225,\"lng\":78.6568942,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/avadi\",\"contact\":null,\"phone\":null},{\"title\":\"Azamgarh\",\"address\":\"Ramashanker, anad auto mobile, civi line, Azamgarh, Uttar Pradesh\",\"lat\":26.0737044,\"lng\":83.1859458,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/node\\/12680\",\"contact\":null,\"phone\":\"9415318051\"},{\"title\":\"Azamgarh\",\"address\":\"Azamgarh, Uttar Pradesh\",\"lat\":26.0737044,\"lng\":83.1859458,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/Azamgarh\",\"contact\":\"kiransingh5858@yahoo.com\",\"phone\":\"9415258658\"},{\"title\":\"Badarpur\",\"address\":\"The art of living badarpur information centre Badarpur, po-badarpur, dist-karimganjBadarpur, Assam,  788806\",\"lat\":24.8644,\"lng\":92.5747,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/badarpur\",\"contact\":\"dniloy@ymail.com\",\"phone\":\"9435334847\"},{\"title\":\"Badaun\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/badaun\",\"contact\":\"saurabhaol@gmail.com\",\"phone\":\"9897466992\"},{\"title\":\"Baddi\",\"address\":\"Himachal Pradesh\",\"lat\":31.8360855,\"lng\":77.169855,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/baddi\",\"contact\":null,\"phone\":null},{\"title\":\"Baharaich\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/baharaich\",\"contact\":\"pramod.jaiswal@vvki.net\",\"phone\":\"9415165252\"},{\"title\":\"Balangir    \",\"address\":\"Balangir   Orissa\",\"lat\":20.7074234,\"lng\":83.4842725,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/balangir\",\"contact\":null,\"phone\":null},{\"title\":\"Balasore\",\"address\":\"House kalyami Das,Niliabag, near milco icem, Balasore, Orissa, 756001\",\"lat\":21.4991702,\"lng\":86.9185854,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/balasore\",\"contact\":\"sdbhanj@hotmail.com\",\"phone\":\"9937022203\"},{\"title\":\"Ballia\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/ballia\",\"contact\":\"ak511@rediffmail.com\",\"phone\":\"9335821059\"},{\"title\":\"Balrampur\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/balrampur\",\"contact\":\"sadhanasrisri@aol.com\",\"phone\":\"9450524944\"},{\"title\":\"Banaskantha\",\"address\":\"Dharti medical store,nr. Dr.house pull end,palanpur, Banaskantha dist., Gujarat\",\"lat\":22.258652,\"lng\":71.1923805,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/banaskantha\",\"contact\":null,\"phone\":\"02742-252469; 9727087815\"},{\"title\":\"Banda\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/banda\",\"contact\":\"sharmapommy@gmail.com\",\"phone\":\"9839224341\"},{\"title\":\"Bangalore\",\"address\":\"VVKI-Karnataka Apex Body, <br \\/>#19, 39th \\\"A\\\" cross, 11th Main,  4th \\\"T\\\" Block, Jayanagar, Bengaluru, Karnataka, 560041\",\"lat\":12.9202374,\"lng\":77.5869458,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bangalore\",\"contact\":null,\"phone\":null},{\"title\":\"Bangalore Ashram\",\"address\":\"The Art Of Living International Centre,Ved Vignan Maha Vidya Peeth,21st k.m Kanakapura Road, Bangalore South, Karnataka, 560082\",\"lat\":12.7935045,\"lng\":77.5046101,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bangalore-ashram\",\"contact\":\"info@vvmvp.org\",\"phone\":\"+91 80 67262626\\/27\\/28\"},{\"title\":\"Bangalore Ashram\",\"address\":\"Art of Living International Center<br \\/>, <br \\/>Ved Vignan Maha Vidya Peeth, 21st Km, Kanakapura Road, Udayapura,Bangalore,, Karnataka, 560 082\",\"lat\":12.82572,\"lng\":77.511221,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bangalore-ashram\",\"contact\":\"info@vvmvp.org\",\"phone\":null},{\"title\":\"Bankura Information Centre\",\"address\":\"C\\/o: Sri Dilip KR.Dey,Suvankar Sarani,Near Dr.C R Pal,Pratap Bagan, Bankura, West Bengal, 722101\",\"lat\":23.2371632,\"lng\":87.0652217,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bankura-information-centre\",\"contact\":\"amiyapal123@gmail.com\",\"phone\":\"9474567333\"},{\"title\":\"Banswara\",\"address\":\"Banswara, Rajasthan, -\",\"lat\":23.55,\"lng\":74.45,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/banswara\",\"contact\":\"dchangeriya@gmail.com\",\"phone\":\"9414101030\"},{\"title\":\"Barabanki\",\"address\":\"B-504, Civil-Lines, Infront Of Civil Courts, Lucknow Road, Barabanki, Uttar Pradesh, 225001\",\"lat\":26.927166,\"lng\":81.191449,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/barabanki-0\",\"contact\":null,\"phone\":null},{\"title\":\"Barabanki\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/barabanki\",\"contact\":\"vikas.12banki@gmail.com\",\"phone\":\"9415075624\"},{\"title\":\"Baran\",\"address\":\"C\\/o Amar Shoe Store, main market , Baran, Baran, Rajasthan, -\",\"lat\":25.1,\"lng\":76.52,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/baran\",\"contact\":\"lajpataol@gmail.com\",\"phone\":\"9413116702\"},{\"title\":\"Barbil\",\"address\":\"Barbil, Orissa\",\"lat\":22.1053397,\"lng\":85.3789642,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/barbil\",\"contact\":null,\"phone\":\"9439386360\"},{\"title\":\"Bareilly\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bareilly\",\"contact\":\"neeta.mona@vvki.net\",\"phone\":\"9412318211\"},{\"title\":\"Bargarh\",\"address\":\"Lane No: 5, J.M.Colony, Budharaja, Sambalpur, Bargarh, Orissa, -\",\"lat\":21.3333,\"lng\":83.6167,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bargarh\",\"contact\":\"adarsh.agl@gmail.com\",\"phone\":\"9439637100\"},{\"title\":\"Baripada\",\"address\":\"Baripada, Orissa\",\"lat\":21.94,\"lng\":86.72,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/baripada\",\"contact\":\"ajaymodi3@yahoo.com\",\"phone\":\"9437013333\"},{\"title\":\"Barmer\",\"address\":\"S\\/o Sh.Guman Singh Bhati,Patwari,Gordiay Wale,Shree Shree Kirana Store,opp.Asapurna dharm Kanta,Nh.-15,Sashtri Nagar, Barmer, Rajasthan, -\",\"lat\":25.2511797,\"lng\":71.7400054,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/barmer\",\"contact\":null,\"phone\":\"9649963675;9509841241\"},{\"title\":\"Barnala\",\"address\":\"Barnala Info center, <br \\/>Heritage Computers, Subhash Gali, KC Road,Barnala, Barnala, Punjab\",\"lat\":30.3819446,\"lng\":75.5467979,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/barnala\",\"contact\":\"amanjindal@tridentindia.com\",\"phone\":\"09815943364\"},{\"title\":\"Barrackpore Information Center\",\"address\":\"5 Talbagan Road (west), 2nd Lane Barrackpore, Barrackpore, West Bengal, 700122\",\"lat\":22.7757052,\"lng\":88.3943282,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/barrackpore-information-center\",\"contact\":\"mahuya_5245@rediffmail.com\",\"phone\":\"9831229914\"},{\"title\":\"Basti\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/basti\",\"contact\":\"pddubey939@gmail.com\",\"phone\":\"9918695133\"},{\"title\":\"Batala\",\"address\":\"B-VI\\/ 187, Old Grain Market, DBN Road, Batala, Punjab\",\"lat\":31.8186,\"lng\":75.2028,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/batala\",\"contact\":null,\"phone\":\"94633 11482\"},{\"title\":\"Bathinda\",\"address\":\"Bathinda Info center, <br \\/># no.13061,Street no.8,Namdev Marg,Bathinda, Bathinda, Punjab\",\"lat\":30.2224098,\"lng\":74.9416879,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bathinda\",\"contact\":\"dkg.22366@gmail.com\",\"phone\":\"09814138292\"},{\"title\":\"Beawar\",\"address\":\"Anandani clinic and Dental Care Center, Arya Samaj Choraha, Pali Bazar, Beawar, Beawar, Rajasthan, -\",\"lat\":26.1,\"lng\":74.32,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/beawar\",\"contact\":\"dranandani@gmail.com\",\"phone\":\"9828140177\"},{\"title\":\"Beed\",\"address\":\"Maharashtra\",\"lat\":19.7514798,\"lng\":75.7138884,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/beed\",\"contact\":null,\"phone\":null},{\"title\":\"Belgaum\",\"address\":\"Ravi S Hiremath, <br \\/>57, Sector 8, Anjanaenagar, Near Datta Mandir, MM Extension, Belgaum, Karnataka, 590016\",\"lat\":15.8496953,\"lng\":74.4976741,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/belgaum\",\"contact\":\"jgdravihiremath@gmail.com\",\"phone\":\"9845183237\"},{\"title\":\"Belpahar\",\"address\":\"Belpahar, Orissa\",\"lat\":21.8218,\"lng\":83.8458,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/belpahar\",\"contact\":null,\"phone\":\"06645-250798\"},{\"title\":\"Berhampore Information Center\",\"address\":\"6, Ratan Saha Ghat Road khagra, Ghat Bhander, Berhampore, West Bengal\",\"lat\":24.0979577,\"lng\":88.2691594,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/berhampore-information-center\",\"contact\":\"sanjoykshetry@opermail.com\",\"phone\":\"9474707676\"},{\"title\":\"Berhampur\",\"address\":\"Puruna Berhampur, Kuthara Mandir Sahi, 1st Line, Berhampur, Orissa\",\"lat\":19.3149618,\"lng\":84.7940911,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/berhampur\",\"contact\":\"jgdgopal.padhy@gmail.com\",\"phone\":null},{\"title\":\"Betul\",\"address\":\"C\\/O, Shri Keshav Vasudev Vyas, Mulla Masjid ke samne, <br \\/>Kothi Bazar, Betul, Madhya Pradesh, 460001\",\"lat\":21.9160003,\"lng\":77.9038897,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/betul\",\"contact\":\" keshavvyas423@gmail.com \",\"phone\":\"9329820931\\/9406567419\"},{\"title\":\"Bhadohi\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhadohi\",\"contact\":\"anoopaolmzp@yahoo.in\",\"phone\":\"9935384199\"},{\"title\":\"Bhadrak\",\"address\":\"R.K. More & Associates, Vivekananda Marg, Bhadrak, Orissa, 756 001\",\"lat\":21.0676351,\"lng\":86.4887816,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhadrak\",\"contact\":null,\"phone\":\"+91-94370-56349; 263020; 267438\"},{\"title\":\"Bhandara\",\"address\":\"Maharashtra\",\"lat\":19.7514798,\"lng\":75.7138884,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhandara\",\"contact\":null,\"phone\":null},{\"title\":\"Bharatpur\",\"address\":\"Neenda Gate, Bharatpur, Bharatpur, Rajasthan, 321001\",\"lat\":27.215599,\"lng\":77.49015,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bharatpur\",\"contact\":null,\"phone\":\"9414026143\"},{\"title\":\"Bharuch\",\"address\":\"House no. # 18, street # 9,gnfc toenship,narmada nagar, Bharuch, Gujarat, -\",\"lat\":21.712609,\"lng\":73.033502,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bharuch\",\"contact\":\"drshah_61@yahoo.com\",\"phone\":\"9662013026\"},{\"title\":\"Bhavnagar\",\"address\":\"D-316, <br \\/>Kaliyabid, Bhavnagar, Gujarat, -\",\"lat\":21.7433405,\"lng\":72.1268032,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhavnagar\",\"contact\":\"bharatpatel201090@yahoo.com\",\"phone\":\"9428433100\"},{\"title\":\"Bhawani patna\",\"address\":\"Bhawani patna, Bihar\",\"lat\":25.6124151,\"lng\":85.0481078,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhawani-patna\",\"contact\":\"santosh.panda327@gmail.com\",\"phone\":null},{\"title\":\"Bhilai\",\"address\":\"Qr.5\\/b, street 20, sector-8, Bhilai, Bhilai, Chhattisgarh\",\"lat\":21.1938423,\"lng\":81.3220628,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhilai\",\"contact\":\"cgapex@vvki.net\",\"phone\":\"9827984767;0788-2290229\"},{\"title\":\"Bhilwara\",\"address\":\"C\\/o Shashi Bros.& Enterprises,Sabun Marg, Near Ashoka Hotel, Bhilwara, Rajasthan, -\",\"lat\":25.321377,\"lng\":74.586953,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhilwara\",\"contact\":null,\"phone\":\"9414116025\"},{\"title\":\"Bhiwani\",\"address\":\"Nai kalalo ki gali, Bichla BajarBhiwani, Haryana, 127021\",\"lat\":28.836618,\"lng\":76.1776494,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhiwani\",\"contact\":\"tinny.singla@gmail.com\",\"phone\":\"9416858892\"},{\"title\":\"Bhopal\",\"address\":\"VVKI MP Apex Body House No.2, Amaltas Colony Phase-1 Chuna Bhatti, Kolar Road, Bhopal, Madhya Pradesh, 462 016\",\"lat\":23.1743338,\"lng\":77.3860482,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhopal\",\"contact\":\"mpapex@vvki.net\",\"phone\":\"098930-62119 \\/ 098272-64760\"},{\"title\":\"Bhubaneshwar\",\"address\":\"VVKI-ODISHA APEX BODY, <br \\/>1st Floor, Plot No: M-2, Samant Vihar, Nalco Square. Near Kalinga Hospital, Chandrasekharpur, Bhubaneshwar, Orissa, 751016\",\"lat\":20.3124562,\"lng\":85.8180235,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bhubaneshwar\",\"contact\":\" orissaapex@vvki.net\",\"phone\":\"(0674) 2301750\"},{\"title\":\"Bijnor\",\"address\":\"Uttar Pradesh\",\"lat\":27.5705886,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bijnor\",\"contact\":\"mithilesh.agarwal@vvki.net\",\"phone\":\"9897167534\"},{\"title\":\"Bikaner\",\"address\":\"Ashirwad, 8 B \\/65, Jai Narayan Vyas Nagar,Bikaner, Bikaner, Rajasthan, -\",\"lat\":28.016667,\"lng\":73.311944,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bikaner\",\"contact\":\"bikaner2009@yahoo.co.in\",\"phone\":null},{\"title\":\"Bilga\",\"address\":\"C\\/o M\\/s Durga & Sons, Main Bazzar, Bilga, Punjab, 144036 \",\"lat\":31.0501545,\"lng\":75.6546595,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bilga\",\"contact\":\"balram68@yahoo.co.in\",\"phone\":\"01826 245132\\/9417968339\"},{\"title\":\"Bilimora\",\"address\":\"25\\/ A,Jalnagar Society, Mahadev Nagar,Gohar Baug, Bilimora, Gujarat, Bilimora, Gujarat, 396321\",\"lat\":20.7702199,\"lng\":72.9625779,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bilimora\",\"contact\":\"aniljgd2003@yahoo.co.in\",\"phone\":\"02634 - 284703; 02642-245501\"},{\"title\":\"Bodh Gaya\",\"address\":\"Art of Living,  Shekwara, Jindapur.  PS- Bodhgaya University (Website : http:\\/\\/www.bodhgayaaolashram.org\\/), Gaya, BiharAndhra Pradesh\",\"lat\":17.0477624,\"lng\":80.0981869,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bodh-gaya-0\",\"contact\":null,\"phone\":\"093320841610\"},{\"title\":\"Bodh Gaya\",\"address\":\"Bihar\",\"lat\":25.0960742,\"lng\":85.3131194,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bodh-gaya-1\",\"contact\":null,\"phone\":null},{\"title\":\"Bodhgaya-Ashram\",\"address\":\"Bihar\",\"lat\":25.0960742,\"lng\":85.3131194,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bodhgaya-ashram\",\"contact\":null,\"phone\":null},{\"title\":\"Bokaro\",\"address\":\"Bokaro Information Centre, Sect 1 C, Qtr No 139 Bokaro Steel City 827001BokaroJharkhand\",\"lat\":23.6734798,\"lng\":86.1293792,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bokaro\",\"contact\":\"aolbokaro@gmail.com \",\"phone\":\"9334382722\"},{\"title\":\"Bongaon\",\"address\":\"West Bengal\",\"lat\":22.9867569,\"lng\":87.8549755,\"website\":\"http:\\/\\/www.artofliving.org\\/in-en\\/bongaon\",\"contact\":null,\"phone\":null}]}";

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //pb.setVisibility(View.GONE);
            JSONObject ob;
            JSONArray ar;
            String id = null;
            Log.e("here", text + " ");
            try {
                ob = new JSONObject(text);
                ar = ob.getJSONArray("centers");
                for (int i = 0; i < ar.length(); i++) {
                    ob = new JSONObject(ar.getString(i));

                    Centers o = new Centers();
                    o.title = ob.getString("title");
                    o.add = ob.getString("address");
                    o.lat = ob.getString("lat");
                    o.lon = ob.getString("lng");
                    o.web = ob.getString("website");
                    o.sontact = ob.getString("contact");
                    o.phone = ob.getString("phone");
                    o.seq = i;
                    centres.add(o);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < centres.size();i++)
            {
                Centers o = new Centers();
                o = centres.get(i);
                ShowMarker(Double.parseDouble(o.lat), Double.parseDouble(o.lon),o.title,R.drawable.ic_pin_drop_black_24dp);
            }

            if(latitude!=null && longitude!=null)
                ShowMarker(latitude,longitude,"",R.drawable.ic_pin_drop_black_24dp);
        }
    }

}
