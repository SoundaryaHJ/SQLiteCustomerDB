package com.example.mysqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText Age, Name;
    Button viewAll, add;
    Switch switch_active;
    ListView customerlist;
    ArrayAdapter customerArray;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.Name);
        Age =(EditText) findViewById(R.id.Age);
        viewAll = (Button) findViewById(R.id.viewAll);
        add = (Button) findViewById(R.id.add);
        switch_active = (Switch) findViewById(R.id.switch_active);
        customerlist = (ListView) findViewById(R.id.customerList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowCustomerOnListView(dataBaseHelper);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CustomerModel customerModel;

                try {
                    customerModel = new CustomerModel(-1, Name.getText().toString(), Integer.parseInt(Age.getText().toString()), switch_active.isChecked());

                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }

                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "error", 0, false);


                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(customerModel);

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                ShowCustomerOnListView(dataBaseHelper);

            }
        });



        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                ShowCustomerOnListView(dataBaseHelper);

            }
        });

        customerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                CustomerModel clickedCustomer = (CustomerModel) adapterView.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomerOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted" + clickedCustomer.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void ShowCustomerOnListView(DataBaseHelper dataBaseHelper2) {
        customerArray = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryOne());
        customerlist.setAdapter(customerArray);
    }
}