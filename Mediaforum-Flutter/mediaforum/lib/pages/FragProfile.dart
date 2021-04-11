

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:mediaforum/main.dart';
import 'package:mediaforum/pages/FragHome.dart';

class FragProfile extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _FragProfileState();
  }
}

class _FragProfileState extends State<FragProfile> {

  BoxDecoration ShadowCardDecoration = BoxDecoration(color: Colors.white, borderRadius: BorderRadius.circular(10), boxShadow: [BoxShadow(color: Colors.black12, blurRadius: 4)]);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      child: ListView(children: <Widget>[
      Container(
        margin: EdgeInsets.fromLTRB(20, 32, 24, 30),
        child: Row(crossAxisAlignment: CrossAxisAlignment.start, children: <Widget>[
            ClipRRect(child: Image.network(Globs.USER['avatar_url'], width: 56, height: 56, fit: BoxFit.cover), borderRadius: BorderRadius.circular(20)),
            SizedBox(width: 14),
            Column(mainAxisSize: MainAxisSize.min, crossAxisAlignment: CrossAxisAlignment.start, children: <Widget>[
              SizedBox(height: 0),
              Text(Globs.USER['username'], style: TextStyle(color: Colors.black, fontSize: 24, fontWeight: FontWeight.w700)),
              SizedBox(height: 4),
              Text(Globs.USER['email'], style: TextStyle(color: Colors.black54, fontSize: 13)),
              SizedBox(height: 2),
              ButtonTheme(
                materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                minWidth: 0,
                padding: EdgeInsets.zero,
                child: FlatButton(child: Text("Account settings"), textColor: Colors.blue.shade600, onPressed: (){})
              )
          ])
        ]),
      ),
      Divider(),
      opItem("Your content", MaterialCommunityIcons.account, true, () {}),
      opItem("Paid memberships", MaterialCommunityIcons.coffee, true, () {}),
      Divider(),
      opItem("Settings", Icons.settings, true, () {}),
      opItem("Dark mode: Off", Icons.brightness_6, true, (){}),
      opItem("Language: English", Icons.language, true, (){}),
      opItem("Help", MaterialCommunityIcons.cloud_question, false, (){}),
      opItem("Send feedback", MaterialCommunityIcons.alert_circle, false, (){}),
      Divider(),
      opItem("Logout Account", MaterialCommunityIcons.door_open, false, (){}),
      SizedBox(height: 60)

    ]));
  }

  Widget opItem(name, icon, hasNavIcon, onTap) {
    return Container(
      child: Material(
          type: MaterialType.transparency,
          child: InkWell(
            onTap: onTap,
            child: Padding(
              padding: EdgeInsets.fromLTRB(24, 18, 24, 18),
              child: Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: <Widget>[
                Row(children: <Widget>[
                  Icon(icon, size: 20),
                  SizedBox(width: 16),
                  Text(name, style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500))
                ]),
                hasNavIcon? Icon(Icons.chevron_right):SizedBox(height: 24)
              ])
            )
          )
        )
    );
  }
}