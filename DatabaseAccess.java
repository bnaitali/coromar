package com.bnaitali.marocoro;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DatabaseAccess {
    private static final String COGNITO_POOL_ID = "us-east-2:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";// your cognito pool
    private static final Regions MY_REGION = Regions.US_EAST_2;
    private static AmazonDynamoDBClient dbClient;
    private static Table dbTable;
    private Context context;
    private static int i;
    private static String DYNAMODB_TABLE;//"XXXXXX"; //Table name created in dynamo db
    CognitoCachingCredentialsProvider credentialsProvider;


    /*
    * CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
    getApplicationContext(),
    "us-east-2:326de064-1f91-41a8-9b07-f446cd9e00b3", // ID du groupe d'identités
    Regions.US_EAST_2 // Région
);
    * */


    private static volatile DatabaseAccess instance;
    private DatabaseAccess(Context context) {
        this.context =context;
       // this.i=i;
        credentialsProvider = new CognitoCachingCredentialsProvider(context, COGNITO_POOL_ID, MY_REGION);
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.US_EAST_2));// pay attention to the region


    }

    public static synchronized DatabaseAccess getInstance(Context context,int k) {

        i = k;

        if (instance == null) {
            instance = new DatabaseAccess(context);

        }
        if(i==1){
            DYNAMODB_TABLE = "XXXX";
        }else if(i==2){
            DYNAMODB_TABLE = "XXXXX";
        }else if(i==3){

            DYNAMODB_TABLE = "XXXX";
        }

        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
        return instance;
    }



    public Document getItem (String user_id){
        Document result = dbTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(user_id));
        return result;
    }
    public List<String> getAllItems() {

   

List<String> ls = new ArrayList<>();
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(DYNAMODB_TABLE);

        ScanResult result = dbClient.scan(scanRequest);


        for (Map<String, AttributeValue> item : result.getItems()){
         
            ls.add(item.values().toString());
   
        }




return ls;
    }

}