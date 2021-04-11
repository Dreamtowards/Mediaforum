
import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:flutter_markdown/flutter_markdown.dart';

import 'package:flutter/cupertino.dart';
import 'package:mediaforum/pages/FragHome.dart';
import 'package:mediaforum/util/util.dart';

import '../main.dart';

class WidgetHomePostPreview extends StatefulWidget {
  Map postdata;

  WidgetHomePostPreview(this.postdata);

  @override
  State<StatefulWidget> createState() {
    return WidgetHomePostPreviewState();
  }
}

class WidgetHomePostPreviewState extends State<WidgetHomePostPreview> {


  @override
  Widget build(BuildContext context) {
    var LIKEBTN_OFF_COLOR = Color(0xFFEEEEEE);
    var LIKEBTN_ON_COLOR = Theme.of(context).primaryColor;
    
    var postdata = widget.postdata;
    
    var BottomOpBarLeft = Row(children: [
      MaterialButton(  // Button Like
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        color: postdata['was_liked']?LIKEBTN_ON_COLOR: LIKEBTN_OFF_COLOR,
        textColor: postdata['was_liked']?Colors.white: Colors.black,
        padding: EdgeInsets.zero,
        minWidth: 74, height: 35,
        child: Row(children: [
          Icon(MaterialCommunityIcons.menu_up, size: 20),
          Text("${postdata['liked_count']} ", style: TextStyle(fontWeight: FontWeight.bold))
        ]),
        onPressed: () {
          setState(() {
            postdata['was_liked'] = !postdata['was_liked'];
            postdata['liked_count'] += postdata['was_liked']? 1 : -1;

            NetReq.request("/api/savelist_opitem", {
              "op": postdata['was_liked']?"add":"remove",
              "savelist_id": Globs.USER['savelists']['LIKEDPOSTS']['id'],
              "post_id": postdata['id']
            }, (resp) {
              Scaffold.of(context).showSnackBar(SnackBar(
                  content: Text(postdata['was_liked']?"Added to likedposts.":"Removed from likedposts."),
                  duration: Duration(milliseconds: 1500)));
            });
          });
        },
      ),
      SizedBox(width: 6),
      MaterialButton(  // Button Dislike
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        color: Color(0xFFEEEEEE),
        padding: EdgeInsets.zero,
        minWidth: 24, height: 35,
        child: Icon(MaterialCommunityIcons.menu_down, size: 20),
        onPressed: () { },
      ),
      SizedBox(width: 10),
      Opacity(opacity: 0.85, child: MaterialButton(  // TextButton Comment
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        padding: EdgeInsets.zero,
        minWidth: 56,
        child: Row(children: <Widget>[
          Icon(MaterialCommunityIcons.comment, size: 15),
          Text(" 1.2k", style: TextStyle(fontWeight: FontWeight.w700, fontSize: 13))
        ]),
        onPressed: () { })),
      Opacity(opacity: 0.85, child: MaterialButton(  // TextButton Save
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        padding: EdgeInsets.zero,
        minWidth: 56,
        child: Row(children: <Widget>[Icon(MaterialCommunityIcons.bookmark, size: 17), Text(" Save", style: TextStyle(fontWeight: FontWeight.w700, fontSize: 13))]),
        onPressed: () { },
      )),
      SizedBox(width: 2),
      Opacity(opacity: 0.85, child: MaterialButton( // TextButton Share
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        padding: EdgeInsets.zero,
        minWidth: 30,
        child: Row(children: <Widget>[Icon(Icons.screen_share, size: 19), Text("", style: TextStyle(fontWeight: FontWeight.w700, fontSize: 13))]),
        onPressed: () {
          Scaffold.of(context).showSnackBar(SnackBar(
            content: Text("Text"),
            duration: Duration(milliseconds: 1500)));
        },
      )),
    ]);

    String timesago = Utils.getTextTimesago(1);

    return Container(
      decoration: FragHome.HomeCardDecoration,
      margin: EdgeInsets.fromLTRB(0, 0, 0, 8),
      padding: EdgeInsets.all(16),
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        SizedBox(height: 4),
        Text(postdata['title'], style: Theme.of(context).textTheme.title),
        SizedBox(height: 3),
        Text("${postdata['user_username']} · $timesago", style: Theme.of(context).textTheme.caption),
        SizedBox(height: 8),
        MarkdownBody(data: postdata['content']),
        SizedBox(height: 16),
//         Bottom Buttons Row
        Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: <Widget>[
          BottomOpBarLeft,
          Container(width: 30, child: Align(alignment: FractionalOffset(5, 0), child: Material(
            type: MaterialType.transparency,
            shape: CircleBorder(),
            clipBehavior: Clip.antiAlias,
            child: InkResponse(
              onTap: (){print("more tapped");},
              child: Padding(
                padding: EdgeInsets.all(4),
                child: Icon(Icons.more_vert, size: 20),
              )
            )
          )))
        ])
      ])
    );
  }
}