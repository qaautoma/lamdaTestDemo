package com.taygo.utility;

import io.restassured.*;
import io.restassured.path.json.*;
import io.restassured.response.*;
import org.apache.commons.text.*;

import static com.taygo.utility.EmailFormat.email;

public class getURLFromMail {
    public static String Uid;
    public static String verificationLink;

    public void uid () {
//        System.out.println ("test1" + email);
        String endpointUrl = "https://inboxes.com/api/v2/inbox/" + "child02@getnada.com";
        System.out.println ("endpointUrl" + endpointUrl);
        // Send the GET request with authorization
        Response response = RestAssured.given ().get (endpointUrl).then ().extract ().response ();

        // Handle the response data
        if (response.getStatusCode () == 200) {
            // Successful response
            System.out.println ("Response data:");
            System.out.println (response.getBody ().asString ());
            String CreateTwe = response.asString ();
            JsonPath js1 = new JsonPath (CreateTwe);

            // Get Validatable response to perform validation
            ValidatableResponse validatableResponse = response.then ();
            validatableResponse.statusCode (200);
            Uid = js1.get ("msgs[0].uid");
            System.out.println ("uid" + Uid);

        } else {
            System.out.println ("Response data:");
            System.out.println (response.getBody ().asString ());
        }
    }

    public void getMessage () {
        String endpointUrl = "https://inboxes.com/api/v2/message/" + Uid;

        // Send the GET request with authorization
        Response response = RestAssured.given ().get (endpointUrl).then ().extract ().response ();

        if (response.getStatusCode () == 200) {
            // Get the response body as a string
            String responseBody = response.getBody ().asString ();
            System.out.println ("Raw Response Body: " + responseBody);

            // Clean the escaped quotes in the response if needed
            String cleanedResponseBody = responseBody.replace ("\\\"" , "\"");

            // Decode HTML entities like &gt; to >
            String unescapedResponseBody = StringEscapeUtils.unescapeHtml4 (cleanedResponseBody);

            // Use regex to find the first <a> tag with href
            String hrefPattern = "<a.*?href=[\"'](.*?)[\"'].*?>";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile (hrefPattern);
            java.util.regex.Matcher matcher = pattern.matcher (unescapedResponseBody);

            if (matcher.find ()) {
                // Extract the href attribute value
                verificationLink = matcher.group (1);
                System.out.println ("Verification Link: " + verificationLink);
            } else {
                System.out.println ("No <a> tag with href found in the response.");
            }

        } else {
            System.out.println ("Error: Response code is not 200");
            System.out.println ("Response Body: " + response.getBody ().asString ());
        }
    }


    public static void main (String[] abc) {
        getURLFromMail getURLFromMail = new getURLFromMail ();
        getURLFromMail.uid ();
        getURLFromMail.getMessage ();
    }
}


