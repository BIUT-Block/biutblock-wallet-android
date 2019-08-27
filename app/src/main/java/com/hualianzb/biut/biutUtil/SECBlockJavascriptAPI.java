package com.hualianzb.biut.biutUtil;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SECBlockJavascriptAPI {

    private String secSDKJSCode;
    private JSContext jsContext = new JSContext();

    public SECBlockJavascriptAPI(Context applicationContext) throws IOException {
        secSDKJSCode = loadJSCode(applicationContext);
        jsContext.evaluateScript(secSDKJSCode);
    }

    public String encryptKeystore(String keystoreString, String password) {
        Log.d("Test Info", keystoreString);
        jsContext.evaluateScript("var KeyString = SECSDK.encryptKeystore(\'" + keystoreString + "\', \"" + password + "\")");
        final JSValue KeyString = jsContext.property("KeyString");
        return KeyString.toString();
    }

    public String decryptKeystore(String cryptedString, String password) {
        jsContext.evaluateScript("var KeyData = SECSDK.decryptKeystore(\"" + cryptedString + "\", \"" + password + "\")");
        final JSValue KeyData = jsContext.property("KeyData");
        try {
            JSONObject obj = new JSONObject(KeyData.toString());
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String PrivKeytoAddress(String privKey) {
        jsContext.evaluateScript("var Address = SECSDK.privKeytoAddress(\"" + privKey + "\")");
        final JSValue Address = jsContext.property("Address");
        return Address.toString();
    }

    public String EntropyToMnemonic(String privKey) {
        jsContext.evaluateScript("var Mnemonic = SECSDK.entropyToMnemonic(\"" + privKey + "\")");
        final JSValue Mnemonic = jsContext.property("Mnemonic");
        return Mnemonic.toString();
    }

    public String MnemonicToEntropy(String Mnemonic) {
        jsContext.evaluateScript("var PrivKey = SECSDK.mnemonicToEntropy(\"" + Mnemonic + "\")");
        final JSValue PrivKey = jsContext.property("PrivKey");
        return PrivKey.toString();
    }

    public String TxSign(String _Tx) {
        jsContext.evaluateScript("var Tx = SECSDK.txSign(\'" + _Tx + "\')");
        final JSValue Tx = jsContext.property("Tx");
        return Tx.toString();
    }

    public String biuTxSign(String _Tx) {
        jsContext.evaluateScript("var Tx = SECSDK.biuTxSign(\'" + _Tx + "\')");
        final JSValue Tx = jsContext.property("Tx");
        return Tx.toString();
    }

    public String biutTxSign(String _Tx) {
        jsContext.evaluateScript("var Tx = SECSDK.biutTxSign(\'" + _Tx + "\')");
        final JSValue Tx = jsContext.property("Tx");
        return Tx.toString();
    }

    private String loadJSCode(Context applicationContext) throws IOException {
        String str;
        StringBuffer buf = new StringBuffer();
        InputStream in = applicationContext.getAssets().open("SECSDK.bundle.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        if (in != null) {
            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n");
            }
        }
        return buf.toString();
    }
}
