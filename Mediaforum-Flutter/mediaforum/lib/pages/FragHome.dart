
import 'package:flutter/material.dart';
import 'package:mediaforum/util/util.dart';
import 'package:mediaforum/widget/WidgetHomePostPreview.dart';

class FragHome extends StatefulWidget {

  static var HomeCardDecoration = BoxDecoration(color: Colors.white, boxShadow: [BoxShadow(color: Colors.grey.withOpacity(0.2), blurRadius: 2)]);

  @override
  State<StatefulWidget> createState() => FragHomeState();
}

class FragHomeState extends State<FragHome> {
  List postlistdata = [];

  _appendPostlistData() async {
    await NetReq.request("/api/post_getfeedlist", {}, (resp) {
      setState(() => postlistdata.addAll(resp));
    });
  }

  @override
  void initState() {
    super.initState();
    _appendPostlistData();
  }

  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
      onRefresh: () async {
        postlistdata = [];  // clear
        return await _appendPostlistData();  // append/fill datas
      },
      child: NotificationListener(child: SingleChildScrollView(child: Column(crossAxisAlignment: CrossAxisAlignment.stretch, children: <Widget>[
        SizedBox(height: 8),
        // ViewHomePost(),
        Column(crossAxisAlignment: CrossAxisAlignment.stretch, children: List.generate(postlistdata.length, (i) {
          return WidgetHomePostPreview(postlistdata[i]);
        })),
        Column(children: <Widget>[
          SizedBox(height: 25),
          CircularProgressIndicator(),
          SizedBox(height: 30),
        ])
      ])), onNotification: (e) {
        if (e is ScrollEndNotification) {
          if (e.metrics.extentAfter == 0.0) {
            _appendPostlistData();
          }
        }
      })
    );
  }
}


