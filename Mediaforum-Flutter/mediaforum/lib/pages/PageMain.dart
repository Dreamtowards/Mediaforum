import 'package:flutter/material.dart';
import 'package:mediaforum/pages/FragHome.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:mediaforum/pages/FragProfile.dart';

import 'FragLibs.dart';

class PageHome extends StatefulWidget {
  @override
  PageHomeState createState() => PageHomeState();
}

class PageHomeState extends State<PageHome> {

  int currentPageIndex = 0;
  List<Widget> pages = <Widget>[
    FragHome(),
    Text("Page Hoting"),
    Text("Page Subs"),
    FragLibs(),
    FragProfile()
  ];

  @override
  Widget build(BuildContext context) {

    TextStyle bottomNavTextStyle = TextStyle(fontSize: 13, fontWeight: FontWeight.w500); // todo: bold

Container(width: ,).

    return Scaffold(
      appBar: AppBar(
        title: Text("MIDFORUM", style: TextStyle(fontWeight: FontWeight.w500),),
        centerTitle: false,
        iconTheme: Theme.of(context).iconTheme,
        textTheme: Theme.of(context).textTheme,
        brightness: Brightness.light,
        backgroundColor: Colors.white,
        actions: <Widget>[
          IconButton(icon: Icon(Icons.cast),
            onPressed: () {},
          ),
          IconButton(icon: Icon(Icons.search),
            onPressed: () {},
          ),
          IconButton(icon: Icon(Icons.notifications_none),
            onPressed: () {},
          ),
          // IconButton(icon: Icon(Icons.apps),
          //   onPressed: () {},
          // ),
        ],
      ),
      body: Container(
        color: Color(0xffeeeeee),
        child: IndexedStack(
          index: currentPageIndex,
          children: pages,
        )
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: currentPageIndex,
        onTap: (i) {
          setState(() {
            currentPageIndex = i;
          });
        },
        items: [
          BottomNavigationBarItem(
            icon: Icon(MaterialCommunityIcons.library_books), // book_open, newspaper_variant_multiple
            title: Text("Home", style: bottomNavTextStyle),
            backgroundColor: Colors.black
          ),
          BottomNavigationBarItem(
            icon: Icon(MaterialCommunityIcons.widgets),  // MaterialCommunityIcons.hexagon_multiple Icons.widgets
            title: Text("Hoting", style: bottomNavTextStyle)
          ),
          BottomNavigationBarItem(
            icon: Icon(MaterialCommunityIcons.inbox_multiple),
            title: Text("Subs", style: bottomNavTextStyle)
          ),
          BottomNavigationBarItem(
            icon: Icon(MaterialCommunityIcons.folder),
            title: Text("Libs", style: bottomNavTextStyle)
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_circle),
            title: Text("Prof", style: bottomNavTextStyle)
          )
        ],
      )
    );
  }
}
