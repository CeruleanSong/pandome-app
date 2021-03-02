import 'package:flutter/material.dart';

import 'package:pandome/ui/main/MainPage.dart';

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'P(a)ndo.me',
      theme: ThemeData(
        primaryColor: Color(0x6200EE),
        primarySwatch: Colors.pink,
      ),
      home: MainPage(title: 'Flutter Demo Home Page'),
    );
  }
}
