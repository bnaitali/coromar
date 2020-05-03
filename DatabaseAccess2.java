package com.bnaitali.marocoro;

import android.content.Context;
import android.util.Log;

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

public class DatabaseAccess2 {
    private static final String COGNITO_POOL_ID = "us-east-2:XXXXXXXXXXXXXXXXXXXXXX";// cognito pool
    private static final Regions MY_REGION = Regions.US_EAST_2;
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;
    private Context context;
    private final String DYNAMODB_TABLE = "XXXXX"; //Table name created in dynamo db
    CognitoCachingCredentialsProvider credentialsProvider;


    /*
    * CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
    getApplicationContext(),
    "us-east-2:326de064-1f91-41a8-9b07-f446cd9e00b3", // ID du groupe d'identités
    Regions.US_EAST_2 // Région
);
    * */


    private static volatile DatabaseAccess2 instance;
    private DatabaseAccess2(Context context) {
        this.context =context;
        credentialsProvider = new CognitoCachingCredentialsProvider(context, COGNITO_POOL_ID, MY_REGION);
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.US_EAST_2));// pay attention to the region
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
    }
    public static synchronized DatabaseAccess2 getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess2(context);
        }
        return instance;
    }
    public Document getItem (String user_id){
        Document result = dbTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(user_id));
        return result;
    }
    public List<String> getAllItems() {

   
        List<String> ls = new ArrayList<>();
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("notices");

        ScanResult result = dbClient.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()){

         
            ls.add(item.toString());
            
        }
      


        return ls;
    }
}