
import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:phonecall_state/phonecall_state.dart';
import 'package:phonecall_state/phone_state_i.dart';


void main() {
  runApp(new MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    return new MaterialApp(
      title: 'Sensors Demo',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: new MyHomePage(title: 'Flutter Demo Home Page', key: null,),
    );
  }
}

class MyHomePage extends StatefulWidget {
  final String title;

  MyHomePage({ Key? key, required this.title}) : super(key: key);

  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  late StreamSubscription streamSubscription;

  @override
  Widget build(BuildContext context) {


    print('setstate');
    return Scaffold(
      appBar: AppBar(
        title: const Text('Example'),
      ),
      body:  Padding(
        padding: const EdgeInsets.all(16.0),
        child:  Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
             const Text('A:'),
             TextButton(
                onPressed: () {
                  setState(() {
                    print('refresh');
                  });
                },
                child: const Text('Refresh'))
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    //streamSubscription.cancel();

  }

  @override
  void initState() {
    super.initState();
    streamSubscription = phoneStateCallEvent.listen((PhoneStateCallEvent event) {
      print('Call is Incoming or Connected ${event.stateC}');
      //event.stateC has values "true" or "false"
    });

  }
}
