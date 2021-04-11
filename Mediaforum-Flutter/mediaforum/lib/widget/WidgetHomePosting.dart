import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:mediaforum/pages/FragHome.dart';

import '../main.dart';

class ViewHomePost extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return ViewHomePostState();
  }
}

class ViewHomePostState extends State<ViewHomePost> {

  bool _visibilyPostBtn = false;

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: FragHome.HomeCardDecoration,
      padding: EdgeInsets.all(16),
      margin: EdgeInsets.only(bottom: 8),
      child: Column(children: <Widget>[
          Row(crossAxisAlignment: CrossAxisAlignment.start, children: <Widget>[
            ClipRRect(child: Image.network(Globs.USER['avatar_url'], width: 40, height: 40, fit: BoxFit.cover), borderRadius: BorderRadius.circular(100)),
            SizedBox(width: 10),
            Expanded(child: ClipRRect(borderRadius: BorderRadius.circular(4), child: TextField(
              keyboardType: TextInputType.multiline,
              maxLines: null,
              style: TextStyle(fontSize: 15, fontWeight: FontWeight.w400),
              decoration: InputDecoration(
                hintText: "Wats New ?",
                filled: true,
                border: InputBorder.none
              ),
              onChanged: (s) {
                setState(() {
                  _visibilyPostBtn = s.isNotEmpty;
                });
              },
            )))
        ]),
        Visibility(visible: _visibilyPostBtn, child:
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: <Widget>[
              RaisedButton(
                color: Theme.of(context).primaryColor,
                textColor: Colors.white,
                onPressed: () {},
                child: Text("Post")
              )
            ],
          )
        )
        ],
      )
    );
  }
  
}