import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:mediaforum/pages/PageMain.dart';
import 'package:mediaforum/util/util.dart';

void main() async {

  await Settings.init();

  Globs.USER = json.decode(Settings.perferences.getString("_user_info") ?? "{}");

  runApp(App());
  
  NetReq.request("/api/user_info", {}, (resp) {  // later update new
    Globs.USER = resp;
    Settings.perferences.setString("_user_info", json.encode(resp));
  });
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    int _primaryValue = 0xFF333333;
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).unfocus();
      },
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          primarySwatch: MaterialColor(_primaryValue, <int, Color>{
            50: Color(0xFF333333),
            100: Color(0xFF333333),
            200: Color(0xFF333333),
            300: Color(0xFF333333),
            400: Color(0xFF333333),
            500: Color(_primaryValue),
            600: Color(0xFF333333),
            700: Color(0xFF333333),
            800: Color(0xFF333333),
            900: Color(0xFF333333),
          }),
        ),
        home: PageHome(),
      )
    );
  }
}

class Globs {

  static Map USER = {
    "username": "UsrNM",
    "savelists": {
      "LIKEDPOSTS": {"id"}
    }
  };

}