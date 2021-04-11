import 'dart:convert';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:path_provider/path_provider.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

typedef Consumer = void Function(dynamic);

class NetReq {

  static const String API_ADDR = "http://192.168.1.110:8080";

//  static final Client http = Client();

  static request(String path, Map<String, dynamic> data, Consumer respf, {Consumer errf, void Function() lcall}) async {
    try {
//    print("req url: " + API_ADDR+path);
      data["uid"] = 35;
      data["accessToken"] = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
      var resp = await http.post(API_ADDR+path, body: json.encode(data), headers: {"Content-Type": "application/json"});  // API_ADDR+path
      var respBody = utf8.decode(resp.bodyBytes);
      var respBodyJsonObj = respBody.isEmpty ? {} : json.decode(respBody);

      if (resp.statusCode != 200)
        throw Exception(respBodyJsonObj['message']);

      respf(respBodyJsonObj);

    } catch (ex) {
      if (errf != null) {
        errf(ex.message);
      } else {
        print("NetReqErr: ${ex.message}");
      }
    } finally {
      if (lcall != null) {
        lcall();
      }
    }
  }

}

class Utils {
  static String getTextTimesago(int stampmillis) {
    int times = (DateTime.now().millisecondsSinceEpoch - stampmillis) ~/ 1000; // in seconds
    if (times < 60) {  // < 60s
      return "A moment ago";
    } else if (times < 60*60) {  // < 1h
      return "${times~/60} minutes ago";
    } else if (times < 60*60*24) {  // < 1day
      return "${times~/(60*60)} hours ago";
    } else if (times < 60*60*24*30) {  // < 1mon
      return "${times~/(60*60*24)} days ago";
    } else if (times < 60*60*24*30*12) {  // < 1ye
      return "${times~/(60*60*24*30)} months ago";
    } else {
      return "${times~/(60*60*24*30*12)} years ago";
    }
  }
}

class Settings {

  static SharedPreferences perferences;

  static init() async {
    WidgetsFlutterBinding.ensureInitialized();
    perferences = await SharedPreferences.getInstance();
    return true;
  }
}