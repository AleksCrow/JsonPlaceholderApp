package com.alexvoronkov.jsonplaceholderapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvoronkov.jsonplaceholderapp.Interface.RequestInterface;
import com.alexvoronkov.jsonplaceholderapp.Models.Posts;
import com.alexvoronkov.jsonplaceholderapp.Retrofit.RetrofitApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private CardAdapter adapter;
    ArrayList<Card> list;
    private ArrayList<Posts> postses;
    private String text;
    public TextView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.recyclerView);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        contacts = (TextView) findViewById(R.id.contacts);
        getContacts();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.linearLayout);
        tabSpec.setIndicator("Главная");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("Тел.книга");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();

        RequestInterface request = RetrofitApi.getClient().create(RequestInterface.class);
        Call<ArrayList<Posts>> call = request.getPosts();
        call.enqueue(new Callback<ArrayList<Posts>>() {
            @Override
            public void onResponse(Call<ArrayList<Posts>> call, final Response<ArrayList<Posts>> response) {

                postses = response.body();

                for (int i = 0; i < 5; i++) {
                    list.add(new Card("posts " + (i + 1), postses.get(i).getBody()));
                }

                adapter = new CardAdapter(list);

                adapter.setListener(new CardAdapter.Listener() {
                    @Override
                    public void onClick(int t, int pos) {
                        if (t < 0 || t > 99) {
                            Toast.makeText(MainActivity.this, R.string.except, Toast.LENGTH_SHORT).show();
                        } else {
                            text = postses.get(t).getBody();
                            adapter.editPosts(pos, new Card("posts " + (pos + 1), text));
                        }
                    }
                });
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Posts>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Что то не так", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getContacts() {

        String phoneNumber = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        StringBuffer output = new StringBuffer();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    output.append("\n Имя: " + name);
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                            Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n Телефон: " + phoneNumber);
                    }
                }
                output.append("\n");
            }
            contacts.setText(output);
        }
    }
}
