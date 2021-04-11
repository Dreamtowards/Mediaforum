
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:mediaforum/pages/FragHome.dart';

import '../main.dart';

class FragLibs extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return FragLibsState();
  }
}

class FragLibsState extends State<FragLibs> {
  
  @override
  Widget build(BuildContext context) {
    List createdSavelists = Globs.USER['savelists']['created'];

    return Container(
      color: Colors.white,
      child: ListView(children: <Widget>[
      SizedBox(height: 8),
      Divider(),
      buildSavelistSysItem("History", Icons.history),
      buildSavelistSysItem("Read later", Icons.watch_later),
      buildSavelistSysItem("Liked posts", Icons.thumb_up),
      Divider(),
      buildCreateSavelistItem(),
      Column(children: List.generate(createdSavelists.length, (i) {
        return buildSavelistUserCreatedItem(createdSavelists[i]['name']);
      })),
      SizedBox(height: 80)
    ]));
  }

  Widget buildSavelistSysItem(text, icon) {
    Color color = Color(0xFF353535);
    return Container(
      // decoration: FragHome.HomeCardDecoration,
      child: Material(type: MaterialType.transparency,
        child: InkWell(
          onTap: (){},
          child: Padding(
            padding: EdgeInsets.fromLTRB(20, 14, 20, 14),
            child: Row(children: <Widget>[
            Icon(icon, color: color), 
            SizedBox(width: 16),
            Text(text, style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600, color: color))
          ]))
        )
      ),
    );
  }

  Widget buildSavelistUserCreatedItem(text) {
    Color color = Color(0xFF444444);
    return Container(
      // decoration: FragHome.HomeCardDecoration,
      child: Material(type: MaterialType.transparency,
        child: InkWell(
          onTap: (){},
          child: Padding(
            padding: EdgeInsets.fromLTRB(20, 14, 20, 14),
            child: Row(children: <Widget>[
              Icon(MaterialCommunityIcons.collage, color: color), 
              SizedBox(width: 16),
              Column(crossAxisAlignment: CrossAxisAlignment.start, children: <Widget>[
                Text(text, style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500, color: color)),
                SizedBox(height: 2),
                Text("Username · 80 posts", style: TextStyle(fontSize: 11.6, fontWeight: FontWeight.w500, color: Colors.black54))
              ])
          ]))
        )
      ),
    );
  }

  Widget buildCreateSavelistItem() {
    return Container(
      // decoration: FragHome.HomeCardDecoration,
      child: Material(type: MaterialType.transparency,
        child: InkWell(
          onTap: (){},
          child: Padding(
            padding: EdgeInsets.fromLTRB(20, 14, 20, 10),
            child: Row(children: <Widget>[
            Icon(MaterialCommunityIcons.plus, color: Colors.blue.shade700), 
            SizedBox(width: 16),
            Text("Create savelist", style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600, color: Colors.blue.shade700))
          ]))
        )
      ),
    );
  }
}